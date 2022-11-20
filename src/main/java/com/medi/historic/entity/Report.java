package com.medi.historic.entity;

import java.sql.Timestamp;
import java.time.LocalDate;
//import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.medi.historic.diabetestatus.DiabeteStatusEnum;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document(collection = "reports")
public class Report implements Cloneable{

	//@Indexed(unique = true)
	private String id;//string of user Id for instance
	//@Transient
	private Integer patientId;
	//private String family;
	//private String given;
	//private LocalDate dob;
	//S@Field("datetime")
	private Date date;
	private DiabeteStatusEnum diabeteStatus;
	private String comment;
	

	public Report(String string) {		
		this.comment = "Not_Registered";
	}
	
	public void setDate( Date date) {
		this.date = (Date)date.clone();
	}
	public Date getDate() {
		if(this.date != null) {
		return (Date) this.date.clone();
		}
		return null;
	}
	
	@Override
	public String toString() {
		return String.format("Report[id=%s, patientId='%s', issueDate='%s', diabeteStatus='%s', comment='%s']",
		        id, patientId,/* family, given,*/ date, diabeteStatus, comment);
	}
	
	public Object clone() {
		Report copy = null;
		try {
			copy = (Report)super.clone();
		}catch(CloneNotSupportedException c) {
			c.printStackTrace();
		}
		if(this.date != null) {
			copy.date = (Date)this.date.clone();
		}
		return copy;
	}
	
}
