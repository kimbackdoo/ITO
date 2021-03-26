var MainAdminProjectFormPage = Vue.component('main-admin-project-form-page', function (resolve, reject) {
    axios.get("/vue/page/main/admin/project-info-form/main.html").then(function (response) {
        resolve({
            "template": response.data,
            "data": function () {
                return {
                    "project": {
                       "panels": {
                            "search": [0],
                            "list": [0]
                        },
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
                        "needPerson":[
                            {"text":"1명", "value": 1},
                            {"text":"2명", "value": 2},
                            {"text":"3명", "value": 3},
                            {"text":"4명", "value": 4},
                            {"text":"5명", "value": 5},
                            {"text":"6명", "value": 6},
                            {"text":"7명", "value": 7},
                            {"text":"8명", "value": 8},
                            {"text":"9명", "value": 9},
                        ],
                        "query": {
                            "adminProjId":null,
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
                        "default": {
                            "adminProjId":null,
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
                        "degree": [
                            {"text": "2년제", "value": "2년제"},
                            {"text": "3년제", "value": "3년제"},
                            {"text": "4년제", "value": "4년제"},
                        ],
                        "status": [
                            {"text": "투입중", "value": "T"},
                            {"text": "섭외중", "value": "F"},
                        ],
                    }
                };
            },
            "watch": {
                "project.addressValue":{
                    "handler": function(value){
                        this.project.addressSelect=this.project.addressIndex[value];
                    }
                },
            },
            "methods": {
                //저장  , 수정
                "saveProject": async function() {
                    var self = this;
                    var data = self.project.query;
                    console.log("save 함수 실행 ")
                    if(data.adminProjId == null){
                        await ito.api.common.project.createProject(data);
                        console.log("create 실행 완료")
                    } else {
                        await ito.api.common.project.modifyProject(data.adminProjId, data);
                    }
                    await ito.alert("저장 성공!!");
                    await self.$router.back();
                },

                //set 설정 id 값 존재시 그 값들에 대한 값들 불러오기
                "setProjectInfo": async function(){
                    var self =this;

                    var id = await self.$route.query.adminProjId;
                    console.log("파라미터 id 값 출력 :  " +id);
                        return new Promise(function (resolve, reject) {
                            Promise.resolve()
                                .then(function () {
                                    return ito.api.common.project.getProject(id);
                                })
                                .then(function (response) {
                                    self.project.query = response.data;
                                })
                                .then(function () { resolve(); });
                        });
                },
                "reset":function() {
                    var self = this;
                    self.project.addressValue="";
                    self.project.query = self.project.default;
                }

            },
            "mounted": async function () {
                var self = this;
                await self.setProjectInfo();
            }
        });
    });
});