(function () {
    angular.module("AutomatedSearchingSystem")
        .controller("CourseController", ["$scope", "$http",
            function ($scope, $http) {

                $scope.request;
                $scope.source = [];
                $scope.courseList = [];

                $scope.searchCourseBar = function () {
                    var requestUrl = "/api/course/search?request=" + $scope.request + "&source=" + $scope.source;

                    return $http.get(requestUrl)
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
