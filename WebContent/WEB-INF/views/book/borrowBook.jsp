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
		function doSearch(){
			//前台判断读者状态是否可用
			if($('#readerState').combobox('getValue')==1){
				var dialog = parent.sy.modalDialog({
					title : '确认信息',
					url :'booksn/findBookBySN?booksBarCode='+$('#booksBarCode').val(), //显示借阅的图书信息，
					buttons : [ {
						text : '借阅',
						handler : function() {
							//dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				//执行借阅，这里注意一个逻辑问题，就是设置了读者，但是在输入框又输入读者id，而没有按下设置读者时，再借阅是读者id应该是按照设置的那个读者来
							$.post('borrowInfo/borrow',{readerId: $('#formReaderId').val(),booksBarCode:$('#booksBarCode').val()},function(result){
								if (result.success){
									$('#dg').datagrid('reload');	// reload the user data
									$.messager.show({	
										title: 'success',
										msg: result.msg
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
			}else{
					$.messager.show({	// show error message
					title: 'Error',
					msg: '读者状态不可用'
					});
				}
}
		function searchReader(){
			$('#readerInfo').form('clear');
		    $('#dg').datagrid('loadData', { total: 0, rows: [] });
			$.post('reader/findSingleReader',{queryReaderId:$('#readerId').val()},function(result){
				if (result.success){ 
					$('#readerInfo').form('load',{
						'readerId': result.reader.readerId,
						'readerName' : result.reader.readerName,
						'readerTypeName':result.reader.readerType.readerTypeName,
						'sex' : result.reader.sex,
						'enable':result.reader.enable
						});
					
				} else {
					$.messager.show({	// show error message
						title: 'Error',
						msg: result.msg
					});
				}
			},'json');
			//设置读者后，查询该读者当前借阅情况
			$('#dg').datagrid('load',{
				readerId: $('#readerId').val()
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
		&nbsp&nbsp读者账号:&nbsp&nbsp&nbsp
			<input id="readerId"  class="easyui-textbox" style="line-height:20px;border:1px solid #ccc">&nbsp&nbsp
			<%if (securityUtil.havePermission("borrow/setReader")) {%>
			<a href="javascript:void(0);" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="searchReader()">查找</a>
			<%}%>
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
		    			
		    			<td><select class="easyui-combobox" id="readerState" name="enable" style="width:150px;" readonly="true">
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
			<input id="booksBarCode"  class="easyui-textbox" style="line-height:20px;border:1px solid #ccc">&nbsp
			<%if (securityUtil.havePermission("borrow/borrowBook")) {%>
			<a href="javascript:void(0);" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="doSearch()">借阅</a>
			<%}%>
		</div>
	</div>
</body>
</html>