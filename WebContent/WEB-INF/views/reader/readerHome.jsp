<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ page import="com.yang.bishe.entity.Reader"%>
<%
	Reader sessionInfo = (Reader) session.getAttribute("sessionInfo");
%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/common/base.jsp"%> 
	<title>图书借阅管理系统</title>
	<script type="text/javascript">
		function addTab(title, url){
		if ($('#centerTabs').tabs('exists', title)){
			$('#centerTabs').tabs('select', title);
		} else {
			var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
			$('#centerTabs').tabs('add',{
				title:title,
				content:content,
				closable:true
			});
		}
	}
	</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',href:'<%=basePath%>common/ReaderNorth.jsp',border:false" style="height:50px;background:#B3DFDA;padding:10px">north region</div>
	<div data-options="region:'west',split:true,title:'导航'" style="width:180px;padding:10px;">
		<div class="easyui-accordion" data-options="fit:true,border:false">
				<div title="借阅情况" data-options="iconCls:'ext-icon-group'" style="padding:10px;">
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" style="width:130px" onclick="addTab('读者管理','reader/goManageReader')">当前借阅</a><br>
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" style="width:130px" onclick="addTab('读者类型','readerType/goReaderType')">历史借阅</a><br>
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" style="width:130px" onclick="addTab('读者类型','readerType/goReaderType')">超期信息</a><br>
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" style="width:130px" onclick="addTab('读者类型','readerType/goReaderType')">欠款记录</a><br>
				</div>
				<div title="查询与统计分析" data-options="iconCls:'ext-icon-application_form_magnify'"style="padding:10px">
					<div>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" style="background:#D3DFDA;width:130px;margin:5px;" onclick="addTab('借阅排行','http://www.ifeng.com')">借阅排行</a><br>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" style="background:#D3DFDA;width:130px;margin:5px;" onclick="addTab('书籍查询','reader/gofindBook')">书籍查询</a>
					</div>
				</div>
					<div title="信息管理" data-options="iconCls:'ext-icon-application_form_magnify'"style="padding:10px">
					<div>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" style="background:#D3DFDA;width:130px;margin:5px;" onclick="addTab('借阅排行','http://www.ifeng.com')">修改密码</a><br>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" style="background:#D3DFDA;width:130px;margin:5px;" onclick="addTab('书籍查询','reader/gofindBook')">个人资料</a>
					</div>
				</div>
		</div>
	</div>
	
	
	<div data-options="region:'south',href:'<%=basePath%>common/south.jsp',border:false" style="height:50px;background:#A9FACD;padding:10px;">south region</div>
	<div data-options="region:'center',title:'Center'">
		<div id="centerTabs" class="easyui-tabs" data-options="fit:true,border:false,plain:true">
	</div>
</body>
</html>