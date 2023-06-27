app.component("area", {
    templateUrl: "view/area.html",
    controller: "formCtrl",
    bindings: {
        view: '@',
		catalog: '@'
    }
});