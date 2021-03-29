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
                            "adminProjId":null,
                            "name": "",
                            "skill": "",
                            "degree":"",
                            "career":"",
                            "sterm": "",
                            "eterm": "",
                            "address":"",
                            "place": "",
                            "prsnl":"",
                            "status":"",
                            "slary":"",
                        }
                    },
                    "person":{
                        "dataTable": {
                            "items":[],
                            "itemsPerPage":0,
                            "totalRows": 0,
                            "page":2,

                        },
                        "pagination": {
                            "length": 10,
                            "totalVisible": 10
                        },
                    }


                }
            },
            "watch": {

/*                 "user.dataTable.itemsPerPage": {
                    "handler": function (newValue, oldValue) {
                        Promise.resolve()
                            .then(this.setUserInfoList)
                            .then(this.replaceQuery)
                    }
                }
            },
*/




            },
            "methods":{

                "setProjectInfo": async function(){
                    var self =this;

                    var id = await self.$route.query.adminProjId;
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



                "serPersonInfo": async function() {
                    var self = this;
                    var projectId = await self.$route.query.adminProjId;
                        return new Promise(function (resolve, reject){
                            Promise.resolve()
                                .then(function (){
                                    // return projectId 값으로 tb_proj_person 을 찾아서 해당 프로젝트에 대한 id 값들을 가져온다.
                                    var params = {};
                                    params.projectId = !_.isEmpty(projectId) ? projectId : null;
                                    return ito.api.common.projectPerson.getProjectPersonList(params)
                                })
                                .then(function (response) {
                                    self.person.items = response.data;
                                }).then(function (){
                                    resolve();
                                })
                        })
                },
                "getPersonInfoList": async function(){
                    var self = this;
                }

            },
            "mounted": async function () {
                var self = this;
                console.log(self.$route.query.adminProjId)
                await self.setProjectInfo();
                await self.serPersonInfo();
            }
        });
    });

});
