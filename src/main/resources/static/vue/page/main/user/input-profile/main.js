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
                            "id": null,
                            "name": null,
                            "birthDate": null,
                            "email": null,
                            "career": null,
                            "jobType": null,
                            "skill": null,
                            "pay": null,
                            "status": null,
                            "postcode": null,
                            "address": null,
                            "detailAddress": null,
                            "extraAddress": null
                        },
                    },
                },
            };
        },
        "methods": {
            "setProfile": function() {
                let self = this,
                    person = _.cloneDeep(store.state.app.person);

                self.inputProfile.form.item = person;
                console.log(self.inputProfile.form.item.career);
            },
            "saveProfile": async function () {
                var self = this,
                    data = self.inputProfile.form.item;

                if(data.id != undefined && data.id != null) {
                    if(await ito.confirm("수정하시겠습니까?")) {
                        ito.api.common.person.modifyPerson(data.id, data);
                        await ito.alert("수정되었습니다.");
                    }
                }
                else {
                    if(await ito.confirm("등록하시겠습니까?")) {
                        ito.api.common.person.createPerson(data);
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
                        if (data.userSelectedType === 'R') self.inputProfile.form.item.address = data.roadAddress;
                        else self.inputProfile.form.item.address = data.jibunAddress;

                        if (data.userSelectedType === 'R') {
                            if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                                self.inputProfile.form.item.extraAddress += data.bname;
                            }
                            if (data.buildingName !== '' && data.apartment === 'Y') {
                                self.inputProfile.form.item.extraAddress += (self.inputProfile.form.item.extraAddress !== '' ? ', ' + data.buildingName : data.buildingName);
                            }
                            if (self.inputProfile.form.item.extraAddress !== '') {
                                self.inputProfile.form.item.extraAddress =  ' (' + self.inputProfile.form.item.extraAddress + ')';
                            }
                        } else {
                            self.inputProfile.form.item.extraAddress = '';
                        }
                        self.inputProfile.form.item.postcode = data.zonecode;
                        self.$refs.detailAddress.focus();
                    },
                }).open();
            },
        },
        "mounted": function() {
            this.setProfile();
        }
    });
});