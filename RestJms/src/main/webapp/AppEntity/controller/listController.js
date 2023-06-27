app.controller('listCtrl', 
function($scope, $state, entityService) {
    console.log("listCtrl");
    var ctrl=this;
    ctrl.model=entityService;

    ctrl.$onInit=function () {
		console.info("onInit");
		ctrl.read(ctrl.view);
	};
	
	ctrl.read=function(view) {
		entityService.read(view)
		 .then(response=>{
			console.log("Response",response)
			$scope.$apply();
		});
	}
	
	ctrl.selectRow=function(entity) {
		console.log("selectRow:",entity);
		entityService.entity=entity;
        $state.go("app."+ctrl.view,{action: "update"});
	}
			
	ctrl.delete=function(entity) {
		console.log("delete:",entity);
	}

}); //End Controller
