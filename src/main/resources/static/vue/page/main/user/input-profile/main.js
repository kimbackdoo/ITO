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
                "autocomplete": {
                    "skill": {
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
                },
            };
        },
        "methods": {
            "init": async function() {
                await this.loadJobItems();
                await this.loadSkillItems();
                await this.setProfile();
            },
            "loadJobItems": async function() {
                let items, self = this;
                items = (await ito.api.common.code.getCodeList({
                    "parentId": "001",
                    "sort": ["ranking, asc"],
                    "rowSize": 1000000
                })).data.items.map(e=>({"text": e.name, "value": e.id}));
                self.select.job.items.push(...items);
            },
            "loadSkillItems": async function() {
                let items, self = this;
                items = (await ito.api.common.code.getCodeList({
                    "idStartLike": "004",
                    "status": "T",
                    "rowSize": 1000000
                })).data.items.map(e=> ({"text": e.name, "value": e.id}));
                items = items.filter(e=> {
                    if(e.value.startsWith("00401")) return e.value.length > 7;
                    else return e.value.length > 5;
                });
                self.autocomplete.skill.items.push(...items);
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
                person.jobType = [person.jobType];
                self.inputProfile.form.item = person;
            },
            "saveProfile": async function () {
                let self = this, person, personSectorList, personLanguageList,
                        sectorList=[], languageList=[];

                person = self.inputProfile.form.item;
                personSectorList = (await ito.api.common.personSector.getPersonSectorList({"personId": person.id})).data.items;
                personSectorList.forEach(e=>{
                    sectorList.push(e.sector);
                });

                personLanguageList = (await ito.api.common.personLanguage.getPersonLanguageList({"personId": person.id})).data.items;
                personLanguageList.forEach(e=>{
                    languageList.push(e.language);
                });

                if(person.id != undefined && person.id != null) {
                    if(await ito.confirm("수정하시겠습니까?")) {
                        await ito.api.app.profile.modifyProfile({
                            "personDto": person,
                            "sectorList": sectorList,
                            "skillList": person.skill,
                            "languageList": languageList
                        });
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