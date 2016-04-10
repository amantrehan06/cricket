<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>World Cup T20 Predictions Result</title>
</head>
<body>
	<%@include file="includes/header.jsp"%>
	<div align="right" style="margin: 2%">
		<%@include file="includes/navigation.jsp"%>
		<table class="table table-bordered">
			<thead>
				<tr>
					<th>Count/Rank</th>
					<th>Name</th>
					<th>Unique ID</th>
					<th>Total Matches Over&nbsp;&nbsp;&nbsp;&nbsp;=</th>
					<th>Correct Predictions&nbsp;&nbsp;&nbsp;&nbsp;+</th>
					<th>Incorrect Predictions&nbsp;&nbsp;&nbsp;&nbsp;+</th>
					<th>Not Predicted</th>
					<th>Accuracy Percentage ( % )</th>
					<th>Points Earned</th>
				</tr>
			</thead>
			<tbody>
				<%
					int count = 1;
				%>
				<c:forEach items="${score}" var="row">
					<tr>
						<td><%=count%></td>
						<td>${row.name}</td>
						<td>${row.empid}</td>
						<td>${row.total}</td>
						<td>${row.win}</td>
						<td>${row.loss}</td>
						<td>${row.np}</td>
						<td>${row.ac} &nbsp;%</td>
						<td>${row.points}</td>
					</tr>
					<%
						count++;
					%>
				</c:forEach>
			</tbody>
		</table>
	<%@include file="includes/footer.jsp"%>
	</div>
</body>
</html>