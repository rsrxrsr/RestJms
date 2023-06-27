//angular.module('app', [])
app.controller('appCtrl', function($scope, $location, $state) {
   var ctrl=this;
  
   ctrl.$onInit=function () {
	  console.info("appCtrl");
      console.log($state);
   }

   $scope.getPath = function() {
   	 return $location.path();
   }

   $scope.$watch("getPath()", function(newVal, oldVal){
	    if (newVal===oldVal) return;
    	console.log("App $Path:" + oldVal + " was changed to: "+newVal);
        if (newVal === "/app/area") {
           console.log("Acceso no autorizado");
		   //$state.go("login");
		}
   });
      
}) // End Controller 

.component("app", {
	templateUrl:"components/app/app.html",
	controller:"appCtrl"
		,bindings: {
   	     data: '<'
		}
});