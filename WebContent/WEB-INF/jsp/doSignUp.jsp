<%@page import="org.json.simple.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=windows-1256"
	pageEncoding="windows-1256"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page
	import="java.net.URI , 
javax.ws.rs.client.Client , 
javax.ws.rs.client.ClientBuilder , 
javax.ws.rs.client.WebTarget , 
javax.ws.rs.core.MediaType , 
javax.ws.rs.core.Response ,
javax.ws.rs.core.UriBuilder , 
org.glassfish.jersey.client.ClientConfig ,
com.models.*,com.services.*, org.json.simple.parser.*"%>
<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1256">
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1256">
<title>Actual Sign Up</title>

</head>
<%
	UserModel u = new UserModel();
u.setEmail(request.getParameter("email"));
u.setName(request.getParameter("uname"));
u.setPass((request.getParameter("pass")));
	ClientConfig cf = new ClientConfig();
	Client client = ClientBuilder.newClient(cf);
	WebTarget target = client.target(UriBuilder.fromUri(
			"http://localhost:8080/FCISquare").build());
	JSONParser parser = new JSONParser();
	Object obj = parser.parse(target.path("rest").path("Services").path("signup")
			.path(u.getName())
			.path(u.getEmail()).path(u.getPass()).request()

			.accept(MediaType.TEXT_PLAIN).get(String.class)

			.toString());
	JSONObject j = (JSONObject) obj;
	
	
%>
<body>

</body>
</html>