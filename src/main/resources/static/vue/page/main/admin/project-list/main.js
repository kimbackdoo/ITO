var MainAdminProjectListPage = Vue.component('main-admin-project-list-page', function (resolve, reject) {
    axios.get("/vue/page/main/admin/project-list/main.html").then(function (response) {
         resolve({
            "template": response.data,
            "data": function () {
                return {
                    "project": {
                        "dialog": false,
                        "selected":[],
                        "panels": {
                            "search": [0],
                            "list": [0]
                        },
                        "dataTable": {
                            "headers": [
                                {"text": "프로젝트명", "value": "name"},
                                {"text": "직종", "value": "job"},
                                {"text": "기술", "value": "skill"},
                                {"text": "경력요건", "value": "career"},
                                {"text": "학위요건", "value": "degree"},
                                {"text": "프로젝트시작", "value": "sterm"},
                                {"text": "프로젝트시작", "value": "eterm"},
                                {"text": "장소", "value": "place"},
                                {"text": "필요인원", "value": "prsnl"},
                                {"text": "현황", "value": "status"},
                                {"text": "희망 급여", "value": "salary"},
                                {"text": "수정" , "value": "actions"},

                            ],
                            "totalRows":0,
                            "items": [],
                            "loading": false,
                            "serverItemsLength": 0,
                            "page": 1,
                            "itemsPerPage": 10,
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
                            "status": [
                                {"text":"투입", "value":"투입"},
                                {"text":"섭외", "value":"섭외"},
                                {"text":"면접", "value":"면접"},
                                {"text":"완료", "value":"완료"},
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
                        "pagination": {
                            "length": 10,
                            "totalVisible": 10
                        },
                        "query": {
                            "id":null,
                            "name": "",
                            "job": "",
                            "skill": "",
                            "career":"",
                            "degree":"",
                            "sterm": "",
                            "eterm": "",
                            "place": "",
                            "prsnl":"",
                            "status":"",
                            "slary":"",
                            "term":"",
                        },
                        "itemsPerPageItems": [
                            {"text":"3개 보기", "value":3},
                            {"text":"5개 보기", "value": 5},
                            {"text": "10개 보기", "value": 10},
                            {"text": "20개 보기", "value": 20},
                            {"text": "30개 보기", "value": 30},
                            {"text": "40개 보기", "value": 40},
                            {"text": "50개 보기", "value": 50}
                        ]
                    }
                };
            },

            "watch": {
                "project.dataTable.addressValue":{
                    "handler": function(value){
                        this.project.dataTable.addressSelect=this.project.dataTable.addressIndex[value];
                    }
                },

                 "project.dataTable.itemsPerPage": {
                    "handler": function (newValue, oldValue) {
                        Promise.resolve()
                            .then(this.setUserInfoList)
                            .then(this.replaceQuery)
                    }
                }

            },
            "methods": {

                //프로젝트 내용 수정시 필요
                "editProjectInfo": function(value){
                    console.log("id값 호출   "+value)
                    this.$router.push({
                        "path": "/main/admin/project-info-form",
                        "query": {
                            "id": value
                        }
                      });
                },
                "DetailProjectInfo": function(value){
                    console.log("id값 호출   "+value.id)
                    this.$router.push({
                        "path": "/main/admin/project-list/detail",
                        "query": {
                            "id": value.id
                        }
                    });
                },
               "deleteProjectInfoList": async function(){
                    var self = this;
                    deleteList = [];


                    self.project.selected.forEach(e => {
                        deleteList.push(e.id);
                    });
                    console.log("삭제할 리스트 id 값들  "+deleteList);

                    if(self.project.selected.length == 0){
                        await ito.alert("삭제할 항목이 없습니다.")
                    }else{
                        if(await ito.confirm("삭제하시겠습니까?")){
                           await ito.api.common.project.removeProjectList(deleteList);
                           await ito.alert("삭제되었습니다.")
                        }
                        self.setProjectInfoList();
                    }

                },
                "setProjectInfoList": function () {
                    var self = this;
                    return new Promise(function (resolve, reject) {
                        Promise.resolve()
                            .then(function () {
                                var params = {};
                                params.page = self.project.dataTable.page;
                                params.size = self.project.dataTable.itemsPerPage;
                                params.name = !_.isEmpty(self.project.query.name) ? self.project.query.name : null;
                                params.job = !_.isEmpty(self.project.query.job) ? self.project.query.job : null;
                                params.career = !_.isEmpty(self.project.query.career) ? self.project.query.career : null;
                                params.degree = !_.isEmpty(self.project.query.degree) ? self.project.query.degree : null;
                                params.sterm = !_.isEmpty(self.project.query.sterm) ? self.project.query.sterm : null;
                                params.eterm = !_.isEmpty(self.project.query.eterm) ? self.project.query.eterm : null;
                                params.place = !_.isEmpty(self.project.query.place) ? self.project.query.place : null;
                                params.prsnl = !_.isEmpty(self.project.query.prsnl) ? self.project.query.prsnl : null;
                                params.status = !_.isEmpty(self.project.query.status) ? self.project.query.status : null;
                                params.slary = !_.isEmpty(self.project.query.slary) ? self.project.query.slary : null;

                                self.project.dataTable.loading = true;
                                return ito.api.common.project.getProjectList(params);
                            })
                            .then(function (response) {
                                var data = response.data;
                                self.project.dataTable.items = data.items;
                                self.project.dataTable.serverItemsLength = data.totalElements;
                                self.project.dataTable.items.forEach(e => {
                                    e.career = e.career+"년"
                                });
                                self.project.dataTable.totalRows = data.items.length;
                                self.project.dataTable.loading = false;
                            })
                            .then(function () { resolve(); });
                    });
                },
                "getQuery": function () {
                    var query = {},
                    routeQuery = this.$route.query;
                    query.id = routeQuery.id ? routeQuery.id : this.project.query.id;
                    query.page = routeQuery.page ? routeQuery.page : String(this.project.dataTable.page);
                    query.size = routeQuery.size ? routeQuery.size : String(this.project.dataTable.itemsPerPage);
                    query.name = routeQuery.name ? routeQuery.name : this.project.query.name;
                    query.job = routeQuery.job ? routeQuery.job : this.project.query.job;
                    query.skill = routeQuery.skill ? routeQuery.skill : this.project.query.skill;
                    query.career = routeQuery.career ? routeQuery.career : this.project.query.career;
                    query.degree = routeQuery.degree ? routeQuery.degree : this.project.query.degree;
                    query.sterm = routeQuery.sterm ? routeQuery.sterm : this.project.query.sterm;
                    query.eterm = routeQuery.eterm ? routeQuery.eterm : this.project.query.eterm;
                    query.place = routeQuery.place ? routeQuery.place : this.project.query.place;
                    query.status=routeQuery.status ? routeQuery.status : this.project.query.status;
                    query.slary=routeQuery.slary ? routeQuery.slary : this.project.query.slary;
                    return query;
                },
                "setQuery": function () {
                    var query = this.getQuery();
                    this.project.query.id = query.id;
                    this.project.dataTable.page = Number(query.page);
                    this.project.dataTable.itemsPerPage = Number(query.size);
                    this.project.query.name = query.name;
                    this.project.query.job = query.job;
                    this.project.query.skill = query.skill;
                    this.project.query.career = query.career;
                    this.project.query.degree = query.degree;
                    this.project.query.sterm = query.sterm;
                    this.project.query.eterm = query.eterm;
                    this.project.query.place = query.place;
                    this.project.query.prsnl = query.prsnl;
                    this.project.query.status = query.status;
                    this.project.query.slary = query.slary;
                },
                "replaceQuery": function () {
                    var query = {},
                    routeQuery = this.$route.query;

                    query.id = this.project.query.id;
                    query.page = String(this.project.dataTable.page);
                    query.size = String(this.project.dataTable.itemsPerPage);
                    query.name = String(this.project.query.name);
                    query.job = String(this.project.query.job);
                    query.skill = String(this.project.query.skill);
                    query.career = String(this.project.query.career);
                    query.degree = String(this.project.query.degree);
                    query.sterm = String(this.project.query.sterm);
                    query.eterm = String(this.project.query.eterm);
                    query.place = String(this.project.query.place);
                    query.status = String(this.project.query.status);
                    query.slary = String(this.project.query.slary);

                    if (!_.isEqual(query, routeQuery)) {
                        this.$router.replace({"query": query});
                    }
                },
                "search": function () {
                    var self = this;
                    return new Promise(function (resolve, reject) {
                        Promise.resolve()
                            .then(function () {
                                return self.setProjectInfoList();
                            })
                            .then(function () {
                                self.replaceQuery();
                            })
                            .then(function () {
                             resolve();
                         });
                    });
                },
                "reset": function () {
                    var self = this;
                    return new Promise(function (resolve, reject) {
                        Promise.resolve()
                            .then(function () {
                            })
                            .then(function () {
                                self.project.dataTable.addressValue=""
                                self.project.query.name = "";
                                self.project.query.job = "";
                                self.project.query.skill = "";
                                self.project.query.career = "";
                                self.project.query.degree = "";
                                self.project.query.sterm = "";
                                self.project.query.eterm = "";
                                self.project.query.place = "";
                                self.project.query.prsnl = "";
                                self.project.query.status = "";
                                self.project.query.slary = "";
                            })
                            .then(function () {
                                return self.search();
                            })
                            .then(function () { resolve(); });
                    });
                }
            },
            "mounted": function () {
                Promise.resolve()
                    .then(this.setQuery)
                    .then(this.replaceQuery)
                    .then(this.setProjectInfoList);
            }

        });
    });
});