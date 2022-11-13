package com.medi.historic.entity;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.data.annotation.Id;

public class Patient {

	private Integer patientId;
//	@Nonnull
	private String family;
//	@Nonnull
	private String given;
//	@Nonnull
	private LocalDate dob;
//	@Nonnull
	private String sex;
//	@Nonnull
	private String address;
//	@Nonnull
	private String phone;
	
	public Patient(String string) {
		this.family = string;
	}
	
	public Object clone() {
		Patient copy = null;
		try {
			copy = (Patient)super.clone();
		}catch(CloneNotSupportedException c){
			c.printStackTrace();
		}
		return copy;
	}
	
}
