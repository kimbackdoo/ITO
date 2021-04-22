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
                "selected": [],
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
            "init": async function() {
                this.loadNoticeList();
            },
            "loadNoticeList": async function(options) {
                let self = this, noticeList;

                self.dataTable.loading = true;
                noticeList = (await ito.api.app.notice.getNoticeList({
                    "page": options !== undefined ? options.page : 1,
                    "rowSize": options !== undefined ? options.itemsPerPage : 10,
                    "sort": options !== undefined ? ito.util.sort(options.sortBy, options.sortDesc) : [],
                })).data;

                console.log(noticeList);

                self.dataTable.totalRows = noticeList.totalRows;
                self.dataTable.items = noticeList.items;
                self.dataTable.loading = false;

                console.log(self.dataTable.items);
            },
            "removeNoticeList": async function() {
                let self = this, removeList = [];

                console.log(self.selected);
            },
        },
        "mounted": function() {
            this.init();
        }
    });
});