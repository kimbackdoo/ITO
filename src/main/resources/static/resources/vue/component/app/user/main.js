var UserMainComponent = Vue.component('user-main-component', function (resolve, reject) {
    axios.get("/resources/vue/component/app/user/main.html").then(function (response) {
        resolve({
            "template": response.data,
            "data": function () {
                return {
                    "user": {
                        "panels": {
                            "list": [0]
                        },
                        "dataTable": {
                            "headers": [
                                {"text": "ID", "sortable": false, "value": "id"},
                                {"text": "Password", "sortable": false, "value": "password"}
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
                "user.dataTable.page": {
                    "handler": function (newValue, oldValue) {
                        this.setUserList();
                    },
                    "deep": true
                }
            },
            "methods": {
                "getUserList": function (params) {
                    return axios({
                        "url": "/api/common/users",
                        "method": "get",
                        "params": params
                    });
                },
                "setUserList": function () {
                    var self = this;
                    return new Promise(function (resolve, reject) {
                        Promise.resolve()
                            .then(function () {
                                var params = {
                                        "page": self.user.dataTable.page,
                                        "size": self.user.dataTable.itemsPerPage
                                    };
                                self.user.dataTable.loading = true;
                                return self.getUserList(params);
                            })
                            .then(function (response) {
                                var data = response.data;
                                self.user.dataTable.items = data.content;
                                self.user.dataTable.serverItemsLength = data.totalElements;
                                self.user.dataTable.loading = false;
                            })
                            .then(function () { resolve(); });
                    });
                }
            },
            "mounted": function () {
                Promise.resolve()
                    .then(this.setUserList);
            }
        });
    });
});