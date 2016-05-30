var collAgeApp = angular.module('collAgeApp', ['ngRoute', 'auth0', 'angular-storage', 'angular-jwt', 'collAgeDirectives', 'ui.calendar']);collAgeApp.config(function (authProvider) {  authProvider.init({    domain: 'collage.auth0.com',    clientID: 'CJvEGDMZs53qqj0dUMOV62ZN65Oj3ARu'  });}).run(function(auth) {  // This hooks al auth events to check everything as soon as the app starts  auth.hookEvents();});// app.jscollAgeApp.config(function (authProvider, $routeProvider, $httpProvider, jwtInterceptorProvider) {  authProvider.on('loginSuccess', function($location, profilePromise, idToken, store) {    profilePromise.then(function(profile) {      store.set('profile', profile);      store.set('token', idToken);    });    $location.path('/main');  });  authProvider.on('loginFailure', function() {         // Error Callback  });  // We're annotating this function so that the `store` is injected correctly when this file is minified  jwtInterceptorProvider.tokenGetter = ['store', function(store) {    // Return the saved token    return store.get('token');  }];  $httpProvider.interceptors.push('jwtInterceptor');  // ...});collAgeApp.run(function($rootScope, auth, store, jwtHelper, $location) {  // This events gets triggered on refresh or URL change  $rootScope.$on('$locationChangeStart', function() {    var token = store.get('token');    if (token) {      if (!jwtHelper.isTokenExpired(token)) {        if (!auth.isAuthenticated) {          auth.authenticate(store.get('profile'), token);        }      } else {        // Either show the login page or use the refresh token to get a new idToken        $location.path('/main');      }    }  else {        $location.path('/main');    }  });});collAgeApp.config(function($routeProvider) { $routeProvider     .when('/main', {      templateUrl : 'views/main.html',      controller  : 'mainController as mainCtrl'     })     .when('/profile', {      templateUrl : 'views/profile.html',      //controller  : 'aboutController',      requiresLogin: true     })     .when('/new-activity', {      templateUrl : 'views/new-activity.html',      controller  : 'newActivityController',      requiresLogin: true     })     .when ('/event/:id', {      templateUrl: 'views/event-details.html',      controller: 'eventController',      requiresLogin: true,      resolve: {        canEdit: function() {            return true;        },        currEvent: function($route, eventService) {            var promise = eventService.getEventById($route.current.params.id);            return promise;        }      }     })     .when ('/signup',{        templateUrl: 'views/sign-up.html',         controller: 'signupController',         requiresLogin: false     })     .when('/calendar', {       templateUrl : 'views/calendar.html',        controller  : 'calendarController as calendarCtrl'     })     .otherwise({      redirectTo: '/main'     });});collAgeApp.controller('mainController',['$scope', '$location', 'eventService', 'auth', function($scope, $location, eventService, auth) {    $scope.auth = auth;    $scope.isAdmin = false;    $scope.user = {};    eventService.events().success(function(data) {        $scope.activities = data;    })    .error(function(err) {        console.log(err);    });    $scope.signin = function() {        auth.signin({            popup: true,            chrome: true        },        function(profile, idToken, accessToken, state, refreshToken) {          $location.path('/profile');        },        function(err){            console.log(err);        });    };    $scope.signout = function() {        localStorage.clear();        auth.signout();    };}]);collAgeApp.controller('eventController', ['$scope','currEvent','auth', function($scope, currEvent, auth) {    var eventCtrl = this;    $scope.auth = auth;    $scope.event = currEvent.data;    $scope.map = { center: { latitude: 32.146414, longitude: 34.888305 }, zoom: 8 };}]);collAgeApp.controller('newActivityController', ['$scope', 'eventService', function($scope, eventService){    $scope.addActivity = function() {        var event = {            name: $scope.activityName,            description: $scope.activityDescription,            imagePath: $scope.imagePath,            location: $scope.activityLocation,            datetime: $scope.activityDatetime,            //categories: $scope.selectedCategories        };        eventService.addEvent(event).then(function() {            console.log('added');        })    }    eventService.getAllCategories().then(function(data){        $scope.allCategories = data.data;    });}]);collAgeApp.controller('userController', ['$scope','currUser','auth', function($scope, currUser, auth) {    var userCtrl = this;    $scope.auth = auth;    $scope.event = currUser.data;    $scope.map = { center: { latitude: 32.146414, longitude: 34.888305 }, zoom: 8 };}]);collAgeApp.controller('signupController',function($scope, $log, $http) {        $scope.signup = function () {        var payload = {            mail: $scope.mail,            password: $scope.password,            firstName: $scope.firstname,            lastName: $scope.lastname,            gender: $scope.gender,            birthday: $scope.birthday,            address: $scope.address,            phone: $scope.phone,            job: $scope.job,            profilePic: $scope.profilePic        };        $http.post('/signup', payload).success(function (data) {            $log.debug(data);        });    };});collAgeApp.controller('calendarController', function($scope, $compile, uiCalendarConfig, eventService){    var date = new Date();    var d = date.getDate();    var m = date.getMonth();    var y = date.getFullYear();    $scope.eventSources = [[{"id":1,"title":"רכיבה על אופניים","description":"רכיבה שווה על אופניים. מומלצת לבעלי כושר גופני גבוה","location":{"x":35.0,"y":32.0},"start":1462741200000,"end":1463000400000,"imagePath":"https://upload.wikimedia.org/wikipedia/commons/4/41/Left_side_of_Flying_Pigeon.jpg","name":"רכיבה על אופניים"},                           //{"id":2,"title":"שחייה צורנית","description":"שחיה בספורטן העירוני בשלל צורות מעניינות. דרושים משתתפים רבים","location":{"x":35.0,"y":32.0},"start":1462741200000,"end":1463000400000,"imagePath":"http://images.mouse.co.il/storage/8/f/490_fake_gay.jpg","name":"שחייה צורנית"},                           //{"id":3,"title":"ארוחת צהריים בארומה","description":"פגישה לקפה ומאפה בסניף הוד השרון","location":{"x":35.0,"y":32.0},"start":1462741200000,"end":1463000400000,"imagePath":"http://www.aroma.co.il/_media/images/general/logo.png","name":"ארוחת צהריים בארומה"}                           ]];    eventService.events().success(function(data) {        $scope.activities = [{"id":1,"title":"רכיבה על אופניים","description":"רכיבה שווה על אופניים. מומלצת לבעלי כושר גופני גבוה","location":{"x":35.0,"y":32.0},"start":1462741200000,"end":1463000400000,"imagePath":"https://upload.wikimedia.org/wikipedia/commons/4/41/Left_side_of_Flying_Pigeon.jpg","name":"רכיבה על אופניים"},                                                       //{"id":2,"title":"שחייה צורנית","description":"שחיה בספורטן העירוני בשלל צורות מעניינות. דרושים משתתפים רבים","location":{"x":35.0,"y":32.0},"start":1462741200000,"end":1463000400000,"imagePath":"http://images.mouse.co.il/storage/8/f/490_fake_gay.jpg","name":"שחייה צורנית"},                                                       //{"id":3,"title":"ארוחת צהריים בארומה","description":"פגישה לקפה ומאפה בסניף הוד השרון","location":{"x":35.0,"y":32.0},"start":1462741200000,"end":1463000400000,"imagePath":"http://www.aroma.co.il/_media/images/general/logo.png","name":"ארוחת צהריים בארומה"}                                                       ];        var all_events = data;        $scope.eventSources = [{          events: all_events,          color: 'light-blue', // an option!          textColor: 'black', // an option!        }];    })     /* Render Tooltip */    $scope.eventRender = function( event, element, view ) {        element.attr({'data-toggle': "tooltip",                     'title': event.title});        $compile(element)($scope);            /* config object */        $scope.uiConfig = {              calendar:{                height: 500,                editable: false,                dayNames: ["ראשון", "שני", "שלישי", "רביעי", "חמישי", "שישי", "שבת"],                dayNamesShort: ["ראשון", "שני", "שלישי", "רביעי", "חמישי", "שישי", "שבת"],                monthNames: ['ינואר', 'פבואר', 'מרץ', 'אפריל', 'מאי', 'יוני', 'יולי', 'אוגוסט', 'ספטמבר', 'אוקטובר', 'נובמבר', 'דצמבר'],                isRTL: true,                timeFormat: 'h:mm',                header: {                  left: 'month basicWeek',                  center: 'title',                  right: 'today next,prev'                },                buttonText: {                    today: 'היום',                    month: 'חודש',                    week: 'שבוע',                    day: 'יום'                },                eventRender: $scope.eventRender              }            };    };});