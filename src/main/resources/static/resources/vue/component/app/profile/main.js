var ProfileMainComponent = Vue.component('profile-main-component', function (resolve, reject) {
    axios.get("/resources/vue/component/app/profile/main.html").then(function (response) {
        resolve({
            "template": response.data,
            "data": function () {
                return {
                    "profile": {
                        "panels": {
                            "list": [0]
                        },
                        "dataTable": {
                            "headers": [
                                {"text": "이름", "sortable": false, "value": "name"},
                                {"text": "직업", "sortable": false, "value": "job"},
                                {"text": "기술", "sortable": false, "value": "skill"},
                                {"text": "생년월일", "sortable": false, "value": "birth"},
                                {"text": "경력", "sortable": false, "value": "career"},
                                {"text": "E-Mail", "sortable": false, "value": "email"},
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
                            {"text": "50", "value": 50},
                            {"text": "60", "value": 60},
                        ]
                    }
                };
            },
            "watch": {
                "profile.dataTable.page": {
                    "handler": function (newValue, oldValue) {
                        this.setProfileList();
                    },
                    "deep": true
                }
            },
            "methods": {
                "getProfileList": function (params) {
                    return axios({
                        "url": "/api/common/apis",
                        "method": "get",
                        "params": params
                    });
                },
                "setProfileList": function () {
                    var self = this;
                    return new Promise(function (resolve, reject) {
                        Promise.resolve()
                            .then(function () {
                                var params = {
                                    "page": self.profile.dataTable.page,
                                    "size": self.profile.dataTable.itemsPerPage
                                };
                                self.profile.dataTable.loading = true;
                                return self.getProfileList(params);
                            })
                            .then(function (response) {
                                var data = response.data;
                                self.profile.dataTable.items = data.content;
                                self.profile.dataTable.serverItemsLength = data.totalElements;
                                self.profile.dataTable.loading = false;
                            })
                            .then(function () { resolve(); });
                    });
                }
            },
            "mounted": function () {
                Promise.resolve()
                    .then(this.setProfileList);
            }
        });
    });
});