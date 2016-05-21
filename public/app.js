var collAgeApp = angular.module('collAgeApp', ['ngRoute', 'auth0', 'angular-storage', 'angular-jwt', 'collAgeDirectives', 'ui.calendar']);collAgeApp.config(function (authProvider) {  authProvider.init({    domain: 'collage.auth0.com',    clientID: 'CJvEGDMZs53qqj0dUMOV62ZN65Oj3ARu'  });}).run(function(auth) {  // This hooks al auth events to check everything as soon as the app starts  auth.hookEvents();});// app.jscollAgeApp.config(function (authProvider, $routeProvider, $httpProvider, jwtInterceptorProvider) {  authProvider.on('loginSuccess', function($location, profilePromise, idToken, store) {    console.log("Login Success");    profilePromise.then(function(profile) {      store.set('profile', profile);      store.set('token', idToken);    });    $location.path('/');  });  authProvider.on('loginFailure', function() {         // Error Callback  });  // We're annotating this function so that the `store` is injected correctly when this file is minified  jwtInterceptorProvider.tokenGetter = ['store', function(store) {    // Return the saved token    return store.get('token');  }];  $httpProvider.interceptors.push('jwtInterceptor');  // ...});collAgeApp.run(function($rootScope, auth, store, jwtHelper, $location) {  // This events gets triggered on refresh or URL change  $rootScope.$on('$locationChangeStart', function() {    var token = store.get('token');    if (token) {      if (!jwtHelper.isTokenExpired(token)) {        if (!auth.isAuthenticated) {          auth.authenticate(store.get('profile'), token);        }      } else {        // Either show the login page or use the refresh token to get a new idToken        $location.path('/');      }    }  });});collAgeApp.config(function($routeProvider) { $routeProvider     .when('/main', {      templateUrl : 'views/main.html',      controller  : 'mainController as mainCtrl'     })     .when('/profile', {      templateUrl : 'views/profile.html',      //controller  : 'aboutController',      requiresLogin: true     })     .when('/new-activity', {      templateUrl : 'views/new-activity.html',      //controller  : 'contactController',      requiresLogin: true     })     .when ('/event/:id', {      templateUrl: 'views/event-details.html',      controller: 'eventController',      requiresLogin: true,      resolve: {        canEdit: function() {            return true;        },        currEvent: function($route, eventService) {            var promise = eventService.getEventById($route.current.params.id);            return promise;        }      }     })     .when('/calendar', {       templateUrl : 'views/calendar.html',        controller  : 'calendarController as calendarCtrl'     })     .otherwise({      redirectTo: '/main'     });});collAgeApp.controller('mainController',['$scope', 'eventService', 'auth', function($scope, eventService, auth) {    $scope.auth = auth;    $scope.isAdmin = false;    $scope.user = {};    eventService.events().success(function(data) {        $scope.activities = data;    })    .error(function(err) {        console.log(err);    });    $scope.signin = function() {        auth.signin({            popup: true,            chrome: true        },        function(profile, idToken, accessToken, state, refreshToken) {          $location.path('/profile');        },        function(err){            console.log(err);        });    };    $scope.signout = function() {        auth.signout();    };}]);collAgeApp.controller('eventController', ['$scope','currEvent','auth', function($scope, currEvent, auth) {    var eventCtrl = this;    $scope.auth = auth;    $scope.event = currEvent.data;   /* var map = new GMaps({                  el : '#map',                  lat: 32.146414,                  lng: 34.888305,                  zoom: 16                });*///    map.addMarker({//          lat: 32.146414,//          lng: 34.888305,//          title: 'HHH',//          infoWindow: {//            content: '<p>HTML Content</p>'//          }//    });}]);collAgeApp.controller('calendarController', function($scope, $compile, uiCalendarConfig, eventService){    var date = new Date();    var d = date.getDate();    var m = date.getMonth();    var y = date.getFullYear();    $scope.all_events = [];    $scope.eventSources = [{     events: $scope.all_events,     color: 'light-blue', // an option!     textColor: 'black', // an option!    }];    eventService.events().success(function(data) {        for(var i = 0; i < data.length; ++i) {          $scope.all_events.push(data[i]);        }    });     /* Render Tooltip */    $scope.eventRender = function( event, element, view ) {        element.attr({'data-toggle': "tooltip",                     'title': event.title});        $compile(element)($scope);    };    /* config object */    $scope.uiConfig = {      calendar:{        height: 500,        editable: false,        dayNames: ["ראשון", "שני", "שלישי", "רביעי", "חמישי", "שישי", "שבת"],        dayNamesShort: ["ראשון", "שני", "שלישי", "רביעי", "חמישי", "שישי", "שבת"],        monthNames: ['ינואר', 'פבואר', 'מרץ', 'אפריל', 'מאי', 'יוני', 'יולי', 'אוגוסט', 'ספטמבר', 'אוקטובר', 'נובמבר', 'דצמבר'],        isRTL: true,        timeFormat: 'h:mm',        header: {          left: 'month basicWeek',          center: 'title',          right: 'today next,prev'        },        buttonText: {            today: 'היום',            month: 'חודש',            week: 'שבוע',            day: 'יום'        },        eventRender: $scope.eventRender      }    };});