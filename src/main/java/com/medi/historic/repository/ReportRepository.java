package com.medi.historic.repository;

import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.medi.historic.entity.Report;

@Repository
public interface ReportRepository extends MongoRepository<Report, ObjectId>{

	@Query(value= "{}",fields= "{_id:1,patientId:1,date:1,diabeteStatus:1,comment:1}", sort = "{date:-1}")
	public List<Report> findByPatientId(Integer Id);

}
