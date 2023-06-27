// App
var app = angular.module('App', ['ui.router', 'rest.module']);  // 'mockup|rest.module'
// Router
app.config(function($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise("/login");
    $stateProvider
        .state("login", {
            url: "/login",
            template: "<h1>login</h1>"
        })
        .state("redirect", {
            url: "/redirect",
            redirectTo: "login"
        })
        .state("app", {
            url: "/app",
            template: "<app></app>"
        })
        .state("app.area", {
            url: "/area/:action",
            //params: "{update: undefined}",
            template: "<area view='area'></area>"
        })
        .state("app.areas", {
            url: "/areas",
            template: "<areas view='area'></areas>"
        })
        .state("app.empresas", {
            url: "/empresas",
            template: "<empresas view='empresa' ></empresas>"
        })
        .state("app.empresa", {
            url: "/empresa/:action",
            //params: "{action: undefined}",
            template: "<empresa view='empresa' catalog='empresa'></empresa>"
        })
}); // End Router

 // Filter
 app.filter('filterNull', function() {
 	return function(p) {
    	return (p === null) ? "-" : p;
    }
});    
// End Filter 

// Interceptor
app.config(function($httpProvider) {
    $httpProvider.interceptors.push('httpInterceptor');
});

app.factory("httpInterceptor", function() {
	  sessionStorage.setItem(
          'token',
          'Basic ' + btoa('rsr' + ':' + 'password')
          //'Bearer ' + user.password
          //null ''
        );
	  //let req = request.clone({ headers: request.headers.set('Authorization', 'Basic ' + token) });

      var request = function request(config) {	        
		  //config.headers["x-api-key"] = "611221-123-300461";
		  config.headers["Authorization"] = sessionStorage.getItem('token');
	      return config;
      };
 
      return {
          request: request
      };
}); 
// End Interceptor 

// End App