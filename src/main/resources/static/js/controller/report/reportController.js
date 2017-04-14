(function () {
    angular.module("AutomatedSearchingSystem")
        .controller("ReportController", ["$scope",
            function ($scope) {

                $scope.myChartObject = {};

                $scope.myChartObject.type = "ColumnChart";

                $scope.myChartObject.data = {
                    "cols": [
                        {id: "c", label: "Назва", type: "string"},
                        {id: "v", label: "Перегляди", type: "number"}
                    ], "rows": [
                        {
                            c: [
                                {v: "Android development"},
                                {v: 34}
                            ]
                        },
                        {
                            c: [
                                {v: "Java"},
                                {v: 65}
                            ]
                        },
                        {
                            c: [
                                {v: "iOS dev"},
                                {v: 23}
                            ]
                        },
                        {
                            c: [
                                {v: "AngularJS"},
                                {v: 32},
                            ]
                        },
                        {
                            c: [
                                {v: "JavaScript"},
                                {v: 55},
                            ]
                        }
                    ]
                };

                $scope.myChartObject.options = {
                    'title': 'Топ-5 популярних онлайн-курсів'
                };

            }]);
})();

