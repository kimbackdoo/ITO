var ProfileMainComponent = Vue.component("profile-main-page", async function (resolve) { resolve({
        "template": (await axios.get("/vue/component/app/profile/main.html")).data,
        "data": function () {
            return {
                "data": {
                    "search": {
                    },
                    "query": {
                        "businessClass": null,
                        "workDivision": null,
                        "hostName": null
                    }
                },
                "select" : {
                    "businessClass": {
                        "items": [
                            {"text": "전체", "value": null}
                        ]
                    },
                    "workDivision": {
                        "items": [
                            {"text": "전체", "value": null},
                        ]
                    },
                    "loading": false
                },
                "btn": {
                    "searchTable": {
                        "loading": false
                    },
                    "downloadTable": {
                        "loading": false
                    },
                },
                "policy": {
                    "panels": {
                        "list": [0]
                    },
                    "selected" : [],
                    "dataTable": {
                        "headers": [
                            {"text": "이름", "sortable": true, "value": "clientName", "align": "center", "width": "120"},
                            {"text": "직업", "sortable": true, "value": "clientJob", "align": "center", "width": "80"},
                            {"text": "기술", "sortable": true, "value": "clientSkill", "align": "center", "width": "80"},
                            {"text": "생년월일(나이)", "sortable": true, "value": "clientBirth", "align": "center", "width": "80"},
                            {"text": "경력기간", "sortable": true, "value": "clientCareer", "align": "center", "width": "120"},
                        ],
                        "query": {
                            "businessClass": null,
                            "workDivision": null,
                            "hostName": null
                        },
                        "items": [],
                        "options": {
                            "page": 1,
                            "itemsPerPage": 10,
                            "sortBy": [],
                            "sortDesc": [],
                            "groupBy": [],
                            "groupDesc": [],
                            "multiSort": true,
                            "mustSort": false
                        },
                        "totalRows": 0,
                        "loading": false
                    }
                },
            };
        },
        "watch": {
            "profile.dataTable.options.page": {
                "handler": async function (n, o) {
                    await this.loadProfileList();
                },
                "deep": true
            },
            "profile.dataTable.options.itemsPerPage": {
                "handler": async function (n, o) {
                    await this.loadProfileList();
                },
                "deep": true
            },"profile.dataTable.options.sortDesc": {
                "handler": async function (n, o) {
                    await this.loadProfileList();
                },
                "deep": true
            }
        },
        "methods": {
            "init": async function () {
                await this.loadBusinessClassItems();
                await this.loadWorkDivisionItems();
                await this.loadProfileList();
            },
            "loadBusinessClassItems": async function () {
                let items;
                items = (await ito.api.common.profile.getProfileList({
                    "parentId": 8,
                    "sort": ["orderNo,asc"],
                    "rowSize": 100000
                })).data.items.map(e=>({"text": e.value, "value": e.name}));
                this.select.businessClass.items.push(
                    ...items,
                    {"text": "기타", "value": [...items.map(e=>e.value)]}
                );
            },
            "loadWorkDivisionItems":async function () {
                this.select.workDivision.items.push(
                    ...(await ito.api.common.profile.getProfileList({
                        "parentId": 8,
                        "sort": ["orderNo,asc"],
                        "rowSize": 100000
                    })).data.items.map(e=>({"text": e.value, "value": e.name}))
                );
            },
            "loadProfileList": async function () {
                let self = this,
                    profileList, param;
                this.profile.dataTable.loading = true;
                console.log(typeof(this.profile.dataTable.query.businessClass))
                if(this.profile.dataTable.query.businessClass !== null && typeof(this.profile.dataTable.query.businessClass) === "object") {
                    param = {
                        "notBusinessClassList": this.profile.dataTable.query.businessClass
                    }
                }else {
                    param = {
                        "businessClass": this.profile.dataTable.query.businessClass
                    }
                }
                console.log(this.profile.dataTable.query,param);
                profileList = (await ito.api.common.profile.getProfileList({
                    "page": this.profile.dataTable.options.page,
                    "rowSize": this.profile.dataTable.options.itemsPerPage,
                    "clientNameLike": this.profile.dataTable.query.hostName,
                    "workDivision": this.profile.dataTable.query.workDivision,
                    ...param,
                    "referenceDate": moment().format("YYYY-MM-DD"),
                    "sort": ito.util.sort(this.profile.dataTable.options.sortBy, this.profile.dataTable.options.sortDesc)
                })).data;
                this.profile.dataTable.totalRows = profileList.totalRows;
                this.profile.dataTable.items = [];
                profileList.items.forEach((e, i)=>{
                    self.profile.dataTable.items.push({
                        "clientName": e.clientName,
                        "clientJob": e.clientJob ,
                        "clientSkill": e.clientSkill,
                        "clientBirth": e.clientBirth,
                        "clientCareer": e.clientCareer,
                    });
                });
                this.profile.dataTable.loading = false;
            },
            // "searchPolicyClientList": function () {
            //     this.policy.dataTable.query = _.cloneDeep(this.data.query);
            //     if(this.policy.dataTable.options.page !== 1) {
            //         this.policy.dataTable.options.page = 1;
            //     }else {
            //         this.loadPolicyClientList();
            //     }
            // },
            // "downloadTable": async function () {
            //     let param;
            //     if(this.profile.dataTable.query.businessClass !== null && typeof(this.policy.dataTable.query.businessClass) !== "number") {
            //         param = {
            //             "notBusinessClassList": this.profile.dataTable.query.businessClass
            //         }
            //     }else {
            //         param = {
            //             "businessClass": this.profile.dataTable.query.businessClass
            //         }
            //     }
            //     await HiInfra.api.common.policyClient.downloadPolicyClientListXlsx({
            //         "clientNameLike": this.policy.dataTable.query.hostName,
            //         "workDivision": this.policy.dataTable.query.workDivision,
            //         ...param,
            //         "referenceDate": moment().format("YYYY-MM-DD"),
            //         "rowSize": 100000000
            //     });
            // },
        },
        "mounted": function () {
            this.init();
        }
    });
});