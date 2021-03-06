var app = angular.module('app', []);
app.controller('coldmap', function ($http) {
    $http.defaults.withCredentials=true;$http.defaults.headers={'Content-Type': 'application/x-www-form-urlencoded'};
    // 百度地图API功能
    var map = new BMap.Map("rdcMapChart");
    var point = new BMap.Point(110.114129, 24.550339);
    map.centerAndZoom(point, 5);
    //添加鼠标滚动缩放
    map.enableScrollWheelZoom();

    //添加缩略图控件
    map.addControl(new BMap.OverviewMapControl({isOpen: false})); //缩略地图控件，默认位于地图右下方，是一个可折叠的缩略地图
    //添加缩放平移控件
    map.addControl(new BMap.NavigationControl());
    //添加比例尺控件
    map.addControl(new BMap.ScaleControl());
    //添加地图类型控件
    //map.addControl(new BMap.MapTypeControl({mapTypes: [BMAP_NORMAL_MAP, BMAP_HYBRID_MAP]}));     //2D图，卫星图   //左上角， 地图类型控件

    $http.get(ER.root + '/i/rdc/findRdcList').success(function (data) {
        var index = 0;
        var markers = [];
        var adds = [];
        for (var i = 0; i < data.length; i++) {
            adds.push({
                id: data[i].id,
                name: data[i].name,
                address: data[i].address,
                lng: data[i].longitude,
                lat: data[i].latitude
            });
            bdGEO();
            if (markers.length == data.length) {
                var markerClusterer = new BMapLib.MarkerClusterer(map, {markers: markers});
                markerClusterer.setMinClusterSize(5);
            }
        }

        function bdGEO() {
            var add = adds[index];
            geocodeSearch(add);
            index++;
        }

        function geocodeSearch(add) {
            var address = new BMap.Point(add.lng, add.lat);
            var marker = new BMap.Marker(address, "");

            marker.addEventListener("click", function () {
                window.location.href = "colddetail.html?id=" + add.id;
            });
            markers.push(marker);
        }
    })
});
