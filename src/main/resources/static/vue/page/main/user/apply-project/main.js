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

                },
                "dialog": false,
                "applyProject": {
                    "panels": {
                        "form": [0]
                    },
                    "form": {
                        "item": {
                            "adminProjId": null,
                            "name": null,
                            "skill": null,
                            "career": null,
                            "degree": null,
                            "term": null,
                            "place": null,
                            "prsnl": null,
                            "status": null,
                            "salary": null
                        },
                    },
                },
                "project": {
                    "panels": {
                        "list": [0]
                    },
                    "selected": [],
                    "dataTable": {
                        "headers": [
                            {"text": "프로젝트명", "value": "name"},
                            {"text": "직종", "value": "job"},
                            {"text": "기술", "value": "skill"},
                            {"text": "경력요건", "value": "career"},
                            {"text": "학위요건", "value": "degree"},
                            {"text": "기간", "value": "term"},
                            {"text": "장소", "value": "place"},
                            {"text": "필요인원", "value": "prsnl"},
                            {"text": "현황", "value": "status"},
                            {"text": "모집마감일", "value": "salary"},
                        ],
                        "query": {
                            "projectName": null,
                        },
                        "items": [],
                        "options": {
                            "page": 1,
                            "itemsPerPage": 10,
                        },
                        "loading" : false,
                        "totalRows": 0,
                    },
                }
            };
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
        },
        "methods": {
            "handleClick": function(value) {
                this.$router.push({
                    "path": "/main/user/apply-project/detail",
                    "query": {
                        "id": value.adminProjId
                    }
                });
            },
            "init": async function() {
                await this.loadProjectList();
            },
            "loadProjectList": async function() {
                let self = this, projectList;

                self.project.dataTable.loading = true;

                projectList = (await ito.api.common.project.getProjectList({
                    "page": self.project.dataTable.options.page,
                    "rowSize": self.project.dataTable.options.itemsPerPage,

                    "nameLike": self.project.dataTable.query.projectName,
                })).data;

                self.project.dataTable.totalRows = projectList.totalRows;
                self.project.dataTable.items = projectList.items;
                self.project.dataTable.loading = false;
            },
        },
        "mounted": function() {
            this.init();
        }
    });
});