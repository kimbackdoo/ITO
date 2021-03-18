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
                        "dialog": false,
                        "dialogDelete": false,
                        "user": {
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
                            "items": [
                                {name: '유수민', number:"01010101010",job_type:"개발자",skill:"JAVA",birthDate:"11111",career:"1",pay:"1",address:"주우소",inputStatus:"투입중",workableDate:"3"},
                                {name: "김희민", number:"01010101010",job_type:"개발자",skill:"JAVA",birthDate:"11111",career:"1",pay:"1",address:"주우소",inputStatus:"투입중",workableDate:"3"},
                                {name: "새대갈1", number:"01010101010",job_type:"개발자",skill:"JAVA",birthDate:"11111",career:"1",pay:"1",address:"주우소",inputStatus:"투입중",workableDate:"3"},
                                {name: "새대갈2", number:"01010101010",job_type:"개발자",skill:"JAVA",birthDate:"11111",career:"1",pay:"1",address:"주우소",inputStatus:"투입중",workableDate:"3"},
                                {name: "새대갈3", number:"01010101010",job_type:"개발자",skill:"JAVA",birthDate:"11111",career:"1",pay:"1",address:"주우소",inputStatus:"투입중",workableDate:"3"},
                                {name: "새대갈4", number:"01010101010",job_type:"개발자",skill:"JAVA",birthDate:"11111",career:"1",pay:"1",address:"주우소",inputStatus:"투입중",workableDate:"3"},
                                {name: "새대갈5", number:"01010101010",job_type:"개발자",skill:"JAVA",birthDate:"11111",career:"1",pay:"1",address:"주우소",inputStatus:"투입중",workableDate:"3"},
                                {name: "새대갈6", number:"01010101010",job_type:"개발자",skill:"JAVA",birthDate:"11111",career:"1",pay:"1",address:"주우소",inputStatus:"투입중",workableDate:"3"},
                                {name: "새대갈7", number:"01010101010",job_type:"개발자",skill:"JAVA",birthDate:"11111",career:"1",pay:"1",address:"주우소",inputStatus:"투입중",workableDate:"3"},
                                {name: "새대갈8", number:"01010101010",job_type:"개발자",skill:"JAVA",birthDate:"11111",career:"1",pay:"1",address:"주우소",inputStatus:"투입중",workableDate:"3"},
                                {name: "새대갈9", number:"01010101010",job_type:"개발자",skill:"JAVA",birthDate:"11111",career:"1",pay:"1",address:"주우소",inputStatus:"투입중",workableDate:"3"},
                                {name: "새대갈10", number:"01010101010",job_type:"개발자",skill:"JAVA",birthDate:"11111",career:"1",pay:"1",address:"주우소",inputStatus:"투입중",workableDate:"3"},
                                {name: "새대갈11", number:"01010101010",job_type:"개발자",skill:"JAVA",birthDate:"11111",career:"1",pay:"1",address:"주우소",inputStatus:"투입중",workableDate:"3"},
                                {name: "새대갈12", number:"01010101010",job_type:"개발자",skill:"JAVA",birthDate:"11111",career:"1",pay:"1",address:"주우소",inputStatus:"투입중",workableDate:"3"},
                                {name: "새대갈13", number:"01010101010",job_type:"개발자",skill:"JAVA",birthDate:"11111",career:"1",pay:"1",address:"주우소",inputStatus:"투입중",workableDate:"3"},
                                {name: "새대갈14", number:"01010101010",job_type:"개발자",skill:"JAVA",birthDate:"11111",career:"1",pay:"1",address:"주우소",inputStatus:"투입중",workableDate:"3"},
                                {name: "새대갈15", number:"01010101010",job_type:"개발자",skill:"JAVA",birthDate:"11111",career:"1",pay:"1",address:"주우소",inputStatus:"투입중",workableDate:"3"},
                                {name: "새대갈16", number:"01010101010",job_type:"개발자",skill:"JAVA",birthDate:"11111",career:"1",pay:"1",address:"주우소",inputStatus:"투입중",workableDate:"3"},
                                {name: "새대갈17", number:"01010101010",job_type:"개발자",skill:"JAVA",birthDate:"11111",career:"1",pay:"1",address:"주우소",inputStatus:"투입중",workableDate:"3"},
                                {name: "새대갈18", number:"01010101010",job_type:"개발자",skill:"JAVA",birthDate:"11111",career:"1",pay:"1",address:"주우소",inputStatus:"투입중",workableDate:"3"}

                            ],
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
                            "address": ["서울특별시",'대전광역시'],
                            "ad1": ["강서구","은평구","광진구","서초구","구로구"],
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
                    return this.editedIndex === -1 ? 'New Item' : 'Edit Item'
                },
            },
            "watch": {
                dialog (val){
                    val || this.close()
                },
                dialogDelete(val){
                    val || this.closeDelete()
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
            "editItem": function(item){
                this.editedIndex = this.items.indexOf(item)
                this.editedItem = Object.assign({}, item)
                this.dialog = true
            },

            "deleteItem": function(item){
                this.editedIndex = this.items.indexOf(item)
                this.editItem = Object.assign({}, item)
            },

            "deleteItemConfirm": function() {
                this.items.splice(this.editedIndex, 1)
                this.closeDelete()
            },
            "close": function(){
                this.dialog = false
                this.$nextTick(() => {
                    this.editedItem = Object.assign({}, this.defaultItem)
                    this.editedIndex = -1
                })
            },
            "closeDelete": function() {
                this.dialogDelete = false
                this.$nextTick(() => {
                    this.editedItem = Object.assign({}, this.defaultItem)
                    this.editedIndex = -1
                })
            },
            "save": function() {
                if(this.editedIndex > -1){
                    Object.assign(this.items[this.editedIndex], this.editedItem)
                }else{
                    this.items.push(this.editedItem)
                }
                this.close()
            },


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