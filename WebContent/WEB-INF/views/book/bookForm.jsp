<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.yang.bishe.entity.Book"%>
<%@ page import="com.yang.bishe.entity.Reader"%>
<%
	Book book=(Book)request.getAttribute("bookInfo");

%>
<!-- 用于借阅时，确认信息-->
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/common/base.jsp"%> 
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',fit:true,border:false">
		<table style="width: 100%;">
			<tr>
				<td><fieldset>
						<table class="table" style="width: 100%;">
							<tr>
								<th>书名</th>
								<td><%=book.getBookName()%></td>
								<th>作者</th>
								<td><%=book.getAuthor()%></td>
							</tr>
							<tr>
								<th>索书号</th>
								<td><%=book.getCallNumber()%></td>
								<th>出版社</th>
								<td><%=book.getPublisher()%></td>
							</tr>
						</table>
					</fieldset></td>
			</tr>
		</table>
	</div>
</body>
</html>