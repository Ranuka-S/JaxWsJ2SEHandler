package com.ranuka.service;

import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
@HandlerChain(file="handler-chain.xml")
public class UserValidate {
	@WebMethod
	public String ValidateUser(String id) {
		return "valid user";
	}
}
