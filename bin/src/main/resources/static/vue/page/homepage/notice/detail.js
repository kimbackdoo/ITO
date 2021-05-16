var HomepageNoticeDetailPage;
HomepageNoticeDetailPage = Vue.component("homepage-notice-detail-page", async function (resolve) {
    resolve({
        "template": (await axios.get("/vue/page/homepage/notice/detail.html")).data,
        "data": function () {
            return {
                "data": {
                    "notice": {}
                }
            };
        },
        "watch": {
        },
        "methods": {
            "setNotice": async function () {
                let notice;
                if(this.$route.query.idx !== undefined && this.$route.query.idx !== null && this.$route.query.idx !== "") {
                    console.log(this.$route.query.idx);
                    notice = (await ito.api.app.homepage.notice.getNotice(this.$route.query.idx)).data;
                    console.log(notice);
                    this.data.notice = notice;
                }
            },
            "saveNotice": async function () {
                let notice;
                notice = _.cloneDeep(this.data.notice);
                delete notice.createdDate;
                delete notice.lastModifiedDate;
                if(await ito.confirm("저장하시겠습니까?")) {
                    if(notice.idx !== undefined && notice.idx !== null && notice.idx !== "") {
                        await ito.api.app.homepage.notice.modifyNotice(notice.idx, notice);
                    }else {
                        await ito.api.app.homepage.notice.createNotice(notice);
                    }
                    await ito.alert("저장했습니다.");
                    this.$router.push({path: "/homepages/notices"});
                }
            },
            "removeNotice": async function () {
                let notice;
                notice = _.cloneDeep(this.data.notice);
                if(await ito.confirm("삭제하시겠습니까?")) {
                    await ito.api.app.homepage.notice.removeNotice(notice.idx);
                    await ito.alert("삭제했습니다.");
                    this.$router.push({path: "/homepages/notices"});
                }
            }
        },
        "mounted": async function () {
            this.setNotice();
        },
    });
});