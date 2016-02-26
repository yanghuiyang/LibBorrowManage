<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
%>
<%
	String roleId = request.getParameter("roleId");
	if (roleId == null) {
		roleId = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/common/base.jsp"%>
<script type="text/javascript">
	var submitForm = function($dialog, $grid, $pjq) {
		if ($('form').form('validate')) {
			var url;
			if ($('#roleId').val().length > 0) {
				url ='role/updateRole';
			} else {
				url = 'role/savaRole';
			}
			$.post(url, sy.serializeObject($('form')), function(result) {
				if (result.success) {
					$grid.datagrid('load');
					$dialog.dialog('destroy');
				} else {
					$pjq.messager.alert('提示', result.msg, 'error');
				}
			}, 'json');
		}
	};
	$(function() {
		if ( $('#roleId').val().length > 0) {
			parent.$.messager.progress({
				text : '数据加载中....'
			});
			$.post('role/getRoleById', {
				roleId : $('#roleId').val()
			}, function(result) {
				if (result.roleId != undefined) {
					$('form').form('load', {
						'roleId' : result.roleId,
						'roleName' : result.roleName,
						'roleDescription' : result.roleDescription
					});
				}
				parent.$.messager.progress('close');
			}, 'json');
		}
	});
</script>
</head>
<body>
	<form method="post" class="form">
		<fieldset>
			<legend>角色基本信息</legend>
			<table class="table" style="width: 100%;">
				<tr>
					<th>编号</th>
					<td><input name="roleId" id="roleId" value="<%=roleId%>" readonly="readonly" /></td>
					<th>角色名称</th>
					<td><input name="roleName" class="easyui-validatebox" data-options="required:true" /></td>
				</tr>
				<tr>
					<th>角色描述</th>
					<td><textarea name="roleDescription"></textarea></td>
				</tr>
			</table>
		</fieldset>
	</form>
</body>
</html>