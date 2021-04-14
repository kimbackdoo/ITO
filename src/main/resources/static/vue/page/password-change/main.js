var PasswordChangeMainPage;
PasswordChangeMainPage = Vue.component("password-change-main-page", async function (resolve) { resolve({
    "template": (await axios.get("/vue/page/password-change/main.html")).data,
    "data": function () {
        return {
            "data": {
                "newPassword": "",
                "oldPassword": ""
            },
            "text": {
                "icon": "mdi-eye-off-outline",
                "type": "password"
            },
            "btn": {
                "savePassword": {
                    "loading": false
                },
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
                    } else if (value.indexOf(moment(store.state.app.person.birthDate).format("MMDD")) !== -1) {
                        message = '생년월일 포함';
                    } else if (value.indexOf(store.state.app.user.username) !== -1) {
                        message = '아이디 포함';
                    } else {
                        flag = true;
                    }
                    return flag || message;
                }
            }
        };
    },
    "methods": {
        "savePassword": async function () {
            let param;
            param = {
                "oldPassword": this.data.oldPassword,
                "newPassword": this.data.newPassword
            };
            this.btn.savePassword.loading = true;
            if (await ito.confirm("변경 하시겠습니까?")) {
                try {
                    await ito.api.common.user.modifyPassword(store.state.app.user.id, param);
                    await ito.alert("저장 되었습니다.");
                    this.$router.push({
                        "path": "/"
                    });
                } catch (e) {
                    await ito.alert(e.response.data.message);
                }
            }
            this.btn.savePassword.loading = false;
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
        }
    },
    "mounted" : async function () {
    },
    "created": async function () {
    },
}); });