<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>IPL 2015 Leader Board</title>
  <script type="text/javascript" src="https://www.google.com/jsapi"></script>
  <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
  <script type="text/javascript">
  google.load('visualization', '1', {packages: ['corechart', 'bar']});
  google.setOnLoadCallback(drawBasic);
  
  function drawBasic() {

        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Users');
        data.addColumn('number', 'Point Balance');
        data.addColumn({type:'string', role:'style'});

        var listSize = '${finalList}';
        var sizeList = '${sizeList}';
		var arr = listSize.split(",");

		for (var i = 0; i < arr.length; i++) {
			var completeText = arr[i].toString().replace("]", "").replace("[","").replace("[", "");
			var name = completeText.substr(0, completeText.indexOf("-"));
			var points = completeText.substr(completeText.indexOf("-") + 1,completeText.length);
			data.addRow([name, parseInt(points), 'color:blue']);
		}

        var options = {
          title: 'Leader Board',
          hAxis: {
            title: 'Players'
          },
          vAxis: {
            title: 'Point Balance',
            minValue:0,
            gridlines :{
            	count:-1
            }
          },
          height:395,
          width:850
        };

        var chart = new google.visualization.ColumnChart(
          document.getElementById('chart_div'));

        chart.draw(data, options);
      }
  </script>  
</head>
<body>
	<%@include file="includes/header.jsp"%>
	<div align="right" style="margin: 2%">
		<%@include file="includes/navigation.jsp"%>
		<h3 align="center">LEADERBOARD : League - ${leagueName}</h3>
		<br/>
		<div align="left">
        	<form action="/iplT20/league/leagueMatches" method="post">
			<input type="hidden" name="id" value="${leagueId}">
			<input type="submit" value="LEAGUE HOME" class ="btn btn-primary">
		</form>
		</div>
		<div align="center" id="chart_div">
		</div>
	</div>
	<%@include file="includes/footer.jsp"%>
</body>
</html>