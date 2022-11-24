package com.medi.historic.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medi.historic.entity.Report;
import com.medi.historic.entity.ReportDTO;
import com.medi.historic.repository.ReportRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ReportService {

	@Autowired
	private ReportRepository reportRepository;

	ReportService(ReportRepository reportRepository) {
		this.reportRepository = reportRepository;
	}

	public List<ReportDTO> findReportByPatientId(Integer patientId) {
		log.info("findReportByPatientId");
		List<ReportDTO> idReportList = new ArrayList<>();
		ReportDTO copyDTO = new ReportDTO();
		if (reportRepository.findByPatientId(patientId).size() > 0) {
			for (Report r : reportRepository.findByPatientId(patientId)) {
				log.debug(r + "r Id - patientId" + patientId);
				if (r.getPatientId().equals(patientId)) {
					log.debug("if for equality");
					copyDTO.setComment(r.getComment());
					copyDTO.setDate(r.getDate());
					copyDTO.setId(r.getId());
					copyDTO.setPatientId(r.getPatientId());
					idReportList.add(copyDTO);
				}
			}
			return idReportList;
		} else {
			idReportList.add(new ReportDTO("Not_Registered"));
			return idReportList;
		}
	}

	public void saveReport(Report newreport) {
		log.info("saveReport");
		reportRepository.save(newreport);
	}

	public void deleteReport(ReportDTO reportDTO) {
		log.info("deleteReport");
		Report report = new Report();
		report.setComment(reportDTO.getComment());
		report.setDate(reportDTO.getDate());
		report.setId(reportDTO.getId());
		report.setPatientId(reportDTO.getPatientId());
		reportRepository.delete(report);
	}

	public void deleteAll(Integer patientId) {
		log.info("deleteAllReport");
		List<Report> list = reportRepository.findByPatientId(patientId);
		reportRepository.deleteAll(list);
	}

}
