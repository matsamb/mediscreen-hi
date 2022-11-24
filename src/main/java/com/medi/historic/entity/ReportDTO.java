package com.medi.historic.entity;

import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReportDTO implements Cloneable{

	private String id;
	private Integer patientId;
	private Date date;
	private String comment;
	

	public ReportDTO(String string) {		
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
		return String.format("Report[id=%s, patientId='%s', issueDate='%s', comment='%s']",
		        id, patientId, date, comment);
	}
	
	public Object clone() {
		ReportDTO copy = null;
		try {
			copy = (ReportDTO)super.clone();
		}catch(CloneNotSupportedException c) {
			c.printStackTrace();
		}
		if(this.date != null) {
			copy.date = (Date)this.date.clone();
		}
		return copy;
	}
	
}
