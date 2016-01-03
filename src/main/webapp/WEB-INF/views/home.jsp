<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script src="../../js/jquery-ui.js"></script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>IPL 2016 Home</title>
<style type="text/css">
.match1{
background-color: #A9E2F3
}
.match2{
background-color: #E1F5A9
}
</style>
</head>
<body>
	<%@include file="includes/header.jsp"%>
	<div align="right" style="margin: 2%">
	<%@include file="includes/navigation.jsp"%>
	<div class="form-style">
	<c:if test="${adjust != ''}">
		<c:if test="${adjust == 'P'}">
			<h3 align="center" style="color: green;">Adjusted</h3>
		</c:if>
		<c:if test="${adjust == 'F'}">
			<h3 align="center" style="color: red;">This Match is already adjusted</h3>
		</c:if>
	</c:if>
		<table class="table table-bordered">
			<thead>
				<tr>
					<th>Match No.</th>
					<th>Details</th>
					<th>Match Date</th>
					<c:choose>
						<c:when test="${user=='nadmin'}">
							<th>Your Pick</th>
						</c:when>
						<c:otherwise>
							<th>Winning Team</th>
							<th>League Adjustments</th>
						</c:otherwise>
					</c:choose>
					<c:if test="${user == 'nadmin'}">
						<th>Prediction</th>
						<th>Result</th>
					</c:if>
				</tr>
			</thead>
			<tbody>
				<%
					int count = 1;
				%>
				<c:forEach items="${matchList}" var="row">
				<c:choose>
				<c:when test="${row.c== 1}">
					<tr class="match1">
						<td><%=count%></td>
						<td><a href="/iplT20/allPredictions?matchId=${row.id}">${row.matchDetails}</a></td>
						<td>${row.matchPlayDate}</td>
						<td><c:choose>
								<c:when test="${user == 'nadmin'}">
									<c:if test="${row.en == 'e'}">
										<a href="/iplT20/prediction?resp=${row.team1}&match=${row.id}">${row.team1}</a>&nbsp;/
										<a href="/iplT20/prediction?resp=${row.team2}&match=${row.id}">${row.team2}</a>&nbsp;/
										<a href="/iplT20/prediction?resp=DRAW&match=${row.id}">DRAW</a>
									</c:if>
									<c:if test="${row.en == 'd'}">
										<span style="color: #585858;">${row.team1}&nbsp;/
										${row.team2}&nbsp;/
										DRAW</span>
									</c:if>
								</c:when>
								<c:otherwise>
									<c:if test="${row.status == 'NULL'}">
										<a
											href="/iplT20/saveMatchResult?resp=${row.team1}&match=${row.id}">${row.team1}</a>&nbsp;/
										<a
											href="/iplT20/saveMatchResult?resp=${row.team2}&match=${row.id}">${row.team2}</a>&nbsp;/
										<a href="/iplT20/saveMatchResult?resp=DRAW&match=${row.id}">DRAW</a>
									</c:if>
									<c:if test="${row.status != 'NULL'}">
										${row.status}
									</c:if>
								</c:otherwise>
							</c:choose></td>
						<c:if test="${user == 'nadmin'}">
							<td>${row.status}</td>
						</c:if>
						<c:if test="${user == 'admin'}">
							<td><form action="" method="post">
									<c:if test="${row.adjustButtonFlag =='F'}">
										<input type="submit" value="Adjust" class = "btn btn-primary">
									</c:if>
									<c:if test="${row.adjustButtonFlag =='T'}">
										<input type="submit" value="Adjust" class = "btn btn-primary" disabled="disabled">
									</c:if>
									<input type="hidden" name ="adjust" value="${row.id}">
								</form> 
							</td>
						</c:if>
						<c:if test="${row.actual != 'NULL' && user == 'nadmin'}">
							<td>${row.actual}</td>
						</c:if>
						<c:if test="${row.actual == 'NULL' && user == 'nadmin'}">
							<td></td>
						</c:if>
					</tr>
					</c:when>
					<c:otherwise>
					<tr class="match2">
						<td><%=count%></td>
						<td><a href="/iplT20/allPredictions?matchId=${row.id}">${row.matchDetails}</a></td>
						<td>${row.matchPlayDate}</td>
						<td><c:choose>
								<c:when test="${user == 'nadmin'}">
									<c:if test="${row.en == 'e'}">
										<a href="/iplT20/prediction?resp=${row.team1}&match=${row.id}">${row.team1}</a>&nbsp;/
										<a href="/iplT20/prediction?resp=${row.team2}&match=${row.id}">${row.team2}</a>&nbsp;/
										<a href="/iplT20/prediction?resp=DRAW&match=${row.id}">DRAW</a>
									</c:if>
									<c:if test="${row.en == 'd'}">
										<span style="color: #585858;">${row.team1}&nbsp;/
										${row.team2}&nbsp;/
										DRAW</span>
									</c:if>
								</c:when>
								<c:otherwise>
									<c:if test="${row.status == 'NULL'}">
										<a
											href="/iplT20/saveMatchResult?resp=${row.team1}&match=${row.id}">${row.team1}</a>&nbsp;/
										<a
											href="/iplT20/saveMatchResult?resp=${row.team2}&match=${row.id}">${row.team2}</a>&nbsp;/
										<a href="/iplT20/saveMatchResult?resp=DRAW&match=${row.id}">DRAW</a>
									</c:if>
									<c:if test="${row.status != 'NULL'}">
										${row.status}
									</c:if>
								</c:otherwise>
							</c:choose></td>
						<c:if test="${user == 'nadmin'}">
							<td>${row.status}</td>
						</c:if>
						<c:if test="${user == 'admin'}">
						${adjustButtonFlag}
							<td><form action="/iplT20/adjust" method="post">
									<c:if test="${row.adjustButtonFlag =='F'}">
										<input type="submit" value="Adjust" class = "btn btn-primary">
									</c:if>
									<c:if test="${row.adjustButtonFlag =='T'}">
										<input type="submit" value="Adjust" class = "btn btn-primary" disabled="disabled">
									</c:if>
									<input type="hidden" name ="adjust" value="${row.id}">
								</form> 
							</td>
						</c:if>
						<c:if test="${row.actual != 'NULL' && user == 'nadmin'}">
							<td>${row.actual}</td>
						</c:if>
						<c:if test="${row.actual == 'NULL' && user == 'nadmin'}">
							<td></td>
						</c:if>
					</tr>
					</c:otherwise>
					</c:choose>
					<%count++;%>
				</c:forEach>
			</tbody>
		</table>
		<c:if test="${user == 'admin'}">
			<div align="center">
				<h3>Add New Match</h3>
			</div>
			<form method="POST" action="/iplT20/newMatch" class="form-style">
				<div class="row">
					<div class="col-lg-3">
						<label for="matchDetails">Match Details</label> <input type="text"
							class="form-control" name="matchDetails"></input>
					</div>
					<div class="col-lg-3">
						<label for="matchPlayDate">Match Play Date</label> <input
							type="text" class="form-control" name="matchPlayDate"></input>
					</div>
					<div class="col-lg-3">
						<label for="team1">Team 1</label> <input type="text"
							class="form-control" name="team1"></input>
					</div>
					<div class="col-lg-3">
						<label for="team2">Team 2</label> <input type="text"
							class="form-control" name="team2"></input>
					</div>
				</div>
				<br> <input type="submit" class="btn btn-primary"
					value="Add Match" class="form-style" />
			</form>
			<div align="center">
				<h3>Add Teams</h3>
			</div>
			<form method="POST" action="/iplT20/newTeam" class="form-style">
				<div class="row">
					<div class="col-lg-3">
						<label for="teamName">Team Name</label> <input type="text"
							class="form-control" name="teamName"></input>
					</div>
					<div class="col-lg-3">
						<label for="teamCode">Team Code</label> <input type="text"
							class="form-control" name="teamCode"></input>
					</div>
					<div class="col-lg-3">
						<label for="captain">Captain</label> <input type="text"
							class="form-control" name="captain"></input>
					</div>
				</div>
				<br> <input type="submit" class="btn btn-primary"
					value="Add Team" class="form-style" />
			</form>
			<br />
			<span style="color: green">${teamsuccess}</span>
		</c:if>
		<%@include file="includes/footer.jsp"%>
		</div>
	</div>
</body>
</html>