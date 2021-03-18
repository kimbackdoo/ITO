var InputProfileMainComponent;
InputProfileMainComponent = Vue.component('inputProfile-main-component', async function(resolve) {
    resolve({
        "template": (await axios.get("/vue/page/main/user/input-profile/main.html")).data,
        "data": function () {
            return {
                "inputProfile": {
                    "panels": {
                        "form": [0]
                    },
                    "form": {
                        "item": {
                            "userProfId": null,
                            "name": null,
                            "birthDate": null,
                            "email": null,
                            "career": null,
                            "job": null,
                            "skill": null,
                            "pay": null,
                            "status": null,
                        },
                    },
                },
                "postcode": '',
                "address": '',
                "detailAddress": '',
                "extraAddress": '',
                "career": {
                    "dataTable": {
                        "headers": [
                            {"text": "프로젝트명", "value": "projectName"},
                            {"text": "기간", "value": "period"},
                            {"text": "직급", "value": "position"},
                            {"text": "담당업무", "value": "task"},
                            {"text": "월급여", "value": "pay"}

                        ],
                        "items": [],
                    },
                },
            };
        },
        "methods": {
            "setProfile": async function(queryId) {
                var self = this;
                return new Promise(function (resolve, reject) {
                    Promise.resolve()
                        .then(function() {
                            return ito.api.common.profile.getProfile(queryId);
                        })
                        .then(function (response) {
                            self.inputProfile.form.item = response.data;
                        })
                        .then(function() { resolve(); });
                });
            },
            "saveProfile": async function () {
                var self = this,
                    data = self.inputProfile.form.item;

                if(data.userProfId != undefined && data.userProfId != null) {
                    if(await ito.confirm("수정하시겠습니까?")) {
                        ito.api.common.profile.modifyProfile(data.userProfId, data);
                        await ito.alert("수정되었습니다.");
                    }
                }
                else {
                    if(await ito.confirm("등록하시겠습니까?")) {
                        ito.api.common.profile.createProfile(data);
                        await ito.alert("등록되었습니다.");
                    }
                }
                self.$router.push({
                    "path": "/main/user/profiles",
                });
            },
            "execDaumPostcode": function() {
                var self = this;
                new daum.Postcode({
                    "onComplete": function(data) {
                        if (data.userSelectedType === 'R') self.address = data.roadAddress;
                        else self.address = data.jibunAddress;

                        if (data.userSelectedType === 'R') {
                            if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                                self.extraAddress += data.bname;
                            }
                            if (data.buildingName !== '' && data.apartment === 'Y') {
                                self.extraAddress += (self.extraAddress !== '' ? ', ' + data.buildingName : data.buildingName);
                            }
                            if (self.extraAddress !== '') {
                                self.extraAddress =  ' (' + self.extraAddress + ')';
                            }
                        } else {
                            self.extraAddress = '';
                        }
                        self.postcode = data.zonecode;
                        self.$refs.detailAddress.focus();
                    },
                }).open();
            },
            "handleClick": function() {
                this.$router.push({
                    "path": "/main/user/profiles",
                });
            },
        },
        "mounted": function() {
            var queryId = this.$route.query.id;
            if(queryId != undefined && queryId != null) {
                Promise.resolve()
                    .then(this.setProfile(queryId),
                    );
            }
        }
    });
});