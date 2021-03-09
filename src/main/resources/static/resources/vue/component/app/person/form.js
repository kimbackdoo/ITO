var PersonFormComponent = Vue.component('person-form-component', function (resolve, reject) {
    axios.get("/resources/vue/component/app/person/form.html").then(function (response) {
        resolve({
            "template": response.data,
            "data": function () {
                return {
                    "person": {
                        "id": null,
                        "firstName": "",
                        "middleName": "",
                        "lastName": "",
                        "birthDate": ""
                    }
                };
            },
            "methods": {
                "createPerson": function (data) {
                    return axios({
                        "url": "/api/common/people",
                        "method": "post",
                        "data": data
                    });
                },
                "modifyPerson": function (id, data) {
                    return axios({
                        "url": "/api/common/people/" + id,
                        "method": "put",
                        "data": data
                    });
                },
                "removePerson": function (id) {
                    return axios({
                        "url": "/api/common/people/" + id,
                        "method": "delete"
                    });
                },
                "getPerson": function (id) {
                    return axios({
                        "url": "/api/common/people/" + id,
                        "method": "get"
                    });
                },
                "setPerson": function () {
                    var self = this;
                    return new Promise(function (resolve, reject) {
                        Promise.resolve()
                            .then(function () {
                                var id = self.$route.params.id;
                                return id ? self.getPerson(id) : null;
                            })
                            .then(function (response) {
                                if (response && response.data) {
                                    self.person = response.data;
                                }
                            })
                            .then(function () { resolve(); });
                    });
                },
                "savePerson": function () {
                    var self = this;
                    return new Promise(function (resolve, reject) {
                        Promise.resolve()
                            .then(function () {
                                return util.confirm({"text": "Are you sure want to save person?"});
                            })
                            .then(function (isConfirmed) {
                                if (isConfirmed) {
                                    return new Promise(function (resolve, reject) {
                                        Promise.resolve()
                                            .then(function () {
                                                var data = self.person;
                                                if (!data.id) {
                                                    return self.createPerson(data);
                                                } else {
                                                    return self.modifyPerson(data.id, data);
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
                "deletePerson": function () {
                    var self = this;
                    return new Promise(function (resolve, reject) {
                        Promise.resolve()
                            .then(function () {
                                return util.confirm({"text": "Are you sure want to delete person?"});
                            })
                            .then(function (isConfirmed) {
                                if (isConfirmed) {
                                    return new Promise(function (resolve, reject) {
                                        Promise.resolve()
                                            .then(function () {
                                                return self.removePerson(self.person.id);
                                            })
                                            .then(function () {
                                                return util.alert({"text": "Deleted successfully!"});
                                            })
                                            .then(function () {
                                                self.$router.replace("/people");
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
                    .then(self.setPerson);
            }
        });
    });
});