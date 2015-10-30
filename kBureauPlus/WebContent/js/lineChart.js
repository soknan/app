var chart;
var chartCursor;



$(function(){
	test();	
});


function renderStackColumnChart(data) {
	
	$('#divTotalRptDaily').html();
	
	var chartData = eval(data);
	alert(chartData);
	
	gDataTotalRptDaily = chartData;
	
	// SERIAL CHART
	chart = new AmCharts.AmSerialChart();
	chart.dataProvider = chartData;
	chart.categoryField = "date";

	// AXES
	// category
	var categoryAxis = chart.categoryAxis;
	categoryAxis.gridAlpha = 0.1;
	categoryAxis.axisAlpha = 0;
	categoryAxis.gridPosition = "start";

	// value
	var valueAxis = new AmCharts.ValueAxis();
	valueAxis.stackType = "regular";
	valueAxis.gridAlpha = 0.1;
	valueAxis.axisAlpha = 0;
	chart.addValueAxis(valueAxis);

	// GRAPHS
	// first graph -- Lite
	var graph = new AmCharts.AmGraph();
	graph.title = "Lite";
	graph.labelText = "[[value]]";
	graph.valueField = "lite";
	graph.type = "column";
	graph.lineAlpha = 0;
	graph.fillAlphas = 1;
	graph.lineColor = "#99CC33";
	chart.addGraph(graph);

	// second graph -- Standard
	graph = new AmCharts.AmGraph();
	graph.title = "Standard";
	graph.labelText = "[[value]]";
	graph.valueField = "std";
	graph.type = "column";
	graph.lineAlpha = 0;
	graph.fillAlphas = 1;
	graph.lineColor = "#FF9D09";
	chart.addGraph(graph);

	// LEGEND
	var legend = new AmCharts.AmLegend();
	chart.addLegend(legend);

	// WRITE
	chart.write('divTotalRptDaily');
}



function renderLineChart() {
	// generate some data first
	generateChartData();
	// SERIAL CHART
	chart = new AmCharts.AmSerialChart();
	chart.pathToImages = "img/amChart/";
	chart.zoomOutButton = {
		backgroundColor : '#000000',
		backgroundAlpha : 0.15
	};
	chart.dataProvider = chartData;
	chart.categoryField = "date";
	chart.balloon.bulletSize = 5;
	// listen for "dataUpdated" event (fired when chart is rendered) and call
	// zoomChart method when it happens
	chart.addListener("dataUpdated", zoomChart);
	// AXES
	// category
	var categoryAxis = chart.categoryAxis;
	categoryAxis.parseDates = true; // as our data is date-based, we set
									// parseDates to true
	categoryAxis.minPeriod = "DD"; // our data is daily, so we set minPeriod to
									// DD
	categoryAxis.dashLength = 1;
	categoryAxis.gridAlpha = 0.15;
	categoryAxis.position = "top";
	categoryAxis.axisColor = "#DADADA";
	// value
	var valueAxis = new AmCharts.ValueAxis();
	valueAxis.axisAlpha = 0;
	valueAxis.dashLength = 1;
	chart.addValueAxis(valueAxis);
	// GRAPH
	var graph = new AmCharts.AmGraph();
	graph.title = "red line";
	graph.valueField = "visits";
	graph.bullet = "round";
	graph.bulletBorderColor = "#FFFFFF";
	graph.bulletBorderThickness = 2;
	graph.lineThickness = 2;
	graph.lineColor = "#5fb503";
	graph.negativeLineColor = "#efcc26";
	graph.hideBulletsCount = 50; // this makes the chart to hide bullets when
									// there are more than 50 series in
									// selection
	chart.addGraph(graph);
	// CURSOR
	chartCursor = new AmCharts.ChartCursor();
	chartCursor.cursorPosition = "mouse";
	chartCursor.pan = true; // set it to fals if you want the cursor to work in
							// "select" mode
	chart.addChartCursor(chartCursor);
	// SCROLLBAR
	var chartScrollbar = new AmCharts.ChartScrollbar();
	chart.addChartScrollbar(chartScrollbar);
	// WRITE
	chart.write("chartdiv");
}

// generate some random data, quite different range
function generateChartData() {
	var firstDate = new Date();
	firstDate.setDate(firstDate.getDate() - 500);
	for ( var i = 0; i < 500; i++) {
		var newDate = new Date(firstDate);
		newDate.setDate(newDate.getDate() + i);
		var visits = Math.round(Math.random() * 40) - 20;
		chartData.push({
			date : newDate,
			visits : visits
		});
	}
}
// this method is called when chart is first inited as we listen for
// "dataUpdated" event
function zoomChart() {
	// different zoom methods can be used - zoomToIndexes, zoomToDates,
	// zoomToCategoryValues
	chart.zoomToIndexes(chartData.length - 40, chartData.length - 1);
}
// changes cursor mode from pan to select
function setPanSelect() {
	if (document.getElementById("rb1").checked) {
		chartCursor.pan = false;
		chartCursor.zoomable = true;
	} else {
		chartCursor.pan = true;
	}
	chart.validateNow();
}

var chart;

var chartData = [{
    year: "2003",
    europe: 2.5,
    namerica: 2.5,
    asia: 2.1,
    lamerica: 0.3,
    meast: 0.2,
    africa: 0.1
}, {
    year: "2004",
    europe: 2.6,
    namerica: 2.7,
    asia: 2.2,
    lamerica: 0.3,
    meast: 0.3,
    africa: 0.1
}, {
    year: "2005",
    europe: 2.8,
    namerica: 2.9,
    asia: 2.4,
    lamerica: 0.3,
    meast: 0.3,
    africa: 0.1
}];

function test() {
    // SERIAL CHART
    chart = new AmCharts.AmSerialChart();
    chart.dataProvider = chartData;
    chart.categoryField = "year";

    // AXES
    // category
    var categoryAxis = chart.categoryAxis;
    categoryAxis.gridAlpha = 0.1;
    categoryAxis.axisAlpha = 0;
    categoryAxis.gridPosition = "start";

    // value
    var valueAxis = new AmCharts.ValueAxis();
    valueAxis.stackType = "regular";
    valueAxis.gridAlpha = 0.1;
    valueAxis.axisAlpha = 0;
    chart.addValueAxis(valueAxis);

    // GRAPHS
    // first graph    
    var graph = new AmCharts.AmGraph();
    graph.title = "Europe";
    graph.labelText = "[[value]]";
    graph.valueField = "europe";
    graph.type = "column";
    graph.lineAlpha = 0;
    graph.fillAlphas = 1;
    graph.lineColor = "#C72C95";
    chart.addGraph(graph);

    // second graph              
    graph = new AmCharts.AmGraph();
    graph.title = "North America";
    graph.labelText = "[[value]]";
    graph.valueField = "namerica";
    graph.type = "column";
    graph.lineAlpha = 0;
    graph.fillAlphas = 1;
    graph.lineColor = "#D8E0BD";
    chart.addGraph(graph);

    // third graph                              
    graph = new AmCharts.AmGraph();
    graph.title = "Asia-Pacific";
    graph.labelText = "[[value]]";
    graph.valueField = "asia";
    graph.type = "column";
    graph.lineAlpha = 0;
    graph.fillAlphas = 1;
    graph.lineColor = "#B3DBD4";
    chart.addGraph(graph);

    // LEGEND                  
    var legend = new AmCharts.AmLegend();
    chart.addLegend(legend);

    // WRITE
    chart.write("chartdiv");
}
