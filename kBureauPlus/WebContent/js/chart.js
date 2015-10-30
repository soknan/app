var gDataAction;
var gDataDecision;
var gDataActionByDay;
var gDataActionByBranch;
var gDataCbcFee;
var gDataIncomeExpense;
var gDataLoanByBranch;
var gDataLoanAll;


function refreshChart() {
	renderChartSummary(gDataAction, gDataDecision, gDataCbcFee,
			gDataIncomeExpense, gDataLoanAll);
}

function renderChartSummary(dataAction, dataDecision, dataFeeCbc,
		dataIncomeExpense, dataLoanAll) {
	renderPieActionSummary(dataAction);
	renderPieDecisionSummary(dataDecision);
	renderColumnChartCbcFeeSummary(dataFeeCbc);
	renderColumnChartIncomeExpenseSummary(dataIncomeExpense);
	renderTableLoanSummary(dataLoanAll);
	renderColumnLoanAmountAll(dataLoanAll);
}

function renderChartAction(dataActionByDay, dataActionByBranch) {
	renderStackColumnActionByDay(dataActionByDay);
	renderStackColumnActionByBranch(dataActionByBranch);
}

function renderChartLoan(data){
	renderColumnLoanCountByBranch(data);
	renderColumnLoanAmountByBranch(data);
}

function renderChartDecision(dataActionByDecision, dataDecisionByAction, dataDecisionByBranch){
	renderStackColumnActionByDecision(dataActionByDecision);
	renderStackColumnDecisionByAction(dataDecisionByAction);
	renderStackColumnDecisionByBranch(dataDecisionByBranch);
}

function renderChartFee(dataCbcFeeByBranch, dataIncomeExpenseByBranch){
	renderColumnChartCbcFeeByBranch(dataCbcFeeByBranch);
	renderColumnChartIncomeExpenseByBranch(dataIncomeExpenseByBranch);
}

function renderPieActionSummary(data) {
	$('#divPieAction').html('');
	console.log(data);
	var chartData = eval(data);
	gDataAction = chartData;
	var legend;

	// PIE CHART
	var chart = new AmCharts.AmPieChart();
	chart.dataProvider = chartData;
	chart.colors = [ "#99CC33", "#FF9D09", "#F66206" ];
	chart.titleField = "type";
	chart.valueField = "value";
	chart.outlineColor = "#FFFFFF";
	chart.outlineAlpha = 0.9;
	chart.outlineThickness = 2;
	chart.startEffect = ">";
	// this makes the chart 3D
	chart.depth3D = 10;
	chart.angle = 5;

	// LEGEND
	legend = new AmCharts.AmLegend();
	legend.align = "center";
	legend.markerType = "circle";
	legend.borderAlpha = 10;
	legend.borderColor = "#DAF0FD";

	legend.backgroundAlpha = 10;
	legend.backgroundColor = "#F4F4F4";

	legend.equalWidths = false;

	chart.addLegend(legend);

	// chart.labelRadius = -70;
	// chart.labelText = "[[title]]: [[value]] ([[percents]]%)";

	// WRITE
	chart.write("divPieAction");
}

function renderPieDecisionSummary(data) {
	$('#divPieDecision').html('');

	var chartData = eval(data);
	gDataDecision = chartData;
	var legend;

	// PIE CHART
	var chart = new AmCharts.AmPieChart();
	chart.dataProvider = chartData;
	chart.colors = [ "#EAEE04", "#32CB1E", "#CB1E4C", "#E5EDEA" ];
	chart.titleField = "type";
	chart.valueField = "value";
	chart.outlineColor = "#FFFFFF";
	chart.outlineAlpha = 0.9;
	chart.outlineThickness = 2;
	chart.startEffect = ">";
	// this makes the chart 3D
	chart.depth3D = 10;
	chart.angle = 5;

	// LEGEND
	legend = new AmCharts.AmLegend();
	legend.align = "center";
	legend.markerType = "circle";
	legend.equalWidths = false;

	legend.markerType = "circle";
	legend.borderAlpha = 10;
	legend.borderColor = "#DAF0FD";

	legend.backgroundAlpha = 10;
	legend.backgroundColor = "#F4F4F4";

	chart.addLegend(legend);

	// chart.labelRadius = -70;
	// chart.labelText = "[[title]]: [[value]] ([[percents]]%)";

	// WRITE
	chart.write("divPieDecision");
}

