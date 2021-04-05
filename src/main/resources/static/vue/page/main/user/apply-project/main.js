var ApplyProjectMainComponent;
ApplyProjectMainComponent = Vue.component('applyProject-main-component', async function(resolve) {
    resolve({
        "template": (await axios.get("/vue/page/main/user/apply-project/main.html")).data,
        "data": function() {
            return {
                "data": {
                    "paginationList": [
                        {"text": "5개 보기", "value": 5},
                        {"text": "10개 보기", "value": 10},
                        {"text": "15개 보기", "value": 15},
                        {"text": "20개 보기", "value": 20},
                        {"text": "25개 보기", "value": 25},
                        {"text": "30개 보기", "value": 30},
                        {"text": "전체 보기", "value": null}
                    ],
                },
                "select": {
                    "job": {
                        "items": [
                            {"text": "전체", "value": null}
                        ]
                    },
                    // "skill": {
                    //     "items": []
                    // },
                    "degree": {
                        "items": []
                    },
                    "status": {
                        "items": [
                            {"text": "섭외", "value": "A"},
                            {"text": "완료", "value": "C"},
                            {"text": "면접", "value": "I"},
                            {"text": "투입", "value": "P"}
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
                        "items": []
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
                            {"text": "프로젝트명", "value": "name"},
                            {"text": "직종", "value": "job"},
                            {"text": "기술", "value": "skill"},
                            {"text": "경력요건", "value": "career"},
                            {"text": "학위요건", "value": "degree"},
                            {"text": "시작기간", "value": "sterm"},
                            {"text": "종료기간", "value": "eterm"},
                            {"text": "지역(시)", "value": "local"},
                            {"text": "지역(구)", "value": "detailLocal"},
                            {"text": "필요인원", "value": "prsnl"},
                            {"text": "현황", "value": "statusName"},
                            {"text": "월급여", "value": "salary"},
                            {"text": "모집마감일", "value": "limitDate"},
                        ],
                        "query": {
                            "projectName": null,
                            "job": null,
                            "skill": null,
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
                        "items": [],
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
                        "loading" : false,
                        "totalRows": 0,
                    },
                }
            };
        },
        "computed": {
            // skillAllSelect() {
            //     return this.project.dataTable.query.skill.length === this.select.skill.items.length;
            // },
            // skillSelect() {
            //     return this.project.dataTable.query.skill.length > 0 && !this.skillAllSelect;
            // },
            degreeAllSelect() {
                return this.project.dataTable.query.degree.length === this.select.degree.items.length;
            },
            degreeSelect() {
                return this.project.dataTable.query.degree.length > 0 && !this.degreeAllSelect;
            },
            // iconSkill() {
            //     if(this.skillAllSelect) return 'mdi-close-box';
            //     if(this.skillSelect) return 'mdi-minus-box';
            //     return 'mdi-checkbox-blank-outline'
            // },
            iconDegree() {
                if(this.degreeAllSelect) return 'mdi-close-box';
                if(this.degreeSelect) return 'mdi-minus-box';
                return 'mdi-checkbox-blank-outline'
            },
        },
        "watch": {
            "project.dataTable.options.page": {
                "handler": async function(n ,o) {
                    await this.loadProjectList();
                },
                "deep": true
            },
            "project.dataTable.options.itemsPerPage": {
                "handler": async function(n, o) {
                    this.project.dataTable.options.page = 1;
                    await this.loadProjectList();
                },
                "deep": true
            },
            "project.dataTable.options.sortDesc": {
                "handler": async function (n, o) {
                    await this.loadProjectList();
                },
                "deep": true
            },
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
                await this.loadProjectList();
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
            "loadProjectList": async function() {
                let self = this, career, projectList;
                career = String(self.project.dataTable.query.careerYear + self.project.dataTable.query.careerMonth);

                self.project.dataTable.loading = true;
                projectList = (await ito.api.common.project.getProjectList({
                    "page": self.project.dataTable.options.page,
                    "rowSize": self.project.dataTable.options.itemsPerPage,
                    "sort": ito.util.sort(self.project.dataTable.options.sortBy, self.project.dataTable.options.sortDesc),

                    "nameLike": self.project.dataTable.query.projectName,
                    "job": self.project.dataTable.query.job,
                    "skillList": self.project.dataTable.query.skill,
                    "career": career,
                    "degree": self.project.dataTable.query.degree,
                    "stermStart": self.project.dataTable.query.stermStart,
                    "local": self.project.dataTable.query.local,
                    "detailLocal": self.project.dataTable.query.detailLocal,
                    "prsnl": self.project.dataTable.query.prsnl,
                    "status": self.project.dataTable.query.status,
                    "salary": self.project.dataTable.query.salary,
                })).data;

                let limitDate, limitDay;
                for(var i=0; i<projectList.items.length; i++) {
                    limitDate = moment(projectList.items[i].limitDate).format("MM-DD");
                    limitDay = moment(projectList.items[i].limitDate).diff(moment(), "day");

                    if(limitDay < 0) projectList.items[i].limitDate = limitDate + " (D+" + Math.abs(limitDay) + ")";
                    else projectList.items[i].limitDate = limitDate + " (D-" + limitDay + ")";

                    switch (projectList.items[i].status) {
                        case 'P': projectList.items[i].statusName = "투입"; break;
                        case 'I': projectList.items[i].statusName = "면접"; break;
                        case 'C': projectList.items[i].statusName = "완료"; break;
                        case 'A': projectList.items[i].statusName = "섭외"; break;
                    }
                }

                self.project.dataTable.totalRows = projectList.totalRows;
                self.project.dataTable.items = projectList.items;
                self.project.dataTable.loading = false;
            },
            "searchProjectList": async function() {
                if(this.project.dataTable.options.page !== 1) {
                    this.project.dataTable.options.page = 1;
                }else {
                    this.loadProjectList();
                }
            },
            "initialize": async function() {
                let self = this;

                self.project.dataTable.query.projectName = null;
                self.project.dataTable.query.job = null;
                self.project.dataTable.query.skill = null;
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
            // "toggleSkill": function() {
            //     this.$nextTick(() => {
            //         if(this.skillAllSelect) {
            //             this.project.dataTable.query.skill = [];
            //         } else {
            //             this.project.dataTable.query.skill = this.select.skill.items.slice();
            //         }
            //     });
            // },
            "toggleDegree": function() {
                this.$nextTick(() => {
                    if(this.degreeAllSelect) {
                        this.project.dataTable.query.degree = [];
                    } else {
                        this.project.dataTable.query.degree = this.select.degree.items.slice();
                    }
                })
            },
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