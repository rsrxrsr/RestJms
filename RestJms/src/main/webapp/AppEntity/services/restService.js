angular.module("rest.module", [])
   .service("repositoryService", function($http) {
// app.service("restService", function($http) {
	var repository=this;
    console.log("restService");

    this.getUrl = function(object) {
	    let url;
        if (typeof object === "string") {
		    //url = "http://localhost:8080/restapi/" + object;		       
		    url = "https://localhost:443/restapi/" + object;		       
		} else {
			url = object._links.self.href;
		}
		return url;    	
	}
	
	this.getId = function(object) {
		let url=repository.getUrl(object);
		let idx=url.lastIndexOf("/")+1;
		let id=url.substring(idx);
		return id;		
	}

    // Read
    this.read = function(view, filtro, orderBy) {
        return new Promise(function(resolve, reject) {
            console.info("Rest.read.pipe", view);
            //service = repository.getUrl(view) + "&view=" + view + "&filtro=" + filtro + "&orderBy=" + orderBy;
            let service = repository.getUrl(view);
            $http.get(service)
				.then(onSucess, onError);
		    function onSucess(response) {
		        console.log(response);
				let recordset = Object.values(response.data._embedded)[0];
				data = recordset.map(record=>{
					record["id"]=repository.getId(record);
					return record;
				})
				console.log(data); 
		        resolve(data);
		    }
		    function onError(error) {
		        console.info('Error', error);
		        reject(error);
		    }
        })
    }

    this.new = function (view, entity) {
        return new Promise(function(resolve, reject) {
	        console.log("New:",view,entity);
			let service = repository.getUrl(view);		
	        $http.post(service, JSON.stringify(entity)
	             ,{headers: {'Content-Type': 'application/json'}})
				.then(response=>{
					response.data["id"]=repository.getId(response.data)
					resolve(response.data)	
					console.log(response.data)					
				})
				.catch(error=>reject(error))
        })
    };
    // Save
    this.save = function (view, entity) {
        console.info("save:",view,entity);
		let service = repository.getUrl(entity);		
        return $http.put(service, JSON.stringify(entity)
             ,{headers: {'Content-Type': 'application/json'}})
    };
    // Delete
    this.delete = function (view, entity) {
        console.info("delete:",view,entity);
		let service = repository.getUrl(entity);		
        return $http.delete(service);
    };
 	
});

/*
	this.save = function(view, entity) {
		console.log("Save", view);
	    return $http.post(this.getUrl(entityName), JSON.stringify(entity), this.getHttpOptions())
	      .pipe(catchError(this.errorHandler))
	} 

	this.delete = function(view, entity) {		
		console.log("Delete", view);
	    return $http.post(this.getUrl(entityName), JSON.stringify(entity), this.getHttpOptions())
	      .pipe(catchError(this.errorHandler))
	}
	
		// var SERVICE="XsServices/RSRentityService.xsjs?option=x13";
		// SERVICE+='&entity='+model.entityName+"&view="+model.viewName;
	
		// New

*/