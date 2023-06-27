// App
var app = angular.module('App', ['ui.router', 'entityListComp', 'entityComp']);
// Router
   app.config(function($stateProvider, $urlRouterProvider) {
   	$urlRouterProvider.otherwise("/entityList");
   	$stateProvider
   	    .state("entityList", {
   	        url: "/entityList",
   	        template: "<entityList data=model.Catalog></entityList>"
   	    })   	    
   	    .state("entity", {
   	        url: "/entity",
   	        template: "<entity data=model.entity></entity>"
   	    }) 	    
   });
//Controller
   app.controller('mainCtrl', function($scope, $http, $location, $state, $filter, DTOptionsBuilder, DTColumnBuilder) {
	   
	   var model = this;
	   var SERVICE="XsServices/RSRentityService.xsjs?option=estandar";
	   $scope.options = DTOptionsBuilder.newOptions().withOption('order', [[2, 'asc']]);

       function start() {			
    	   console.info("Start",$location.path());
            var date = new Date();
            model.FECHA = new Date(date.getFullYear(), date.getMonth(), 1);
            //model.FECHA = new Date(date.getFullYear(), 3, 30);
	       	model.visor="";	       	
        	model.Catalog={};
        	model.filtro={};
        	read("VC_PERIODO");
        	read("VC_II_PERIODO");
         	//$state.go('layout.ted.nacional');
        	 //window.location.assign("https://091402shdwq01.dwq.infonavit.net:4320/XS_PLTF_INFT/SPK_PLTF_INFT/appHome/index.html")
        };

        // ReadAll
        model.readAll = function (view) {
    	  	console.info("readAll",view);
        	var items = document.querySelectorAll("."+view);
        	if (items!=null) {
	        	for (var item of items) {
            	read(item.dataset.viewName,item.dataset.viewOrder);
	        	}
        	}
        }
        // Read
        function read(view,viewOrder) {
        	console.info("Read",view);
         model.Catalog[view]=[];
        	service=SERVICE+"&view="+view+"&filtro=" +JSON.stringify(getFilter(view))+"&order="+viewOrder;
         $http.get(service).then(onSucess,onError);
        	//mockup();
            function onSucess(response) {
                console.info("RR",response);
                model.Catalog[view] = response.data.results;
                model.Catalog[view].forEach(function(entity,index) {
                	model.Catalog[view][index]=fmtEntity(entity);
                });
                transform(view);                
            };
            function onError(error) {
                console.info('Error', error);
            	model.visor="Error al accesar información";
            }
        };
        // Filter
        function getFilter(view) {
        	var filter={}, operator={};
        	var items = document.querySelectorAll("."+view);
        	if (items!=null) {
	        	for (item of items) {
	        		//console.info("item.value",model.filtro[item.value]);
	        		filter[item.name]= model.filtro[item.name] ? model.filtro[item.name] : item.value;
	        		operator[item.name] = item.dataset.operator==undefined ? '=' : element[i].dataset.operator.substring(0,2);
	        	}
        	}
         filter={"entity":filter,"operator":operator}; 
         console.info(view,filter)
        	return filter;
        }
        // fmtEntity
		function fmtEntity(entity) {
			if (entity==null || entity==undefined) return;
	      var element, dato;
			for (var label in entity) {
	    		dato=entity[label];
	    		// if (dato!=undefined && isNaN(dato) &&
				// (dato.indexOf("T06:")==10 || dato.indexOf("T05:")==10) ) {
		    	if (dato!=undefined && isNaN(dato) && (dato.indexOf(".000Z")==19 || dato.indexOf("T05:")==10) ) {
	    			entity[label]= new Date(getFecha(dato));
	    		}
			}
			return entity;
		}
	   // Fecha Db
        function getFecha (fe) {
        	if (fe==null) return;
        	var dt = new Date(fe);
        	var fecha=dt.toUTCString();
        	return fecha;
        }
      // selectOption
		model.selectOption = function(A,p,ob) {
			if (ob!=undefined) for(i=0;i<A.length;i++) if(A[i][p]==ob[p]) return A[i];
			return {};
		}
	    
	   $scope.$on('onChangeDATE', function(event, data) {
			console.info("Main.onChangeDATE",data);
	    	model.filtro.FH_INFN=data;
			model.filtro.TX_DLGN_EDO='Nacional';
			model.readAll('TED');
		    $state.go('layout.ted.nacional');
		});
	    
		
	   start();

	    
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
	    
	   

   }); // End Controller
   
   //
   app.filter('filterNull', function() {
       return function(p) {
           return (p === null) ? "-" : p;
       }
   });    
//   
