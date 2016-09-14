(function () {
 var userService = function($http) {
        return {
            getUsersByName: function(name) {
                return $http.get('/getUsersByName/' + name);
            },

            getUserByMail: function(mail) {
                return $http.get('/getUserByMail/' + mail);
            },

            getUsers: function() {
                return $http.get('/getUsers');
            }
        };
    };

    var userPreviewDirective = function () {
        return {
            restrict: 'E',
            scope: {
                id: '@',
                ngModel: '='
            },
            replace: true,
            templateUrl: 'components/user/user.html',
            controller: ['$scope', '$location', function($scope, $location) {
                $scope.showUserProfile = function(id) {
                    $location.path('/profile/' + id);
                }
            }],
            link: function ($scope, element, attrs) {
                var userProfile = $scope.ngModel;
                $scope.name = userProfile.firstName + " " + userProfile.lastName;
                $scope.id = userProfile.mail;
                $scope.profilePic = userProfile.profilePic;
            } //DOM manipulation
        };
    };

    angular.module('collAgeUserPrevDirective',['ngRoute']).service('userService', userService).directive('userPreview', userPreviewDirective);
}());