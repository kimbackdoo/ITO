var ito;
ito = {
    "version": "1.0.0",
    "init": function () {
        axios.defaults.paramsSerializer = function (params) {
            return Qs.stringify(params, {"indices": false});
        }
    },
    "alert": function (message) {
        return new Promise(function (resolve, reject) {
            store.commit("app/SET_ALERT", {
                "value": true,
                "message": message,
                "callback": function () {
                    resolve();
                }
            });
        });
    },
    "confirm": function (message, oktext = '예', canceltext = '아니오') {
        return new Promise(function (resolve, reject) {
            store.commit("app/SET_CONFIRM", {
                "value": true,
                "message": message,
                "oktext" : oktext,
                "canceltext" : canceltext,
                "callback": function (result) {
                    resolve(result);
                }
            });
        });
    },
    "auth": {
        "login": async function (username, password) {
            var authorization,
                token;
            token = Basil.localStorage.get("token");
            if (token && await ito.auth.authenticated(token)) {
                authorization = "Bearer " + token;
            } else {
                authorization = (await axios({
                    "url": "/api/login",
                    "method": "post",
                    "data": {
                        "username": username,
                        "password": password
                    }
                })).headers.authorization;
                token = authorization.replace("Bearer ", "");
                Basil.localStorage.set("token", token);
            }
            axios.defaults.headers.Authorization = authorization;
        },
        "logout": async function (token) {
            await axios({
                "url": "/api/logout",
                "method": "post",
                "headers": {
                    "Authorization": "Bearer " + token
                }
            });
            Basil.localStorage.remove("token");
            delete axios.defaults.headers.Authorization;
        },
        "signUp": async function (data) {
             return await axios({
                "url": "/api/sign-up",
                "method": "post",
                "data": data,
            });
        },
        "idExists": async function (data) {
            return await axios({
                "url": "/api/id-exists",
                "method": "post",
                "data": data,
            });
        },
        "code": async function (type) {
            return await axios({
                "url": "/api/codes/"+type,
                "method": "post"
            });
        },
        "authenticated": async function (token) {
            if (token) {
                try {
                    await axios({
                        "url": "/api",
                        "method": "get",
                        "headers": {
                            "Authorization": token
                        }
                    });
                    return true;
                } catch (e) {
                    return false;
                }
            } else {
                return false;
            }
        },
        "authorize": async function (token) {
            var authentication, user;
            authentication = jwt_decode(token);
            store.commit("app/SET_TOKEN", token);
            store.commit("app/SET_USER", authentication.user);
            store.commit("app/SET_PERSON", authentication.person);
            store.commit("app/SET_ROLE_LIST", authentication.roleList);
            store.commit("app/SET_MENU_LIST", authentication.menuList);
            store.commit("app/SET_TREE_MENU_LIST", authentication.treeMenuList);
            axios.defaults.headers.Authorization = "Bearer " + token;
        },
        "unauthorize": function () {
            store.commit("app/SET_TOKEN", null);
            store.commit("app/SET_USER", null);
            store.commit("app/SET_PERSON", null);
            store.commit("app/SET_ROLE_LIST", null);
            store.commit("app/SET_MENU_LIST", null);
            store.commit("app/SET_TREE_MENU_LIST", null);
            delete axios.defaults.headers.Authorization;
            Basil.localStorage.remove("token");
        },
        "getToken": function () {
            return Basil.localStorage.get("token");
        }
    },
    "api": {
        "common": {
            "api": {
                "getApiList": function (params) { return axios({"url": "/api/common/apis", "method": "get", "params": params}); },
                "getApi": function (id) { return axios({"url": "/api/common/apis/" + id, "method": "get"}); },
                "createApiList": function (data) { return axios({"url": "/api/common/apis?bulk", "method": "post", "data": data}); },
                "createApi": function (data) { return axios({"url": "/api/common/apis", "method": "post", "data": data}); },
                "modifyApiList": function (data) { return axios({"url": "/api/common/apis", "method": "put", "data": data}); },
                "modifyApi": function (id, data) { return axios({"url": "/api/common/apis/" + id, "method": "put", "data": data}); },
                "removeApiList": function (data) { return axios({"url": "/api/common/apis", "method": "delete", "data": data}); },
                "removeApi": function (id) { return axios({"url": "/api/common/apis/"+ id, "method": "delete"}); }
            },
            "menu": {
                "getMenuList": function (params) { return axios({"url": "/api/common/menus", "method": "get", "params": params}); },
                "getMenu": function (id) { return axios({"url": "/api/common/menus/" + id, "method": "get"}); },
                "createMenuList": function (data) { return axios({"url": "/api/common/menus?bulk", "method": "post", "data": data}); },
                "createMenu": function (data) { return axios({"url": "/api/common/menus", "method": "post", "data": data}); },
                "modifyMenuList": function (data) { return axios({"url": "/api/common/menus", "method": "put", "data": data}); },
                "modifyMenu": function (id, data) { return axios({"url": "/api/common/menus/" + id, "method": "put", "data": data}); },
                "removeMenuList": function (data) { return axios({"url": "/api/common/menus", "method": "delete", "data": data}); },
                "removeMenu": function (id) { return axios({"url": "/api/common/menus/"+ id, "method": "delete"}); }
            },
            "person": {
                "getPersonList": function (params) { return axios({"url": "/api/common/people", "method": "get", "params": params}); },
                "getPerson": function (id) { return axios({"url": "/api/common/people/" + id, "method": "get"}); },
                "createPersonList": function (data) { return axios({"url": "/api/common/people?bulk", "method": "post", "data": data}); },
                "createPerson": function (data) { return axios({"url": "/api/common/people", "method": "post", "data": data}); },
                "modifyPersonList": function (data) { return axios({"url": "/api/common/people", "method": "put", "data": data}); },
                "modifyPerson": function (id, data) { return axios({"url": "/api/common/people/" + id, "method": "put", "data": data}); },
                "removePersonList": function (data) { return axios({"url": "/api/common/people", "method": "delete", "data": data}); },
                "removePerson": function (id) { return axios({"url": "/api/common/people/"+ id, "method": "delete"}); },
            },
            "role": {
                "getRoleList": function (params) { return axios({"url": "/api/common/roles", "method": "get", "params": params}); },
                "getRole": function (id) { return axios({"url": "/api/common/roles/" + id, "method": "get"}); },
                "createRoleList": function (data) { return axios({"url": "/api/common/roles?bulk", "method": "post", "data": data}); },
                "createRole": function (data) { return axios({"url": "/api/common/roles", "method": "post", "data": data}); },
                "modifyRoleList": function (data) { return axios({"url": "/api/common/roles", "method": "put", "data": data}); },
                "modifyRole": function (id, data) { return axios({"url": "/api/common/roles/" + id, "method": "put", "data": data}); },
                "removeRoleList": function (data) { return axios({"url": "/api/common/roles", "method": "delete", "data": data}); },
                "removeRoleAllDependencyList": function (data) { return axios({"url": "/api/common/roles/delete-all-dependency", "method": "delete", "data": data}); },
                "removeRole": function (id) { return axios({"url": "/api/common/roles/"+ id, "method": "delete"}); }
            },
            "roleApi": {
                "getRoleApiList": function (params) { return axios({"url": "/api/common/role-apis", "method": "get", "params": params}); },
                "getRoleApi": function (roleId, apiId) { return axios({"url": "/api/common/role-apis/" + roleId + "," + apiId, "method": "get"}); },
                "createRoleApiList": function (data) { return axios({"url": "/api/common/role-apis?bulk", "method": "post", "data": data}); },
                "createRoleApi": function (data) { return axios({"url": "/api/common/role-apis", "method": "post", "data": data}); },
                "modifyRoleApiList": function (data) { return axios({"url": "/api/common/role-apis", "method": "put", "data": data}); },
                "modifyRoleApi": function (roleId, apiId, data) { return axios({"url": "/api/common/role-apis/" + roleId + "," + apiId, "method": "put", "data": data}); },
                "removeRoleApiList": function (data) { return axios({"url": "/api/common/role-apis", "method": "delete", "data": data}); },
                "removeRoleApi": function (roleId, apiId) { return axios({"url": "/api/common/role-apis/"+ roleId + "," + apiId, "method": "delete"}); }
            },
            "roleMenu": {
                "getRoleMenuList": function (params) { return axios({"url": "/api/common/role-menus", "method": "get", "params": params}); },
                "getRoleMenu": function (roleId, menuId) { return axios({"url": "/api/common/role-menus/" + roleId + "," + menuId, "method": "get"}); },
                "createRoleMenuList": function (data) { return axios({"url": "/api/common/role-menus?bulk", "method": "post", "data": data}); },
                "createRoleMenu": function (data) { return axios({"url": "/api/common/role-menus", "method": "post", "data": data}); },
                "modifyRoleMenuList": function (data) { return axios({"url": "/api/common/role-menus", "method": "put", "data": data}); },
                "modifyRoleMenu": function (roleId, menuId, data) { return axios({"url": "/api/common/role-menus/" + roleId + "," + menuId, "method": "put", "data": data}); },
                "removeRoleMenuList": function (data) { return axios({"url": "/api/common/role-menus", "method": "delete", "data": data}); },
                "removeRoleMenu": function (roleId, menuId) { return axios({"url": "/api/common/role-menus/"+ roleId + "," + menuId, "method": "delete"}); }
            },
            "roleUser": {
                "getRoleUserList": function (params) { return axios({"url": "/api/common/role-users", "method": "get", "params": params}); },
                "getRoleUser": function (userId) { return axios({"url": "/api/common/role-users/" + roleId + "," + userId, "method": "get"}); },
                "createRoleUserList": function (data) { return axios({"url": "/api/common/role-users?bulk", "method": "post", "data": data}); },
                "createRoleUser": function (data) { return axios({"url": "/api/common/role-users", "method": "post", "data": data}); },
                "modifyRoleUserList": function (data) { return axios({"url": "/api/common/role-users", "method": "put", "data": data}); },
                "modifyRoleUser": function (roleId, userId, data) { return axios({"url": "/api/common/role-users/" + roleId + "," + userId, "method": "put", "data": data}); },
                "removeRoleUserList": function (data) { return axios({"url": "/api/common/role-users", "method": "delete", "data": data}); },
                "removeRoleUser": function (roleId, userId) { return axios({"url": "/api/common/role-users/"+ roleId + "," + userId, "method": "delete"}); }
            },
            "user": {
                "getUserList": function (params) { return axios({"url": "/api/common/users", "method": "get", "params": params}); },
                "getUser": function (id) { return axios({"url": "/api/common/users/" + id, "method": "get"}); },
                "createUserList": function (data) { return axios({"url": "/api/common/users?bulk", "method": "post", "data": data}); },
                "createUser": function (data) { return axios({"url": "/api/common/users", "method": "post", "data": data}); },
                "modifyUserList": function (data) { return axios({"url": "/api/common/users", "method": "put", "data": data}); },
                "modifyUser": function (id, data) { return axios({"url": "/api/common/users/" + id, "method": "put", "data": data}); },
                "removeUserList": function (data) { return axios({"url": "/api/common/users", "method": "delete", "data": data}); },
                "removeUser": function (id) { return axios({"url": "/api/common/users/"+ id, "method": "delete"}); },
                "modifyPassword": function (id, data) { return axios({"url": "/api/common/users/" + id + "/password", "method": "put", "data": data}); }
            },
            "userPerson": {
                "getUserPersonList": function (params) { return axios({"url": "/api/common/user-people", "method": "get", "params": params}); },
                "getUserPerson": function (userId) { return axios({"url": "/api/common/user-people/" + userId, "method": "get"}); },
                "getUserId": function (personId) { return axios({"url": "/api/common/user-people/id" + personId, "method": "get"}); },
                "createUserPersonList": function (data) { return axios({"url": "/api/common/user-people?bulk", "method": "post", "data": data}); },
                "createUserPerson": function (data) { return axios({"url": "/api/common/user-people", "method": "post", "data": data}); },
                "modifyUserPersonList": function (data) { return axios({"url": "/api/common/user-people", "method": "put", "data": data}); },
                "modifyUserPerson": function (userId, data) { return axios({"url": "/api/common/user-people/" + userId, "method": "put", "data": data}); },
                "removeUserPersonList": function (data) { return axios({"url": "/api/common/user-people", "method": "delete", "data": data}); },
                "removeUserPerson": function (userId) { return axios({"url": "/api/common/user-people/"+ userId, "method": "delete"}); }
            },
            "code": {
                "getCodeList": function (params) { return axios({"url": "/api/common/codes", "method": "get", "params": params}); },
                "getCode": function (id) { return axios({"url": "/api/common/codes/" + id, "method": "get"}); },
                "createCodeList": function (data) { return axios({"url": "/api/common/codes?bulk", "method": "post", "data": data}); },
                "createCode": function (data) { return axios({"url": "/api/common/codes", "method": "post", "data": data}); },
                "modifyCodeList": function (data) { return axios({"url": "/api/common/codes", "method": "put", "data": data}); },
                "modifyCode": function (id, data) { return axios({"url": "/api/common/codes/" + id, "method": "put", "data": data}); },
                "removeCodeList": function (data) { return axios({"url": "/api/common/codes", "method": "delete", "data": data}); },
                "removeCode": function (id) { return axios({"url": "/api/common/codes/"+ id, "method": "delete"}); },
            },
            "personCareer": {
                "getPersonCareerList": function (params) { return axios({"url": "/api/common/person-career", "method": "get", "params": params}); },
                "getPersonCareer": function (personId, careerId) { return axios({"url": "/api/common/person-career/" + personId + "," + careerId, "method": "get"}); },
                "createPersonCareerList": function (data) { return axios({"url": "/api/common/person-career?bulk", "method": "post", "data": data}); },
                "createPersonCareer": function (data) { return axios({"url": "/api/common/person-career", "method": "post", "data": data}); },
                "modifyPersonCareerList": function (data) { return axios({"url": "/api/common/person-career", "method": "put", "data": data}); },
                "modifyPersonCareer": function (personId, careerId, data) { return axios({"url": "/api/common/person-career/" + personId + "," + careerId, "method": "put", "data": data}); },
                "removePersonCareerList": function (data) { return axios({"url": "/api/common/person-career", "method": "delete", "data": data}); },
                "removePersonCareer": function (personId, careerId) { return axios({"url": "/api/common/person-career/"+ personId + "," + careerId, "method": "delete"}); }
            },
            "career": {
                "getCareerList": function (params) { return axios({"url": "/api/common/career", "method": "get", "params": params}); },
                "getCareer": function (personcareerId) { return axios({"url": "/api/common/career/" + personcareerId, "method": "get"}); },
                "createCareerList": function (data) { return axios({"url": "/api/common/career?bulk", "method": "post", "data": data}); },
                "createCareer": function (data) { return axios({"url": "/api/common/career", "method": "post", "data": data}); },
                "modifyCareerList": function (data) { return axios({"url": "/api/common/career", "method": "put", "data": data}); },
                "modifyCareer": function (personcareerId, data) { return axios({"url": "/api/common/career/" + personcareerId, "method": "put", "data": data}); },
                "removeCareerList": function (data) { return axios({"url": "/api/common/career", "method": "delete", "data": data}); },
                "removeCareer": function (personcareerId) { return axios({"url": "/api/common/career/"+ personcareerId, "method": "delete"}); }
            },
            "project": {
                "getProjectList": function (params) { return axios({"url": "/api/common/projects", "method": "get", "params": params}); },
                "getProject": function (id) { return axios({"url": "/api/common/projects/" + id, "method": "get"}); },
                "createProjectList": function (data) { return axios({"url": "/api/common/projects?bulk", "method": "post", "data": data}); },
                "createProject": function (data) { return axios({"url": "/api/common/projects", "method": "post", "data": data}); },
                "modifyProjectList": function (data) { return axios({"url": "/api/common/projects", "method": "put", "data": data}); },
                "modifyProject": function (id, data) { return axios({"url": "/api/common/projects/" + id, "method": "put", "data": data}); },
                "removeProjectList": function (data) { return axios({"url": "/api/common/projects", "method": "delete", "data": data}); },
                "removeProject": function (id) { return axios({"url": "/api/common/projects/"+ id, "method": "delete"}); }
            },
            "personSector": {
                "getPersonSectorList": function (params) { return axios({"url": "/api/common/person-sectors", "method": "get", "params": params}); },
                "getPersonSector": function (personId, sector) { return axios({"url": "/api/common/person-sectors/" + personId + "," + sector, "method": "get"}); },
                "createPersonSectorList": function (data) { return axios({"url": "/api/common/person-sectors?bulk", "method": "post", "data": data}); },
                "createPersonSector": function (data) { return axios({"url": "/api/common/person-sectors", "method": "post", "data": data}); },
                "modifyPersonSectorList": function (data) { return axios({"url": "/api/common/person-sectors", "method": "put", "data": data}); },
                "modifyPersonSector": function (personId, sector, data) { return axios({"url": "/api/common/person-sectors/" + personId + "," + sector, "method": "put", "data": data}); },
                "removePersonSectorList": function (data) { return axios({"url": "/api/common/person-sectors", "method": "delete", "data": data}); },
                "removePersonSector": function (personId, sector) { return axios({"url": "/api/common/person-sectors/"+ personId + "," + sector, "method": "delete"}); }
            },
            "personSkill": {
                "getPersonSkillList": function (params) { return axios({"url": "/api/common/person-skills", "method": "get", "params": params}); },
                "getPersonSkill": function (personId, skill) { return axios({"url": "/api/common/person-skills/" + personId + "," + skill, "method": "get"}); },
                "createPersonSkillList": function (data) { return axios({"url": "/api/common/person-skills?bulk", "method": "post", "data": data}); },
                "createPersonSkill": function (data) { return axios({"url": "/api/common/person-skills", "method": "post", "data": data}); },
                "modifyPersonSkillList": function (data) { return axios({"url": "/api/common/person-skills", "method": "put", "data": data}); },
                "modifyPersonSkill": function (personId, skill, data) { return axios({"url": "/api/common/person-skills/" + personId + "," + skill, "method": "put", "data": data}); },
                "removePersonSkillList": function (data) { return axios({"url": "/api/common/person-skills", "method": "delete", "data": data}); },
                "removePersonSkill": function (personId, skill) { return axios({"url": "/api/common/person-skills/"+ personId + "," + skill, "method": "delete"}); }
            },
            "personLanguage": {
                "getPersonLanguageList": function (params) { return axios({"url": "/api/common/person-languages", "method": "get", "params": params}); },
                "getPersonLanguage": function (personId, language) { return axios({"url": "/api/common/person-languages/" + personId + "," + language, "method": "get"}); },
                "createPersonLanguageList": function (data) { return axios({"url": "/api/common/person-languages?bulk", "method": "post", "data": data}); },
                "createPersonLanguage": function (data) { return axios({"url": "/api/common/person-languages", "method": "post", "data": data}); },
                "modifyPersonLanguageList": function (data) { return axios({"url": "/api/common/person-languages", "method": "put", "data": data}); },
                "modifyPersonLanguage": function (personId, language, data) { return axios({"url": "/api/common/person-languages/" + personId + "," + language, "method": "put", "data": data}); },
                "removePersonLanguageList": function (data) { return axios({"url": "/api/common/person-languages", "method": "delete", "data": data}); },
                "removePersonLanguage": function (personId, language) { return axios({"url": "/api/common/person-languages/"+ personId + "," + language, "method": "delete"}); }
            },
            "projectSkill": {
                "getProjectSkillList": function (params) { return axios({"url": "/api/common/project-skills", "method": "get", "params": params}); },
                "getProjectSkill": function (projectId, skill) { return axios({"url": "/api/common/project-skills/" + projectId + "," + skill, "method": "get"}); },
                "createProjectSkillList": function (data) { return axios({"url": "/api/common/project-skills?bulk", "method": "post", "data": data}); },
                "createProjectSkill": function (data) { return axios({"url": "/api/common/project-skills", "method": "post", "data": data}); },
                "modifyProjectSkillList": function (data) { return axios({"url": "/api/common/project-skills", "method": "put", "data": data}); },
                "modifyProjectSkill": function (projectId, skill, data) { return axios({"url": "/api/common/project-skills/" + projectId + "," + skill, "method": "put", "data": data}); },
                "removeProjectSkillList": function (data) { return axios({"url": "/api/common/project-skills", "method": "delete", "data": data}); },
                "removeProjectSkill": function (projectId, skill) { return axios({"url": "/api/common/project-skills/"+ projectId + "," + skill, "method": "delete"}); }
            },
            "projectPerson": {
                "getProjectPersonList": function(params) {return axios({"url": "/api/common/project-person", "method": "get", "params": params}); },
                "createProjectPerson": function (data) { return axios({"url": "/api/common/project-person", "method": "post", "data": data}); },
                "modifyProjectPerson": function (personId, projectId, data) { return axios({"url": "/api/common/project-person/" + personId + "," + projectId, "method": "put", "data": data}); },
                "removeProjectPerson": function (projectId, personId) { return axios({"url": "/api/common/project-person/"+ projectId + "," + personId, "method": "delete"}); },
                "removeProjectPersonList": function (data) { return axios({"url": "/api/common/project-person","method": "delete" , "data": data}); }
            },
            "projectCareer": {
                "getProjectCareerList": function (params) { return axios({"url": "/api/common/project-careers", "method": "get", "params": params}); },
                "createProjectCareer": function (data) { return axios({"url": "/api/common/project-careers", "method": "post", "data": data}); },
                "modifyProjectCareer": function (projectId, careerId, data) { return axios({"url": "/api/common/project-careers/" + projectId + "," + careerId, "method": "put", "data": data}); },
                "removeProjectCareerList": function (data) { return axios({"url": "/api/common/project-careers", "method": "delete", "data": data}); },
                "removeProjectCareer": function (projectId, careerId) { return axios({"url": "/api/common/project-careers/"+ projectId + "," + careerId, "method": "delete"}); }
            },
        },
        "app": {
            "account": {
                "createAccount": function (data) { return axios({"url": "/api/app/accounts", "method": "post", "data": data}); },
                "modifyAccount": function (data) { return axios({"url": "/api/app/accounts", "method": "put", "data": data}); },
                "removeAccount": function (data) { return axios({"url": "/api/app/accounts", "method": "delete", "data": data}); },
            },
            "RoleAndRoleMenuAndRoleApi": {
                "createRoleAndRoleMenuAndRoleApi": function (data) { return axios({"url": "/api/app/role-menu-apis", "method": "post", "data": data}); },
                "modifyRoleAndRoleMenuAndRoleApi": function (data) { return axios({"url": "/api/app/role-menu-apis", "method": "put", "data": data}); },
            },
            "upload" : {
                "person" : function (data) { return axios({"url": "/api/app/uploads/person","enctype": "multipart/form-data", "contentType" : false, "cache" : false, "processData" : false, "method": "post", "data": data});},
                "ito" : function (data) { return axios({"url": "/api/app/uploads/ito","enctype": "multipart/form-data", "contentType" : false, "cache" : false, "processData" : false, "method": "post", "data": data});},
            },
            "profile": {
                "createProfile": function (data) { return axios({"url": "/api/app/profile", "method": "post", "data": data}); },
                "modifyProfile": function (data) { return axios({"url": "/api/app/profile", "method": "put", "data": data}); },
                "removeProfile": function (id) { return axios({"url": "/api/app/profile/"+id, "method": "delete"}); },
                "removeProfileList": function (data) { return axios({"url": "/api/app/profile", "method": "delete", "data": data}); },
            },
            "involvement": {
                "createInvolvement": function (data) { return axios({"url": "/api/app/involvements", "method": "post", "data": data}); },
                "modifyInvolvement": function (data) { return axios({"url": "/api/app/involvements", "method": "put", "data": data}); },
                "removeInvolvement": function (data) { return axios({"url": "/api/app/involvements", "method": "delete", "data": data}); },
                "getCareerIdList": function (projectId, personId)  { return axios({"url": "/api/app/involvements/"+projectId+","+personId, "method": "get"}); },
            },
            "personDownload": {
                "downloadPersonListXlsx": async function(params, title) {
                    let a, data, url;
                    data = (await axios({
                        "url": "/api/app/person-downloads/list.xlsx",
                        "method": "get",
                        "responseType": "blob",
                        "params": params
                    })).data;
                    url = window.URL.createObjectURL(data);
                    a = document.createElement("a");
                    a.setAttribute("href", url);
                    switch(title) {
                        case "available": a.setAttribute("download", "가용인력 현황.xlsx"); break;
                        case "confirm": a.setAttribute("download", "확정 현황.xlsx"); break;
                        case "apply": a.setAttribute("download", "지원자 현황.xlsx"); break;
                        default: a.setAttribute("download", "사용자 현황.xlsx");
                    }
                    a.click();
                    window.URL.revokeObjectURL(url);
                },
            },
            "projectDownload": {
                "downloadProjectListXlsx": async function(params) {
                    let a, data, url;
                    data = (await axios({
                        "url": "/api/app/project-downloads/list.xlsx",
                        "method": "get",
                        "responseType": "blob",
                        "params": params
                    })).data;
                    url = window.URL.createObjectURL(data);
                    a = document.createElement("a");
                    a.setAttribute("href", url);
                    a.setAttribute("download", "프로젝트목록.xlsx");
                    a.click();
                    window.URL.revokeObjectURL(url);
                },
            },
            "homepage": {
                "notice": {
                    "getNoticeList": function (params) { return axios({"url": "/api/app/homepages/notices", "method": "get", "params": params}); },
                    "getNotice": function (id) { return axios({"url": "/api/app/homepages/notices/" + id, "method": "get"}); },
                    "createNoticeList": function (data) { return axios({"url": "/api/app/homepages/notices?bulk", "method": "post", "data": data}); },
                    "createNotice": function (data) { return axios({"url": "/api/app/homepages/notices", "method": "post", "data": data}); },
                    "modifyNoticeList": function (data) { return axios({"url": "/api/app/homepages/notices", "method": "put", "data": data}); },
                    "modifyNotice": function (id, data) { return axios({"url": "/api/app/homepages/notices/" + id, "method": "put", "data": data}); },
                    "removeNoticeList": function (data) { return axios({"url": "/api/app/homepages/notices", "method": "delete", "data": data}); },
                    "removeNotice": function (id) { return axios({"url": "/api/app/homepages/notices/"+ id, "method": "delete"}); },
                },
                "contact": {
                    "getContactList": function (params) { return axios({"url": "/api/app/homepages/contacts", "method": "get", "params": params}); },
                    "getContact": function (id) { return axios({"url": "/api/app/homepages/contacts/" + id, "method": "get"}); },
                    "createContactList": function (data) { return axios({"url": "/api/app/homepages/contacts?bulk", "method": "post", "data": data}); },
                    "createContact": function (data) { return axios({"url": "/api/app/homepages/contacts", "method": "post", "data": data}); },
                    "modifyContactList": function (data) { return axios({"url": "/api/app/homepages/contacts", "method": "put", "data": data}); },
                    "modifyContact": function (id, data) { return axios({"url": "/api/app/homepages/contacts/" + id, "method": "put", "data": data}); },
                    "removeContactList": function (data) { return axios({"url": "/api/app/homepages/contacts", "method": "delete", "data": data}); },
                    "removeContact": function (id) { return axios({"url": "/api/app/homepages/contacts/"+ id, "method": "delete"}); },
                }

            },
            "vacation": {
                "getVacationList": function (params) {return axios({"url": "/api/app/vacations", "method": "get", "params":params}); },
                "getVacationInfo": function (id) {return axios({"url": "/api/app/vacations/"+id, "method": "get"}); },
                "createVacation": function (data) { return axios({"url": "/api/app/vacations", "method": "post", "data": data}); },
                "modifyVacation": function (id, data) { return axios({"url": "/api/app/vacations/" + id, "method": "put", "data": data}); },
                "removeVacationList": function (data) { return axios({"url": "/api/app/vacations", "method": "delete", "data": data}); },
                "removeVacation": function (id) { return axios({"url": "/api/app/vacations/"+ id, "method": "delete"}); },
            },
            "approval": {
                "getApprovalList": function (params) {return axios({"url": "/api/app/approvals", "method": "get", "params":params}); },
                "getApproval": function (id) {return axios({"url": "/api/app/approvals/"+id, "method": "get" }); },
                "createApproval": function (data) { return axios({"url": "/api/app/approvals", "method": "post", "data": data}); },
                "modifyApproval": function (id, data) { return axios({"url": "/api/app/approvals/" + id, "method": "put", "data": data}); },
                "removeApprovalList": function (data) { return axios({"url": "/api/app/approvals", "method": "delete", "data": data}); },
                "removeApproval": function (id) { return axios({"url": "/api/app/approvals/"+ id, "method": "delete"}); },
            },
            "notice": {
                "getNoticeList": function (params) {return axios({"url": "/api/app/notices", "method": "get", "params":params}); },
                "getNotice": function (id) {return axios({"url": "/api/app/notices/"+id, "method": "get"}); },
                "createNotice": function (data) { return axios({"url": "/api/app/notices", "method": "post", "data": data}); },
                "modifyNotice": function (id, data) { return axios({"url": "/api/app/notices/" + id, "method": "put", "data": data}); },
                "removeNoticeList": function (data) { return axios({"url": "/api/app/notices", "method": "delete", "data": data}); },
                "removeNotice": function (id) { return axios({"url": "/api/app/notices/"+ id, "method": "delete"}); },
            },
            "vacationDownload": {
                "getVacationDataXlsx": function (params) {return axios({"url": "/api/app/vacationDataXlsx", "method":"get", "params": params}); },
                "downloadVacationXlsx": async function (params){
                    var a,data,url;
                    data = (await axios({
                        "url": "/api/app/vacationDataXlsx",
                        "method":"get",
                        "responseType": "blob",
                        "params": params
                    })).data;
                    url = window.URL.createObjectURL(data);
                    a = document.createElement("a");
                    a.setAttribute("href",url);
                    a.setAttribute("download", "휴가신청서.xlsx");
                    a.click();
                    window.URL.revokeObjectURL(url);
                }
            },
			"expenditureDownload": {
                "downloadExpenditureXlsx": async function (){
                    var a,data,url;
                    data = (await axios({
                        "url": "/api/app/expenditureXlsx",
                        "method":"get",
                        "responseType": "blob"
                    })).data;
                    url = window.URL.createObjectURL(data);
                    a = document.createElement("a");
                    a.setAttribute("href",url);
                    a.setAttribute("download", "지출결의서.xlsx");
                    a.click();
                    window.URL.revokeObjectURL(url);
                }
			},
            "mailSend": {
                "getMailSend": function (params) {return axios({"url": "/api/app/mails", "method": "get", "params":params}); },
            }


        },
        "util": {
            "menu": {
                "getTreeMenuList": function (params) { return axios({"url": "/api/util/menus/tree-menus", "method": "get", "params": params}); }
            }
        }
    },
    "util": {
        "sort": function (sortBy, sortDesc) {
            let str;
            str = [];
            if(sortBy.length > 0 && sortDesc.length > 0) {
                for (var i = 0;  i < sortBy.length; i++) {
                    str.push(sortBy[i] + "," + (sortDesc[i] ? "desc" : "asc"));
                }
            }
            return str;
        },
        "getPostcode": function () {
            return new Promise(function (resolve, reject) {
                new daum.Postcode({
                    "oncomplete": function (data) {
                        resolve(data);
                    },
                    "onclose": function (state) {
                        resolve();
                    }
                }).open();
            });
        }
    }
};
ito.init();