<!DOCTYPE html>
<html lang="en">
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
    <title>IPL 2015</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap-theme.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
    <link href="css/signin.css" rel="stylesheet">
  </head>
  <body>
    <h1 align="center">IPL 2015 ${message} </h1>
    <div class="container">
      <form  method="POST" action="/iplT20/home" class="form-signin" method = "post">
        <h2 class="form-signin-heading">Please sign in</h2>
        <label for="userEmail" class="sr-only">Ibtechnology Email address</label>
        <input type="email" name="userEmail" class="form-control" value="" placeholder="someone@example.com"></input>
        <label class="sr-only">Password</label>
        <input type="password" name="userPassword" class="form-control" value="" placeholder="Password"></input>
        <a href="/iplT20/register">New User Registration</a>
        <br>
        <p style="color: red"><%=message%></p>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
      </form>
    </div>
  </body>
</html>