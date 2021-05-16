var ApplyProjectMainComponent;
ApplyProjectMainComponent = Vue.component('applyProject-main-component', async function(resolve) {
    resolve({
        "template": (await axios.get("/vue/page/main/user/apply-project/main.html")).data,
        "data": function() {
            return {
                "select": {
                    "job": {
                        "items": [
                            {"text": "전체", "value": null},
                        ]
                    },
                    // "skill": {
                    //     "items": []
                    // },
                    "degree": {
                        "items": [
                            {"text": "전체", "value": null},
                        ]
                    },
                    "status": {
                        "items": [
                            {"text": "섭외중", "value": "A"},
                            {"text": "섭외 완료", "value": "C"},
                            {"text": "인터뷰", "value": "I"},
                            {"text": "투입중", "value": "P"},
                            {"text": "이외", "value": "N"}
                        ]
                    },
                    "careerYear": {
                        "items": [
                            {"text": "1년미만", "value": 0}
                        ]
                    },
                    "careerMonth": {
                        "items": []
                    },
                    "local": {
                        "items": []
                    },
                    "detailLocal": {
                        "items": [
                            {"text": "전체", "value": null}
                        ]
                    },
                },
                "dialog": false,
                "project": {
                    "panels": {
                        "list": [0, 1]
                    },
                    "selected": [],
                    "dataTable": {
                        "headers": [
                            {"text": "프로젝트명", "value": "name", "align": "center", "width": "120", cellClass:"text-truncate"},
                            {"text": "직종", "value": "job", "align": "center", "width": "120", cellClass:"text-truncate"},
                            {"text": "기술", "value": "skill", "align": "center", "width": "120", cellClass:"text-truncate"},
                            {"text": "경력요건", "value": "career", "align": "center", "width": "120", cellClass:"text-truncate"},
                            {"text": "학위요건", "value": "degree", "align": "center", "width": "120", cellClass:"text-truncate"},
                            {"text": "시작기간", "value": "sterm", "align": "center", "width": "120", cellClass:"text-truncate"},
                            {"text": "종료기간", "value": "eterm", "align": "center", "width": "120", cellClass:"text-truncate"},
                            {"text": "지역(시)", "value": "local", "align": "center", "width": "120", cellClass:"text-truncate"},
                            {"text": "지역(구)", "value": "detailLocal", "align": "center", "width": "120", cellClass:"text-truncate"},
                            {"text": "필요인원", "value": "prsnl", "align": "center", "width": "120", cellClass:"text-truncate"},
                            {"text": "현황", "value": "statusName", "align": "center", "width": "120", cellClass:"text-truncate"},
                            {"text": "월급여", "value": "salary", "align": "center", "width": "120", cellClass:"text-truncate"},
                            {"text": "모집마감일", "value": "limitDate", "align": "center", "width": "120", cellClass:"text-truncate"},
                        ],
                        "query": {
                            "projectName": null,
                            "job": null,
                            "skillListLike": [],
                            "careerYear": null,
                            "careerMonth": null,
                            "degree": [],
                            "stermStart": null,
                            "stermEnd": null,
                            "etermStart": null,
                            "etermEnd": null,
                            "local": null,
                            "detailLocal": null,
                            "prsnl": null,
                            "status": null,
                            "salary": null
                        },
                        "search": false,
                        "items": [],
                        "loading" : false,
                        "totalRows": 0,
                    },
                }
            };
        },
        "watch": {
            // "project.dataTable.query.job": {
            //     "handler": async function () {
            //         let items,
            //             skill = this.select.job.items.find(e=>e.value == this.project.dataTable.query.job);
            //
            //         this.select.skill.items = [];
            //         items = (await ito.api.common.code.getCodeList({
            //             "idStartLike": "004",
            //             "status": "T",
            //             "skill": skill !== undefined ? skill.text : null,
            //             "rowSize": 1000000
            //         })).data.items.map(e=>({"text": e.name, "value": e.id}));
            //         items = items.filter(e=> {
            //             if(e.value.startsWith("00401")) return e.value.length > 7;
            //             else return e.value.length > 5;
            //         });
            //
            //         this.select.skill.items.push(...items);
            //         this.project.dataTable.query.skill = [];
            //     }
            // },
            "project.dataTable.query.local": {
                "handler": async function(value) {
                    let items,
                        local = this.select.local.items.find(e=> e.value == this.project.dataTable.query.local);

                    this.select.detailLocal.items = [];
                    items = (await ito.api.common.code.getCodeList({
                        "parentId": value,
                        "status": "T",
                        "detailLocal": local !== undefined ? local.text : null,
                        "rowSize": 10000000
                    })).data.items.map(e=> ({"text": e.name, "value": e.id}));
                    items = items.filter(e=> e.value.length > 5);
                    this.select.detailLocal.items.push(...items);
                }
            },
        },
        "methods": {
            "handleClick": function(value) {
                this.$router.push({
                    "path": "/main/user/apply-project/detail",
                    "query": {
                        "id": value.id
                    }
                });
            },
            "init": async function() {
                await this.loadJobItems();
                await this.loadLocalItems();
                await this.loadDegreeItems();
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
            "loadLocalItems": async function() {
                let items = (await ito.api.common.code.getCodeList({
                    "parentId": "006",
                    "sort": ["ranking, asc"],
                    "rowSize": 1000000
                })).data.items.map(e=>({"text": e.name, "value": e.id}));;
                this.select.local.items.push(...items);
            },
            "loadDegreeItems": async function() {
                let items = (await ito.api.common.code.getCodeList({
                    "parentId": "007",
                    "sort": ["ranking, asc"],
                    "rowSize": 10000000
                })).data.items.map(e=> ({"text": e.name, "value": e.id}));
                this.select.degree.items.push(...items);
            },
            "loadProjectList": async function(options) {
                let self = this, career, projectList, jobCodeList, degreeCodeList, localCodeList, detailLocalCodeList;
                if(self.project.dataTable.query.careerYear !== null && self.project. dataTable.query.careerMonth !== null) {
                    career = String(self.project.dataTable.query.careerYear + self.project.dataTable.query.careerMonth);
                }

                self.project.dataTable.search = false;
                self.project.dataTable.loading = true;
                projectList = (await ito.api.common.project.getProjectList({
                    "page": options !== undefined ? options.page : 1,
                    "rowSize": options !== undefined ? options.itemsPerPage : 10,
                    "sort": options !== undefined ? ito.util.sort(options.sortBy, options.sortDesc) : [],

                    "nameLike": self.project.dataTable.query.projectName,
                    "job": self.project.dataTable.query.job,
                    "skillListLike": self.project.dataTable.query.skillListLike,
                    "career": career,
                    "degree": self.project.dataTable.query.degree,
                    "stermStart": self.project.dataTable.query.stermStart,
                    "local": self.project.dataTable.query.local,
                    "detailLocal": self.project.dataTable.query.detailLocal,
                    "prsnl": self.project.dataTable.query.prsnl,
                    "status": self.project.dataTable.query.status,
                    "salary": self.project.dataTable.query.salary,
                })).data;

                jobCodeList = (await ito.api.common.code.getCodeList({
                    "parentId": "001",
                    "sort": ["ranking, asc"],
                    "rowSize": 100000000,
                })).data.items.map(e=> ({"text": e.name, "value": e.id}));

                degreeCodeList = (await ito.api.common.code.getCodeList({
                    "parentId": "007",
                    "sort": ["ranking, asc"],
                    "rowSize": 1000000000,
                })).data.items.map(e=> ({"text": e.name, "value": e.id}))

                localCodeList = (await ito.api.common.code.getCodeList({
                    "parentId": "006",
                    "sort": ["ranking, asc"],
                    "rowSize": 1000000000,
                })).data.items.map(e=> ({"text": e.name, "value": e.id}));

                detailLocalCodeList = (await ito.api.common.code.getCodeList({
                    "idStartLike": "006_____",
                    "sort": ["ranking, asc"],
                     "rowSize": 1000000000,
                })).data.items.map(e=> ({"text": e.name, "value": e.id}));

                projectList.items.forEach(e=> {
                    for(let i=0; i<jobCodeList.length; i++) {
                        if(e.job === jobCodeList[i].value) {
                            e.job = jobCodeList[i].text;
                            break;
                        }
                    }
                    for(let i=0; i<degreeCodeList.length; i++) {
                        if(e.degree === degreeCodeList[i].value) {
                            e.degree = degreeCodeList[i].text;
                            break;
                        }
                    }
                    for(let i=0; i<localCodeList.length; i++) {
                        if(e.local === localCodeList[i].value) {
                            e.local = localCodeList[i].text;
                            break;
                        }
                    }
                    for(let i=0; i<detailLocalCodeList.length; i++) {
                        if(e.detailLocal === detailLocalCodeList[i].value) {
                            e.detailLocal = detailLocalCodeList[i].text;
                            break;
                        }
                    }
                });

                let limitDate, limitDay;
                for(var i=0; i<projectList.items.length; i++) {
                    limitDate = moment(projectList.items[i].limitDate).format("MM-DD");
                    limitDay = moment(projectList.items[i].limitDate).diff(moment(), "days");

                    if(limitDay < 0) projectList.items[i].limitDate = limitDate + " (D+" + Math.abs(limitDay) + ")";
                    else projectList.items[i].limitDate = limitDate + " (D-" + limitDay + ")";

                    switch (projectList.items[i].status) {
                        case 'P': projectList.items[i].statusName = "투입중"; break;
                        case 'I': projectList.items[i].statusName = "인터뷰"; break;
                        case 'C': projectList.items[i].statusName = "섭외 완료"; break;
                        case 'A': projectList.items[i].statusName = "섭외중"; break;
                        case 'N': projectList.items[i].statusName = "이외"; break;
                    }
                }

                self.project.dataTable.totalRows = projectList.totalRows;
                self.project.dataTable.items = projectList.items;
                self.project.dataTable.loading = false;
            },
            "searchProjectList": async function() {
                this.project.dataTable.search = true;
            },
            "initialize": async function() {
                let self = this;

                self.project.dataTable.query.projectName = null;
                self.project.dataTable.query.job = null;
                self.project.dataTable.query.skillListLike = [];
                self.project.dataTable.query.careerYear = null;
                self.project.dataTable.query.careerMonth = null;
                self.project.dataTable.query.degree = [];
                self.project.dataTable.query.stermStart = null;
                self.project.dataTable.query.local = null;
                self.project.dataTable.query.detailLocal = null;
                self.project.dataTable.query.prsnl = null;
                self.project.dataTable.query.status = null;
                self.project.dataTable.query.salary = null;
            },
            "delimit": function(v) {
                let reducer = (a, e) => [...a, ...e.split(/[, ]+/)]
                this.project.dataTable.query.skillListLike = [...new Set(v.reduce(reducer, []))]
            }
        },
        "mounted": function() {
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