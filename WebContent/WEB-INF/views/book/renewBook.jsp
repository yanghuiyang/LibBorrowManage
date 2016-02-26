<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.yang.bishe.entity.User"%>
<%@ page import="com.yang.bishe.entity.Book"%>
<%@ page import="com.yang.bishe.entity.Reader"%>
<%@ page import="com.yang.bishe.util.SecurityUtil"%>
<%
	SecurityUtil securityUtil = new SecurityUtil(session);
	User sessionInfo = (User) session.getAttribute("sessionInfo");
%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/common/base.jsp"%> 
	<script type="text/javascript">
	var readerId;
		function doSearch(){
			//逻辑问题留存（防止多次按下归还阅按钮。 可利用disable 让ajax返回前不能按归还按钮）
			var dialog = parent.sy.modalDialog({
				title : '确认信息',
				url :'booksn/findBookBySN?booksBarCode='+$('#booksBarCode').val(), //显示归还的图书信息，
				buttons : [ {
					text : '续借',
					handler : function() {
						$.post('borrowInfo/renew',{booksBarCode:$('#booksBarCode').val()},function(result){
							if (result.success){
								$('#dg').datagrid('reload');	// reload the user data
								//每次归还后设置读者信息。列出当前读者借阅情况
								readerId=result.msg;
								searchReader();
								$.messager.show({	
									title: 'success',
									msg: '续借成功'
								});
								dialog.dialog('destroy');
							} else {
								$.messager.show({	// show error message
									title: 'Error',
									msg: result.msg
								});
								dialog.dialog('destroy');
							}
						},'json');
						
					}
				},{
					text : '取消',
					handler : function() {
						dialog.dialog('destroy');
					}
				} ]
			});
}
		function searchReader(){
			$('#readerInfo').form('clear');
		    $('#dg').datagrid('loadData', { total: 0, rows: [] });
			$.post('reader/findSingleReader',{queryReaderId:readerId},function(result){
				if (result.success){ 
					$('#readerInfo').form('load',{
						'readerId': result.reader.readerId,
						'readerName' : result.reader.readerName,
						'readerTypeName':result.reader.readerType.readerTypeName,
						'sex' : result.reader.sex,
						'enable':result.reader.enable
						});
					
				} else {
					//$.messager.show({	// show error message
					//	title: 'Error',
					//	msg: result.msg
					//});
				}
			},'json');
			//设置读者后，查询该读者当前借阅情况
			$('#dg').datagrid('load',{
				readerId: readerId
			});
		}
			
	</script>
</head>
<body class="easyui-layout"  data-options="fit:true,border:false">
	<table id="dg"  class="easyui-datagrid" data-options="fit:true,border:false"
			url="borrowInfo/listReaderNowBorrow"
			toolbar="#toolbar" pagination="true"
			rownumbers="true" fitColumns="true" singleSelect="true">
		<thead>
			<tr>
		<!-- <th field="picture" style="width:100px; height:100px;" data-options="formatter:function(v){return '<img src='+v+'/>'}">图书</th> -->
				<th field="bookState" width="4%">状态</th>
				<th field="booksBarCode" width="15%">条形码</th>
				<th field="bookName" width="12%">书名</th>
				<th field="callNumber" width="15%">索书号</th>
				<th field="renewNum" width="8%">续借次数</th>	
				<th field="borrowDate" width="23%">借阅时间</th>	
				<th field="dueDate" width="23%">应还时间</th>	
			</tr>
		</thead>
	</table>
	<div id="toolbar">
	<br>
		<form id="readerInfo" method="post">
	    	<table cellpadding="5">
	    		<tr>
	    		<td>读者账号:</td>
	    			<td><input class="easyui-textbox" id="formReaderId" type="text" name="readerId" readonly="true"></td>
	    			<td>读者姓名:</td>
	    			<td><input class="easyui-textbox" type="text" name="readerName" readonly="true"></td>
	    			<td>读者性别:</td>
	    			<td><input class="easyui-textbox" type="text" name="sex" readonly="true"></td>
	    			</tr>
	    		<tr>
	    			<td>读者类型:</td>
	    			<td><input class="easyui-textbox" type="text" name="readerTypeName" readonly="true"></td>
	    			<td>状态:</td>
	    			<td><select class="easyui-combobox" name="enable" style="width:150px;" readonly="true">
							<option value=""></option>
							<option value="1">可用</option>
							<option value="2">暂停使用（罚款未缴纳） </option>
							<option value="3">不可用</option>
						</select>
					</td>
	    		</tr>
	    	</table>
	    </form>
		<div>检索：</div> 
		<div>
			&nbsp&nbsp条形码:&nbsp&nbsp&nbsp
			<input id="booksBarCode" class="easyui-textbox" style="line-height:20px;border:1px solid #ccc">&nbsp
			<%if (securityUtil.havePermission("renew/renewBook")) {%>
			<a href="javascript:void(0);" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="doSearch()">续借</a>
			<%}%>
		</div>
	</div>
</body>
</html>