function renderStackColumnActionByDay(data) {
	$('#divActionByDay').html('');
	var chartData = eval(data);
	gDataActionByDay = chartData;

	// SERIAL CHART
	chart = new AmCharts.AmSerialChart();
	chart.dataProvider = chartData;
	chart.categoryField = "date";
	//chart.startDuration = 1;

	// AXES
	// category
	var categoryAxis = chart.categoryAxis;
	categoryAxis.gridAlpha = 0.1;
	categoryAxis.axisAlpha = 0;
	categoryAxis.labelRotation = 45;
	categoryAxis.gridPosition = "start";

	// value
	var valueAxis = new AmCharts.ValueAxis();
	valueAxis.stackType = "regular";
	valueAxis.gridAlpha = 0.1;
	valueAxis.axisAlpha = 0;
	valueAxis.title = "# Daily Enquiry";
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
	
	// 3rd graph -- Standard
	graph = new AmCharts.AmGraph();
	graph.title = "Standard2";
	graph.labelText = "[[value]]";
	graph.valueField = "std2";
	graph.type = "column";
	graph.lineAlpha = 0;
	graph.fillAlphas = 1;
	graph.lineColor = "#F66206";
	chart.addGraph(graph);

	chart.depth3D = 10;
	chart.angle = 70;

	// LEGEND
	var legend = new AmCharts.AmLegend();
	chart.addLegend(legend);

	// WRITE
	chart.write("divActionByDay");
}

function renderStackColumnActionByBranch(data) {
	$('#divActionByBranch').html('');
	var chartData = eval(data);
	gDataActionByBranch = chartData;

	// SERIAL CHART
	chart = new AmCharts.AmSerialChart();
	chart.dataProvider = chartData;
	chart.categoryField = "label";
	//chart.startDuration = 1;

	// AXES
	// category
	var categoryAxis = chart.categoryAxis;
	categoryAxis.gridAlpha = 0.1;
	categoryAxis.axisAlpha = 0;
	//categoryAxis.labelRotation = 45;
	categoryAxis.gridPosition = "start";

	// value
	var valueAxis = new AmCharts.ValueAxis();
	valueAxis.stackType = "regular";
	valueAxis.gridAlpha = 0.1;
	valueAxis.axisAlpha = 0;
	valueAxis.title = "# Enquiry";
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
	graph.showAllValueLabels = true;
	chart.addGraph(graph);
	
	// 3rd graph -- Standard
	graph = new AmCharts.AmGraph();
	graph.title = "Standard2";
	graph.labelText = "[[value]]";
	graph.valueField = "std2";
	graph.type = "column";
	graph.lineAlpha = 0;
	graph.fillAlphas = 1;
	graph.lineColor = "#F66206";
	graph.showAllValueLabels = true;
	chart.addGraph(graph);

	chart.depth3D = 20;
	chart.angle = 60;

	// LEGEND
	var legend = new AmCharts.AmLegend();
	chart.addLegend(legend);

	// WRITE
	chart.write("divActionByBranch");
}

