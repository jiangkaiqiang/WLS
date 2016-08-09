coldWeb.controller('orderGenerate', function ($rootScope, $scope, $state, $stateParams,$cookies, $http, $location) {
	$scope.orderDto = $stateParams.data;
	$scope.load = function(){
		$http.get('/i/user/findUser').success(function(data,status,config,headers){
			$rootScope.user = data;
			if($rootScope.user == undefined || $rootScope.user.id == 0){
				url = "http://" + $location.host() + ":" + $location.port();
				window.location.href = url;
			}
	    });
    }
    $scope.load();
    $scope.getTeleNum = function (ownerTele,userTele) {
    	$http.get('/i/orders/getTelephone').success(function(data){
    		alert("对方联系人的手机号已经发送到您的手机，请及时联系");
	    });
    }
});
