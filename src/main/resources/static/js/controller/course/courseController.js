(function () {
    angular.module("AutomatedSearchingSystem")
        .controller("CourseController", ["$uibModal", "$scope", "$http", "$location",
            function ($uibModal, $scope, $http, $location) {

                $scope.courseDetails = {};
                $scope.status = '  ';
                $scope.sourceList = [];
                $scope.request = {
                    string: "",
                    source: [] // TODO current: ,id,id,id or ,,id
                };
                $scope.courseList = [];

                $scope.searchCourseBar = function () {
                    var requestUrl = "/api/course/search?source=" + $scope.request.source.toString() + "&request=" + $scope.request.string;


                    $scope.courseList = $scope.getRequest(requestUrl).then(function (response) {
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

                $scope.goToCourseSource = function (requestUrl) {
                    window.open(requestUrl, '_blank');
                };

                $scope.goToCoursesPage = function () {
                    $location = "/courses";
                };

                $scope.openCourseDetails = function (courseId) {
                    var requestUrl = "/api/course/" + courseId;
                    //$location.path("courses/" + courseId);
                    //      $location.replace();

                    $scope.getRequest(requestUrl).then(function (response) {
                        $scope.courseDetails = response.data;
                    }, function errorCallback(response) {
                    });

                    $uibModal.open({
                        animation: true,
                        size: 'md',
                        templateUrl: "/static/page/course/details-modal.html",
                        scope: $scope
                    });
                    //.closed.then(function(){$location.path("courses")});
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
