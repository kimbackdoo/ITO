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
                                {"text": "학위요건", "value": "degree"},
                                {"text": "경력요건", "value": "career"},
                                {"text": "프로젝트 시작", "value": "sterm"},
                                {"text": "프로젝트 끝 ", "value": "eterm"},
                                {"text": "장소(시,구)", "value": "local"},
                                {"text": "장소(시,구)", "value": "detailLocal"},
                                {"text": "필요인원", "value": "prsnl"},
                                {"text": "현황", "value": "status"},
                                {"text": "희망 급여", "value": "salary"},
                                {"text": "모집마감일", "value": "limitDate"},
                                {"text": "수정" , "value": "edit"},
                            ],
                            "items": [],
                            "totalRows":0,
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
                            "certificateStatus": [
                                 {"text":"보유", "value":"T"},
                                 {"text":"없음", "value":"F"},
                            ],
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
                                {"text":"1", "value": 0.01},
                                {"text":"2", "value": 0.02},
                                {"text":"3", "value": 0.03},
                                {"text":"4", "value": 0.04},
                                {"text":"5", "value": 0.05},
                                {"text":"6", "value": 0.06},
                                {"text":"7", "value": 0.07},
                                {"text":"8", "value": 0.08},
                                {"text":"9", "value": 0.09},
                                {"text":"10", "value": 0.10},
                                {"text":"11", "value": 0.11}
                            ],
                        },
                        "query": {
                            "id":null,
                            "name": "",
                            "job": "",
                            "skill": "",
                            "career":null,
                            "career1":null,
                            "career2":null,
                            "degree":"",
                            "sterm": "",
                            "eterm": "",
                            "localPlace":"",
                            "detailLocalPlace": "",
                            "prsnl":"",
                            "status":"",
                            "salary":"",
                            "term":"",
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
                        "localPlace":{
                            "items":[
                                {"text": "전체", "value": null}
                            ]
                        },
                        "detailLocalPlace":{
                            "items":[
                                {"text": "전체", "value": null}
                            ]
                        },
                        "education":{
                            "items":[]
                        },
                        "status":{
                            "items":[
                                {"text": "현황", "value": null},
                                {"text": "섭외", "value": "A"},
                                {"text": "완료", "value": "C"},
                                {"text": "면접", "value": "I"},
                                {"text": "투입", "value": "P"}
                            ]
                        }
                    }
                };
            },
            "computed": {
               iconDegree() {
                      if(this.degreeAllSelect) return 'mdi-close-box';
                   if(this.degreeSelect) return 'mdi-minus-box';
                   return 'mdi-checkbox-blank-outline'
            },

            },
            "watch": {
                "project.dataTable.options.page": {
                    "handler": async function(n, o) {
                        await this.setProjectInfoList();
                    },
                    "deep": true
                },
                "project.dataTable.options.itemsPerPage": {
                    "handler": async function(n, o) {
                        await this.setProjectInfoList();
                    },
                    "deep": true
                },
                "project.dataTable.options.sortDesc": {
                    "handler": async function (n, o) {
                        await this.setProjectInfoList();
                    },
                    "deep": true
                },
                "project.dataTable.local":{
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
                },
                "project.query.job": {
                    "handler": async function () {
                        var self = this;
                        let items,
                            skill = self.select.job.items.find(e=>e.value == self.project.query.job);

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
                        self.project.query.skill = [];
                    }
                },
                "project.query.localPlace": {
                    "handler": async function(n, o){
                        let detailLocal = this.select.localPlace.items.find(e=>e.value == this.project.query.localPlace);

                        if(o != null){
                            this.project.query.detailLocalPlace= [];
                        }
                        this.select.detailLocalPlace.items=[];
                        let items = (await ito.api.common.code.getCodeList({
                            "idStartLike":"006",
                            "status": "T",
                            "detailLocal": detailLocal !== undefined ? detailLocal.text : null,
                            "rowSize": 1000000
                        })).data.items.map(e => ({"text": e.name, "value": e.id}))
                        items = items.filter(e => e.value.length > 5)
                        this.select.detailLocalPlace.items.push( {"text": "전체", "value": null}, ...items)
                    }
                }
            },
            "methods": {
                "loadEducation": async function() {
                    var self = this;
                    let items;
                    items = (await ito.api.common.code.getCodeList({
                        "parentId": "007",
                        "sort": ["ranking, asc"],
                        "rowSize": 1000000
                    })).data.items.map(e => ({"text": e.name, "value": e.id}));
                    self.select.education.items.push(...items);
                },
               "loadLocalPlace": async function(){
                    var self = this;
                    let items;
                    items = (await ito.api.common.code.getCodeList({
                        "parentId": "006",
                        "sort": ["ranking, asc"],
                        "rowSize": 1000000
                    })).data.items.map(e => ({"text": e.name, "value": e.id}));
                    self.select.localPlace.items.push(...items);
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

                //프로젝트 내용 수정시 필요
                "addProjectInfo": function(){
                    this.$router.push({
                        "path": "/main/admin/project-info-form",
                    })
                },
                "editProjectInfo": function(value){
                    console.log(value);
                    this.$router.push({
                        "path": "/main/admin/project-info-form",
                        "query": {
                            "id": value.id
                        }
                      });
                },
                "detailProjectInfo": function(value){
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
                    var deleteList = [];

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
                                params.page = self.project.dataTable.options.page;
                                params.size = self.project.dataTable.options.itemsPerPage;
                                params.name = !_.isEmpty(self.project.query.name) ? self.project.query.name : null;
                                params.job = !_.isEmpty(self.project.query.job) ? self.project.query.job : null;
                                params.skill = !_.isEmpty(self.project.query.skill) ? self.project.query.skill : null;
                                params.degree = !_.isEmpty(self.project.query.degree) ? self.project.query.degree : null;
                                params.career = _.isEmpty(self.project.query.career1) && _.isEmpty(self.project.query.career2) ? null : String(self.project.query.career1 + self.project.query.career2);
                                params.sterm = !_.isEmpty(self.project.query.sterm) ? self.project.query.sterm : null;
                                params.eterm = !_.isEmpty(self.project.query.eterm) ? self.project.query.eterm : null;
                                params.local = !_.isEmpty(self.project.query.LocalPlace) ? self.project.query.LocalPlace : null;
                                params.detailLocal = !_.isEmpty(self.project.query.detailLocalPlace) ? self.project.query.detailLocalPlace : null;
                                params.prsnl = !_.isEmpty(self.project.query.prsnl) ? Number(self.project.query.prsnl) : null;
                                params.status = !_.isEmpty(self.project.query.status) ? self.project.query.status : null;
                                params.salary = !_.isEmpty(self.project.query.salary) ? Number(self.project.query.salary) : null;
                                params.sort=ito.util.sort(self.project.dataTable.options.sortBy, self.project.dataTable.options.sortDesc);

                                self.project.dataTable.loading = true;
                                return ito.api.common.project.getProjectList(params);
                            })
                            .then(function (response) {
                                //데이터값 인계
                                var data = response.data;
                                self.project.dataTable.items = data.items;
                                self.project.dataTable.serverItemsLength = data.totalElements;
                                self.project.dataTable.items.forEach(e => {
                                    e.career = e.career+"년"
                                    e.salary = e.salary+"만원"
                                    switch(e.status){
                                        case 'A':
                                             e.status = "섭외"; break;
                                        case 'C':
                                             e.status = "완료"; break;
                                        case 'I':
                                             e.status = "면접"; break;
                                        case 'P':
                                             e.status = "투입"; break;
                                    }
                                    switch(e.degree){
                                        case "007001":
                                             e.degree = "무관"; break;
                                        case "007002":
                                             e.degree = "고등학교 졸업"; break;
                                        case "007003":
                                             e.degree = "전문대 졸업"; break;
                                        case "007004":
                                             e.degree = "대학교 졸업"; break;
                                    }
                                    var limitDat, limitDay;
                                    limitDate = moment(e.limitDate).format("MM-DD");
                                    limitDay = moment(e.limitDate).diff(moment(), "day");

                                    if(limitDay < 0) e.limitDate = limitDate + " (D+" + Math.abs(limitDay) + ")";
                                    else e.limitDate = limitDate + " (D-" + limitDay + ")";



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
                    query.detailLocalPlace = routeQuery.detailLocalPlace ? routeQuery.detailLocalPlace : this.project.query.detailLocalPlace;
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
                    this.project.query.detailLocalPlace = query.detailLocalPlace;
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
//                    query.career = String(this.project.query.career);
                    query.degree = String(this.project.query.degree);
                    query.sterm = String(this.project.query.sterm);
                    query.eterm = String(this.project.query.eterm);
                    query.detailLocalPlace = String(this.project.query.detailLocalPlace);
                    query.status = String(this.project.query.status);
                    query.salary = String(this.project.query.salary);

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
                                self.project.dataTable.local=""
                                self.project.query.name = "";
                                self.project.query.job = "";
                                self.project.query.skill = "";
                                self.project.query.career = "";
                                self.project.query.degree = "";
                                self.project.query.sterm = "";
                                self.project.query.eterm = "";
                                self.project.query.detailLocal = "";
                                self.project.query.prsnl = "";
                                self.project.query.status = "";
                                self.project.query.slary = "";
                            })
                            .then(function () {
                                return self.search();
                            })
                            .then(function () { resolve(); });
                    });
                },
                "toggleDegree": function() {
                    this.$nextTick(() => {
                        if(this.degreeAllSelect) {
                            this.project.query.degree = [];
                        } else {
                            this.project.query.degree = this.select.education.items.slice();
                        }
                    })
                }
            },
            "mounted": function () {
                    Promise.all([
                    this.loadEducation(),
                    this.loadLocalPlace(),
                    this.loadJobItems(),
                    this.setQuery(),
                    this.replaceQuery(),
                    this.setProjectInfoList()
                ]);
            }

        });
    });
});