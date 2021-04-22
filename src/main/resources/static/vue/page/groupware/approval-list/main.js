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
                        {"text": "신청자", "value": "name", "align": "center", "width": "120", cellClass: "text-truncate"},
                        {"text": "구분", "value": "type", "align": "center", "width": "120", cellClass: "text-truncate"},
                        {"text": "휴가기간", "value": "term", "align": "center", "width": "120", cellClass: "text-truncate"},
                        {"text": "팀장 결재", "value": "teamLeader", "align": "center", "width": "120", cellClass: "text-truncate"},
                        {"text": "대표 결재", "value": "president", "align": "center", "width": "120", cellClass: "text-truncate"},
                        {"text": "최종승인날짜", "value": "approvalDate", "align": "center", "width": "120", cellClass: "text-truncate"},
                    ],
                    "items": [],
                    "totalRows": 0,
                    "loading": false,
                }
            };
        },
        "methods": {
            "init": async function() {
                await this.loadApprovalList();
            },
            "loadApprovalList": async function(options) {
                let self = this, vacationId, vacation, userName, approvalList;

                self.dataTable.loading = true;
                approvalList = (await ito.api.app.approval.getApprovalList({
                    "page": options !== undefined ? options.page : 1,
                    "rowSize": options !== undefined ? options.itemsPerPage : 10,
                    "sort": options !== undefined ? ito.util.sort(options.sortBy, options.sortDesc) : [],
                })).data;

                for(let i=0; i<approvalList.items.length; i++) {
                    vacationId = approvalList.items[i].vacationId;
                    vacation = (await ito.api.app.vacation.getVacationInfo(vacationId)).data;
                    userName = (await ito.api.common.user.getUser(vacation.userId)).data.username;

                    approvalList.items[i].name = userName;
                    approvalList.items[i].term = vacation.sterm + " ~ " + vacation.eterm;

                    switch (vacation.type) {
                        case "M": approvalList.items[i].type = "월차"; break;
                        case "O": approvalList.items[i].type = "반차"; break;
                        case "S": approvalList.items[i].type = "병가"; break;
                        case "E": approvalList.items[i].type = "기타"; break;
                    }
                    switch(approvalList.items[i].teamLeader) {
                        case "T": approvalList.items[i].teamLeader = "O"; break;
                        case "F": approvalList.items[i].teamLeader = "X"; break;
                    }
                    switch(approvalList.items[i].president) {
                        case "T": approvalList.items[i].president = "O"; break;
                        case "F": approvalList.items[i].president = "X"; break;
                    }
                }

                self.dataTable.totalRows = approvalList.totalRows;
                self.dataTable.items = approvalList.items;
                self.dataTable.loading = false;
                console.log(approvalList);
            }
        },
        "mounted": function() {
            this.init();
        }
    });
});