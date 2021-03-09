var RoleFormComponent = Vue.component('role-form-component', function (resolve, reject) {
    axios.get("/resources/vue/component/app/role/form.html").then(function (response) {
        resolve({
            "template": response.data,
            "data": function () {
                return {
                    "role": {
                        "id": null,
                        "name": "",
                        "description": ""
                    }
                };
            },
            "methods": {
                "createRole": function (data) {
                    return axios({
                        "url": "/api/common/roles",
                        "method": "post",
                        "data": data
                    });
                },
                "modifyRole": function (id, data) {
                    return axios({
                        "url": "/api/common/roles/" + id,
                        "method": "put",
                        "data": data
                    });
                },
                "removeRole": function (id) {
                    return axios({
                        "url": "/api/common/roles/" + id,
                        "method": "delete"
                    });
                },
                "getRole": function (id) {
                    return axios({
                        "url": "/api/common/roles/" + id,
                        "method": "get"
                    });
                },
                "setRole": function () {
                    var self = this;
                    return new Promise(function (resolve, reject) {
                        Promise.resolve()
                            .then(function () {
                                var id = self.$route.params.id;
                                return id ? self.getRole(id) : null;
                            })
                            .then(function (response) {
                                if (response && response.data) {
                                    self.role = response.data;
                                }
                            })
                            .then(function () { resolve(); });
                    });
                },
                "saveRole": function () {
                    var self = this;
                    return new Promise(function (resolve, reject) {
                        Promise.resolve()
                            .then(function () {
                                return util.confirm({"text": "Are you sure want to save role?"});
                            })
                            .then(function (isConfirmed) {
                                if (isConfirmed) {
                                    return new Promise(function (resolve, reject) {
                                        Promise.resolve()
                                            .then(function () {
                                                var data = self.role;
                                                if (!data.id) {
                                                    return self.createRole(data);
                                                } else {
                                                    return self.modifyRole(data.id, data);
                                                }
                                            })
                                            .then(function () {
                                                return util.alert({"text": "Saved successfully!"});
                                            })
                                            .then(function () {
                                                self.$router.back();
                                            })
                                            .then(function () { resolve(); });
                                    });
                                }
                            })
                            .then(function () { resolve(); });
                    });
                },
                "deleteRole": function () {
                    var self = this;
                    return new Promise(function (resolve, reject) {
                        Promise.resolve()
                            .then(function () {
                                return util.confirm({"text": "Are you sure want to delete role?"});
                            })
                            .then(function (isConfirmed) {
                                if (isConfirmed) {
                                    return new Promise(function (resolve, reject) {
                                        Promise.resolve()
                                            .then(function () {
                                                return self.removeRole(self.role.id);
                                            })
                                            .then(function () {
                                                return util.alert({"text": "Deleted successfully!"});
                                            })
                                            .then(function () {
                                                self.$router.replace("/roles");
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
                var self = this;
                Promise.resolve()
                    .then(self.setRole);
            }
        });
    });
});