var app = angular.module('app', []).controller('findPassword',function($http, $location, $scope) {
	$http.defaults.withCredentials = true;$http.defaults.headers = {'Content-Type' : 'application/x-www-form-urlencoded'};
	$scope.vsphone = function(telephone) {// 验证手机号码
		var length = (telephone + '').length;
		var mobile = /^1[3|4|5|8][0-9]\d{4,8}$/;
		return telephone && length == 11&& mobile.test(telephone);
	};
	$scope.vertelephone = function() {// 验证手机号码
		var ct = $scope.vsphone($scope.telephone);
		if(ct){
				$.post( ER.root+"/i/user/existenceUserName", {userName: $scope.telephone}, function(data) {
	                if(data){
	                	$("#mention1").html("");
	                	$("#but_vercode").attr("disabled", !ct).css("background-color", ct ? "#438BCB" : "#cccccc");
					}else{
						$("#mention1").html("系统未找到该用户信息！");
					}
				});//
		}else{
			$("#mention1").html("");
			$("#but_vercode").attr("disabled", true).css("background-color", "#cccccc");
		}
	};
	
	$scope.getVerCode = function() {
		setTime(document.getElementById("but_vercode"));
		$scope.getMobileCode('user_findwpd', $scope.telephone,'#but_vercode');
	};
	$scope.getMobileCode = function(key, telephone, vcid) {//获取验证码
		$http.get(ER.root+ "/i/ShareRdcController/sharvistPhone.json",{params : {key : 'user_findwpd',telephone : telephone}}).success(function(data) {
				if (data.success) {$scope.mtvarcode = data.entity;$(vcid).data('vc', true);}
				//alert(data.message);
				layer.open({
    		 	    content: data.message
    		 	    ,btn: '确定'
    		 	  });
		});
	};
    $scope.veteleCode = function() {// 验证码
	 if ($("#but_vercode").data('vc') == true) {
		var ct = $scope.verrcode&& $scope.mtvarcode&& ($scope.mtvarcode.toLowerCase() == $scope.verrcode.toLowerCase());
		$("#app_but1").attr("disabled", !ct);
		if (ct) {$("#app_but1").removeClass("gray");} else {$("#app_but1").addClass("gray");}
	} 
//	 else {
//		$http.get(ER.root+ "/i/ShareRdcController/sharvistCode",{params : {key : 'user_findwpd',telephone :$scope.telephone,yzm : $scope.verrcode}}).success(function(data) {
//			if (data) {
//				$("#app_but1").attr("disabled", false);
//				$("#app_but1").removeClass("gray");
//			} else {
//				$("#app_but1").attr("disabled", true);$("#app_but1").addClass("gray");
//			}
//		});
//	}
   };
   $scope.checkData=function (){ 
  	      var password = $("#txt_password").val(); 
          var repsword = $("#txt_repsword").val(); 
           if (password.length!=0 && repsword.length!=0) {
//        	   if(password.length<6){
//        		   $("#mention2").html("密码长度不能小于6位！");
//        		    return false;
//        	   }
           	   if(password != repsword) {
		         $("#mention2").html("两次密码输入不一致，请重新输入");
		         return false;
		       }
		       else {
		    		$scope.savedata();
		       }
           } else{
        	 if(password.length==0){
        		 $("#txt_password").focus();
        	 }else if(repsword.length==0){
        		 $("#txt_repsword").focus();
        	 }
           	  $("#mention2").html("密码不能为空哦，请输入密码~");
           }
  	  };
   	$scope.savedata= function () {// 修改密码
		var me = "#btn_login"; if ($(me).data('isLoading') === true) return;$(me).text("提交中...");$("#mention2").html(""); //防止再次点击
        $.ajax({
        	type: 'POST',
        	data: {key:'user_findwpd',username:$scope.telephone,toke:$scope.verrcode,password:$("#txt_repsword").val()},
            url: ER.root+"/i/user/upPwdByTelephone",
            complete : function(e){$(me).text("确定"); $(me).delay(500).data('isLoading',false);},
            success: function(data){
            	if(data.success){
            		//alert("密码重置成功！");
            		layer.open({
	    		 	    content: '密码重置成功咯~'
	    		 	    ,btn: '确定'
	    		 	  });
            		window.location.href = "login.html";
            	}else{
            		$("#mention2").html("修改失败~请稍后重试~");
            	} }
          });
	};
	$scope.initevg = function() {
		$("#app_but1").click(function() {
			$("#stup1").hide();
			$("#stup2").show();
			$("#title_div").html("修改密码");
		});
	};
	$scope.initevg();
	var tel = util.getCookie("username");if ($scope.vsphone(tel)) {$scope.telephone=tel; $("#but_vercode").attr("disabled", false).css("background-color", "#438BCB" );}
});
