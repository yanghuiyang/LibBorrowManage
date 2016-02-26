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
	function formatday(val,row){
		var date = new Date(val);
		if(row.createDate==null){
			return "";
		}else
			return mydateformatter(date);
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
			url="loseInfo/listAll"
			toolbar="#toolbar" pagination="true"
			rownumbers="true" fitColumns="true" singleSelect="true">
		<thead>
			<tr>
				<th field="bookBarCode" width="10%">图书条码</th>
				<th field="bookName" width="10%">图书名</th>
				<th field="createDate" width="11%" formatter="formatday">丢失日期</th>
				<th field="readerId" width="10%">读者账号</th>
				<th field="operatorId" width="12%">操作员</th>
			</tr>
		</thead>
	</table>
</body>
</html>