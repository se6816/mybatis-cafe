<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<button class="btn btn-primary" data-toggle="modal" data-target="#report-modal"><i class="bi bi-megaphone-fill"></i></button>
<div class="modal fade" id="report-modal" tabindex="-1" aria-labelledby="report-Label" aria-hidden="true">
  <div class="modal-dialog">
   	<div class="modal-content">
      	<div class="modal-header">
        	<h5 class="modal-title" id="report-Label">관리자에게 신고하기</h5>
        	<button type="button" class="btn-close" data-dismiss="modal" aria-label="Close"></button>
     	</div>
     	<div class="modal-body">
       		<form id="report-form">
       			<input type="hidden" class="form-control" id="report-id" value="${bbsVO.bid}" readonly>
        		<select class="form-control" name="reportType" id="reportType">
			 		<option value="Fword">욕설 신고</option>
			 		<option value="Abusing">반복 글 신고</option>
			 		<option value="Pornogaphy">음란물 신고</option>		 
			 	</select>
			 	<div class="mb-3">
        			<label for="reply-comment-content" class="col-form-label">기타 내용:</label>
         			<textarea class="form-control" id="report-content"></textarea>
        		</div>
       		</form>
       		<p>*은 필수 </p>
     	</div>
     						
      	<div class="modal-footer">
      		 <button type="button" class="btn btn-primary" onClick="sendReport()">신고하기</button>
   		</div>
   	</div>
   </div>
 </div>

<script type="text/javascript">
function sendReport(){
	let select=document.querySelector("#reportType");
	let val=select.options[select.selectedIndex].value;
	let data={
			reportType : val,
			bid : $("#report-id").val(),
			content : $("#report-content").val()
	}
	$.ajax({
		data : JSON.stringify(data),
		type :"POST",
		url : "/spring/api/report",
		contentType: "application/json",
		dataType: "text" 
	  }).done(function(data){
		 alert(data);
			
		}).fail(function(err){
			if(err.status=='401'){
				alert("로그인 후 이용해주십시오");
				location.assign(location.origin+"/spring/loginForm");
			}else{
				alert("서버와 연결이 원활하지 않습니다");
			}
		});
	
}
</script>
</body>
</html>