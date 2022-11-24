package com.medi.historic.restcontroller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.medi.historic.entity.ReportDTO;
import com.medi.historic.service.ReportService;

@SpringBootTest
@AutoConfigureMockMvc
public class ReportControllerIT {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ReportService reportService;

	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext) {

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

	}

	@Test
	public void givenNoRequestBody_whenDeleteReport_thenItShouldReturnStatusBadRequest() throws Exception {

		mockMvc.perform(delete("/report")
				// .header("Authentication", "bearer")
				.param("patientId", "1").contentType(MediaType.APPLICATION_JSON)
				// .content("{\"id\":\"1\", \"patientId\":1, \"date\":\"1689-09-14\",
				// \"comment\":\"yeyo\"}")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());

	}

	@Test
	public void givenAListOfNotRegisteredReport_whenDeleteReport_thenItShouldReturnNotFound() throws Exception {

		ReportDTO report = new ReportDTO("Not_Registered");
		report.setId("2");
		when(reportService.findReportByPatientId(1)).thenReturn(List.of(report));

		mockMvc.perform(delete("/report")
				// .header("Authentication", "bearer")
				.param("patientId", "1").contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":\"1\", \"patientId\":1, \"date\":\"1689-09-14\", \"comment\":\"yeyo\"}")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());

	}

	@Test
	public void givenAListOfRegisteredReport_whenDeleteReport_thenItShouldReturnNoContent() throws Exception {

		ReportDTO report = new ReportDTO("yeyo");
		report.setDate(Date.valueOf("1689-09-14"));
		report.setPatientId(1);
		report.setId("1");
		when(reportService.findReportByPatientId(1)).thenReturn(List.of(report));

		mockMvc.perform(delete("/report") // .header("Authentication", "bearer")
				.param("patientId", "1").contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":\"1\", \"patientId\":1, \"date\":\"1689-09-14\", \"comment\":\"yeyo\"}")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

	}
	
	@Test
	public void given2AListOfRegisteredReport_whenDeleteReport_thenItShouldReturnNoContent() throws Exception {

		ReportDTO report = new ReportDTO("yeyo");
		report.setDate(Date.valueOf("1689-09-14"));
		report.setPatientId(1);
		report.setId("1");
		when(reportService.findReportByPatientId(1)).thenReturn(List.of(report));

		mockMvc.perform(delete("/report") // .header("Authentication", "bearer")
				.param("patientId", "1").contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":\"deleteAll\", \"patientId\":1, \"date\":\"1689-09-14\", \"comment\":\"yeyo\"}")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

	}

	@Test
	public void givenAReport_whenDeleteReport_thenItShouldReturnStatusNotFound() throws Exception {

		ReportDTO report = new ReportDTO("yeyo");
		report.setDate(Date.valueOf("1689-09-14"));
		report.setPatientId(1);
		report.setId("55");

		when(reportService.findReportByPatientId(1)).thenReturn(List.of(report, report));
		// when(reportService.savePatient(new Patient())).thenReturn(1);

		mockMvc.perform(delete("/report")
				// .header("Authentication", "bearer")
				.param("patientId", "55").contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":\"1\", \"patientId\":1, \"date\":\"1689-09-14\", \"comment\":\"yeyo\"}")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());

	}

	@Test
	public void givenANotRegisteredReport_whenDeleteReport_thenItShouldReturnStatusNotFound() throws Exception {

		ReportDTO report = new ReportDTO("Not_Registered");
		report.setId("2");

		when(reportService.findReportByPatientId(1)).thenReturn(List.of(report));
		// when(reportService.savePatient(new Patient())).thenReturn(1);

		mockMvc.perform(delete("/report")
				// .header("Authentication", "bearer")
				.param("patientId", "1").contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":\"1\", \"patientId\":1, \"date\":\"1689-09-14\", \"comment\":\"yeyo\"}")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());

	}

	@Test
	public void givenNoRequestBody_whenPutReport_thenItShouldReturnBadRequest() throws Exception {

		ReportDTO report = new ReportDTO("Not_Registered");

		when(reportService.findReportByPatientId(1)).thenReturn(List.of(report));

		mockMvc.perform(put("/report")
				// .header("Authentication", "bearer")
				.param("patientId", "1").contentType(MediaType.APPLICATION_JSON)
				// .content("{\"id\":\"1\", \"patientId\":1, \"date\":\"1689-09-14\",
				// \"comment\":\"yeyo\"}")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());

	}

	@Test
	public void givenARequestBody_whenPutReport_thenItShouldReturnStatusOk() throws Exception {

		ReportDTO report = new ReportDTO("TestNone");
		report.setDate(Date.valueOf(LocalDate.now()));
		report.setPatientId(1);

		when(reportService.findReportByPatientId(1)).thenReturn(List.of(report));

		mockMvc.perform(put("/report")
				// .header("Authentication", "bearer")
				.param("patientId", "1").contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":\"1\", \"patientId\":1, \"date\":\"1689-09-14\", \"comment\":\"yeyo\"}")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

	}

	@Test
	public void givenNoRequestBody_whenPostReport_thenItShouldReturnStatusBadRequest() throws Exception {

		ReportDTO report = new ReportDTO("Not_Registered");

		when(reportService.findReportByPatientId(1)).thenReturn(List.of(report));

		mockMvc.perform(post("/report/add")
				// .header("Authentication", "bearer")
				.param("patientId", "1").contentType(MediaType.APPLICATION_JSON)
				// .content("{\"id\":\"1\", \"patientId\":1, \"date\":\"1689-09-14\",
				// \"comment\":\"yeyo\"}")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());

	}

	@Test
	public void givenARequestBody_whenPostReport_thenItShouldReturnStatusIsCreated() throws Exception {

		ReportDTO report = new ReportDTO("yeyo");
		report.setDate(Date.valueOf(LocalDate.now()));
		report.setPatientId(1);

		when(reportService.findReportByPatientId(1)).thenReturn(List.of(report));

		mockMvc.perform(post("/report/add")
				// .header("Authentication", "bearer")
				.param("patientId", "1").contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":\"1\", \"patientId\":1, \"date\":\"1689-09-14\", \"comment\":\"yeyo\"}")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
	}

	@Test
	public void givenANotRegisteredReport_whenGetReport_thenItShouldReturnStatusIsNotFound() throws Exception {

		ReportDTO report = new ReportDTO("Not_Registered");

		when(reportService.findReportByPatientId(1)).thenReturn(List.of(report));

		mockMvc.perform(get("/report")
				// .header("Authentication", "bearer")
				.param("patientId", "1").contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":\"1\", \"patientId\":1, \"date\":\"1689-09-14\", \"comment\":\"yeyo\"}")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());

	}

	@Test
	public void givenARegisteredReport_whenGetReport_thenItShouldReturnStatusIsOk() throws Exception {

		ReportDTO report = new ReportDTO("yeyo");
		// report.setDate(Date.valueOf(LocalDate.now()));
		report.setPatientId(1);
		report.setId("1");

		when(reportService.findReportByPatientId(1)).thenReturn(List.of(report));

		mockMvc.perform(get("/report")
				// .header("Authentication", "bearer")
				.param("patientId", "1").contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":\"1\", \"patientId\":1, \"date\":\"1689-09-14\", \"comment\":\"yeyo\"}")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

	}

}