function renderColumnLoanCountByBranch(data) {
	$('#divLoanCountByBranch').html('');
	var chartData = eval(data);
	gDataLoanByBranch = chartData;

	// SERIAL CHART
	chart = new AmCharts.AmSerialChart();
	chart.dataProvider = chartData;
	chart.categoryField = "label";
	//chart.startDuration = 1;

	// AXES
	// category
	var categoryAxis = chart.categoryAxis;
	categoryAxis.gridAlpha = 0.1;
	categoryAxis.axisAlpha = 0;
	//categoryAxis.labelRotation = 45;
	categoryAxis.gridPosition = "start";

	// value
	var valueAxis = new AmCharts.ValueAxis();
	valueAxis.stackType = "regular";
	valueAxis.gridAlpha = 0.1;
	valueAxis.axisAlpha = 0;
	valueAxis.title = "Count";
	chart.addValueAxis(valueAxis);

	// GRAPHS
	// first graph -- Lite
	var graph = new AmCharts.AmGraph();
	graph.title = "Lite";
	graph.labelText = "[[value]]";
	graph.valueField = "loan";
	graph.type = "column";
	graph.lineAlpha = 0;
	graph.fillAlphas = 1;
	graph.lineColor = "#99CC33";
	graph.showAllValueLabels = true;
	chart.addGraph(graph);

	chart.depth3D = 20;
	chart.angle = 60;

	// WRITE
	chart.write("divLoanCountByBranch");
}

function renderColumnLoanAmountByBranch(data) {
	$('#divLoanAmountByBranch').html('');
	var chartData = eval(data);

	// SERIAL CHART
	chart = new AmCharts.AmSerialChart();
	chart.dataProvider = chartData;
	chart.categoryField = "label";
	//chart.startDuration = 1;

	// AXES
	// category
	var categoryAxis = chart.categoryAxis;
	categoryAxis.gridAlpha = 0.1;
	categoryAxis.axisAlpha = 0;
	//categoryAxis.labelRotation = 45;
	categoryAxis.gridPosition = "start";

	// value
	var valueAxis = new AmCharts.ValueAxis();
	valueAxis.stackType = "none";
	valueAxis.gridAlpha = 0.1;
	valueAxis.axisAlpha = 0;
	valueAxis.title = "Amount in USD";
	chart.addValueAxis(valueAxis);

	// GRAPHS
	// first graph -- Lite
	var graph = new AmCharts.AmGraph();
	graph.title = "amount";
	graph.labelText = "$ [[value]]";
	graph.valueField = "amount";
	graph.type = "column";
	graph.lineAlpha = 0;
	graph.fillAlphas = 1;
	graph.lineColor = "#FF9D09";
	graph.showAllValueLabels = true;
	chart.addGraph(graph);

	chart.depth3D = 20;
	chart.angle = 60;

	// WRITE
	chart.write("divLoanAmountByBranch");
}

function renderColumnChartCbcFeeSummary(data) {
	$('#divCbcFee').html('');
	var chartData = eval(data);
	gDataCbcFee = chartData;

	// SERIAL CHART
	chart = new AmCharts.AmSerialChart();
	chart.dataProvider = chartData;
	chart.categoryField = "type";
	//chart.startDuration = 1;

	// AXES
	// category
	var categoryAxis = chart.categoryAxis;
	categoryAxis.gridAlpha = 0.1;
	categoryAxis.axisAlpha = 0;
	categoryAxis.gridPosition = "start";

	// value
	var valueAxis = new AmCharts.ValueAxis();
	valueAxis.stackType = "none";
	valueAxis.gridAlpha = 0.1;
	valueAxis.axisAlpha = 0;
	valueAxis.title = "Fee charged in US Dollar";
	chart.addValueAxis(valueAxis);

	// GRAPHS
	// first graph -- Lite
	var graph = new AmCharts.AmGraph();
	graph.labelText = "$ [[value]]";
	graph.valueField = "value";
	graph.colorField = "color";
	graph.type = "column";
	graph.lineAlpha = 0;
	graph.fillAlphas = 1;
	graph.lineColor = "#99CC33";
	chart.addGraph(graph);

	chart.depth3D = 30;
	chart.angle = 60;

	// WRITE
	chart.write("divCbcFee");
}

