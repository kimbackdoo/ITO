var router;
router = new VueRouter({
    "mode": "history",
    "routes": [
        {
            "name": "Application",
            "path": "/",
            "redirect": "/dashboard",
            "component": LayoutMainComponent,
            "children": [
                {"name": "Dashboard", "path": "dashboard", "component": DashboardMainComponent},
                {"name": "Users", "path": "users", "component": UserMainComponent},
                {"name": "People", "path": "people", "component": PersonMainComponent},
                {"name": "People Form", "path": "people/new", "component": PersonFormComponent},
                {"name": "People Form", "path": "people/:id", "component": PersonFormComponent},
                {"name": "Roles", "path": "roles", "component": RoleMainComponent},
                {"name": "Roles Form", "path": "roles/new", "component": RoleFormComponent},
                {"name": "Roles Form", "path": "roles/:id", "component": RoleFormComponent},
                {"name": "Menus", "path": "menus", "component": MenuMainComponent},
                {"name": "APIs", "path": "apis", "component": ApiMainComponent},
                {"name": "Profile", "path": "profile", "component": ProfileMainComponent},
                {"name": "InputProfile", "path": "input-profile", "component": InputProfileMainComponent},
            ],
            "beforeEnter": function (to, from, next) {
                if (!store.state.app.authenticated) {
                    next("/sign-in");
                }
                next();
            }
        },
        {
            "name": "Sign In",
            "path": "/sign-in",
            "component": LayoutSignInComponent,
            "beforeEnter": function (to, from, next) {
                if (store.state.app.authenticated) {
                    next("/");
                }
                next();
            }
        },
        {
            "name": "Sign Up",
            "path": "/sign-up",
            "component": LayoutSignUpComponent
        },
        {
            "name": "404 Not found",
            "path": "/404",
            "component": LayoutStatus404Component
        }
    ]
});
router.beforeEach(function (to, from, next) {
    Promise.resolve()
        .then(function () {
            if (!to.name) {
                next("/404");
            }
        })
        .then(function () {
            document.title = to.name;
        })
        .then(function () {
            return store.dispatch("app/authorize");
        })
        .then(function () {
            return store.state.app.authenticated && !store.state.menu.menuList.length
                    ? store.dispatch("menu/initMenuList") : null;
        })
        .then(function () {
            return store.state.app.authenticated
                    ? store.dispatch("user/initUser") : null;
        })
        .then(function () {
            store.commit("menu/SET_CURRENT_MENU_LIST", to.path);
            store.commit("menu/SET_CURRENT_MENU", to.path);
        })
        .then(function () {
            next();
        });
});
router.afterEach(function (to, from) {
    gtag("config", window.GA_TRACKING_ID, {
        "page_title": to.name,
        "page_path": to.fullPath,
        "send_page_view": true
    });
});