(function () {
    var eventService = function($http) {
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
                        id: '@',
                        title: '@',
                        location: '@',
                        start: '@',
                        end: '@',
                        ngModel: '='
                     },
                    replace: true,
                    templateUrl: 'components/activity/activity.html',
                    controller: ['$scope', 'eventService', function($scope, eventService) {
                    }],
                    link: function ($scope, element, attrs) {
                        var activity = $scope.ngModel;
                         $scope.location = activity.location;
                         $scope.start = activity.start;
                         $scope.imagePath = activity.imagePath;
                    } //DOM manipulation
        };
    };

    angular.module('collAgeDirectives',[]).service('eventService', eventService).directive('eventPreview', eventPreviewDirective);
}());