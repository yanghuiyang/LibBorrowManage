<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.yang.bishe.util.SecurityUtil"%>
<%
	SecurityUtil securityUtil = new SecurityUtil(session);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="/common/base.jsp"%> <%--前面加了/就是代表绝对路径，即包含了localhost+端口号+项目名 --%>
	<title>jQuery EasyUI CRUD Demo</title>
	<script type="text/javascript">
		var url;
		function newUser(){
			$('#dlg').dialog('open').dialog('setTitle','新建用户');
			$('#fm').form('clear');
			url = 'user/addUser';
		}
		function editUser(){
			var row = $('#dg').datagrid('getSelected');
			// alert(row.userId);
			document.getElementById("userIdform").style.visibility="hidden";
			if (row){
				$('#fm').form('clear');
				$('#dlg').dialog('open').dialog('setTitle','编辑用户');
				$('#fm').form('load',row);
				//url = 'user/updateUser?id='+row.userId;
				url ='user/updateUser';
			}
		}
		function saveUser(){
			$('#fm').form('submit',{
				//url:'user/addUser',
				url: url,
				onSubmit: function(){
					return $(this).form('validate');
				},
				success: function(result){
					var result = eval('('+result+')');
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
						$('#dlg').dialog('close');		// close the dialog
						$('#dg').datagrid('reload');	// reload the user data
					} else {
						$.messager.show({
							title: 'Error',
							msg: result.msg
						});
					}
				}
			});
		}
		
		function userRole(){
			var row = $('#dg').datagrid('getSelected');
			// alert(row.role.roleName);
			if (row){
				$('#rolefm').form('clear');
				$('#roleDlg').dialog('open').dialog('setTitle','用户角色');
				$('#rolefm').form('load',row);
				if(row.role.roleName!=null){
					$('#formRole').combobox('setValue',row.role.roleId);
					}
			//	saveRole();
			}	
		}
		function saveRole(){
			if($('#formRole').combobox('getValue')){
				$.post('user/grantRole', sy.serializeObject($('#rolefm')), function(result) {
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
							$('#roleDlg').dialog('close');		// close the dialog
							$('#dg').datagrid('reload');	// reload the user data
					}
				}, 'json');
			}
		}
		
		function removeUser(){
			var row = $('#dg').datagrid('getSelected');
			 //alert(row.id);
			if (row){
				$.messager.confirm('Confirm','你确定要删除用户吗?',function(r){
					if (r){
						$.post('user/removeUser',{id:row.userId},function(result){
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
			queryUserId: $('#searchBox').val()
		});
}
	function clearSearch(){
		$('#searchBox').val('');
		$('#dg').datagrid('load',{});
	}
	function formatStatus(val,row){
		if(row.enable==0){
			return "不可用";
		}else if(row.enable==1)
			return "可用";
		else return val;
	}
	
	</script>
	<!--修改默认datagrid的行高  -->
	<style>.datagrid-cell{line-height:30px}</style> 
</head>
<body class="easyui-layout"  data-options="fit:true,border:false">
	<table id="dg" title="My Users" class="easyui-datagrid" data-options="fit:true,border:false"
			url="user/listUsers"
			toolbar="#toolbar" pagination="true"
			rownumbers="true" fitColumns="true" singleSelect="true">
		<thead>
			<tr>
				<th field="userId" width="50">用户账号</th>
				<th field="userName" width="50">用户名</th>
				
				<th field="enable" width="50" formatter="formatStatus">是否可用</th>
			</tr>
		</thead>
	</table>
	<div id="toolbar">
		<%if (securityUtil.havePermission("user/add")) {%>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">新建用户</a>
		<%}%>
		<%if (securityUtil.havePermission("user/update")) {%>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">编辑用户</a>
		<%}%>
		<%if (securityUtil.havePermission("user/role")) {%>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-mini-edit" plain="true" onclick="userRole()">用户角色</a>
		<%}%>
		<%if (securityUtil.havePermission("user/delete")) {%>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeUser()">删除用户</a>
		<%}%>
		<div>
			<span>用户账号</span>
			<input id="searchBox" style="line-height:26px;border:1px solid #ccc">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="doSearch()">Search</a>
			<td><a href="javascript:void(0);" class="easyui-linkbutton" iconCls="ext-icon-zoom_out" plain="true" onclick="clearSearch()">清空查询</a></td>
		</div>
	</div>
	
	<div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
			closed="true" buttons="#dlg-buttons">
		<form id="fm" method="post">
			<table cellpadding="5">
			<tr id="userIdform">
				<th>用户账号:</th>
				<td><input name="userId" class="easyui-validatebox" required="true" ></td>
			</tr>
			<tr>
				<th>用户名:</th>
				<td><input name="userName" class="easyui-validatebox" required="true"></td>
			</tr>
			<tr>
				<th>密码:</th>
				<td><input name="passWord" type="password"></input></td>
			</tr>
			<tr>
				<th>是否可用:</th>
				<td><select class="easyui-combobox" name="enable" editable="false" style="width:150px;">
    				<option value="0">不可用</option>
    				<option value="1">可用</option>
					</select></td>
			</tr>
			</table>
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUser()">Save</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">Cancel</a>
	</div>
	<!-- 用于角色的对话框 -->
	<div id="roleDlg" class="easyui-dialog" style="width:300px;height:200px;padding:10px 20px"
			closed="true" buttons="#roleDlg-buttons" modal="true">
		<form id="rolefm" method="post">
			<table cellpadding="5">
			<tr>
				<td><input name="userId"  type="hidden"></td>
			</tr>
			<tr>
				<th>用户角色:</th>
				<td><select name="role.roleId" id="formRole" class="easyui-combobox" data-options="required:true,editable:false,valueField:'roleId',textField:'roleName',url:'role/getRole',panelHeight:'auto'" style="width: 155px;"></select></td>
			</tr>
			</table>
		</form>
	</div>
	<div id="roleDlg-buttons">
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveRole()">Save</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#roleDlg').dialog('close')">Cancel</a>
	</div>
</body>
</html>