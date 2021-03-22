var MainAdminProjectFormPage = Vue.component('main-admin-project-form-page', function (resolve, reject) {
    axios.get("/vue/page/main/admin/project-info-form/main.html").then(function (response) {
        resolve({
            "template": response.data,
            "data": function () {
                return {
                    "project": {
                        "jobTypeItems": [ //직종 선택지
                             {"text":"개발자", "value":"개발자"},
                             {"text":"기획자", "value":"기획자"},
                             {"text":"퍼블리셔", "value":"퍼블리셔"},
                             {"text":"디자이너", "value":"디자이너"}
                         ],
                        "addr1": ["강서구","은평구","광진구","서초구","구로구"], //주소1 선택지
                        "addr2": ["중구","서구","대덕구","유성구","동구"], //주소2 선택지
                        "career": [ //경력 선택지
                            {"text":"신입", "value": "0"},
                            {"text":"1년", "value": "1"},
                            {"text":"2년", "value": "2"},
                            {"text":"3년", "value": "3"},
                            {"text":"4년", "value": "4"},
                            {"text":"5년", "value": "5"},
                            {"text":"6년", "value": "6"},
                            {"text":"7년", "value": "7"},
                            ],
                        "degree": [ //학위 선택지
                            {"text": "2년제 졸업", "value": "2년제 졸업"},
                            {"text": "3년제 졸업", "value": "3년제 졸업"},
                            {"text": "4년제 졸업", "value": "4년제 졸업"},
                        ],
                        "needPerson": [ //필요인원수 선택지
                            {"text": "1명", "value": "1"},
                            {"text": "2명", "value": "2"},
                            {"text": "3명", "value": "3"},
                            {"text": "4명", "value": "4"},
                        ],
                        "status": [ //현황 선택지
                            {text: "섭외", "value": "섭외"},
                            {text: "면담", "value": "면담"},
                            {text: "투입", "value": "투입"},
                            {text: "완료", "value": "완료"},
                        ],
                        "query": {
                            "name": "",
                            "jobType": "",
                            "career": "",
                            "period": "",
                            "addr1": "",
                            "addr2": "",
                            "needPerson": "",
                            "status":"",
                        },
                    }
                };
            },
            "methods": {
            },
            "mounted": function () {
            }
        });
    });
});