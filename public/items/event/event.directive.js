collAgeApp.directive('collageEvent', function(){
    return {
        restrict: 'E',
        templateUrl: "items/event/event.template.html"
        scope: {
            name: "Bar Gal"
        }
    }
});