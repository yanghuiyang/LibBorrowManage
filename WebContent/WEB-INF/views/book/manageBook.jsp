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
		function newBook(){
			$('#dlg').dialog('open').dialog('setTitle','添加书籍');
			$('#fm').form('clear');
		//	$('#filedBookID').attr('disabled', true);
			document.getElementById("fieldBookID").style.visibility="hidden";
			url = 'book/addBook';
		}
		function editBook(){
			
			var row = $('#dg').datagrid('getSelected');
			// alert(row.userId);
			
			if (row){
				$('#fm').form('clear'); //防止先前 编辑，然后再编辑时会默认出现上一个book的属性（会填充后来的book没有的属性）
				$('#dlg').dialog('open').dialog('setTitle','编辑书籍');
				$('#fm').form('load',row);
				//解决编辑时不能显示书类型问题	
				if(row.bookType.bookTypeName!=null){
					$('#formBookType').combobox('setValue',row.bookType.bookTypeId);
					}
				url ='book/updateBook';
			}
		}
		function saveBook(){
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
		function removeBook(){
			var row = $('#dg').datagrid('getSelected');
			 //alert(row.id);
			if (row){
				$.messager.confirm('Confirm','你确定要删除书籍吗?',function(r){
					if (r){
						$.post('book/removeBook',{id:row.bookId},function(result){
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
							{field:'booksBarCode',title:'条形码',width:100},
							{field:'locationName',title:'馆藏地',width:100},
							{field:'bookState',title:'状态',width:100,
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
								}}
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
	function douBanSearch(){
			$.post('book/getBookInfoByDouBanApi',{isbn:$('#formISBN').val()},function(result){
				if (result.success){
					$('#fm').form('load',{
						//'readerId': result.reader.readerId,
						'bookName' : result.book.bookName,
						'author':result.book.author,
						'publisher' : result.book.publisher,
						'publishYear':result.book.publishYear,
						'price':result.book.price,
						'page':result.book.page
						});
					
				} else {
					$.messager.show({	// show error message
						title: 'Error',
						msg: result.msg
					});
				}
			},'json');
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
		<%if (securityUtil.havePermission("book/add")) {%>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newBook()">新建</a>
		<%}%>
		<%if (securityUtil.havePermission("book/edit")) {%>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editBook()">编辑</a>
		<%}%>
		<%if (securityUtil.havePermission("book/delete")) {%>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeBook()">删除</a>
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
	
	<div id="dlg" class="easyui-dialog" style="width:400px;height:450px;padding:10px 20px"
			closed="true" buttons="#dlg-buttons">
		<form id="fm" method="post" class="form">
		<table  class="table" style="width: 100%;"> 
			<tr id="fieldBookID">
				<th>编号</th>
				<td><input name="bookId" readonly="readonly"></td>
			</tr> 
			<tr>
				<th>书名</th>
				<td><input name="bookName" class="easyui-validatebox" required="true" ></td>
			</tr> 
			<tr>
				<th>书类型</th>
				<td><select name="bookType.bookTypeId" id="formBookType" class="easyui-combobox" data-options="required:true,editable:false,valueField:'bookTypeId',textField:'bookTypeName',url:'bookType/getBookType',panelHeight:'auto'" style="width: 155px;"></select></td>
			</tr> 
			<tr>
				<th>作者</th>
				<td><input name="author" required="true"></td>
			</tr> 
			<tr>
				<th>索书号</th>
				<td><input name="callNumber"></td>
			</tr> 
			<tr>
				<th>ISBN</th>
				<td><input name="iSBN" id="formISBN"></td> <td><a href="javascript:void(0);" class="easyui-linkbutton" iconCls="ext-icon-zoom_out" plain="true" onclick="douBanSearch()">联网查询</a></td>
			</tr> 
			<tr>
				<th>出版社</th>
				<td><input name="publisher"></td>
			</tr> 
			<tr>
				<th>出版年份</th>
				<td><input name="publishYear"></td>
			</tr> 
			<tr>
				<th>系列</th>
				<td><input name="series"></td>
			</tr> 
			<tr>
				<th>语言</th>
				<td><input name="language"></td>
			</tr> 
			<tr>
				<th>价格</th>
				<td><input name="price" required="true"></td>
			</tr>
			<tr>
				<th>页数</th>
				<td><input name="page"></td>
			</tr>
		</table>
		</form>
		
	</div>
	<div id="dlg-buttons">
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveBook()">Save</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">Cancel</a>
	</div>
</body>
</html>