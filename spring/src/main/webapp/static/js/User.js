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
			console.log(data);
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
			if(!result){
				return;
			}
			let cur_pw=$("#cur_pw").val();
			let change_pw=$("#change_pw").val();
			let change_pw_check=$("#change_pw_check").val();
			if(!checkVal(cur_pw)){
				alert("양식이 부족합니다.");
				$("#cur_pw").focus();
				return;
			} 
			else if(!checkVal(change_pw)){
				alert("양식이 부족합니다.");
				$("#change_pw").focus();
				return;
			}
			else if(!checkVal(change_pw_check)){
				alert("양식이 부족합니다.");
				$("#change_pw_check").focus();
				return;
			}
			if(change_pw!=change_pw_check){
				alert("새 비밀번호가 일치하지 않습니다.");
				return;
			}
			if(change_pw.length<8){
				alert("새 비밀번호가 8글자 이상이어야 합니다.");
				return;
			}
			let data={
					cur_passwd : cur_pw,
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
				$("#cur_pw").text="";
				$("#change_pw").text="";
				$("#change_pw_check").text="";
			}).fail(function(error){
				alert("서버와 연결이 원활하지 않습니다");
			}); 
		},
		checkVal: function(value){
			var tmp =value.replace(/\s| /gi, '');
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