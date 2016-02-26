<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.yang.bishe.util.SecurityUtil"%>
<%
	SecurityUtil securityUtil = new SecurityUtil(session);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="/common/base.jsp"%>
	<script type="text/javascript">
		<!--未考虑删除类型级联问题 ->
		var url;
		function newReaderType(){
			$('#dlg').dialog('open').dialog('setTitle','添加读者类型');
			$('#fm').form('clear');
		//	$('#filedBookID').attr('disabled', true);
			document.getElementById("fieldReaderTypeID").style.visibility="hidden";
			url = 'readerType/addReaderType';
		}
		function editReaderType(){
			var row = $('#dg').datagrid('getSelected');
			// alert(row.userId);
			if (row){
				$('#dlg').dialog('open').dialog('setTitle','编辑读者类型');
				$('#fm').form('load',row);
				url ='readerType/updateReaderType';
			}
		}
		function saveReaderType(){
			$.post(url, sy.serializeObject($('#fm')), function(result) {
				if (result.success) {
						$.messager.show({
						title:'Info',
						msg:result.msg,
						showType:'fade',
						style:{
							right:'',
							bottom:''
							}
						});
						$('#dlg').dialog('close');		// close the dialog
						$('#dg').datagrid('reload');	// reload the user data
				} else {
					$.messager.show({
						title: 'Error',
						msg: result.msg
					});
				}
			}, 'json');
		}
		function doSearch(){
			$('#dg').datagrid('load',{
				queryReaderTypeName: $('#searchBox').val()
		});
}
	function clearSearch(){
		$('#searchBox').val('');
		$('#dg').datagrid('load',{});
	}
	
	</script>
	<!--修改默认datagrid的行高  -->
	<style>.datagrid-cell{line-height:25px}</style>
</head>
<body class="easyui-layout"  data-options="fit:true,border:false">
	<table id="dg" class="easyui-datagrid" data-options="fit:true,border:false"
			url="readerType/listReaderType"
			toolbar="#toolbar" pagination="true"
			rownumbers="true" fitColumns="true" singleSelect="true">
		<thead>
			<tr>
				<th field="readerTypeId" width="50">类型ID</th>
				<th field="readerTypeName" width="50">类型名称</th>
			</tr>
		</thead>
	</table>
	<div id="toolbar">
		<br>
		<%if (securityUtil.havePermission("readerType/add")) {%>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newReaderType()">新建</a>
		<%}%>
		<%if (securityUtil.havePermission("readerType/edit")) {%>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editReaderType()">编辑</a>
		<%}%>
		<%if (securityUtil.havePermission("readerType/edit")) {%>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeReaderType()">删除</a>
		<%}%>
		<br>
		<div>
			<br>
			<span>读者类型:</span>
			<input id="searchBox" class="easyui-textbox" style="line-height:25px;border:1px solid #ccc">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="doSearch()">Search</a>
			<td><a href="javascript:void(0);" class="easyui-linkbutton" iconCls="ext-icon-zoom_out" plain="true" onclick="clearSearch()">清空查询</a></td>
		</div>
	</div>
	<br>
	<div id="dlg" class="easyui-dialog" style="width:300px;height:350px;padding:10px 20px"
			closed="true" buttons="#dlg-buttons">
		<form id="fm" method="post" novalidate>
		<table cellpadding="5"> 
		<div class="fitem" id="fieldReaderTypeID">
				<label>类型ID:</label>
				<input name="readerTypeId" readonly="readonly">
			</div> 
			<div class="fitem">
				<label>类型名:</label>
				<input name="readerTypeName" class="easyui-validatebox" required="true" >
			</div>
	
		</table>
		</form>
		
	</div>
	<div id="dlg-buttons">
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveReaderType()">Save</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">Cancel</a>
	</div>
</body>
</html>