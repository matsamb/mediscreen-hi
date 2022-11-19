package com.medi.historic;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.medi.historic.entity.Report;
import com.medi.historic.repository.ReportRepository;
import com.medi.historic.service.ReportService;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {

	@Mock
	private ReportRepository reportRepository;
	
	@InjectMocks
	private ReportService reportService;
	
	@Test
	public void test() {
		
		Report report  = new Report("yeyo");
		//report.setDate(java.sql.Date.(LocalDate.now()));
		report.setPatientId(1);	
		report.setId("1");
		
		List<Report> reportList = List.of(report);
		
		when(reportRepository.findAll()).thenReturn(reportList);
		
		List<Report> foundPatient = reportService.findReportByPatientId(1);
		
		assertThat(foundPatient.get(0).getComment()).isEqualTo("Not_Registered");
		
	}
	
}
