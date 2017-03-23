(function () {
    angular.module("AutomatedSearchingSystem")
        .directive("navigationBar", function () {
            return {
                restrict: "E",
                templateUrl: "/static/page/directive/top-navigation-bar.html",
                controller: "topNavigationController"
            }
        });
})();