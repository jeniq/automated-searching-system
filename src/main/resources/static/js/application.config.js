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
                    .otherwise({
                        templateUrl: "/static/page/404.html"
                    });

                $locationProvider.html5Mode(true);

            }]);
})();
