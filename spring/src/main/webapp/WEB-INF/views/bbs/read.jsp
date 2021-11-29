<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include file="../include/header.jsp" %>
   
<%@include file="../include/banner.jsp" %>
    <div id="wrapper">
    	<form role="form" method="post">	
				<input type="hidden" id="bid" name="bid" value="${bbsVO.bid}" readonly/>
		</form>
    	<div id="main_content">
    		<div class="line"></div>
    		<div id="menu">	
    			<div class="list-group">
  					<c:forEach var="i" begin="0" end="${boardType.getCategoryLength()-1}">
  					  <button type="button" class="list-group-item list-group-item-action" onclick="location.href='${pageContext.request.contextPath}/bbs/${boardType.name()}?bcode=${boardType.getBcode().get(i)}'">  ${boardType.getCategory().get(i)}  </button>
  					</c:forEach>
				</div>
			</div>
		<div id="section">
			<div class="board-name" data-board="${boardType.name()}"><p><span class="board-text">${boardType.boardName}</span>게시판</p></div>
			<div class="line"></div>
    		<div class="container w-100 mx-auto mt-5">
    				
    			<p class="board-subject">${bbsVO.subject}</p>
    			<br/>
    			<div class="board-writer" >작성자 : <strong>${bbsVO.writer}</strong></div>&nbsp;
    			<div class="board-hit" >조회수 : ${bbsVO.hit}</div>
    			<br/>
    			<hr/>
    			<br/>
    			<br/>
    			<div class="board-content">
    			<p>${bbsVO.content}</p>
    			</div>
    			<div>
    			
    			<p id="lovers-count">${bbsVO.lovers}</p>
    			<button type="button" id="btn-lovers" class="btn btn-primary"><i id="icon" class="bi-hand-thumbs-up"></i></button>
    			<%@include file="../include/report_admin.jsp" %>
    			</div>
    			<div class="download-group">
    				<ul id="download-list">
    				
    				</ul>
    			</div>
    			<div style="float:left;">
					<button type="button" id="btn-List" class="btn btn-primary" onclick="location.href='${pageContext.request.contextPath}/bbs/${boardType.name()}${pagingMaker.makeURI(pagingMaker.pageCria.page,pagingMaker.pageCria.bcode)}'">목록으로</button>
				</div>
				<c:if test="${principal.username==bbsVO.writer}">
					<div style="float:right;">
						<button type="button" id="btn-modify-page" class="btn btn-primary" onClick="location.href= '${pageContext.request.contextPath}/bbs/${boardType.name()}/${bbsVO.bid}/modify${pagingMaker.makeURI(pagingMaker.pageCria.page,pagingMaker.pageCria.bcode)}'">수정하기</button>
					</div>	
				</c:if>
				<c:if test="${isAdmin}">
					
					<div style="float:right;">
						<button type="button" id="btn-delete"class="btn btn-primary">관리자 삭제하기</button>
					</div>	
					
				 </c:if>

    		</div>
    		
    		<br/>
    		<br/>
    		
    		
    		<div class="card" id="reply_Input">
    			<form>
    				<div class="card-body">
    					<label for="secret">비밀 글</label> <input type="checkbox" id="secret" name="secret" value="secret"/>
    					<br/>
    					<input type="hidden" id="reply-Id" value="${bbsVO.bid}" />
    					<textarea  class="form-control" rows="2" id="reply-content"name="reply_content"></textarea>
    				</div>
    				<div class="card-footer">
    					<div style="text-align:right;">
							<button type="button" id="btn-reply-write" class="btn btn-primary">입력</button>
						</div>
					</div>
				</form>  
    		</div>
    		<div class="card">
    				<ul id="reply-list">
    				
    				</ul>
    					<button id="btn-add-reply" class="btn btn-secondary" data-page=1><i class="bi-chevron-down"></i></button>
    				<div class="modal fade" id="reply-comment-modal" tabindex="-1" aria-labelledby="reply-comment-Label" aria-hidden="true">
  						<div class="modal-dialog">
   							 <div class="modal-content">
      							<div class="modal-header">
        							<h5 class="modal-title" id="reply-comment-Label">답변 달기</h5>
        							<button type="button" class="btn-close" data-dismiss="modal" aria-label="Close"></button>
     							 </div>
     							 <div class="modal-body">
       								 <form id="form">
       								 		
           								 <div class="mb-3">
           								 	<input type="hidden" class="form-control" id="reply-rgroup" readonly>
          									<input type="hidden" class="form-control" id="reply-rstep" readonly>
          									<label for="re-secret" class="col-form-label">비밀 글</label> 
          									<input type="checkbox" id="re-secret" name="re-secret" value="secret"/>
    									</div>
      						 			  <div class="mb-3">
        								    <label for="reply-comment-content" class="col-form-label">내용:</label>
         									  <textarea class="form-control" id="reply-comment-content"></textarea>
        									</div>
       								 </form>
     							 </div>
     						
      				<div class="modal-footer">
      					  <button type="button" class="btn btn-primary" id="btn-add-Rreply">답글 달기</button>
   				   </div>
   				   </div>
   				 </div>
 				 </div>
 				 	
    					
    			</div>
 				 
				
    		
    	</div>
    	</div>
    		
    		
    </div>
