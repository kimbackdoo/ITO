var MainAdminProjectDetailPage = Vue.component('main-admin-project-detail-page', function (resolve, reject) {
    axios.get("/vue/page/main/admin/project-list/main-detail.html").then(function (response) {
         resolve({
            "template": response.data,
            "data": function() {
                return {
                    "project": {
                        "panels": {
                            "search": [0],
                            "list": [0]
                        },
                        "dataTable": {
                            "headers": [
                                {"text": "이름", "value": "name"},
                                {"text": "전화번호", "value": "phoneNumber"},
                                {"text": "직종",  "value": "jobType"},
                                {"text": "기술",  "value": "skill"},
                                {"text": "학력", "value": "education"},
                                {"text": "경력",  "value": "career"},
                                {"text": "자격증 유무", "value": "certificateStatus"},
                                {"text": "생년월일(나이)",  "value": "birthDate"},
                                {"text": "희망 급여",  "value": "pay"},
                                {"text": "투입여부",  "value": "inputStatus"},
                                {"text": "업무 시작 가능일",  "value": "workableDay"}
                            ],
                        },
                        "items": {
                            "id":null,
                            "name": "",
                            "skill": "",
                            "degree":"",
                            "career":"",
                            "sterm": "",
                            "eterm": "",
                            "local": "",
                            "detailLocal": "",
                            "postcode": "",
                            "address":"",
                            "detailAddress": "",
                            "prsnl":"",
                            "status":"",
                            "slary":"",
                        }
                    },
                    "person":{
                        "dataTable": {
                            "items":[],
                            "totalRows": 0,
                            "page":2,
                            "loading":false,
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
                        },
                        "pagination": {
                            "length": 10,
                            "totalVisible": 10
                        },
                    }


                }
            },
            "watch": {

                "person.dataTable.options.page": {
                    "handler": async function(n, o) {
                        await this.setPersonInfo();
                    },
                    "deep": true
                },
                "person.dataTable.options.itemsPerPage": {
                    "handler": async function(n, o) {
                        await this.setPersonInfo();
                    },
                    "deep": true
                },
                "person.dataTable.options.sortDesc": {
                    "handler": async function (n, o) {
                        await this.setPersonInfo();
                    },
                    "deep": true
                }

            },
            "methods":{

                "setProjectInfo": async function(){
                    var self =this;
                    var id = await self.$route.query.id;
                        return new Promise(function (resolve, reject) {
                            Promise.resolve()
                                .then(function () {
                                    return ito.api.common.project.getProject(id);
                                })
                                .then(function (response) {
                                    self.project.items = response.data;
                                })
                                .then(function () { resolve(); });
                        });
                },

                "setPersonInfo": async function(){
                    var self = this;
                    var projectId = await self.$route.query.id;
                    var params = {};
                    params.projectId = !_.isEmpty(projectId) ? projectId : null;
                    self.person.dataTable.loading = true;
                    var data = (await ito.api.common.projectPerson.getProjectPersonList(params)).data
                    self.person.dataTable.items = data.items.map(e=> e.person);
                    self.person.dataTable.totalRows = data.totalRows;
                    console.log(" items 값  출력      " + self.person.dataTable.items)
                    self.person.dataTable.loading = false;
                },

            },
            "mounted": async function () {
                var self = this;
                await self.setProjectInfo();
                await self.setPersonInfo();
            }
        });
    });

});
