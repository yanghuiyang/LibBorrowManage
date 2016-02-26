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
		function newBookLocation(){
			$('#dlg').dialog('open').dialog('setTitle','添加馆藏地');
			$('#fm').form('clear');
		//	$('#filedBookID').attr('disabled', true);
			document.getElementById("fieldBookLocationID").style.visibility="hidden";
			url = 'bookLocation/addBookLocation';
		}
		function editBookLocation(){
			var row = $('#dg').datagrid('getSelected');
			// alert(row.userId);
			if (row){
				$('#dlg').dialog('open').dialog('setTitle','编辑馆藏地');
				$('#fm').form('load',row);
				url ='bookLocation/updateBookLocation';
			}
		}
		function saveBookLocation(){
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
			url="bookLocation/getBookLocation"
			toolbar="#toolbar" pagination="false"
			rownumbers="true" fitColumns="true" singleSelect="true">
		<thead>
			<tr>
				<th field="locationId" width="50">类型ID</th>
				<th field="locationName" width="50">馆藏地名称</th>
			</tr>
		</thead>
	</table>
	<div id="toolbar">
		<br>
		<%if (securityUtil.havePermission("bookLocation/add")) {%>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newBookLocation()">新建</a>
		<%}%>
		<%if (securityUtil.havePermission("bookLocation/edit")) {%>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editBookLocation()">编辑</a>
		<%}%>
		<%if (securityUtil.havePermission("bookLocation/edit")) {%>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeBookLocation()">删除</a>
		<%}%>
		<br>
	</div>
	<br>
	<div id="dlg" class="easyui-dialog" style="width:300px;height:200px;padding:10px 20px"
			closed="true" buttons="#dlg-buttons">
		<form id="fm" method="post" novalidate>
		<table cellpadding="5"> 
			<div class="fitem" id="fieldBookLocationID">
				<label>馆藏地ID:</label>
				<input name="locationId" readonly="readonly">
			</div>
			<div class="fitem">
				<label>馆藏地名:</label>
				<input name="locationName" class="easyui-validatebox" required="true" >
			</div>
	
		</table>
		</form>
		
	</div>
	<div id="dlg-buttons">
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveBookLocation()">Save</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">Cancel</a>
	</div>
</body>
</html>