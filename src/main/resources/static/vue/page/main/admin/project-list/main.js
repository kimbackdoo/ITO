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
                                {"text": "프로젝트명", "value": "name","width": 120,"align": "center", cellClass:"text-truncate"},
                                {"text": "직종", "value": "job","width": 120,"align": "center", cellClass:"text-truncate"},
                                {"text": "기술", "value": "skill","width": 120,"align": "center", cellClass:"text-truncate"},
                                {"text": "학위요건", "value": "degree","width": 120,"align": "center", cellClass:"text-truncate"},
                                {"text": "경력요건", "value": "career","width": 120,"align": "center", cellClass:"text-truncate"},
                                {"text": "프로젝트 시작", "value": "sterm","width": 120,"align": "center", cellClass:"text-truncate"},
                                {"text": "프로젝트 끝 ", "value": "eterm","width": 120,"align": "center", cellClass:"text-truncate"},
                                {"text": "장소(시,구)", "value": "local","width": 120,"align": "center", cellClass:"text-truncate"},
                                {"text": "장소(시,구)", "value": "detailLocal","width": 120,"align": "center", cellClass:"text-truncate"},
                                {"text": "필요인원", "value": "prsnl","width": 120,"align": "center", cellClass:"text-truncate"},
                                {"text": "현황", "value": "status","width": 120,"align": "center", cellClass:"text-truncate"},
                                {"text": "희망 급여", "value": "salary","width": 120,"align": "center", cellClass:"text-truncate"},
                                {"text": "모집마감일", "value": "limitDate","width": 120,"align": "center", cellClass:"text-truncate"},
                                {"text": "수정" , "value": "edit","type": "icon","width": 120,"align": "center", cellClass:"text-truncate"},
                            ],
                            "cell": {
                                "icon": {
                                    "edit": {
                                        "title": "mdi-pencil"
                                    }
                                }
                            },
                            "items": [],
                            "totalRows":0,
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
                            "id":"",
                            "name": "",
                            "job": "",
                            "skill": "",
                            "skillList": [],
                            "career":"",
                            "career1":"",
                            "career2":"",
                            "degree":"",
                            "sterm": "",
                            "eterm": "",
                            "local":"",
                            "detailLocal": "",
                            "prsnl":"",
                            "status":"",
                            "salary":"",
                            "term": "",
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
                        "local":{
                            "items":[
                                {"text": "전체", "value": null}
                            ]
                        },
                        "detailLocal":{
                            "items":[
                                {"text": "전체", "value": null}
                            ]
                        },
                        "education":{
                            "items":[]
                        },
                        "status":{
                            "items":[
                                {"text": "섭외중", "value": 'A'},
                                {"text": "섭외완료", "value": 'C'},
                                {"text": "인터뷰", "value": 'I'},
                                {"text": "투입중", "value": 'P'},
                                {"text": "이외", "value": 'N'}
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
                "project.query.local": {
                    "handler": async function(n, o){
                        let detailLocal = this.select.local.items.find(e=>e.value == this.project.query.local);

                        if(o != null){
                            this.project.query.detailLocal= [];
                        }
                        this.select.detailLocal.items=[];
                        let items = (await ito.api.common.code.getCodeList({
                            "idStartLike":"006",
                            "status": "T",
                            "detailLocal": detailLocal !== undefined ? detailLocal.text : null,
                            "rowSize": 1000000
                        })).data.items.map(e => ({"text": e.name, "value": e.id}))
                        items = items.filter(e => e.value.length > 5)
                        this.select.detailLocal.items.push( {"text": "전체", "value": null}, ...items)
                    }
                }
            },
            "methods": {
                "delimit": function(v) {
                    let reducer = (a, e) => [...a, ...e.split(/[, ]+/)]
                    this.user.query.skillList = [...new Set(v.reduce(reducer, []))]
                },
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
                    self.select.local.items.push(...items);
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
                    this.$router.push({
                        "path": "/main/admin/project-info-form",
                        "query": {
                            "id": value.item.id
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

               "deleteProjectInfoList": async function(items){
                    var self = this;
                    var param = {};
                    let deleteList = items.map(e=> e.id);

                    console.log("삭제할 리스트 id 값들  "+deleteList);

                    if(items.length == 0){
                        await ito.alert("삭제할 항목이 없습니다.")
                    }else{
                        if(await ito.confirm("삭제하시겠습니까?")){
                            for(var i in deleteList){

                               param.projectId = deleteList[i];
                                console.log(param.projectId)
                               var projectParam=[];
                               var person = (await ito.api.common.projectPerson.getProjectPersonList(param)).data
                                if(person != null){
                                    console.log(person.items)
                                   person.items.forEach(e=> {
                                           projectParam.push({"personId": e.personId,"projectId": deleteList[i] });
                                   })
                                     console.log(projectParam)
                                   await ito.api.common.projectPerson.removeProjectPersonList(projectParam)
                                }
                            }
                            await ito.api.common.project.removeProjectList(deleteList);
                            await ito.alert("삭제되었습니다.")
                        }
                        self.setProjectInfoList();
                    }
                },
                "setProjectInfoList": function (options) {
                    var self = this;
                    return new Promise(function (resolve, reject) {
                        Promise.resolve()
                            .then(function () {
                                var params = {};
                                if(options !== undefined) {
                                    params.page = options.page;
                                    params.rowSize = options.itemsPerPage;
                                    params.sort=ito.util.sort(options.sortBy, options.sortDesc);
                                }
                                params.name = !_.isEmpty(self.project.query.name) ? self.project.query.name : null;
                                params.job = !_.isEmpty(self.project.query.job) ? self.project.query.job : null;
                                params.skill = !_.isEmpty(self.project.query.skill) ? self.project.query.skill : null;
                                params.skillListLike = !_.isEmpty(self.project.query.skillList) ? self.project.query.skillList : [];
                                params.degree = !_.isEmpty(self.project.query.degree) ? self.project.query.degree : null;
                                params.career = _.isEmpty(self.project.query.career1) && _.isEmpty(self.project.query.career2) ? null : String(self.project.query.career1 + self.project.query.career2);
                                params.sterm = !_.isEmpty(self.project.query.sterm) ? self.project.query.sterm : null;
                                params.eterm = !_.isEmpty(self.project.query.eterm) ? self.project.query.eterm : null;
                                params.local = !_.isEmpty(self.project.query.Local) ? self.project.query.Local : null;
                                params.detailLocal = !_.isEmpty(self.project.query.detailLocal) ? self.project.query.detailLocal : null;
                                params.prsnl = !_.isEmpty(self.project.query.prsnl) ? Number(self.project.query.prsnl) : null;
                                params.status = !_.isEmpty(self.project.query.status) ? (self.project.query.status).charAt(0) : null;
                                params.salary = !_.isEmpty(self.project.query.salary) ? Number(self.project.query.salary) : null;

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
                                             e.status = "섭외중"; break;
                                        case 'C':
                                             e.status = "섭외완료"; break;
                                        case 'I':
                                             e.status = "인터뷰"; break;
                                        case 'P':
                                             e.status = "투입중"; break;
                                        case 'N':
                                             e.status = "이외"; break;
                                    }
                                    switch(e.degree){
                                        case "00701":
                                             e.degree = "학력 무관"; break;
                                        case "00702":
                                             e.degree = "고등학교 졸업"; break;
                                        case "00703":
                                             e.degree = "(2~3년제)전문대 졸업"; break;
                                        case "00704":
                                             e.degree = "(4년제)대학교 졸업"; break;
                                        case "00705":
                                             e.degree = "석사"; break;
                                        case "00706":
                                             e.degree = "박사"; break;
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
                    query.career = routeQuery.career ? routeQuery.career : String(this.project.query.career1 + this.project.query.career2);
                    query.degree = routeQuery.degree ? routeQuery.degree : this.project.query.degree;
                    query.sterm = routeQuery.sterm ? routeQuery.sterm : this.project.query.sterm;
                    query.eterm = routeQuery.eterm ? routeQuery.eterm : this.project.query.eterm;
                    query.detailLocal = routeQuery.detailLocal ? routeQuery.detailLocal : this.project.query.detailLocal;
                    query.status=routeQuery.status ? routeQuery.status : this.project.query.status;
                    query.slary=routeQuery.slary ? routeQuery.slary : this.project.query.slary;
                    query.skillList=routeQuery.skillList ? routeQuery.skillList : this.project.query.skillList;
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
                    this.project.query.skillList = query.skillList;
                    this.project.query.career = query.career;
                    this.project.query.degree = query.degree;
                    this.project.query.sterm = query.sterm;
                    this.project.query.eterm = query.eterm;
                    this.project.query.detailLocal = query.detailLocal;
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
                    query.skillList = this.project.query.skillList;
                    query.career = String(this.project.query.career);
                    query.degree = String(this.project.query.degree);
                    query.sterm = String(this.project.query.sterm);
                    query.eterm = String(this.project.query.eterm);
                    query.detailLocal = String(this.project.query.detailLocal);
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
                                self.project.dataTable.local=null;
                                self.project.query.name = null;
                                self.project.query.job = null;
                                self.project.query.skill = null;
                                self.project.query.skillList = [];
                                self.project.query.career = null;
                                self.project.query.career1 = null;
                                self.project.query.career2 = null;
                                self.project.query.degree = null;
                                self.project.query.sterm = null;
                                self.project.query.eterm = null;
                                self.project.query.detailLocal = null;
                                self.project.query.prsnl = null;
                                self.project.query.status = null;
                                self.project.query.salary = null;
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
            "mounted": async function () {
                    Promise.all([
                    await this.loadEducation(),
                    await this.loadLocalPlace(),
                    await this.loadJobItems(),
                    await this.setQuery(),
                    this.replaceQuery(),
                    this.setProjectInfoList()
                ]);
            }

        });
    });
});