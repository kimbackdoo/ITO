var store;
store = new Vuex.Store({
    "modules": {
        "app": AppStore,
        "api": ApiStore,
        "user": UserStore,
        "menu": MenuStore,
        "socket": SocketStore
    }
});