<script>
$('#reply-comment-modal').on('show.bs.modal', function (event) {
	  var button = $(event.relatedTarget);
	  var recipient = button.data('whatever');
	  var rgroup= button.data('rgroup');
	  var rstep= button.data('rstep');
	  var modal = $(this);
	  modal.find('.modal-title').text(recipient+ "에게 답변 달기");
	  modal.find('.modal-body input:hidden[id=reply-rgroup]').val(rgroup);
	  modal.find('.modal-body input:hidden[id=reply-rstep]').val(rstep);
	})
</script>
<script src="${pageContext.request.contextPath}/js/reply.js"></script>
<script>
$("#btn-add-reply").on("click",function(){
	let target=this;
	let data={
			page : target.dataset.page
	}
	$.ajax({ 
		type:"GET",
		url:"/spring/reply/"+$('.board-name').data('board')+"/"+$("#bid").val(),
		data : data,
		contentType: "application/json; charset=utf-8",
		dataType: "json"
	}).done(function(data){
		let html="";
		let username='<c:out value="${principal.username}"/>';
		for(var reply of data.list){
			let date=new Date(reply.regdate);
			html+="<li class='reply-box ";
			if(reply.rstep>0){
				html+="add-reply";
			}
			html+="'>";
			if(reply.delYn===true){
				html+="<p>이미 삭제된 댓글입니다</p>";
				html+="</li>";
				continue;
			}
			html+="<p style='text-align:right;'>작성날짜 :"+moment(date).format('YYYY-MM-DD')+"</p>";
			html+="<p>작성자 :"+reply.writer+"</p>";
			if(reply.secret===true &&!(username===reply.writer || 
									  username===reply.Bwriter)){
				html+="<p>비밀글입니다</p>";
			}else{
				html+="<p class='collapse' id='collapse-"+reply.rid+"' aria-expanded='false'>"+reply.content+"</p>";
			}
			html+="<div class='paper-button";
			if(reply.length<100){
				html+=" hide";
			}
			html+="'>";
			html+="<a role='button' class='collapsed' data-toggle='collapse' href='#collapse-"+reply.rid+"' aria-expanded='false' aria-controls='"+reply.rid+"'></a>";
			html+="</div>";
			html+=`<p style='text-align:right;'>`;
			html+="<button type='button' class='btn btn-primary' data-toggle='modal' data-target='#reply-comment-modal' data-whatever='"+reply.writer+"' data-rgroup='"+reply.rgroup+"'>답변</button> ";
			if(username===reply.writer){
				html+=`<button type='button' class='btn btn-primary btn-reply-delete' onClick="index.deleteReply(this,'`+reply.rid+`')">삭제</button>`;
			}
			
			<c:if test="${isAdmin}">
				html+=`<button type='button' class='btn btn-primary btn-reply-delete' onClick="index.deleteReply(this,'`+reply.rid+`')">관리자 삭제</button>`;
				
			 </c:if>
			html+="</p>";
			html+="</li>";
		}
		$("#reply-list").append(html);
		if(data.list.length<10)
			$(target).remove();
		else
			target.dataset.page++;	
	}).fail(function(err){
		alert("서버와 연결이 원활하지 않습니다");
		console.log(err);
	});
});
</script>

<script defer>
	var arr= document.querySelectorAll("div.board-content > p > img");
	var html="";
	for(var file of arr){
		console.log(file);	
		let name_Idx=file.getAttribute("src").lastIndexOf("/")+1;
		let file_Name=file.getAttribute("src").substring(name_Idx);
		html+="<a target='_blank' href='/spring/download/"+$('.board-name').data('board')+"/"+file.dataset.fid+"'><li>"+file_Name+"</li></a>";
	}
	let list= document.querySelector("#download-list");
	$("#download-list").empty();
	$("#download-list").append(html);
	<c:if test="${isLovers}">
		$("#icon").attr('class','bi-hand-thumbs-up-fill');
	</c:if>
	$("#btn-add-reply").click();
</script>


<%@include file="../include/footer.jsp" %>



