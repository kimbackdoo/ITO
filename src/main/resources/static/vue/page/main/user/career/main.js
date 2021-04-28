var CareerMainComponent;
CareerMainComponent = Vue.component('career-main-component', async function(resolve) {
    resolve({
        "template": (await axios.get("/vue/page/main/user/career/main.html")).data,
        "data": function() {
            return {
                "data": {
                    "query": {
                        "maxDate": moment().format("YYYY-MM-DD")
                    },
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
                "career": {
                    "panels": {
                        "list": [0],
                        "form": [0]
                    },
                    "form": {
                        "item": {
                            "personCareerId": null,
                            "personId": null,
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
                            {"text": "프로젝트명", "value": "name", "align": "center", "width": "120", cellClass:"text-truncate"},
                            {"text": "시작 기간", "value": "startPeriod", "align": "center", "width": "120", cellClass:"text-truncate"},
                            {"text": "종료 기간", "value": "endPeriod", "align": "center", "width": "120", cellClass:"text-truncate"},
                            {"text": "직급", "value": "position", "align": "center", "width": "120", cellClass:"text-truncate"},
                            {"text": "담당업무", "value": "task", "align": "center", "width": "120", cellClass:"text-truncate"},
                            {"text": "월급여", "value": "pay", "align": "center", "width": "120", cellClass:"text-truncate"}
                        ],
                        "items": [],
                        "loading": false,
                        "totalRows": 0,
                        "totalCareer": 0,
                    },
                },
                "dialogCareer": {
                    "visible": false,
                    "title": "경력 등록",
                    "data": {}
                },
                "dialogFileUpload": {
                    "visible": false,
                    "title": "파일 업로드"
                }
            }
        },
        "methods": {
            "init": async function() {
                await this.loadCareerList();
            },
            "loadCareerList": async function(options) {
                let self = this, careerList;
                self.career.dataTable.loading = true;

                careerList = (await ito.api.common.career.getCareerList({
                    "page": options !== undefined ? options.page : 1,
                    "rowSize": options !== undefined ? options.itemsPerPage : 10,
                    "sort": options !== undefined ? ito.util.sort(options.sortBy, options.sortDesc) : [],
                })).data;

                self.career.dataTable.totalRows = careerList.totalRows;
                self.career.dataTable.items = careerList.items;
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
                    await ito.alert("삭제할 경력이 없습니다.");
                else if(await ito.confirm("삭제하시겠습니까?")) {
                    await ito.api.common.career.removeCareerList(deleteList);
                    await ito.alert("삭제되었습니다.");
                    self.loadCareerList();
                }
            },
            "deleteCareer": async function(data) {
                if(await ito.confirm("삭제하시겠습니까?")) {
                    ito.api.common.career.removeCareer(data.personCareerId);
                    await ito.alert("삭제되었습니다.");
                    this.loadCareerList();
                }
            },
            "saveCareer": async function(data) {
                let self = this;

                data.personId = store.state.app.person.id;

                if(await ito.confirm("저장하시겠습니까?")) {
                    if(data.personCareerId != undefined && data.personCareerId != null)
                        ito.api.common.career.modifyCareer(data.personCareerId, data);
                    else
                        ito.api.common.career.createCareer(data);
                    await ito.alert("저장되었습니다.");

                    self.dialog = false;
                    await self.loadCareerList();
                }
            },
            "openNewCareerDialog": function () {
                this.dialogCareer.data = {};
                this.dialogCareer.visible = true;
            },
            "openCareerDialog": function (data) {
                this.dialogCareer.title = "경력 수정";
                this.dialogCareer.data = data;
                this.dialogCareer.visible = true;
            },
            "openFileUploadDialog": function() {
                this.dialogFileUpload.visible = true;
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
                    this.loadCareerList();
                }
            },
        },
        "mounted": function() {
            this.init();
        }
    });
});