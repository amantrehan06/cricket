<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>IPL 2015 Point Status</title>
  <script type="text/javascript" src="https://www.google.com/jsapi"></script>
  <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
  <script type="text/javascript">
  google.load('visualization', '1', {packages: ['corechart', 'bar']});
  google.setOnLoadCallback(drawBasic);
  
  function drawBasic() {

	    var team1 = '${team1}';
	    var team2 = '${team2}';
	    var t1c = parseInt('${team1Amt}');
	    var t2c = parseInt('${team2Amt}');
	    
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Teams');
        data.addColumn('number', 'Points');
        data.addColumn({type:'string', role:'style'});

        data.addRows([
          [team1, t1c,'color:orange'],
          [team2, t2c,'color:blue'],
        ]);

        var options = {
          title: 'Point Statistics',
          hAxis: {
            title: 'Teams'
          },
          vAxis: {
            title: 'Total Points for this Match',
            maxValue: 100,
            minValue:0,
            gridlines :{
            	count:11
            }
          },
          height:395,
          width:850
        };

        var chart = new google.visualization.ColumnChart(
          document.getElementById('chart_div'));

        chart.draw(data, options);
      }

  $(document).ready(function(){
	    $("#stats").click(function(){
	        $("#chart_div").show(1000);
	        $("#res").hide(1000);
	    });
	});

 $(document).ready(function(){
	    $("#overall").click(function(){
	    	$("#chart_div").hide(1000);
	        $("#res").show(1000);
	    });
	});
  </script>  
</head>
<body>
	<%@include file="includes/header.jsp"%>
	<div align="right" style="margin: 2%">
		<%@include file="includes/navigation.jsp"%>
		<h3 align="center">POINT STATS : League - ${leagueName}</h3>
		<br/>
		<div align="left" style="float: left;">
        	<form action="/t20/league/leagueMatches" method="post">
			<input type="hidden" name="id" value="${leagueId}">
			<input type="submit" value="LEAGUE HOME" class ="btn btn-primary">
		</form>
		</div>
		<br/>
		<p align="center"><button id="stats" class="btn btn-primary">SHOW SCALE</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<button id="overall" class ="btn btn-primary">SHOW OTHERS GAME</button>
		<br/><br/>
		<div align="center" id="chart_div">
		</div>
		<table style="display: none;"  id="res" class="table table-bordered">
			<thead>
				<tr>
					<th>Sr. No.</th>
					<th>Name</th>
					<th>Prediction</th>
					<th>Points</th>
				</tr>
			</thead>
			<tbody>
				<%
					int count = 1;
				%>
				<c:forEach items="${bids}" var="row">
					<tr>
						<td><%=count%></td>
						<td>${row.name}</td>
						<td>${row.prediction}</td>
						<td>${row.playAmount}</td>
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