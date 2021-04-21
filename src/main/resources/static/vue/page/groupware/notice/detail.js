var GroupwareNoticeDetailPage;
GroupwareNoticeDetailPage = Vue.component('roupware-notice-detail-page', async function (resolve) {
    resolve({
        "template": (await axios.get("/vue/page/groupware/notice/detail.html")).data,
        "data": function() {
            return {
                "data": {
                    "notice": {}
                }
            };
        },
        "methods": {

        }
    });
});
