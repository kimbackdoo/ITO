var MainAdminFormPage = Vue.component('main-admin-userInfo-form-page', function (resolve, reject) {
    axios.get("/vue/page/main/admin/user-info-form/main.html").then(function (response) {

       resolve({
            "template": response.data,
            "data": function () {
                return {
                    "data": {
                        "id":"",
                        "name": "",
                        "jobType": "",
                        "skill": "",
                        "phoneNumber":"",
                        "career": "",
                        "career1": "",
                        "career2": "",
                        "localPlace": "",
                        "detailLocalPlace": "",
                        "education": "",
                        "certificateStatus": "",
                        "pay": "",
                        "postcode":"",
                        "address": "",
                        "detailAddress":"",
                        "extraAddress":"",
                        "inputStatus":"",
                        "birthDate":"",
                        "workableDay":"",
                    },
                    "data2":{
                        "skill": [],
                    },
                  "user": {
                        "panels": {
                            "search": [0],
                            "list": [0]
                        },
                        "selected": [],
                        "valid": true,
                        "dialog": false,
                        "dialogDelete": false,
                        "pagination": {
                            "length": 10,
                            "totalVisible": 10
                        },
                        "formRule":{
                            "name": [
                                (v) =>
                                    !!v || this.$vuetify.lang.t('$vuetify.rule.required', ['name'])
                            ],
                            "phoneNumber": [
                                (v) =>
                                    !!v || this.$vuetify.lang.t('$vuetify.rule.required', ['phoneNumber'])
                            ],
                            "jobType": [
                                (v) =>
                                    !!v || this.$vuetify.lang.t('$vuetify.rule.required', ['jobType'])
                            ],
                        },
                        "dataTable": {
                            "items": [],
                            "loading": false,
                            "serverItemsLength": 0,
                            "page": 1,
                            "itemsPerPage": 10,
                            "education": [
                                 {"text":"2년제", "value":"2년제"},
                                 {"text":"3년제", "value":"3년제"},
                                 {"text":"4년제", "value":"4년제"},
                            ],
                            "certificateStatus": [
                                 {"text":"보유", "value":"T"},
                                 {"text":"없음", "value":"F"},
                            ],
                            "career1": [
                                {"text":"1년미만", "value": 0},
                                {"text":"1", "value": 1},
                                {"text":"2", "value": 2},
                                {"text":"3", "value": 3},
                                {"text":"4", "value": 4},
                                {"text":"5", "value": 5},
                                {"text":"6", "value": 6},
                                {"text":"7", "value": 8},
                                {"text":"8", "value": 9},
                                {"text":"9", "value": 10},
                                {"text":"10", "value": 11},
                                {"text":"12", "value": 12},
                                {"text":"13", "value": 13},
                                {"text":"14", "value": 14},
                                {"text":"15", "value": 15},
                                {"text":"16", "value": 16},
                                {"text":"17", "value": 17},
                                {"text":"18", "value": 18},
                                {"text":"19", "value": 19},
                                {"text":"20", "value": 20}
                            ],
                            "career2": [
                                {"text":"1", "value": 0.08},
                                {"text":"2", "value": 0.16},
                                {"text":"3", "value": 0.24},
                                {"text":"4", "value": 0.32},
                                {"text":"5", "value": 0.40},
                                {"text":"6", "value": 0.48},
                                {"text":"7", "value": 0.56},
                                {"text":"8", "value": 0.64},
                                {"text":"9", "value": 0.72},
                                {"text":"10", "value": 0.80},
                                {"text":"11", "value": 0.88}
                            ],
                            "inputStatus": [
                                {"text":"투입중", "value": 'T'},
                                {"text":"섭외중", "value": 'F'},
                            ],
                            "checkbox:": [],
                            "editedIndex": -1,
                            "skill": {
                                "items": []
                            },
                      },
                    "select": {
                        "job": {
                            "items": [
                                {"text": "전체", "value": null}
                            ]
                        },
                        "skill": {
                            "items": [
                                {"text": "전체", "value": null}
                            ]
                        },
                        "status": {
                            "items": [
                                {"text": "섭외", "value": "A"},
                                {"text": "완료", "value": "C"},
                                {"text": "면접", "value": "I"},
                                {"text": "투입", "value": "P"}
                            ]
                        },
                        "localPlace": {
                            "items": [
                                {"text": "전체", "value": null}
                            ]
                        },
                        "detailLocalPlace": {
                            "items": [
                                {"text": "전체", "value": null}
                            ]
                        }
                    }
                  },
                    "test": null
               };
            },
            "computed": {
            },
            "watch": {
/*                "data.jobType": {
                    "handler": async function (n, o) {
                        let skill = this.user.select.job.items.find(e=>e.value == this.data.jobType);
                        console.log(skill)

                        if(o !== null) {
                            this.data2.skill = [];
                        }
                        this.user.select.skill.items = [];
                        let items = (await ito.api.common.code.getCodeList({
                            "idStartLike": "004",
                            "status": "T",
                            "skill": skill !== undefined ? skill.text : null,
                            "rowSize": 1000000
                        })).data.items.map(e=> ({"text": e.name , "value": e.id}))
                        items = items.filter(e => e.value.length > 5)
                        this.user.select.skill.items.push(...items)

                    }
                },
*/
                "data.localPlace": {
                    "handler": async function(n, o){
                        let detailLocal = this.user.select.localPlace.items.find(e=>e.value == this.data.localPlace);

                        if(o != null){
                            this.data.detailLocalPlace= [];
                        }
                        this.user.select.detailLocalPlace.items=[];
                        let items = (await ito.api.common.code.getCodeList({
                            "idStartLike":"006",
                            "status": "T",
                            "detailLocal": detailLocal !== undefined ? detailLocal.text : null,
                            "rowSize": 1000000
                        })).data.items.map(e => ({"text": e.name, "value": e.id}))
                        items = items.filter(e => e.value.length > 5)
                        this.user.select.detailLocalPlace.items.push(...items)
                        console.log(this.user.select.detailLocalPlace.items)
                    }
                }
            },
            "methods": {
                "loadLocalPlace": async function(){
                    var self = this;
                    let items;
                    items = (await ito.api.common.code.getCodeList({
                        "parentId": "006",
                        "sort": ["ranking, asc"],
                        "rowSize": 1000000
                    })).data.items.map(e => ({"text": e.name, "value": e.id}));
                    self.user.select.localPlace.items.push(...items);
                },
                "loadJobItems": async function() {
                    var self = this;
                    let items;
                    items = (await ito.api.common.code.getCodeList({
                        "parentId": "001",
                        "sort": ["ranking, asc"],
                        "rowSize": 1000000
                    })).data.items.map(e=>({"text": e.name, "value": e.id}));

                    self.user.select.job.items.push(...items);

                },
                "loadSkillItems": async function() {
                    var self = this;
                    let items;
                    let skill = {"text": "개발자", "value": "00101"};

                    items = (await ito.api.common.code.getCodeList({
                        "idStartLike": ["004"],
                        "status": "T",
                        "rowSize": 1000000
                    })).data.items.map(e => ({"text": e.name , "value": e.id}));
                    console.log(items)
                    items = items.filter(e => e.value.length > 5)
                    console.log(items)
                    self.user.select.skill.items.push(...items)

                },
                "execDaumPostcode": function() {
                    var self = this;
                    new daum.Postcode({
                        "onComplete": function(data) {
                            if (data.userSelectedType === 'R') self.data.address = data.roadAddress;
                            else self.data.address = data.jibunAddress;

                            if (data.userSelectedType === 'R') {
                                if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                                    self.data.extraAddress += data.bname;
                                }
                                if (data.buildingName !== '' && data.apartment === 'Y') {
                                    self.data.extraAddress += (self.data.extraAddress !== '' ? ', ' + data.buildingName : data.buildingName);
                                }
                                if (self.data.extraAddress !== '') {
                                    self.data.extraAddress =  ' (' + self.data.extraAddress + ')';
                                }
                            } else {
                                self.data.extraAddress = '';
                            }
                            self.data.postcode = data.zonecode;
                            self.$refs.detailAddress.focus();
                        },
                    }).open();
                },
                "setUserInfo": async function(){
                    let self = this;
                    let personId;
                    let personSkillList,skillList,person;

                    personId = self.$route.query.id;
                    let response = personId ? await ito.api.common.person.getPerson(personId) : null;
                    if(response && response.data){
                        person = response.data;
                        personSkillList = (await ito.api.common.personSkill.getPersonSkillList({personId})).data.items;
                        skillList = [];
                        console.log(personSkillList);
                        this.test = {skill: personSkillList.map(e=> e.skill)};

                        console.log(this.test)
                        person.skill = _.cloneDeep(this.test);
                        console.log(person);
                        self.data = _.cloneDeep(person);
                        self.data2.skill = this.test;
                        self.data.jobType = [person.jobType];
/*                        console.log(self.data2.skill)
                        console.log(self.data)
                        console.log(_.cloneDeep(self.data))
*/

                    }
                },
                "saveUserInfo": function () {
                    var self = this;
                    let person, personSectorList,personLanguageList,
                        sectorList=[], languageList=[];

                  if(this.$refs.form.validate()){
                    return new Promise(function (resolve, reject) {
                        Promise.resolve()
                            .then(function () {
                                    return new Promise( function (resolve, reject) {
                                        Promise.resolve()
                                            .then(async function () {
                                                var data = self.data;
                                                data.skill = self.data2.skill;
                                                data.jobType = String(data.jobType)
                                                if (!data.id) {
/*                                                    await ito.api.app.profile.createProfile({
                                                        "personDto": person,
                                                        "sectorList": sectorList,
                                                        "skillList": person.skill,
                                                        "languageList": languageList
                                                    });
*/													console.log( typeof(data.jobType));
                                                    console.log( data);

                                                    return ito.api.common.person.createPerson(data);
                                                } else {

/*                                                    personSectorList =  (await ito.api.common.personSector.getPersonSectorList({"personId": data.id})).data.items;
                                                    personSectorList.forEach(e => {
                                                        sectorList.push(e.sector)
                                                    })
                                                    console.log(sectorList);

                                                    personLanguageList = (await ito.api.common.personLanguage.getPersonLanguageList({"personId": data.id})).data.items;
                                                    personLanguageList.forEach(e=>{
                                                        languageList.push(e.language);
                                                    });
                                                    console.log(languageList);
                                                    await ito.api.app.profile.modifyProfile({
                                                        "personDto": person,
                                                        "sectorList": sectorList,
                                                        "skillList": person.skill,
                                                        "languageList": languageList
                                                    });
*/
                                                    return ito.api.common.person.modifyPerson(data.id, data);
                                                }
                                            })
                                            .then(function () {
                                                return ito.alert("저장 성공!!!");
                                            })
                                            .then(function () {
                                                self.$router.back();
                                            })
                                            .then(function () { resolve(); });
                                    });
                            })
                            .then(function () { resolve(); });
                    });
                   }
                },


            "validate": function() {
                this.$refs.form.validate()
            },
            "resetValidation": function () {
                this.$refs.form.resetValidation()
            },
            "reset": function () {
                var self = this;
                self.$refs.form.resetValidation()
                   return new Promise(function (resolve, reject) {
                       Promise.resolve()
                           .then(function () {
                           })
                           .then(function () {
                               self.user.dataTable.addressValue="";
                               self.data.address="";
                               self.data.postcode="";
                               self.data.detailAddress="";
                               self.data.name = "";
                               self.data.phoneNumber = "";
                               self.data.jobType = "";
                               self.data2.skill = "";
                               self.data.birthDate = "";
                               self.data.career = "";
                               self.data.pay = "";
                               self.data.inputStatus = "";
                               self.data.workableDay = "";
                               self.data.certificateStatus = "";
                               self.data.localPlace = "";
                               self.data.detailLocalPlace = "";
                               self.data.education = "";
                           })
                           .then(function () { resolve(); });
                    });
             },
         },
         "mounted": async function () {
                var self = this;
                await Promise.all([
                    await self.loadJobItems(),
                    await self.loadSkillItems(),
                    self.loadLocalPlace(),
                    self.setUserInfo()
                ]);
//                console.log(this.data.skill, this.test);
//                this.data.skill = this.test;
//                console.log(this.data.skill, this.test);
         }

       });
    });
});