/**
 * 发布货品车
 */
var releaseCarInfo = {
	$scope:null,
    initvalidate: function() { //验证必填项
        jQuery.validator.addMethod("isMobile", function(value, element) {var length = value.length; var mobile = /^1[3|4|5|8][0-9]\d{4,8}$/;return this.optional(element) || (length == 11 && mobile.test(value));},"请正确填写您的手机号码");
        $("#release_item_from").validate({
            rules: {
                title: { required: true},provinceId: { required: true},city: { required: true },
                codeLave1: { required: true },
//                staddress: { required: true },
                sqm: { required: true,number:true    },
                unitPrice: { required: true, number:true   },
                reservation: { required: true }, telephone: { required: true,isMobile: true }
            },
            messages: {
                title: { required: "请输入描述!"}, provinceId: { required: "请选择省份!"},
                city: { required: "请选择城市！" }, codeLave1: { required: "请选择品类！" },
//                staddress: { required: "请输入出发地信息！" },
                sqm: { required: "请输入数量！" ,number:"请正确输入数量信息！！"}, unitPrice: { required: "请输入单价",number:"请正确输入单价信息！" },
                reservation: { required: "请设置信息有效期！" }, telephone: { required: '请输入联系人电话信息！', pattern: '请正确输入联系方式！'
                }
            },
            success: function(label) {
                label.remove();
            } //label.removeClass('error');label.text("").addClass("success");
        });
    },
   
    savedata: function() {
        if ($("#release_item_from").valid()) {
            this.addvo();
        } else {
            $($("#release_item_from input.error")[0]).focus();
        }
    },
    addvo: function() {
    	var vo = {}; 
        $("#hl_validEndTime").val( $("[name=daterangepicker_end]").val());
        $("#hl_validStartTime").val($("[name=daterangepicker_start]").val());
        if(releaseCarInfo.$scope.gl_rdc=1){
        	$("[name=rdcID]").val($("[name=rdcID_list]:checked").val());//仅支持一个
        }
        var unit1=$("#sl_provinceId1 option:selected").text()+"-"+$("#sl_cityid1 option:selected").text();
        var unit2=$("#sl_provinceId2 option:selected").text()+"-"+$("#sl_cityid2 option:selected").text();
        $("#hide_div [name=unit1]").val(unit1);
        $("#hide_div [name=unit2]").val(unit2);
//        $("#hide_div [name=attrvalue]").val($("#tx_attrvalue option:selected").text());
        var data = $("#release_item_from").serializeArray();
        $.each(data, function(index, item) { vo[item.name] = item.value; });
        var formdata = new FormData();formdata.append("data", JSON.stringify(vo));
        $.ajax({
            url: "/i/ShareRdcController/shareFreeRelease",
            data: formdata,
            processData: false,
            contentType: false,
            type: 'POST',
            dataType:"json",
            success: function(data) {
            	if(data.success){
            		 alert("发布成功！");
            		 releaseCarInfo.$scope.gocoldShareComment();
            	}else{
            		alert("发布失败！！");
            	}
            }});
        }
};

coldWeb.controller('releaseCarInfo',function($rootScope, $scope, $stateParams, $state, $cookies, $http, $location) {
	 if(user==null||(user!=null&&user.id==0)){
		 alert("请登录后执行该操作！");
		 window.location.href =  "http://" + $location.host() + ":" + $location.port() + "/login.html#/releaseCarInfo";return;
	  }
	$scope.gl_rdc=1;
	$scope.dataType = 2;//当前数据类型
	releaseCarInfo.$scope=$scope;
	$scope.appmode=[{},{tit:"货品-测试",tolimg:["goods","outCur","offerCur"],tool:[[1,"出货"],[2,"求货"]]},{tit:"配送-测试",tolimg:["car","carCur","noCarCur"],tool:[[1,"有车"],[2,"求车"]]},{tit:"仓库-测试",tolimg:["rent","rentCur","noRentCur"],tool:[[1,"出租"],[2,"求租"]]}];
	$scope.gocoldShareComment=function(){ $state.go('coldShareComment',{_cuttid: 2});};
    $scope.initMode=function(){
    	$(".mode_hide").addClass("hide");
    	$(".mode_"+$scope.dataType).removeClass("hide");
    	$(".mode_"+$scope.dataType+"_"+ $scope.typeCode).removeClass("hide");
    	$("#tx_title").val($scope.appmode[$scope.dataType].tit+$scope.appmode[$scope.dataType].tool[$scope.typeCode-1][1]+parseInt(Math.random()*100)+"!");
    };
    $scope.changtype=function(_em){
  	       var em=$(_em); 
  	       $("#item_type_div span").removeClass($scope.appmode[$scope.dataType].tolimg[$scope.typeCode]); 
	       $scope.typeCode=em.attr("value");
	       em.addClass($scope.appmode[$scope.dataType].tolimg[$scope.typeCode]);
	       $scope.typeText=em.text();
	       $scope.initMode();
    };
    $scope.pageChanged = function () {
		 $http({method:'POST',url:'/i/ShareRdcController/getRdcByUid'}).success(function (data) {
			   $scope.rdclist = data.data;//
			   $scope.bigTotalItems = data.total;
			   if(data.total==0){ $("#rdclist_info").addClass("hide"); }
		  });
    };
    $scope.changcity1 = function(id) {
    	$http.get('/i/city/findCitysByProvinceId', { params: {"provinceID": $scope.stprovinceID}  }).success(function(data) {$scope.city1 = data;}); 
    };
    $scope.changcity2 = function(id) {
    	$http.get('/i/city/findCitysByProvinceId', { params: {"provinceID": $scope.toprovinceID}  }).success(function(data) {$scope.city2 = data;}); 
    };
    $scope.initdata = function() {
        $scope.typeCode=$scope.appmode[$scope.dataType].tool[0][0];
        $scope.typeText=$scope.appmode[$scope.dataType].tool[0][1];
        $scope.initMode();
        releaseCarInfo.initvalidate();
        //初始化数据
        $scope.pageChanged();
        $('#reservationtime').daterangepicker({timePicker: true, timePickerIncrement: 30,format: 'YYYY-DD-MM HH:mm'});
        $http.get('/i/ShareRdcController/getPSFilterData').success(function(data) {
        	$scope.codeLave1 = data.entity.fm;
        	$scope.codeLave2 = data.entity.cl;
        	$scope.ps_cr_type = data.entity.sk;
        }); //
        $http.get('/i/city/findProvinceList').success(function(data) {
        	$scope.provinces = data; 
        	$scope.stprovinceID = data[0].provinceId; 
        	$scope.toprovinceID = data[0].provinceId; 
        	$scope.changcity1();
        	$scope.changcity2();
        }); //加载区域数据
    };
    $scope.initdata();
});