app.controller('formCtrl', 
function($scope, $location, $state, $stateParams, entityService) {
    console.log("formCtrl");
    var ctrl=this;
	ctrl.model=entityService;
    ctrl.entity = {};
	ctrl.model.errs=[];
	ctrl.eventSource = {};

    ctrl.$onInit=function() {
		console.info("url", $location.url(), $stateParams);
		entityService.readAll(ctrl.catalog)
				.then(ctrl.onSucess,ctrl.onError);			
		//ctrl.isUpdate = $location.url().includes("update");
		ctrl.action=$stateParams.action;		
		if (ctrl.action==="update") {
			ctrl.entity=entityService.entity;
		} 
	};

	ctrl.list=function(entity) {
		console.log("List:",entity);
		entityService.entity=entity;
        $state.go("app."+ctrl.view+"s");
	}

	ctrl.new=function() {
		console.log("New",ctrl.view,ctrl.entity);
		entityService.new(ctrl.view,ctrl.entity)
			.then(response=>{
				ctrl.entity=response;
				ctrl.onSucess(response);
				$scope.$apply();
			})
			.catch(ctrl.onError);
	}
	
	ctrl.save=function() {
		console.log("Save",ctrl.view,ctrl.entity);
		entityService.save(ctrl.view,ctrl.entity)
			.then(ctrl.onSucess,ctrl.onError);
	}
	
	ctrl.delete=function() {
		console.log("Delete",ctrl.view,ctrl.entity);
		entityService.delete(ctrl.view,ctrl.entity)
			.then(ctrl.onSucess,ctrl.onError);
	}
		
    ctrl.back=function() {
		console.log("back");
		//console.log("$state", $state.current.name)
		window.history.back();
	}
	
    ctrl.onSucess=function(response) {
	    console.log("onSuccess",response);
        ctrl.visor="Proceso efectuado ... " + new Date();
	}
	
	ctrl.onError=function(error) {
    	console.info('onError',error);
		ctrl.visor="Error en servicio... ";
	}
	
	ctrl.subscribe=function() {
		ctrl.eventSource = new EventSource("https://localhost/webapi/subscribe");
		ctrl.eventSource.onopen = event => console.log("Open Subscription");
		ctrl.eventSource.onerror = error => console.log("Error Subscription", error);
		ctrl.eventSource.onmessage = function(event) {
			ctrl.visor="Recive Event Server";
			console.log("Brodcast", event.data);
		}
		ctrl.eventSource.addEventListener("brodcast", event=>{
			console.log("Server Event", event.data);
		});	
	}

}); //End Controller
