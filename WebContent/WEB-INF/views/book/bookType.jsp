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
		function newBookType(){
			$('#dlg').dialog('open').dialog('setTitle','添加书籍类型');
			$('#fm').form('clear');
		//	$('#filedBookID').attr('disabled', true);
			document.getElementById("fieldBookTypeID").style.visibility="hidden";
			url = 'bookType/addBookType';
		}
		function editBookType(){
			var row = $('#dg').datagrid('getSelected');
			// alert(row.userId);
			if (row){
				$('#dlg').dialog('open').dialog('setTitle','编辑书籍类型');
				$('#fm').form('load',row);
				url ='bookType/updateBookType';
			}
		}
		function saveBookType(){
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
		function removeBookType(){
			var row = $('#dg').datagrid('getSelected');
			 //alert(row.id);
			if (row){
				$.messager.confirm('Confirm','你确定要删除图书类型吗?',function(r){
					if (r){
						$.post('bookType/removeBookType',{bookTypeId:row.bookTypeId},function(result){
							if (result.success){
								$.messager.show({
						title:'Info',
						msg:result.msg,
						showType:'fade',
						style:{
							right:'',
							bottom:''
							}
						});
								$('#dg').datagrid('reload');	// reload the user data
							} else {
								$.messager.show({	// show error message
									title: 'Error',
									msg: result.msg
								});
							}
						},'json');
					}
				});
			}
		}
		function doSearch(){
			$('#dg').datagrid('load',{
				queryBookTypeName: $('#searchBox').val()
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
			url="bookType/listBookType"
			toolbar="#toolbar" pagination="true"
			rownumbers="true" fitColumns="true" singleSelect="true">
		<thead>
			<tr>
				<th field="bookTypeId" width="50">类型ID</th>
				<th field="bookTypeCode" width="50">类型编码</th>
				<th field="bookTypeName" width="50">类型名称</th>
			</tr>
		</thead>
	</table>
	<div id="toolbar">
		<br>
		<%if (securityUtil.havePermission("bookType/add")) {%>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newBookType()">新建</a>
		<%}%>
		<%if (securityUtil.havePermission("bookType/edit")) {%>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editBookType()">编辑</a>
		<%}%>
		<%if (securityUtil.havePermission("bookType/delete")) {%>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeBookType()">删除</a>
		<%}%>
		<br>
		<div>
			<br>
			<span>书类型:</span>
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
		<tr id="fieldBookTypeID">
				<th>类型ID:</th>
				<td><input name="bookTypeId" readonly="readonly"></td>
		</tr> 
		<tr>
				<th>类型编码:</th>
				<td><input name="bookTypeCode" class="easyui-validatebox" required="true" ></td>
		</tr>
		<tr>
				<th>类型名:</th>
				<td><input name="bookTypeName" class="easyui-validatebox" required="true" ></td>
		</tr>
	
		</table>
		</form>
		
	</div>
	<div id="dlg-buttons">
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveBookType()">保存</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
	</div>
</body>
</html>