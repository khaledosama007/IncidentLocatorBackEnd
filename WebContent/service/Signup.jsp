<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>
<title>
	FCI Square
</title>

</head>

<style>
	filedset {
		width:500px;
	}
	
	legend {
		font-size:20px;
	}
	
	label.field {
		text-align: left;
		float: left;
		width: 100px;
	}
	
	input.textfield {
		width: 300px;
		float: left;
	}
</style>

<body>
<center><h1>Welcome to FCI Square</h1>
		<h1> Signup now</h1></center>

<form action = "doSignUp" method = "post" >
	<fieldset>
		<legend>Sign Up</legend>
		<p><label class="field">User name:</label> <input class="textfield" type="text" name = "uname"/> <br> </p>
		<p><label class="field">Email:</label> <input class="textfield" type="text" name = "email"/> <br> </p>
		<p><label class="field">Password:</label> <input class="textfield" type="text" name = "pass"/> <br> </p>
		<input type="submit" value = "Signup"/>
	</fieldset>
</form>
</body>

</html>