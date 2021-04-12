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
                            {"text": "이름", "value": "name", "align": "center", "width": "120", cellClass:"text-truncate"},
                            {"text": "성별", "value": "gender", "align": "center", "width": "120", cellClass:"text-truncate"},
                            {"text": "평가점수", "value": "ratingScore", "align": "center", "width": "120", cellClass:"text-truncate"},
                            {"text": "평가메모", "value": "memo", "align": "center", "width": "200", cellClass:"w-12 text-truncate"},
                            {"text": "전화번호", "value": "phoneNumber", "align": "center", "width": "120", cellClass:"text-truncate"},
                            {"text": "직종", "value": "jobType", "align": "center", "width": "120", cellClass:"text-truncate"},
                            {"text": "기술", "value": "skill", "align": "center", "width": "120", cellClass:"text-truncate"},
                            {"text": "학력", "value": "education", "align": "center", "width": "120", cellClass:"text-truncate"},
                            {"text": "경력", "value": "career", "align": "center", "width": "120", cellClass:"text-truncate"},
                            {"text": "자격증 유무", "value": "certificateStatus", "align": "center", "width": "120", cellClass:"text-truncate"},
                            {"text": "생년월일(나이)", "value": "birthDate", "align": "center", "width": "120", cellClass:"text-truncate"},
                            {"text": "희망 월급여", "value": "pay", "align": "center", "width": "120", cellClass:"text-truncate"},
                            {"text": "지역", "value": "address", "align": "center", "width": "120", cellClass:"text-truncate"},
                            {"text": "현재 지원한 프로젝트", "value": "applyProject", "align": "center", "width": "120", cellClass:"text-truncate"},
                            {"text": "프로젝트명", "value": "projectName", "align": "center", "width": "120", cellClass:"text-truncate", "type": "autocomplete"},
                            {"text": "투입여부", "value": "inputStatus", "align": "center", "width": "120", cellClass:"text-truncate"},
                            {"text": "업무 가능일", "value": "workableDay", "align": "center", "width": "120", cellClass:"text-truncate"}
                        ],
                        "cell": {
                            "autocomplete": {
                                "projectName": {
                                    "items": []
                                }
                            }
                        },
                        "totalRows":0,
                        "items": [],
                        "loading": false,
                    },
                    "query": {
                        "name": null,
                        "sex": null,
                        "jobType": null,
                        "skillListLike": [],
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
                        "ratingScore": null
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
                            {"text": "섭외중", "value": "A"},
                            {"text": "섭외완료", "value": "C"},
                            {"text": "인터뷰", "value": "I"},
                            {"text": "투입중", "value": "P"},
                            {"text": "이외", "value": "N"},
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
                        "items": [
                            {"text": "전체", "value": null},
                        ]
                    },
                    "sex": {
                        "items": [
                            {"text": "무관", "value": null},
                            {"text": "남자", "value": "M"},
                            {"text": "여자", "value": "F"},
                        ]
                    },
                    "ratingScroe": {
                        "items": []
                    }
                },
            };
        },
        "watch": {
            "user.query.local": {
                "handler": async function(value){
                    let items,
                        local = this.select.local.items.find(e=> e.value == this.user.query.local);

                    this.select.detailLocal.items=[];
                    items = (await ito.api.common.code.getCodeList({
                        "idStartLike": value,
                        "status": "T",
                        "detailLocal": local !== undefined ? local.text : null,
                        "rowSize": 1000000
                    })).data.items.map(e => ({"text": e.name, "value": e.id}))
                    items = items.filter(e => e.value.length > 5)
                    this.select.detailLocal.items.push(...items);
                }
            },
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
                    "parentId": "001",
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
            "setUserInfoList": async function (options) {
                let self = this, personList, codeList, projectList, startBirth, endBirth, projectPersonPromiseList;
                let careerValue = String(self.user.query.careerYear + self.user.query.careerMonth);

                if(self.user.query.birthDate != null) {
                    startBirth = moment().subtract(Number(self.user.query.birthDate)-1, "y") .format("YYYY-01-01");
                    endBirth = moment().subtract(Number(self.user.query.birthDate)-1, "y") .format("YYYY-12-31");
                }

                self.user.dataTable.loading = true;
                personList = (await ito.api.common.person.getPersonList({
                    "page": options !== undefined ? options.page : 1,
                    "rowSize": options !== undefined ? options.itemsPerPage : 10,
                    "sort": options !== undefined ? ito.util.sort(options.sortBy, options.sortDesc) : [],

                    "name": self.user.query.name,
                    "jobType": self.user.query.jobType,
                    "career": careerValue === "0" ? null : careerValue,
                    "minPay": self.user.query.minPay,
                    "local": self.user.query.local,
                    "detailLocal": self.user.query.detailLocal,
                    "inputStatus": self.user.query.inputStatus,
                    "education": self.user.query.education,
                    "certificateStatus": self.user.query.certificateStatus,
                    "skillListLike": self.user.query.skillListLike,
                    "startBirthDate": startBirth,
                    "endBirthDate": endBirth,
                    "workableDay": moment().add("1", "M").format("YYYY-MM-DD"),
                    "ratingScore": self.user.query.ratingScore,
                })).data;
                projectPersonPromiseList = [];
                personList.items.forEach(e=> {
                    projectPersonPromiseList.push(ito.api.common.projectPerson.getProjectPersonList({"personId": e.id}));
                });
                (await Promise.all(projectPersonPromiseList)).forEach((e, i) => {
                    console.log(e.data.items);
                });

                codeList = (await ito.api.common.code.getCodeList({
                    "parentId": "001",
                    "sort": ["ranking, asc"],
                    "rowSize": 1000000
                })).data.items.map(e=>({"text": e.name, "value": e.id}));

                projectList = (await ito.api.common.project.getProjectList({
                    "limitDateStart": moment().format("YYYY-MM-DD")
                })).data.items.map(e=>({"text": e.name, "value": e.id}));
                this.user.dataTable.cell.autocomplete.projectName.items.push(...projectList);

                self.user.dataTable.totalRows = personList.totalRows;
                self.user.dataTable.items = personList.items;
                self.user.dataTable.items.forEach(e => {
                    for(var i=0; i<codeList.length; i++) {
                        if(e.jobType === codeList[i].value) {
                            e.jobType = codeList[i].text;
                            break;
                        }
                    }
                    e.gender = (e.gender === "M") ? "남자" : "여자"
                    e.inputStatus = (e.inputStatus == "T") ? "투입중" : "섭외중"
                    e.certificateStatus = (e.certificateStatus == "T") ? "있음" : "없음"
                    e.birthDate = moment(e.birthDate).format("YY") + "년생 (" + Number(moment().diff(moment(e.birthDate), "years")+1) + ")";
                    e.career = e.career+"년"
                    e.pay = String(e.minPay) +" ~ " +String(e.maxPay);
                });
                self.user.dataTable.loading = false;
            },
            "inputProject": async function(value) {
                if(await ito.confirm("지원하시겠습니까?")) {
                    await ito.api.common.projectPerson.createProjectPerson({
                        "personId": value.item.id,
                        "projectId": value.id,
                        "status": "F",
                    });
                    await ito.alert("지원되었습니다.");
                }
            },
            "search": async function () {
                await this.setUserInfoList();
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
                this.user.query.birthDate = null;
                this.user.query.job = null;
                this.user.query.skillListLike = [];
                this.user.query.ratingScore = null;
            },
            "delimit": function(v) {
                let reducer = (a, e) => [...a, ...e.split(/[, ]+/)]
                this.user.query.skillListLike = [...new Set(v.reduce(reducer, []))]
            }
        },
        "mounted": async function () {
            this.init();
        },
        "created": function() {
            for(var i=1; i<=20; i++) {
                this.select.careerYear.items.push({"text": String(i), "value": Number(i)});
                if(i < 12) this.select.careerMonth.items.push({"text": String(i), "value": Number(i * 0.01)});

                if(i<6) this.select.ratingScroe.items.push({"text": String(i), "value": Number(i)});
            }
        }
    });
});