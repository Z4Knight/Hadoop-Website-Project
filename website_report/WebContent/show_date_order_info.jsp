<%@ page language="java"    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style type="text/css">
	.top1{
		float:left;
		border: 5px solid #e2e1dd; 
		padding: 10px;
		background:#23aeac;
		width:340px;
		height:300x;
		color:white;
		display: block;
	    text-decoration: none;
   		font-size: 1.3em;
   		line-height:130%;
   		
	}
	.select{
		background:#23aeac;
		color:white;
	    text-decoration: none;
   		font-size: 1.3em;	
	}
</style>
</head>
<body>
	<!--  
<div>
<span class="select" >选择时间对下列信息有效</span>
  <select class="select"  id="selectTime" onchange="selectDate()">
    <option value ="null">请选择时间</option>
    <option value="2017-06-19">2017-06-19</option>
    <option value="2017-06-20">2017-06-20</option>
    <option value="2017-06-21">2017-06-21</option>
    <option value="2017-06-22">2017-06-22</option>
    <option value="2017-06-23">2017-06-23</option>
</select>
</div>
	-->
<div class="top1">
	<span class="top-span">订单个数</span>
	<br />
	<span class="top-span" id="order-nums"></span>
</div>
<div class="top1">
	<span class="top-span">支付成功订单个数</span>
		<br />
	<span class="top-table" id="success-nums"></span>
</div>
<div class="top1">
	<span class="top-span">退款订单个数</span>
		<br />
	<span class="top-span" id="refund-nums"></span>
</div>
<div class="top1">
	<span class="top-span">订单总金额</span>
		<br />
	<span class="top-span" id="order-totalamounts"></span>
</div>
<div class="top1">
	<span class="top-span">当天订单金额</span>
	<br />
	<span class="top-span" id="order-todayamouts"></span>
</div>
<div class="top1">
	<span class="top-span">当天支付金额</span>
		<br />
	<span class="top-table" id="success-amounts"></span>
</div>
<div class="top1">
	<span class="top-span">当天退款金额</span>
		<br />
	<span class="top-span" id="refund-todayamouts"></span>
</div>
<div class="top1">
	<span class="top-span">订单退款总金额</span>
		<br />
	<span class="top-span" id="refund-totalamounts"></span>