function renderColumnChartIncomeExpenseSummary(data) {
	$('#divIncomeExpense').html('');
	var chartData = eval(data);
	gDataIncomeExpense = chartData;

	// SERIAL CHART
	chart = new AmCharts.AmSerialChart();
	chart.dataProvider = chartData;
	chart.categoryField = "type";
	//chart.startDuration = 1;

	// AXES
	// category
	var categoryAxis = chart.categoryAxis;
	categoryAxis.gridAlpha = 0.1;
	categoryAxis.axisAlpha = 0;
	categoryAxis.gridPosition = "start";

	// value
	var valueAxis = new AmCharts.ValueAxis();
	valueAxis.stackType = "none";
	valueAxis.gridAlpha = 0.1;
	valueAxis.axisAlpha = 0;
	valueAxis.title = "Amount in US Dollar";
	chart.addValueAxis(valueAxis);

	// GRAPHS
	// first graph -- Lite
	var graph = new AmCharts.AmGraph();
	graph.labelText = "$ [[value]]";
	graph.valueField = "value";
	graph.colorField = "color";
	graph.type = "column";
	graph.lineAlpha = 0;
	graph.fillAlphas = 1;
	graph.lineColor = "#99CC33";
	chart.addGraph(graph);

	chart.depth3D = 30;
	chart.angle = 60;

	// WRITE
	chart.write("divIncomeExpense");
}


function renderStackColumnActionByDecision(data) {
	$('#divActionByDecision').html('');
	var chartData = eval(data);
	
	// SERIAL CHART
	chart = new AmCharts.AmSerialChart();
	chart.dataProvider = chartData;
	chart.categoryField = "label";
	//chart.startDuration = 1;

	// AXES
	// category
	var categoryAxis = chart.categoryAxis;
	categoryAxis.gridAlpha = 0.1;
	categoryAxis.axisAlpha = 0;
	//categoryAxis.labelRotation = 45;
	categoryAxis.gridPosition = "start";

	// value
	var valueAxis = new AmCharts.ValueAxis();
	valueAxis.stackType = "regular";
	valueAxis.gridAlpha = 0.1;
	valueAxis.axisAlpha = 0;
	valueAxis.title = "# Enquiry";
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
	graph.showAllValueLabels = true;
	chart.addGraph(graph);
	
	// second graph -- Standard
	graph = new AmCharts.AmGraph();
	graph.title = "Standard2";
	graph.labelText = "[[value]]";
	graph.valueField = "std2";
	graph.type = "column";
	graph.lineAlpha = 0;
	graph.fillAlphas = 1;
	graph.lineColor = "#F66206";
	graph.showAllValueLabels = true;
	chart.addGraph(graph);

	chart.depth3D = 20;
	chart.angle = 60;

	// LEGEND
	var legend = new AmCharts.AmLegend();
	chart.addLegend(legend);

	// WRITE
	chart.write("divActionByDecision");
}

function renderStackColumnDecisionByAction(data) {
	$('#divDecisionByAction').html('');
	var chartData = eval(data);
	
	// SERIAL CHART
	chart = new AmCharts.AmSerialChart();
	chart.dataProvider = chartData;
	chart.categoryField = "label";
	//chart.startDuration = 1;

	// AXES
	// category
	var categoryAxis = chart.categoryAxis;
	categoryAxis.gridAlpha = 0.1;
	categoryAxis.axisAlpha = 0;
	//categoryAxis.labelRotation = 45;
	categoryAxis.gridPosition = "start";

	// value
	var valueAxis = new AmCharts.ValueAxis();
	valueAxis.stackType = "none";
	valueAxis.gridAlpha = 0.1;
	valueAxis.axisAlpha = 0;
	valueAxis.title = "# Enquiry";
	chart.addValueAxis(valueAxis);

	// GRAPHS
	// first graph -- Pending
	var graph = new AmCharts.AmGraph();
	graph.title = "Pending";
	graph.labelText = "[[value]]";
	graph.valueField = "pending";
	graph.type = "column";
	graph.lineAlpha = 0;
	graph.fillAlphas = 1;
	graph.lineColor = "#EAEE04";
	chart.addGraph(graph);

	// second graph -- Approved
	graph = new AmCharts.AmGraph();
	graph.title = "Approved";
	graph.labelText = "[[value]]";
	graph.valueField = "approved";
	graph.type = "column";
	graph.lineAlpha = 0;
	graph.fillAlphas = 1;
	graph.lineColor = "#32CB1E";
	chart.addGraph(graph);
	
	// third graph -- Rejected
	graph = new AmCharts.AmGraph();
	graph.title = "Rejected";
	graph.labelText = "[[value]]";
	graph.valueField = "rejected";
	graph.type = "column";
	graph.lineAlpha = 0;
	graph.fillAlphas = 1;
	graph.lineColor = "#CB1E4C";
	chart.addGraph(graph);
	
	// fourth graph -- Cancelled
	graph = new AmCharts.AmGraph();
	graph.title = "Cancelled";
	graph.labelText = "[[value]]";
	graph.valueField = "cancelled";
	graph.type = "column";
	graph.lineAlpha = 0;
	graph.fillAlphas = 1;
	graph.lineColor = "#E5EDEA";
	chart.addGraph(graph);

	chart.depth3D = 10;
	chart.angle = 40;

	// LEGEND
	var legend = new AmCharts.AmLegend();
	chart.addLegend(legend);

	// WRITE
	chart.write("divDecisionByAction");
}

