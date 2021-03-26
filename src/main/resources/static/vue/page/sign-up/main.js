var SignUpMainPage;
SignUpMainPage = Vue.component("sign-up-main-page", async function (resolve) { resolve({
    "template": (await axios.get("/vue/page/sign-up/main.html")).data,
    "data": function () {
        return {
            "stepper": {
                "category": ["약관", "개인정보","보유기술","경력","완료"],
                "complete": 4
            },
            "data": {
                "person":{},
                "user": {},
                "skill": {},
                "role": {
                    "id": 2
                }
            },
            "code": {
                "job": {
                    "items": []
                }
            },
            "form": [
                "termsForm",
                "userForm",
                "skillForm",
                "careerForm",
                "successForm"
            ]
        };
    },
    "watch": {
    },
    "methods": {
        "init": async function () {
            await this.getJobTypeList();
        },
        "getJobTypeList": async function () {
            this.code.job.items = (await ito.auth.code("jobs")).data.items;
        },
        "existUsername": async function (username) {
            return (await ito.auth.idExists({"username": username})).data > 0 ? true : false;
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
        "nextStep": async function (index) {
            let validate;
            if (index === this.stepper.category.length) {
                this.stepper.complete = 1;
            } else {
                switch(index) {
                case 1:
                    this.stepper.complete = index + 1;
                    break;
                case 2:
                    console.log(this.$refs[this.form[index-1]]);
                    validate = this.$refs[this.form[index-1]].$refs[this.form[index-1]].validate();
                    if(!validate) {
                        await ito.alert("유효한 값을 작성해주세요.");
                    }else if (await this.existUsername(this.data.user.username)) {
                        await ito.alert("동일한 아이디가 존재합니다.");
                    }else {
                        this.stepper.complete = index + 1;
                    }
                    break;
                case 3:
                case 4:
                case 5:
                    this.stepper.complete = index + 1;
                    break;
                default:
                    break;
                }
            }
        }
    },
    "mounted": async function () {
        this.init();
    }
}); });