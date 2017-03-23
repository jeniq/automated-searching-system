(function () {
    angular.module("AutomatedSearchingSystem")
        .directive("searchBar", function () {
            return {
                restrict: "E",
                templateUrl: "/static/page/directive/search-bar.html",
                controller: "searchBarController"
            }
        });
})();