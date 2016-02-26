<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
    
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>">
	<meta charset="UTF-8">
	<title>图书馆借阅管理系统</title>
	<link rel="stylesheet" type="text/css" href="style/login.css">
	<script type="text/javascript" src="js/jquery-easyui-1.4.2/jquery.min.js"></script>
	<script type="text/javascript" src="js/jquery-easyui-1.4.2/jquery.easyui.min.js"></script>

</head>
<body>
<div id="header" background-color="black"color="white" text-align="center" padding="5px">
	<h1 style="font-size:300%">高校图书馆借阅管理系统</h1>
</div>

	<div class="wrapper">
			<div class="content">
				<div id="form_wrapper" class="form_wrapper">
					<form action="home" method="post" class="login active">
						<h3>Login</h3>
						<div>
							<label>Username:</label>
							<input name="UserId" type="text" />
						</div>
						<div>
							<label>Password:</label>
							<input name="PassWord" type="password" />
						</div>
						<div class="bottom">
							<input type="submit" value="Login"></input>
						</div>
						<div>	
						<%if (request.getAttribute("errorMsg")!=null) {%>
							<p id="msg" font-size="12px" color="red"><%=request.getAttribute("errorMsg")%></p>
						<%}%>			
						</div>
					</form>
				</div>
			</div>
		</div>
</body>
</html>