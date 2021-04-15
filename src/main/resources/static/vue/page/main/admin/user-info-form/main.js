var MainAdminFormPage = Vue.component('main-admin-userInfo-form-page', function (resolve, reject) {
    axios.get("/vue/page/main/admin/user-info-form/main.html").then(function (response) {

       resolve({
            "template": response.data,
            "data": function () {
                return {
                    "data": {
                        "id":"",
                        "name": "",
                        "gender": "",
                        "jobType": "",
                        "skill": "",
                        "phoneNumber":"",
                        "career": "",
                        "career1": "",
                        "career2": "",
                        "memo": "",
                        "local": "",
                        "detailLocal": "",
                        "education": "",
                        "certificateStatus": "",
                        "minPay": "",
                        "maxPay": "",
                        "postcode":"",
                        "address": "",
                        "detailAddress":"",
                        "extraAddress":"",
                        "inputStatus":"",
                        "birthDate":"",
                        "workableDay":"",
                        "ratingScore":"",
                        "actualPay":"",
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
                                {"text":"1", "value": 0.01},
                                {"text":"2", "value": 0.02},
                                {"text":"3", "value": 0.03},
                                {"text":"4", "value": 0.04},
                                {"text":"5", "value": 0.05},
                                {"text":"6", "value": 0.06},
                                {"text":"7", "value": 0.07},
                                {"text":"8", "value": 0.08},
                                {"text":"9", "value": 0.09},
                                {"text":"10", "value": 0.10},
                                {"text":"11", "value": 0.11}
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
                        "status":{
                            "items":[
                                {"text": "미정", "value": "N"},
                                {"text": "섭외중", "value": "A"},
                                {"text": "섭외완료", "value": "C"},
                                {"text": "인터뷰", "value": "I"},
                                {"text": "투입중", "value": "P"}
                            ]
                        },
                        "education":{
                            "items":[
                                {"text": "전체", "value": null}
                            ]
                        },
                        "local": {
                            "items": [
                                {"text": "전체", "value": null}
                            ]
                        },
                        "gender":{
                            "items":[
                                {"text": "무관", "value": null},
                                {"text": "남자", "value": "M"},
                                {"text": "여자", "value": "F"}
                            ]
                        },
                        "detailLocal": {
                            "items": [
                                {"text": "전체", "value": null}
                            ]
                        },
                        "ratingScore": {
                            "items":[
                                {"text":"무관", "value":0},
                                {"text":"1점", "value":1},
                                {"text":"2점", "value":2},
                                {"text":"3점", "value":3},
                                {"text":"4점", "value":4},
                                {"text":"5점", "value":5},
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
                "data.local": {
                    "handler": async function(n, o){
                        let detailLocal = this.user.select.local.items.find(e=>e.value == this.data.local);

                        if(o != null && o != ""){
                            this.data.detailLocal= null;
                        }
                        this.user.select.detailLocal.items=[];
                        let items = (await ito.api.common.code.getCodeList({
                            "idStartLike":"006",
                            "status": "T",
                            "detailLocal": detailLocal !== undefined ? detailLocal.text : null,
                            "rowSize": 1000000
                        })).data.items.map(e => ({"text": e.name, "value": e.id}))
                        items = items.filter(e => e.value.length > 5)
                        this.user.select.detailLocal.items.push(...items)
                        console.log(this.user.select.detailLocal.items)
                    }
                }
            },
            "methods": {
                "loadEducation": async function() {
                    var self = this;
                    let items;
                    items = (await ito.api.common.code.getCodeList({
                        "parentId": "007",
                        "sort": ["ranking, asc"],
                        "rowSize": 1000000
                    })).data.items.map(e => ({"text": e.name, "value": e.id}));
                    self.user.select.education.items.push(...items);
                },
                "loadLocalPlace": async function(){
                    var self = this;
                    let items;
                    items = (await ito.api.common.code.getCodeList({
                        "parentId": "006",
                        "sort": ["ranking, asc"],
                        "rowSize": 1000000
                    })).data.items.map(e => ({"text": e.name, "value": e.id}));
                    self.user.select.local.items.push(...items);
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
                        console.log(person);
                        self.data = _.cloneDeep(person);
                        console.log(self.data);
                        self.data.career1 = Number(Math.floor(self.data.career))
                        self.data.career2 = (((self.data.career) * 100)%100) * (0.01);
                        self.data2.skill = person.skill
                        self.data.jobType = [person.jobType];
                        console.log(self.data);
                    }
                },
                "saveUserInfo": function () {
                    var self = this;
                    let person, personSectorList, personLanguageList,
                        sectorList=[], languageList=[];

                  if(this.$refs.form.validate()){
                    return new Promise(function (resolve, reject) {
                        Promise.resolve()
                            .then(function () {
                                    return new Promise( function (resolve, reject) {
                                        Promise.resolve()
                                            .then(async function () {
                                                var data = self.data;
                                                data.skill = String(self.data2.skill);
                                                data.career = data.career1 + data.career2;
                                                data.jobType = String(data.jobType)
                                                data.detailLocal = String(data.detailLocal)
                                                data.jobType = String(data.jobType)
                                                data.days = Number(data.career1)*365 + (Number(data.career2)*3000);
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
                               self.data.postcode=null;
                               self.data.address=null;
                               self.data.detailAddress=null;
                               self.data.name = null;
                               self.data.phoneNumber = null;
                               self.data.jobType = null;
                               self.data2.skill = null;
                               self.data.birthDate = null;
                               self.data.career1 = null;
                               self.data.career2 = null;
                               self.data.inputStatus = null;
                               self.data.workableDay = null;
                               self.data.certificateStatus = null;
                               self.data.local = null;
                               self.data.detailLocal = null;
                               self.data.education = null;
                               self.data.rank = null;
                               self.data.minPay = null;
                               self.data.maxPay = null;
                           })
                           .then(function () { resolve(); });
                    });
             },
         },
         "mounted": async function () {
                var self = this;
                await Promise.all([
                    self.setUserInfo(),
                    self.loadJobItems(),
                    self.loadSkillItems(),
                    self.loadLocalPlace(),
                    self.loadEducation()
                ]);
//                console.log(this.data.skill, this.test);
//                this.data.skill = this.test;
//                console.log(this.data.skill, this.test);
         }
       });
    });
});