var UserStore;
UserStore = {
    "namespaced": true,
    "state": {
        "user": {}
    },
    "mutations": {
        "SET_USER": function (state, payload) {
            state.user = payload;
        }
    },
    "actions": {
        /* 사용자 초기화 */
        "initUser": function (context) {
            return new Promise(function (resolve, reject) {
                Promise.resolve()
                    .then(function () {
                        var token = context.rootState.app.jwtToken;
                        return context.dispatch("api/getUserByToken", {"token": token}, {"root": true});
                    })
                    .then(function (response) {
                        var data = response.data;
                        context.commit("SET_USER", data);
                    })
                    .then(function () { resolve(); })
                    .catch(function (error) { reject(error); });
            });
        }
    }
};