var MenuMainComponent = Vue.component('menu-main-component', function (resolve, reject) {
    axios.get("/resources/vue/component/app/menu/main.html").then(function (response) {
        resolve({
            "template": response.data,
            "data": function () {
                return {
                    "menu": {
                        "panels": {
                            "list": [0],
                            "form": [0]
                        },
                        "list": {
                            "items": [],
                            "active": undefined
                        },
                        "form": {
                            "item": {
                                "id": null,
                                "parentId": null,
                                "name": null,
                                "description": null,
                                "url": null,
                                "icon": null,
                                "ranking": null
                            },
                            "autocomplete": {
                                "parentId": {
                                    "items": []
                                }
                            }
                        }
                    }
                };
            },
            "computed": {
                "autocompleteMenuList": function () {
                    var autocompleteMenuList = [],
                        items = this.menu.list.items,
                        i;
                    autocompleteMenuList.push({"text": "APP", "value": "APP"});
                    for (i = 0; i < items.length; i++) {
                        autocompleteMenuList.push({
                            "text": items[i].id,
                            "value": items[i].id
                        });
                    }
                    return autocompleteMenuList;
                }
            },
            "watch": {
                "menu.list.active": function (value) {
                    if (value) {
                        this.setMenu(value.id);
                    } else {
                        this.resetMenu();
                    }
                }
            },
            "methods": {
                "getMenuList": function (params) {
                    return axios({
                        "url": "/api/app/menus",
                        "method": "get",
                        "params": params
                    });
                },
                "setMenuList": function () {
                    var self = this;
                    return new Promise(function (resolve, reject) {
                        Promise.resolve()
                            .then(function () {
                                return self.getMenuList({"upperParentId": "APP"});
                            })
                            .then(function (response) {
                                var data = response.data;
                                self.menu.list.items = data;
                            })
                            .then(function () { resolve(); });
                    });
                },
                "createMenu": function (data) {
                    return axios({
                        "url": "/api/common/menus",
                        "method": "post",
                        "data": data
                    });
                },
                "modifyMenu": function (id, data) {
                    return axios({
                        "url": "/api/common/menus/" + btoa(id),
                        "method": "put",
                        "data": data
                    });
                },
                "removeMenu": function (id) {
                    return axios({
                        "url": "/api/common/menus/" + btoa(id),
                        "method": "delete"
                    });
                },
                "getMenu": function (id) {
                    return axios({
                        "url": "/api/common/menus/" + btoa(id),
                        "method": "get"
                    });
                },
                "setMenu": function (id) {
                    var self = this;
                    return new Promise(function (resolve, reject) {
                        Promise.resolve()
                            .then(function () {
                                return self.getMenu(id);
                            })
                            .then(function (response) {
                                var data = response.data;
                                self.menu.form.item.id = data.id;
                                self.menu.form.item.parentId = data.parentId;
                                self.menu.form.item.name = data.name;
                                self.menu.form.item.description = data.description;
                                self.menu.form.item.url = data.url;
                                self.menu.form.item.icon = data.icon;
                                self.menu.form.item.ranking = data.ranking;
                            })
                            .then(function () { resolve(); });
                    });
                },
                "resetMenu": function () {
                    this.menu.list.active = undefined;
                    this.menu.form.item.id = null;
                    this.menu.form.item.parentId = null;
                    this.menu.form.item.name = null;
                    this.menu.form.item.description = null;
                    this.menu.form.item.url = null;
                    this.menu.form.item.icon = null;
                    this.menu.form.item.ranking = null;
                },
                "saveMenu": function () {
                    var self = this;
                    return new Promise(function (resolve, reject) {
                        Promise.resolve()
                            .then(function () {
                                return util.confirm({"text": "Are you sure want to save menu?"});
                            })
                            .then(function (isConfirmed) {
                                if (isConfirmed) {
                                    return new Promise(function (resolve, reject) {
                                        Promise.resolve()
                                            .then(function () {
                                                var data = self.menu.form.item,
                                                    active = self.menu.list.active;
                                                if (!active) {
                                                    return self.createMenu(data);
                                                } else {
                                                    return self.modifyMenu(data.id, data);
                                                }
                                            })
                                            .then(function () {
                                                return util.alert({"text": "Saved successfully!"});
                                            })
                                            .then(function () {
                                                location.reload();
                                            })
                                            .then(function () { resolve(); });
                                    });
                                }
                            })
                            .then(function () { resolve(); });
                    });
                },
                "deleteMenu": function () {
                    var self = this;
                    return new Promise(function (resolve, reject) {
                        Promise.resolve()
                            .then(function () {
                                return util.confirm({"text": "Are you sure want to delete menu?"});
                            })
                            .then(function (isConfirmed) {
                                if (isConfirmed) {
                                    return new Promise(function (resolve, reject) {
                                        Promise.resolve()
                                            .then(function () {
                                                return self.removeMenu(self.menu.form.item.id);
                                            })
                                            .then(function () {
                                                return util.alert({"text": "Deleted successfully!"});
                                            })
                                            .then(function () {
                                                location.reload();
                                            })
                                            .then(function () { resolve(); });
                                    });
                                }
                            })
                            .then(function () { resolve(); });
                    });
                }
            },
            "mounted": function () {
                Promise.resolve()
                    .then(this.setMenuList);
            }
        });
    });
});