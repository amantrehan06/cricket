<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>IPL 2015 All Predictions</title>
  <script type="text/javascript" src="https://www.google.com/jsapi"></script>
  <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
  <script type="text/javascript">
  google.load('visualization', '1', {packages: ['corechart', 'bar']});
  google.setOnLoadCallback(drawBasic);
  
  function drawBasic() {

	    var team1 = '${t1}';
	    var team2 = '${t2}';
	    var team3 = 'NOT PREDICTED';
	    var t1c = Number(parseFloat('${t1c}').toFixed(1));
	    var t2c = Number(parseFloat('${t2c}').toFixed(1));
	    var t3c = Number(parseFloat('${np}').toFixed(1));
	    
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Predictions');
        data.addColumn('number', 'Percentage');
        data.addColumn({type:'string', role:'style'});

        data.addRows([
          [team1, t1c,'color:orange'],
          [team2, t2c,'color:blue'],
          [team3, t3c,'color:gray'],
        ]);

        var options = {
          title: 'Predictions Percentage',
          hAxis: {
            title: 'Predictions'
          },
          vAxis: {
            title: 'Percentage',
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
	<!-- 	<p align="center" class="form-style lead">YOUR FRIENDS PREDICTIONS FOR THIS MATCH</p> -->
		<%@include file="includes/navigation.jsp"%>
		<h3 align="center">Match - ${match}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Date - ${date}</h3>
		<br/>
		<p align="center"><button id="stats" class="btn btn-primary">Show Stats</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<button id="overall" class ="btn btn-primary">Show Predictions</button>
		</p>	
		<div align="center" id="chart_div">
		</div>
		<table style="display: none;"  id="res" class="table table-bordered">
			<thead>
				<tr>
					<th>Sr. No.</th>
					<th>Friend's Name</th>
					<th>Friend's Prediction</th>
				</tr>
			</thead>
			<tbody>
				<%
					int count = 1;
				%>
				<c:forEach items="${predictionList}" var="row">
					<tr>
						<td><%=count%></td>
						<td>${row.name}</td>
						<td>${row.prediction}</td>
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