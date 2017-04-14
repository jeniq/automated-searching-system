(function () {
    angular.module("AutomatedSearchingSystem")
        .directive("navBar", function () {
            return {
                restrict: "E",
                templateUrl: "/static/page/directive/nav-bar.html"
            }

        });
})();