let index = {
			init: function(){
				let _this=this;
				$("#btn-lovers").on("click",function(e){
					e.preventDefault();
					_this.lover();
				});
				$(".btn-reply-delete").on("click",function(){
					_this.deleteReply();
					
					
				});
				$("#btn-add-Rreply").on("click",()=>{
					this.write("re");
				});
				$("#btn-reply-write").on("click",()=>{
					this.write("new");
				});
			},
			lover : function(){
				$.ajax({ type:"POST",
					    async:false,
						url:"/spring/api/lovers/"+$('.board-name').data('board')+"/"+$("#bid").val(),
						contentType: "application/json; charset=utf-8",
						dataType: "text"
				}).done(function(data){
					if(data==0){
						$("#lovers-count").text(parseInt($("#lovers-count").text())+1);
						$("#icon").attr('class','bi-hand-thumbs-up-fill');
					}
					else{
						$("#lovers-count").text(parseInt($("#lovers-count").text())-1);
						$("#icon").attr('class','bi-hand-thumbs-up');
					}
				}).fail(function(err){
					if(err.status=='401'){
						alert("로그인 후 이용해주십시오");
						location.assign(location.origin+"/spring/loginForm");
					}
				});
			},
			write : function(behavior){
				let result=confirm("댓글을 작성하시겠습니까?");
				if(!result){
					return;
				}
				if(behavior=="new"){ // 새 댓글 작성
					var secret=false;
					if($("input:checkbox[name=secret]").is(":checked")==true){
						secret=true;
					}
					var data={
						content : $("#reply-content").val(),
						bid : $("#reply-Id").val(),
						secret : secret
					}
				}
				else{            // 댓글에 댓글 달기
					var secrect=false;
					if($("input:checkbox[name=re-secret]").is(":checked")==true){
						secret=true;
					}
		
					var data={
							content : $("#reply-comment-content").val(),
							bid : $("#reply-Id").val(),
							rgroup : $("#reply-rgroup").val(),
							rstep : $("#reply-rstep").val(),
							secret : secret
					}	
				}
				
				$.ajax({ type:"POST",
						url:"/spring/api/reply/"+$('.board-name').data('board'),
						data: JSON.stringify(data),
						contentType: "application/json; charset=utf-8",
						dataType: "text"
				}).done(function(data){
					alert(data);
					location.assign(location.origin+"/spring/bbs/"+$('.board-name').data('board')+"/"+$("#reply-Id").val());
				}).fail(function(err){
					if(err.status=='401'){
						alert("로그인 후 이용해주십시오");
						location.assign(location.origin+"/spring/loginForm");
					}
					else{
						alert(err.responseText);
					}
				});
			},
			deleteReply : function(target){
				let result=confirm("댓글을 삭제하시겠습니까?");
				if(!result){
					return;
				}
				let rid=target.dataset.rid;
				console.log(rid);
				$.ajax({ type:"DELETE",
					url:"/spring/api/reply/"+$('.board-name').data('board')+"/"+rid,
					contentType: "application/json; charset=utf-8",
					dataType: "text"
				}).done(function(data){
				alert(data);
				location.assign(location.origin+"/spring/bbs/"+$('.board-name').data('board')+"/"+$("#reply-Id").val());
				}).fail(function(err){
					if(err.status=='401'){
						alert("로그인 후 이용해주십시오");
						location.assign(location.origin+"/spring/loginForm");
					}
				});
			}
	
			
}

index.init();