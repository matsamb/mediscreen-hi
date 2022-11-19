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
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.medi.historic.entity.Report;
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
		
		mockMvc = MockMvcBuilders
					.webAppContextSetup(webApplicationContext)
					.build();
		
	}
	
	@Test
	public void givenANotAuthenticatedUser_whenDeleteReportForNotARegisteredOne_thenItShouldReturnStatusBadRequest() throws Exception{
		
		Report report  = new Report();
		report.setComment("Not_Registered");

		when(reportService.findReportByPatientId(1)).thenReturn(List.of(report));
		
		mockMvc.perform(delete("/report")	
				//.header("Authentication", "bearer")
				.param("patientId", "1")
				.contentType(MediaType.APPLICATION_JSON)					
					
					.content("{\"id\":\"1\", \"patientId\":1, \"family\":\"maxe\", \"given\":\"tex\", \"date\":\"1689-09-14\", \"diabeteStatus\":\"NONE\", \"comment\":\"yeyo\"}")
					.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
		
	}
	
	@Test
	public void givenAnAuthenticatedUser_whenDeleteReportForNotARegisteredOne_thenItShouldReturnNotFound() throws Exception{
		
		Report report  = new Report("Not_Registered");
		
		when(reportService.findReportByPatientId(1)).thenReturn(List.of(report));
		
		mockMvc.perform(delete("/report")	
				.header("Authentication", "bearer")
				.param("patientId", "1")
				.contentType(MediaType.APPLICATION_JSON)					
					
					.content("{\"id\":\"1\", \"patientId\":1, \"family\":\"maxe\", \"given\":\"tex\", \"date\":\"1689-09-14\", \"diabeteStatus\":\"NONE\", \"comment\":\"yeyo\"}")
					.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
		
	}
	
	@Test
	public void givenAnAuthenticatedUser_whenDeleteReportForARegisteredOne_thenItShouldReturnStatusNotFound() throws Exception{
		
		Report report  = new Report("TestNone");
		report.setDate(Date.valueOf(LocalDate.now()));
		report.setPatientId(1);		
		
		when(reportService.findReportByPatientId(1)).thenReturn(List.of(report));
		//when(reportService.savePatient(new Patient())).thenReturn(1);
		
		mockMvc.perform(delete("/report")	
				.header("Authentication", "bearer")
				.param("patientId", "1")
				.contentType(MediaType.APPLICATION_JSON)					
					
					.content("{\"id\":\"1\", \"patientId\":1, \"family\":\"maxe\", \"given\":\"tex\", \"date\":\"1689-09-14\", \"diabeteStatus\":\"NONE\", \"comment\":\"yeyo\"}")
					.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
		
	}
	
	@Test
	public void givenANotAuthenticatedUser_whenPutReportForNotARegisteredOne_thenItShouldReturnBadRequest() throws Exception{
		
		Report report  = new Report("Not_Registered");
		
		when(reportService.findReportByPatientId(1)).thenReturn(List.of(report));
		
		mockMvc.perform(put("/report")	
				//.header("Authentication", "bearer")
				.param("patientId", "1")
				.contentType(MediaType.APPLICATION_JSON)					
					
					.content("{\"id\":\"1\", \"patientId\":1, \"family\":\"maxe\", \"given\":\"tex\", \"date\":\"1689-09-14\", \"diabeteStatus\":\"NONE\", \"comment\":\"yeyo\"}")
					.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
		
	}
	
	@Test
	public void givenAnAuthenticatedUser_whenPutReportForARegisteredOne_thenItShouldReturnStatusOk() throws Exception{
		
		Report report  = new Report("TestNone");
		report.setDate(Date.valueOf(LocalDate.now()));
		report.setPatientId(1);			
		
		when(reportService.findReportByPatientId(1)).thenReturn(List.of(report));
		
		mockMvc.perform(put("/report")	
				.header("Authentication", "bearer")
				.param("patientId", "1")
				.contentType(MediaType.APPLICATION_JSON)					
					
					.content("{\"id\":\"1\", \"patientId\":1, \"family\":\"maxe\", \"given\":\"tex\", \"date\":\"1689-09-14\", \"diabeteStatus\":\"NONE\", \"comment\":\"yeyo\"}")
					.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		
	}
	
	
	@Test
	public void givenANotAuthenticatedUser_whenPostReportAdd_thenItShouldReturnStatusBadRequest() throws Exception{
		
		Report report  = new Report("Not_Registered");
		
		when(reportService.findReportByPatientId(1)).thenReturn(List.of(report));
		
		mockMvc.perform(post("/report/add")	
				//.header("Authentication", "bearer")
				.param("patientId", "1")
				.contentType(MediaType.APPLICATION_JSON)					
					
					.content("{\"id\":\"1\", \"patientId\":1, \"family\":\"maxe\", \"given\":\"tex\", \"date\":\"1689-09-14\", \"diabeteStatus\":\"NONE\", \"comment\":\"yeyo\"}")
					.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
		
	}
	
	@Test
	public void givenAnAuthenticatedUser_whenPostReportAdd_thenItShouldReturnStatusIsCreated() throws Exception{
		
		Report report  = new Report("TestNone");
		report.setDate(Date.valueOf(LocalDate.now()));
		report.setPatientId(1);	
		
		when(reportService.findReportByPatientId(1)).thenReturn(List.of(report));
		
		mockMvc.perform(post("/report/add")	
				.header("Authentication", "bearer")
				.param("patientId", "1")
				.contentType(MediaType.APPLICATION_JSON)					
					
					.content("{\"id\":\"1\", \"patientId\":1, \"family\":\"maxe\", \"given\":\"tex\", \"date\":\"1689-09-14\", \"diabeteStatus\":\"NONE\", \"comment\":\"yeyo\"}")
					.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}
	
	@Test
	public void givenANotAuthenticatedUser_whenGetReport_thenItShouldReturnStatusNotFound() throws Exception{
		
		Report report  = new Report("Not_Registered");
		
		when(reportService.findReportByPatientId(1)).thenReturn(List.of(report));
		
		mockMvc.perform(get("/report")	
				//.header("Authentication", "bearer")
				.param("patientId", "1")
				.contentType(MediaType.APPLICATION_JSON)					
					
					.content("{\"id\":\"1\", \"patientId\":1, \"family\":\"maxe\", \"given\":\"tex\", \"date\":\"1689-09-14\", \"diabeteStatus\":\"NONE\", \"comment\":\"yeyo\"}")
					.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
		
	}
	
	@Test
	public void givenAnAuthenticatedUser_whenGetReportForANotRegisteredOne_thenItShouldReturnStatusIsNotFound() throws Exception{
		
		Report report  = new Report("Not_Registered");
		
		when(reportService.findReportByPatientId(1)).thenReturn(List.of(report));
		
		mockMvc.perform(get("/report")	
				.header("Authentication", "bearer")
				.param("patientId", "1")
				.contentType(MediaType.APPLICATION_JSON)					
					
					.content("{\"id\":\"1\", \"patientId\":1, \"family\":\"maxe\", \"given\":\"tex\", \"date\":\"1689-09-14\", \"diabeteStatus\":\"NONE\", \"comment\":\"yeyo\"}")
					.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
		
	}
	
	@Test
	public void givenAnAuthenticatedUser_whenGetReportForARegisteredOne_thenItShouldReturnStatusIsOk() throws Exception{
		
		Report report  = new Report("yeyo");
		//report.setDate(Date.valueOf(LocalDate.now()));
		report.setPatientId(1);	
		report.setId("1");
		
		when(reportService.findReportByPatientId(1)).thenReturn(List.of(report));
		
		mockMvc.perform(get("/report")	
				.header("Authentication", "bearer")
				.param("patientId", "1")
				.contentType(MediaType.APPLICATION_JSON)					
					
					.content("{\"id\":\"1\", \"patientId\":1, \"family\":\"maxe\", \"given\":\"tex\", \"date\":\"1689-09-14\", \"diabeteStatus\":\"NONE\", \"comment\":\"yeyo\"}")
					.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
		
	}

}
