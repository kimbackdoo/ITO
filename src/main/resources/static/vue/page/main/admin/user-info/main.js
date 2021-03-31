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
                                {"text": "이름", "value": "name",cellClass:"text-truncate"},
                                {"text": "전화번호", "value": "phoneNumber", cellClass:"text-truncate"},
                                {"text": "직종",  "value": "jobType", cellClass:"text-truncate"},
                                {"text": "기술",  "value": "skill",cellClass:"text-truncate"},
                                {"text": "학력", "value": "education", cellClass:"text-truncate"},
                                {"text": "경력",  "value": "career", cellClass:"text-truncate"},
                                {"text": "자격증 유무", "value": "certificateStatus",cellClass:"text-truncate"},
                                {"text": "생년월일(나이)",  "value": "birthDate", cellClass:"text-truncate"},
                                {"text": "희망 급여",  "value": "pay", cellClass:"text-truncate"},
                                {"text": "지역",  "value": "address", cellClass:"text-truncate"},
                                {"text": "투입여부",  "value": "inputStatus", cellClass:"text-truncate"},
                                {"text": "업무 가능일",  "value": "workableDay", cellClass:"text-truncate"}

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
                            "checkbox:": [],
                        },
                        "query": {
                            "id":0,
                            "name": null,
                            "job": null,
                            "skill":null,
                            "career1": null,
                            "career2": null,
                            "career": null,
                            "phoneNumber":null,
                            "education":null,
                            "certificateStatus": null,
                            "pay": null,
                            "address": null,
                            "inputStatus":null,
                            "birthDate":null,
                            "workableDay":null,
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
                },

                "user.query.career1": {
                    "handler": function (){
                        var self = this;
                        let data1 = self.user.query.career1;
                        let data2 = self.user.query.career2;
                        var career = (data1 + data2).toString
                        self.user.query.career=career;
                    }
                },
                "user.query.career2": {
                    "handler": function (){
                        var self = this;
                        let data1 = self.user.query.career1;
                        let data2 = self.user.query.career2;
                        var career = (data1 + data2).toString
                        self.user.query.career=career;
                    }
                },
                "user.query.job": {
                    "handler": async function () {
                        var self = this;
                        let items,
                            skill = self.select.job.items.find(e=>e.value == self.user.query.job);

                        self.select.skill.items = [];
                        items = (await ito.api.common.code.getCodeList({
                            "idStartLike": "004",
                            "status": "T",
                            "skill": skill !== undefined ? skill.text : null,
                            "rowSize": 1000000
                        })).data.items.map(e=>({"text": e.name, "value": e.id}));
                        items = items.filter(e=> {
                            if(e.value.startsWith("00401")) return e.value.length > 7;
                            else return e.value.length > 5;
                        });
                        self.select.skill.items.push(
                            {"text": "전체", "value": null},
                            ...items
                        );
                        self.user.query.skill = [];
                    }
                },
            },
            "methods": {
                "editUserInfo": function(value){
                    this.$router.push({
                        "path": "/main/admin/user-info-form",
                        "query": {
                            "id": value.id
                        }
                       });
                },
                "addUserInfo": function(value){
                    this.$router.push({
                        "path": "/main/admin/user-info-form",
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
                "loadJobItems": async function() {
                    var self = this;
                    let items;
                    items = (await ito.api.common.code.getCodeList({
                        "parentId": "001",
                        "sort": ["ranking, asc"],
                        "rowSize": 1000000
                    })).data.items.map(e=>({"text": e.name, "value": e.id}));
                    self.select.job.items.push(...items);
                },
                "setUserInfoList": async function () {
                    var self = this
                    var careerValue = String(self.user.query.career1 + self.user.query.career2);
                    var params = {}, data;
                    params.page = self.user.dataTable.options.page;
                    params.rowSize = self.user.dataTable.options.itemsPerPage;
                    params.name = !_.isEmpty(self.user.query.name) ? self.user.query.name : null;
                    params.jobType = !_.isEmpty(self.user.query.jobType) ? self.user.query.jobType : null;
                    params.career = !_.isEmpty(careerValue) ? careerValue : null;
                    params.pay = !_.isEmpty(self.user.query.pay) ? self.user.query.pay : null;
                    params.address = !_.isEmpty(self.user.query.address) ? self.user.query.address : null;
                    params.inputStatus = !_.isEmpty(self.user.query.inputStatus) ? self.user.query.inputStatus : null;
                    params.education = !_.isEmpty(self.user.query.education) ? self.user.query.education : null;
                    params.certificateStatus = !_.isEmpty(self.user.query.certificateStatus) ? self.user.query.certificateStatus : null;

                    params.sort=ito.util.sort(self.user.dataTable.options.sortBy, self.user.dataTable.options.sortDesc);

                    self.user.dataTable.loading = true;
                    console.log("params.career 값 :  "+ params.career);
                    data = (await ito.api.common.person.getPersonList(params)).data;
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
                                self.user.query.career1 = "";
                                self.user.query.career2 = "";
                                self.user.query.pay = "";
                                self.user.query.address = "";
                                self.user.query.inputStatus = "";
                                self.user.query.education = "";
                                self.user.query.certificateStatus = "";
                                self.user.query.job = "";
                                self.user.query.skill = "";
                            })
                            .then(function () {
                                return self.search();
                            })
                            .then(function () { resolve(); });
                    });
                },
                "openDialog": function () {
                    this.dialog.visible = true;
                },
                "fileUpload": async function (dataUpload) {
                    var form = new FormData();
                    store.commit("app/SET_LOADING", true);
                    form.append("file" , dataUpload.selectedFile);

                    var returnType = await ito.api.app.upload.person(form);
                    store.commit("app/SET_LOADING", false);

                    if(returnType.data.returnVal != 'SUCCESS'){
                        await ito.alert(returnType.data.returnMsg);
                    }else{
                        await ito.alert(returnType.data.returnMsg);
                        this.dialog.visible = false;
                        this.setUserInfoList();
                        this.loadJobItems();
                    }
                }
            },
            "mounted": async function () {
                Promise.all([
                    this.setUserInfoList(),
                    this.loadJobItems()
                ]);
            }
        });
    });
});