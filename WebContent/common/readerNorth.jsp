<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.yang.bishe.entity.Reader"%>
<%
	Reader sessionInfo = (Reader) session.getAttribute("sessionInfo");
%>
<script type="text/javascript">
	function logoutFun() {
		$.post('loginOut', function(result) {
			window.location.replace('index');//这里还是得要按照springmvc映射的规则来，index还是得有controller响应
		}, 'json');
		
	};
</script>
<div id="sessionInfoDiv" style="position: absolute; right: 10px; top: 10px;">
	<%
		if (sessionInfo != null) {
			out.print(com.yang.bishe.util.StringUtil.formateString("欢迎您，{0}", sessionInfo.getReaderName()));
		}
	%>
</div>
<div style="position: absolute; right: 0px; bottom: 0px;">
	 <a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_kzmbMenu',iconCls:'ext-icon-cog'">控制面板</a> <a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_zxMenu',iconCls:'ext-icon-disconnect'">注销</a>
</div>
<div id="layout_north_zxMenu" style="width: 100px; display: none;">
	<div class="menu-sep"></div>
	<div data-options="iconCls:'ext-icon-door_out'" onclick="logoutFun();">退出系统</div>
</div>