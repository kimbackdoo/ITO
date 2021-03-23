var MainAdminFormPage = Vue.component('main-admin-userInfo-form-page', function (resolve, reject) {
    axios.get("/vue/page/main/admin/user-info-form/main.html").then(function (response) {

       resolve({
            "template": response.data,
            "data": function () {
                return {
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
                            "loading": false,
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
                            "editedItem": {
                                "id":"",
                                "name": "",
                                "jobType": "",
                                "phoneNumber":"",
                                "career": "",
                                "education": "",
                                "ccertificateStatus": "",
                                "pay": "",
                                "address": "",
                                "inputStatus":"",
                                "birthDate":"",
                                "workableDay":"",
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
                    "query": {
                        "id":"",
                    },
                  }
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
            },
            "methods": {
                "setUserInfo": function () {
                    var self = this;
                    return new Promise(function (resolve, reject) {
                        Promise.resolve()
                            .then(function () {
                                var id = self.$route.query.id;
                                console.log("파라미터 id"+id);
                                return id ? ito.api.common.person.getPerson(id) : null;
                            })
                            .then(function (response) {
                                if (response && response.data) {
                                    self.user.dataTable.editedItem = response.data;
                                }
                            })
                            .then(function () { resolve(); });
                    });
                },

                "saveUserInfo": function () {
                    var self = this;
                  if(this.$refs.form.validate()){
                    return new Promise(function (resolve, reject) {
                        Promise.resolve()
                            .then(function () {
                                    return new Promise(function (resolve, reject) {
                                        Promise.resolve()
                                            .then(function () {
                                                var data = self.user.dataTable.editedItem;
                                                if (!data.id) {
                                                    return ito.api.common.person.createPerson(data);
                                                } else {
                                                    console.log("수정되는 id 값 === " +  data.id);
                                                    console.log("수정,입력되는 전화번호 값  ===" + data.phoneNumber)
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
                               self.user.dataTable.editedItem.name = "";
                               self.user.dataTable.editedItem.phoneNumber = "";
                               self.user.dataTable.editedItem.jobType = "";
                               self.user.dataTable.editedItem.skill = "";
                               self.user.dataTable.editedItem.birthDate = "";
                               self.user.dataTable.editedItem.career = "";
                               self.user.dataTable.editedItem.pay = "";
                               self.user.dataTable.editedItem.inputStatus = "";
                               self.user.dataTable.editedItem.workableDay = "";
                               self.user.dataTable.editedItem.certificateStatus = "";
                               self.user.dataTable.editedItem.education = "";
                           })
                           .then(function () { resolve(); });
                    });
             },
         },
         "mounted": function () {
                var self = this;
                Promise.resolve()
                    .then(self.setUserInfo);
         }

       });
    });
});