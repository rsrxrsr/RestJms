//angular.module("mockup.module", [])
app.service("empresaService", function() {
	var model=this;
    console.log("empresaService");	
	// Valid
	model.valid = function(entity) {
		model.errs = [];		
    	if (!entity.empresa) {
			model.errs.push("Proporcione empresa")
		}
    	if (!entity.grupo) {
			model.errs.push("Proporcione grupo")
		}
//		return model.errs.length===0;
		return model.errs;
	} 	
});