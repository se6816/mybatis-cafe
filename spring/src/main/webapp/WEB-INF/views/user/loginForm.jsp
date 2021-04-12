<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx" crossorigin="anonymous"></script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>

<sec:authorize access="isAuthenticated()">
    <% response.sendRedirect("/bbs/main"); %>
</sec:authorize>
<meta charset="UTF-8">
<title>로그인</title>
</head>
<body>

<div class="container w-100 mx-auto mt-5">
<h1 class="text-success" align="center">카페</h1>
<br><br>
<div class="card" style="width:500px; margin:auto;">
<div class="card-header"> 
<h5 align="center" style="font-weight: bold;">로그인</h5>
</div>
<div class="form-group card-body" >

<form action="/spring/login" method="post">
  ID: <input type="text" class="form-control" id="username" name="username" />
  <br/>
   비밀번호: <input type="password" class="form-control" id="password" name="password" />
  <br/>
  <p class="err">${ERR}</p>
  <input type="submit" class="btn btn-success btn-block" value="로그인" />
	
</form>
<a href="<c:url value='/join'/>">회원가입을 하지 않으셨나요?</a>
</div>

<div class="card-footer">


</div>
</div>
</div>
</body>
</html>