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
                                {"text": "생년월일(나이)",  "value": "birthDate", cellClass:"text-truncate"},
                                {"text": "직종",  "value": "jobType"},
                                {"text": "기술",  "value": "skill"},
                                {"text": "학력", "value": "education"},
                                {"text": "경력",  "value": "career"},
                                {"text": "자격증 유무", "value": "certificateStatus"},
                                {"text": "희망 급여(최저)",  "value": "minPay"},
                                {"text": "희망 급여(최고)",  "value": "maxPay"},
                                {"text": "현황",  "value": "inputStatus"},
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
                            "limitDate":"",
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
                                    self.project.items.career += "년 이상"
                                    self.project.items.prsnl += "명"
                                    switch(self.project.items.status){
                                        case 'A':
                                             self.project.items.status = "섭외중"; break;
                                        case 'C':
                                             self.project.items.status = "섭외완료"; break;
                                        case 'I':
                                             self.project.items.status = "인터뷰"; break;
                                        case 'P':
                                             self.project.items.status = "투입중"; break;
                                        case 'N':
                                             self.project.items.status = "미정"; break;
                                    }
                                    switch(self.project.items.degree){
                                        case "007001":
                                             self.project.items.degree = "학력 무관"; break;
                                        case "007002":
                                             self.project.items.degree = "고등학교 졸업"; break;
                                        case "007003":
                                             self.project.items.degree = "(2~3년제)전문대 졸업"; break;
                                        case "007004":
                                             self.project.items.degree = "(4년제)대학교 졸업"; break;
                                        case "007005":
                                             self.project.items.degree = "석사"; break;
                                        case "007006":
                                             self.project.items.degree = "박사"; break;
                                    }
                                    if(e.birthDate != null){
                                        var v = e.birthDate.slice(0,4);
                                        var age = e.birthDate.slice(2,4);
                                        if(Number(v) < 2000){
                                            var a = 2000 - Number(v);
                                            e.birthDate =  age+"년생 "+ "("+(Number(moment().format("YY")) + a + 1)+")";
                                        }
                                        else{
                                            var a = Number(v) - 2000;
                                            e.birthDate = age+"년생 " + "("+(a + 1 )+")";
                                        }
                                    }

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
                    self.person.dataTable.items = data.items;
                    self.person.dataTable.items.forEach(e =>{
                        e.certificateStatus =(e.certificateStatus == "T") ? "있음" : "없음"

                    })
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
