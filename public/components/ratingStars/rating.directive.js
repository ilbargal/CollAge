(function() {
    'use strict';
    angular
        .module('collAgeDirectives', [])
        .controller('RatingController', RatingController)
        .directive('starRating', starRating);

    function RatingController() {
        this.rating1 = 5;
        this.rating2 = 2;
        this.isReadonly = true;
        this.rateFunction = function(rating) {
            console.log('Rating selected: ' + rating);
        };
    }

    function starRating() {
        return {
            restrict: 'EA',
            templateUrl: 'components/ratingStars/ratingStars.html',
            scope: {
                ratingValue: '=ngModel',
                max: '=?', // optional (default is 5)
                onRatingSelect: '&?',
                readonly: '=?'
            },
            link: function(scope, element, attributes) {
                if (scope.max == undefined) {
                    scope.max = 5;
                }
                function updateStars() {
                    scope.stars = [];
                    for (var i = 0; i < scope.max; i++) {
                        scope.stars.push({
                            filled: i < scope.ratingValue
                        });
                    }
                };
                scope.toggle = function(index) {
                    if (scope.readonly == undefined || scope.readonly === false){
                        scope.ratingValue = index + 1;
                        scope.onRatingSelect({
                            rating: index + 1
                        });
                    }
                };
                scope.$watch('ratingValue', function(oldValue, newValue) {
                    if (newValue) {
                        updateStars();
                    }
                });
            }
        };
    }
})();
