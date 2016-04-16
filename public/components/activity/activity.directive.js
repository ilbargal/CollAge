(function () {
    var activityService = function() {
        var data = [
            {id:1, title:'רכיבה על אופניים', datetime: '14/04/2016 09:00:00', location: 'רמת גן'},
            {id:2, title:'שחייה צורנית', datetime: '15/04/2016 09:00:00', location: 'תל אביב'},
            {id:3, title:'ארוחת צהריים בארומה', datetime: '20/04/2016 04:00:00', location: 'הוד השרון'},
        ];

        return {
            activities: function () {
                return data;
            },
            getActivityById: function(id) {
                return data.filter(function(x){return x.id == id})[0];
            },
            addActivity: function (activity) {
            },
            deleteActivity: function (id) {
            }
        };
    };

    var activityDirective = function () {
        return {
            restrict: 'E',
                    scope: {
                        //@ reads the attribute value, = provides two-way binding, & works with functions
                        id: '@',
                        title: '@',
                        location: '@',
                        datetime: '@',
                     },
                    replace: true,
                    templateUrl: 'components/activity/activity.html',
                    controller: ['$scope', 'activityService', function($scope, activityService) {
                        var activityFromDB = activityService.getActivityById($scope.id);
                        $scope.location = activityFromDB.location;
                        $scope.datetime = activityFromDB.datetime;
                    }],
                    link: function ($scope, element, attrs) { } //DOM manipulation
        };
    };

    angular.module('collAgeDirectives',[]).service('activityService', activityService).directive('activity', activityDirective);
}());