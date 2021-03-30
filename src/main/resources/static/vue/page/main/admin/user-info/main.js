var MainAdminPage = Vue.component('main-admin-userInfo-page', function (resolve, reject) {
    axios.get("/vue/page/main/admin/user-info/main.html").then(function (response) {
        resolve({
            "template": response.data,
            "data": function () {
                return {
                    "user": {
                        "dialog": false,
                        "panels": {
                            "search": [0],
                            "list": [0]
                        },
                        "dataTable": {
                            "headers": [
                                {"text": "이름", "value": "name"},
                                {"text": "전화번호", "value": "phoneNumber"},
                                {"text": "직종",  "value": "jobType"},
                                {"text": "기술",  "value": "skill"},
                                {"text": "학력", "value": "education"},
                                {"text": "경력",  "value": "career"},
                                {"text": "자격증 유무", "value": "certificateStatus"},
                                {"text": "생년월일(나이)",  "value": "birthDate"},
                                {"text": "희망 급여",  "value": "pay"},
                                {"text": "지역",  "value": "address"},
                                {"text": "투입여부",  "value": "inputStatus"},
                                {"text": "업무 가능일",  "value": "workableDay"}

                            ],
                            "totalRows":0,
                            "items": [],
                            "loading": false,
                            "options": {
                                "page": 1,
                                "itemsPerPage": 10,
                                "sortBy": [],
                                "sortDesc": [],
                                "groupBy": [],
                                "groupDesc": [],
                                "multiSort": true,
                                "mustSort": false
                            },
                            "education": [
                                 {"text":"2년제", "value":"2년제"},
                                 {"text":"3년제", "value":"3년제"},
                                 {"text":"4년제", "value":"4년제"},
                            ],
                            "certificateStatus": [
                                 {"text":"보유", "value":"T"},
                                 {"text":"없음", "value":"F"},
                            ],
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
                            "checkbox:": [],
                        },
                        "query": {
                            "id":0,
                            "name": "",
                            "jobType": "",
                            "career": "",
                            "phoneNumber":"",
                            "education":"",
                            "certificateStatus": "",
                            "pay": "",
                            "address": "",
                            "inputStatus":"",
                            "birthDate":"",
                            "workableDay":"",
                        },
                    },
                    "dialog": {
                        "visible": false,
                        "title": "파일 업로드"
                    }
                };
            },
            "watch": {
                "user.dataTable.options.page": {
                    "handler": async function(n, o) {
                        await this.setUserInfoList();
                    },
                    "deep": true
                },
                "user.dataTable.options.itemsPerPage": {
                    "handler": async function(n, o) {
                        await this.setUserInfoList();
                    },
                    "deep": true
                },
                "user.dataTable.options.sortDesc": {
                    "handler": async function (n, o) {
                        await this.setUserInfoList();
                    },
                    "deep": true
                }
            },
            "methods": {
    //------------------------------------------------------
                "editUserInfo": function(value){
                    this.$router.push({
                        "path": "/main/admin/user-info-form",
                        "query": {
                            "id": value.id
                        }
                       });
                },
                "handlePageChange": function (value){
                    return this.currentPage=value;
                },
                "deleteUserInfoList": async function(items){
                    var self = this;

                    if(items.length == 0){
                        await ito.alert("삭제할 항목이 없습니다.")
                    }else{
                        if(await ito.confirm("삭제하시겠습니까?")){
                           await ito.api.common.person.removePersonList(items.map(e=>e.id));
                           await ito.alert("삭제되었습니다.")
                        }
                        self.setUserInfoList();
                    }

                },
                "setUserInfoList": async function () {
                    var self = this,
                        params = {}, data;
                    params.page = self.user.dataTable.options.page;
                    params.rowSize = self.user.dataTable.options.itemsPerPage;
                    params.name = !_.isEmpty(self.user.query.name) ? self.user.query.name : null;
                    params.jobType = !_.isEmpty(self.user.query.jobType) ? self.user.query.jobType : null;
                    params.career = !_.isEmpty(self.user.query.career) ? self.user.query.career : null;
                    params.pay = !_.isEmpty(self.user.query.pay) ? self.user.query.pay : null;
                    params.address = !_.isEmpty(self.user.query.address) ? self.user.query.address : null;
                    params.inputStatus = !_.isEmpty(self.user.query.inputStatus) ? self.user.query.inputStatus : null;
                    params.education = !_.isEmpty(self.user.query.education) ? self.user.query.education : null;
                    params.certificateStatus = !_.isEmpty(self.user.query.certificateStatus) ? self.user.query.certificateStatus : null;
                    self.user.dataTable.loading = true;
                    data = (await ito.api.common.person.getPersonList(params)).data;
                    console.log(data);
                    self.user.dataTable.items = data.items;
                    self.user.dataTable.totalRows = data.totalRows;
                    self.user.dataTable.items.forEach(e => {
                        e.inputStatus = (e.inputStatus == "T") ? "투입중" : "섭외중"
                        e.certificateStatus = (e.certificateStatus == "T") ? "있음" : "없음"
                        e.career = e.career+"년"
                    });
                    self.user.dataTable.loading = false;
                },
                "search": async function () {
                    var self = this;
                    await self.setUserInfoList();
                },
                "reset": function () {
                    var self = this;
                    return new Promise(function (resolve, reject) {
                        Promise.resolve()
                            .then(function () {
                            })
                            .then(function () {
                                self.user.dataTable.addressValue=""
                                self.user.query.name = "";
                                self.user.query.jobType = "";
                                self.user.query.career = "";
                                self.user.query.pay = "";
                                self.user.query.address = "";
                                self.user.query.inputStatus = "";
                                self.user.query.education = "";
                                self.user.query.certificateStatus = "";
                            })
                            .then(function () {
                                return self.search();
                            })
                            .then(function () { resolve(); });
                    });
                },
                "openDialog": function () {
                    this.dialog.visible = true;
                }
            },
            "mounted": async function () {
                Promise.all([
                    this.setUserInfoList()
                ]);
            }
        });
    });
});