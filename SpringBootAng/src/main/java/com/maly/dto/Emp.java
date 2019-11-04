package com.maly.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Entity
@ToString
public class Emp {
	
	
	private String firstName;
	private String lastName;
	//private String dob;
	//private String doj;
	private String gender;
	//private String  designation;
	//private Double sal;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long eid;
	
}
