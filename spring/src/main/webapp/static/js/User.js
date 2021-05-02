let index={
		init: function(){
			$("#btn-save").on("click",()=>{
				this.save();
			});
			$("#btn-delete").on("click",()=>{
				this.deleteMember();
			});
			$("#btn-change-pwd").on("click",()=>{
				this.changePw();
			});
			$("#search-ID").on("click",()=>{
				this.findID();
			});
			$("#email-Passwd").on("click",()=>{
				this.findPW();
			});
			
			
		},
		
		save : function(){
			let result=confirm("회원가입하시겠습니까?");
			if(!result){
				return;
			}
			let save_passwd = $("#passwd").val();
			let save_passwd_check = $("#passwd_check").val();
			if(save_passwd!=save_passwd_check){
				alert("비밀번호 확인이 일치하지 않습니다.");
				return;
			}
			let data = {
					id : $("#id").val(),
					username: $("#username").val(),
					password: $("#passwd").val(),
					email: $("#email").val()
			};
			$.ajax({ 
				type: "POST",
				url: "/spring/api/user",
				data: JSON.stringify(data), 
				contentType: "application/json; charset=utf-8",
				dataType: "text" 
			}).done(function(data){
				console.log(data);
				alert(data);
				location.assign(location.origin+"/spring/bbs/main");
				

			}).fail(function(error){
				alert(error.responseText);
			}); 
		},
		deleteMember : function(){
			let result=confirm("회원탈퇴하시겠습니까? 탈퇴하시면 관련 작성글이 삭제되고" +
					"되돌리실수 없습니다.");
			if(!result){
				return;
			}
			$.ajax({ 
				type: "DELETE",
				url: "/spring/api/user",
				contentType: "application/json; charset=utf-8",
				dataType: "text" 
			}).done(function(data){
				alert(data);
				location.assign(location.origin+"/spring/logout");
			}).fail(function(error){
				alert("서버와 연결이 원활하지 않습니다");
			}); 
		},
		changePw : function(){
			let result=confirm("비밀번호를 변경하시겠습니까?");
			let key= location.pathname.substring(location.pathname.lastIndexOf('/')+1);
			if(!result){
				return;
			}
			let change_pw=$("#passwd").val();
			let change_pw_check=$("#passwd-check").val();
			if(!this.checkVal(change_pw)){
				alert("양식이 부족합니다.");
				$("#passwd").focus();
				return;
			}
			else if(!this.checkVal(change_pw_check)){
				alert("양식이 부족합니다.");
				$("#passwd-check").focus();
				return;
			}
			if(change_pw!=change_pw_check){
				alert("새 비밀번호가 일치하지 않습니다.");
				return;
			}
			console.log(change_pw);
			if(change_pw.length<8){
				alert("새 비밀번호가 8글자 이상이어야 합니다.");
				return;
			}
			let data={
					key : key,
					new_passwd : change_pw
			};
			$.ajax({ 
				type: "PUT",
				url: "/spring/api/user",
				contentType: "application/json; charset=utf-8",
				data : JSON.stringify(data),
				dataType: "text" 
			}).done(function(data){
				alert(data);
				location.assign(location.origin+"/spring/bbs/main");
			}).fail(function(error){
				alert(error.responseText);
			}); 
		},
		findID : function(){
			let data={
				email : $("#email").val()
			};
			$.ajax({ 
				type: "POST",
				url: "/spring/api/find/id",
				contentType: "application/json; charset=utf-8",
				data : JSON.stringify(data),
				dataType: "text" 
			}).done(function(data){
				alert(data);
			}).fail(function(error){
				alert(error.responseText);
			}); 
		},
		findPW : function(){
			console.log("aa");
			let data={
					email : $("#email").val()
				};
				$.ajax({ 
					type: "POST",
					url: "/spring/api/change/pw",
					contentType: "application/json; charset=utf-8",
					data : JSON.stringify(data),
					dataType: "text" 
				}).done(function(data){
					alert(data);
				}).fail(function(error){
					alert(error.responseText);
				}); 
		},
		checkVal : function(value){
			let tmp =value.replace(/\s| /gi, '');
			if(tmp == '')
				{
					return false;
				}
			else
				{
				 return true;
				}
		}
		
}

index.init();