<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
var sy = sy || {};
sy.pixel_0 = '<%=path%>/style/pixel_0.gif';//0像素的背景，一般用于占位
</script>

<link rel="stylesheet" type="text/css" href="style/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="style/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="style/easyui/demo.css">
<link rel="stylesheet" type="text/css" href="style/my.css">
<script type="text/javascript" src="js/jquery-easyui-1.4.2/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery-easyui-1.4.2/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/syExt.js"></script>
<script type="text/javascript" src="js/datagrid-detailview.js"></script>