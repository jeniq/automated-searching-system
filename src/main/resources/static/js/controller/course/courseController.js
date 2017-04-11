(function () {
    angular.module("AutomatedSearchingSystem")
        .controller("CourseController", ["$scope", "$http",
            function ($scope, $http) {

                $scope.request = {
                    string: "",
                    source: ""
                };
                $scope.courseList = [];

                $scope.searchCourseBar = function () {
                    var requestUrl = "/api/course/search?request=" + $scope.request.string +
                        "&source=" + $scope.request.source;

                    $scope.searchByRequest(requestUrl).then(function (response) {
                        $scope.courseList = response.data;
                    }, function errorCallback(response) {
                    });
                };

                $scope.showCoursesTable = function () {
                    if ($scope.courseList.length > 0) {
                        return true;
                    }
                    return false;
                };

                // http
                $scope.searchByRequest = function (url) {
                    return $http.get(url)
                        .then(function (callback) {
                            callback.isError = false;
                            return callback;
                        }, function (callback) {
                            callback.isError = true;
                            return callback;
                        });
                };

            }]);
})();
