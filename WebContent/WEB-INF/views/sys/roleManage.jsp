<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.yang.bishe.util.SecurityUtil"%>
<%
	String contextPath = request.getContextPath();
	SecurityUtil securityUtil = new SecurityUtil(session);
%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/common/base.jsp"%>
<script type="text/javascript">
	var grid;
	function addFun() {
		var dialog = parent.sy.modalDialog({
			title : '添加角色信息',
			url : 'role/goRoleForm',
			buttons : [ {
				text : '添加',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
	};
	function showFun(id) {
		var dialog = parent.sy.modalDialog({
			title : '查看角色信息',
			url : 'role/goRoleForm?roleId=' + id
		});
	};
	function editFun(id) {
		var dialog = parent.sy.modalDialog({
			title : '编辑角色信息',
			url : 'role/goRoleForm?roleId=' + id,
			buttons : [ {
				text : '编辑',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
	};
	function removeFun(id) {
		parent.$.messager.confirm('询问', '您确定要删除此记录？', function(r) {
			if (r) {
				$.post('role/deleteRole', {
					roleId : id
				}, function() {
					grid.datagrid('reload');
				}, 'json');
			}
		});
	};
	function grantFun(id) {
		var dialog = parent.sy.modalDialog({
			title : '角色授权',
			url : 'role/goRoleGrant?roleId=' + id,
			buttons : [ {
				text : '授权',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
	};
	$(function() {
		grid = $('#grid').datagrid({
			title : '',
			url : 'role/getRole',
			rownumbers : true,
			singleSelect : true,
			idField : 'roleId',
			columns : [ [{
				width : '100',
				title : '角色名称',
				field : 'roleName',
			},{
				width : '300',
				title : '角色描述',
				field : 'roleDescription'
			}, {
				title : '操作',
				field : 'action',
				width : '120',
				formatter : function(value, row) {
					var str = '';
					<%if (securityUtil.havePermission("role/getById")) {%>
					str += sy.formatString('<img class="iconImg ext-icon-note" title="查看" onclick="showFun(\'{0}\');"/>', row.roleId);
					<%}%>
					<%if (securityUtil.havePermission("role/update")) {%>
					str += sy.formatString('<img class="iconImg ext-icon-note_edit" title="编辑" onclick="editFun(\'{0}\');"/>', row.roleId);
					<%}%>
					<%if (securityUtil.havePermission("role/grant")) {%>
					str += sy.formatString('<img class="iconImg ext-icon-key" title="授权" onclick="grantFun(\'{0}\');"/>', row.roleId);
					<%}%>
					<%if (securityUtil.havePermission("role/delete")) {%>
					str += sy.formatString('<img class="iconImg ext-icon-note_delete" title="删除" onclick="removeFun(\'{0}\');"/>', row.roleId);
					<%}%>
					return str;
				}
			} ] ],
			toolbar : '#toolbar',
			onBeforeLoad : function(param) {
				parent.$.messager.progress({
					text : '数据加载中....'
				});
			},
			onLoadSuccess : function(data) {
				$('.iconImg').attr('src', sy.pixel_0);
				parent.$.messager.progress('close');
			}
		});
	});
</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div id="toolbar" style="display: none;">
		<table>
			<tr>
				<%if (securityUtil.havePermission("role/add")) {%>
				<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_add',plain:true" onclick="addFun();">添加</a></td>
				<%}%>
				<td><div class="datagrid-btn-separator"></div></td>
			</tr>
		</table>
	</div>
	<div data-options="region:'center',fit:true,border:false">
		<table id="grid" data-options="fit:true,border:false"></table>
	</div>
</body>
</html>