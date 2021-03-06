wlsWeb.controller('my-space',function($http, $location,$rootScope, $scope,$state,Upload) {
	 $scope.load = function(){
		 $http.get('/i/user/findUser').success(function (data) {
			if(data!=null&&data.id!=undefined){
					 $scope.user = data;
					 /*$scope.educates = {};
					 $scope.skills = {};
					 $scope.honors = {};
					 $scope.jobs = {};*/
					 $scope.skilldegree=0;
					 $scope.followerNum = 0;
					 $http.get('/i/user/findFollowerByUserId', {
				            params: {
				                "userID": $scope.user.id
				            }
				        }).success(function (data) { 
				        	$scope.followers = data;
				        	$scope.followerNum = $scope.followers.length;
				     });
					 $scope.findMessageByReceiverId();
					 $http.get('/i/user/findEducateByUserID', {
				            params: {
				                "userID": $scope.user.id
				            }
				        }).success(function (data) { 
				        	if(data.success){
				        		$scope.educates = data.data;
				        	}
				     });
					 $http.get('/i/user/findSkillByUserID', {
				            params: {
				                "userID": $scope.user.id
				            }
				        }).success(function (data) { 
				        	if(data.success){
				        		$scope.skills = data.data;
				        	}
				        	else{
				        		$scope.skills = undefined;
				        	}
				     });
					 $http.get('/i/user/findHonorByUserID', {
				            params: {
				                "userID": $scope.user.id
				            }
				        }).success(function (data) { 
				        	if(data.success){
				        		$scope.honors = data.data;
				        	}
				     });
					 $http.get('/i/user/findJobByUserID', {
				            params: {
				                "userID": $scope.user.id
				            }
				        }).success(function (data) { 
				        	if(data.success){
				        		$scope.jobs = data.data;
				        	}
				     });
					 $scope.publishNum = 0;
					 $http.get('/i/publish/findPublishByUserId', {
				            params: {
				                "userID": $scope.user.id
				            }
				        }).success(function (data) { 
				        	if(data!=null){
				        	$scope.publishs = data;
				        	$scope.publishNum = $scope.publishs.length;
				        	}
				     });
					 $http.get('/i/city/findCitysByProvinceId', {
				            params: {
				                "provinceID": $scope.user.provinceid
				            }
				        }).success(function (data) {
				            $scope.citys = data;
				        });
					 $http.get('/i/city/findSchoolsByCityId', {
				            params: {
				                "cityID": $scope.user.cityid
				            }
				        }).success(function (data) {
				            $scope.schools = data;
				        });
					 
					 if($scope.user.roleid!=null){
						 $http.get('/i/user/findRoleById', {
					            params: {
					                "roleID": $scope.user.roleid
					            }
					        }).success(function (data) {
					            $scope.userRole = data.name;
					     });
						 }
						 else{
							 $scope.userRole = '';
						 }
					 
					 if($scope.user.provinceid!=null){
					 $http.get('/i/city/findProvinceById', {
				            params: {
				                "provinceID": $scope.user.provinceid
				            }
				        }).success(function (data) {
				            $scope.userProvince = data.pr_province;
				     });
					 }
					 else{
						 $scope.userProvince = '';
					 }
					 if($scope.user.cityid!=null){
					 $http.get('/i/city/findCityById', {
				            params: {
				                "CityID": $scope.user.cityid
				            }
				        }).success(function (data) {
				            $scope.userCity = data.ci_city;
				     });
					 }
					 else{
						 $scope.userCity = '';
					 }
					 if($scope.user.schoolid!=null){
						 $http.get('/i/city/findSchoolById', {
					            params: {
					                "schoolID": $scope.user.schoolid
					            }
					        }).success(function (data) {
					            $scope.userSchool = data.sh_shool;
					     });
						 }
					else{
							 $scope.userSchool = '';
						 }
					  if($scope.user.sex==0){
							$scope.user.sex = "男";
						}
					  else if($scope.user.sex==1){
							$scope.user.sex = "女";
						}
						if($scope.user.score==undefined||$scope.user.score==null||
								$scope.user.score<100){
							$scope.level = 1;
						}
						else if($scope.user.score<500&&$scope.user.score>=100){
							$scope.level = 2;
						}
						else if($scope.user.score>=500){
							$scope.level = 3;
						}
						if($scope.user.nickname==undefined||$scope.user.age==undefined
								||$scope.user.sex==undefined||$scope.user.cityid==undefined
								||$scope.user.provinceid==undefined||$scope.user.schoolid==undefined
								||$scope.user.major==undefined||$scope.user.interest==undefined
								||$scope.user.roleid==undefined||$scope.user.signature==undefined||
								($scope.user.skill1==undefined&&$scope.user.skill2==undefined)
								){
							$("#personsal-info_alter").modal("show");
						}
					 }
					else{
						alert("请先登录");
						window.location.href="#/login";
					}
	        });
		   };
		$scope.load();
		
		
		$scope.refreshUser = function(){
			 $http.get('/i/user/findUser').success(function (data) {
						 $scope.user = data;
			 });
		 };
		 
		 $scope.findMessageByReceiverId = function(){
			 $http.get('/i/message/findMessageByReceiverId', {
		            params: {
		                "userID": $scope.user.id
		            }
		        }).success(function (data) { 
		        	$scope.messages = data;
		        	$scope.messageNum = $scope.messages.length;
		     });
		 };
		 // 获取省列表
	    $http.get('/i/city/findProvinceList').success(function (data) {
	        $scope.provinces = data;
	        if($scope.user.provinceid==null)
	        	$scope.user.provinceid = data[0].pr_id;
	    });
	    
	    
	    // 获取角色列表
	    $http.get('/i/user/findRoles').success(function (data) {
	        $scope.roles = data.data;
	        if($scope.user.roleid==null)
	        	$scope.user.roleid = data.data[0].id;
	    });
	   
	    // 根据省ID查询城市列表
	    $scope.provinceSelected = function () {
	        $http.get('/i/city/findCitysByProvinceId', {
	            params: {
	                "provinceID": $scope.user.provinceid
	            }
	        }).success(function (data) {
	            $scope.citys = data;
	               $scope.user.cityid = data[0].ci_id;
	            if($scope.user.cityid==1||$scope.user.cityid==2||
	            		$scope.user.cityid==3||$scope.user.cityid==4){
	            	$scope.citySelected();
	            }
	        });
	    };
	    
	 // 根据城市ID查询学校列表
	    $scope.citySelected = function () {
	        $http.get('/i/city/findSchoolsByCityId', {
	            params: {
	                "cityID": $scope.user.cityid
	            }
	        }).success(function (data) {
	            $scope.schools = data;
	               $scope.user.schoolid = data[0].sh_id;
	        });
	    };
		$scope.saveInfo = function() {
			if($scope.user.skill1.length>4||$scope.user.skill2.length>4){
				alert("技能不能超过4个字");
				return;
			}
			if($scope.user.interest.length>15){
				alert("兴趣爱好不能超过15个字");
				return;
			}
			if($scope.user.sex=="男"){
				$scope.sex = 0;
			}
			else{
				$scope.sex = 1;
			}
		    	$http.get( "/i/user/updateUser",{
		    		params : {
		    			signature : $scope.user.signature,
		    			sex : $scope.sex,
		    			
		    			pr_id : $scope.user.provinceid,
		    			ci_id : $scope.user.cityid,
		    			sh_id : $scope.user.schoolid,
		    			major : $scope.user.major,
		    			roleid : $scope.user.roleid,
		    			nickname : $scope.user.nickname,
		    			interest : $scope.user.interest,
		    			age : $scope.user.age,
		    			skill1 : $scope.user.skill1,
		    			skill2 : $scope.user.skill2
		    		}
		    	}).success(function(data) {
		    		if(data.success){
		    			$state.reload();
		    		}
		    		else{
		    			alert("信息修改失败");
		    		}
			   });
		   };
		   
		  
		   
		   $scope.updateAvatar = function() {
			   var useravatar = $('#useravatar').get(0).files[0];
			   if(useravatar==null||useravatar==undefined){
				   alert("请选择图片");
				   return;
			   }
			   data = {
					   useravatar: useravatar
		            };
			   Upload.upload({
	               url: '/i/user/updateAvatar',
	               headers :{ 'Content-Transfer-Encoding': 'utf-8' },
	               data: data
	           }).then(function (resp) {
	    		   window.location.reload();
	           });
	       };
	       
	       
	       $scope.updatePhoto = function() {
			   var userphoto = $('#userphoto').get(0).files[0];
			   if(userphoto==null||userphoto==undefined){
				   alert("请选择图片");
				   return;
			   }
			   data = {
					   userphoto: userphoto
		            };
			   Upload.upload({
	               url: '/i/user/updatePhoto',
	               headers :{ 'Content-Transfer-Encoding': 'utf-8' },
	               data: data
	           }).then(function (resp) {
	        	   $scope.refreshUser();
	        	   $("#edit_photo").modal("hide");
	           });
	       };
		   
	   function delcfm() {
		        if (!confirm("确认要删除？")) {
		            return false;
		        }
		        return true;
		}
		
	   $scope.cancel = function () {
		   $state.reload();
	   };
	   
	    $scope.deleteEducate = function (educateID) {
	    	if(delcfm()){
	    	$http.get('/i/user/deleteEducate', {
	            params: {
	                "educateID": educateID
	            }
	        }).success(function (data) {
	        	if(data.success){
	    			//alert("删除成功");
	    			$scope.educates = data.data;
	    		}
	        });
	    	}
	    };
		   
		   $scope.saveEducate = function() {
			   $scope.educatefulltime=0;
			    	$http.get( "/i/user/addEducate",{
			    		params : {
			    			educatestarttime : $("#educatestarttime")[0].value,
			    			educateendtime : $("#educateendtime")[0].value,
			    			educateschool : $scope.educateschool,
			    			educatedegree : $scope.educatedegree,
			    			educatefulltime : $scope.educatefulltime,
			    			educateuserid : $scope.user.id,
			    			educatemajor : $scope.educatemajor,
			    			educatedescribe : $scope.educatedescribe,
			    		}
			    	}).success(function(data) {
			    		if(data.success){
			    			 $("#education_form").css("display","none");
			    		       $("#body_edu").css("display","");
			    		       $("#body_edu_icon").css("display","");
			    			//alert("教育经历添加成功");
			    			 $scope.educates = data.data;
			    		}
			    		else{
			    			alert("教育经历添加失败");
			    		}
				   });
			   };
			   
			   
			   
			   $scope.deleteJob = function (jobID) {
			    	if(delcfm()){
			    	$http.get('/i/user/deleteJob', {
			            params: {
			                "jobID": jobID
			            }
			        }).success(function (data) {
			        	if(data.success){
			    			//alert("删除成功");
			    			 $scope.jobs = data.data;
			    		}
			        });
			    	}
			    };
				   
				   $scope.saveJob = function() {
					    	$http.get( "/i/user/addJob",{
					    		params : {
					    			jobstarttime : $("#jobstarttime")[0].value,
					    			jobendtime : $("#jobendtime")[0].value,
					    			jobcompany : $scope.jobcompany,
					    			jobposition : $scope.jobposition,
					    			jobcompanysize : $scope.jobcompanysize,
					    			jobindustry : $scope.jobindustry,
					    			jobcompanynature : $scope.jobcompanynature,
					    			jobdescribe : $scope.jobdescribe,
					    			jobuserid : $scope.user.id
					    		}
					    	}).success(function(data) {
					    		if(data.success){
					    			 $("#work_form").css("display","none");
					    			//alert("工作经历添加成功");
					    			 $scope.jobs = data.data;
					    		}
					    		else{
					    			alert("工作经历添加失败");
					    		}
						   });
					   };
					   
					   
					   
					   $scope.deleteHonor = function (honorID) {
					    	if(delcfm()){
					    	$http.get('/i/user/deleteHonor', {
					            params: {
					                "honorID": honorID
					            }
					        }).success(function (data) {
					        	if(data.success){
					    			//alert("删除成功");
					    			$scope.honors = data.data;
					    		}
					        });
					    	}
					    };
						   
						   $scope.saveHonor = function() {
							    	$http.get( "/i/user/addHonor",{
							    		params : {
							    			honorstarttime : $("#honorstarttime")[0].value,
							    			honorendtime : $("#honorendtime")[0].value,
							    			honorhonor : $scope.honorhonor,
							    			honoruserid : $scope.user.id
							    		}
							    	}).success(function(data) {
							    		if(data.success){
							    			 $("#honor_form").css("display","none");
							    			//alert("所获荣誉添加成功");
							    			$scope.honors = data.data;
							    		}
							    		else{
							    			alert("所获荣誉添加失败");
							    		}
								   });
							   };
							   
							   
							   $scope.deleteSkill = function (skillID) {
							    	if(delcfm()){
							    	$http.get('/i/user/deleteSkill', {
							            params: {
							                "skillID": skillID
							            }
							        }).success(function (data) {
							        	if(data.success){
							    			//alert("删除成功");
							    			$scope.skills = data.data;
							    		}
							        });
							    	}
							    };
								   
								   $scope.saveSkill = function() {
									    	$http.get( "/i/user/addSkill",{
									    		params : {
									    			skillskill : $scope.skillskill,
									    			skilldegree : $scope.skilldegree,
									    			skilluserid : $scope.user.id
									    		}
									    	}).success(function(data) {
									    		if(data.success){
									    			 $("#skill_form").css("display","none");
									    			//alert("技能添加成功");
									    			$scope.skills = data.data;
									    		}
									    		else{
									    			alert("技能添加失败");
									    		}
										   });
									   };
									   
									   
									   $scope.saveResume = function() {
									    	$http.get( "/i/user/updateResume",{
									    		params : {
									    			realname : $scope.user.realname,
									    			intention : $scope.user.intention,
									    			age : $scope.user.age,
									    			address : $scope.user.address,
									    			telephone : $scope.user.telephone,
									    			introduction : $scope.user.introduction,
									    			email : $scope.user.email
									    		}
									    	}).success(function(data) {
									    		if(data.success){
									    			$('#save_resume_alter').modal("show");
									    			$scope.refreshUser();
									    			$("#edit_education").css("display","");
									    		    $("#cancel_education").css("display","none");
									    		    $("#save_education").css("display","none");
									    		    $("#resume").css("display","");
									    		    $("#resume_edit").css("display","none");
									    		}
									    		else{
									    			alert("简历修改失败");
									    		}
										   });
									   };
									   
									   
									   $scope.deleteMessage  = function(messageID) {
										   if(delcfm()){
									    	$http.get( "/i/message/deleteMessage",{
									    		params : {
									    			msgID : messageID,
									    			messageSenderID : $scope.user.id
									    			}
									    	}).success(function(data) {
									    		//alert("删除成功");
									    		$scope.findMessageByReceiverId();
										   });
										   }
									   };
									   
									   $scope.messageResponse  = function(messageID,messageReceiverID,msgcategory
											   ,resopnseMsg) {
									    	$http.get( "/i/message/messageResponse",{
									    		params : {
									    			messageID : messageID,
									    			messageReceiverID : messageReceiverID,
									    			messageResponse : resopnseMsg,
									    			msgcategory : msgcategory,
									    			messageSenderID : $scope.user.id
									    		}
									    	}).success(function(data) {
									    		$("#quick_reply_alter").modal("show");
									    		$scope.findMessageByReceiverId();
										   });
									   };
									   
									   $scope.deletePublish  = function(publishID) {
										   if(delcfm()){
									    	$http.get( "/i/publish/deletePublish",{
									    		params : {
									    			publishID : publishID,
									    			userID : $scope.user.id
									    			}
									    	}).success(function(data) {
									    		//alert("删除成功");
									    		$scope.publishs = data;
									        	$scope.publishNum = $scope.publishs.length;
										   });
										   }
									   };
									   
									   $scope.goUserSpace = function(userID) {
										   $http.get('/i/user/findUserByID', {
									            params: {
									                "spaceUserID": userID
									            }
									        }).success(function(data){
												 $scope.user = data;
												 if($scope.user.suproleid==1){
													 $state.go('my-space-ask', {"spaceID": userID});
												 }
												 else if($scope.user.suproleid==2){
													 $state.go('my-space-company-ask', {"spaceID": userID});
												 }
												 else{
													 alert("用户不存在！！");
												 }
								    	 });
										};
									    
									   
									   $scope.goBlogInfo = function(publishID) {
									      	 $state.go('blog-info', {"publishID": publishID});
									   	};
									   	
									   	$scope.schoolid = -1;
										$('body')
										 .on('click', '#optionsRadios1', function(e) {
											    $scope.schoolid = -1;
										        $("#select_school").css("display","none");
										    });
										 $('body')
										    .on('click', '#optionsRadios2', function(e) {
										        $("#select_school").css("display","");
										    });
									   	$scope.updatePublish  = function(publishID) {
									   		$("#tab_2list").css("display","none");
									   		$("#tab-2update").css("display","");
									   	    $http.get('/i/publish/findPublishByID', {
								                params: {
								                    "publishID": publishID
								                }
								            }).success(function(data,status,config,headers){
								                $scope.publish = data;
								                /*$scope.totalPicFiles = $scope.publish.picFiles;
								                $scope.totalAppendixs = $scope.publish.appendixDtos;
								                $scope.totalVideoFiles = $scope.publish.pubVideo;*/
								                $scope.title = $scope.publish.title;
								                $scope.schoolid = $scope.publish.schoolid;
								                $scope.content = $scope.publish.content;
								                if($scope.schoolid!=-1&&$scope.schoolid!=null&&$scope.schoolid!=undefined){
								                	 $("input[name='optionsRadios1']").eq(1).attr("checked","checked");
								                	 $("#select_school").css("display","");
								                	 $http.get('/i/city/findSchoolById', {
								         	            params: {
								         	                "schoolID": $scope.schoolid
								         	            }
								         	        }).success(function(data){
								         				 $scope.defaultschool = data;
								         				$scope.schoolname=$scope.defaultschool.sh_shool; 
								            	    });
								                }
								                else{
								                	 $("input[name='optionsRadios1']").eq(0).attr("checked","checked");
								                }
								                $("input[name='optionsRadios3']").eq($scope.publish.pubcategory-1).attr("checked","checked");
								                document.getElementById("contentdetail").innerHTML=$scope.publish.content;
								             });
									    };
									    
									    $scope.searchSchool = function(schoolname){
									    	if(schoolname==''){
									    		$("#schoolUl").css("display","none");
									    	}
									    	else{
									    		 $http.get('/i/city/findSchoolByName', {
									    	            params: {
									    	                "schoolName": schoolname
									    	            }
									    	        }).success(function (data) {
									     	    	$scope.totalSchools = data;
									     	    	$("#schoolUl").css("display","");
									     	    });
									    	}
									    };
									    $scope.chooseSchool = function(school){
									    	$scope.schoolname = school.sh_shool;
									    	$("#schoolUl").css("display","none");
									    	$scope.schoolid = school.sh_id;
									    };
									    
									   
									    
									    $scope.addPicFiles = function () {
									    	if($scope.totalPicFiles==undefined){
									    		$scope.totalPicFiles = [];
									    	}
											if($scope.picfiles.length==0){return;};
											var allfiles = $scope.totalPicFiles.concat($scope.picfiles);
											if(allfiles.length>6){alert("最多选择6张！");return;}
									        $scope.totalPicFiles=allfiles; 
									    };
									    $scope.dropPicFile = function(picFile){
									        angular.forEach($scope.totalPicFiles,function(item, key){
									            if(item == picFile){
									                $scope.totalPicFiles.splice(key,1); return false;
									            }
									        });
									    };
									    $scope.addAppendixs = function () {
									    	if($scope.totalAppendixs==undefined){
									    	 $scope.totalAppendixs = [];
									    	}
											if($scope.appendixs.length==0){return;};
											var allfiles = $scope.totalAppendixs.concat($scope.appendixs);
											if(allfiles.length>3){alert("最多选择3个！");return;}
									        $scope.totalAppendixs=allfiles; 
									    };
									    $scope.dropAppendixFile = function(file){
									        angular.forEach($scope.totalAppendixs,function(item, key){
									            if(item == file){
									                $scope.totalAppendixs.splice(key,1); return false;
									            }
									        });
									    };
									    
									    $scope.addVideoFiles = function () {
									    	if($scope.totalVideoFiles==undefined){
									    	$scope.totalVideoFiles = [];
									    	}
											if($scope.videofiles.length==0){return;};
											var allfiles = $scope.totalVideoFiles.concat($scope.videofiles);
											if(allfiles.length>2){alert("最多选择2个！");return;}
									        $scope.totalVideoFiles=allfiles; 
									    };
									    $scope.dropVideoFile = function(file){
									        angular.forEach($scope.totalVideoFiles,function(item, key){
									            if(item == file){
									                $scope.totalVideoFiles.splice(key,1); return false;
									            }
									        });
									    };
									    
									    function checkInput() {
											var flag = true;
											// 检查必须填写项
											if ($scope.title == undefined || $scope.title == '') {
												flag = false;
											}
											if ($scope.content == undefined || $scope.content == '') {
												flag = false;
											}
											return flag;
										}
									    
									    $scope.confirmUpdatePub  = function() {
									    	if (checkInput()) {
												   data = {
														    'title' : $scope.title,
											            	'describe' : $scope.describe,
															'pubcategory' : $('input[name="optionsRadios3"]:checked').val(),
															//'publisher' : $rootScope.user.id,
															'publisher' : $rootScope.user.id,
															'content' : $scope.content,
															'schoolid' : $scope.schoolid,
															'publishid' : $scope.publish.id
											            };
												        if($scope.totalPicFiles!=undefined)
											            for(var i = 0; i < $scope.totalPicFiles.length; i++){
											                data["picFile" + i] = $scope.totalPicFiles[i];
											            }
												        if($scope.totalAppendixs!=undefined)
											            for(var i = 0; i < $scope.totalAppendixs.length; i++){
											                data["appendix" + i] = $scope.totalAppendixs[i];
											            }
												        if($scope.totalVideoFiles!=undefined)
											            for(var i = 0; i < $scope.totalVideoFiles.length; i++){
											                data["videoFile" + i] = $scope.totalVideoFiles[i];
											            }
											       Upload.upload({
											                url: '/i/publish/addPublish',
											                headers :{ 'Content-Transfer-Encoding': 'utf-8' },
											                data: data
											            }).success(function (data) {
										            if(data.success){
										            	alert("修改成功");
										            	$state.reload();
										            }
										        });
											} else {
												alert("标题和内容不允许为空!");
											}
									    };
									    
									    $scope.cancelUpdate  = function() {
									    	$("#tab-2update").css("display","none");
									   		$("#tab_2list").css("display","");
									    };
									   	
									   $scope.Preview=function(){ //打印预览
										   $("#resume").jqprint();
										};
										 $scope.responseMessage = function(messageID){
									            $("#reply_area-"+messageID).css("display","");
									    };

});