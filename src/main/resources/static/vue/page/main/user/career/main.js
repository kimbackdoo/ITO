var CareerMainComponent;
CareerMainComponent = Vue.component('career-main-component', async function(resolve) {
    resolve({
        "template": (await axios.get("/vue/page/main/user/career/main.html")).data,
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
                "dialog": false,
                "career": {
                    "panels": {
                        "list": [0],
                        "form": [0]
                    },
                    "form": {
                        "item": {
                            "personCareerId": null,
                            "name": null,
                            "startPeriod": null,
                            "endPeriod": null,
                            "position": null,
                            "task": null,
                            "pay": null
                        }
                    },
                    "selected": [],
                    "dataTable": {
                        "headers": [
                            {"text": "프로젝트명", "value": "name"},
                            {"text": "시작 기간", "value": "startPeriod"},
                            {"text": "종료 기간", "value": "endPeriod"},
                            {"text": "직급", "value": "position"},
                            {"text": "담당업무", "value": "task"},
                            {"text": "월급여", "value": "pay"},
                            {"value": "actions"},
                        ],
                        "items": [],
                        "options": {
                            "page": 1,
                            "itemsPerPage": 10,
                        },
                        "loading": false,
                        "totalRows": 0,
                    },
                },
            }
        },
        "watch": {
            "career.dataTable.options.page": {
                "handler": async function(n, o) {
                    await this.loadCareerList();
                },
                "deep": true
            },
            "career.dataTable.options.itemPerPage": {
                "handler": async function(n, o) {
                    this.career.dataTable.options.page = 1;
                    await this.loadCareerList();
                },
                "deep": true
            }
        },
        "methods": {
            "init": async function() {
                await this.loadCareerList();
            },
            "loadCareerList": async function() {
                let self = this, careerList;
                self.career.dataTable.loading = true;

                careerList = (await ito.api.common.career.getCareerList({
                    "page": self.career.dataTable.options.page,
                    "rowSize": self.career.dataTable.options.itemsPerPage
                })).data;

                self.career.dataTable.totalRows = careerList.totalRows;
                self.career.dataTable.items = careerList.items;

                let start, end;
                start = moment(careerList.items[0].startPeriod);
                end = moment(careerList.items[0].endPeriod);
                console.log(end.diff(start, 'days'));

                self.career.dataTable.loading = false;
            },
            "initialize": async function() {
                let self = this;

                self.career.form.item.name = null;
                self.career.form.item.startPeriod = null;
                self.career.form.item.endPeriod = null;
                self.career.form.item.position = null;
                self.career.form.item.task = null;
                self.career.form.item.pay = null;
            },
            "setCareer": async function(queryId) {
                let self = this;

                self.dialog = true;

                return new Promise(function (resolve, reject) {
                    Promise.resolve()
                        .then(function () {
                            return ito.api.common.career.getCareer(queryId);
                        })
                        .then(function (response) {
                            self.career.form.item = response.data;
                        })
                        .then(function () { resolve(); });
                });
            },
            "deleteCareerList": async function() {
                let self = this, deleteList = [];

                self.career.selected.forEach(e => {
                    deleteList.push(e.personCareerId);
                });

                if(self.career.selected.length == 0)
                    await ito.alert("삭제한 경력이 없습니다.");
                else if(await ito.confirm("삭제하시겠습니까?")) {
                    await ito.api.common.career.removeCareerList(deleteList);
                    await ito.alert("삭제되었습니다.");

                    self.career.dataTable.options.page = 1;
                    self.loadCareerList();
                }
            },
            "deleteCareer": async function(queryId) {
                if(await ito.confirm("삭제하시겠습니까?")) {
                    ito.api.common.career.removeCareer(queryId);
                    await ito.alert("삭제되었습니다.");

                    this.career.dataTable.options.page = 1;
                    this.loadCareerList();
                }
            },
            "saveCareer": async function() {
                let self = this,
                    data = self.career.form.item;

                if(await ito.confirm("저장하시겠습니까?")) {
                    if(data.personCareerId != undefined && data.personCareerId != null)
                        ito.api.common.career.modifyCareer(data.personCareerId, data);
                    else
                        ito.api.common.career.createCareer(data);
                    await ito.alert("저장되었습니다.");

                    self.dialog = false;
                    self.career.dataTable.options.page = 1;
                    self.loadCareerList();
                }
            }
        },
        "mounted": function() {
            this.init();
        }
    });
});