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
	<script type="text/javascript">
		var url;
		function newReader(){
			$('#dlg').dialog('open').dialog('setTitle','添加读者');
			$('#fm').form('clear');
		//	$('#filedBookID').attr('disabled', true);
			
			url = 'reader/addReader';
		}
		function editReader(){
			var row = $('#dg').datagrid('getSelected');
			$('#fm').form('clear');
			document.getElementById("fieldReaderID").style.visibility="hidden";
			if (row){
				$('#dlg').dialog('open').dialog('setTitle','编辑读者');
				$('#fm').form('load',row);
				//解决编辑是不能显示书类型问题	
				if(row.readerType.readerTypeName!=null){
					$('#formReaderType').combobox('setValue',row.readerType.readerTypeId);
					}
				url ='reader/updateReader';
			}
		}
		function saveReader(){
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
		function removeReader(){
			var row = $('#dg').datagrid('getSelected');
			 //alert(row.id);
			if (row){
				$.messager.confirm('Confirm','你确定要删除读者吗?',function(r){
					if (r){
						$.post('reader/removeReader',{id:row.readerId},function(result){
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
			queryReaderId: $('#searchBox').val()
		});
}
	function clearSearch(){
		$('#searchBox').val('');
		$('#dg').datagrid('load',{});
	}
	
	function formatReaderType(val,row,index){
		//alert(row.bookType);
		if(row.readerType==null){
			return "";
		}else
			return row.readerType.readerTypeName;
	} 
	function formatBirthday(val,row){
		var date = new Date(val);
		if(row.birthday==null){
			return "";
		}else
			return mydateformatter(date);
	}
	function formatStatus(val,row){
		if(row.enable==1){
			return "可用";
		}else if(row.enable==2){
			return "欠款，暂停使用";
		}else if(row.enable==3)
			return "不可用";
		else return val;
	}
	function mydateformatter(date){
		var y = date.getFullYear();
		var m = date.getMonth()+1;
		var d = date.getDate();
		return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
	}
	function mydateparser(s){
		if (!s) return new Date();
		var ss = (s.split('-'));
		var y = parseInt(ss[0],10);
		var m = parseInt(ss[1],10);
		var d = parseInt(ss[2],10);
		if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
			return new Date(y,m-1,d);
		} else {
			return new Date();
		}
	}                                  
	</script>
	<!--修改默认datagrid的行高  -->
	<style>.datagrid-cell{line-height:30px}</style> 
</head>
<body class="easyui-layout"  data-options="fit:true,border:false">
	<table id="dg" class="easyui-datagrid" data-options="fit:true,border:false"
			url="reader/listReader"
			toolbar="#toolbar" pagination="true"
			rownumbers="true" fitColumns="true" singleSelect="true">
		<thead>
			<tr>
				<th field="readerId" width="10%">读者账号</th>
				<th field="readerName" width="10%">读者姓名</th>
				<th field="readerType.readerTypeName" width="14%" formatter="formatReaderType">读者类型</th>
				<th field="sex" width="4%">性别</th>
				<th field="birthday" width="11%" formatter="formatBirthday">出生日期</th>
				<th field="email" width="50">电子邮件</th>
				<th field="tel" width="12%">联系电话</th>
				<th field="enable" width="8%" formatter="formatStatus">状态</th>
			</tr>
		</thead>
	</table>
	<div id="toolbar">
		<br>
		<%if (securityUtil.havePermission("reader/add")) {%>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newReader()">新建</a>
		<%}%>
		<%if (securityUtil.havePermission("reader/edit")) {%>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editReader()">编辑</a>
		<%}%>
		<%if (securityUtil.havePermission("reader/delete")) {%>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeReader()">删除</a>
		<%}%>
		<div>
		<br>
			<span>读者账号:</span>
			<input id="searchBox" class="easyui-textbox" style="line-height:26px;border:1px solid #ccc">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="doSearch()">Search</a>
			<td><a href="javascript:void(0);" class="easyui-linkbutton" iconCls="ext-icon-zoom_out" plain="true" onclick="clearSearch()">清空查询</a></td>
		</div>
		<br>
	</div>
	
	<div id="dlg" class="easyui-dialog" style="width:300px;height:360px;padding:10px 20px"
			closed="true" buttons="#dlg-buttons">
		<form id="fm" method="post" class="form">
		<table class="table" style="width: 100%;"> 
			<tr id="fieldReaderID">
				<th>读者账号</th>
				<td><input name="readerId" class="easyui-validatebox" required="true" ></td>
			</tr> 
			<tr>
				<th>读者姓名</th>
				<td><input name="readerName" class="easyui-validatebox" required="true" ></td>
			</tr>
			<tr>
				<th>读者类型</th>
				<td><select name="readerType.readerTypeId" id="formReaderType" class="easyui-combobox" data-options="required:true,editable:false,valueField:'readerTypeId',textField:'readerTypeName',url:'readerType/getReaderType',panelHeight:'auto'" style="width: 155px;"></select></td>
			</tr>
			<tr>
				<th>性别</th>
				<td><select class="easyui-combobox" name="sex" editable="false" style="width:155px;">
    				<option value="男">男</option>
    				<option value="女">女</option>
					</select></td>
			</tr>		
			<tr>
				<th>出生日期</th>
				<td><input class="easyui-datebox" name="birthday" data-options="formatter:mydateformatter,parser:mydateparser,editable:false"></td>
			</tr>
			<tr>
				<th>电子邮件</th>
				<td><input name="email" class="easyui-validatebox" validType="email"></td>
			</tr>
			<tr>
				<th>联系电话</th>
				<td><input name="tel" class="easyui-validatebox"></td>
			</tr>
			<tr>
				<th>状态</th>
				<td><select class="easyui-combobox" name="enable" editable="false" required="true" style="width:150px;">
    				<option value="1">可用</option>
    				<option value="2">欠款，暂停使用</option>
    				<option value="3">不可用</option>
					</select></td>
			</tr>
		</table>
		</form>
		
	</div>
	<div id="dlg-buttons">
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveReader()">Save</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">Cancel</a>
	</div>
</body>
</html>