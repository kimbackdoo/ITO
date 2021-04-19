var GroupwareApprovalListPage;
GroupwareApprovalListPage = Vue.component('groupware-approval-list-page', async function (resolve) {
    resolve({
        "template": (await axios.get("/vue/page/groupware/approval-list/main.html")).data,
        // "data": function() {
        //
        // },
    });
});