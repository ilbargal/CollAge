var collAgeApp = angular.module('collAgeApp', ['ngRoute', 'auth0', 'angular-storage', 'angular-jwt', 'collAgeDirectives', 'ui.calendar', 'ngAutocomplete', 'multipleSelect', 'ngAutocomplete']);collAgeApp.config(function (authProvider) {  authProvider.init({    domain: 'collage.auth0.com',    clientID: 'CJvEGDMZs53qqj0dUMOV62ZN65Oj3ARu'  });}).run(function(auth) {  // This hooks al auth events to check everything as soon as the app starts  auth.hookEvents();});// app.jscollAgeApp.config(function (authProvider, $routeProvider, $httpProvider, jwtInterceptorProvider) {  authProvider.on('loginSuccess', function($location, profilePromise, idToken, store) {    profilePromise.then(function(profile) {      store.set('profile', profile);      store.set('token', idToken);    });  });  authProvider.on('loginFailure', function() {         // Error Callback  });  // We're annotating this function so that the `store` is injected correctly when this file is minified  jwtInterceptorProvider.tokenGetter = ['store', function(store) {    // Return the saved token    return store.get('token');  }];  $httpProvider.interceptors.push('jwtInterceptor');});collAgeApp.run(function($rootScope, auth, store, jwtHelper, $location, authService) {    // This events gets triggered on refresh or URL change    $rootScope.$on('$locationChangeStart', function () {        var token = store.get('token');        if (token) {            if (!auth.isAuthenticated) {                auth.authenticate(store.get('profile'), token);            }        }        else {            // Either show the login page or use the refresh token to get a new idToken            //$location.path('/main');            alertify.error('עליך להתחבר למערכת');            $location.path('/main');        }    });});collAgeApp.config(function($routeProvider) { $routeProvider     .when('/main', {      templateUrl : 'views/main.html',      controller  : 'mainController',     requiresLogin: true     })     .when('/profile', {      templateUrl : 'views/profile.html',      controller  : 'UsersController',      requiresLogin: true     })     .when('/new-activity', {      templateUrl : 'views/new-activity.html',      controller  : 'newActivityController',      requiresLogin: true     })     .when ('/event/:id', {      templateUrl: 'views/event-details.html',      controller: 'eventController',      requiresLogin: true,      resolve: {        canEdit: function() {            return true;        },        currEvent: function($route, eventService) {            var promise = eventService.getEventById($route.current.params.id);            return promise;        }      }     })     .when ('/signup',{        templateUrl: 'views/sign-up.html',         controller: 'signupController',         requiresLogin: false     })     .when ('/signin',{         templateUrl: 'views/sign-in.html',         controller: 'signinController',         requiresLogin: false     })     .when('/calendar', {       templateUrl : 'views/calendar.html',        controller  : 'calendarController as calendarCtrl'     })     .otherwise({      redirectTo: '/main'     });});collAgeApp.controller('morisController', function($scope, $http, auth, store){    $scope.auth = auth;    $scope.signout = function() {        localStorage.clear();        auth.signout();        $location.path('/main');    };    $scope.signup = function() {        auth.signup({                connection: 'Username-Password-Authentication'            },            function(profile, idToken, accessToken, state, refreshToken) {                $location.path('/profile');            });    }})collAgeApp.controller('mainController', function($scope, $http, $window, auth, eventService, $location, $route) {    $scope.auth = auth;    $scope.isAdmin = false;    $scope.user = {};    $scope.messageNoEvents = "טוען אירועים";    if ($scope.auth.profile == undefined){        eventService.getRecommendedEvents("gal.moreh@gmail.com").success(function(data) {            $scope.activities = data;            $scope.messageNoEvents = "";        });    } else {        eventService.getRecommendedEvents($scope.auth.profile.user_metadata.mail).success(function (data) {            $scope.activities = data;            $scope.messageNoEvents = "";        });    }    $scope.signin = function() {        auth.signin({            popup: true,            chrome: true,            connection: 'Username-Password-Authentication'        },        function(profile, idToken, accessToken, state, refreshToken) {          $location.path('/profile');        },        function(err){            console.log('MyError: ' + err);        });    };    $scope.signup = function() {        auth.signup({            connection: 'Username-Password-Authentication'        },        function(profile, idToken, accessToken, state, refreshToken) {            debugger;            $location.path('/profile');        },        function(err){            console.log('signup error: ' + err);        });    }    $scope.signout = function() {        $location.path('/main');        localStorage.clear();        auth.signout();        $route.reload();    };});collAgeApp.controller('eventController', ['$scope','currEvent','auth', '$http', '$location', function($scope, currEvent, auth, $http, $location) {    var eventCtrl = this;    $scope.auth = auth;    $scope.event = currEvent.data;    $scope.map = new GMaps({        el: '#eventMap',        lat: 32.09204338934781,        lng: 34.781748577952385    });    GMaps.geocode({        address: $scope.event.location,        callback: function(results, status) {            if (status == 'OK') {                var latlng = results[0].geometry.location;                $scope.map.setCenter(latlng.lat(), latlng.lng());                $scope.map.addMarker({                    lat: latlng.lat(),                    lng: latlng.lng()                });            }        }    });    $scope.saveEventStatus = function(status) {        var serverDetails = {            eventId : $scope.event.id,            userMail: $scope.auth.profile.user_metadata.email,            statusCode: status        }        $http.post('/saveEventStatus', serverDetails)            .success(function(res) {                $location.reload();            })            .error(function(err) {                console.log(err);            })    }}]);collAgeApp.controller('newActivityController', ['$scope', '$location', 'auth','eventService',function($scope, $location, auth, eventService){    $scope.auth = auth;    $scope.activityLocation = 'תל אביב';    debugger;    $scope.addActivity = function() {        var event = {            name: $scope.activityName,            description: $scope.activityDescription,            owner: $scope.auth.profile.user_metadata.mail,            imagePath: $scope.imagePath,            location: $scope.activityLocation,            datetime: $scope.activityDatetime,            categories: $scope.selectedCategories        };        if ($scope.imagePath == undefined) {            event.imagePath = "../images/pic08.jpg";        }        eventService.addEvent(event).success(function(id) {            console.log('added event');            alertify.success('האירוע נוסף בהצלחה. הנך מועבר לפרטי האירוע')            $location.path("/event/" + id);        }).error(function(err) {           console.log(err);            alertify.error('אירעה שגיאה בהוספת האירוע');        });    }    eventService.getAllCategories().then(function(data){        $scope.allCategories = data.data;    });    $scope.locationPoint = {        lat: 32.09204338934781,        lng: 34.781748577952385    };    $scope.map = new GMaps({        el: '#map',        lat: $scope.locationPoint.lat,        lng: $scope.locationPoint.lng,        click: function(e) {            $scope.map.removeMarkers();            var marker = new google.maps.Marker({                position: e.latLng,                map: $scope.map            });            $scope.locationPoint = {                lat: e.latLng.lat(),                lng: e.latLng.lng()            };            $scope.map.addMarker(marker);            $scope.map.panTo(e.latLng);        }    });    $scope.map.addMarker({        lat: $scope.locationPoint.lat,        lng: $scope.locationPoint.lng    });    $scope.searchOnMap = function(textLocation) {        GMaps.geocode({            address: textLocation,            callback: function(results, status) {                if (status == 'OK') {                    var latlng = results[0].geometry.location;                    $scope.map.setCenter(latlng.lat(), latlng.lng());                    $scope.map.removeMarkers();                    $scope.map.addMarker({                        lat: latlng.lat(),                        lng: latlng.lng()                    });                }            }        });    }}]);collAgeApp.controller('userController', ['$scope','currUser','auth', function($scope, currUser, auth) {    var userCtrl = this;    $scope.auth = auth;    $scope.event = currUser.data;}]);collAgeApp.controller('UsersController', function($scope, $http, $window, $location, auth, eventService, store) {    $scope.auth = auth;    eventService.getAllCategories().then(function(data){        $scope.allCategories = data.data;    });    var userid = auth.profile.user_id;    $scope.profile = auth.profile.user_metadata;    if ($scope.profile == undefined) {        $scope.profile = {            mail: auth.profile.email,            password: auth.profile.password,            birthday: new Date(),            type: "0",            gender: "0",            selectedCategories : []        };        // Facebook connection        if (auth.profile.user_id.startsWith("facebook")) {            $scope.profile.firstname = auth.profile.given_name;            $scope.profile.lastname = auth.profile.family_name;            $scope.profile.profilePic = auth.profile.picture;        }    }        // Facebook connectio    else {        $scope.profile.birthday = new Date($scope.profile.birthday);        $scope.profile.type = auth.profile.user_metadata.type;        $scope.profile.selectedCategories = $scope.profile.categories;    }    $scope.saveProfile = function() {        var payload = {            mail:           $scope.profile.mail,            //password:       $scope.profile.password,            type:           $scope.profile.type,            firstname:      $scope.profile.firstname,            lastname:       $scope.profile.lastname,            firstName:      $scope.profile.firstname,            lastName:       $scope.profile.lastname,            gender:         $scope.profile.gender,            birthday:       new Date($scope.profile.birthday),            address:        $scope.profile.address,            phone:          $scope.profile.phone,            job:            $scope.profile.job,            profilePic:    $scope.profile.profilePic,            description:    $scope.profile.description,            categories:     $scope.profile.selectedCategories        };        $scope.profile.picture = payload.profilePic;        // $http.patch("https://collage.auth0.com/api/v2/users/" + userid, {"user_metadata": payload}).        // success(function(profile){        //     store.remove('profile');        //     store.set('profile', profile);        //        //     // Redirect to main page        //     $location.path('/');        //     alertify.success('המשתמש עודכן בהצלחה');        // });        $http.post('/updateUser', payload).            success(function(data) {            alertify.success('המשתמש עודכן בהצלחה');            $http.patch("https://collage.auth0.com/api/v2/users/" + userid, {"user_metadata": payload}).            success(function(profile){                store.remove('profile');                store.set('profile', profile);                // Redirect to main page                $location.path('/');                alertify.success('המשתמש עודכן בהצלחה');            })                .err(function(err){});        }).            error(function(err) {            console.log(err);        });    }});collAgeApp.controller('calendarController', function($scope, $compile, uiCalendarConfig, eventService){    var date = new Date();    var d = date.getDate();    var m = date.getMonth();    var y = date.getFullYear();    eventService.getAllEvents().success(function(data) {        $scope.activities = data;        var all_events = data;        $scope.eventSources[0].push({"id":2,"title":"שחייה צורנית","description":"שחיה בספורטן העירוני בשלל צורות מעניינות. דרושים משתתפים רבים","location":{"x":35.0,"y":32.0},"start":1462741200000,"end":1463000400000,"imagePath":"http://images.mouse.co.il/storage/8/f/490_fake_gay.jpg","name":"שחייה צורנית"})        for(var i=0; i< data.length; i++) {            var strDate = data[i].datetime;            var d = new Date(strDate.substring(6, 10),                strDate.substring(3, 5),                strDate.substring(0, 2),                '11',                '51',                '00');            strDate = strDate.replace(/\//g, "-");            strDate = strDate.replace(" ", "T");            strDate = strDate + ":00";            $scope.eventSources[0].push(                {                    "id": data[i].id,                    "title": data[i].name,                    "description": data[i].description,                    "start": d,                    "end": d,                    "name": data[i].name                }            );        }        uiCalendarConfig.calendars.myCalendar1.fullCalendar('render')    });    /* Render Tooltip */    $scope.eventRender = function( event, element, view ) {        element.attr({'data-toggle': "tooltip",            'title': event.title});        $compile(element)($scope);    };    $scope.renderCalender = function(calendar) {        if(uiCalendarConfig.calendars[calendar]){            uiCalendarConfig.calendars[calendar].fullCalendar('render');        }    };    /* config object */    $scope.uiConfig = {        calendar:{            height: 500,            editable: false,            dayNames: ["ראשון", "שני", "שלישי", "רביעי", "חמישי", "שישי", "שבת"],            dayNamesShort: ["ראשון", "שני", "שלישי", "רביעי", "חמישי", "שישי", "שבת"],            monthNames: ['ינואר', 'פבואר', 'מרץ', 'אפריל', 'מאי', 'יוני', 'יולי', 'אוגוסט', 'ספטמבר', 'אוקטובר', 'נובמבר', 'דצמבר'],            isRTL: true,            timeFormat: 'h:mm',            header: {                left: 'month basicWeek',                center: 'title',                right: 'today next,prev'            },            buttonText: {                today: 'היום',                month: 'חודש',                week: 'שבוע',                day: 'יום'            },            eventRender: $scope.eventRender        }    };});