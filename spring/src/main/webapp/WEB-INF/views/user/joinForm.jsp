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

<meta charset="UTF-8">
<title>로그인</title>
</head>
<body>

<div class="container w-100 mx-auto mt-5">
<br><br>
<div class="card" style="width:500px; margin:auto;">
<div class="card-header"> 
<h5 align="center" style="font-weight: bold;">회원가입</h5>
</div>
<div class="form-group card-body" >
<form action="" method="post">
   <label for="id">아이디:</label>
  <input type="text" class="form-control" id="id" name="id"/>
  <br/>
  <label for="username">활동명:</label>
  <input type="text" class="form-control" id="username" name="username"/>
  <br/>
  <label for="password">비밀번호:</label>
  <input type="password" class="form-control" id="passwd" name="passwd"/>
  <br/>
  <label for="password_check">비밀번호확인:</label>
  <input type="password" class="form-control" id="passwd_check" name="passwd_check"/>
  <br/>
  <label for="email">email:</label>
  <input type="email" class="form-control" id="email" name="email"/>
   
 <br/>
  
  
  <input type="button" id="btn-save" class="btn btn-success btn-block" value="회원가입" />
</form>
<script src="<c:url value='/js/User.js'/>"></script>
</div>
<div class="card-footer">
</div>
</div>
</div>
</body>
</html>