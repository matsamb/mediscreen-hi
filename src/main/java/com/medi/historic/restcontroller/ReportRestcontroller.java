package com.medi.historic.restcontroller;

import java.net.URI;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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

	// TODO dev without security
	@GetMapping("/report")
	public ResponseEntity<List<Report>> findReport(@RequestParam Integer patientId
	// , @RequestHeader String authentication
	) {

		// if(authentication.contentEquals("Not_Authenticated")) {
		// log.info(given+""+family+" not authenticated"); return
		// ResponseEntity.badRequest().build(); }else {

		if (reportService.findReportByPatientId(patientId).get(0).getComment() == "Not_Registered") {
			log.info("No registered report for patient number" + patientId);
			return ResponseEntity.notFound().build();
		} else {
			log.info("Report found for patient number " + patientId);
			return ResponseEntity.ok(reportService.findReportByPatientId(patientId));
		}
		// }
	}

	@PostMapping("/report/add")
	public ResponseEntity<Report> createPatient(@RequestBody Optional<Report> report
	// , @RequestHeader String authentication
	) {

		// if(authentication.contentEquals("Not_Authenticated")) {
		// log.info(given+""+family+" not authenticated"); return
		// ResponseEntity.badRequest().build(); }else {

		if (report.isEmpty()) {
			log.info("No request body");
			return ResponseEntity.badRequest().build();
		} else {
			log.info("Creating new patient");
			Report newreport = report.get();

			// Validator validator =
			// Validation.buildDefaultValidatorFactory().getValidator();
			// Set<ConstraintViolation<Patient>> violations =
			// validator.validate(newPatient);

			if (/* violations.size()>0 */report.isEmpty()) {
				log.info("Bad request, constraint violations: "/* +violations */);
				return ResponseEntity.badRequest().build();
			} else {

				String savedReportId = UUID.randomUUID().toString();
				newreport.setId(savedReportId);
				Date issueNow = new Date();
				LocalDate dob = LocalDate.of(2009, 9, 19);
				issueNow.setTime(System.currentTimeMillis());
				newreport.setDate(issueNow);

				URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/report")
						.buildAndExpand("?patientId=" + newreport.getPatientId()).toUri();
				log.info("Loading new report " + newreport.getId() + " for patient " + newreport.getPatientId());
				reportService.saveReport(newreport);
				return ResponseEntity.created(location).body(newreport);
			}
		}
		// }
	}

	@PutMapping("/report")
	public ResponseEntity<Report> updateReport(@RequestParam Integer patientId, @RequestBody Optional<Report> report
	// , @RequestHeader String authentication
	) {

		// if(authentication.contentEquals("Not_Authenticated")) {
		// log.info(given+""+family+" not authenticated"); return
		// ResponseEntity.badRequest().build(); }else {

		if (report.isEmpty()) {
			log.info("No request body");
			return ResponseEntity.badRequest().build();
		} else {
			log.info("Creating new report");
			Report newReport = report.get();

			// Validator validator =
			// Validation.buildDefaultValidatorFactory().getValidator();
			// Set<ConstraintViolation<Patient>> violations =
			// validator.validate(newPatient);

			// if (/* violations.size()>0 */patient.isEmpty()) {
			// log.info("Bad request, constraint violations: "/* +violations */);
			// return ResponseEntity.badRequest().build();
			// } else {

			// TODO transfert ID initialisation to user interface
			if (reportService.findReportByPatientId(patientId).get(0).getComment() == "Not_Registered") {
				log.info("Report " + newReport.getId() + " not registered");
				return ResponseEntity.notFound().build();
			} else {
				log.info("Report " + newReport.getId() + ", for patient number " + newReport.getPatientId()
						+ ", updated");
				reportService.saveReport(newReport);
				return ResponseEntity.ok(newReport);
			}
			// }
		}
		// }
	}

	@DeleteMapping("/report")
	public ResponseEntity<Report> deleteReport(@RequestBody Report report, @RequestParam Integer patientId
	// , @RequestHeader String authentication
	) {
		//
		// if(authentication.contentEquals("Not_Authenticated")) {
		// log.info(given+""+family+" not authenticated"); return
		// ResponseEntity.badRequest().build(); }else {

		if (report.getComment().contentEquals("deletePatient") && report.getId().contentEquals("deleteAll")) {
			log.info("Deleting all Patient " + patientId + " report");
			if (reportService.findReportByPatientId(patientId).get(0).getComment() == "Not_Registered") {
				log.info("No report registered for patient " + patientId);
				return ResponseEntity.notFound().build();
			} else {
				log.info("All report deleted for patient " + patientId);				
				reportService.deleteAll(reportService.findReportByPatientId(patientId));
				return ResponseEntity.noContent().build();
			}
		} else {
			if (reportService.findReportByPatientId(patientId).get(0).getComment() == "Not_Registered") {
				log.info(" not registered");
				return ResponseEntity.notFound().build();
			} else {

				for (Report r : reportService.findReportByPatientId(patientId)) {
					if (r.getId().contentEquals(report.getId()) && r.getDate() == report.getDate()) {
						log.info("Deleting patient " + patientId + " report " + report.getId());
						reportService.deleteReport(report);
						return ResponseEntity.noContent().build();
					}
				}
				log.info("Report " + report.getId() + " not registered");
				return ResponseEntity.notFound().build();

			}
		}
		// }
	}

}
