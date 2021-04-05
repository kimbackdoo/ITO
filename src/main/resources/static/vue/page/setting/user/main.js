var SettingUserMainPage;
SettingUserMainPage = Vue.component("setting-user-main-page", async function (resolve) { resolve({
    "template": (await axios.get("/vue/page/setting/user/main.html")).data,
    "data": function () {
        return {
            "data": {
                "userList": [],
                "person":{},
                "user": {},
                "role": {
                    "id": null
                }
            },
            "dialog": {
                "title": "테스트",
                "visible": false
            },
            "text": {
                "icon": "mdi-eye-off-outline",
                "type": "password"
            },
            "select": {
                "roleList": []
            },
            "btn": {
                "saveAccount": {
                    "loading": false
                }
            },
            "statusNameList": {
                "T": "사용",
                "F": "회원가입",
                "D": "삭제"
            },
            "rules": {
                "required": value => !!value || '필수',
                "password": (value) => {
                    let message, flag;
                    flag = false;
                    if(value == null || value.length < 8) {
                        message = '비밀번호 8자 이상';
                    } else if (!/(?=.*\d{1,50})(?=.*[~`!@#$%\^&*()-+=]{1,50})(?=.*[a-zA-Z]{2,50}).{8,50}$/g.test(value)) {
                        message = '영문자 숫자 특수문자 조합';
                    } else if (/(\w)\1\1/.test(value)){
                        message = '동일한 3자리 문자';
                    } else if (!this.stck(value, 3)) {
                        message = '연속된 3자리 문자';
                    } else if (value.indexOf(moment(this.data.person.birthDate).format("MMDD")) !== -1) {
                        message = '생년월일 포함';
                    } else if (value.indexOf(this.data.user.username) !== -1) {
                        message = '아이디 포함';
                    } else {
                        flag = true;
                    }
                    return flag || message;
                },
                "email": value => /^([0-9a-zA-Z_\.-]+)@([0-9a-zA-Z_-]+)(\.[0-9a-zA-Z_-]+){1,2}$/.test(value) || '형식이 맞지 않습니다.'
            }
        };
    },
    "methods": {
        "loadUserList": async function () {
            var userPersonList;
            userPersonList = (await ito.api.common.userPerson.getUserPersonList({
                "page": 1,
                "rowSize": 100000000
            })).data.items;

            this.data.userList = userPersonList;
        },
        "loadRoleList": async function () {
            var roleList;
            roleList = (await ito.api.common.role.getRoleList({
                "page": 1,
                "rowSize": 100000000
            })).data.items;
            this.select.roleList = [{"id": null, "name": "선택"}, ...roleList];
        },
        "clickUser": async function (i) {
            let roleList;
            this.data.person = this.data.userList[i].person;
            this.data.user = this.data.userList[i].user;
            roleList = (await ito.api.common.roleUser.getRoleUserList({"userId": this.data.user.id, "rowSize": 1})).data.items;

            if (roleList.length === 1) {
                this.data.role = roleList[0].role;
            } else {
                this.data.role = {"id": null};
            }
        },
        "existUsername": async function (username) {
            return (await ito.api.common.user.getUserList({"username": username, "rowSize": 1})).data.items.length > 0 && !this.data.user.id ? true : false;
        },
        "saveAccount": async function () {
            let user, person, role, account, validate;
            user = this.data.user;
            person = this.data.person;
            role = this.data.role;
            this.btn.saveAccount.loading = true;
            validate = this.$refs.form.validate();
            if (!validate) {
                await ito.alert("유효한 값을 작성해주세요.");
            } else if (await this.existUsername(user.username)) {
                await ito.alert("동일한 아이디가 존재합니다.");
            } else if (await ito.confirm("저장 하시겠습니까?")) {
                if (user.id && person.id) {
                    await ito.api.app.account.modifyAccount({"userDto": user, "personDto": person, "roleId": role.id});
                } else {
                    account = (await ito.api.app.account.createAccount({"userDto": user, "personDto": person, "roleId": role.id})).data;
                    this.data.user.id = account.userId;
                    this.data.person.id = account.personId;
                }
                await ito.alert("저장 되었습니다.");
                await this.loadUserList();
            }
            this.btn.saveAccount.loading = false;
        },
        "stck": function (str, limit) {
            var o, d, p, n = 0, l = limit == null ? 4 : limit;
            for (var i = 0; i < str.length; i++) {
                var c = str.charCodeAt(i);
                if (i > 0 && (p = o - c) > -2 && p < 2 && (n = p == d ? n + 1 : 0) > l - 3)
                    return false;
                d = p;
                o = c;
            }
            return true;
        },
        "showPassword": function () {
            if(this.text.icon == 'mdi-eye-off-outline'){
                this.text.icon = 'mdi-eye-outline';
                this.text.type = 'text';
            } else {
                this.text.icon = 'mdi-eye-off-outline';
                this.text.type = 'password';
            }
        },
        "open": function () {
            this.dialog.visible = true;
        },
        "upload": async function (dataUpload) {
            var form = new FormData();
            store.commit("app/SET_LOADING", true);
            form.append("file" , dataUpload.selectedFile);

            var returnType = await ito.api.app.upload.ito(form);
            store.commit("app/SET_LOADING", false);

            if(returnType.data.returnVal != 'SUCCESS'){
                await ito.alert(returnType.data.returnMsg);
            }else{
                await ito.alert(returnType.data.returnMsg);
                this.dialog.visible = false;
            }
        }

    },
    "mounted": async function () {
        Promise.all([
            this.loadUserList(),
            this.loadRoleList()
        ]);
    }
}); });