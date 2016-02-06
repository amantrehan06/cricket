<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>User Registration</title>
</head>
<body class="trophy5">
<%@include file="includes/header.jsp"%>
	<!-- <p align="center" class="lead">New User Registration</p> -->
	<br/>
	<!-- <div class="form-style"> -->
    <div id="loginModal" class="modal show" tabindex="-1" role="dialog" aria-hidden="true"> 
   <div class="modal-dialog"  style="margin-left:10%; margin-top:10px;"> 
  <div class="modal-content"> 
<div class="container">
  <div class="row">
  	<div class="col-md-6">
    <form:form method="POST" class="form col-md-12 center-block" action="/t20/register/new">
      <%--   <div class="row">
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
  		<br> --%>
  		
             <div class="modal-header">
          <!-- <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button> -->
          <h1 class="text-center">Register</h1>
         
     	 </div>
     	 <div class="modal-body">   
  		 	<div class="control-group">
              <label class="control-label" for="email_id">Email ID</label>
              <div class="controls">
                <input type="text" id="email_id" name="email_id" placeholder="No Offical Email Id - Strictly refused" class="form-control input-lg" required>
                <!-- <p class="help-block">Personal Email ID(No Offical Email IDS please - Strictly refused)</p> -->
              </div>
            </div>
            
            <div class="control-group">
              <label class="control-label" for="password">Password</label>
              <div class="controls">
                <input type="text" id="password" name="password" placeholder="Password" class="form-control input-lg" required>
                <p class="help-block"></p>
              </div>
            </div>
            
            <div class="control-group">
              <label class="control-label" for="emp_id">Unique Id</label>
              <div class="controls">
                <input type="text" id="emp_id" name="emp_id" placeholder="Uniqe ID" class="form-control input-lg" required>
                <!-- <p class="help-block"></p> -->
              </div>
            </div>
            
            <div class="control-group">
              <label class="control-label" for="firstName">First Name</label>
              <div class="controls">
                <input type="text" id="firstName" name="firstName" placeholder="First Name" class="form-control input-lg" required>
                <p class="help-block"></p>
              </div>
            </div>
            
            <div class="control-group">
              <label class="control-label" for="lastName">Last Name</label>
              <div class="controls">
                <input type="text" id="lastName" name="lastName" placeholder="Last Name" class="form-control input-lg" required>
                <p class="help-block"></p>
              </div>
            </div>         
         </div>
           <span><a href="/t20/">Back</a></span> 
           <div class="form-group">
        <input type="submit" class="btn btn-primary btn-lg btn-block" value="Register"/>
        </div>      
             
    </form:form>
</div>
</div>
</div>

</div>
</div>
</div>
<!-- <div class="col-lg-5 col-md-push-1">
            <div class="col-md-12">
                <div class="alert alert-success">
                    <strong><span class="glyphicon glyphicon-ok"></span> Success! Message sent.</strong>
                </div>
                <div class="alert alert-danger">
                    <span class="glyphicon glyphicon-remove"></span><strong> Error! Please check all page inputs.</strong>
                </div>
            </div>
        </div> -->

<%@include file="includes/footer.jsp"%>
</body>
</html>