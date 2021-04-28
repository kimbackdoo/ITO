var router;
router = new VueRouter({
    "mode": "history",
    "routes": [
        {
            "name": "Application",
            "path": "/",
            "redirect": "/main",
            "component": MainLayout,
            "children": [
                {
                    "name": "Main",
                    "path": "/main",
                },

                // 사용자 메뉴
                {
                    "name": "user",
                    "path": "/main/user",
                    "redirect": "/main/user/input-profile"
                },
                {
                    "name": "InputProfile",
                    "path": "/main/user/input-profile",
                    "component": InputProfileMainComponent
                },
                {
                    "name": "InputProfileCareer",
                    "path": "/main/user/career",
                    "component": CareerMainComponent
                },
                {
                    "name": "ApplyProject",
                    "path": "/main/user/apply-project",
                    "component": ApplyProjectMainComponent
                },
                {
                    "name": "ApplyProjectDetail",
                    "path": "/main/user/apply-project/detail",
                    "component": ApplyProjectDetailMainComponent
                },

                //관리자 메뉴

                {
                    "name": "adminMenu",
                    "path": "/main/admin",
                    "redirect": "/main/admin/user-info"
                },
                {
                    "name": "사용자현황",
                    "path": "/main/admin/user-info",
                    "component": MainAdminPage
                },
                {
                    "name": "사용자정보 입력하기",
                    "path": "/main/admin/user-info-form",
                    "component": MainAdminFormPage
                },
                {
                    "name": "프로젝트 목록",
                    "path": "/main/admin/project-lists",
                    "component": MainAdminProjectListPage
                },
                {
                    "name": "AdminProjectDetail",
                    "path": "/main/admin/project-list/detail",
                    "component": MainAdminProjectDetailPage
                },
                {
                      "name": "프로젝트 입력하기",
                      "path": "/main/admin/project-info-form",
                      "component": MainAdminProjectFormPage
                },
                {
                    "name": "가용인력",
                    "path": "/main/admin/available-list",
                    "component": MainAdminAvailableListPage
                },
                {
                    "name": "Homepages",
                    "path": "/homepages",
                    "redirect": "/homepages/contacts",
                },
                {
                    "name": "Contacts",
                    "path": "/homepages/contacts",
                    "component": HomepageContactMainPage
                },
                {
                    "name": "ContactDetails",
                    "path": "/homepages/contacts/details",
                    "component": HomepageContactDetailPage
                },
                {
                    "name": "Notices",
                    "path": "/homepages/notices",
                    "component": HomepageNoticeMainPage
                },
                {
                    "name": "NoticeDetails",
                    "path": "/homepages/notices/details",
                    "component": HomepageNoticeDetailPage
                },
                {
                    "name": "Settings",
                    "path": "/settings",
                    "redirect": "/settings/apis",
                },
                {
                    "name": "APIs",
                    "path": "/settings/apis",
                    "component": SettingApiMainPage
                },
                {
                    "name": "APIsDetail",
                    "path": "/settings/apis/detail",
                    "component": SettingApiDetailMainPage
                },
                {
                    "name": "Menus",
                    "path": "/settings/menus",
                    "component": SettingMenuMainPage
                },
                {
                    "name": "Roles",
                    "path": "/settings/roles",
                    "component": SettingRoleMainPage
                },
                {
                    "name": "RolesDetail",
                    "path": "/settings/roles/detail",
                    "component": SettingRoleDetailMainPage
                },
                {
                    "name": "Users",
                    "path": "/settings/users",
                    "component": SettingUserMainPage
                },
                {
                    "name": "Codes",
                    "path": "/settings/codes",
                    "component": SettingCodeMainPage
                },
                {
                    "name": "PasswordChanges",
                    "path": "/password-change",
                    "component": PasswordChangeMainPage
                },

                // 그룹웨어 메뉴
                {
                    "name": "GroupwareMenu",
                    "path": "/groupware",
                    "redirect": "/groupware/main"
                },
                {
                    "name": "GroupwareInfo",
                    "path": "/groupware/main",
                    "component": GroupwareMainPage
                },
                {
                    "name": "GroupwareNotices",
                    "path": "/groupware/notices",
                    "component": GroupwareNoticePage
                },
                {
                    "name": "GroupwareNoticeDetails",
                    "path": "/groupware/notices/details",
                    "component": GroupwareNoticeDetailPage
                },
                {
                    "name": "GroupwareApprovalList",
                    "path": "/groupware/approval-list",
                    "component": GroupwareApprovalListPage
                },
                {
                    "name": "GroupwareApproval",
                    "path": "/groupware/approval",
                    "component": GroupwareApprovalPage
                }
            ]
        },
        {
            "name": "Blank",
            "component": BlankLayout,
            "path": "/blank",
            "children": [
                {
                    "name": "Sign In",
                    "path": "/sign-in",
                    "component": SignInMainPage,
                },
                {
                    "name": "Sign Up",
                    "path": "/sign-up",
                    "component": SignUpMainPage
                }
            ]
        }
    ]
});
/* beforeEach */
router.beforeEach(async function (to, from, next) {
    var token,
        treeMenuList,
        treeMenu,
        regex,
        authenticated,
        mainTitle,
        subTitle,
        title,
        titlePath,
        i;
    mainTitle = "ITO";
    regex = to.matched.length > 0 ? to.matched[to.matched.length - 1].regex : null;

    token = ito.auth.getToken();
    if (await ito.auth.authenticated(token)) {
        if (!store.state.app.token) {
            ito.auth.authorize(token);
        }
        treeMenuList = store.state.app.treeMenuList;
        authenticated = false;
        for (i = 0; i < treeMenuList.length; i++) {
            treeMenu = treeMenuList[i];
            if (regex && regex.test(treeMenu.path)) {
                authenticated = true;
                break;
            }
        }
        titlePath = [];
        for (i = 0; i < treeMenuList.length; i++) {
            treeMenu = treeMenuList[i];
            if (new RegExp(treeMenu.path + ".*", "g").test(to.path)) {
                titlePath.push(treeMenu.name);
            }
        }
        store.state.app.showMenuPathName = titlePath.join(" > ");
        titlePath = titlePath.reverse();
        title = titlePath.join(" < ");
        if (authenticated) {
            document.title = title;
            next();
        } else {
            next("/");
        }
    } else {
        subTitle = "로그인";
        titlePath = [subTitle, mainTitle];
        title = titlePath.join(" < ");
        document.title = title;
        ito.auth.unauthorize();
        if (to.path === "/sign-in" || to.path === "/sign-up") {
            next();
        } else {
            next("/sign-in");
        }
    }
});