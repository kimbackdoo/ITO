var HomepageNoticeMainPage;
HomepageNoticeMainPage = Vue.component("homepage-notice-main-page", async function (resolve) {
    resolve({
        "template": (await axios.get("/vue/page/homepage/notice/main.html")).data,
        "data": function () {
            return {
                "notice": {
                    "panels": {
                        "list": [0]
                    },
                },
                "dataTable": {
                    "headers": [
                        {"text": "제목", "value": "title","width": 120,"align": "center", cellClass:"text-truncate"},
                        {"text": "내용", "value": "content","width": 120,"align": "center", cellClass:"text-truncate"},
                        {"text": "공지일자", "value": "createdDate","width": 120,"align": "center", cellClass:"text-truncate"},
                    ],
                    "cell": {
                        "icon": {
                            "edit": {
                                "title": "mdi-pencil"
                            }
                        }
                    },
                    "items": [],
                    "totalRows":0,
                    "loading": false,
                },
            };
        },
        "watch": {
        },
        "methods": {
            "loadNoticeList": async function () {
                let notice;
                notice = (await ito.api.app.homepage.notice.getNoticeList()).data;
                this.dataTable.totalRows = notice.totalRows;
                this.dataTable.items = notice.items;

            }
        },
        "mounted": async function () {
            this.loadNoticeList();
        },
    });
});