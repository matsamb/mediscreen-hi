package com.medi.historic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.medi.historic.entity.Report;
import com.medi.historic.entity.ReportDTO;
import com.medi.historic.repository.ReportRepository;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {

	@Mock
	private ReportRepository reportRepository;
	
	@InjectMocks
	private ReportService reportService;
	
	@Test
	public void givenAPatientId_whenDeleteAll_thenDeleteRepositoryShouldBeUsedOnce() {
		reportService.deleteAll(1);
		verify(reportRepository, times(1)).deleteAll(anyList());	
	}
	
	@Test
	public void givenAReportDTO_whenDeleteReport_thenDeleteRepositoryShouldBeUsedOnce() {
		ReportDTO reportDTO = new ReportDTO();
		reportDTO.setComment("ayo");
		reportDTO.setDate(Date.valueOf("1685-11-11"));
		reportDTO.setId("2");
		reportDTO.setPatientId(2);
		
		Report report = new Report();
		report.setComment("ayo");
		report.setDate(Date.valueOf("1685-11-11"));
		report.setId("2");
		report.setPatientId(2);
		
		reportService.deleteReport(reportDTO);
		verify(reportRepository, times(1)).delete(report);
	}
	
	@Test
	public void givenANewReport_whenSaveReport_thenRepositorySaveMethodShouldBeUsedOnce() {
		
		Report report = new Report();
		report.setComment("ayo");
		report.setDate(Date.valueOf("1685-11-11"));
		report.setId("2");
		report.setPatientId(2);
			
		reportService.saveReport(report);
		
		verify(reportRepository, times(1)).save(report);
	}
	
	@Test
	public void givenAPatientIdWithRegisteredReport_whenFindReport_thenItShouldReturnAListOfReportDTO() {
		
		Report report = new Report();
		report.setComment("ayo");
		report.setDate(Date.valueOf("1685-11-11"));
		report.setId("2");
		report.setPatientId(2);
		
		ReportDTO reportDTO = new ReportDTO();
		reportDTO.setComment("ayo");
		reportDTO.setDate(Date.valueOf("1685-11-11"));
		reportDTO.setId("2");
		reportDTO.setPatientId(2);
		
		when(reportRepository.findByPatientId(2)).thenReturn(List.of(report));
		
		List<ReportDTO> retrunedList = reportService.findReportByPatientId(2);
		assertThat(retrunedList).isEqualTo(List.of(reportDTO));
		
	}
	
	@Test
	public void givenAPatientIdWithoutRegisteredReport_whenFindReport_thenItShouldReturnAListOfReportDTO() {
				
		when(reportRepository.findByPatientId(2)).thenReturn(List.of());
		
		List<ReportDTO> retrunedList = reportService.findReportByPatientId(2);
		
		assertThat(retrunedList).isEqualTo(List.of(new ReportDTO("Not_Registered")));
		
	}
	
}
