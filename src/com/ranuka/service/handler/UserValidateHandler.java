package com.ranuka.service.handler;

import java.util.Iterator;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.Node;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.MessageContext.Scope;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import com.ranuka.service.util.WebServiceUtil;

public class UserValidateHandler implements SOAPHandler<SOAPMessageContext> {
	
	//WebServiceUtil util;

	private static final String WSSE_NS_URI = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
	private static final QName QNAME_WSSE_SECURITY = new QName(WSSE_NS_URI, "Security");
	private static final QName QNAME_WSSE_USERNAMETOKEN = new QName(WSSE_NS_URI, "UsernameToken");
	private static final QName QNAME_WSSE_USERNAME = new QName(WSSE_NS_URI, "Username");
	private static final QName QNAME_WSSE_PASSWORD = new QName(WSSE_NS_URI, "Password");

	@Override
	public boolean handleMessage(SOAPMessageContext context) {

		String wsseUsername = null;
		String wssePassword = null;

		// check for a outbound request
		boolean isOutBoundIndicator = (boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

		if (!isOutBoundIndicator) {
			try {
				SOAPHeader soapHeader = context.getMessage().getSOAPHeader();

				if (soapHeader != null) {

					Iterator<SOAPElement> securityElements = soapHeader.getChildElements(QNAME_WSSE_SECURITY);

					while (securityElements.hasNext()) {
						SOAPElement securityEliment = securityElements.next();

						if ((securityEliment.getElementName().getLocalName()).equals("Security")) {

							Iterator<?> it2 = securityEliment.getChildElements();
							while (it2.hasNext()) {

								Node soapNode = (Node) it2.next();

								if (soapNode instanceof SOAPElement) {

									SOAPElement element = (SOAPElement) soapNode;
									QName elementQname = element.getElementQName();

									if (QNAME_WSSE_USERNAMETOKEN.equals(elementQname)) {
										SOAPElement usernameTokenElement = element;
										wsseUsername = getFirstChildElementValue(usernameTokenElement,
												QNAME_WSSE_USERNAME);
										wssePassword = getFirstChildElementValue(usernameTokenElement,
												QNAME_WSSE_PASSWORD);
										System.out
												.println("username = " + wsseUsername + " password = " + wssePassword);

										break;
									}
								}

							}

						}
						
							context.put("USERNAME", wsseUsername);
							context.setScope("USERNAME", Scope.APPLICATION);
							
							context.put("PASSWORD", wssePassword);
							context.setScope("PASSWORD", Scope.APPLICATION);
						

					}
				}
				if (wsseUsername == null || wssePassword == null){
					
					WebServiceUtil.createSoapErrMsg(context.getMessage(), "UserName Or Password Empty");
				}
			} catch (SOAPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return true;
	}

	private String getFirstChildElementValue(SOAPElement soapElement, QName qNameToFind) {
		String value = null;
		Iterator<?> it = soapElement.getChildElements(qNameToFind);
		while (it.hasNext()) {
			SOAPElement element = (SOAPElement) it.next();
			value = element.getValue();
		}
		return value;
	}

	@Override
	public boolean handleFault(SOAPMessageContext context) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void close(MessageContext context) {
		// TODO Auto-generated method stub

	}

	@Override
	public Set<QName> getHeaders() {
		// TODO Auto-generated method stub
		return null;
	}

}
