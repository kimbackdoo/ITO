var PersonMainComponent = Vue.component('person-main-component', function (resolve, reject) {
    axios.get("/resources/vue/component/app/person/main.html").then(function (response) {
        resolve({
            "template": response.data,
            "data": function () {
                return {
                    "person": {
                        "panels": {
                            "search": [0],
                            "list": [0]
                        },
                        "dataTable": {
                            "headers": [
                                {"text": "ID", "sortable": false, "value": "id"},
                                {"text": "First Name", "sortable": false, "value": "firstName"},
                                {"text": "Middle Name", "sortable": false, "value": "middleName"},
                                {"text": "Last Name", "sortable": false, "value": "lastName"},
                                {"text": "Birth Date", "sortable": false, "value": "birthDate"}
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
                            "firstName": "",
                            "middleName": "",
                            "lastName": "",
                            "birthDate": ""
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
                "person.dataTable.page": {
                    "handler": function (newValue, oldValue) {
                        Promise.resolve()
                            .then(this.setPersonList)
                            .then(this.replaceQuery);
                    }
                },
                "person.dataTable.itemsPerPage": {
                    "handler": function (newValue, oldValue) {
                        Promise.resolve()
                            .then(this.setPersonList)
                            .then(this.replaceQuery);
                    }
                }
            },
            "methods": {
                "getPersonList": function (params) {
                    return axios({
                        "url": "/api/common/people",
                        "method": "get",
                        "params": params
                    });
                },
                "setPersonList": function () {
                    var self = this;
                    return new Promise(function (resolve, reject) {
                        Promise.resolve()
                            .then(function () {
                                var params = {};
                                params.page = self.person.dataTable.page;
                                params.size = self.person.dataTable.itemsPerPage;
                                params.id = !_.isEmpty(self.person.query.id) ? self.person.query.id : null;
                                params.firstName = !_.isEmpty(self.person.query.firstName) ? self.person.query.firstName : null;
                                params.middleName = !_.isEmpty(self.person.query.middleName) ? self.person.query.middleName : null;
                                params.lastName = !_.isEmpty(self.person.query.lastName) ? self.person.query.lastName : null;
                                params.birthDate = !_.isEmpty(self.person.query.birthDate) ? self.person.query.birthDate : null;
                                self.person.dataTable.loading = true;
                                return self.getPersonList(params);
                            })
                            .then(function (response) {
                                var data = response.data;
                                self.person.dataTable.items = data.content;
                                self.person.dataTable.serverItemsLength = data.totalElements;
                                self.person.dataTable.loading = false;
                            })
                            .then(function () { resolve(); });
                    });
                },
                "getQuery": function () {
                    var query = {},
                        routeQuery = this.$route.query;
                    query.page = routeQuery.page ? routeQuery.page : String(this.person.dataTable.page);
                    query.size = routeQuery.size ? routeQuery.size : String(this.person.dataTable.itemsPerPage);
                    query.id = routeQuery.id ? routeQuery.id : this.person.query.id;
                    query.firstName = routeQuery.firstName ? routeQuery.firstName : this.person.query.firstName;
                    query.middleName = routeQuery.middleName ? routeQuery.middleName : this.person.query.middleName;
                    query.lastName = routeQuery.lastName ? routeQuery.lastName : this.person.query.lastName;
                    query.birthDate = routeQuery.birthDate ? routeQuery.birthDate : this.person.query.birthDate;
                    return query;
                },
                "setQuery": function () {
                    var query = this.getQuery();
                    this.person.dataTable.page = Number(query.page);
                    this.person.dataTable.itemsPerPage = Number(query.size);
                    this.person.query.id = query.id;
                    this.person.query.firstName = query.firstName;
                    this.person.query.middleName = query.middleName;
                    this.person.query.lastName = query.lastName;
                    this.person.query.birthDate = query.birthDate;
                },
                "replaceQuery": function () {
                    var query = {},
                        routeQuery = this.$route.query;
                    query.page = String(this.person.dataTable.page);
                    query.size = String(this.person.dataTable.itemsPerPage);
                    query.id = String(this.person.query.id);
                    query.firstName = String(this.person.query.firstName);
                    query.middleName = String(this.person.query.middleName);
                    query.lastName = String(this.person.query.lastName);
                    query.birthDate = String(this.person.query.birthDate);
                    if (!_.isEqual(query, routeQuery)) {
                        this.$router.replace({"query": query});
                    }
                },
                "search": function () {
                    var self = this;
                    return new Promise(function (resolve, reject) {
                        Promise.resolve()
                            .then(function () {
                                return self.setPersonList();
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
                            })
                            .then(function () {
                                self.person.query.id = "";
                                self.person.query.firstName = "";
                                self.person.query.middleName = "";
                                self.person.query.lastName = "";
                                self.person.query.birthDate = "";
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
                    .then(this.setPersonList);
            }
        });
    });
});