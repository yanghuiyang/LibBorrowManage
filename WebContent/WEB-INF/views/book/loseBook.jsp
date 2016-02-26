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
		var booksBarCode;
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
		
	function payFineFun(BarCode){
		$('#fm').form('clear');
		$('#dlg').dialog('open').dialog('setTitle','丢失赔偿');
		booksBarCode=BarCode;
	}
	function goFine(){
		$.post('loseInfo/lose', {fine:$('#formFine').val(),booksBarCode:booksBarCode,readerId:$('#readerId').val()}, function(result) {
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
	function formatBookType(val,row,index){
			if(row.bookType==null){
				return "";
			}else
				return row.bookType.bookTypeName;
		}
	function formatAction(val,row){
		var str = '';
		<%if (securityUtil.havePermission("loseBook/payFine")) {%>
		str += sy.formatString('<img class="iconImg ext-icon-note_edit" title="丢失处理" onclick="payFineFun(\'{0}\');"/>', row.booksBarCode);
		<%}%>
		return str;
	}
	$(function(){
		$('#dg').datagrid({
			onLoadSuccess : function(data) {
			$('.iconImg').attr('src', sy.pixel_0);
			}
		});
	});
	</script>
</head>
<body class="easyui-layout"  data-options="fit:true,border:false">
	<table id="dg"  class="easyui-datagrid" data-options="fit:true,border:false"
			url="borrowInfo/listReaderNowBorrow"
			toolbar="#toolbar" pagination="true"
			rownumbers="true" fitColumns="true" singleSelect="true">
		<thead>
			<tr>
				<th field="bookState" width="4%">状态</th>
				<th field="booksBarCode" width="12%">条形码</th>
				<th field="bookName" width="12%">书名</th>
				<th field="bookType.bookTypeName" width="10%" formatter="formatBookType">书类型</th>
				<th field="callNumber" width="15%">索书号</th>
				<th field="borrowDate" width="10%">借阅时间</th>	
				<th field="dueDate" width="10%">应还时间</th>	
				<th field="price" width="5%">价格</th>
				<th field="action" width="8%" formatter="formatAction">操作</th>
			</tr>
		</thead>
	</table>
	<div id="toolbar">
	<br>
		&nbsp&nbsp证件号:&nbsp&nbsp&nbsp
			<input id="readerId" class="easyui-textbox" style="line-height:20px;border:1px solid #ccc">&nbsp&nbsp
			<%if (securityUtil.havePermission("loseBook/setReader")) {%>
			<a href="javascript:void(0);" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="searchReader()">Search</a>
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
		<div>	
		</div>
	</div>	
	<div id="dlg" class="easyui-dialog" style="width:250px;height:250px;padding:10px 20px"
			closed="true" buttons="#dlg-buttons" modal="true">
		<form id="fm" method="post" class="form" >
		<fieldset>
			<table class="table" style="width: 100%;">
				<tr>
					<th>罚款金额</th>
					<td><input name="fine" id="formFine" class="easyui-validatebox" data-options="required:true" /></td>
				</tr>
			</table>
		</fieldset>
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-ok" onclick="goFine()">缴纳</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">Cancel</a>
	</div>
</body>
</html>