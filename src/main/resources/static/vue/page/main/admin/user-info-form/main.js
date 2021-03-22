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
                            "number": [
                                (v) =>
                                    !!v || this.$vuetify.lang.t('$vuetify.rule.required', ['number'])
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
                                {"text": "전화번호", "value": "number",},
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
                                {"text":"투입중", "value": "투입중"},
                                {"text":"섭외중", "value": "섭외중"},
                            ],
                            "checkbox:": [],
                            "editedIndex": -1,
                            "editedItem": {
                                "id":"",
                                "name": "",
                                "jobType": "",
                                "career": "",
                                "pay": "",
                                "address": "",
                                "inputStatus":"",
                                "birthDate":"",
                                "workableDay":"",
                            },
                            "defaultItem": {
                                "id":"",
                                "name": "",
                                "jobType": "",
                                "career": "",
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
                        "name": "",
                        "jobType": "",
                        "career": "",
                        "pay": "",
                        "address": "",
                        "inputStatus":"",
                        "birthDate":"",
                        "workableDay":"",
                    },
                  }
               };
            },
            "computed": {
                formTitle() {
                    return this.user.dataTable.editedIndex === -1 ? 'New Item' : '정보 수정하기'
                },

            },
            "watch": {
                dialog (val){
                    return val || this.close()
                },
                dialogDelete(val){
                    return val || this.closeDelete()
                },
                "user.dataTable.addressValue":{
                    "handler": function(value){
                        this.user.dataTable.addressSelect=this.user.dataTable.addressIndex[value];
                    }
                },

            },
            "methods": {

               "createUserInfo": function (data) {
                    return axios({
                        "url": "/api/common/user-info",
                        "method": "post",
                        "data": data
                    });
                },

                "modifyUserInfo": function (id, data) {
                    return axios({
                        "url": "/api/common/user-info/" + id,
                        "method": "put",
                        "data": data
                    });
                },
                "getUserInfo": function (id) {
                    return axios({
                        "url": "/api/common/user-info/" + id,
                        "method": "get"
                    });
                },
                "setUserInfo": function () {
                    var self = this;
                    return new Promise(function (resolve, reject) {
                        Promise.resolve()
                            .then(function () {
                                var id = self.$route.query.id;
                                console.log("파라미터 id"+id);
                                return id ? self.getUserInfo(id) : null;
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
                                                    return self.createUserInfo(data);
                                                } else {
                                                    console.log("수정되는 id 값     "+ data.id);
                                                    return self.modifyUserInfo(data.id, data);
                                                }
                                            })
                                            .then(function () {
                                                return util.alert({"text": "Saved successfully!"});
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



               //------------------삭제 관련 -----------------------------------------

                "removeUserInfo": function (id) {
                    return axios({
                        "url": "/api/common/user-info/" + id,
                        "method": "delete"
                    });
                },



                "deleteUserInfo": function () {
                    var self = this;
                    return new Promise(function (resolve, reject) {
                        Promise.resolve()
                            .then(function () {
                                return util.confirm({"text": "삭제하시겠습니까?"});
                            })
                            .then(function (isConfirmed) {
                                if (isConfirmed) {
                                    return new Promise(function (resolve, reject) {
                                        Promise.resolve()
                                            .then(function () {
                                                return self.removeUserInfo(self.user.dataTable.editedItem.id);
                                            })
                                            .then(function () {
                                                return util.alert({"text": "Deleted successfully!"});
                                            })
                                            .then(function () {
                                                self.$router.replace("/user-info");
                                            })
                                            .then(function () { resolve(); });
                                    });
                                }
                            })
                            .then(function () { resolve(); });
                    });
                },

            //메소드

            //항목 선택 후 -> 삭제하기
             // 보류
/*            "handleClick": function() {

            },
*/
            //DB에 저장


/*           "alert": function(option) {
                  return Vuex.store.dispatch("app/alert", option);
            },
           "confirm": function(option) {
                  return Vuex.store.dispatch("app/confirm", option);
            },
*/            "editItem": function(item){
                this.user.dataTable.editedIndex = this.user.dataTable.items.indexOf(item)
                this.user.dataTable.editedItem = Object.assign({}, item)
//                this.user.dialog = true
            },

            "deleteItem": function(item){
                this.user.dataTable.editedIndex = this.user.dataTable.items.indexOf(item)
                this.user.dataTable.editItem = Object.assign({}, item)
                this.user.dialogDelete = true
            },

            //삭제하기
            "deleteItemConfirm": function() {
                this.user.dataTable.items.splice(this.user.dataTable.editedIndex, 1)
                this.closeDelete()
            },
            "close": function(){
                this.user.dialog = false
                this.$nextTick(() => {
                    this.user.dataTable.editedItem = Object.assign({}, this.user.dataTable.defaultItem)
                    this.user.dataTable.editedIndex = -1
                })
            },

            //삭제 취소
            "closeDelete": function() {
                this.user.dialogDelete = false
                this.$nextTick(() => {
                    this.user.dataTable.editedItem = Object.assign({}, this.user.dataTable.defaultItem)
                    this.user.dataTable.editedIndex = -1
                })
            },
            "validate": function() {
                this.$refs.form.validate()
            },
            "resetValidation": function () {
                this.$refs.form.resetValidation()
            },
            "save": function() {
                if(this.$refs.form.validate()){
                    if(this.user.dataTable.editedIndex > -1){
                            Object.assign(this.user.dataTable.items[this.user.dataTable.editedIndex], this.user.dataTable.editedItem)
                    }else{
                        this.user.dataTable.items.push(this.user.dataTable.editedItem)
                    }
                    this.close();
                }
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
                               self.user.dataTable.editedItem.number = "";
                               self.user.dataTable.editedItem.jobType = "";
                               self.user.dataTable.editedItem.skill = "";
                               self.user.dataTable.editedItem.birthDate = "";
                               self.user.dataTable.editedItem.career = "";
                               self.user.dataTable.editedItem.pay = "";
                               self.user.dataTable.editedItem.inputStatus = "";
                               self.user.dataTable.editedItem.workableDay = "";
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