wlsWeb.controller('news',function($http, $location, $state,$scope,$interval) {
	$scope.defaultCoverPic = "../../assets/img/portfolio/1.jpg";
	// 显示最大页数
    $scope.maxSize = 10;
    // 总条目数(默认每页十条)
    $scope.bigTotalItems = 10;
    // 当前页
    $scope.bigCurrentPage = 1;
	$scope.AllNews = [];
	$scope.optAudit = 8;
	$scope.AllCategory = [
	                      {id:"8",name:"全部"},
	                      {id:"1",name:"科技类"},
	                      {id:"2",name:"文娱类"},
	                      {id:"3",name:"创业类"},
	                      {id:"4",name:"时事类"},
	                      {id:"5",name:"校园类"}
	                      
	];
	$scope.AllSize = [
	                      {id:10,value:"10"},
	                      {id:20,value:"20"},
	                      {id:30,value:"30"},
	                      {id:40,value:"40"},
	                      {id:50,value:"50"}
	];
	 // 获取当前news的列表
    $scope.getNews = function() {
		$http({
			method : 'POST',
			url : '/i/information/findAllInformation',
			params : {
				pageNum : $scope.bigCurrentPage,
				pageSize : $scope.maxSize,
				audit : $scope.optAudit,
				keyword : encodeURI($scope.keyword,"UTF-8"),
			}
		}).success(function(data) {
			$scope.bigTotalItems = data.size;
			$scope.numPages = data.pages;
			$scope.AllNews = data.list;
		});
	};
    
	$scope.firstPage = function() {
		$scope.bigCurrentPage = 1;
		$scope.getNews();
	};
	
	$scope.endPage = function() {
		$scope.bigCurrentPage = $scope.numPages;
		$scope.getNews();
	};
	
	$scope.pagedes = function() {
		if($scope.bigCurrentPage>1){
			$scope.bigCurrentPage = $scope.bigCurrentPage-1;
			$scope.getNews();
		}
		   
	};
	
	$scope.pageadd = function() {
		if($scope.bigCurrentPage<$scope.numPages){
			$scope.bigCurrentPage = $scope.bigCurrentPage+1;
			 $scope.getNews();
		}
		  
	};
	
	$scope.getNews();

	$scope.auditChanged = function(optAudit) {
		$scope.optAudit = optAudit;
		$scope.getNews();
	};
    
	$scope.goSearch = function () {
		$scope.getNews();
    };
    
    $scope.showNewsNum = function (num) {
    	$scope.maxSize = num;
		$scope.getNews();
    };
    
    $scope.goNewsInfo = function(newID) {
   	 var url = $state.href('news-info', {"newID": newID});
	 window.open(url);
	};
});
