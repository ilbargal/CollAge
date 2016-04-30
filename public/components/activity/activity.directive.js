(function () {
    var eventService = function() {
        var data = [
            {
                id:1,
                title:'רכיבה על אופניים',
                description: 'רכיבה שווה על אופניים. מומלצת לבעלי כושר גופני גבוה',
                datetime: '14/04/2016',
                location: 'רמת גן',
                imageSrc: 'https://upload.wikimedia.org/wikipedia/commons/4/41/Left_side_of_Flying_Pigeon.jpg'
            },
            {
                id:2,
                title:'שחייה צורנית',
                description: 'שחיה בספורטן העירוני בשלל צורות מעניינות. דרושים משתתפים רבים',
                datetime: '15/04/2016',
                location: 'תל אביב',
                imageSrc: 'http://images.mouse.co.il/storage/8/f/490_fake_gay.jpg'
            },
            {
                id:3,
                title:'ארוחת צהריים בארומה',
                title:'פגישה לקפה ומאפה בסניף הוד השרון',
                datetime: '20/04/2016',
                location: 'הוד השרון',
                imageSrc: 'http://www.aroma.co.il/_media/images/general/logo.png'
            },
            {
                id:4,
                title:'ערב קלפים מטורף',
                description: 'ברידג, פוקר ועוד',
                datetime: '23/04/2016',
                location: 'כפר סבא, רחוב השופטים 26ב',
                imageSrc: 'http://www.best-games-ever.com/files/images/32.JPG'
            },
        ];

        return {
            events: function () {
                return data;
            },
            getEventById: function(id) {
                return data.filter(function(x){return x.id == id})[0];
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
                        datetime: '@',
                     },
                    replace: true,
                    templateUrl: 'components/activity/activity.html',
                    controller: ['$scope', 'eventService', function($scope, eventService) {
                        var activityFromDB = eventService.getEventById($scope.id);
                        $scope.location = activityFromDB.location;
                        $scope.datetime = activityFromDB.datetime;
                        $scope.imageSrc = activityFromDB.imageSrc;
                    }],
                    link: function ($scope, element, attrs) { } //DOM manipulation
        };
    };

    angular.module('collAgeDirectives',[]).service('eventService', eventService).directive('eventPreview', eventPreviewDirective);
}());