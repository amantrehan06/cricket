<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>IPL 2016 Teams</title>
</head>
<body>
<%@include file="includes/header.jsp"%>
<div align="right" style="margin: 2%">
		<%@include file="includes/navigation.jsp"%>
		<table class="table table-bordered">
			<thead>
				<tr>
					<th>Team Name</th>
					<th>Team Code</th>
					<th>Team Captain</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${teams}" var="row">
				<tr>
					<td>${row.teamName}</td>
					<td>${row.teamCode}</td>
					<td>${row.captain}</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>
<%@include file="includes/footer.jsp"%>
</body>
</html>