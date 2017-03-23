(function () {
    angular.module("AutomatedSearchingSystem")
        .directive("coursesListTable", function () {
            return {
                restrict: "E",
                templateUrl: "/static/page/directive/courses-list-table.html",
                controller: "coursesListTableController"
            }
        });
})();