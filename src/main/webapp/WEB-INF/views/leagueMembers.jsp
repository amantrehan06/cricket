<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>IPL 2015 League Members</title>
</head>
<body>
	<%@include file="includes/header.jsp"%>
	<div align="right" style="margin: 2%">
		<%@include file="includes/navigation.jsp"%>
		<h4 align="center">LEAGUE : ${leagueName}</h4>
		<div align="center">
		<form method="post" action="/t20/league/join">
			<input type="hidden" name="leagueId" value="${leagueId}">
		</form>
		</div>
		<br/>
		<h4 align="center" style="color: red;">${invalid}</h4>
		<table class="table table-bordered">
			<thead>
				<tr>
					<th>Sr. No.</th>
					<th>League Members</th>
				</tr>
			</thead>
			<tbody>
				<%
					int count = 1;
				%>
				<c:forEach items="${leagueMembersList}" var="row">
					<tr>
						<td><%=count%></td>
						<td>${row.user.firstName}&nbsp;${row.user.lastName}</td>
					</tr>
					<%
						count++;
					%>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<%@include file="includes/footer.jsp"%>
</body>
</html>