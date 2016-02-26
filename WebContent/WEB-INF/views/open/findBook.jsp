<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.yang.bishe.entity.User"%>
<%@ page import="com.yang.bishe.entity.Book"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/common/base.jsp"%> 
	<script type="text/javascript">
		function doSearch(){
				$('#dg').datagrid('load',{
					queryBookName: $('#bookName').val()
			});
		}
		function complexSearch(){
			//$('#dlg').dialog('open');
			window.parent.addTab('高级查询','open/goComplexSearch','ext-icon-application_form_magnify');
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
								}},
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
		
	</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<table id="dg" title="search Book" class="easyui-datagrid" style="width:100%;height:100%;"
			url="book/findBook"
			toolbar="#toolbar" pagination="true"
			rownumbers="true" fitColumns="true" singleSelect="true">
		<thead>
			<tr>
				<th field="bookName" width="60">书名</th>
				<th field="author" width="55">作者</th>
				<th field="series" width="45">丛书名</th>
				<th field="publisher" width="60">出版社</th>
				<th field="callNumber" width="50">索书号</th>
				<th field="totalNum" width="30">馆藏</th>
				<th field="canBorrowNum" width="30">可借</th>
			</tr>
		</thead>
	</table>
	<div id="toolbar" style="height:50px;">
		<div>
			<br>
			<span>书名:</span>
			<input id="bookName" class="easyui-textbox" style="line-height:22px;border:1px solid #ccc">
			<a href="javascript:void(0);" class="easyui-linkbutton" plain="true" onclick="doSearch()">Search</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" plain="true" onclick="complexSearch()">高级查询</a>
		</div>
	</div>
</body>
</html>