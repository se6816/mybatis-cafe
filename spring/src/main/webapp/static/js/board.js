let index = {
			init: function(){
				
				$("#btn-write").on("click",()=>{
					this.write();
				});
				$("#btn-delete").on("click",()=>{
					this.deleteboard();
				});
				$("#btn-modify").on("click",()=>{
					this.modify();
				});
				$("#btn-find").on("click",()=>{
					this.find();
				});
			},
			
			write : function(){
				let result=confirm("작성 완료하시겠습니까?");
				if(!result){
					return;
				}
				let select = document.querySelector("#board-bcode");
				let value=select.options[select.selectedIndex].value;
				let array=document.querySelectorAll('div.note-editable > p > img');
				let fid = new Array();
				for(var file of array){
					fid.push(file.dataset.fid);
				}
				let data={
						subject : $("#subject").val(),
						content : $("#content").val(),
						bcode : value,
						fid : fid
				}
				$.ajax({ type:"POST",
						url:"/spring/api/board/"+$('.board-name').data('board'),
						data: JSON.stringify(data),
						contentType: "application/json; charset=utf-8",
						dataType: "text"
				}).done(function(data){
					alert(data);
					location.assign(location.origin+"/spring/bbs/"+$('.board-name').data('board'));
				}).fail(function(err){
					if(err.status=='401'){
						alert("로그인 후 이용해주십시오");
						location.assign(location.origin+"/spring/loginForm");
						return;
					}
					if(err.status=='404'){
						alert(err.responseText);
						return;
					}
					alert("서버와 연결이 원활하지 않습니다");
					return;
				});
			},
			deleteboard : function(){
				let result=confirm("삭제하시겠습니까?");
				if(!result){
					return;
				}
				let bid= $("#bid").val();
				
				$.ajax({ type:"DELETE",
						url:"/spring/api/board/"+$('.board-name').data('board')+"/"+bid,
						contentType: "application/json; charset=utf-8",
						dataType: "text"
				}).done(function(data){
					alert(data);
					location.assign(location.origin+"/spring/bbs/"+$('.board-name').data('board'));
				}).fail(function(err){
					if(err.status=='400'){
						alert("페이지를 찾지 못했습니다");
						return;
					}
					if(err.status=='401'){
						alert("로그인 후 이용해주십시오");
						location.assign(location.origin+"/spring/loginForm");
						return;
					}
					if(err.status=='403'){
						alert(err.responseText);
						return;
					}
					alert("서버와 연결이 원활하지 않습니다");
					return;
				});
			},
			
			modify : function(){
				let result=confirm("수정하시겠습니까?");
				if(!result){
					return;
				}
				let array=document.querySelectorAll('div.note-editable > p > img');
				let fid = new Array();
				for(var file of array){
					fid.push(file.dataset.fid);
				}
			
				
				let data = {
						subject : $("#subject").val(),
						content : $("#content").val(),
						bid :  $("#bid").val(),
						fid : fid
				}
				$.ajax({ type:"PUT",
					url:"/spring/api/board/"+$('.board-name').data('board'),
					contentType: "application/json; charset=utf-8",
					headers:{
						"X-HTTP-Method-Override":"PUT"
					},
					data : JSON.stringify(data),
					dataType: "text"
			}).done(function(data){
				alert(data);
				location.assign(location.origin+"/spring/bbs/"+$('.board-name').data('board')+"/"+$("#bid").val());
			}).fail(function(err){
				if(err.status=='400'){
					alert("페이지를 찾지 못했습니다");
					return;
				}
				if(err.status=='401'){
					alert("로그인 후 이용해주십시오");
					location.assign(location.origin+"/spring/loginForm");
					return;
				}
				if(err.status=='403'){
					alert(err.responseText);
					return;
				}
				if(err.status=='404'){
					alert(err.responseText);
					return;
				}
				alert("서버와 연결이 원활하지 않습니다");
				return;
	
			});
			},
			find : function(){
				let select = document.querySelector("#findType");
				let findType=select.options[select.selectedIndex].value;
				let bcode= $('.board-name').data('bcode');
				location.assign(location.origin+"/spring/bbs/"+$('.board-name').data('board')+"?bcode="+bcode+"&findType="+findType+"&keyword="+$("#keyword").val());
			}
			
		
			
}

index.init();