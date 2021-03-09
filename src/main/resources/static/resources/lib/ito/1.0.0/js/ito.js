var ito;
ito = {
    "version": "1.0.0",
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
                "removePerson": function (id) { return axios({"url": "/api/common/people/"+ id, "method": "delete"}); }
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
                "createUserPersonList": function (data) { return axios({"url": "/api/common/user-people?bulk", "method": "post", "data": data}); },
                "createUserPerson": function (data) { return axios({"url": "/api/common/user-people", "method": "post", "data": data}); },
                "modifyUserPersonList": function (data) { return axios({"url": "/api/common/user-people", "method": "put", "data": data}); },
                "modifyUserPerson": function (userId, data) { return axios({"url": "/api/common/user-people/" + userId, "method": "put", "data": data}); },
                "removeUserPersonList": function (data) { return axios({"url": "/api/common/user-people", "method": "delete", "data": data}); },
                "removeUserPerson": function (userId) { return axios({"url": "/api/common/user-people/"+ userId, "method": "delete"}); }
            }
        }
    },
    "util": {
    }
};
