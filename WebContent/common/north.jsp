<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.yang.bishe.entity.User"%>
<%
	User sessionInfo = (User) session.getAttribute("sessionInfo");
%>
<script type="text/javascript">
	function logoutFun() {
		$.post('loginOut', function(result) {
			window.location.replace('index');//这里还是得要按照springmvc映射的规则来，index还是得有controller响应
		}, 'json');
		
	};
	function showMyInfoFun(){
		//window.parent.open("user/list", "listUser");
		$('#dlg').dialog('open').dialog('setTitle','用户信息');
	};
</script>
<div id="sessionInfoDiv" style="position: absolute; right: 10px; top: 10px;">
	<%
		if (sessionInfo != null) {
			out.print(com.yang.bishe.util.StringUtil.formateString("欢迎您，{0}", sessionInfo.getUserName()));
		}
	%>
</div>
<div style="position: absolute; right: 0px; bottom: 0px;">
	 <a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_kzmbMenu',iconCls:'ext-icon-cog'">控制面板</a> <a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_zxMenu',iconCls:'ext-icon-disconnect'">注销</a>
</div>
<div id="layout_north_kzmbMenu" style="width: 100px; display: none;">
	<div data-options="iconCls:'ext-icon-user_edit'" onclick="$('#passwordDialog').dialog('open');">修改密码</div>
	<div class="menu-sep"></div>
	<div data-options="iconCls:'ext-icon-user'" onclick="showMyInfoFun();">我的信息</div>
</div>
<div id="layout_north_zxMenu" style="width: 100px; display: none;">
	<div class="menu-sep"></div>
	<div data-options="iconCls:'ext-icon-door_out'" onclick="logoutFun();">退出系统</div>
</div>
<div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
		closed="true">
		<table class="table" style="width: 100%;">
			<tr>
				<th>用户ID</th>
				<td><%=sessionInfo.getUserId()%></td>
			</tr>
			<tr>
				<th>登录名</th>
				<td><%=sessionInfo.getUserName()%></td>
			</tr>
			<tr>
				<th>角色</th>
				<td><%=sessionInfo.getRole().getRoleName()%></td>
			</tr>
		</table>
</div>