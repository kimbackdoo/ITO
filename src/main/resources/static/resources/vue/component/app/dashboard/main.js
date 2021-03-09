var DashboardMainComponent = Vue.component('dashboard-main-component', function (resolve, reject) {
    axios.get("/resources/vue/component/app/dashboard/main.html").then(function (response) {
        resolve({
            "template": response.data,
            "data": function () {
                return {
                    "dashboard": {
                        "panels": {
                            "test": [0],
                            "dialog": [0],
                            "googleAnalytics": [0],
                            "requestMapping": [0],
                            "user": [0],
                            "person": [0],
                            "role": [0],
                            "menu": [0],
                            "api": [0]
                        }
                    },
                    "requestMapping": {
                        "dataTable": {
                            "headers": [
                                {"text": "Method", "value": "method"},
                                {"text": "Url", "value": "url"}
                            ],
                            "items": [],
                            "loading": false,
                            "page": 1,
                            "itemsPerPage": 10
                        },
                        "pagination": {
                            "length": 10,
                            "totalVisible": 10
                        }
                    },
                    "user": {
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
                        }
                    },
                    "person": {
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
                        }
                    },
                    "role": {
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
                        }
                    },
                    "menu": {
                        "dataTable": {
                            "headers": [
                                {"text": "ID", "sortable": false, "value": "id"},
                                {"text": "Parent ID", "sortable": false, "value": "parentId"},
                                {"text": "Name", "sortable": false, "value": "name"},
                                {"text": "Description", "sortable": false, "value": "description"},
                                {"text": "URL", "sortable": false, "value": "url"},
                                {"text": "Icon", "sortable": false, "value": "icon"},
                                {"text": "Ranking", "sortable": false, "value": "ranking"}
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
                        }
                    },
                    "api": {
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
                        }
                    }
                };
            },
            "watch": {
                "user.dataTable.page": {
                    "handler": function (newValue, oldValue) {
                        this.setUserList();
                    },
                    "deep": true
                },
                "person.dataTable.page": {
                    "handler": function (newValue, oldValue) {
                        this.setPersonList();
                    },
                    "deep": true
                },
                "role.dataTable.page": {
                    "handler": function (newValue, oldValue) {
                        this.setRoleList();
                    },
                    "deep": true
                },
                "menu.dataTable.page": {
                    "handler": function (newValue, oldValue) {
                        this.setMenuList();
                    },
                    "deep": true
                },
                "api.dataTable.page": {
                    "handler": function (newValue, oldValue) {
                        this.setApiList();
                    },
                    "deep": true
                }
            },
            "methods": {
                "customAlert": function () {
                    util.alert({
                        "title": "Alert Title",
                        "text": "alert text"
                    }).then(function () {
                        console.log("callback!");
                    });
                },
                "customConfirm": function () {
                    util.confirm({
                        "title": "Confirm Title",
                        "text": "confirm text"
                    }).then(function (isConfirmed) {
                        if (isConfirmed) {
                            console.log("callback yes!");
                        } else {
                            console.log("callback no!");
                        }
                    });
                },
                "selectContent": function () {
                    return new Promise(function (resolve, reject) {
                        gtag("event", "select_content", {
                            "event_label": "내용 선택",
                            "items": [
                                {
                                    "id": "ITEM_1",
                                    "name": "Item 1"
                                }
                            ],
                            "event_callback": function () {
                                util.alert({
                                    "text": "select_content end"
                                })
                                .then(resolve);
                            }
                        });
                    });
                },
                "viewItemList": function () {
                    return new Promise(function (resolve, reject) {
                        gtag("event", "view_item_list", {
                            "event_label": "아이템 목록 조회",
                            "items": [
                                {
                                    "id": "ITEM_1",
                                    "name": "Item 1"
                                }
                            ],
                            "event_callback": function () {
                                util.alert({
                                    "text": "view_item_list end"
                                })
                                .then(resolve);
                            }
                        });
                    });
                },
                "viewItem": function () {
                    return new Promise(function (resolve, reject) {
                        gtag("event", "view_item", {
                            "event_label": "아이템 조회",
                            "items": [
                                {
                                    "id": "ITEM_1",
                                    "name": "Item 1"
                                }
                            ],
                            "event_callback": function () {
                                util.alert({
                                    "text": "view_item end"
                                })
                                .then(resolve);
                            }
                        });
                    });
                },
                "viewPromotion": function () {
                    return new Promise(function (resolve, reject) {
                        gtag("event", "view_promotion", {
                            "event_label": "프로모션 조회",
                            "promotions": [
                                {
                                    "id": "ITEM_1",
                                    "name": "Item 1"
                                }
                            ],
                            "event_callback": function () {
                                util.alert({
                                    "text": "view_promotion end"
                                })
                                .then(resolve);
                            }
                        });
                    });
                },
                "addToCart": function () {
                    return new Promise(function (resolve, reject) {
                        gtag("event", "add_to_cart", {
                            "event_label": "장바구니에 추가",
                            "event_callback": function () {
                                util.alert({
                                    "text": "add_to_cart end"
                                })
                                .then(resolve);
                            }
                        });
                    });
                },
                "removeFromCart": function () {
                    return new Promise(function (resolve, reject) {
                        gtag("event", "remove_from_cart", {
                            "event_label": "장바구니에서 제거",
                            "event_callback": function () {
                                util.alert({
                                    "text": "remove_from_cart end"
                                })
                                .then(resolve);
                            }
                        });
                    });
                },
                "beginCheckout": function () {
                    return new Promise(function (resolve, reject) {
                        gtag("event", "begin_checkout", {
                            "event_label": "결제",
                            "items": [
                                {
                                    "id": "ITEM_1",
                                    "name": "Item 1"
                                }
                            ],
                            "event_callback": function () {
                                util.alert({
                                    "text": "begin_checkout end"
                                })
                                .then(resolve);
                            }
                        });
                    });
                },
                "purchase": function () {
                    return new Promise(function (resolve, reject) {
                        gtag("event", "purchase", {
                            "event_label": "구매",
                            "transaction_id": "TRANSACTION_1",
                            "event_callback": function () {
                                util.alert({
                                    "text": "purchase end"
                                })
                                .then(resolve);
                            }
                        });
                    });
                },
                "refund": function () {
                    return new Promise(function (resolve, reject) {
                        gtag("event", "refund", {
                            "event_label": "환불",
                            "transaction_id": "TRANSACTION_1",
                            "event_callback": function () {
                                util.alert({
                                    "text": "refund end"
                                })
                                .then(resolve);
                            }
                        });
                    });
                },
                "getRequestMappingList": function () {
                    return axios({
                        "url": "/public/request-mappings",
                        "method": "get"
                    });
                },
                "setRequestMappingList": function () {
                    var self = this;
                    return new Promise(function (resolve, reject) {
                        Promise.resolve()
                            .then(function () {
                                return self.getRequestMappingList();
                            })
                            .then(function (response) {
                                var data = response.data,
                                    methodsCondition = data.methodsCondition,
                                    patternsCondition = data.patternsCondition,
                                    items = [],
                                    requestMapping,
                                    methods,
                                    patterns,
                                    i = 0,
                                    j = 0,
                                    k = 0;
                                for (i = 0; i < data.length; i++) {
                                    requestMapping = data[i];
                                    methodsCondition = requestMapping.methodsCondition;
                                    patternsCondition = requestMapping.patternsCondition;
                                    methods = methodsCondition.methods;
                                    patterns = patternsCondition.patterns;
                                    for (j = 0; j < methods.length; j++) {
                                        for (k = 0; k < patterns.length; k++) {
                                            items.push({
                                                "method": methods[j],
                                                "url": patterns[k]
                                            });
                                        }
                                    }
                                }
                                self.requestMapping.dataTable.items = items;
                            })
                            .then(function () { resolve(); });
                    });
                },
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
                },
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
                                var params = {
                                        "page": self.person.dataTable.page,
                                        "size": self.person.dataTable.itemsPerPage
                                    };
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
                                var params = {
                                        "page": self.role.dataTable.page,
                                        "size": self.role.dataTable.itemsPerPage
                                    };
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
                "getMenuList": function (params) {
                    return axios({
                        "url": "/api/common/menus",
                        "method": "get",
                        "params": params
                    });
                },
                "setMenuList": function () {
                    var self = this;
                    return new Promise(function (resolve, reject) {
                        Promise.resolve()
                            .then(function () {
                                var params = {
                                        "page": self.menu.dataTable.page,
                                        "size": self.menu.dataTable.itemsPerPage
                                    };
                                self.menu.dataTable.loading = true;
                                return self.getMenuList(params);
                            })
                            .then(function (response) {
                                var data = response.data;
                                self.menu.dataTable.items = data.content;
                                self.menu.dataTable.serverItemsLength = data.totalElements;
                                self.menu.dataTable.loading = false;
                            })
                            .then(function () { resolve(); });
                    });
                },
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
                Promise
                    .resolve(
                        this.setRequestMappingList(),
                        this.setUserList(),
                        this.setPersonList(),
                        this.setRoleList(),
                        this.setMenuList(),
                        this.setApiList());
            }
        });
    });
});