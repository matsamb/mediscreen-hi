package com.medi.historic.entity;

import java.util.Date;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document(collection = "reports")
public class Report implements Cloneable{


	private String id;
	private Integer patientId;
	private Date date;
	private String comment;
	

	public Report(String string) {		
		this.comment = "Not_Registered";
	}
	
	public void setDate(Date date) {
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
		return String.format("Report[id=%s, patientId='%s', issueDate='%s', comment='%s']",
		        id, patientId, date, comment);
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
