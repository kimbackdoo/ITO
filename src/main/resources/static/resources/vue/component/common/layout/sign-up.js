var LayoutSignUpComponent = Vue.component('layout-sign-up-component', function (resolve, reject) {
    axios.get("/resources/vue/component/common/layout/sign-up.html").then(function (response) {
        resolve({
            "template": response.data,
            "data": function () {
                return {
                    "username": "",
                    "password": "",
                    "firstName": "",
                    "middleName": "",
                    "lastName": "",
                    "birthDate": ""
                };
            },
            "methods": {
                "apiSignUp": function (data) {
                    return axios({
                        "url": "/api/app/users/sign-up",
                        "method": "post",
                        "data": {
                            "user": {
                                "id": data.username,
                                "password": data.password
                            },
                            "person": {
                                "firstName": data.firstName,
                                "middleName": data.middleName,
                                "lastName": data.lastName,
                                "birthDate": data.birthDate
                            }
                        }
                    });
                },
                "signUp": function (data) {
                    var self = this;
                    return new Promise(function (resolve, reject) {
                        Promise.resolve()
                            .then(function () {
                                var data = {
                                        "username": self.username,
                                        "password": self.password,
                                        "firstName": self.firstName,
                                        "middleName": self.middleName,
                                        "lastName": self.lastName,
                                        "birthDate": self.birthDate
                                    };
                                return self.apiSignUp(data);
                            })
                            .then(function (response) {
                                return util.alert({"text": "Sign Up Success"}).then(function () {
                                    self.$router.replace("/sign-in");
                                });
                            })
                            .then(function () { resolve(); });
                    });
                }
            }
        });
    });
});