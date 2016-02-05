<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<c:url value="/resources/themes/css/jquery-ui-1.9.0.custom.css" />" rel="stylesheet" />
<link href="<c:url value="/resources/themes/css/style.css" />" rel="stylesheet">
<script src="<c:url value="/resources/themes/scripts/jquery-1.8.2.js" />"></script>
<script src="<c:url value="/resources/themes/scripts/jquery-ui-1.9.0.custom.min.js" />"></script>
<title>Add User Form</title>
</head>
<body>
	<script>
		$(function() {
			$("input[type=submit], a, button").button();
			
		    $( "#dateOfBirth" ).datepicker({
		        showOn: "button",
		        buttonImage: "<c:url value="/resources/css/images/calendar.gif" />",
		        buttonImageOnly: true
		    });
		});
	</script>
	<div class="mainContent">
		<h1>Add User Form</h1>
		
		
		<form:form id="addUserForm" modelAttribute="person" method="post" action="processAddUser">
			<table class="addUserTable">
				<tr class="formRow">
					<td><form:label path="firstName">First Name</form:label></td>
					<td><form:input class="textfield" path="firstName" /></td>
				</tr>
				<tr>
					<td><form:label path="lastName">Last Name</form:label></td>
					<td><form:input class="textfield" path="lastName" /></td>
				</tr>
				<tr>
					<td><form:label path="dateOfBirth">Date of Birth</form:label>
					</td>
					<td><form:input id="dateOfBirth" class="dobTextfield" path="dateOfBirth" /></td>
				</tr>
				<tr>
					<td><form:label path="salary">Salary</form:label></td>
					<td><form:input class="textfield" path="salary" /></td>
				</tr>
				<tr>
					<td><form:label path="phoneNumber">Phone</form:label></td>
					<td><form:input class="textfield" path="phoneNumber" /></td>
				</tr>
				<tr>
					<td><form:label path="state">State</form:label></td>
					<td><form:input class="stateTextfield" path="state" /></td>
				</tr>
				<tr>
					<td><form:label path="email">Email</form:label></td>
					<td><form:input class="textfield" path="email" /></td>
				</tr>
				<tr></tr><tr></tr><tr></tr>
				<tr>
					<td></td>
					<td><input type="submit" value="Add User" /></td>
				</tr>
			</table>
		</form:form>
		<br /><br />
		<a href="main">See User List</a>
	</div>
</body>
</html>

