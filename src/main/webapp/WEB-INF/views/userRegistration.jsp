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
  <div class="modal-dialog" style="margin-left:10%; margin-top:50px;">
  <div class="modal-content">
      <div class="modal-header">
          <!-- <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button> -->
          <h1 class="text-center">Register</h1>
      </div>
      <div class="modal-body">   
          <form class="form col-md-12 center-block" method="POST" action="/t20/register/new" method = "post">
            <div class="form-group">
              <input type="text" name="email_id" class="form-control input-lg" placeholder="Email" required>
            </div>
            <div class="form-group">
              <input type="text" name="password" class="form-control input-lg" placeholder="Password" required>
            </div>
            <div class="form-group">
              <input type="text" name="emp_id" class="form-control input-lg" placeholder="Unique Id" required>
            </div>
            <div class="form-group">
              <input type="text" name="firstName" class="form-control input-lg" placeholder="First Name" required>
            </div>
            <div class="form-group">
              <input type="password"  name="firstName" class="form-control input-lg" placeholder="Last Name" required>
            </div>
            <div class="form-group">
              <button class="btn btn-primary btn-lg btn-block" value="Register">Register</button>
              <span><a href="/t20/">Back</a></span>
               <span class="pull-right"><a href="/t20/contact">Need help?</a></span>               
            </div>
                 
            
          </form>
          
      </div>     
      <div class="modal-footer">
          <div class="col-md-12">
          <!-- <button class="btn" data-dismiss="modal" aria-hidden="true">Cancel</button> -->
		  </div>		   	
      </div>
   
   
   
   <%--  <div id="loginModal" class="modal show" tabindex="-1" role="dialog" aria-hidden="true"> 
   <div class="modal-dialog"  style="margin-left:10%; margin-top:10px;"> 
  <div class="modal-content"> 
<div class="container">
  <div class="row">
  	<div class="col-md-6">
  	 <div class="modal-body">  	 
    <form:form method="POST" class="form col-md-12 center-block" action="/t20/register/new">
    
             <div class="modal-header">
          <!-- <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button> -->
          <h1 class="text-center">Register</h1>
         
     	 </div>
     	  
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
</div> --%>

<%@include file="includes/footer.jsp"%>
</body>
</html>