Vue.config.devtools = true;

var app;
app = new Vue({
    "el": '#app',
    "router": router,
    "store": store,
    "vuetify": vuetify
});

var util;
util = {
    "alert": function (option) {
        return store.dispatch("app/alert", option);
    },
    "confirm": function (option) {
        return store.dispatch("app/confirm", option);
    }
};

