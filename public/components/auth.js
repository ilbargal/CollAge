(function () {
    var authService = function() {
        var loginFacebook = function() {
            hello('facebook').login({
              // default is 'popup'
              display: 'popup',
              // for explicit grant, default is 'token' for implicit grant
              response_type: 'code',
              // default is true for initiate auth flow, despite current valid token
              force: false
            });
        };

        return {
            login: function(userId) {
                return loginFacebook();
            }
        };
    };
    collAgeApp.service('authService', authService);
}());