var InputProfileMainComponent;
InputProfileMainComponent = Vue.component('inputProfile-main-component', async function(resolve) {
    resolve({
        "template": (await axios.get("/vue/page/main/user/input-profile/main.html")).data,
        "data": function () {
            return {
                "select": {
                    "job": {
                        "items": []
                    },
                },
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
                    "skill": {
                        "items": []
                    },
                },
            };
        },
        "watch": {
            "inputProfile.form.item.jobType": {
                "handler": async function (n, o) {
                    let skill = this.select.job.items.find(e=>e.value == this.inputProfile.form.item.jobType);

                    if(o !== null) {
                        this.inputProfile.form.item.skill = [];
                    }
                    this.inputProfile.skill.items = [];
                    this.inputProfile.skill.items = (await ito.api.common.code.getCodeList({
                        "idStartLike": "004",
                        "status": "T",
                        "skill": skill !== undefined ? skill.text : null,
                        "rowSize": 1000000
                    })).data.items.filter(e=> {
                        if(e.id.startsWith("00401")) return e.id.length > 7;
                        else return e.id.length > 5;
                    });
                }
            },
        },
        "methods": {
            "init": async function() {
                await this.loadJobItems();
                await this.setProfile();
            },
            "loadJobItems": async function() {
                let items;
                items = (await ito.api.common.code.getCodeList({
                    "parentId": "001",
                    "sort": ["ranking, asc"],
                    "rowSize": 1000000
                })).data.items.map(e=>({"text": e.name, "value": e.id}));
                this.select.job.items.push(...items);
            },
            "setProfile": async function() {
                let self = this, personId, skill, person, personSkillList;

                personId = store.state.app.person.id;
                person = (await ito.api.common.person.getPerson(personId)).data;
                personSkillList = (await ito.api.common.personSkill.getPersonSkillList({personId})).data.items;
                skill = [];
                personSkillList.forEach(e=>{
                    skill.push(e.skill);
                });
                person.skill = skill;
                self.inputProfile.form.item = person;
            },
            "saveProfile": async function () {
                let self = this, personId, skill, personSkillList,
                    data = self.inputProfile.form.item;
                personId = data.id;
                personSkillList = (await ito.api.common.personSkill.getPersonSkillList({personId})).data.items;
                skill = [];
                personSkillList.forEach(e=>{
                    skill.push(e.skill);
                });
                data.skill = skill;

                console.log(data.skill);

                if(data.id != undefined && data.id != null) {
                    if(await ito.confirm("수정하시겠습니까?")) {
                        await ito.api.app.person.modifyPerson(data);
                        // ito.api.common.personSkill.modifyPersonSkill(data.id, data.skill, data);
                        await ito.alert("수정되었습니다.");
                    }
                }
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
            this.init();
        }
    });
});