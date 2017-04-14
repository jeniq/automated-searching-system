(function () {
    angular.module("AutomatedSearchingSystem")
        .config(["$routeProvider", "$locationProvider",
            function ($routeProvider, $locationProvider) {
                $routeProvider
                    .when("/", {
                        redirectTo: "/courses"
                    })
                    .when("/courses", {
                        templateUrl: "/static/page/courses-list/courses-list-page.html",
                        controller: "CourseController"
                    })
                    .when("/details", {
                        templateUrl: "/static/page/course/details-modal.html",
                        controller: "CourseController"
                    })
                    .when("/courses/:id", {
                        templateUrl: "",
                        controller: "CourseController"
                    })
                    .when("/report", {
                        templateUrl: "/static/page/report/report-page.html",
                        controller: "ReportController"
                    })
                    .otherwise({
                        templateUrl: "/static/page/404.html"
                    });

                $locationProvider.html5Mode(true);

            }]);
})();