function renderStackColumnDecisionByBranch(data) {
	$('#divDecisionByBranch').html('');
	var chartData = eval(data);
	
	// SERIAL CHART
	chart = new AmCharts.AmSerialChart();
	chart.dataProvider = chartData;
	chart.categoryField = "label";
	//chart.startDuration = 1;

	// AXES
	// category
	var categoryAxis = chart.categoryAxis;
	categoryAxis.gridAlpha = 0.1;
	categoryAxis.axisAlpha = 0;
	//categoryAxis.labelRotation = 45;
	categoryAxis.gridPosition = "start";

	// value
	var valueAxis = new AmCharts.ValueAxis();
	valueAxis.stackType = "none";
	valueAxis.gridAlpha = 0.1;
	valueAxis.axisAlpha = 0;
	valueAxis.title = "# Enquiry";
	chart.addValueAxis(valueAxis);

	// GRAPHS
	// first graph -- Pending
	var graph = new AmCharts.AmGraph();
	graph.title = "Pending";
	graph.labelText = "[[value]]";
	graph.valueField = "pending";
	graph.type = "column";
	graph.lineAlpha = 0;
	graph.fillAlphas = 1;
	graph.lineColor = "#EAEE04";
	chart.addGraph(graph);

	// second graph -- Approved
	graph = new AmCharts.AmGraph();
	graph.title = "Approved";
	graph.labelText = "[[value]]";
	graph.valueField = "approved";
	graph.type = "column";
	graph.lineAlpha = 0;
	graph.fillAlphas = 1;
	graph.lineColor = "#32CB1E";
	chart.addGraph(graph);
	
	// third graph -- Rejected
	graph = new AmCharts.AmGraph();
	graph.title = "Rejected";
	graph.labelText = "[[value]]";
	graph.valueField = "rejected";
	graph.type = "column";
	graph.lineAlpha = 0;
	graph.fillAlphas = 1;
	graph.lineColor = "#CB1E4C";
	chart.addGraph(graph);
	
	// fourth graph -- Cancelled
	graph = new AmCharts.AmGraph();
	graph.title = "Cancelled";
	graph.labelText = "[[value]]";
	graph.valueField = "cancelled";
	graph.type = "column";
	graph.lineAlpha = 0;
	graph.fillAlphas = 1;
	graph.lineColor = "#E5EDEA";
	chart.addGraph(graph);
	chart.depth3D = 5;
	chart.angle = 40;

	// LEGEND
	var legend = new AmCharts.AmLegend();
	chart.addLegend(legend);

	// WRITE
	chart.write("divDecisionByBranch");
}

