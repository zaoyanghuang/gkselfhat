<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>登录页面</title>
</head>
<body>
<form action="/rediscontroller/insertredisdata" method="post">
  添加数据：<input name="insertdata" type="text" class="insertdata"/>
  <input name="insertdatabtn" type="submit" class="btn" value="添加" />
  添加结果：<span>${msg}<span/>
</form>
<form action="/rediscontroller/selredisdata" method="post">
  查询数据：<input name="seldata" type="text" class="seldata"/>
  <input name="seldatabtn" type="submit" class="btn" value="查询" />
  查询结果：<span>${msg}<span/>
</form>

</body>
</html>
</html>
