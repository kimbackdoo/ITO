var ProfileMainComponent;
ProfileMainComponent = Vue.component('profile-main-component', async function (resolve) {
    resolve({
        "template": (await axios.get("/vue/page/main/user/profile/main.html")).data,
        "data": function () {
            return {
                "data": {
                    "paginationList": [
                        {"text": "5개 보기", "value": 5},
                        {"text": "10개 보기", "value": 10},
                        {"text": "15개 보기", "value": 15},
                        {"text": "20개 보기", "value": 20},
                        {"text": "25개 보기", "value": 25},
                        {"text": "30개 보기", "value": 30},
                        {"text": "전체 보기", "value": null}
                    ]
                },
                "profile": {
                    "panels": {
                        "list": [0]
                    },
                    "dataTable": {
                        "selected": [],
                        "headers": [
                            {"text": "이름", "sortable": true, "value": "name", "align": "center"},
                            {"text": "직업", "sortable": true, "value": "job", "align": "center"},
                            {"text": "기술", "sortable": true, "value": "skill", "align": "center"},
                            {"text": "생년월일(나이)", "sortable": true, "value": "birthDate", "align": "center"},
                            {"text": "경력기간", "sortable": true, "value": "career", "align": "center"},
                            {"text": "희망월급여", "sortable": true, "value": "pay", "align": "center"},
                            {"text": "공개여부", "sortable": true, "value": "status", "align": "center"},
                        ],
                        "items": [],
                        "options": {
                            "page": 1,
                            "itemsPerPage": 10,
                            "sortBy": [],
                            "sortDesc": [],
                        },
                        "loading": false,
                        "totalRows": 0,
                    },
                    "pagination": {
                        "length": 10,
                        "totalVisible": 10
                    },
                }
            };
        },
        "watch": {
            "profile.dataTable.options.page": {
                "handler": async function (newValue, oldValue) {
                    await this.loadProfileList();
                },
                "deep": true
            },
            "profile.dataTable.options.itemsPerPage": {
                "handler": async function(newValue, oldValue) {
                    this.profile.dataTable.options.page = 1;
                    await this.loadProfileList();
                },
                "deep": true
            },
            "profile.dataTable.option.sortDesc": {
                "handler": async function(newValue, oldValue) {
                    await this.loadProfileList();
                },
                "deep": true
            }
        },
        "methods": {
            "init": async function() {
                await this.loadProfileList();
            },
            "loadProfileList": async function() {
                let self = this,
                    profileList;

                self.profile.dataTable.selected = [];
                self.profile.dataTable.loading = true;

                profileList = (await ito.api.common.profile.getProfileList({
                    "page": this.profile.dataTable.options.page,
                    "rowSize": this.profile.dataTable.options.itemsPerPage,
                    "sort": ito.util.sort(self.profile.dataTable.options.sortBy, self.profile.dataTable.options.sortDesc)
                })).data;

                self.profile.dataTable.totalRows = profileList.totalRows;
                self.profile.dataTable.items = [];
                profileList.items.forEach((e, i) => {
                    self.profile.dataTable.items.push({
                        "name": e.name,
                        "job": e.job,
                        "skill": e.skill,
                        "birthDate": e.birthDate,
                        "career": e.career,
                        "pay": e.pay,
                        "status": e.status
                    });
                });
                self.profile.dataTable.loading = false;
            },
            "deleteProfile": async function() {
                var self = this,
                    deleteList = [];

                self.profile.dataTable.selected.forEach(e => {
                    deleteList.push(e.id);
                })

                if(await ito.confirm("삭제하시겠습니까?")){
                    await ito.api.common.profile.removeProfileList(deleteList);
                    await ito.alert({"text": "삭제되었습니다."});

                    self.profile.dataTable.options.page = 1;
                    self.loadProfileList();
                }
            },
            "handleClick": function(value) {
                this.$router.push({
                    "path": "/main/user/input-profile",
                    "query": {
                        "id": value.id
                    }
                });
            },
        },
        "mounted": function () {
            this.init();
        }
    });
});