function renderColumnChartCbcFeeByBranch(data) {
	$('#divCbcFeeByBranch').html('');
	var chartData = eval(data);
	
	// SERIAL CHART
	chart = new AmCharts.AmSerialChart();
	chart.dataProvider = chartData;
	chart.categoryField = "label";
	//chart.startDuration = 1;

	// AXES
	// category
	var categoryAxis = chart.categoryAxis;
	categoryAxis.gridAlpha = 0.1;
	categoryAxis.axisAlpha = 0;
	//categoryAxis.labelRotation = 45;
	categoryAxis.gridPosition = "start";

	// value
	var valueAxis = new AmCharts.ValueAxis();
	valueAxis.stackType = "none";
	valueAxis.gridAlpha = 0.1;
	valueAxis.axisAlpha = 0;
	valueAxis.title = "Amount in US Dollar";
	chart.addValueAxis(valueAxis);

	// GRAPHS
	// first graph -- Lite
	var graph = new AmCharts.AmGraph();
	graph.title = "Lite";
	graph.labelText = "$ [[value]]";
	graph.valueField = "feeLite";
	graph.type = "column";
	graph.lineAlpha = 0;
	graph.fillAlphas = 1;
	graph.lineColor = "#99CC33";
	chart.addGraph(graph);

	// second graph -- Standard
	graph = new AmCharts.AmGraph();
	graph.title = "Standard";
	graph.labelText = "$ [[value]]";
	graph.valueField = "feeStd";
	graph.type = "column";
	graph.lineAlpha = 0;
	graph.fillAlphas = 1;
	graph.lineColor = "#FF9D09";
	chart.addGraph(graph);
	
	// 3rd graph -- Standard
	graph = new AmCharts.AmGraph();
	graph.title = "Standard2";
	graph.labelText = "$ [[value]]";
	graph.valueField = "feeStd2";
	graph.type = "column";
	graph.lineAlpha = 0;
	graph.fillAlphas = 1;
	graph.lineColor = "#F66206";
	chart.addGraph(graph);
	
	chart.depth3D = 5;
	chart.angle = 40;

	// LEGEND
	var legend = new AmCharts.AmLegend();
	chart.addLegend(legend);

	// WRITE
	chart.write("divCbcFeeByBranch");
}

function renderColumnChartIncomeExpenseByBranch(data) {
	$('#divIncomeExpenseByBranch').html('');
	var chartData = eval(data);
	
	// SERIAL CHART
	chart = new AmCharts.AmSerialChart();
	chart.dataProvider = chartData;
	chart.categoryField = "label";
	//chart.startDuration = 1;

	// AXES
	// category
	var categoryAxis = chart.categoryAxis;
	categoryAxis.gridAlpha = 0.1;
	categoryAxis.axisAlpha = 0;
	//categoryAxis.labelRotation = 45;
	categoryAxis.gridPosition = "start";

	// value
	var valueAxis = new AmCharts.ValueAxis();
	valueAxis.stackType = "none";
	valueAxis.gridAlpha = 0.1;
	valueAxis.axisAlpha = 0;
	valueAxis.title = "Amount in US Dollar";
	chart.addValueAxis(valueAxis);

	// GRAPHS
	// first graph -- Lite
	var graph = new AmCharts.AmGraph();
	graph.title = "Income";
	graph.labelText = "$ [[value]]";
	graph.valueField = "income";
	graph.type = "column";
	graph.lineAlpha = 0;
	graph.fillAlphas = 1;
	graph.lineColor = "#99CC33";
	chart.addGraph(graph);

	// second graph -- Standard
	graph = new AmCharts.AmGraph();
	graph.title = "Expense";
	graph.labelText = "$ [[value]]";
	graph.valueField = "expense";
	graph.type = "column";
	graph.lineAlpha = 0;
	graph.fillAlphas = 1;
	graph.lineColor = "#FF9D09";
	chart.addGraph(graph);
	
	chart.depth3D = 20;
	chart.angle = 40;

	// LEGEND
	var legend = new AmCharts.AmLegend();
	chart.addLegend(legend);

	// WRITE
	chart.write("divIncomeExpenseByBranch");
}

