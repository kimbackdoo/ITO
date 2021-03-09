var ApiMainComponent = Vue.component('api-main-component', function (resolve, reject) {
    axios.get("/resources/vue/component/app/api/main.html").then(function (response) {
        resolve({
            "template": response.data,
            "data": function () {
                return {
                    "api": {
                        "panels": {
                            "list": [0]
                        },
                        "dataTable": {
                            "headers": [
                                {"text": "URL", "sortable": false, "value": "apiId.url"},
                                {"text": "Method", "sortable": false, "value": "apiId.method"},
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
                "api.dataTable.page": {
                    "handler": function (newValue, oldValue) {
                        this.setApiList();
                    },
                    "deep": true
                }
            },
            "methods": {
                "getApiList": function (params) {
                    return axios({
                        "url": "/api/common/apis",
                        "method": "get",
                        "params": params
                    });
                },
                "setApiList": function () {
                    var self = this;
                    return new Promise(function (resolve, reject) {
                        Promise.resolve()
                            .then(function () {
                                var params = {
                                        "page": self.api.dataTable.page,
                                        "size": self.api.dataTable.itemsPerPage
                                    };
                                self.api.dataTable.loading = true;
                                return self.getApiList(params);
                            })
                            .then(function (response) {
                                var data = response.data;
                                self.api.dataTable.items = data.content;
                                self.api.dataTable.serverItemsLength = data.totalElements;
                                self.api.dataTable.loading = false;
                            })
                            .then(function () { resolve(); });
                    });
                }
            },
            "mounted": function () {
                Promise.resolve()
                    .then(this.setApiList);
            }
        });
    });
});