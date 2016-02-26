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
	<div data-options="region:'north',href:'<%=basePath%>common/north.jsp',border:false" style="height:50px;background:#B3DFDA;padding:10px">north region</div>
	<div data-options="region:'west',split:true,title:'导航'" style="width:180px;padding:10px;">
		<div class="easyui-accordion" data-options="fit:true,border:false">
				<div title="图书借阅管理" data-options="selected:true,iconCls:'ext-icon-application_side_boxes'" style="padding:10px;">
					<div>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" style="width:130px" onclick="addTab('图书借阅','book/borrow')">图书借阅</a><br>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" style="width:130px" onclick="addTab('图书归还','book/return')">图书归还</a><br>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" style="width:130px" onclick="addTab('图书续借','book/renew')">图书续借</a><br>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" style="width:130px" onclick="addTab('ifeng','http://www.ifeng.com')">图书丢失（这里申报？罚款）</a><br>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" style="width:130px" onclick="addTab('ifeng','http://www.ifeng.com')">预借？</a><br>
					</div>
				</div>
				<div title="读者管理" data-options="iconCls:'ext-icon-group'" style="padding:10px;">
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" style="width:130px" onclick="addTab('读者信息管理','reader/goManageReader')">读者信息管理</a><br>
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" style="width:130px" onclick="addTab('读者类型管理','readerType/goReaderType')">读者类型管理</a><br>
				</div>
				<div title="图书管理" data-options="iconCls:'ext-icon-application_key'"style="padding:10px">
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" style="width:130px" onclick="addTab('图书基本信息管理','book/goManageBook')">图书基本信息管理</a><br>
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" style="width:130px" onclick="addTab('图书条码关联','booksn/goAddBookSN')">图书条码关联</a><br>
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" style="width:130px" onclick="addTab('图书条码管理','booksn/goManageBookSN')">图书条码管理</a><br>
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" style="width:130px" onclick="addTab('图书类型管理','bookType/goBookType')">图书类型</a><br>
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" style="width:130px" onclick="addTab('图书馆藏地','bookLocation/goBookLocation')">图书馆藏地</a><br>
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" style="width:130px" onclick="addTab('ifeng','http://www.ifeng.com')">图书报损？</a><br>
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" style="width:130px" onclick="addTab('ifeng','http://www.ifeng.com')"></a><br>
				</div>
				<div title="查询与统计分析" data-options="iconCls:'ext-icon-application_form_magnify'"style="padding:10px">
					<div>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" style="background:#D3DFDA;width:130px;margin:5px;" onclick="addTab('借阅排行','open/goStatistics')">借阅排行</a><br>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" style="background:#D3DFDA;width:130px;margin:5px;" onclick="addTab('书籍查询','open/gofindBook')">书籍查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" style="background:#D3DFDA;width:130px;margin:5px;" onclick="addTab('读者分类统计','reader/goReaderStatistics')">读者分类统计</a>
					</div>
				</div>
				<div title="充值管理" data-options="iconCls:'ext-icon-application_form_magnify'"style="padding:10px">
					<div>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" style="background:#D3DFDA;width:130px;margin:5px;" onclick="addTab('缴纳罚款','reader/goPayFine')">缴纳罚款</a><br>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" style="background:#D3DFDA;width:130px;margin:5px;" onclick="addTab('借阅排行','http://www.ifeng.com')">缴纳丢失图书罚款？</a><br>
					</div>
				</div>
				<div title="系统管理" data-options="iconCls:'ext-icon-application_view_tile'" style="padding:10px">
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" style="width:130px" onclick="addTab('用户管理','user/list')">UserCRUD</a>
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" style="width:130px" onclick="addTab('系统参数设置','readerType/goSysParameter')">系统参数设置</a>
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" style="width:130px" onclick="addTab('备份管理','backup/goBackup')">备份管理</a>
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" style="width:130px" onclick="addTab('备份管理','user/list')">初始化？</a>
				</div>
			</div>
	</div>
	
	
	<div data-options="region:'south',href:'<%=basePath%>common/south.jsp',border:false" style="height:50px;background:#A9FACD;padding:10px;">south region</div>
	<div data-options="region:'center',title:'Center'">
		<div id="centerTabs" class="easyui-tabs" data-options="fit:true,border:false,plain:true">
	</div>
</body>
</html>