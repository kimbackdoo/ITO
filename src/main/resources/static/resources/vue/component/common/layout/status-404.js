var LayoutStatus404Component = Vue.component('layout-status-404-component', function (resolve, reject) {
    axios.get("/resources/vue/component/common/layout/status-404.html").then(function (response) {
        resolve({
            "template": response.data,
            "data": function () {
                return {
                };
            },
            "methods": {
            }
        });
    });
});