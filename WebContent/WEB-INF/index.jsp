<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	
	<title>图书借阅管理系统</title>
	<link rel="stylesheet" type="text/css" href="css/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="css/easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="css/easyui/demo.css">
	<script type="text/javascript" src="js/jquery-easyui-1.4.2/jquery.min.js"></script>
	<script type="text/javascript" src="js/jquery-easyui-1.4.2/jquery.easyui.min.js"></script>
</head>
<body>
	<h2>Validate Form on Submit</h2>
	<div style="text-align:center; padding:15em; margin:10px  0; background:#AAA">
	<div class="easyui-panel" title="登陆" style="width:400px">
		<div style="text-align:center; padding:10px 60px 20px 60px ">
	    <form id="ff" class="easyui-form" method="post" data-options="novalidate:true">
	    	<table cellpadding="10">
	    		<tr>
	    			<td>用户名:</td>
	    			<td><input class="easyui-textbox" type="text" name="name" data-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td>密码:</td>
	    			<td><input class="easyui-textbox" type="text" name="subject" data-options="required:true"></input></td>
	    		</tr>
	    		
	    	</table>
	    </form>
	    <div style="text-align:left;padding:5px;margin:20px ">
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">Submit</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">Clear</a>
	    </div>
	    </div>
	</div>
	</div>
	<script>
		function submitForm(){
			$('#ff').form('submit',{
				onSubmit:function(){
					return $(this).form('enableValidation').form('validate');
				}
			});
		}
		function clearForm(){
			$('#ff').form('clear');
		}
	</script>
</body>
</html>