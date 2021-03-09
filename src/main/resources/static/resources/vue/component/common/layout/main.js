var LayoutMainComponent = Vue.component('layout-main-component', function (resolve, reject) {
    axios.get("/resources/vue/component/common/layout/main.html").then(function (response) {
        resolve({
            "template": response.data,
            "data": function () {
                return {
                    "drawer": null
                };
            },
            "methods": {
                "logout": function () {
                    var self = this;
                    return new Promise(function (resolve, reject) {
                        Promise.resolve()
                            .then(function () {
                                return self.$store.dispatch("app/signout");
                            })
                            .then(function () {
                                self.$router.replace("/sign-in");
                            })
                            .then(function () { resolve(); })
                            .catch(function (error) { reject(error); });
                    });
                }
            },
            "mounted": function () {
                this.$store.dispatch("socket/initSocket");
            }
        });
    });
});