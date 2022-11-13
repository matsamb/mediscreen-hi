package com.medi.historic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medi.historic.entity.Report;
import com.medi.historic.repository.ReportRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ReportService {

	@Autowired
	private ReportRepository reportRepository;
	
	ReportService(ReportRepository reportRepository){
		this.reportRepository = reportRepository;
	}

	public List<Report> findReportByFamilyAndGiven(String family, String given) {
		log.info("findReportByFamilyAndGiven");
		if(reportRepository.findByFamilyAndGiven(family, given).size()>0) {
		return reportRepository.findByFamilyAndGiven(family, given);
		}else {
			return List.of(new Report("Not_Registered")); 
		}
	}

	public String saveReport(Report newreport) {
		log.info("saveReport");
		reportRepository.save(newreport);
		log.info(reportRepository.findByFamilyAndGiven(newreport.getFamily(), newreport.getGiven()).size());
		for(Report r : reportRepository.findByFamilyAndGiven(newreport.getFamily(), newreport.getGiven())){
			if(r.getDate() == newreport.getDate()) {
				return r.getId();
			}
		}
		return "Not_Registered";
	}

	public void deleteReport(Report report) {
		log.info("deleteReport");
		reportRepository.delete(report);
	}

	
	
}
