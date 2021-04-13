<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include file="../include/header.jsp" %>
   
<%@include file="../include/banner.jsp" %>
    <div id="wrapper">
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
    			<form method="post" role="form" id="modifyForm">
    					<div class="form-group">
    					
    						<input type="hidden" id="bid"name="bid" value="${bbsVO.bid}" readonly="true"/>
    						<label for="subject">제목:</label>
 						 	<input type="text" class="form-control" id="subject" name="subject" value="${bbsVO.subject}"/>
  							<br>
  							<label for="content">본문:</label>
 							 <textarea class="form-control summernote" rows="5" id="content" name="content">${bbsVO.content}</textarea>
						</div>
						<div style="float:left;">
							<button type="button" id="btn-List"class="btn btn-primary" onclick="location.href='${pageContext.request.contextPath}/bbs/${boardType.name()}${pagingMaker.makeURI(pagingMaker.pageCria.page,pagingMaker.pageCria.bcode)}'">목록으로</button>
						</div>
						<div style="float:right;">
							<button type="button" id="btn-delete"class="btn btn-primary">삭제하기</button>
						</div>
						<div style="float:right;">
							<button type="button" id="btn-modify" class="btn btn-primary btn-block">수정하기</button>
						</div> 
						
					</form>

    		</div>
    	</div>
    	</div>
    		
    		
    </div>
    <script>
      $('.summernote').summernote({
        placeholder: '글을 입력해주세요',
        codeviewFilter:true,
        tabDisable:false,
        tabsize: 2,
        height: 300,
       	callbacks:{
       			onImageUpload: function(files){
       				for(var i in files){
       					if(files[i].type=="image/png" ||
       						files[i].type=="image/jpg"||
       						files[i].type=="image/bmp"||
       						files[i].type=="image/jpeg"||
       						files[i].type=="image/gif"){
       						sendFile(files[i]);
       					}
       				}	
       				
      			 }
     		 }
      
      	 
      });
      
      function sendFile(file){
    	  let data = new FormData();
    	  data.append("file",file);
    	  $.ajax({
    		  data : data,
    		  type :"POST",
    		  url : "/spring/file/"+$('.board-name').data('board')+"/upload",
    		  contentType: false,
    		  enctype : "multipart/form-data",
    		  processData : false
    	  }).done(function(data){
    		  let url= data.file.reSource_PathName;
    		  let fid= data.file.fileid;
			   $('.summernote').summernote('insertImage',"/spring/"+url,function($image){
				  let width=$image.width();
				  let height=$image.height();
				   if($image.width()>300){
					   width=300;
				   }
				   if($image.height>300){
					   height=300;
				   }
				  $image.css('width',width);
				  $image.css('height',height);
				  $image.attr('data-fid',fid);
			   });
				
			}).fail(function(err){
				console.log(err);
			});
      }
    </script> 
<script src="${pageContext.request.contextPath}/js/Board.js"></script>
<%@include file="../include/footer.jsp" %>