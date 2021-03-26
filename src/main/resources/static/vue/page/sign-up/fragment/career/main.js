var SignUpMainCareerFragment;
SignUpMainCareerFragment = Vue.component("sign-up-main-career-fragment", async function (resolve) { resolve({
    "template": (await axios.get("/vue/page/sign-up/fragment/career/main.html")).data,
        "data": function() {
            return {
                "dialog": {
                    "visible": false,
                    "title": "경력 등록",
                    "data": {}
                },
                "data": {
                    "career": {
                    }
                },
                "career": {
                    "dataTable": {
                        "headers": [
                            {"text": "프로젝트명", "value": "name"},
                            {"text": "시작 기간", "value": "startPeriod"},
                            {"text": "종료 기간", "value": "endPeriod"},
                            {"text": "직급", "value": "position"},
                            {"text": "담당업무", "value": "task"},
                            {"text": "월급여", "value": "pay"}
                        ],
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
                        "loading": false,
                        "totalRows": 0
                    },
                },
                "rules": {
                    "required": value => !!value || '필수',
                }
            }
        },
        "watch": {
            "data.career": {
                "handler": function(n , o) {
                    this.$emit("update:career", n);
                },
                "deep": true
            },
        },
        "methods": {
            "init": async function() {
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
            "saveCareer": async function(data) {
                let self = this, id, items;
                items = self.career.dataTable.items;
                if(data.id === undefined) {
                    id = items.length > 0 ? self.career.dataTable.items[items.length-1].id : 0;
                    data.id = id + 1;
                    self.career.dataTable.items.push(_.cloneDeep(data));
                    self.career.dataTable.totalRows = self.career.dataTable.totalRows + 1;
                }else {
                    Object.assign(self.career.dataTable.items.find(e=>e.id === data.id), _.cloneDeep(data));
                }
            },
            "openCareerDialog": function () {
                this.dialog.data = {};
                this.dialog.visible = true;
            },
            "modifyCareer": function (data) {
                this.dialog.title = "경력 수정";
                this.dialog.data = data;
                this.dialog.visible = true;
            }
        },
        "mounted": function() {
        }
    });
});