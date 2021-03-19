var MainAdminProjectListPage = Vue.component('main-admin-project-list-page', function (resolve, reject) {
    axios.get("/vue/page/main/admin/project-list/main.html").then(function (response) {
        resolve({
            "template": response.data,
            "data": function () {
                return {
                    "project": {
                        "panels": {
                            "search": [],
                            "list": [0]
                        },
                        "project": {
                            "name": "",

                        }
                    }
                };
            },
            "methods": {
            },
            "mounted": function () {
            }
        });
    });
});