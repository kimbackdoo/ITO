var app;
app = new Vue({
    "el": '#app',
    "router": router,
    "store": store,
    "vuetify": vuetify,
    "methods": {
        "setAxiosConfig": function () {
            var self = this;
            axios.interceptors.request.use(function (config) {
                self.$store.dispatch("app/showOverlay");
                return config;
            }, function (error) {
                self.$store.dispatch("app/showOverlay");
                return Promise.reject(error);
            });
            axios.interceptors.response.use(function (response) {
                self.$store.dispatch("app/hideOverlay");
                return response;
            }, function (error) {
                return new Promise(function (resolve, reject) {
                    Promise.resolve()
                        .then(function () { return self.$store.dispatch("app/hideOverlay"); })
                        .then(function () {
                            var data = error.response.data,
                                title,
                                text;
                            title = data.error + ' (' + data.status + ')';
                            text = '';
                            text += '<div class="text-left">';
                            text += '    <p>Status Code : ' + data.status + '</p>';
                            text += '    <p>Message : ' + data.message + '</p>';
                            text += '</div>';
                            return util.alert({
                                "title": title,
                                "text": text
                            }).then(function () {
                                switch (data.status) {
                                    case 403:
                                        self.$router.replace("/sign-in");
                                        break;
                                    default:
                                        break;
                                }
                            });
                        })
                        .then(function () { reject(error); });
                });
            });
        }
    },
    "mounted": function () {
        var self = this;
        Promise.resolve()
            .then(self.setAxiosConfig);
    }
});