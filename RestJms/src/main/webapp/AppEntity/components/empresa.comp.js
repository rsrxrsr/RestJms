app.component("empresa", {
    templateUrl: "view/empresa.html",
    controller: "formCtrl",
    bindings: {
        view: '@',
		catalog: '@'
    }
});