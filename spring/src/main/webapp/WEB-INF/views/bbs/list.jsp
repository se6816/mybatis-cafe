<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
  
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
			<div class="board-name" data-board="${boardType.name()}" data-bcode="${pagingMaker.pageCria.bcode}"><p><span class="board-text">${boardType.boardName}</span>게시판</p>
			</div>
			<div class="line"></div>
			<div class="form=group" style="float:right;">
			 <select class="form-control" name="sort" id="sort" onchange="sort()">
			 		<option value="popular">가장 인기있는 순</option>
			 		<option value="recent">가장 최근 순</option>		 
			 </select>
			</div> 
    		<table class="table table-bordered table-hover" id="table">
  			<thead>
   			 <tr style="background-color:#eff;">
    			  <th scope="col">글번호</th>
     			 <th scope="col">제목</th>
     			 <th scope="col">작성자</th>
     			 <th scope="col">작성일</th>
     			 <th scope="col">조회수</th>
   			 </tr>
  			</thead>
 			 <tbody>
 			  <c:forEach items="${list}" var="bvo">
   				 <tr>
     				 <th scope="row">${bvo.bid}</th>
    				  <td><a class="list-subject" target="_blank" href="${pageContext.request.contextPath}/bbs/${boardType.name()}/${bvo.bid}${pagingMaker.makeURI(pagingMaker.pageCria.page,pagingMaker.pageCria.bcode)}">${bvo.subject}</a>
    				  		<strong>[${bvo.replyCount}]</strong>
    				  </td>
    				  <td>${bvo.writer}</td>
    				 	<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${bvo.regdate}"/></td>
    				  	
    				  <td>${bvo.hit}</td>
  				  </tr>
  		    </c:forEach>
			</tbody>
			</table>
			<a href="${boardType.name()}/write${pagingMaker.makeURI(pagingMaker.pageCria.page,pagingMaker.pageCria.bcode)}">
				<button style="float:right;"type="button"  class="btn btn-primary">쓰기
			</button></a>
			<br/>	
			<br/>
			<nav aria-label="paging list">
 				<ul class="pagination pagination-sm justify-content-center">
  				  <c:choose>
  				  	<c:when test="${pagingMaker.prev == true}">
  				 	 	<li class="page-item">
     					 	<a class="page-link" href="${boardType.name()}${pagingMaker.makeURI(pagingMaker.startPage-10,pagingMaker.pageCria.bcode)}" aria-disabled="true" aria-label="Previous">
     						 	<span aria-hidden="true">&laquo;</span>
     				 		</a>
  				  		</li>
  				  	</c:when>
  				  	<c:otherwise>
  				  		<li class="page-item disabled">
     						 <a class="page-link" href="#" tabindex="-1" aria-disabled="true" aria-label="Previous">
     					 		<span aria-hidden="true">&laquo;</span>
     				 		</a>
  				  		</li>
  				  	</c:otherwise>
  				  
  				  </c:choose>
  				  <c:forEach begin="${pagingMaker.startPage}" end="${pagingMaker.endPage}" var="Pnum">
  				  		<c:choose>
  				  			<c:when test="${pagingMaker.pageCria.page==Pnum}">
  				  				<li class="page-item disabled">
  				  					<a class="page-link active" href="#">${Pnum}<span class="sr-only">(current)</span></a>
  				  				</li>
  				  			</c:when>
  				  			<c:otherwise>
  				  				<li class="page-item">
  				  					<a class="page-link" href="${boardType.name()}${pagingMaker.makeURI(Pnum,pagingMaker.pageCria.bcode)}">${Pnum}</a>
  				  					
  				  				</li>
  				  			</c:otherwise>
  				  		</c:choose>
  				  </c:forEach>
   				  <c:choose>
   				  <c:when test="${pagingMaker.next&&pagingMaker.endPage > 0}">
   				  	<li class="page-item">
   				     <a class="page-link" href="${boardType.name()}${pagingMaker.makeURI(pagingMaker.endPage+1,pagingMaker.pageCria.bcode)}" aria-label="Next">
   				     	<span aria-hidden="true">&raquo;</span>
   				     </a>
  				    </li>
   				  </c:when>
   				  <c:otherwise>
   				    	<li class="page-item disabled">
   				   		  <a class="page-link" href="#"  tabindex="-1" aria-label="Next">
   				   		  	<span aria-hidden="true">&raquo;</span>
   				   	 	 </a>
  				   	 	</li>
   				  	</c:otherwise>
   				  </c:choose>
    			  
 		 	   </ul>
			</nav>
			<div class="find-section">
				<select name="findType" id="findType" class="col-md-2">
					<option value="N"
						<c:out value="${pagingMaker.pageCria.findType==null? 'selected':''}"/>>
				    ------------</option>
					<option value="S"
						<c:out value="${pagingMaker.pageCria.findType=='S'? 'selected':''}"/>>제목</option>
					<option value="C"
					<c:out value="${pagingMaker.pageCria.findType=='C'? 'selected':''}"/>>내용</option>
					<option value="W"
					<c:out value="${pagingMaker.pageCria.findType=='W'? 'selected':''}"/>>작성자</option>
					<option value="SC"
					<c:out value="${pagingMaker.pageCria.findType=='SC'? 'selected':''}"/>>제목+내용</option>
				</select>
				<input type="text" name="keyword" id="keyword" value="${pagingMaker.pageCria.keyword}"/>
				<button type="button" id="btn-find">검색</button>
    		</div>
    	</div>
    	
    
    </div>
    		
    		
    		
   </div>


      
<%@include file="../include/footer.jsp" %>

<script type="text/javascript">
	let select = document.querySelector("#sort");
	for(let i=0; select.options.length;i++){
		if(select.options[i].value=="${pagingMaker.pageCria.sortType.name()}"){
			select.options[i].selected=true;
			break;
		}
	}	
</script>
<script src="${pageContext.request.contextPath}/js/Board.js"></script>

<script type="text/javascript">
function sort(){
	let select = document.querySelector("#sort");
	let val=select.options[select.selectedIndex].value;
	location.assign("${pageContext.request.contextPath}/bbs/${boardType.name()}${pagingMaker.remakeURI(pagingMaker.pageCria.page,pagingMaker.pageCria.bcode)}&sortType="+val);
}
</script>

