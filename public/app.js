var collAgeApp = angular.module('collAgeApp', ['ngRoute', 'auth0', 'angular-storage', 'angular-jwt', 'collAgeDirectives', 'ui.calendar', 'multipleSelect']);collAgeApp.config(function (authProvider) {  authProvider.init({    domain: 'collage.auth0.com',    clientID: 'CJvEGDMZs53qqj0dUMOV62ZN65Oj3ARu'  });}).run(function(auth) {  // This hooks al auth events to check everything as soon as the app starts  auth.hookEvents();});// app.jscollAgeApp.config(function (authProvider, $routeProvider, $httpProvider, jwtInterceptorProvider) {  authProvider.on('loginSuccess', function($location, profilePromise, idToken, store) {    profilePromise.then(function(profile) {      localStorage.setItem('profile', JSON.stringify(profile));      localStorage.setItem('token', idToken);    });  });  authProvider.on('loginFailure', function() {         // Error Callback  });  // We're annotating this function so that the `store` is injected correctly when this file is minified  jwtInterceptorProvider.tokenGetter = ['store', function(store) {    // Return the saved token    return localStorage.getItem('token');  }];  $httpProvider.interceptors.push('jwtInterceptor');});collAgeApp.run(function($rootScope, auth, store, jwtHelper, $location) {    // This events gets triggered on refresh or URL change    $rootScope.$on('$locationChangeStart', function () {        var token = localStorage.getItem('token');        if (token) {            if (!auth.isAuthenticated) {                auth.authenticate(JSON.parse(localStorage.getItem('profile')), token);            }        } else {            // Either show the login page or use the refresh token to get a new idToken            //$location.path('/main');        }        if (!jwtHelper.isTokenExpired(token)) {            //auth.profile = JSON.parse(auth.profile);        }        else {            alertify.error('עליך להתחבר למערכת');            $location.path('/main');        }    })});collAgeApp.config(function($routeProvider) { $routeProvider     .when('/main', {      templateUrl : 'views/main.html',      controller  : 'mainController'     })     .when('/profile', {      templateUrl : 'views/profile.html',      controller  : 'UsersController',      requiresLogin: true     })     .when('/new-activity', {      templateUrl : 'views/new-activity.html',      controller  : 'newActivityController',      requiresLogin: true     })     .when ('/event/:id', {      templateUrl: 'views/event-details.html',      controller: 'eventController',      requiresLogin: true,      resolve: {        canEdit: function() {            return true;        },        currEvent: function($route, eventService) {            var promise = eventService.getEventById($route.current.params.id);            return promise;        }      }     })     .when ('/signup',{        templateUrl: 'views/sign-up.html',         controller: 'signupController',         requiresLogin: false     })     .when ('/signin',{         templateUrl: 'views/sign-in.html',         controller: 'signinController',         requiresLogin: false     })     .when('/calendar', {       templateUrl : 'views/calendar.html',        controller  : 'calendarController as calendarCtrl'     })     .otherwise({      redirectTo: '/main'     });});collAgeApp.controller('mainController',['$scope', '$location', 'auth', 'eventService', function($scope, $location, auth, eventService) {    $scope.auth = auth;    $scope.isAdmin = false;    $scope.user = {};    eventService.getAllEvents().success(function(data) {        $scope.activities = data;    })    $scope.signin = function() {        auth.signin({            popup: true,            chrome: true,            connection: 'Username-Password-Authentication'        },        function(profile, idToken, accessToken, state, refreshToken) {          $location.path('/profile');        },        function(err){            console.log('MyError: ' + err);        });    };    $scope.signup = function() {        auth.signup({            connection: 'Username-Password-Authentication'        },        function(profile, idToken, accessToken, state, refreshToken) {            $location.path('/profile');        });    }    $scope.signout = function() {        localStorage.clear();        auth.signout();        $location.path('/main');    };}]);collAgeApp.controller('eventController', ['$scope','currEvent','auth', '$http', '$location', function($scope, currEvent, auth, $http, $location) {    var eventCtrl = this;    $scope.auth = auth;    $scope.event = currEvent.data;    $scope.map = new GMaps({        el: '#eventMap',        lat: 32.09204338934781,        lng: 34.781748577952385    });    GMaps.geocode({        address: $scope.event.location,        callback: function(results, status) {            if (status == 'OK') {                var latlng = results[0].geometry.location;                $scope.map.setCenter(latlng.lat(), latlng.lng());                $scope.map.addMarker({                    lat: latlng.lat(),                    lng: latlng.lng()                });            }        }    });    $scope.saveEventStatus = function(status) {        var serverDetails = {            eventId : $scope.event.id,            userMail: $scope.auth.profile.email,            statusCode: status        }        $http.post('/saveEventStatus', serverDetails)            .success(function(res) {                debugger;                $location.reload();            })            .error(function(err) {                console.log(err);            })    }}]);collAgeApp.controller('newActivityController', ['$scope', '$location', 'auth','eventService',function($scope, $location, auth, eventService){    $scope.auth = auth;    $scope.activityLocation = 'תל אביב';    $scope.addActivity = function() {        var event = {            name: $scope.activityName,            description: $scope.activityDescription,            owner: $scope.auth.profile.email,            imagePath: $scope.imagePath,            location: $scope.activityLocation,            datetime: $scope.activityDatetime,            categories: $scope.selectedCategories        };        if ($scope.imagePath == undefined) {            event.imagePath = "../images/pic08.jpg";        }        eventService.addEvent(event).success(function(id) {            console.log('added event');            alertify.success('האירוע נוסף בהצלחה. הנך מועבר לפרטי האירוע')            $location.path("/event/" + id);        }).error(function(err) {           console.log(err);            alertify.error('אירעה שגיאה בהוספת האירוע');        });    }    eventService.getAllCategories().then(function(data){        $scope.allCategories = data.data;    });    $scope.locationPoint = {        lat: 32.09204338934781,        lng: 34.781748577952385    };    $scope.map = new GMaps({        el: '#map',        lat: $scope.locationPoint.lat,        lng: $scope.locationPoint.lng,        click: function(e) {            $scope.map.removeMarkers();            var marker = new google.maps.Marker({                position: e.latLng,                map: $scope.map            });            $scope.locationPoint = {                lat: e.latLng.lat(),                lng: e.latLng.lng()            };            $scope.map.addMarker(marker);            $scope.map.panTo(e.latLng);        }    });    $scope.map.addMarker({        lat: $scope.locationPoint.lat,        lng: $scope.locationPoint.lng    });    $scope.searchOnMap = function(textLocation) {        GMaps.geocode({            address: textLocation,            callback: function(results, status) {                if (status == 'OK') {                    var latlng = results[0].geometry.location;                    $scope.map.setCenter(latlng.lat(), latlng.lng());                    $scope.map.removeMarkers();                    $scope.map.addMarker({                        lat: latlng.lat(),                        lng: latlng.lng()                    });                }            }        });    }}]);collAgeApp.controller('userController', ['$scope','currUser','auth', function($scope, currUser, auth) {    var userCtrl = this;    $scope.auth = auth;    $scope.event = currUser.data;}]);collAgeApp.controller('UsersController',function($scope, $http, $window, auth) {    auth.profile.birthday = new Date(auth.profile.birthday);    $scope.profile = auth.profile;    $scope.profile.type = "0";    $scope.saveProfile = function() {        var payload = {            mail:           $scope.profile.email,            password:       $scope.profile.password,            type:           $scope.profile.type,            firstName:      $scope.profile.firstname,            lastName:       $scope.profile.lastname,            gender:         $scope.profile.gender,            birthday:       $scope.profile.birthday,            address:        $scope.profile.address,            phone:          $scope.profile.phone,            job:            $scope.profile.job,            profilePic:     $scope.profile.picture,            description:    $scope.profile.description        };        $http.post('/updateUser', payload).            success(function(data)        {        }).            error(function(err) {            console.log(err);        })    }});collAgeApp.controller('calendarController', function($scope, $compile, uiCalendarConfig, eventService){    var date = new Date();    var d = date.getDate();    var m = date.getMonth();    var y = date.getFullYear();    $scope.eventSources = [[{"id":1,"title":"רכיבה על אופניים","description":"רכיבה שווה על אופניים. מומלצת לבעלי כושר גופני גבוה","location":{"x":35.0,"y":32.0},"start":1462741200000,"end":1463000400000,"imagePath":"https://upload.wikimedia.org/wikipedia/commons/4/41/Left_side_of_Flying_Pigeon.jpg","name":"רכיבה על אופניים"},                           //{"id":2,"title":"שחייה צורנית","description":"שחיה בספורטן העירוני בשלל צורות מעניינות. דרושים משתתפים רבים","location":{"x":35.0,"y":32.0},"start":1462741200000,"end":1463000400000,"imagePath":"http://images.mouse.co.il/storage/8/f/490_fake_gay.jpg","name":"שחייה צורנית"},                           //{"id":3,"title":"ארוחת צהריים בארומה","description":"פגישה לקפה ומאפה בסניף הוד השרון","location":{"x":35.0,"y":32.0},"start":1462741200000,"end":1463000400000,"imagePath":"http://www.aroma.co.il/_media/images/general/logo.png","name":"ארוחת צהריים בארומה"}                           ]];    eventService.getAllEvents().success(function(data) {        $scope.activities = [{"id":1,"title":"רכיבה על אופניים","description":"רכיבה שווה על אופניים. מומלצת לבעלי כושר גופני גבוה","location":{"x":35.0,"y":32.0},"start":1462741200000,"end":1463000400000,"imagePath":"https://upload.wikimedia.org/wikipedia/commons/4/41/Left_side_of_Flying_Pigeon.jpg","name":"רכיבה על אופניים"},                                                       //{"id":2,"title":"שחייה צורנית","description":"שחיה בספורטן העירוני בשלל צורות מעניינות. דרושים משתתפים רבים","location":{"x":35.0,"y":32.0},"start":1462741200000,"end":1463000400000,"imagePath":"http://images.mouse.co.il/storage/8/f/490_fake_gay.jpg","name":"שחייה צורנית"},                                                       //{"id":3,"title":"ארוחת צהריים בארומה","description":"פגישה לקפה ומאפה בסניף הוד השרון","location":{"x":35.0,"y":32.0},"start":1462741200000,"end":1463000400000,"imagePath":"http://www.aroma.co.il/_media/images/general/logo.png","name":"ארוחת צהריים בארומה"}                                                       ];        var all_events = data;        $scope.eventSources = [{          events: all_events,          color: 'light-blue', // an option!          textColor: 'black', // an option!        }];    })     /* Render Tooltip */    $scope.eventRender = function( event, element, view ) {        element.attr({'data-toggle': "tooltip",                     'title': event.title});        $compile(element)($scope);            /* config object */        $scope.uiConfig = {              calendar:{                height: 500,                editable: false,                dayNames: ["ראשון", "שני", "שלישי", "רביעי", "חמישי", "שישי", "שבת"],                dayNamesShort: ["ראשון", "שני", "שלישי", "רביעי", "חמישי", "שישי", "שבת"],                monthNames: ['ינואר', 'פבואר', 'מרץ', 'אפריל', 'מאי', 'יוני', 'יולי', 'אוגוסט', 'ספטמבר', 'אוקטובר', 'נובמבר', 'דצמבר'],                isRTL: true,                timeFormat: 'h:mm',                header: {                  left: 'month basicWeek',                  center: 'title',                  right: 'today next,prev'                },                buttonText: {                    today: 'היום',                    month: 'חודש',                    week: 'שבוע',                    day: 'יום'                },                eventRender: $scope.eventRender              }            };    };});