var LayoutSignInComponent = Vue.component('layout-sign-in-component', function (resolve, reject) {
    axios.get("/resources/vue/component/common/layout/sign-in.html").then(function (response) {
        resolve({
            "template": response.data,
            "data": function () {
                return {
                    "username": "",
                    "password": ""
                };
            },
            "methods": {
                "login": function () {
                    var self = this;
                    return new Promise(function (resolve, reject) {
                        Promise.resolve()
                            .then(function () {
                                var user = {
                                        "username": self.username,
                                        "password": self.password
                                    };
                                return self.$store.dispatch("app/authenticate", user);
                            })
                            .then(function (response) {
                                var data = response.data;
                                if (data && !data.error) {
                                    self.$router.replace("/");
                                } else {
                                    return util.alert({"title": "Login Failed", "text": data.error});
                                }
                            })
                            .then(function () { resolve(); })
                            .catch(function (error) { reject(error); });
                    });
                }
            }
        });
    });
});