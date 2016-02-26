<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.yang.bishe.entity.User"%>
<%@ page import="com.yang.bishe.entity.Book"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/common/base.jsp"%> 
	<script type="text/javascript">
		function doComplexSearch(){
			validateForm();
			$('#dg').datagrid('loadData', { total: 0, rows: [] });
			$('#dg').datagrid('options').url="book/complexFind";
			$('#dg').datagrid('load',sy.serializeObject($('#ff')));
		}
		function validateForm(){
			//验证检索表单
	    	var key1 = document.getElementById('key1').value;
			key1 = sy.filterStr(key1);
			var key2 = document.getElementById('key2').value;
			key2 = sy.filterStr(key2);
			var key3 = document.getElementById('key3').value;
			key3 = sy.filterStr(key3);
		    if((key1 == null || key1.trim()=="") && (key2 == null || key2.trim() == "") && (key3 == null || key3.trim() == "")){  
		      //  alert("请输入检索关键字！");  
		      $.messager.alert({
				title: 'Error',
				msg: '请输入检索关键字！'
			});
		        return false;  
		    } 
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
	<table id="dg" class="easyui-datagrid" style="width:100%;height:100%; "
			url=""
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
	<div id="toolbar" style="height:130px;">
		<div style="padding:10px 3px 10px 10px">
	   		<form id="ff" class="easyui-form" method="post" data-options="novalidate:true">
	    		<table cellpadding="5">
					<tr>
		    			<td>书名:</td>
		    			<td><input id="key1" class="easyui-textbox" type="text" name="name" ></input></td>
		    			<td>逻辑:</td>
		    			<td><select class="easyui-combobox" name="logic1" editable="false" style="width:150px;">
							<option value="and" selected="selected">并且</option>
							<option value="or" >或者</option>
						</select></td>
		    		</tr>
		    		<tr>
		    			<td>作者:</td>
		    			<td><input id="key2" class="easyui-textbox" type="text" name="author"></input></td>
		    			<td>逻辑:</td>
		    			<td><select class="easyui-combobox" name="logic2" editable="false" style="width:150px;">
							<option value="and" selected="selected">并且</option>
							<option value="or" >或者</option>
						</select></td>
		    		</tr>
					<tr>
		    			<td>系列:</td>
		    			<td><input  id="key3" class="easyui-textbox" type="text" name="series"></input></td>
		    			<td><a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" onclick="doComplexSearch()">查询</a></td>
		    		</tr>
	   			</table>
	    	</form>
		</div>	
	</div>
</body>
</html>