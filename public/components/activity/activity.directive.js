(function () {
    var eventService = function($http) {
        return {
            getAllEvents: function () {
                return $http.get("/getAllEvents");
            },
            getRecommendedEvents: function(mail){
                if (mail != undefined) {
                    return $http.get("/getAllEvents/" + mail);
                }

                return $http.get("/getAllEvents");
            },
            getEventById: function(id) {
                return $http.get("/getEvent/" + id);
            },
            getUserCategories: function(email) {
                return $http.get('/getUserCategories/' + email);
            },
            getUserByMail: function (email) {
                return $http.get('/getUser/' + email);
            },
            addEvent: function (activity) {
                return $http.post("/addActivity", activity);
            },
            getAllCategories: function() {
                return $http.get("getAllCategories");
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
                    controller: ['$scope', '$location', 'eventService', function($scope, $location, eventService) {
                        $scope.showFullEvent = function(id) {
                            $location.path('/event/' + id);
                        }
                    }],
                    link: function ($scope, element, attrs) {
                        var activity = $scope.ngModel;
                         $scope.location = activity.location;
                         $scope.start = activity.datetime;
                         $scope.imagePath = activity.imagePath;
                    } //DOM manipulation
        };
    };

    angular.module('collAgeDirectives',['ngRoute']).service('eventService', eventService).directive('eventPreview', eventPreviewDirective);
}());