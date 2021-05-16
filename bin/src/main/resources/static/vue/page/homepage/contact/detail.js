var HomepageContactDetailPage;
HomepageContactDetailPage = Vue.component("homepage-contact-detail-page", async function (resolve) {
    resolve({
        "template": (await axios.get("/vue/page/homepage/contact/detail.html")).data,
        "data": function () {
            return {
                "data": {
                    "contact": {},
                },
            };
        },
        "watch": {
        },
        "methods": {
            "setContact": async function () {
                let contact;
                if(this.$route.query.idx !== undefined && this.$route.query.idx !== null && this.$route.query.idx !== "") {
                    contact = (await ito.api.app.homepage.contact.getContact(this.$route.query.idx)).data;
                    this.data.contact = contact;
                }
            },
        },
        "mounted": async function () {
            this.setContact();
        },
    });
});