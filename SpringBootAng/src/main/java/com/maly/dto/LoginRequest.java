package com.maly.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginRequest {
	
	@NotBlank
	private String userId;
	@NotBlank
	private String password;

}