function renderTableLoanSummary(dataLoanAll){
	if(dataLoanAll=='') return;
	gDataLoanAll = eval(dataLoanAll);
	
	var str = "<table class=\"table table-bordered table-striped\">"+
	"    <thead>"+
	"        <tr>"+
	"            <th width=\"50px\">"+
	"            </th>"+
	"            <th width=\"100px\">"+
	"                Number of Loan"+
	"            </th>"+
	"            <th>"+
	"                Amount in USD (1 USD = 4,000 KHR)"+
	"            </th>"+
	"        </tr>"+
	"    </thead>"+
	"    <tbody>";
	for(var i=0; i<gDataLoanAll.length; i++){
	str+=	"<tr>"+
			"<td><b><i>"+	gDataLoanAll[i]["label"]+ "</b></i></td>"+
			"<td>"+ addCommas(gDataLoanAll[i]["loan"]) +"</td><td> $ "+ addCommas(gDataLoanAll[i]["amount"]) +"</td>"+
			"</tr>";
	}
	str +=	"    </tbody>"+
	"</table>";
	$('#divLoanSummary').html(str);
}

function renderColumnLoanAmountAll(data) {
	$('#divLoanSummaryChart').html('');
	var chartData = eval(data);

	// SERIAL CHART
	chart = new AmCharts.AmSerialChart();
	chart.dataProvider = chartData;
	chart.categoryField = "label";
	//chart.startDuration = 1;

	// AXES
	// category
	var categoryAxis = chart.categoryAxis;
	categoryAxis.gridAlpha = 0.1;
	categoryAxis.axisAlpha = 0;
	//categoryAxis.labelRotation = 45;
	categoryAxis.gridPosition = "start";

	// value
	var valueAxis = new AmCharts.ValueAxis();
	valueAxis.stackType = "none";
	valueAxis.gridAlpha = 0.1;
	valueAxis.axisAlpha = 0;
	valueAxis.title = "Amount in USD";
	chart.addValueAxis(valueAxis);

	// GRAPHS
	// first graph -- Lite
	var graph = new AmCharts.AmGraph();
	graph.title = "amount";
	graph.labelText = "$ [[value]]";
	graph.valueField = "amount";
	graph.type = "column";
	graph.lineAlpha = 0;
	graph.fillAlphas = 1;
	graph.lineColor = "#FF9D09";
	graph.showAllValueLabels = true;
	chart.addGraph(graph);

	chart.depth3D = 20;
	chart.angle = 60;
	
	// WRITE
	chart.write("divLoanSummaryChart");
}

function addCommas(nStr)
{
	nStr += '';
	x = nStr.split('.');
	x1 = x[0];
	x2 = x.length > 1 ? '.' + x[1] : '';
	var rgx = /(\d+)(\d{3})/;
	while (rgx.test(x1)) {
		x1 = x1.replace(rgx, '$1' + ',' + '$2');
	}
	return x1 + x2;
}

function renderChartActiveAcc(data){
	renderColumnActiveAcc(data);
}

function renderChartActiveAcc(dataDetail, data) {
	renderStackColumnActiveAcc(data);
	//renderColumnActiveAcc(data);
	
	renderTableHorizontal(data, dataDetail, 'divNumActiveAcc');
}

