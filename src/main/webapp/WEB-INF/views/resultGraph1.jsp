<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script type="text/javascript">
	google.load('visualization', '1', {
		packages : [ 'corechart', 'bar' ]
	});
	google.setOnLoadCallback(drawMultSeries);

	function drawMultSeries() {
		var data = new google.visualization.DataTable();
		data.addColumn('string', 'Players');
		data.addColumn('number', 'Score');
		data.addColumn({type:'string', role:'style'});
		var listSize = '${finalList}';
		var arr = listSize.split(",");

		var grid = arr[0].toString().replace("]", "").replace("[","").replace("[", "");
		grid = grid.substr(grid.indexOf("-") + 1,grid.length);
		var grVal = parseInt(grid)+1;

		for (var i = 0; i < arr.length; i++) {
			var completeText = arr[i].toString().replace("]", "").replace("[","").replace("[", "");
			var name = completeText.substr(0, completeText.indexOf("-"));
			var win = completeText.substr(completeText.indexOf("-") + 1,completeText.length);
			if(i<=3){
				data.addRow([name, parseInt(win), 'color:SteelBlue']);
			}else{
				data.addRow([name, parseInt(win), 'color:SteelBlue']);
			}
		}
		var options = {
			title : 'Players Correct Predictions',
			'chartArea' : {
				top : 100,
				right : 0,
				bottom : 0,
				width : '60%'
			},
			height:'1000',
			hAxis : {
				title : 'Correct Predictions',
				minValue : 0,
				gridlines: {count: grVal}
			},
			vAxis : {
				title : 'Players',
				textStyle:{fontSize: '12', paddingRight: '100',marginRight: '100'}
			}
		};

		var chart = new google.visualization.BarChart(document
				.getElementById('chart_div'));
		chart.draw(data, options);
	}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>IPL 2016 Predictions Result Graph</title>
</head>
<body>
	<%@include file="includes/header.jsp"%>
	<div align="right" style="margin: 2%">
	<%@include file="includes/navigation.jsp"%>
	<div id="chart_div"
		style="padding-left: 3%; padding-right: 3%"></div>
	<%@include file="includes/footer.jsp"%>
	</div>
</body>
</html>