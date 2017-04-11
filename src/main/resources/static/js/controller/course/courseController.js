(function () {
    angular.module("AutomatedSearchingSystem")
        .controller("CourseController", ["$scope", "$http",
            function ($scope, $http) {

                $scope.sourceList = [];
                $scope.request = {
                    string: "",
                    source: [] // TODO current: ,id,id,id or ,,id
                };
                $scope.courseList = [];

                $scope.searchCourseBar = function () {
                    var requestUrl = "/api/course/search?source=" + $scope.request.source.toString() + "&request=" + $scope.request.string;

                    $scope.getRequest(requestUrl).then(function (response) {
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

                $scope.getAllSources = function () {
                    var requestUrl = "/api/source/all";

                    $scope.getRequest(requestUrl).then(function (response) {
                        $scope.sourceList = response.data;
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
                $scope.getAllSources();

            }]);
})();
