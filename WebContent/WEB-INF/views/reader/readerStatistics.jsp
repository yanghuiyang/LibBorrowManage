<%@page import="com.yang.bishe.util.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/common/base.jsp"%> 
	<script type="text/javascript">
	
	function doSearch(){
		$('#dg').datagrid('loadData', { total: 0, rows: [] });
		$('#dg').datagrid('options').url="readerType/statistics";
		$('#dg').datagrid('load',{
				readerTypeId:$('#state').combobox('getValue')}
			);
	}
		
	</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<table id="dg" class="easyui-datagrid" style="width:100%;height:100%;"
			toolbar="#toolbar" pagination="false" 
			rownumbers="true" fitColumns="true" singleSelect="true">
		<thead>
			<tr>
				<th field="readerTypeName" width="60">读者类型名</th>
				<th field="readerNum" width="55">读者人数</th>
				<th field="nowBorrowNum" width="45">当前借阅数</th>
				<th field="historyBorrowNum" width="60">历史总借阅数</th>
			</tr>
		</thead>
	</table>
	<div id="toolbar" style="height:45px;padding:5px">
		<br>
		<tr>
			<td>&nbsp&nbsp&nbsp检索条件：</td>
			<td>
				<select name="readerType" id="state" class="easyui-combobox" data-options="editable:false,valueField:'readerTypeId',textField:'readerTypeName',url:'readerType/getReaderType',panelHeight:'auto'" style="width: 155px;"></select>
			</td>
			<td></td>
			<td><a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">查询</a></td>
		<tr>
	</div>
</body>
</html>