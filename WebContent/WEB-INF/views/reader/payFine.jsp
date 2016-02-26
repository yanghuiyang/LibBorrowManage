<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.yang.bishe.entity.User"%>
<%@ page import="com.yang.bishe.entity.Book"%>
<%@ page import="com.yang.bishe.entity.Reader"%>
<%@ page import="com.yang.bishe.util.SecurityUtil"%>
<%
	User sessionInfo = (User) session.getAttribute("sessionInfo");
	SecurityUtil securityUtil = new SecurityUtil(session);
%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/common/base.jsp"%> 
	<script type="text/javascript">
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
            $('#dg').datagrid({
                onLoadSuccess: compute,//加载完毕后执行计算
                url: 'borrowInfo/listReaderAllFine', 
                queryParams:{readerId: $('#readerId').val()}
               
        });
            function compute() {  //计算函数
                var rows = $('#dg').datagrid('getRows'); //获取当前的数据行
                var ptotal = 0;     //计算listprice的总和
                for (var i = 0; i < rows.length; i++) {
                    ptotal += rows[i]['fine'];
                }     
             //   $('#totalFine').val(ptotal);
            	$('#readerInfo').form('load',{
					'totalFine':ptotal
					});
              
            }
		}
	function payFine(){
		if($('#formReaderId').val()==''){
			$.messager.show({	// show error message
				title: 'Error',
				msg: '请先设置读者'
			});
		}else if($('#totalFine').val()==0){
			$.messager.show({	// show error message
				title: 'Error',
				msg: '读者未欠费'
			});
		}else{
			$.messager.confirm('确认', '确定缴完全部费用：'+$('#totalFine').val()+'元?', function(r){
				if (r){
					$.post('borrowInfo/payFine',{readerId:$('#formReaderId').val()},function(result){
						if (result.success){ 
							searchReader();	
							$.messager.show({	// show error message
								title: 'Info',
								msg: result.msg
							});
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
	</script>
</head>
<body class="easyui-layout"  data-options="fit:true,border:false">
	<table id="dg"  class="easyui-datagrid" data-options="fit:true,border:false"
			toolbar="#toolbar" pagination="false"
			rownumbers="true" fitColumns="true" singleSelect="true">
		<thead>
			<tr>
				<th field="returnDate" width="23%">欠款时间</th>	
				<th field="booksBarCode" width="15%">条形码</th>
				<th field="bookName" width="12%">书名</th>
				<th field="callNumber" width="15%">索书号</th>	
				<th field="fine" width="23%">欠款金额</th>	
			</tr>
		</thead>
	</table>
	<div id="toolbar">
	<br>
		&nbsp&nbsp证件号:&nbsp&nbsp&nbsp&nbsp&nbsp
			<input id="readerId" class="easyui-textbox">
			<%if (securityUtil.havePermission("payFine/search")) {%>
			<a href="javascript:void(0);" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="searchReader()">Search</a>
			<%}%>
			<%if (securityUtil.havePermission("payFine/payFine")) {%>
			<a href="javascript:void(0);" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="payFine()">缴费&nbsp&nbsp&nbsp&nbsp</a>
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
						<td>总欠款金额:</td>
		    			<td><input name="totalFine" class="easyui-textbox" id="totalFine" type="text" readonly="true"></td>
		    		</tr>
		    	</table>
		    </form> 
	</div>
</body>
</html>