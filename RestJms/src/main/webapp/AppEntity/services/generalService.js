//angular.module("valid.module", [])
app.service("generalService", function(empresaService) {
    console.log("generalService");
	_this=this;	
	this.model={"empresa" : empresaService};
	this.valid=function(view,entity) {
		let errs=[];
		if (_this.model[view]) {
			errs=_this.model[view].valid(entity);		
		}
		return errs;
	}
});