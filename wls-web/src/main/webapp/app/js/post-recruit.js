wlsWeb.controller('post-recruit',function($http, $location,$rootScope, Upload,$state,$scope) {
	if($rootScope.user==null||$rootScope.user.id==undefined){
		alert("请先登录");
		window.location.href="#/login";
	}
/*	// 获取学校列表
    $http.get('/i/city/findSchoolList').success(function (data) {
    	$scope.schools = data;
        var sh = {"sh_id":-1,"sh_shool":"全部学校"};
    	 $scope.schools.push(sh);
    	 $scope.schools = sortJson($scope.schools,"sh_id");
    	 $scope.schoolid = -1;
    });*/
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
    
    function sortJson(json,key){ 
        for (var i = 0; i < json.length; i++) {
            for (var j = 0; j < json.length-1; j++) {
                if (json[j][key]>json[j+1][key]) {
                    var temp=json[j];
                    json[j]=json[j+1];
                    json[j+1]=temp;
                };
            };
        };
        return json;
    };
    $scope.totalPicFiles = [];
    $scope.totalAppendixs = [];
    $scope.totalVideoFiles = [];
    $scope.addPicFiles = function () {
		if($scope.picfiles.length==0){return;};
		var allfiles = $scope.totalPicFiles.concat($scope.picfiles);
		if(allfiles.length>1){alert("最多选择1张！");return;}
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
    $scope.pubSubmit = function() {
		//alert(UE.getEditor('container').getContent());
		//$scope.content = UE.getEditor('container').getContent();
		if (checkInput()) {
			   data = {
					    'title' : $scope.title,
		            	//'describe' : $scope.describe,
						'pubcategory' : $('input[name="optionsRadios3"]:checked').val(),
						//'publisher' : $rootScope.user.id,
						'publisher' : $rootScope.user.username,
						'salary' : $scope.salary,
						'position' : $scope.position,
						
						'education' : $scope.education,
						'content' : $scope.content,
						
						'companyName' : $scope.companyName,
						'companyAddress' : $scope.companyAddress,
						'schoolid' : $scope.schoolid
		            };
		            for(var i = 0; i < $scope.totalPicFiles.length; i++){
		                data["picFile" + i] = $scope.totalPicFiles[i];
		            }
		         /*   for(var i = 0; i < $scope.totalAppendixs.length; i++){
		                data["appendix" + i] = $scope.totalAppendixs[i];
		            }
		            for(var i = 0; i < $scope.totalVideoFiles.length; i++){
		                data["videoFile" + i] = $scope.totalVideoFiles[i];
		            }*/
		       Upload.upload({
		                url: '/i/recruit/addRecruit',
		                headers :{ 'Content-Transfer-Encoding': 'utf-8' },
		                data: data
		            }).success(function (data) {
	            if(data.success){
	            	alert("发布成功");
	            	window.location.href="#/recruits";
	            }
	        });
		} else {
			alert("标题和内容不允许为空!");
		}
	};
});
