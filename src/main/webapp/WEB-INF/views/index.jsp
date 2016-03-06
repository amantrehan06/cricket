<!DOCTYPE html>
<html lang="en" >
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri= "http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri= "http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri= "http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%ServletContext sc = request.getServletContext();
String message="";
if (sc.getAttribute("message")!= null){message=sc.getAttribute("message").toString();}%>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>World Cup T20</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap-theme.min.css">
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
	<link rel="stylesheet" href="/t20/css/custom.css"></link>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>	
 	<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
 	<script src="http://code.jquery.com/ui/1.11.4/jquery-ui.js"></script> 
    
    <link href="css/signin.css" rel="stylesheet">
  </head>
  <body class="trophy5">
  <%--   <h1 align="center">World Cup T20 ${message} </h1>
    <div class="container">
      <form  method="POST" action="/t20/home" class="form-signin" method = "post">
        <h2 class="form-signin-heading">Login</h2>
        <label for="userEmail" class="sr-only">Email address</label>
        <input type="email" name="userEmail" class="form-control" value="" placeholder="someone@example.com"></input>
        <label class="sr-only">Password</label>
        <input type="password" name="userPassword" class="form-control" value="" placeholder="Password"></input>
        <a href="/t20/register">New User Registration</a>
        <br>
        <p style="color: red"><%=message%></p>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
      </form>
    </div> --%>
    
    <div id="loginModal" class="modal show" tabindex="-1" role="dialog" aria-hidden="true">
  <div class="modal-dialog" style="margin-left:10%; margin-top:50px;">
  <div class="modal-content">
      <div class="modal-header">
          <!-- <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button> -->
          <h1 class="text-center">Login</h1>
      </div>
      <div class="modal-body">   
          <form class="form col-md-12 center-block" method="POST" action="/t20/home" method = "post">
            <div class="form-group">
              <input type="text" name="userEmail" class="form-control input-lg" placeholder="Email" required>
            </div>
            <div class="form-group">
              <input type="password"  name="userPassword" class="form-control input-lg" placeholder="Password" required>
            </div>
            <div class="form-group">
              <button class="btn btn-primary btn-lg btn-block">Sign In</button>
              <span class="pull-right"><a href="/t20/register">New User Registration</a></span>
               <span><a href="/t20/contact">Need help?</a></span> 
               <span><br><a href="/t20/notice">Terms and Condition</a></span> 
              <p style="color: red"><%=message%></p>
            </div>
            
          </form>
          
      </div>     
      <div class="modal-footer">
          <div class="col-md-12">
          <!-- <button class="btn" data-dismiss="modal" aria-hidden="true">Cancel</button> -->
		  </div>		   	
      </div>
     
  </div>
  
  </div>
 
   <div class="col-lg-5 col-md-push-1">
            <div class="col-md-12">
           <c:if test="${not empty success}">
           <div class="alert alert-success">
                    <strong><span class="glyphicon glyphicon-ok"></span> ${success} </strong>
                </div>
           </c:if>
              <c:if test="${not empty failure}">   
                <div class="alert alert-danger">
                    <span class="glyphicon glyphicon-remove"></span><strong> ${failure}</strong>
                </div>
                </c:if>
            </div>
        </div>
</div>

  </body>
</html>