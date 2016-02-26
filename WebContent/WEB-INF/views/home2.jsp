<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ page import="com.yang.bishe.entity.User"%>
<%
	User sessionInfo = (User) session.getAttribute("sessionInfo");
%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/common/base.jsp"%> 
	<title>图书借阅管理系统</title>
	<script type="text/javascript">
	var centerTabs;
	$(function() {
		$('#treeMenu').tree({
			url: 'operation/getHomeMenu',
			onLoadSuccess:function(node, data){$(this).tree('collapseAll')},
			loadFilter: function(rows){
				return convert(rows);
			},
			onClick: function(node){
				if (node.url) {
					addTab(node.text,node.url,node.iconCls);
					}
				}
		});
		function convert(rows){
			function exists(rows, parentId){
				for(var i=0; i<rows.length; i++){
					if (rows[i].id == parentId) return true;
				}
				return false;
			}
			var nodes = [];
			// get the top level nodes
			for(var i=0; i<rows.length; i++){
				var row = rows[i];
				if (!exists(rows, row.parentId)){
					nodes.push({
						id:row.id,
						text:row.text,
						target:row.attributes.target,
						url:row.attributes.url,
						iconCls:row.iconCls
					});
				}
			}
			
			var toDo = [];
			for(var i=0; i<nodes.length; i++){
				toDo.push(nodes[i]);
			}
			while(toDo.length){
				var node = toDo.shift();	// the parent node
				// get the children nodes
				for(var i=0; i<rows.length; i++){
					var row = rows[i];
					if (row.parentId == node.id){
						var child = {id:row.id,text:row.text,target:row.attributes.target,url:row.attributes.url,iconCls:row.iconCls};
						if (node.children){
							node.children.push(child);
						} else {
							node.children = [child];
						}
						toDo.push(child);
					}
				}
			}
			return nodes;
		}
	
	centerTabs=$('#centerTabs').tabs({
		tools : [{
			text : '刷新',
			iconCls : 'ext-icon-arrow_refresh',
			handler : function() {
				var panel = centerTabs.tabs('getSelected').panel('panel');
				var frame = panel.find('iframe');
				try {
					if (frame.length > 0) {
						for (var i = 0; i < frame.length; i++) {
							frame[i].contentWindow.document.write('');
							frame[i].contentWindow.close();
							frame[i].src = frame[i].src;
						}
						if (navigator.userAgent.indexOf("MSIE") > 0) {// IE特有回收内存方法
							try {
								CollectGarbage();
							} catch (e) {
							}
						}
					}
				} catch (e) {
				}
			}
		}, {
			text : '关闭',
			iconCls : 'ext-icon-cross',
			handler : function() {
				var index = centerTabs.tabs('getTabIndex', centerTabs.tabs('getSelected'));
				var tab = centerTabs.tabs('getTab', index);
				if (tab.panel('options').closable) {
					centerTabs.tabs('close', index);
				} else {
					$.messager.alert('提示', '[' + tab.panel('options').title + ']不可以被关闭！', 'error');
				}
			}
		} ]
	});
	$('#passwordDialog').show().dialog({
		modal : true,
		closable : true,
		iconCls : 'ext-icon-lock_edit',
		buttons : [ {
			text : '修改',
			handler : function() {
				if ($('#passwordDialog form').form('validate')) {
					$.post('user/updatePwd', {
						'passWord' : $('#pwd').val()
					}, function(result) {
						if (result.success) {
							$.messager.alert('提示', '密码修改成功！', 'info');
							$('#passwordDialog').dialog('close');
						}
					}, 'json');
				}
			}
		} ],
		onOpen : function() {
			$('#passwordDialog form :input').val('');
		}
	}).dialog('close');
		
});	
	function addTab(title, url,iconCls){
		if ($('#centerTabs').tabs('exists', title)){
			$('#centerTabs').tabs('select', title);
		} else {
			var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
			$('#centerTabs').tabs('add',{
				title:title,
				content:content,
				closable:true,
				iconCls : iconCls,
			});
		}
	}

	</script>
</head>
<body class="easyui-layout">
<div data-options="region:'north',href:'<%=basePath%>common/north.jsp',border:false" style="height:50px;background:#B3DFDA;padding:10px">north region</div>	
	<div data-options="region:'west',split:true,title:'导航'" style="width:200px;padding:10px;">
		<div class="easyui-panel" style="padding:5px"data-options="fit:true,border:false">
			<ul id="treeMenu" class="easyui-tree"  animate="true"> </ul>
			
		</div>
	</div>
	
	
	<div data-options="region:'south',href:'<%=basePath%>common/south.jsp',border:false" style="height:50px;background:#A9FACD;padding:10px;">south region</div>
	<div data-options="region:'center',title:'Center'">
		<div id="centerTabs" class="easyui-tabs" data-options="fit:true,border:false,plain:true">
	</div>
	<div id="passwordDialog" title="修改密码" style="display: none;">
		<form method="post" class="form" onsubmit="return false;">
			<table class="table">
				<tr>
					<th>新密码</th>
					<td><input id="pwd" name="passWord" type="password" class="easyui-validatebox" data-options="required:true" /></td>
				</tr>
				<tr>
					<th>重复密码</th>
					<td><input type="password" class="easyui-validatebox" data-options="required:true,validType:'eqPwd[\'#pwd\']'" /></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>