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
                        "userData": {
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
                            "headers": [
                                {"text": "이름", "sortable": false, "value": "name"},
                                {"text": "전화번호", "sortable": false, "value": "number"},
                                {"text": "직종", "sortable": false, "value": "jobType"},
                                {"text": "기술", "sortable": false, "value": "skill"},
                                {"text": "생년월일(나이)", "sortable": false, "value": "birthDate"},
                                {"text": "경력", "sortable": false, "value": "career"},
                                {"text": "희망 급여", "sortable": false, "value": "pay"},
                                {"text": "지역", "sortable": false, "value": "address"},
                                {"text": "투입여부", "sortable": false, "value": "inputStatus"},
                                {"text": "업무 가능일", "sortable": false, "value": "workableDay"},
                                {"text": "수정 / 삭제", "sortable":false, "value": "actions"}
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
                            "addr1": ["서울특별시",'대전광역시'],
                            "address": ["강서구","은평구","광진구","서초구","구로구"],
                            "ad2": ["중구","서구","대덕구","유성구","동구"],
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
                    }
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
            },
            "methods": {
/*                "createUserInfo": function (data) {
                    return axios({
                        "url": "/api/common/people",
                        "method": "post",
                        "data": data
                    });
                },
                "modifyUserInfo": function (id, data) {
                    return axios({
                        "url": "/api/common/people/" + id,
                        "method": "put",
                        "data": data
                    });
                },
                "removeUserInfo": function (id) {
                    return axios({
                        "url": "/api/common/people/" + id,
                        "method": "delete"
                    });
                },
                "getUserInfo": function (id) {
                    return axios({
                        "url": "/api/common/people/" + id,
                        "method": "get"
                    });
                },
                "setUserInfo": function () {
                    var self = this;
                    return new Promise(function (resolve, reject) {
                        Promise.resolve()
                            .then(function () {
                                var id = self.$route.params.id;
                                return id ? self.getPerson(id) : null;
                            })
                            .then(function (response) {
                                if (response && response.data) {
                                    self.person = response.data;
                                }
                            })
                            .then(function () { resolve(); });
                    });
                },
                "saveUserInfo": function () {
                    var self = this;
                    return new Promise(function (resolve, reject) {
                        Promise.resolve()
                            .then(function () {
                                return util.confirm({"text": "Are you sure want to save person?"});
                            })
                            .then(function (isConfirmed) {
                                if (isConfirmed) {
                                    return new Promise(function (resolve, reject) {
                                        Promise.resolve()
                                            .then(function () {
                                                var data = self.person;
                                                if (!data.id) {
                                                    return self.createPerson(data);
                                                } else {
                                                    return self.modifyPerson(data.id, data);
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
                                }
                            })
                            .then(function () { resolve(); });
                    });
                },
                "deletePerson": function () {
                    var self = this;
                    return new Promise(function (resolve, reject) {
                        Promise.resolve()
                            .then(function () {
                                return util.confirm({"text": "Are you sure want to delete person?"});
                            })
                            .then(function (isConfirmed) {
                                if (isConfirmed) {
                                    return new Promise(function (resolve, reject) {
                                        Promise.resolve()
                                            .then(function () {
                                                return self.removePerson(self.person.id);
                                            })
                                            .then(function () {
                                                return util.alert({"text": "Deleted successfully!"});
                                            })
                                            .then(function () {
                                                self.$router.replace("/people");
                                            })
                                            .then(function () { resolve(); });
                                    });
                                }
                            })
                            .then(function () { resolve(); });
                    });
                }
*/
            //메소드
            "handleClick": function() {

            },
            "editItem": function(item){
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
             }


/*            "mounted": function () {
                var self = this;
                Promise.resolve()
                    .then(self.setPerson);
            }
*/
         }
       });

    });

});