<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri= "http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri= "http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri= "http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="/t20/css/custom.css"></link>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>

<style type="text/css">
.form-style {
	margin: 20px;20px;20px;20px;
}
</style>
<div>
<script type="text/javascript">
var myVar=setInterval(function () {dateTimer()}, 1000);
var counter = 0;
function dateTimer() {
    var now = new Date();
    now = new Date();
    year = "" + now.getFullYear();
    month = "" + (now.getMonth() + 1); if (month.length == 1) { month = "0" + month; }
    day = "" + now.getDate(); if (day.length == 1) { day = "0" + day; }
    hour = "" + now.getHours(); if (hour.length == 1) { hour = "0" + hour; }
    minute = "" + now.getMinutes(); if (minute.length == 1) { minute = "0" + minute; }
    second = "" + now.getSeconds(); if (second.length == 1) { second = "0" + second; }
    if (document.getElementById != null){
    document.getElementById("currdate").innerHTML = "Current Time :"+" "+ year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
    }
}
</script>

</div>