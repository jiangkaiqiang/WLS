wlsWeb.controller('recruits',function($http, $location, $state,$scope,$interval) {
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
	                      {id:"1",name:"实习"},
	                      {id:"2",name:"全职"},
	                      {id:"3",name:"兼职"}
	                      
	];
	$scope.AllSize = [
	                      {id:10,value:"10"},
	                      {id:20,value:"20"},
	                      {id:30,value:"30"},
	                      {id:40,value:"40"},
	                      {id:50,value:"50"}
	];
	 // 获取当前招聘信息的列表
    $scope.getNews = function() {
		$http({
			method : 'POST',
			url : '/i/recruit/findAllRecruit',
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
    
    $scope.goNewsInfo = function(recruitID) {
   	 var url = $state.href('recruit-info', {"recruitID": recruitID});
	 window.open(url);
	};
});
