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
                    ],
                },
                "profile": {
                    "panels": {
                        "list": [0]
                    },
                    "selected": [],
                    "dataTable": {
                        "headers": [
                            {"text": "이름", "value": "name",},
                            {"text": "직업", "value": "job",},
                            {"text": "기술", "value": "skill",},
                            {"text": "생년월일(나이)", "value": "birthDate",},
                            {"text": "총 경력", "value": "totalCareer",},
                            {"text": "희망월급여", "value": "pay",},
                            {"text": "공개여부", "value": "status",},
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
            "handleClick": function(value) {
                this.$router.push({
                    "path": "/main/user/input-profile",
                    "query": {
                        "id": value.userProfId
                    }
                });
            },
            "init": async function() {
                await this.loadProfileList();
            },
            "loadProfileList": async function() {
                let self = this,
                    profileList;

                self.profile.dataTable.loading = true;

                profileList = (await ito.api.common.profile.getProfileList({
                    "page": self.profile.dataTable.options.page,
                    "rowSize": self.profile.dataTable.options.itemsPerPage,
                    "sort": ito.util.sort(self.profile.dataTable.options.sortBy, self.profile.dataTable.options.sortDesc)
                })).data;

                self.profile.dataTable.totalRows = profileList.totalRows;
                self.profile.dataTable.items = profileList.items;

                for(let i=0; i<self.profile.dataTable.items.length; i++) {
                    if(self.profile.dataTable.items[i].status==='T') self.profile.dataTable.items[i].status = '공개';
                    else self.profile.dataTable.items[i].status = '비공개';
                }

                self.profile.dataTable.loading = false;
            },
            "deleteProfileList": async function() {
                var self = this,
                    deleteList = [];

                self.profile.selected.forEach(e => {
                    deleteList.push(e.userProfId);
                });

                if(self.profile.selected.length == 0)
                    await ito.alert("삭제할 프로필이 없습니다.");
                else if(await ito.confirm("삭제하시겠습니까?")){
                    await ito.api.common.profile.removeProfileList(deleteList);
                    await ito.alert("삭제되었습니다.");

                    self.profile.dataTable.options.page = 1;
                    self.loadProfileList();
                }
            },
        },
        "mounted": function () {
            this.init();
        }
    });
});