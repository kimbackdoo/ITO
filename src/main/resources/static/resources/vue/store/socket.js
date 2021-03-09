var SocketStore;
SocketStore = {
    "namespaced": true,
    "state": {
        "socket": {},
        "stompClient": {}
    },
    "mutations": {
        /* 소켓 설정 */
        "SET_SOCKET": function (state, payload) {
            state.socket = payload;
        },
        /* 스톰프 클라이언트 설정 */
        "SET_STOMP_CLIENT": function (state, payload) {
            state.stompClient = payload;
        }
    },
    "actions": {
        /* 소켓 초기화 */
        "initSocket": function (context) {
            var stompClient,
                user;
            context.commit("SET_SOCKET", new SockJS("/ws"));
            context.commit("SET_STOMP_CLIENT", Stomp.over(context.state.socket));
            user = context.rootState.user.user;
            stompClient = context.state.stompClient;
            stompClient.debug = false;
            stompClient.connect({}, function (frame) {
                // 로그아웃
                stompClient.subscribe("/user/" + user.id + "/logout", function (response) {
                    util.alert({
                        "title": "Session timeout",
                        "text": "Session has expired!"
                    }).then(function () {
                        router.replace("/sign-in");
                    });
                });
            });
        }
    }
};