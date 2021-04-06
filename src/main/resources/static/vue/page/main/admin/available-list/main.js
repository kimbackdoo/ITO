var MainAdminAvailableListPage
MainAdminAvailableListPage = Vue.component('main-admin-availableList-page', async function (resolve) {
    resolve({
        "template": (await axios.get("/vue/page/main/admin/available-list/main.html")).data,
        "data": function () {
            return {
                "user": {
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
                            {"text": "희망 월급여",  "value": "pay", cellClass:"text-truncate"},
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
                    },
                    "query": {
                        "name": null,
                        "jobType": null,
                        "skill": null,
                        "careerYear": null,
                        "careerMonth": null,
                        "career": null,
                        "local": null,
                        "detailLocal": null,
                        "phoneNumber": null,
                        "education": [],
                        "certificateStatus": null,
                        "minPay": null,
                        "address": null,
                        "inputStatus": null,
                        "birthDate": null,
                        "workableDay": null,
                    },
                },
                "select": {
                    "job": {
                        "items": [
                            {"text": "전체", "value": null},
                        ]
                    },
                    "status": {
                        "items": [
                            {"text": "섭외", "value": "A"},
                            {"text": "완료", "value": "C"},
                            {"text": "면접", "value": "I"},
                            {"text": "투입", "value": "P"},
                        ]
                    },
                    "education": {
                        "items": [
                            {"text": "전체", "value": null},
                        ]
                    },
                    "careerYear": {
                        "items": [
                            {"text":"1년미만", "value": 0},
                        ]
                    },
                    "careerMonth": {
                        "items": []
                    },
                    "certificateStatus": {
                        "items": [
                            {"text":"보유", "value":"T"},
                            {"text":"없음", "value":"F"},
                        ]
                    },
                    "local": {
                        "items": []
                    },
                    "detailLocal": {
                        "items": []
                    },
                },
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
            "user.query.local": {
                "handler": async function(value){
                    let items,
                        local = this.select.local.items.find(e=> e.value == this.user.query.local);

                    console.log(local);

                    this.select.detailLocal.items=[];
                    items = (await ito.api.common.code.getCodeList({
                        "idStartLike": value,
                        "status": "T",
                        "detailLocal": local !== undefined ? local.text : null,
                        "rowSize": 1000000
                    })).data.items.map(e => ({"text": e.name, "value": e.id}))

                    console.log(items);

                    items = items.filter(e => e.value.length > 5)
                    this.select.detailLocal.items.push(...items);
                }
            }
        },
        "methods": {
            "init": async function() {
                await this.loadJobItems();
                await this.loadEducation();
                await this.loadLocal();
                await this.setUserInfoList();
            },
            "loadJobItems": async function() {
                let items;
                items = (await ito.api.common.code.getCodeList({
                    "parentId": "002",
                    "sort": ["ranking, asc"],
                    "rowSize": 1000000
                })).data.items.map(e=>({"text": e.name, "value": e.id}));
                this.select.job.items.push(...items);
            },
            "loadEducation": async function() {
                let items;
                items = (await ito.api.common.code.getCodeList({
                    "parentId": "007",
                    "sort": ["ranking, asc"],
                    "rowSize": 1000000
                })).data.items.map(e => ({"text": e.name, "value": e.id}));
                this.select.education.items.push(...items);
            },
            "loadLocal": async function(){
                let items;
                items = (await ito.api.common.code.getCodeList({
                    "parentId": "006",
                    "sort": ["ranking, asc"],
                    "rowSize": 1000000
                })).data.items.map(e => ({"text": e.name, "value": e.id}));
                this.select.local.items.push(...items);
            },
            "setUserInfoList": async function () {
                let self = this, pay, personList, items,
                    careerValue = String(self.user.query.careerYear + self.user.query.careerMonth);

                self.user.dataTable.loading = true;
                personList = (await ito.api.common.person.getPersonList({
                    "page": self.user.dataTable.options.page,
                    "rowSize": self.user.dataTable.options.itemsPerPage,
                    "sort": ito.util.sort(self.user.dataTable.options.sortBy, self.user.dataTable.options.sortDesc),

                    "name": self.user.query.name,
                    "jobType": self.user.query.jobType,
                    "career": careerValue,
                    "minPay": self.user.query.minPay,
                    "local": self.user.query.local,
                    "detailLocal": self.user.query.detailLocal,
                    "inputStatus": self.user.query.inputStatus,
                    "education": self.user.query.education,
                    "certificateStatus": self.user.query.certificateStatus,
                })).data;

                items = (await ito.api.common.code.getCodeList({
                    "parentId": "002",
                    "sort": ["ranking, asc"],
                    "rowSize": 1000000
                })).data.items.map(e=>({"text": e.name, "value": e.id}));

                self.user.dataTable.totalRows = personList.totalRows;
                self.user.dataTable.items = personList.items;
                self.user.dataTable.items.forEach(e => {
                    for(var i=0; i<items.length; i++) {
                        if(e.jobType === items[i].value) {
                            e.jobType = items[i].text;
                            break;
                        }
                    }

                    e.inputStatus = (e.inputStatus == "T") ? "투입중" : "섭외중"
                    e.certificateStatus = (e.certificateStatus == "T") ? "있음" : "없음"
                    e.career = e.career+"년"
                    e.pay = String(e.minPay) +" ~ " +String(e.maxPay);
                });
                self.user.dataTable.loading = false;
            },
            "search": async function () {
                if(this.user.dataTable.options.page !== 1) {
                    this.user.dataTable.options.page = 1;
                }else {
                    await this.setUserInfoList();
                }
            },
            "reset": function () {
                this.user.query.name = null;
                this.user.query.jobType = null;
                this.user.query.career = null;
                this.user.query.careerYear = null;
                this.user.query.careerMonth = null;
                this.user.query.pay = null;
                this.user.query.address = null;
                this.user.query.inputStatus = null;
                this.user.query.education = [];
                this.user.query.certificateStatus = null;
                this.user.query.job = null;
                this.user.query.skill = null;
            },
        },
        "mounted": async function () {
            this.init();
        },
        "created": function() {
            for(var i=1; i<=20; i++) {
                this.select.careerYear.items.push({"text": String(i), "value": Number(i)});
                if(i < 12) this.select.careerMonth.items.push({"text": String(i), "value": Number(i * 0.01)});
            }
        }
    });
});