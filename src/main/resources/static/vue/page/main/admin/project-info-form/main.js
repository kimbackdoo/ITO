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
                            "admin_proj_id":0,
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
            },
            "mounted": function () {
            }
        });
    });
});