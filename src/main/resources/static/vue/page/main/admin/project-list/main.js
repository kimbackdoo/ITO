var MainAdminProjectListPage = Vue.component('main-admin-project-list-page', function (resolve, reject) {
    axios.get("/vue/page/main/admin/project-list/main.html").then(function (response) {
        resolve({
            "template": response.data,
            "data": function () {
                return {
                    "project": {
                        "panels": {
                            "search": [],
                            "list": [0]
                        },
                        "dataTable": { //데이터 목록
                            "headers": [
                                {"text": "프로젝트명", "sortable": false, "value": "pname"},
                                {"text": "직종", "sortable": false, "value": "jobType"},
                                {"text": "필요기술", "sortable": false, "value": "skill"},
                                {"text": "경력요건", "sortable": false, "value": "career"},
                                {"text": "학위요건", "sortable": false, "value": "degree"},
                                {"text": "기간", "sortable": false, "value": "period"},
                                {"text": "장소", "sortable": false, "value": "place"},
                                {"text": "필요인원수", "sortable": false, "value": "needPerson"},
                                {"text": "현황", "sortable": false, "value": "status"},
                        ]},
                        "pagination": { //페이지 표시
                            "length": 10,
                            "totalVisible": 10
                        },
                        "page": 1,
                        "items": [ //예시 데이터
                            {pname: "대학교 홈페이지 개발", jobType: "개발자", skill: "PHP", career: "2년", degree: "3년제 이상", period: "2021-03-22~2021-05-31", place: "중구", needPerson: "3명", status: "투입"},
                            {pname: "배달어플 개발", jobType: "개발자", skill: "JAVA", career: "3년", degree: "4년제 이상", period: "2021-03-22~2021-07-31", place: "서구", needPerson: "4명", status: "섭외"},
                        ],
                        "loading": false,
                        "serverItemsLength": 0,
                        "itemsPerPage": 10,
                        "itemsPerPageItems": [
                            {"text":"3", "value":3},
                            {"text":"5", "value": 5},
                            {"text": "10", "value": 10},
                            {"text": "20", "value": 20},
                            {"text": "30", "value": 30},
                            {"text": "40", "value": 40},
                            {"text": "50", "value": 50}
                        ],
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
                "projectDetail": function(value) {
                    this.$router.push({
                        "path": "/main/admin/project-list/project-detail"
                    })
                },
                "search": function () {
                    var self = this;
                    return new Promise(function (resolve, reject) {
                        Promise.resolve()
                            .then(function () {
                                return self.setUserInfoList();
                            })
                            .then(function () {
                                self.replaceQuery();
                            })
                            .then(function () {
                             resolve();
                         });
                    });
                },
            },
            "mounted": function () {
            }
        });
    });
});