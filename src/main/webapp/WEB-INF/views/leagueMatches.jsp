<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>League Matches</title>
<script type="text/javascript">
function validateBidAmt(val){
	var amt = document.getElementById("bidAmt"+val).value;
	var numCheck = isNaN(amt);
	if(numCheck){
		alert("Please Enter Numeric Value for Points")
		document.getElementById("bidAmt"+val).value="";
	}else{
		var intCheck=amt.indexOf(".");
	  if(intCheck != -1){
		  alert("No Decimal Values Please");
		  document.getElementById("bidAmt"+val).value="";	
	  }
	}
}
function validateFields(val) {
	var amt = document.getElementById("bidAmt"+val).value;
	var prediction = document.getElementById("team"+val).value;
	var saveFlag = false;
	var avl = '${avlBal}';
	if(amt == ""){
		alert("Please Enter Points");
	}else{
		if(prediction == ""){
			alert("Please select Prediction");
		}else{
			if(parseInt(avl) ==0){
				alert("Sorry...Points exhausted..You cannot play now");
			}else{
				if(parseInt(amt) > parseInt(avl)){
					alert("You can play a max of "+avl+" points");
				}else{
					var confirmVal = confirm("You are submitting "+amt+" points. Are you sure? You will not be able to change this later.");
					if(confirmVal){
						saveFlag = true;
					}
				}
			}
		}
	}
	return saveFlag;
}
</script>
</head>
<body>
	<%@include file="includes/header.jsp"%>
	<div align="right" style="margin: 2%">
		<%@include file="includes/navigation.jsp"%>
		<h3 align="center">League - ${leagueName}</h3>
		<br />
		<ul class="nav navbar-nav">
			<li><form action="/t20/league/leaderBoard" method=post>
					<input type="submit" value="LEADER BOARD" class="btn btn-primary">
					<input type="hidden" name="league" value="${league}">
				</form></li>
		</ul>
		<h3 align="center" style="color: red;">${failMessage}</h3>
		<h3 align="center" style="color: red;">${minAmt}</h3>
		<h3 align="right" style="padding-right: 2%;">Available Points:
			${avlBal}</h3>
		<table class="table table-bordered">
			<thead>
				<tr>
					<th>Match No.</th>
					<th>Details</th>
					<th>Match Date</th>
					<th>Select Your Pick</th>
					<th>Point Statistics</th>
					<th>Result</th>
				</tr>
			</thead>
			<tbody>
				<%
					int count = 1;
				%>
				<c:forEach items="${matchList}" var="row">
					<tr style="background-color: #A9E2F3">
						<td><%=count%></td>
						<td>${row.matchDetails}</td>
						<td>${row.matchPlayDate}</td>
						<c:if test="${row.bidFlag == 0}">
							<td><form action="/t20/league/saveBid" method="post"
									onsubmit="return validateFields(${row.id});">
									<c:if test="${row.en == 'e'}">
										<select id="team${row.id}" name="team${row.id}"
											class="selectpicker">
											<option value="">Please Select</option>
											<option value="${row.team1}">${row.team1}</option>
											<option value="${row.team2}">${row.team2}</option>
										</select>
										<input type="text" id="bidAmt${row.id}" name="bidAmt${row.id}"
											value="" placeholder="Points"
											onblur="javascript:validateBidAmt(${row.id});">
										<input type="submit" value="SAVE" class="btn btn-primary">
									</c:if>
									<c:if test="${row.en == 'd'}">
										<select id="team${row.id}" name="team${row.id}"
											class="selectpicker" disabled="disabled">
											<option value="">Please Select</option>
											<option value="${row.team1}">${row.team1}</option>
											<option value="${row.team2}">${row.team2}</option>
										</select>
										<input type="text" id="bidAmt${row.id}" name="bidAmt${row.id}"
											value="" placeholder="Points"
											onblur="javascript:validateBidAmt(${row.id});"
											disabled="disabled">
										<input type="submit" value="SAVE" class="btn btn-primary"
											disabled="disabled">
									</c:if>
									<input type="hidden" name="match${row.id}" value="${row.id}">
									<input type="hidden" name="league${row.id}"
										value="${row.league_id}">
								</form></td>
						</c:if>
						<c:if test="${row.bidFlag == 1}">
							<td><select class="selectpicker" disabled="disabled">
									<option value="">${row.prediction}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
							</select> <input type="text" value="${row.playAmount}" disabled="disabled">
						</c:if>
						<c:if test="${row.en == 'd'}">
							<td><form action="/t20/league/viewBidStats" method=post>
									<input type="submit" value="GET STATS" class="btn btn-primary">
									<input type="hidden" name="team1" value="${row.team1}">
									<input type="hidden" name="team2" value="${row.team2}">
									<input type="hidden" name="match" value="${row.id}"> <input
										type="hidden" name="league" value="${row.league_id}">
								</form></td>
						</c:if>
						<c:if test="${row.en == 'e'}">
							<td><form action="/t20/league/viewBidStats" method=post>
									<input type="hidden" name="team1"
										value="${row.team1}"> <input type="hidden"
										name="team2" value="${row.team2}"> <input
										type="hidden" name="match" value="${row.id}"> <input
										type="hidden" name="league" value="${row.league_id}">
								</form></td>
						</c:if>
						<c:if test="${row.status =='NULL'}">
							<td></td>
						</c:if>
						<c:if test="${row.status !='NULL'}">
							<td>${row.status}</td>
						</c:if>
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
