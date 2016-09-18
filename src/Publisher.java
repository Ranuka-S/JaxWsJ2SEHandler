import javax.xml.ws.Endpoint;

import com.ranuka.service.UserValidate;

public class Publisher {

	public static void main(String[] args) {
		Endpoint.publish("http://localhost:8080/ws/server", new UserValidate());

		System.out.println("Service is published!");
	}

}
