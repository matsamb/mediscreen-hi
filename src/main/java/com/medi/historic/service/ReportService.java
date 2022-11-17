package com.medi.historic.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.stereotype.Service;

import com.medi.historic.entity.Report;
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

	public List<Report> findReportByPatientId(Integer patientId) {
		log.info("findReportByPatientId");
		List<Report> idReportList = new ArrayList<>();
		if (reportRepository.findByPatientId(patientId).size() > 0) {
			idReportList.add(new Report("Not_Registered"));
			for (Report r : reportRepository.findByPatientId(patientId)) {
				log.info(r+"r Id - patientId"+patientId);
				if (r.getPatientId().equals(patientId)) {
					log.info("if for equality");
					idReportList.add(r);
				}
			}
			if(idReportList.size() > 1) {
				idReportList.remove(0);
				return idReportList;
			}
			
			return idReportList;
		} else {
			idReportList.add(new Report("Not_Registered"));
			return idReportList;
		}
	}

	public String saveReport(Report newreport) {
		log.info("saveReport");
		reportRepository.save(newreport);
		log.info(reportRepository.findByPatientId(newreport.getPatientId()).size());
		for (Report r : reportRepository.findByPatientId(newreport.getPatientId())) {
			if (r.getDate() == newreport.getDate()) {
				return r.getId();
			}
		}
		return "Not_Registered";
	}

	public void deleteReport(Report report) {
		log.info("deleteReport");
		reportRepository.delete(report);
	}

	public void deleteAll(List<Report> reports) {
		log.info("deleteAllReport");
		reportRepository.deleteAll(reports);		
	}

}
