<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>IPL 2015 League</title>
<script type="text/javascript">
function validateName() {
	var leagueName = document.getElementById("leagueName").value;
	if(leagueName == ""){
		alert("Please Enter League Name");
		return false;
	}else{
		return true;
	}
}
</script>
</head>
<body>
<%
					int count = 1;
				%>
	<%@include file="includes/header.jsp"%>
	<div align="right" style="margin: 2%">
		<%@include file="includes/navigation.jsp"%>
		<h4 align="center" style="color: red;">PLEASE DO READ THE LEAGUE <a href="/t20/league/rules">RULES</a> ON THE ADJACENT LINK</h4>
		<br/>
		<div align="left" style="float:left;width: 30%;">
		<h4 align="center">Join Available Leagues</h4>
			<table class="table table-bordered">
			<thead>
				<tr>
					<th>Sr. No.</th>
					<th>Leagues</th>
					<th>League Owner</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${leagueList}" var="row">
					<tr>
						<td><%=count%></td>
						<td><form action="/t20/league/members" method="post">
						<input type="hidden" name="id" value="${row.id}">
						<input type="submit" value="${row.league_name}" class ="btn btn-primary">
						</form> </td>
						<td>${row.user.firstName}&nbsp;${row.user.lastName}</td>
					</tr>
					<%
						count++;
					%>
				</c:forEach>
			</tbody>
		</table>
		</div>
		<div align="center" style="float:left;width: 30%;padding-left: 6%;">
			<h4 align="center">Create Your League</h4>
			<form  method="POST" action="/t20/league/new" onsubmit="return validateName();">
			</form>
			<c:if test="${cflag=='P'}">
				<h5>Please save this code.</h5><h5 align="center" style="color: green">${leagueAddMessage}</h5><h5> People who want to join your league will need it from you !!</h5>
			</c:if>
			<c:if test="${cflag=='F'}">
				<h5 align="center" style="color: red">${leagueAddMessage}</h5>
			</c:if>
		</div>
		
		<div align="left" style="float:right;width: 37%;">
			<h4 align="center">My Leagues (Enrolled/Owned)</h4>
			<table class="table table-bordered">
			<thead>
				<tr>
					<th>Sr. No.</th>
					<th>Leagues</th>
					<th>Available Points</th>
				</tr>
			</thead>
			<tbody>
				<%
					int count2 = 1;
				%>
				<c:forEach items="${leagueUserList}" var="row">
					<tr>
						<td><%=count2%></td>
						<td><form action="/t20/league/leagueMatches" method="post">
						<input type="hidden" name="id" value="${row.league.id}">
						<input type="submit" value="${row.league.league_name}" class ="btn btn-primary">
						</form>
						<td>${row.available_balance}</td>
					</tr>
					<%
						count2++;
					%>
				</c:forEach>
			</tbody>
		</table>
		</div>
	</div>
	<%@include file="includes/footer.jsp"%>
</body>
</html>