var GroupwareApprovalListPage;
GroupwareApprovalListPage = Vue.component('groupware-approval-list-page', async function (resolve) {
    resolve({
        "template": (await axios.get("/vue/page/groupware/approval-list/main.html")).data,
        "data": function() {
            return {
                "signList": {
                    "panels": {
                        "list": [0]
                    }
                },
                "dataTable": {
                    "headers": [
                        {"text": "신청자", "value": "applicant", "align": "center", "width": "120", cellClass: "text-truncate"},
                        {"text": "구분", "value": "division", "align": "center", "width": "120", cellClass: "text-truncate"},
                        {"text": "휴가기간", "value": "term", "align": "center", "width": "120", cellClass: "text-truncate"},
                        {"text": "팀장 결재", "value": "teamLeaderSign", "align": "center", "width": "120", cellClass: "text-truncate"},
                        {"text": "대표 결재", "value": "dpSign", "align": "center", "width": "120", cellClass: "text-truncate"},
                        {"text": "최종승인날짜", "value": "signDate", "align": "center", "width": "120", cellClass: "text-truncate"},
                    ],
                    "items": [],
                    "totalRows": 0,
                    "loading": false,
                }
            };
        },
        "method": {
            "loadSignList": async function() {

            }
        }
    });
});