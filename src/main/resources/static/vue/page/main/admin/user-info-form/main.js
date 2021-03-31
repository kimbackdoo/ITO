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
                                "skill": null,
                                "phoneNumber":"",
                                "career": "",
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
                            "headers": [
                                {"text": "이름", "value": "name",},
                                {"text": "전화번호", "value": "phoneNumber",},
                                {"text": "직종",  "value": "jobType",},
                                {"text": "기술", "value": "skill",},
                                {"text": "생년월일(나이)",  "value": "birthDate",},
                                {"text": "경력", "value": "career",},
                                {"text": "희망 급여",  "value": "pay",},
                                {"text": "지역",  "value": "address",},
                                {"text": "투입여부",  "value": "inputStatus",},
                                {"text": "업무 가능일",  "value": "workableDay",},
                                {"text": "수정 / 삭제", "sortable":false, "value": "actions",},
                            ],
                            "items": [],
                            "loading": false,
                            "serverItemsLength": 0,
                            "page": 1,
                            "itemsPerPage": 10,
                            "jobTypeItems": [
                                 {"text":"개발자", "value":"개발자"},
                                 {"text":"기획자", "value":"기획자"},
                                 {"text":"퍼블리셔", "value":"퍼블리셔"},
                                 {"text":"디자이너", "value":"디자이너"}
                             ],
                            "education": [
                                 {"text":"2년제", "value":"2년제"},
                                 {"text":"3년제", "value":"3년제"},
                                 {"text":"4년제", "value":"4년제"},
                            ],
                            "certificateStatus": [
                                 {"text":"보유", "value":"T"},
                                 {"text":"없음", "value":"F"},
                            ],
                            "address": [
                                {"text":"서울특별시", "value":0},
                                {"text":"경기도", "value":1},
                                {"text":"대전광역시", "value":2},
                            ],
                            "addressValue": "",
                            "addressIndex": [
                                ["강서구","은평구","광진구","서초구","구로구"],
                                ["김포시","부천","광명","시흥","안양","과천","성남","하남","수원","광주"],
                                ["중구","서구","대덕구","유성구","동구"],
                            ],
                            "addressSelect": [],
                            "career": [
                                {"text":"신입", "value": "0"},
                                {"text":"1년", "value": "1"},
                                {"text":"2년", "value": "2"},
                                {"text":"3년", "value": "3"},
                                {"text":"4년", "value": "4"},
                                {"text":"5년", "value": "5"},
                                {"text":"6년", "value": "6"},
                                {"text":"7년", "value": "7"},
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
                            "itemsPerPageItems": [
                                {"text":"3", "value":3},
                                {"text":"5", "value": 5},
                                {"text": "10", "value": 10},
                                {"text": "20", "value": 20},
                                {"text": "30", "value": 30},
                                {"text": "40", "value": 40},
                                {"text": "50", "value": 50}
                            ]
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
                    }
                  },
                    "test": null
               };
            },
            "computed": {
/*                formTitle() {
                    return this.user.dataTable.editedIndex === -1 ? 'New Item' : '정보 수정하기'
                },
*/
            },
            "watch": {
                "user.dataTable.addressValue":{
                    "handler": function(value){
                        this.user.dataTable.addressSelect=this.user.dataTable.addressIndex[value];
                    }
                },
                "user.dataTable.editedItem.jobType": {
                    "handler": async function (n, o) {
                        let skill = this.user.select.job.items.find(e=>e.value == this.data.jobType);

                        if(o !== null) {
                            this.data.skill = [];
                        }
                        this.user.dataTable.skill.items = [];
                        this.user.dataTable.skill.items = (await ito.api.common.code.getCodeList({
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
                "user.dataTable.editedItem.skill": {
                    "handler": function (n, o) {
                        if(n.length > 3) {
                            ito.alert("3개 이하만 가능합니다.");
                            n.pop();
                            return;
                        }
                    }
                },

            },
            "methods": {
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
                        console.log(this.test);
                        Object.assign(person, this.test);
                        self.data = _.cloneDeep(person);
                        console.log(self.data)
                        console.log(person, skillList);
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
                                                person=data;
                                                console.log("data.id    : "  +data.id)


                                                if (!data.id) {

                                                    await ito.api.app.profile.createProfile({
                                                        "personDto": person,
                                                        "sectorList": sectorList,
                                                        "skillList": person.skill,
                                                        "languageList": languageList
                                                    });
                                                    return ito.api.common.person.createPerson(data);
                                                } else {

                                                    personSectorList =  (await ito.api.common.personSector.getPersonSectorList({"personId": data.id})).data.items;
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
                               self.data.skill = "";
                               self.data.birthDate = "";
                               self.data.career = "";
                               self.data.pay = "";
                               self.data.inputStatus = "";
                               self.data.workableDay = "";
                               self.data.certificateStatus = "";
                               self.data.education = "";
                           })
                           .then(function () { resolve(); });
                    });
             },
         },
         "mounted": async function () {
                var self = this;
                await Promise.all([
                    self.setUserInfo(),
                    self.loadJobItems()
                ]);
//                console.log(this.data.skill, this.test);
//                this.data.skill = this.test;
//                console.log(this.data.skill, this.test);
         }

       });
    });
});