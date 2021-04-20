var GroupwareNoticePage;
GroupwareNoticePage = Vue.component('groupware-notice-page', async function (resolve) {
    resolve({
        "template": (await axios.get("/vue/page/groupware/notice/main.html")).data,
        "data": function() {
            return {
                "notice": {
                    "panels": {
                        "list": [0]
                    }
                },
                "dataTable": {
                    "headers": [
                        {"text": "제목", "value": "title", "width": 120, "align": "center", cellClass:"text-truncate"},
                        {"text": "내용", "value": "content", "width": 120, "align": "center", cellClass:"text-truncate"},
                        {"text": "공지일자", "value": "createDate", "width": 120, "align": "center", cellClass:"text-truncate"}
                    ],
                    "cell": {
                        "icon": {
                            "edit": {
                                "title": "mdi-pencil"
                            }
                        }
                    },
                    "items": [],
                    "totalRows": 0,
                    "loading": false,
                },
            };
        },
        "methods": {
            "loadNoticeList": async function() {
                let notice;
            },
            "removeNoticeList": async function() {

            },
        }
    });
});