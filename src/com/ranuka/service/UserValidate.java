package com.ranuka.service;

import javax.annotation.Resource;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;

@WebService
@HandlerChain(file="handler-chain.xml")
public class UserValidate {
	@Resource
	WebServiceContext wsctx;
	
	@WebMethod
	public String ValidateUser(String id) {
		String username = (String)wsctx.getMessageContext().get("USERNAME");
		String password = (String)wsctx.getMessageContext().get("PASSWORD");
		
		return "hello " + username +" your password "+ password;
	}
}
