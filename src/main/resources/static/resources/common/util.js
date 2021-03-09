var util;
util = {
    "alert": function (option) {
        return store.dispatch("app/alert", option);
    },
    "confirm": function (option) {
        return store.dispatch("app/confirm", option);
    }
};