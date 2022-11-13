package com.medi.historic.restcontroller;

import java.net.URI;
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
	
	ReportRestcontroller(ReportService reportService){
		this.reportService = reportService;
	}
	
	// TODO dev without security
	@GetMapping("/report")
	public ResponseEntity<List<Report>> findReport(@RequestParam String family, @RequestParam String given
	//, @RequestHeader String authentication 
			) {
	
		 // if(authentication.contentEquals("Not_Authenticated")) {
		 // log.info(given+""+family+" not authenticated"); return
		 // ResponseEntity.badRequest().build(); }else {
		 
		if (reportService.findReportByFamilyAndGiven(family, given).get(0).getFamily() == "Not_Registered") {
			log.info(given + "" + family + " not registered");
			return ResponseEntity.notFound().build();
		} else {
			log.info(given + "" + family + " Found");
			return ResponseEntity.ok(reportService.findReportByFamilyAndGiven(family, given));
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
				Date issueNow= new Date();
				issueNow.setTime(System.currentTimeMillis());
				newreport.setDate(issueNow);

				URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/report")
						.buildAndExpand("?family=" + newreport.getFamily() + "&given=" + newreport.getGiven())
						.toUri();
				log.info("Loading new report " + newreport.getGiven() + " " + newreport.getFamily() + " "
						+ newreport.getId());
				reportService.saveReport(newreport);
				return ResponseEntity.created(location).body(newreport);
			}
		}
		// }
	}

	@PutMapping("/report")
	public ResponseEntity<Report> updateReport(@RequestBody Optional<Report> report
	// , @RequestHeader String authentication 
			) {
		
		//  if(authentication.contentEquals("Not_Authenticated")) {
		//  log.info(given+""+family+" not authenticated"); return
		//  ResponseEntity.badRequest().build(); }else {
		 

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
			if (reportService.findReportByFamilyAndGiven(newReport.getFamily(), newReport.getGiven()).get(0).getFamily() == "Not_Registered") {
				log.info(newReport.getGiven()+" "+ newReport.getFamily()+" not registered");
				return ResponseEntity.notFound().build();
			} else {
				log.info("Report "+newReport.getGiven()+" "+ newReport.getFamily()+" details updated");
				reportService.saveReport(newReport);
				return ResponseEntity.ok(newReport);
			}
			// }
		}
		// }
	}
	
	@DeleteMapping("/report")
	public ResponseEntity<Report> deleteReport(@RequestBody Report report,@RequestParam String family, @RequestParam String given
	// , @RequestHeader String authentication 
			) {
		//
		// if(authentication.contentEquals("Not_Authenticated")) {
		// log.info(given+""+family+" not authenticated"); return
		// ResponseEntity.badRequest().build(); }else {
		
		if (reportService.findReportByFamilyAndGiven(family, given).get(0).getFamily() == "Not_Registered") {
			log.info(given + "" + family + " not registered");
			return ResponseEntity.notFound().build();
		} else {	
			for(Report r : reportService.findReportByFamilyAndGiven(family, given)) {
				if(r.getId().contentEquals(report.getId())&&r.getDate() == report.getDate()) {
					log.info(given + "" + family + " Found");
						reportService.deleteReport(report);
						return ResponseEntity.noContent().build();
				}			
			}
			log.info(given + "" + family + " not registered");
			return ResponseEntity.notFound().build();
		}
		// }
	}
		

	
}
