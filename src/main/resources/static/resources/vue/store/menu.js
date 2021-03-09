var MenuStore;
MenuStore = {
    "namespaced": true,
    "state": {
        "menuList": [],
        "currentMenuList": [],
        "currentMenu": {}
    },
    "mutations": {
        "SET_MENU_LIST": function (state, payload) {
            state.menuList = payload;
        },
        "SET_CURRENT_MENU_LIST": function (state, payload) {
            var url = payload,
                urlPaths = url.split("/"),
                urlDepth = urlPaths.length,
                urlShortPath = urlPaths[0] + "/" + urlPaths[1],
                menuList = state.menuList,
                currentMenuList = [],
                i = 0;
            for (i = 0; i < menuList.length; i++) {
                if (menuList[i].url == "/"
                    || (menuList[i].url.startsWith(urlShortPath)
                            && menuList[i].depth <= urlDepth)) {
                    currentMenuList.push({
                        "text": menuList[i].name,
                        "to": menuList[i].url,
                        "disabled": menuList[i].depth >= urlDepth,
                        "exact": true
                    });
                }
            }
            state.currentMenuList = currentMenuList;
        },
        "SET_CURRENT_MENU": function (state, payload) {
            var url = payload,
                urlPaths = url.split("/"),
                urlDepth = urlPaths.length,
                urlShortPath = urlPaths[0] + "/" + urlPaths[1],
                menuList = state.menuList,
                currentMenu = {},
                i = 0;
            for (i = 0; i < menuList.length; i++) {
                if (menuList[i].url.startsWith(urlShortPath)
                        && menuList[i].depth == urlDepth) {
                    currentMenu = menuList[i];
                    break;
                }
            }
            state.currentMenu = currentMenu;
        }
    },
    "actions": {
        /* 메뉴 목록 초기화 */
        "initMenuList": function (context) {
            return new Promise(function (resolve, reject) {
                Promise.resolve()
                    .then(function () {
                        return context.dispatch("api/getMenuList", {"upperId": "APP"}, {"root": true});
                    })
                    .then(function (response) {
                        var data = response.data;
                        context.commit("SET_MENU_LIST", data);
                    })
                    .then(function () { resolve(); })
                    .catch(function (error) { reject(error); });
            });
        }
    }
};