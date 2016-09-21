package com.ranuka.service.util;

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.soap.SOAPFaultException;

public class WebServiceUtil {

	public static void createSoapErrMsg(SOAPMessage msg, String cause) {
		try {
			SOAPBody soapBody = msg.getSOAPPart().getEnvelope().getBody();
			SOAPFault soapfault = soapBody.addFault();
			soapfault.setFaultString(cause);
			throw new SOAPFaultException(soapfault);
	
		} catch (SOAPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
