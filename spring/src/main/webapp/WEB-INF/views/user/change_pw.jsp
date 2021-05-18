<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx" crossorigin="anonymous"></script>
<meta charset="UTF-8">
<title>비밀번호 바꾸기</title>
</head>
<body>

<div class="container w-100 mx-auto mt-5">
<h1 class="text-success" align="center">mybatis-카페</h1>
<br>
<div class="card" style="width:500px; margin:auto;">
<div class="card-header"> 
<h5 align="center" style="font-weight: bold;">비밀번호 바꾸기</h5>
</div>
<div class="form-group card-body" >
  <label for="passwd">비밀번호:</label>
  <input type="password" class="form-control" id="passwd"/>
  <label for="passwd-check">비밀번호 확인:</label>
  <input type="password" class="form-control" id="passwd-check"/>
</div>
<div class="card-footer">
<button class="btn btn-success btn-block" id="btn-change-pwd">비밀번호 바꾸기</button>
<br>
</div>
</div>
</div>
<script src="<c:url value='/js/user.js'/>"></script>
</body>
</html>