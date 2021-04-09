var MainAdminProjectFormPage = Vue.component('main-admin-project-form-page', function (resolve, reject) {
    axios.get("/vue/page/main/admin/project-info-form/main.html").then(function (response) {
        resolve({
            "template": response.data,
            "data": function () {
                return {
                    "data": {
                        "project": {}
                    },
                    "rules": {
                        "required": value => !!value || '필수'
                    },
                    "select": {
                        "educationItems": [],
                        "jobTypeItems": [],
                        "skillItems": [],
                        "certificateStatusItems": [
                            {"text":"보유", "value":"T"},
                            {"text":"없음", "value":"F"},
                        ],
                        "careerItems": [
                             {"text":"신입", "value": "0"},
                             {"text":"1년", "value": "1"},
                             {"text":"2년", "value": "2"},
                             {"text":"3년", "value": "3"},
                             {"text":"4년", "value": "4"},
                             {"text":"5년", "value": "5"},
                             {"text":"6년", "value": "6"},
                             {"text":"7년", "value": "7"},
                             {"text":"8년", "value": "8"},
                             {"text":"9년", "value": "9"},
                             {"text":"10년", "value": "10"},
                             {"text":"11년", "value": "11"},
                             {"text":"12년", "value": "12"},
                             {"text":"13년", "value": "13"},
                             {"text":"14년", "value": "14"},
                             {"text":"15년", "value": "15"},
                             {"text":"16년", "value": "16"},
                             {"text":"17년", "value": "17"},
                             {"text":"18년", "value": "18"},
                             {"text":"19년", "value": "19"},
                             {"text":"20년", "value": "20"},
                             {"text":"20년 이상", "value": "99"}
                        ],
                        "needPersonItems":[
                            {"text":"1명", "value": 1},
                            {"text":"2명", "value": 2},
                            {"text":"3명", "value": 3},
                            {"text":"4명", "value": 4},
                            {"text":"5명", "value": 5},
                            {"text":"6명", "value": 6},
                            {"text":"7명", "value": 7},
                            {"text":"8명", "value": 8},
                            {"text":"9명", "value": 9},
                        ],
                        "statusItems": [
                            {"text": "섭외", "value": "A"},
                            {"text": "완료", "value": "C"},
                            {"text": "면접", "value": "I"},
                            {"text": "투입", "value": "P"},
                        ],
                    },
                   "panels": {
                        "search": [0],
                        "list": [0]
                    }
                };
            },
            "watch": {
                "data.project.address":{
                    "handler": async function(value){
                        let id = (await ito.api.common.code.getCodeList({"parentId": "006", "nameLike": value.split(" ")[0], "rowSize": 1, "status": "T"})).data.items[0].id;
                        let id2 = (await ito.api.common.code.getCodeList({"parentId": id, "nameLike": value.split(" ")[1], "rowSize": 1, "status": "T"})).data.items[0].id;
                        this.$set(this.data.project, "local", id);
                        this.$set(this.data.project, "detailLocal", id2);
                    }
                },
                "data.project.job": {
                    "handler": async function (n, o) {
                        let skill = this.select.jobTypeItems.find(e=>e.value == this.data.project.job);

                        if(o !== null) {
                            this.$set(this.data.project, "skill", []);
                        }
                        this.select.skillItems = [];
                        this.select.skillItems = (await ito.api.common.code.getCodeList({
                            "idStartLike": "004",
                            "status": "T",
                            "skill": skill !== undefined ? skill.text : null,
                            "rowSize": 1000000
                        })).data.items.filter(e=> {
                            if(e.id.startsWith("00401")) return e.id.length > 7;
                            else return e.id.length > 5;
                        });
                    }
                }
            },
            "methods": {
                "init": async function() {
                    await this.setProjectInfo();
                    await this.getJobTypeItems();
                    await this.getEducationItems();
                },
                "delimit": function(v) {
                    let reducer = (a, e) => [...a, ...e.split(/[, ]+/)]
                    this.user.query.skillList = [...new Set(v.reduce(reducer, []))]
                },
                "getJobTypeItems": async function () {
                    this.select.jobTypeItems = (await ito.api.common.code.getCodeList({
                        "parentId": "001",
                        "sort": ["ranking,asc"],
                        "rowSize": 1000000,
                        "status": "T"
                    })).data.items.map(e=>({"text": e.name, "value": e.id}));
                },
                "getEducationItems": async function () {
                    this.select.educationItems = (await ito.api.common.code.getCodeList({
                        "parentId": "007",
                        "sort": ["ranking,asc"],
                        "rowSize": 1000000,
                        "status": "T"
                    })).data.items.map(e=>({"text": e.name, "value": e.id}));
                },
                "saveProject": async function() {
                    var self = this;
                    var data = _.cloneDeep(self.data.project);
                    var validate = this.$refs.projectForm.validate();
                    if(!validate) {
                        await ito.alert("필수값을 입력해주세요.");
                    }else if(await ito.confirm("저장하시겠습니까?")) {
                        if(data.id !== undefined && data.id !== null && data.id !== ""){
                            await ito.api.common.project.modifyProject(data.id, data);
                        } else {
                            await ito.api.common.project.createProject(data);
                        }
                        await ito.alert("저장했습니다.");
                        await self.$router.push({"path": "/main/admin/project-lists"});
                    }
                },

                //set 설정 id 값 존재시 그 값들에 대한 값들 불러오기
                "setProjectInfo": async function(){
                    var self =this;

                    var id = await self.$route.query.id;
                    if(id !== undefined) {
                        return new Promise(function (resolve, reject) {
                            Promise.resolve()
                                .then(function () {
                                    return ito.api.common.project.getProject(id);
                                })
                                .then(function (response) {
                                    self.data.project = response.data;
                                })
                                .then(function () { resolve(); });
                        });
                    }
                },
                "setAddress": async function () {
                    var self = this,
                        data,
                        address,
                        detailAddress;
                    data = await ito.util.getPostcode();
                    if (data) {
                        if (data.userSelectedType === "R") {
                            address = data.roadAddress;
                            detailAddress = "";
                            if (data.bname && data.bname !== "") {
                                detailAddress += data.bname;
                            }
                            if (data.buildingName && data.buildingName !== "") {
                                detailAddress += detailAddress !== "" ? ", " + data.buildingName : data.buildingName;
                            }
                            address += detailAddress !== "" ? " (" + detailAddress + ")" : "";
                        } else {
                            address = data.jibunAddress;
                        }
                        this.$set(self.data.project, "postcode", data.zonecode);
                        this.$set(self.data.project, "address", address);
                    }
                }
            },
            "mounted": async function () {
                this.init();
            }
        });
    });
});