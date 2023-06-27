// Controller
app.controller('mainCtrl', function($scope, $location, $filter, entityService) {
    model = this;
//
    obj = JSON.stringify($location);
    model.path = $location.path();
    console.log("mainCtrl",obj,model.path);

    $scope.$watch('$location.path()', function(newVal, oldVal){
    	console.log("Path:" + oldVal + " was changed to: "+newVal);
   	    model.path = newVal;
    });
/*
    model.format = function (fmt, dato) {
    	var result;
    	if (dato==null) {
    		return "-";
    	}
    	switch(fmt) {
        case "RD":
        	result=Number($filter("number")(dato,2));
        	break;
        case "ND":
        	result=$filter("number")(dato,2);
        	break;
        case "NU":
        	result=$filter("number")(dato);
        	break;
        case "PC":
        	result=$filter("number")(dato*100,2)+"%";
        	break;
        case "PE":
        	result=$filter("currency")(dato);
        	break;
        default:
        	result=dato;
            ;
    	}
    	return result;
    }

   $scope.$on('onChangeDATE', function(event, data) {
		console.info("Main.onChangeDATE",data);
    	model.filtro.FH_INFN=data;
		model.filtro.TX_DLGN_EDO='Nacional';
		model.readAll('TED');
	    $state.go('layout.ted.nacional');
	});
	
*/

}); // End Controller