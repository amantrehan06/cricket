<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>World Cup T20 Home</title>
 
<style type="text/css">
.match1{
background-color: #FFFFFF
}
.match2{
background-color: #FFFFFF
}
</style>
</head>
<body >
	<%@include file="includes/header.jsp"%>
	
<link rel="stylesheet" href="http://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">

  <script src="http://code.jquery.com/jquery-1.10.2.js"></script>
  <script src="http://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
   <!-- <link rel="stylesheet" href="/t20/css/jquery-ui-timepicker-addon.css"></link>
    <script src="/t20/js/jquery-ui-timepicker-addon.js"></script>  -->
<link rel="stylesheet" href="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.2/themes/smoothness/jquery-ui.css" type="text/css" media="all" />
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-datetimepicker/2.4.5/jquery.datetimepicker.js"></script>
 <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-datetimepicker/2.4.5/jquery.datetimepicker.css">
  
  <script>
  $(function() { 
 	 $('#timedate').datetimepicker();
  });
  
  var teamList = "";
  var teamCode = "";
  $.ajax({
	  type: "GET",
	  url: "/t20/teamsList",
	  async: false,
	  success: function(result){
		  teamList = JSON.parse(result);
  }});
  
  $.ajax({
	  type: "GET",
	  url: "/t20/teamsCode",
	  async: false,
	  success: function(result){
		  teamCode = JSON.parse(result);
  }});
  
  $(function() {  
    $("#teamListName").autocomplete({
      source: teamList
    });
  });
  
  $(function() {  
	    $('#teamListCode').autocomplete({
	      source: teamCode
	    });
	  });
  
  $(function() {  
	    $('#teamListCode1').autocomplete({
	      source: teamCode
	    });
	  });
  
  $(function() {  
	    $('#teamListCode2').autocomplete({
	      source: teamCode
	    });
	  });

  </script>
  
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
				<!-- {actual=NULL, c=1, team1=CSK, team2=MUM, matchDetails=NEW MATCH SETUP, en=e, id=1, matchPlayDate=Sat Apr 02 04:11:22  2016, status=NOT PREDICTED YET} -->
				<c:forEach items="${matchList}" var="row">
				<c:choose>
				<c:when test="${row.c== 1}">
					<tr class="match1">
						<td><%=count%></td>
						
						
						<td>
						<c:if test="${row.matchEnable == 'e'}">
							<a href="/t20/allPredictions?matchId=${row.id}">${row.matchDetails}</a>
						</c:if>
						<c:if test="${row.matchEnable == 'd'}">
							${row.matchDetails}
						</c:if>
						</td>
						
						<td>${row.matchPlayDate}</td>
						
						
						
						<td><c:choose>
								<c:when test="${user == 'nadmin'}">
									<c:if test="${row.en == 'e'}">
										<a href="/t20/prediction?resp=${row.team1}&match=${row.id}">${row.team1}</a>&nbsp;/
										<a href="/t20/prediction?resp=${row.team2}&match=${row.id}">${row.team2}</a>
										
									</c:if>
									<c:if test="${row.en == 'd'}">
										<span style="color: #585858;">${row.team1}&nbsp;/
										${row.team2}
										</span>
									</c:if>
								</c:when>
								<c:otherwise>
									<c:if test="${row.status == 'NULL'}">
										<a
											href="/t20/saveMatchResult?resp=${row.team1}&match=${row.id}">${row.team1}</a>&nbsp;/
										<a
											href="/t20/saveMatchResult?resp=${row.team2}&match=${row.id}">${row.team2}</a>
										
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
						
						<td>
						<c:if test="${row.matchEnable == 'e'}">
							<a href="/t20/allPredictions?matchId=${row.id}">${row.matchDetails}</a>
						</c:if>
						<c:if test="${row.matchEnable == 'd'}">
							${row.matchDetails}
						</c:if>
						</td>
						
						<td>${row.matchPlayDate}</td>
						<td><c:choose>
								<c:when test="${user == 'nadmin'}">
									<c:if test="${row.en == 'e'}">
										<a href="/t20/prediction?resp=${row.team1}&match=${row.id}">${row.team1}</a>&nbsp;/
										<a href="/t20/prediction?resp=${row.team2}&match=${row.id}">${row.team2}</a>
									</c:if>
									<c:if test="${row.en == 'd'}">
										<span style="color: #585858;">${row.team1}&nbsp;/
										${row.team2}
										</span>
									</c:if>
								</c:when>
								<c:otherwise>
									<c:if test="${row.status == 'NULL'}">
										<a
											href="/t20/saveMatchResult?resp=${row.team1}&match=${row.id}">${row.team1}</a>&nbsp;/
										<a
											href="/t20/saveMatchResult?resp=${row.team2}&match=${row.id}">${row.team2}</a>
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
							<td><form action="/t20/adjust" method="post">
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
			<form method="POST" action="/t20/newMatch" class="form-style">
				<div class="row">
					<div class="col-lg-3">
						<label for="matchDetails">Match Details</label> <input type="text"
							class="form-control" name="matchDetails"></input>
					</div>
					<div class="col-lg-3">
						<label for="matchPlayDate">Match Play Date</label> <input id = "timedate"
							type="text" class="form-control" name="matchPlayDate"></input>
					</div>
					<div class="col-lg-3">
						<label for="team1">Team 1</label> <input type="text" id="teamListCode1"
							class="form-control" name="team1"></input>
					</div>
					<div class="col-lg-3">
						<label for="team2">Team 2</label> <input type="text" id="teamListCode2"
							class="form-control" name="team2"></input>
					</div>
				</div>
				<br> <input type="submit" class="btn btn-primary"
					value="Add Match" class="form-style" />
			</form>
			<div align="center">
				<h3>Add Teams</h3>
			</div>
			<form method="POST" action="/t20/newTeam" class="form-style">
				<div class="row">
					<div class="col-lg-3">
						<label for="teamName">Team Name</label> <input id="teamListName" type="text"
							class="form-control" name="teamName"></input>
					</div>
					<div class="col-lg-3">
						<label for="teamCode">Team Code</label> <input type="text" id="teamListCode"
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