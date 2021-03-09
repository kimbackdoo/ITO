var RoleMainComponent = Vue.component('role-main-component', function (resolve, reject) {
    axios.get("/resources/vue/component/app/role/main.html").then(function (response) {
        resolve({
            "template": response.data,
            "data": function () {
                return {
                    "role": {
                        "panels": {
                            "search": [0],
                            "list": [0]
                        },
                        "dataTable": {
                            "headers": [
                                {"text": "ID", "sortable": false, "value": "id"},
                                {"text": "Name", "sortable": false, "value": "name"},
                                {"text": "Description", "sortable": false, "value": "description"}
                            ],
                            "items": [],
                            "loading": false,
                            "serverItemsLength": 0,
                            "page": 1,
                            "itemsPerPage": 10
                        },
                        "pagination": {
                            "length": 10,
                            "totalVisible": 10
                        },
                        "query": {
                            "id": "",
                            "name": "",
                            "description": ""
                        },
                        "itemsPerPageItems": [
                            {"text": "10", "value": 10},
                            {"text": "20", "value": 20},
                            {"text": "30", "value": 30},
                            {"text": "40", "value": 40},
                            {"text": "50", "value": 50}
                        ]
                    }
                };
            },
            "watch": {
                "role.dataTable.page": {
                    "handler": function (newValue, oldValue) {
                        Promise.resolve()
                            .then(this.setRoleList)
                            .then(this.replaceQuery);
                    }
                },
                "role.dataTable.itemsPerPage": {
                    "handler": function (newValue, oldValue) {
                        Promise.resolve()
                            .then(this.setRoleList)
                            .then(this.replaceQuery);
                    }
                }
            },
            "methods": {
                "getRoleList": function (params) {
                    return axios({
                        "url": "/api/common/roles",
                        "method": "get",
                        "params": params
                    });
                },
                "setRoleList": function () {
                    var self = this;
                    return new Promise(function (resolve, reject) {
                        Promise.resolve()
                            .then(function () {
                                var params = {};
                                params.page = self.role.dataTable.page;
                                params.size = self.role.dataTable.itemsPerPage;
                                params.id = !_.isEmpty(self.role.query.id) ? self.role.query.id : null;
                                params.name = !_.isEmpty(self.role.query.name) ? self.role.query.name : null;
                                params.description = !_.isEmpty(self.role.query.description) ? self.role.query.description : null;
                                self.role.dataTable.loading = true;
                                return self.getRoleList(params);
                            })
                            .then(function (response) {
                                var data = response.data;
                                self.role.dataTable.items = data.content;
                                self.role.dataTable.serverItemsLength = data.totalElements;
                                self.role.dataTable.loading = false;
                            })
                            .then(function () { resolve(); });
                    });
                },
                "getQuery": function () {
                    var query = {},
                        routeQuery = this.$route.query;
                    query.page = routeQuery.page ? routeQuery.page : String(this.role.dataTable.page);
                    query.size = routeQuery.size ? routeQuery.size : String(this.role.dataTable.itemsPerPage);
                    query.id = routeQuery.id ? routeQuery.id : this.role.query.id;
                    query.name = routeQuery.name ? routeQuery.name : this.role.query.name;
                    query.description = routeQuery.description ? routeQuery.description : this.role.query.description;
                    return query;
                },
                "setQuery": function () {
                    var query = this.getQuery();
                    this.role.dataTable.page = Number(query.page);
                    this.role.dataTable.itemsPerPage = Number(query.size);
                    this.role.query.id = query.id;
                    this.role.query.name = query.name;
                    this.role.query.description = query.description;
                },
                "replaceQuery": function () {
                    var query = {},
                        routeQuery = this.$route.query;
                    query.page = String(this.role.dataTable.page);
                    query.size = String(this.role.dataTable.itemsPerPage);
                    query.id = String(this.role.query.id);
                    query.name = String(this.role.query.name);
                    query.description = String(this.role.query.description);
                    if (!_.isEqual(query, routeQuery)) {
                        this.$router.replace({"query": query});
                    }
                },
                "search": function () {
                    var self = this;
                    return new Promise(function (resolve, reject) {
                        Promise.resolve()
                            .then(function () {
                                self.role.dataTable.page = 1;
                            })
                            .then(function () {
                                return self.setRoleList();
                            })
                            .then(function () {
                                self.replaceQuery();
                            })
                            .then(function () { resolve(); });
                    });
                },
                "reset": function () {
                    var self = this;
                    return new Promise(function (resolve, reject) {
                        Promise.resolve()
                            .then(function () {
                                self.role.query.id = "";
                                self.role.query.name = "";
                                self.role.query.description = "";
                            })
                            .then(function () {
                                return self.search();
                            })
                            .then(function () { resolve(); });
                    });
                }
            },
            "mounted": function () {
                Promise.resolve()
                    .then(this.setQuery)
                    .then(this.replaceQuery)
                    .then(this.setRoleList);
            }
        });
    });
});