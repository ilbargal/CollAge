(function () {
    var eventService = function() {
        var date = new Date();
        var d = date.getDate();
        var m = date.getMonth();
        var y = date.getFullYear();

        var data = [
            {
                id:1,
                title:'רכיבה על אופניים',
                description: 'רכיבה שווה על אופניים. מומלצת לבעלי כושר גופני גבוה',
                start: new Date(y, m, d - 5),
                end: new Date(y, m, d - 2),
                location: 'רמת גן',
                imageSrc: 'https://upload.wikimedia.org/wikipedia/commons/4/41/Left_side_of_Flying_Pigeon.jpg'
            },
            {
                id:2,
                title:'שחייה צורנית',
                description: 'שחיה בספורטן העירוני בשלל צורות מעניינות. דרושים משתתפים רבים',
                start: '15/04/2016',
                end: '',
                location: 'תל אביב',
                imageSrc: 'http://images.mouse.co.il/storage/8/f/490_fake_gay.jpg'
            },
            {
                id:3,
                title:'ארוחת צהריים בארומה',
                title:'פגישה לקפה ומאפה בסניף הוד השרון',
                start: '20/04/2016',
                end: '',
                location: 'הוד השרון',
                imageSrc: 'http://www.aroma.co.il/_media/images/general/logo.png'
            },
            {
                id:4,
                start:'ערב קלפים מטורף',
                description: 'ברידג, פוקר ועוד',
                start: '23/04/2016',
                end: '',
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
                        start: '@',
                        end: '@',
                     },
                    replace: true,
                    templateUrl: 'components/activity/activity.html',
                    controller: ['$scope', 'eventService', function($scope, eventService) {
                        var activityFromDB = eventService.getEventById($scope.id);
                        $scope.location = activityFromDB.location;
                        $scope.start = activityFromDB.start;
                        $scope.imageSrc = activityFromDB.imageSrc;
                    }],
                    link: function ($scope, element, attrs) { } //DOM manipulation
        };
    };

    angular.module('collAgeDirectives',[]).service('eventService', eventService).directive('eventPreview', eventPreviewDirective);
}());