function renderStackColumnActiveAcc(data) {
	$('#divNumActiveAccDetail').html('');
	var chartData = eval(data);
	gDataActionByDay = chartData;

	// SERIAL CHART
	chart = new AmCharts.AmSerialChart();
	chart.dataProvider = chartData;
	chart.categoryField = "label";
	//chart.startDuration = 1;

	// AXES
	// category
	var categoryAxis = chart.categoryAxis;
	categoryAxis.gridAlpha = 0.1;
	categoryAxis.axisAlpha = 0;
	//categoryAxis.labelRotation = 45;
	categoryAxis.gridPosition = "start";

	// value
	var valueAxis = new AmCharts.ValueAxis();
	valueAxis.stackType = "regular";
	valueAxis.gridAlpha = 0.1;
	valueAxis.axisAlpha = 0;
	valueAxis.title = "Count clients";
	chart.addValueAxis(valueAxis);

	// GRAPHS
	// first graph -- Lite
	var graph = new AmCharts.AmGraph();
	graph.title = "New clients";
	graph.labelText = "[[value]]";
	graph.valueField = "outside";
	graph.type = "column";
	graph.lineAlpha = 0;
	graph.fillAlphas = 1;
	graph.lineColor = "#99CC33";
	chart.addGraph(graph);

	// second graph -- Standard
	graph = new AmCharts.AmGraph();
	graph.title = "Existing clients";
	graph.labelText = "[[value]]";
	graph.valueField = "kredit";
	graph.type = "column";
	graph.lineAlpha = 0;
	graph.fillAlphas = 1;
	graph.lineColor = "#FF9D09";
	chart.addGraph(graph);

	chart.depth3D = 10;
	chart.angle = 70;

	// LEGEND
	var legend = new AmCharts.AmLegend();
	chart.addLegend(legend);

	// WRITE
	chart.write("divNumActiveAccDetail");
}

function renderColumnActiveAcc(data) {
	$('#divNumActiveAcc').html('');
	var chartData = eval(data);
	gDataLoanByBranch = chartData;

	// SERIAL CHART
	chart = new AmCharts.AmSerialChart();
	chart.dataProvider = chartData;
	chart.categoryField = "label";
	//chart.startDuration = 1;

	// AXES
	// category
	var categoryAxis = chart.categoryAxis;
	categoryAxis.gridAlpha = 0.1;
	categoryAxis.axisAlpha = 0;
	//categoryAxis.labelRotation = 45;
	categoryAxis.gridPosition = "start";

	// value
	var valueAxis = new AmCharts.ValueAxis();
	valueAxis.stackType = "regular";
	valueAxis.gridAlpha = 0.1;
	valueAxis.axisAlpha = 0;
	valueAxis.title = "Count";
	chart.addValueAxis(valueAxis);

	// GRAPHS
	// first graph -- Lite
	var graph = new AmCharts.AmGraph();
	graph.title = "Lite";
	graph.labelText = "[[value]]";
	graph.valueField = "loan";
	graph.type = "column";
	graph.lineAlpha = 0;
	graph.fillAlphas = 1;
	graph.lineColor = "#99CC33";
	graph.showAllValueLabels = true;
	chart.addGraph(graph);

	chart.depth3D = 20;
	chart.angle = 60;

	// WRITE
	chart.write("divNumActiveAcc");
}

function renderTableHorizontal(dataCln, dataAmt, div_id){
	if(dataAmt == null || dataAmt=='') return;
	var gdataCln = eval(dataCln);
	var gdataAmt = eval(dataAmt);
	
	var str = 	"<table class=\"table table-bordered table-striped\" >"+
				"    <thead>"+
				"        <tr>"+
				"            <th width=\"20px\"># Active Accounts</th>";
	
	for(var i=0; i<gdataAmt.length; i++){
		str += "<th colspan=\"2\">"+ gdataAmt[i]["label"] +"</th>";
		
	}
	str += 		"        </tr>"+
				"    </thead>"+
				"    <tbody>";
	
	var label = ['New clients', 'Existing clients', 'Total'];
	var labelValue = ['outside', 'kredit', 'total'];
	
	for(var j=0; j<3; j++){
		str +=	"<tr>"+
			"<td><b><i>"+ label[j]	+ "</b></i></td>";
		for(var i=0; i<gdataAmt.length; i++){
			str +=	"<td width=\"20px\">"+ addCommas(gdataCln[i][labelValue[j]]) +"</td>";
			str +=	"<td width=\"60px\">$"+ addCommas(gdataAmt[i][labelValue[j]]) +"</td>";
		}
		str += "</tr>";
	}
	str +=	"    </tbody>"+
	"</table>";
	$('#' + div_id).html(str);
}