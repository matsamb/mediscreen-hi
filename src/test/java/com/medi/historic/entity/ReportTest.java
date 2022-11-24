package com.medi.historic.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Date;

import org.junit.jupiter.api.Test;

public class ReportTest {

	@Test
	public void givenAReportClone_whenCloneModified_thenItShouldBeDifferentFromReport() {

		Report report = new Report("ayo");
		report.setComment("ayo");
		report.setDate(Date.valueOf("1685-11-11"));
		report.setId("2");
		report.setPatientId(2);

		Report reportClone = (Report) report.clone();

		reportClone.setDate(Date.valueOf("1995-11-10"));

		assertThat(reportClone).isNotEqualTo(report);

	}

	@Test
	public void givenAReportDTOClone_whenCloneModified_thenItShouldBeDifferentFromReportDTO() {

		ReportDTO reportDTO = new ReportDTO("ayo");
		reportDTO.setComment("ayo");
		reportDTO.setDate(Date.valueOf("1685-11-11"));
		reportDTO.setId("2");
		reportDTO.setPatientId(2);

		ReportDTO reportClone = (ReportDTO) reportDTO.clone();

		reportClone.setDate(Date.valueOf("1995-11-10"));

		assertThat(reportClone).isNotEqualTo(reportDTO);

	}

}
