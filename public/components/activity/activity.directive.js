(function () {
    var eventService = function($http) {
        var date = new Date();
        var d = date.getDate();
        var m = date.getMonth();
        var y = date.getFullYear();
        return {
            events: function () {
                return $http.get("/getAllEvents");
            },
            getEventById: function(id) {
                return $http.get("/getEvent/" + id);
            },
            addEvent: function (activity) {
            },
            deleteEvent: function (id) {
            }
        };
    };

    var eventPreviewDirective = function () {
        return {
            restrict: 'E',
                    scope: {
                        //@ reads the attribute value, = provides two-way binding, & works with functions
                        id: '@',
                        title: '@',
                        location: '@',
                        start: '@',
                        end: '@',
                        ngModel: "="
                     },
                    replace: true,
                    templateUrl: 'components/activity/activity.html',
                    controller: ['$scope', 'eventService', function($scope, eventService) {
                    }],
                    link: function ($scope, element, attrs) {
                        var activity = $scope.ngModel;
                        $scope.location = activity.location;
                        $scope.datetime = activity.datetime;
                        $scope.imagePath = activity.imagePath;
                    } //DOM manipulation
        };
    };

    angular.module('collAgeDirectives',[]).service('eventService', eventService).directive('eventPreview', eventPreviewDirective);
}());