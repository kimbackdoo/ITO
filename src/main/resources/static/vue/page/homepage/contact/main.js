var HomepageContactMainPage;
HomepageContactMainPage = Vue.component("homepage-contact-main-page", async function (resolve) {
    resolve({
        "template": (await axios.get("/vue/page/homepage/contact/main.html")).data,
        "data": function () {
            return {
                "contact": {
                    "panels": {
                        "list": [0]
                    },
                },
                "dataTable": {
                    "headers": [
                        {"text": "제목", "value": "title","width": 120,"align": "center", cellClass:"text-truncate"},
                        {"text": "내용", "value": "content","width": 120,"align": "center", cellClass:"text-truncate"},
                        {"text": "이름", "value": "name","width": 120,"align": "center", cellClass:"text-truncate"},
                        {"text": "회사명", "value": "company","width": 120,"align": "center", cellClass:"text-truncate"},
                        {"text": "전화번호", "value": "phone","width": 120,"align": "center", cellClass:"text-truncate"},
                        {"text": "이메일", "value": "email","width": 120,"align": "center", cellClass:"text-truncate"},
                        {"text": "문의 타입", "value": "contactType","width": 120,"align": "center", cellClass:"text-truncate"},
                        {"text": "문의일자", "value": "lastModifiedDate","width": 120,"align": "center", cellClass:"text-truncate"},
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
            "loadContactList": async function () {
                let contact;
                contact = (await ito.api.app.homepage.contact.getContactList()).data;
                this.dataTable.totalRows = contact.totalRows;
                this.dataTable.items = contact.items;

            }
        },
        "mounted": async function () {
            this.loadContactList();
        },
    });
});