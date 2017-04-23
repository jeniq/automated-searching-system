(function () {
    angular.module("AutomatedSearchingSystem")
        .controller("CourseController", ["$uibModal", "$scope", "$http", "$location",
            function ($uibModal, $scope, $http, $location) {

                $scope.pageSize = 5;
                $scope.cachedCourseListSize;
                $scope.showPaginationButton = true;
                $scope.courseDetails = {};
                $scope.sourceList = [];
                $scope.request = {
                    string: "",
                    source: [],
                    category: [],
                    language: []
                };
                $scope.courseList = [];
                $scope.promise = [];
                $scope.boxesSelected = {source: false, language: false};
                $scope.notFoundMessage = false;

                /**
                 * This method check that check-buttons are selected
                 * @returns {boolean}
                 */
                $scope.checkForSelection = function () {
                    $scope.boxesSelected.source = false;
                    $scope.boxesSelected.language = false;

                    if ($scope.request.source.length == 0) { // In case selected 'all'
                        $scope.boxesSelected.source = true;
                    } else {
                        angular.forEach($scope.request.source, function (value) {
                            if (value != null) {
                                $scope.boxesSelected.source = true;
                            }
                        });
                    }

                    if ($scope.request.language.length == 0) { // In case selected 'all'
                        $scope.boxesSelected.language = true;
                    } else {
                        angular.forEach($scope.request.language, function (value) {
                            if (value != null) {
                                $scope.boxesSelected.language = true;
                            }
                        });
                    }

                    return ($scope.boxesSelected.source && $scope.boxesSelected.language);
                };

                $scope.searchCourses = function () {
                    $scope.pageSize = 5;
                    $scope.showPaginationButton = true;
                    $scope.courseList = [];
                    $scope.getCourses();
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

                    $scope.notFoundMessage = false;
                    $scope.pageSize = $scope.pageSize + 5;
                    $scope.cachedCourseListSize = $scope.courseList.length;

                    if ($scope.checkForSelection()) {
                        if ($scope.request.source.length == 0) {
                            $scope.promise = $scope.postRequest(requestAllUrl, $scope.request).then(function (response) {
                                $scope.courseList = response.data;
                                $scope.checkForNonEmptyResult();
                                if ($scope.cachedCourseListSize == $scope.courseList.length) {
                                    $scope.showPaginationButton = false;
                                }
                            }, function errorCallback(response) {
                            });
                        } else {
                            $scope.promise = $scope.postRequest(requestUrl, $scope.request).then(function (response) {
                                $scope.courseList = response.data;
                                $scope.checkForNonEmptyResult();
                                if ($scope.cachedCourseListSize == $scope.courseList.length) {
                                    $scope.showPaginationButton = false;
                                }
                            }, function errorCallback(response) {
                            });
                        }
                    } else {
                        alert("Оберіть, будь ласка, джерело та мову пошуку");
                    }
                };

                // Show message if no one course found
                $scope.checkForNonEmptyResult = function () {
                    $scope.notFoundMessage = $scope.courseList.length == 0;
                };

                $scope.showCoursesTable = function () {
                    return $scope.courseList.length > 0;
                };

                // Sources
                $scope.getAllSources = function () {
                    var requestUrl = "/api/source/all";

                    $scope.getRequest(requestUrl).then(function (response) {
                        $scope.sourceList = response.data;
                    }, function errorCallback(response) {
                    });
                };

                $scope.goToCourseSource = function (requestUrl) {
                    var url = "/api/course/watched?course=" + $scope.courseDetails.id;
                    $scope.postRequest(url);

                    window.open(requestUrl, '_blank'); // Opens in new window
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

                $scope.postRequest = function (url) {
                    return $http.post(url)
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
                $scope.getCourses();
            }]);
})();
