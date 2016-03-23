var collAgeApp = angular.module('collAgeApp', ['ngRoute']);

collAgeApp.config(function($routeProvider) {
  $routeProvider
      // route for the home page
      .when('/', {
          templateUrl : 'pages/main.html',
          controller  : 'homeController'
      })

      // route for the about page
      .when('/right', {
          templateUrl : 'pages/right-sidebar.html',
          //controller  : 'aboutController'
      })

      // route for the contact page
      .when('/contact', {
          templateUrl : 'pages/contact.html',
          controller  : 'contactController'
      })
      .otherwise({
        redirectTo: '/'
      });
});