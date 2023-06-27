//angular.module("entityService", [])
app.service("entityService", function(generalService, repositoryService, $filter) {
    console.log("entityService");
    var model = this;
    var repository = arguments[1];
    model.catalog =[];
    model.entity = {};

    // ReadAll
    this.readAll = function(catalog) {
		console.info("readAll", catalog);
		if (catalog) {
			let views=catalog.split(",");
			let promises=[];
    	    for (var view of views) {
        	    promises.push(model.read(view));
       		}
			return Promise.all(promises);
		}
		return new Promise((resolve)=>resolve(catalog));	
	}

    // Read
    this.read = function(view, orderBy) {
        return new Promise(function(resolve, reject) {
            console.info("Read", view);
            model.catalog[view] = [];
            repository.read(view, JSON.stringify(model.getFilter(view)), orderBy)
					  .then(onSucess, onError);
            function onSucess(response) {
                console.info("RR", response);
                model.catalog[view] = response;
                resolve(model.catalog[view]);
            }
            function onError(error) {
                reject(error);
            }
        })
    }

	// New
	this.new = function(view, entity) {
        return new Promise(function(resolve, reject) {
			console.log("New",view)
			model.errs=generalService.valid(view,entity);
			if (model.errs.length>0) {
				return reject("Error en validación")
			}				
			repository.new(view,entity)
			.then(response=>{
				model.entity=response
				resolve(response)				
			})
			.catch(error=>reject(error))
		})
	} 
    // Save
	this.save = function(view, entity) {
		console.log("Save",view);
		return repository.save(view,entity);		
	} 
    // Delete 
	this.delete = function(view, entity) {
		console.log("Delete",view);		
		return repository.delete(view,entity);		
	}
	
	// Valid
	this.valid = function(entity) {
		model.errs = [];		
    	if (!entity.nombre) {
			model.errs.push("Proporcione nombre")
		}
		return model.errs.length===0;
	} 

    // Filter ''
    model.getFilter = function(view) {
        var filter = {},
            operator = {};
        var items = document.querySelectorAll("." + view);
        if (items != null) {
            for (item of items) {
                //console.info("item.value",model.filtro[item.value]);
                filter[item.name] = model.filtro[item.name] ? model.filtro[item.name] : item.value;
                operator[item.name] = item.dataset.operator == undefined ? '=' : item.dataset.operator.substring(0, 2);
            }
        }
        filter = { "entity": filter, "operator": operator };
        console.info("filtro", filter)
        return filter;
    }
    // fmtEntity
    function fmtEntity(entity, view) {
        model.catalog[view].forEach(function(entity, index) {
            model.catalog[view][index] = fmtEntity(entity);
        });

        if (entity == null || entity == undefined) return;
        for (item in entity) {
            dato = entity[item];
            if (dato != undefined && isNaN(dato) && (dato.indexOf(".000Z") == 19 || dato.indexOf("T05:") == 10)) {
                entity[item] = new Date(getFecha(dato));
            }
        }
        return entity;
    }

    // Fecha Actual
    this.getDate = function(aa, mm, dd) {
            var date = new Date();
            //		return new Date(date.getFullYear(), date.getMonth(), 1);
            return new Date(aa, mm, dd);
        }
        // Fecha String
    function getFecha(fe) {
        if (fe == null) return;
        var dt = new Date(fe);
        var fecha = dt.toUTCString();
        return fecha;
    }
    //Mes String
    this.getMes = function(fe) {
        if (fe == null) return;
        var mes = ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"];
        return mes[fe.getMonth()];
    }

    // GetWeek
    Date.prototype.getWeek = function() {
        // function getWeek() {
        // call new Date(2017, 2, 4).getWeekNumber();
        var d = new Date(+this); //Creamos un nuevo Date con la fecha de "this".
        d.setHours(0, 0, 0, 0); //Nos aseguramos de limpiar la hora.
        d.setDate(d.getDate() + 4 - (d.getDay() || 7)); // Recorremos los días para asegurarnos de estar "dentro de la semana"
        //Finalmente, calculamos redondeando y ajustando por la naturaleza de los números en JS:
        return Math.ceil((((d - new Date(d.getFullYear(), 0, 1)) / 8.64e7) + 1) / 7);
    };
    // selectOption
    this.selectOption = function(A, p, ob) {
        if (ob != undefined)
            for (i = 0; i < A.length; i++)
                if (A[i][p] == ob[p]) return A[i];
        return {};
    }

    function format(fmt, dato) {
        var result;
        if (dato == null) {
            return "-";
        }
        result = Number(dato);
        if (isNaN(result)) {
            return dato;
        } else {
            dato = result;
        }
        switch (fmt) {
            case "RD":
                result = Number($filter("number")(dato, 2));
                break;
            case "ND":
                result = $filter("number")(dato, 2);
                break;
            case "NU":
                result = $filter("number")(dato);
                break;
            case "PC":
                result = $filter("number")(dato, 2) + "%";
                break;
            case "PE":
                result = $filter("currency")(dato);
                break;
            default:
                result = dato;;
        }
        return result;
    }

});