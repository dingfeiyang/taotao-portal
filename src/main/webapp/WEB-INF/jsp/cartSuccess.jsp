<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加购物车成功</title>
</head>
 <link type="text/css" rel="stylesheet" href="/css/base.css" />
  <style>
  	.main .cart{
  		color:red;
  		font-size: 30px;
  		margin-left: 900px;
  		margin-top: 100px;
  		margin-bottom: 100px;
  	}
  </style>
<body>
<jsp:include page="commons/header.jsp" />

<div class="main">
	<div class="cart">添加购物车成功&nbsp;&nbsp;&nbsp;&nbsp;<a href="/cart/cart.html">去购物车结算</a></div>
	
</div>
<script type="text/javascript" src="/js/base-v1.js"></script>

	<!-- links start -->
    <jsp:include page="commons/footer-links.jsp"></jsp:include>
    <!-- links end -->
</body>
<script type="text/javascript" src="/js/jquery-1.6.4.js"></script>
<script type="text/javascript" src="/js/jquery.cookie.js"></script>
<script type="text/javascript">
var TT = TAOTAO = {
		checkLogin : function(){
			var _ticket = $.cookie("TT_TOKEN");
			if(!_ticket){
				return ;
			}
			$.ajax({
				url : "http://localhost:8084/user/token/" + _ticket,
				dataType : "jsonp",
				type : "GET",
				success : function(data){
					if(data.status == 200){
						var username = data.data.username;
						var html = username + "，欢迎来到淘淘！<a href=\"http://www.taotao.com/user/logout.html\" class=\"link-logout\">[退出]</a>";
						$("#loginbar").html(html);
					}
				}
			});
		}
	}

	$(function(){
		// 查看是否已经登录，如果已经登录查询登录信息
		TT.checkLogin();
	});
</script>
</html>