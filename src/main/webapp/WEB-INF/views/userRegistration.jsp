<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>IPL 2015 User Registration</title>
</head>
<body>
<%@include file="includes/header.jsp"%>
	<p align="center" class="lead">New User Registration</p>
	<br/>
	<div class="form-style">
    <form:form method="POST" action="/iplT20/register/new">
        <div class="row">
  			<div class="col-lg-3">
  				<form:label path="email_id">Personal Email ID(No Offical Email IDS please - Strictly refused)</form:label>
            	<form:input type="text" class="form-control" path="email_id" id="email"></form:input>
  			</div>
  			<div class="col-lg-3">
  				<form:label path="password">Password</form:label>
            	<form:input type="password" class="form-control" path="password" id="password"></form:input>
  			</div>
  			<div class="col-lg-3">
  				<form:label path="emp_id">Uniqe ID (can be DOB ex: 2nd July 1988 can be mentioned as 271988 )</form:label>
            	<form:input type="text" class="form-control" path="emp_id" id="emp_id"></form:input>
  			</div>
  			<div class="col-lg-3">
  				<form:label path="firstName">First Name</form:label>
            	<form:input type="text" class="form-control" path="firstName" id="firstName"></form:input>
  			</div>
  			<div class="col-lg-3">
  				<form:label path="lastName">Last Name</form:label>
            	<form:input type="text" class="form-control" path="lastName" id="lastName"></form:input>
  			</div>
  		</div>
  		<br>
        <input type="submit" class="btn btn-primary" value="Register"/>
    </form:form>
</div>
<%@include file="includes/footer.jsp"%>
</body>
</html>