<%@page import="com.yang.bishe.util.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/common/base.jsp"%> 
	<script type="text/javascript">
		//分类排行
		function doSearch(){
			if($('#bookTypeId').combobox('getValue')==''){
				$.messager.alert({	
					title: 'Error',
					msg: '请先选择书类型'
				});
			}else{
				$('#dg').datagrid({
	                url: 'borrowInfo/statistics', 
	                queryParams:{bookTypeId: $('#bookTypeId').combobox('getValue'),startDate:$('#start').datebox('getValue'),endDate:$('#end').datebox('getValue')}
	        	});
			}
		}
		//总排行
		function doSearchOverall(){
			$('#dg').datagrid({
                url: 'borrowInfo/statistics', 
                queryParams:{overall:'overall',startDate:$('#start').datebox('getValue'),endDate:$('#end').datebox('getValue')}
        	});
		}
		//日期区间设置格式
		function mydateformatter(date){
				var y = date.getFullYear();
				var m = date.getMonth()+1;
				var d = date.getDate();
				return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
			}
		function mydateparser(s){
			if (!s) return new Date();
			var ss = (s.split('-'));
			var y = parseInt(ss[0],10);
			var m = parseInt(ss[1],10);
			var d = parseInt(ss[2],10);
			if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
				return new Date(y,m-1,d);
			} else {
				return new Date();
			}
		}
		$.extend($.fn.validatebox.defaults.rules,{  
			 dateValid : {  
			        validator : function(value,param) { //参数value为当前文本框的值，也就是endDate  
			            startTime = $(param[0]).datetimebox('getValue');//获取起始时间的值  
			           // var start = $.fn.datebox.defaults.parser(startTime);  
			          //  var end = $.fn.datebox.defaults.parser(value);  
			            varify = value > startTime;
			            return varify;  
			        },  
			        message : '结束时间要大于开始时间!'  
			    }  
		});  
	</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<table id="dg" title="借阅排行" class="easyui-datagrid" style="width:100%;height:100%;"
			toolbar="#toolbar" pagination="false" 
			rownumbers="true" fitColumns="true" singleSelect="true">
		<thead>
			<tr>
				<th field="borrowNum" width="60">借阅次数</th>
				<th field="bookName" width="60">书名</th>
				<th field="author" width="55">作者</th>
				<th field="iSBN" width="45">ISNB</th>
				<th field="publisher" width="60">出版社</th>
				<th field="publishYear" width="60">出版年</th>
				<th field="CallNumber" width="50">索书号</th>
			</tr>
		</thead>
	</table>
	<div id="toolbar" style="height:70px;">
		<div>
			<table>
				<tr>
					<td>&nbsp&nbsp&nbsp&nbsp类别排行:</td>
					<td>&nbsp&nbsp&nbsp&nbsp书类别:</td>
					<td><select id="bookTypeId" class="easyui-combobox" data-options="editable:false,valueField:'bookTypeId',textField:'bookTypeName',url:'bookType/getBookType',panelHeight:'auto'" style="width:180px;"></select></td>
					<td><a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search"  onclick="doSearch()">Search</a></td>
				</tr>
				<tr>
					<td>&nbsp&nbsp&nbsp&nbsp总排行：</td>
					<td>&nbsp&nbsp&nbsp&nbsp<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search"  onclick="doSearchOverall()">查询</a></td>
					<td></td>
					<td>&nbsp&nbsp&nbsp&nbsp查询日期区间(可选)</td>
				 	<td><input id="start" class="easyui-datebox" data-options="formatter:mydateformatter,parser:mydateparser,editable:false"></input>——</td>
				 	<td><input id="end" class="easyui-datebox" data-options="formatter:mydateformatter,parser:mydateparser,editable:false" validType="dateValid['#start']"></input></td>
					<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom_out',plain:true" onclick="$('#start').datebox('setValue','');$('#end').datebox('setValue','');$('#dg').datagrid('load',{});">重置过滤</a></td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>