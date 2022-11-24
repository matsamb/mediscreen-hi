package com.medi.historic.restcontroller;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.medi.historic.entity.Report;
import com.medi.historic.entity.ReportDTO;
import com.medi.historic.service.ReportService;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
public class ReportRestcontroller {

	@Autowired
	private ReportService reportService;

	ReportRestcontroller(ReportService reportService) {
		this.reportService = reportService;
	}

	@GetMapping("/report")
	public ResponseEntity<List<ReportDTO>> findReport(@RequestParam Integer patientId) {

		List<ReportDTO> reportList = List.of(new ReportDTO("Not_Registered"));
		if (reportService.findReportByPatientId(patientId).equals(reportList)) {
			log.info("No registered report for patient number" + patientId);
			return ResponseEntity.notFound().build();
		} else {
			log.info("Report found for patient number " + patientId);
			return ResponseEntity.ok(reportService.findReportByPatientId(patientId));
		}

	}

	@PostMapping("/report/add")
	public ResponseEntity<ReportDTO> createReport(@RequestBody Optional<ReportDTO> reportDTO) {

		if (reportDTO.isEmpty()) {
			log.info("No request body");
			return ResponseEntity.badRequest().build();
		} else {
			log.info("Creating new patient: "+reportDTO);
			ReportDTO newreportDTO = reportDTO.get();
			Report newreport = new Report();
			newreport.setComment(newreportDTO.getComment());
			newreport.setDate(newreportDTO.getDate());
			newreport.setPatientId(newreportDTO.getPatientId());
			String savedReportId = UUID.randomUUID().toString();
			newreport.setId(savedReportId);
			Date issueNow = new Date();
			issueNow.setTime(System.currentTimeMillis());
			newreport.setDate(issueNow);
			newreportDTO.setDate(issueNow);
			newreportDTO.setId(savedReportId);

			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/report")
					.buildAndExpand("?patientId=" + newreport.getPatientId()).toUri();
			log.info("Loading new report " + newreport.getId() + " for patient " + newreport.getPatientId());
			reportService.saveReport(newreport);
			return ResponseEntity.created(location).body(newreportDTO);

		}

	}

	@PutMapping("/report")
	public ResponseEntity<ReportDTO> updateReport(@RequestParam Integer patientId, @RequestBody Optional<ReportDTO> reportDTO) {

		if (reportDTO.isEmpty()) {
			log.info("No request body");
			return ResponseEntity.badRequest().build();
		} else {
			log.info("Updating report: "+reportDTO);
			ReportDTO newreportDTO = reportDTO.get();
			log.info("Updating report: "+newreportDTO);

			Report newReport = new Report();
			newReport.setComment(newreportDTO.getComment());
			newReport.setDate(newreportDTO.getDate());
			newReport.setPatientId(newreportDTO.getPatientId());
			newReport.setId(newreportDTO.getId());
			log.info("Updating report " + newReport);

			log.info("Report " + newReport.getId() + ", for patient number " + newReport.getPatientId() + ", updated");
			reportService.saveReport(newReport);
			return ResponseEntity.ok(newreportDTO);
		}
	}

	@DeleteMapping("/report")
	public ResponseEntity<ReportDTO> deleteReport(@RequestBody Optional<ReportDTO> reportDTO,
			@RequestParam Integer patientId) {

		if (reportDTO.isEmpty()) {
			log.info("No request body");
			return ResponseEntity.badRequest().build();
		} else {
			ReportDTO report = reportDTO.get();
			log.info("Deleting all Patient " + patientId + " report"+ report);

				log.info("Deleting patient "+reportService.findReportByPatientId(patientId));
				for (ReportDTO r : reportService.findReportByPatientId(patientId)) {
					if (r.getId().contentEquals(report.getId())){
						log.info("Deleting patient " + patientId + " report " + report.getId());
						reportService.deleteReport(report);
					//	return ResponseEntity.noContent().build();
					}
					if (report.getId().contentEquals("deleteAll")) {
						log.info("DeletingAll patient " + patientId + " report " + report.getId());
						reportService.deleteReport(r);
						//return ResponseEntity.noContent().build();
					}
				}
				log.info("Report " + report.getId() + " not registered");
				return ResponseEntity.notFound().build();
		}
	}
}
