(function () {
    angular.module("AutomatedSearchingSystem")
        .controller("ReportController", ["$scope", "$http",
            function ($scope, $http) {

                $scope.getTopCourses = function () {
                    var url = "/api/course/statistic";

                    $scope.getRequest(url).then(function (response) {
                        $scope.myDataSource.data =  response.data;
                    }, function errorCallback(response) {
                    });
                };

                // http
                $scope.getRequest = function (url) {
                    return $http.get(url)
                        .then(function (callback) {
                            callback.isError = false;
                            return callback;
                        }, function (callback) {
                            callback.isError = true;
                            return callback;
                        });
                };

                // call
                $scope.getTopCourses();

                $scope.myDataSource = {
                    chart: {
                        caption: "Топ-5 популярних курсів",
                        yAxisName: "Кількість переглядів",
                        paletteColors: "#0075c2",
                        bgColor: "#ffffff",
                        showXAxisLine: "1",
                        xAxisLineColor: "#999999",
                        divlineColor: "#999999",
                        divLineDashed: "1",
                        showAlternateHGridColor: "0"
                    },
                    data:[]
                };
            }]);
})();

