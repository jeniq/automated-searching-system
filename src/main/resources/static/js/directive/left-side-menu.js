(function () {
    angular.module("AutomatedSearchingSystem")
        .directive("leftSideMenu", function () {
            return {
                restrict: "E",
                templateUrl: "/static/page/directive/left-side-menu.html",
                controller: "leftSideMenuController"
            }
        });
})();