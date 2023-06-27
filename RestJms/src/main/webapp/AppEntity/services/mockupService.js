angular.module("mockup.module", [])
.service("repositoryService", function() {
	//app.service("mockupService", function($http) {
	var repository=this;
    console.log("mockupService");

    let mockup = [
		{
            "area": "Planeación 1",
            "nombre": "Area de Planeación",
            "central": "Dirección",
        },{
            "area": "Planeación 2",
            "nombre": "Area de Planeación",
            "central": "Dirección",
        },{
            "area": "Planeación 3",
            "nombre": "Area de Planeación",
            "central": "Dirección",
        }
       ];

    // Read
    this.read = function(view, filtro, orderBy) {
        return new Promise(function(resolve, reject) {
            console.info("Hana.read", view);
            resolve(mockup);
        })
    }

	this.new = function(view, entity) {
		console.log("Save", view);
		mockup.push(entity);
		return new Promise((resolve)=>resolve(entity));			
	} 

	this.save = function(view, entity) {
		console.log("Save", view);
		let pos = mockup.indexOf(entity);
		console.log("Entity",entity['area'],entity,"Post=",pos);
		if (pos>=0) {
			mockup[pos]=entity;
		} else {
			//mockup.push(entity);
		    console.log("Inexistente");			
		} 
		return new Promise((resolve)=>resolve(entity));			
	} 

	this.delete = function(view, entity) {		
		console.log("Delete", view);
		let pos = mockup.indexOf(entity);
		mockup.splice(pos,1);		
		return new Promise((resolve)=>resolve(entity));			
	} 
	
});