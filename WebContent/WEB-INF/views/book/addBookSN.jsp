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
		function newBookSN(){
			var row = $('#dg').datagrid('getSelected');
			if (row){
				$('#dlg').dialog('open').dialog('setTitle','关联条码');
				//$('#fm').form('clear');
				$('#fm').form('load',{
					"book.bookId":row.bookId,
					"booksBarCode":'' //清空上一次新建时填的条形码
				});
			}else{
				$.messager.show({
					title: 'Error',
					msg:'请先选择书籍'
				});
			}	
			url = 'booksn/addBookSN';
		}
		function saveBookSN(){
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
		function doSearch(){
			$('#dg').datagrid('load',{
			queryBookName: $('#searchBox').val()
		});
}
	function clearSearch(){
		$('#searchBox').val('');
		$('#dg').datagrid('load',{});
	}
	
	function formatBookType(val,row,index){
		//alert(row.bookType);
		if(row.bookType==null){
			return "";
		}else
			return row.bookType.bookTypeName;
	} 
	<!--创建子数据网格  -->
		$(function(){
			$('#dg').datagrid({
				view: detailview,
				detailFormatter:function(index,row){
					return '<div style="padding:2px"><table id="ddv-' + index + '"></table></div>';
				},
				onExpandRow: function(index,row){
					$('#ddv-'+index).datagrid({
						url:'book/findSnByBook?bookId='+row.bookId,
						fitColumns:true,
						singleSelect:true,
						rownumbers:true,
						loadMsg:'',
						height:'auto',
						columns:[[
							{field:'booksBarCode',title:'条形码',width:'100'},
							{field:'book.bookId',title:'书ID', width:'100',
								formatter : function() {
										return row.bookId;
								}},
							{field:'bookLocation.locationName',title:'馆藏地',width:100,
								formatter : function(value, row) {
									if(row.bookLocation==null){
										return "";
									}else
										return row.bookLocation.locationName;
								}
							},
							{field:'bookState',title:'状态',width:'100',
								formatter : function(value, row) {
									switch (value) {
									case 1:
										return '待借阅';
									case 2:
										return '用户预约';
									case 3:
										return '已借出';
									case 4:
										return '报损';
									default:
										return '';
									}
								}},
								{
									title : '操作',
									field : 'action',
									width : '80',
									formatter : function(value, row) {
										var str = '';
										//str = '<a href="#" onclick="editFun(this)">Save</a> ';
										<%if (securityUtil.havePermission("relevance/edit")) {%>
										str += sy.formatString('<img class="iconImg ext-icon-note_edit" title="编辑" onclick="editFun(\'{0}\');"/>', index);
										<%}%>
										<%if (securityUtil.havePermission("relevance/delete")) {%>
										str += sy.formatString('<img class="iconImg  ext-icon-note_delete" title="删除" onclick="removeFun(\'{0}\');"/>',row.booksBarCode);
										<%}%>
										return str;
									}
								}
						]],
						onResize:function(){
							$('#dg').datagrid('fixDetailRowHeight',index);
						},
						onLoadSuccess:function(){
							setTimeout(function(){
								$('#dg').datagrid('fixDetailRowHeight',index);
							},0);
						}
					});
					$('#dg').datagrid('fixDetailRowHeight',index);
				}
			});
		});
		
		 function editFun(index){
			//alert(id);
			var row =$('#ddv-'+index).datagrid('getSelected');
			if(row){
				$('#fm').form('clear');
				$('#dlg').dialog('open').dialog('setTitle','编辑条码');
				//document.getElementById("formBookId").style.visibility="hidden";
			//	$('#fm').form('load',row);
			//	alert(row.book.bookId);
				$('#fm').form('load',{
					"booksBarCode":row.booksBarCode,
					"bookState":row.bookState,
					"book.bookId":row.book.bookId,
				});
				if(row.bookLocation.locationName!=null){
					$('#formLocationId').combobox('setValue',row.bookLocation.locationId);
					}
			}
			url = 'booksn/updateBookSN';
		}
		 function removeFun(barcode){
			$.post('booksn/removeBookSN',{booksBarCode:barcode}, function(result) {
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
		
	</script>
	<!--修改默认datagrid的行高  -->
	<style>.datagrid-cell{line-height:30px}</style> 
</head>
<body class="easyui-layout"  data-options="fit:true,border:false">
	<table id="dg" class="easyui-datagrid" data-options="fit:true,border:false"
			url="book/listBookForUser"
			toolbar="#toolbar" pagination="true"
			rownumbers="true" fitColumns="true" singleSelect="true">
		<thead>
			<tr>
				<th field="bookId" width="5%">书ID</th>
				<th field="bookName" width="10%">书名</th>
				<th field="bookType.bookTypeName" width="14%" formatter="formatBookType">书类型</th>
				<th field="author" width="50">作者</th>
				<th field="callNumber" width="50">索书号</th>
				<th field="iSBN" width="12%">ISBN</th>
				<th field="publisher" width="11%">出版社</th>
				<th field="publishYear" width="5%">出版年份</th>
				<th field="series" width="5%">系列</th>
				<th field="language" width="50">语言</th>
				<th field="price" width="5%">价格</th>
				<th field="page" width="5%">页数</th>
			</tr>
		</thead>
	</table>
	<div id="toolbar">
		<br>
		<%if (securityUtil.havePermission("relevance/add")) {%>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newBookSN()">新建条码</a>
		<%}%>
		<div>
		<br>
			<span>书名:</span>
			<input id="searchBox" class="easyui-textbox" style="line-height:26px;border:1px solid #ccc">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="doSearch()">Search</a>
			<td><a href="javascript:void(0);" class="easyui-linkbutton" iconCls="ext-icon-zoom_out" plain="true" onclick="clearSearch()">清空查询</a></td>
		</div>
		<br>
	</div>
	
	<div id="dlg" class="easyui-dialog" style="width:250px;height:250px;padding:10px 20px"
			closed="true" buttons="#dlg-buttons">
		<form id="fm" method="post" style="width: 100%;">
		<table cellpadding="5"> 
			<div class="fitem" >
				<label>条形码&nbsp&nbsp&nbsp&nbsp</label>
				<input name="booksBarCode" class="easyui-validatebox" required="true"  >
			</div> 
			<div class="fitem">
				<label>状态&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</label>
				<select class="easyui-combobox" name="bookState" editable="false" style="width:150px;">
					<option value="1" selected="selected">可借</option>
				</select>
			</div>
			<div class="fitem">
				<label>馆藏地&nbsp&nbsp&nbsp</label>
				<select name="bookLocation.locationId" id="formLocationId" class="easyui-combobox" data-options="editable:false,valueField:'locationId',textField:'locationName',url:'bookLocation/getBookLocation',panelHeight:'auto'" style="width: 155px;"></select>
			</div>
			<div class="fitem">
				<label>书编号&nbsp&nbsp&nbsp&nbsp</label>
				<input name="book.bookId" id="formBookId" class="easyui-validatebox"  readonly="readonly" >
			</div> 
		</table>
		</form>
		
	</div>
	<div id="dlg-buttons">
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveBookSN()">Save</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">Cancel</a>
	</div>
</body>
</html>