</div>
<div id="mainBar"
		style="height: 500px; border: 1px solid #ccc; padding: 10px;"></div>
	<!--  
	<script src="js/echarts.js"></script>
	-->
	<script src="js/jquery.min.js"></script>
	<script type="text/javascript">
	
	$(function(){
		$.ajax({url:"/website_report/DateOrderServlet",type:"GET",success:function(msg){
		var data = msg.listData;
		var date = ar=msg.brand;
		$("#order-nums").text(date[0] + "\t" + data[0]).append("<br />")
		.append(date[1] + "\t" + data[9]).append("<br />")
		.append(date[2] + "\t" + data[15]).append("<br />")
		.append(date[3] + "\t" + data[21]).append("<br />")
		.append(date[4] + "\t" + data[30]).append("<br />");
		$("#success-nums").text(date[0] + "\t" + data[6]).append("<br />")
		.append(date[1] + "\t" + "0").append("<br />")
		.append(date[2] + "\t" + "0").append("<br />")
		.append(date[3] + "\t" + data[27]).append("<br />")
		.append(date[4] + "\t" + "0").append("<br />");
		
		$("#refund-nums").text(date[0] + "\t" + data[3]).append("<br />")
		.append(date[1] + "\t" + data[12]).append("<br />")
		.append(date[2] + "\t" + data[18]).append("<br />")
		.append(date[3] + "\t" + data[24]).append("<br />")
		.append(date[4] + "\t" + data[33]).append("<br />");
	
		$("#order-totalamounts").text(date[0] + "\t" + data[2]).append("<br />")
		.append(date[1] + "\t" + data[11]).append("<br />")
		.append(date[2] + "\t" + data[17]).append("<br />")
		.append(date[3] + "\t" + data[23]).append("<br />")
		.append(date[4] + "\t" + data[32]).append("<br />");
		
		
		
		
		
		$("#order-todayamouts").text(date[0] + "\t" + data[1]).append("<br />")
		.append(date[1] + "\t" + data[10]).append("<br />")
		.append(date[2] + "\t" + data[16]).append("<br />")
		.append(date[3] + "\t" + data[22]).append("<br />")
		.append(date[4] + "\t" + data[31]).append("<br />");
		
		$("#success-amounts").text(date[0] + "\t" + "0").append("<br />")
		.append(date[1] + "\t" + "0").append("<br />")
		.append(date[2] + "\t" + "0").append("<br />")
		.append(date[3] + "\t" + "0").append("<br />")
		.append(date[4] + "\t" + "0").append("<br />");
		
		
		
		$("#refund-todayamouts").text(date[0] + "\t" + data[1]).append("<br />")
		.append(date[1] + "\t" + data[10]).append("<br />")
		.append(date[2] + "\t" + data[16]).append("<br />")
		.append(date[3] + "\t" + data[22]).append("<br />")
		.append(date[4] + "\t" + data[31]).append("<br />");
		
		$("#refund-totalamounts").text(date[0] + "\t" + data[2]).append("<br />")
		.append(date[1] + "\t" + data[11]).append("<br />")
		.append(date[2] + "\t" + data[17]).append("<br />")
		.append(date[3] + "\t" + data[23]).append("<br />")
		.append(date[4] + "\t" + data[32]).append("<br />");
		}})
		
	})
			 <!--
	var myChart;
		var option;		
		require.config({
			paths : {
				echarts : './js'
			}
		});
		require(
				[
				//这里的'echarts'相当于'./js'  
				'echarts', 'echarts/chart/bar', 'echarts/chart/line','echarts/chart/pie',],
				//创建ECharts图表方法  
				function(ec) {
					//--- 折柱 ---  
					//基于准备好的dom,初始化echart图表  
					 myChart = ec.init(document.getElementById('mainBar'));
					//定义图表option  
					
				});
		
		var i=199;

		function test()
		{
			var ar= [1,1,1];
			var ar2=[1,1,1];
			var ardown=[1,1,1];
			var artotal=[1,1,1];
			$.ajax({url:"/website_report/HourlyServlet",type:"GET",success:function(msg){
				/* var xx=eval('('+msg+')');
				ar=xx.data1; */
				
				ar=msg.brand;
				ar2=msg.listData;
				ardown=msg.listData1;
				artotal=msg.listData2;
			
				//ar2=msg.listData1;
				//ar3=msg.listData2;
				option = {
						//标题，每个图表最多仅有一个标题控件，每个标题控件可设主副标题  
						title : {
							//主标题文本，'\n'指定换行  
							text : 'Hourly情况报表',
							//主标题文本超链接  
							// link : 'http://www.tqyb.com.cn/weatherLive/climateForecast/2014-01-26/157.html',
							//副标题文本，'\n'指定换行  
							// subtext : 'www.stepday.com',
							//副标题文本超链接  
							// sublink : 'http://www.stepday.com/myblog/?Echarts',
							//水平安放位置，默认为左侧，可选为：'center' | 'left' | 'right' | {number}（x坐标，单位px）  
							x : 'left',
							//垂直安放位置，默认为全图顶端，可选为：'top' | 'bottom' | 'center' | {number}（y坐标，单位px）  
							y : 'top'
						},
						//提示框，鼠标悬浮交互时的信息提示  
						tooltip : {
							//触发类型，默认（'item'）数据触发，可选为：'item' | 'axis'  
							trigger : 'item'
						},
						//图例，每个图表最多仅有一个图例  
						legend : {
							//显示策略，可选为：true（显示） | false（隐藏），默认值为true  
							show : true,
							//水平安放位置，默认为全图居中，可选为：'center' | 'left' | 'right' | {number}（x坐标，单位px）  
							x : 'center',
							//垂直安放位置，默认为全图顶端，可选为：'top' | 'bottom' | 'center' | {number}（y坐标，单位px）  
							y : 'top',
							//legend的data: 用于设置图例，data内的字符串数组需要与sereis数组内每一个series的name值对应  
							data : [ '每天每小时用户数','每天每小时会话数','每天每小时会话长度']
						},
						//工具箱，每个图表最多仅有一个工具箱  
						toolbox : {
							//显示策略，可选为：true（显示） | false（隐藏），默认值为false  
							show : true,
							//启用功能，目前支持feature，工具箱自定义功能回调处理  
							feature : {
								//辅助线标志  
								mark : {
									show : true
								},
								//dataZoom，框选区域缩放，自动与存在的dataZoom控件同步，分别是启用，缩放后退  
								dataZoom : {
									show : true,
									title : {
										dataZoom : '区域缩放',
										dataZoomReset : '区域缩放后退'
									}
								},
								//数据视图，打开数据视图，可设置更多属性,readOnly 默认数据视图为只读(即值为true)，可指定readOnly为false打开编辑功能  
								dataView : {
									show : true,
									readOnly : false
								},
								//magicType，动态类型切换，支持直角系下的折线图、柱状图、堆积、平铺转换  
								magicType : {
									show : true,
									type : [ 'line', 'bar','pie' ]
								},
								//restore，还原，复位原始图表  
								restore : {
									show : true
								},
								//saveAsImage，保存图片（IE8-不支持）,图片类型默认为'png'  
								saveAsImage : {
									show : true
								}
							}
						},
						//是否启用拖拽重计算特性，默认关闭(即值为false)  
						calculable : true,
						//直角坐标系中横轴数组，数组中每一项代表一条横轴坐标轴，仅有一条时可省略数值  
						//横轴通常为类目型，但条形图时则横轴为数值型，散点图时则横纵均为数值型  
						xAxis : [ {
							name : '时间段',
							//显示策略，可选为：true（显示） | false（隐藏），默认值为true  
							show : true,
							//坐标轴类型，横轴默认为类目型'category'  
							type : 'category',
							//类目型坐标轴文本标签数组，指定label内容。 数组项通常为文本，'\n'指定换行  
							data : ar
						} ],
						//直角坐标系中纵轴数组，数组中每一项代表一条纵轴坐标轴，仅有一条时可省略数值  
						//纵轴通常为数值型，但条形图时则纵轴为类目型  
						yAxis : [ {
							
							name : '数量',
							//显示策略，可选为：true（显示） | false（隐藏），默认值为true  
							show : true,
							//坐标轴类型，纵轴默认为数值型'value'  
							type : 'value',
							//分隔区域，默认不显示  
							splitArea : {
								show : true
							},
							date: ardown
						} ],

						//sereis的数据: 用于设置图表数据之用。series是一个对象嵌套的结构；对象内包含对象  
						series : [
								{
									//系列名称，如果启用legend，该值将被legend.data索引相关  
									name : '每天每小时用户数',
									//图表类型，必要参数！如为空或不支持类型，则该系列数据不被显示。  
									type : 'line',
									//系列中的数据内容数组，折线图以及柱状图时数组长度等于所使用类目轴文本标签数组axis.data的长度，并且他们间是一一对应的。数组项通常为数值  
									data :ar2,
									//系列中的数据标注内容  
									markPoint : {
										data : [ {
											type : 'max',
											name : '最大值'
										}, {
											type : 'min',
											name : '最小值'
										} ]
									},
									//系列中的数据标线内容  
									markLine : {
										data : [ {
											type : 'average',
											name : '平均值'
										} ]
									}
								
								},
								{
									//系列名称，如果启用legend，该值将被legend.data索引相关  
									name : '每天每小时会话数',
									//图表类型，必要参数！如为空或不支持类型，则该系列数据不被显示。  
									type : 'line',
									//系列中的数据内容数组，折线图以及柱状图时数组长度等于所使用类目轴文本标签数组axis.data的长度，并且他们间是一一对应的。数组项通常为数值  
									data :ardown,
									//系列中的数据标注内容  
									markPoint : {
										data : [ {
											type : 'max',
											name : '最大值'
										}, {
											type : 'min',
											name : '最小值'
										} ]
									},
									//系列中的数据标线内容  
									markLine : {
										data : [ {
											type : 'average',
											name : '平均值'
										} ]
									}
								
								},
								{
									//系列名称，如果启用legend，该值将被legend.data索引相关  
									name : '每天每小时会话长度',
									//图表类型，必要参数！如为空或不支持类型，则该系列数据不被显示。  
									type : 'line',
									//系列中的数据内容数组，折线图以及柱状图时数组长度等于所使用类目轴文本标签数组axis.data的长度，并且他们间是一一对应的。数组项通常为数值  
									data :artotal,
									//系列中的数据标注内容  
									markPoint : {
										data : [ {
											type : 'max',
											name : '最小值'
										}, {
											type : 'min',
											name : '最大值'
										} ]
									},
									//系列中的数据标线内容  
									markLine : {
										data : [ {
											type : 'average',
											name : '平均值'
										} ]
									}
								
								}
								]
					};

					
					myChart.setOption(option);
				
			}});
			

			//setTimeout("test()", 2000);
		}
		-->
		setTimeout("test()", 50);
	</script>
</body>
</html>