var ApiStore;
ApiStore = {
    "namespaced": true,
    "state": {
    },
    "mutations": {
    },
    "actions": {
        /* 로그인 */
        "login": function (context, payload) {
            return axios({
                "url": "/api/login",
                "method": "post",
                "data": payload
            });
        },
        /* 로그아웃 */
        "logout": function (context) {
            return axios({
                "url": "/api/logout",
                "method": "post"
            });
        },
        /* API (토큰 검증용 임시 API) */
        "api": function (context, payload) {
            return axios({
                "url": "/api",
                "methods": "get",
                "headers": {
                    "Authorization": payload
                }
            });
        },
        /* 메뉴 목록 조회 */
        "getMenuList": function (context, payload) {
            return axios({
                "url": "/api/app/menus",
                "method": "get",
                "params": payload
            });
        },
        /* 사용자 조회 */
        "getUserByToken": function (context, payload) {
            return axios({
                "url": "/api/app/users/by-token",
                "method": "get",
                "params": payload
            });
        }
    }
};