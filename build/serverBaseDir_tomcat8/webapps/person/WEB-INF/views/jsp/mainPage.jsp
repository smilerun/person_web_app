<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<c:url value="/resources/themes/css/jquery-ui-1.9.0.custom.css" />" rel="stylesheet" />
<link href="<c:url value="/resources/themes/css/style.css" />" rel="stylesheet">
<script src="<c:url value="/resources/themes/scripts/jquery-1.8.2.js" />"></script>
<script src="<c:url value="/resources/themes/scripts/jquery-ui-1.9.0.custom.min.js" />"></script>
<title>Person Page</title>
</head>
<body>
<body>
	<script>
		$(function() {
			$("input[type=submit], a, button").button();
			
		});
	</script>
	<div class="mainContent">
		<h1>User Main Page</h1>
		<br />
		<a href="addUser">Add a New User</a>
		<br /><br />
		<c:if test="${!empty people}">
			<table class="userListTable" >
				<tr class="userTableHead" >
					<td class="width100LB">First Name</td>
					<td class="width100LB">Last Name</td>
					<td class="adjacentLB">Date of Birth</td>
					<td class="adjacentLB">Phone</td>
					<td class="adjacentLB">Salary</td>
					<td class="width50LB">State</td>
					<td class="adjacentLB">Email</td>
					<td class="adjacentLB"></td>
					<td class="adjacentLRB"></td>
				</tr>
				<c:forEach items="${people}" var="person">				
					<tr class="userInfoRow"> 
						<td class="width100LB"><c:out value="${person.firstName}" /></td>
						<td class="width100LB"><c:out value="${person.lastName}" /></td>
						<td class="adjacentLB"><fmt:formatDate pattern="MM/dd/yyyy" value="${person.dateOfBirth}" /></td>
						<td class="adjacentLB"><c:out value="${person.phoneNumber}" /></td>
						<td class="adjacentLB"><fmt:formatNumber value="${person.salary}" type="currency"/></td>
						<td class="width50LB"><c:out value="${person.state}" /></td>
						<td class="adjacentLB"><c:out value="${person.email}" /></td>
						<td class="adjacentLB"><a href="edit?personId=${person.personId}">Edit</a></td>
						<td class="adjacentLRB"><a href="delete?personId=${person.personId}">Delete</a></td>
					</tr>
				</c:forEach>
			</table>
		</c:if>		
	</div>
</body>
</html>



