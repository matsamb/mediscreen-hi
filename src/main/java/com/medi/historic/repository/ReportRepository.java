package com.medi.historic.repository;

import java.util.List;
import java.util.UUID;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.medi.historic.entity.Report;

@Repository
public interface ReportRepository extends MongoRepository<Report, ObjectId>{

	@Query(value= "{}",fields= "{_id:1,family:1,given:1,date:1,diabeteStatus:1,comment:1}", sort = "{date:-1}")
	public List<Report> findByFamilyAndGiven(String family, String given);
	
//	@Query(value= "{}",fields= "{family:1,given:1}", sort = "{date:-1}")
//	public List<Report> findByFamily();
	
//	@Aggregation("{'$project':{'id','$family'}}")
//	public List<Report> findAllFamily();
	
}
