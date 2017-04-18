(function () {
    angular.module("AutomatedSearchingSystem")
        .controller("CourseController", ["$uibModal", "$scope", "$http", "$location",
            function ($uibModal, $scope, $http, $location) {

                $scope.pageSize = 5;
                $scope.cachedCourseListSize;
                $scope.showPaginationButton = true;
                $scope.courseDetails = {};
                $scope.status = '  ';
                $scope.sourceList = [];
                $scope.request = {
                    string: "",
                    source: [],
                    category: [],
                    language: []
                };
                $scope.courseList = [];
                $scope.promise;

                $scope.searchCourseBar = function () {
                    $scope.pageSize = 5;
                    $scope.showPaginationButton = true;
                    $scope.getCourseList();
                };

                $scope.checkSourcesSelected = function (id) {
                    if (id == 0) {
                        $scope.request.source = [];
                    } else {
                        $scope.request.source[0] = null;
                    }
                };

                $scope.checkLanguageSelected = function (id) {
                    if (id == 0) {
                        $scope.request.language = [];
                    } else {
                        $scope.request.language[0] = null;
                    }
                };

                $scope.getCourses = function () {
                    var requestUrl = "/api/course/search?size=" + $scope.pageSize;
                    var requestAllUrl = "/api/course/all?size=" + $scope.pageSize;

                    if ($scope.request.source[0] == 0) {
                        $scope.promise = $scope.postRequest(requestAllUrl, $scope.request).then(function (response) {
                            $scope.courseList = response.data;
                        }, function errorCallback(response) {
                        });
                    } else {
                        $scope.promise = $scope.postRequest(requestUrl, $scope.request).then(function (response) {
                            $scope.courseList = response.data;
                        }, function errorCallback(response) {
                        });
                    }
                };

                $scope.getCourseList = function () {
                    var requestUrl = "/api/course/search/params?source=" + $scope.request.source.toString() +
                        "&request=" + $scope.request.string + "&size=" + $scope.pageSize;
                    $scope.pageSize = $scope.pageSize + 5;

                    $scope.cachedCourseListSize = $scope.courseList.length;
                    $scope.promise = $scope.getRequest(requestUrl).then(function (response) {
                        $scope.courseList = response.data;

                        if ($scope.cachedCourseListSize == $scope.courseList.length) {
                            $scope.showPaginationButton = false;
                        }

                    }, function errorCallback(response) {
                    });
                };

                $scope.getAllCourses = function () {
                    var requestUrl = "/api/course/all?size=" + $scope.pageSize;
                    $scope.pageSize = $scope.pageSize + 5;

                    $scope.getRequest(requestUrl).then(function (response) {
                        $scope.courseList = response.data;
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

                $scope.getRequest = function (url, body) {
                    return $http.get(url, body)
                        .then(function (callback) {
                            callback.isError = false;
                            return callback;
                        }, function (callback) {
                            callback.isError = true;
                            return callback;
                        });
                };

                $scope.postRequest = function (url, body) {
                    return $http.post(url, body)
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
                $scope.getAllCourses();
            }]);
})();
