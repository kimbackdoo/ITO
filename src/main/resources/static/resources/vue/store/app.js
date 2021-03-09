var AppStore;
AppStore = {
    "namespaced": true,
    "state": {
        "overlay": false,
        "alert": {
            "visible": false,
            "title": "Alert",
            "text": "",
            "callback": function () {}
        },
        "confirm": {
            "visible": false,
            "title": "Confirm",
            "text": "",
            "callback": function () {}
        },
        "jwtToken": null,
        "authenticated": false
    },
    "mutations": {
        "SET_OVERLAY": function (state, payload) {
            state.overlay = payload;
        },
        "SET_ALERT": function (state, payload) {
            state.alert = payload;
        },
        "SET_CONFIRM": function (state, payload) {
            state.confirm = payload;
        },
        "SET_JWT_TOKEN": function (state, payload) {
            state.jwtToken = payload;
        },
        "SET_AUTHENTICATED": function (state, payload) {
            state.authenticated = payload;
        }
    },
    "actions": {
        /* 오버레이 보여주기 */
        "showOverlay": function (context) {
            context.commit("SET_OVERLAY", true);
        },
        /* 오버레이 숨기기 */
        "hideOverlay": function (context) {
            return new Promise(function (resolve, reject) {
                setTimeout(function () {
                    context.commit("SET_OVERLAY", false);
                    resolve();
                }, 200);
            });
        },
        /* 알림 */
        "alert": function (context, payload) {
            return new Promise(function (resolve, reject) {
                context.commit("SET_ALERT", {
                    "visible": true,
                    "title": payload && payload.title ? payload.title : "Alert",
                    "text": payload && payload.text ? payload.text : "",
                    "callback": function () { resolve(); }
                });
            });
        },
        /* 확인 */
        "confirm": function (context, payload) {
            return new Promise(function (resolve, reject) {
                context.commit("SET_CONFIRM", {
                    "visible": true,
                    "title": payload && payload.title ? payload.title : "Confirm",
                    "text": payload && payload.text ? payload.text : "",
                    "callback": payload.callback ? payload.callback : function (isConfirmed) { resolve(isConfirmed); }
                });
            });
        },
        /* 토큰 유효화 */
        "validateToken": function (context, payload) {
            context.commit("SET_JWT_TOKEN", payload);
            context.commit("SET_AUTHENTICATED", true);
            Vue.$cookies.set("JWT_TOKEN", payload);
            axios.defaults.headers.common["Authorization"] = "Bearer " + payload;
        },
        /* 토큰 무효화 */
        "invalidateToken": function (context) {
            context.commit("SET_JWT_TOKEN", null);
            context.commit("SET_AUTHENTICATED", false);
            Vue.$cookies.remove("JWT_TOKEN");
            axios.defaults.headers.common["Authorization"] = null;
        },
        /* 토큰 검증 */
        "verifyToken": function (context, payload) {
            return new Promise(function (resolve, reject) {
                Promise.resolve()
                    .then(function () {
                        var jwtToken = payload,
                            authorization = jwtToken ? "Bearer " + jwtToken : null;
                        return authorization ? context.dispatch("api/api", authorization, {"root": true}) : null;
                    })
                    .then(function (response) { resolve(response); })
                    .catch(function (error) { reject(error); });
            });
        },
        /* 인증 */
        "authenticate": function (context, payload) {
            return new Promise(function (resolve, reject) {
                Promise.resolve()
                    .then(function () {
                        var user = {
                                "username": payload.username,
                                "password": payload.password
                            };
                        return context.dispatch("api/login", user, {"root": true});
                    })
                    .then(function (response) {
                        return new Promise(function (resolve, reject) {
                            var data = response.data,
                                headers = response.headers,
                                authorization = headers ? headers.authorization : null,
                                jwtToken = authorization ? authorization.replace("Bearer ", "") : null;
                            Promise.resolve()
                                .then(function () {
                                    return jwtToken ? context.dispatch("authorize", jwtToken) : null;
                                })
                                .then(function () { resolve(response); });
                        });
                    })
                    .then(function (response) { resolve(response); })
                    .catch(function (error) { reject(error); });
            });
        },
        /* 인가 */
        "authorize": function (context, payload) {
            return new Promise(function (resolve, reject) {
                var jwtToken = payload ? payload : Vue.$cookies.get("JWT_TOKEN");
                Promise.resolve()
                    .then(function () {
                        return context.dispatch("verifyToken", jwtToken);
                    })
                    .then(function (response) {
                        return new Promise(function (resolve, reject) {
                            Promise.resolve()
                                .then(function () {
                                    return (response && response.data && !response.data.error)
                                            ? context.dispatch("validateToken", jwtToken)
                                            : context.dispatch("invalidateToken", jwtToken);
                                })
                                .then(function () { resolve(response); })
                                .catch(function (error) { reject(error); });
                        });
                    })
                    .then(function (response) { resolve(response); })
                    .catch(function (error) { reject(error); });
            });
        },
        /* 사인아웃 */
        "signout": function (context) {
            return new Promise(function (resolve, reject) {
                Promise.resolve()
                    .then(function () {
                        return context.dispatch("api/logout", null, {"root": true});
                    })
                    .then(function (response) {
                        return new Promise(function (resolve, reject) {
                            Promise.resolve()
                                .then(function () {
                                    return response && response.data && !response.data.error
                                            ? context.dispatch("invalidateToken") : null;
                                })
                                .then(function () { resolve(response); })
                                .catch(function (error) { reject(error) });
                        });
                    })
                    .then(function (response) { resolve(response); })
                    .catch(function (error) { reject(error) });
            });
        }
    }
};