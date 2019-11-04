package com.maly.dto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(name="USER_LOGIN")
@ToString
public class User {
	@Id
	private long userId;
	private String username;
	private String userCountry;
	private String role;
	private String password;

}
