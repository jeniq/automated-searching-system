(function () {
    angular.module("AutomatedSearchingSystem")
        .controller("leftSideMenuController", ["$scope",
            function ($scope) {
                $scope.courseList = [
                    {
                        name:"Prometheus"
                    },
                    {
                        name:"Coursera"
                    },
                    {
                        name:"EdX"
                    }
                ];
            }]);
})();
