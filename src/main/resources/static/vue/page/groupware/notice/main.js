var GroupwareNoticePage;
GroupwareNoticePage = Vue.component('groupware-notice-page', async function (resolve) {
    resolve({
        "template": (await axios.get("/vue/page/groupware/notice/main.html")).data,
        // "data": function() {
        //
        // },
    });
});