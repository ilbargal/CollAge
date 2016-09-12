(function () {
    var userPreviewDirective = function () {
        return {
            restrict: 'E',
            scope: {
                id: '@',
                name: '@',
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
                $scope.name = userProfile.name;
                $scope.profilePic = userProfile.profilePic;
            } //DOM manipulation
        };
    };

    angular.module('collAgeUserPrevDirective',['ngRoute']).directive('userPreview', userPreviewDirective);
}());