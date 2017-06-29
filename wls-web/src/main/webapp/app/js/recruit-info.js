wlsWeb.controller('recruit-info',function($http, $location, $state, $rootScope,$stateParams,$scope) {
    $scope.load = function(){
           $scope.newID = $stateParams.recruitID;
            $http.get('/i/recruit/findRecruitByID', {
                params: {
                    "recruitID": $scope.newID
                }
            }).success(function(data){
                   $scope.information = data;
                   document.getElementById("inforContent").innerHTML=$scope.information.content;
               });
           };
        $scope.load();
});
