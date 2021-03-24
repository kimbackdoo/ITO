
var MainAdminPage = Vue.component('main-admin-userInfo-page', function (resolve, reject) {
    axios.get("/vue/page/main/admin/user-info/main.html").then(function (response) {
        resolve({
            "template": response.data,
            "data": function () {
                return {
                    "dataUpload":{
                        "panels": {
                              "list": [0,1]
                        },
                    "dataTypeList" : [
                        {
                            "label" : "CMDB",
                            "value" : 1,
                        },
                        {
                            "label" : "DB백업",
                            "value" : 2,
                            },
                        {
                            "label" : "복구현황",
                            "value" : 3,
                        },
                        {
                            "label" : "백업구성도",
                            "value" : 4,
                        },
                        ],
                    "selectedFile" : null,
                    "selectedFileName" : null,
                    "seletedType" : 1,
                    "uploadReferenceMonth" : moment().format('YYYY-MM'),
                    },
                    "user": {
                        "dialog": false,
                        "userSelected":[],
                        "panels": {
                            "search": [0],
                            "list": [0]
                        },
                        "dataTable": {
                            "headers": [
                                {"text": "이름", "value": "name"},
                                {"text": "전화번호", "value": "phoneNumber"},
                                {"text": "직종",  "value": "jobType"},
                                {"text": "기술",  "value": "skill"},
                                {"text": "학력", "value": "education"},
                                {"text": "경력",  "value": "career"},
                                {"text": "자격증 유무", "value": "certificateStatus"},
                                {"text": "생년월일(나이)",  "value": "birthDate"},
                                {"text": "희망 급여",  "value": "pay"},
                                {"text": "지역",  "value": "address"},
                                {"text": "투입여부",  "value": "inputStatus"},
                                {"text": "업무 가능일",  "value": "workableDay"}

                            ],
                            "totalRows":0,
                            "items": [],
                            "loading": false,
                            "serverItemsLength": 0,
                            "page": 1,
                            "itemsPerPage": 10,
                            "education": [
                                 {"text":"2년제", "value":"2년제"},
                                 {"text":"3년제", "value":"3년제"},
                                 {"text":"4년제", "value":"4년제"},
                            ],
                            "certificateStatus": [
                                 {"text":"보유", "value":"T"},
                                 {"text":"없음", "value":"F"},
                            ],
                            "jobTypeItems": [
                                 {"text":"개발자", "value":"개발자"},
                                 {"text":"기획자", "value":"기획자"},
                                 {"text":"퍼블리셔", "value":"퍼블리셔"},
                                 {"text":"디자이너", "value":"디자이너"}
                             ],
                            "address": [
                                {"text":"서울특별시", "value":0},
                                {"text":"경기도", "value":1},
                                {"text":"대전광역시", "value":2},
                            ],
                            "addressValue": "",
                            "addressIndex": [
                                ["강서구","은평구","광진구","서초구","구로구"],
                                ["김포시","부천","광명","시흥","안양","과천","성남","하남","수원","광주"],
                                ["중구","서구","대덕구","유성구","동구"],
                            ],
                            "addressSelect": [],
                            "career": [
                                {"text":"신입", "value": "0"},
                                {"text":"1년", "value": "1"},
                                {"text":"2년", "value": "2"},
                                {"text":"3년", "value": "3"},
                                {"text":"4년", "value": "4"},
                                {"text":"5년", "value": "5"},
                                {"text":"6년", "value": "6"},
                                {"text":"7년", "value": "7"},
                            ],
                            "checkbox:": [],
                        },
                        "pagination": {
                            "length": 10,
                            "totalVisible": 10
                        },
                        "query": {
                            "id":0,
                            "name": "",
                            "jobType": "",
                            "career": "",
                            "phoneNumber":"",
                            "education":"",
                            "certificateStatus": "",
                            "pay": "",
                            "address": "",
                            "inputStatus":"",
                            "birthDate":"",
                            "workableDay":"",
                        },
                        "itemsPerPageItems": [
                            {"text":"3개 보기", "value":3},
                            {"text":"5개 보기", "value": 5},
                            {"text": "10개 보기", "value": 10},
                            {"text": "20개 보기", "value": 20},
                            {"text": "30개 보기", "value": 30},
                            {"text": "40개 보기", "value": 40},
                            {"text": "50개 보기", "value": 50}
                        ]
                    }
                };
            },

            "watch": {
                "user.dataTable.addressValue":{
                    "handler": function(value){
                        this.user.dataTable.addressSelect=this.user.dataTable.addressIndex[value];
                    }
                },

                "user.dataTable.page": {
                    "handler": function (newValue, oldValue){
                        Promise.resolve()
                            .then(this.setUserInfoList)
                            .then(this.replaceQuery);
                    }
                },

                 "user.dataTable.itemsPerPage": {
                    "handler": function (newValue, oldValue) {
                        Promise.resolve()
                            .then(this.setUserInfoList)
                            .then(this.replaceQuery)
                    }
                }

            },
            "methods": {
    //-----------------파일 업로드 -------------------------
                //파일 찾기 버튼
                "onButtonClick" : function (){
                    console.log("----------------------onButtonClick------------------")
                    this.isSelecting = true
                    window.addEventListener('focus', () => {
                        this.isSelecting=false
                    }, {once: true})
                    this.$refs.uploader.click()
                    console.log("----------------------uploader click ------------------")
                },
                "onFileChanged": function(e) {
                    this.dataUpload.selectedFile = e.target.files[0];
                    if(this.dataUpload.selectedFile != undefined && this.dataUpload.selectedFile != null){
                        this.dataUpload.selectedFileName = this.dataUpload.selectedFile.name;
                    }else{
                        this.dataUpload.selectedFileName=null;
                    }
                },


                "onFileUpload": async function() {
                    var self = this, form = new FormData();

                    //선택한 파일이 있을때
                    if(self.dataUpload.selectedFile != null && self.dataUpload.selectedFile.size > 0){
                        store.commit("app/SET_LOADING", true);
                        form.append("file" , self.dataUpload.selectedFile);
                        form.append("fileType" , self.dataUpload.seletedType);

                        if(self.dataUpload.seletedType == 1 || self.dataUpload.selectedType == 2)
                            form.append("uploadReferenceMonth" , self.dataUpload.uploadReferenceMonth);

                        var returnType = await ito.api.app.dataUpload.uploadDataFile(form);
                        console.log("---------uploadDatafile(form) 성공 -----------")
                        store.commit("app/SET_LOADING", false);

                        if(returnType.data.returnVal != 'success'){
                            ito.alert(returnType.data.returnMsg);
                        }else{
                            ito.alert("업로드에 성공했습니다!!");
                            self.fileClear();
                            self.setUserInfoList();
                        }
                    }else{
                        ito.alert("파일을 선택해주세요.");
                    }
                },
                "fileClear": function() {
                    document.getElementById("hiddenFile").value = "";
                    this.dataUpload.selectedFileName = null;
                    this.dataUpload.selectedFile = null;
                },


    //------------------------------------------------------
                "editUserInfo": function(value){
                    this.$router.push({
                        "path": "/main/admin/user-info-form",
                        "query": {
                            "id": value.id
                        }
                       });
                },
                "handlePageChange": function (value){
                    return this.currentPage=value;
                },
                "deleteUserInfoList": async function(){
                    var self = this;
                    deleteList = [];

                    self.user.userSelected.forEach(e => {
                        deleteList.push(e.id);
                    });
                    if(self.user.userSelected.length == 0){
                        await ito.alert("삭제할 항목이 없습니다.")
                    }else{
                        if(await ito.confirm("삭제하시겠습니까?")){
//                           await self.removeUserInfoList(deleteList);
                           await ito.api.common.person.removePersonList(deleteList);
                           await ito.alert("삭제되었습니다.")
                        }
                        self.setUserInfoList();
                    }

                },
                "setUserInfoList": function () {
                    var self = this;
                    return new Promise(function (resolve, reject) {
                        Promise.resolve()
                            .then(function () {
                                var params = {};
                                params.page = self.user.dataTable.page;
                                params.size = self.user.dataTable.itemsPerPage;
                                params.name = !_.isEmpty(self.user.query.name) ? self.user.query.name : null;
                                params.jobType = !_.isEmpty(self.user.query.jobType) ? self.user.query.jobType : null;
                                params.career = !_.isEmpty(self.user.query.career) ? self.user.query.career : null;
                                params.pay = !_.isEmpty(self.user.query.pay) ? self.user.query.pay : null;
                                params.address = !_.isEmpty(self.user.query.address) ? self.user.query.address : null;
                                params.inputStatus = !_.isEmpty(self.user.query.inputStatus) ? self.user.query.inputStatus : null;
                                params.education = !_.isEmpty(self.user.query.education) ? self.user.query.education : null;
                                params.certificateStatus = !_.isEmpty(self.user.query.certificateStatus) ? self.user.query.certificateStatus : null;
                                self.user.dataTable.loading = true;
                                return ito.api.common.person.getPersonList(params);
                            })
                            .then(function (response) {
                                var data = response.data;
                                self.user.dataTable.items = data.items;
                                self.user.dataTable.items.forEach(e => {
                                    e.inputStatus = (e.inputStatus == "T") ? "투입중" : "섭외중"
                                    e.certificateStatus = (e.certificateStatus == "T") ? "있음" : "없음"
                                    e.career = e.career+"년"
                                });
                                self.user.dataTable.totalRows = data.items.length;
                                self.user.dataTable.serverItemsLength = data.totalElements;
                                self.user.dataTable.loading = false;
                            })
                            .then(function () { resolve(); });
                    });
                },
                "getQuery": function () {
                    var query = {},
                    routeQuery = this.$route.query;
                    query.id = routeQuery.id ? routeQuery.id : this.user.query.id;
                    query.page = routeQuery.page ? routeQuery.page : String(this.user.dataTable.page);
                    query.size = routeQuery.size ? routeQuery.size : String(this.user.dataTable.itemsPerPage);
                    query.name = routeQuery.name ? routeQuery.name : this.user.query.name;
                    query.jobType = routeQuery.jobType ? routeQuery.jobType : this.user.query.jobType;
                    query.career = routeQuery.career ? routeQuery.career : this.user.query.career;
                    query.pay = routeQuery.pay ? routeQuery.pay : this.user.query.pay;
                    query.address = routeQuery.address ? routeQuery.address : this.user.query.address;
                    query.inputStatus=routeQuery.inputStatus ? routeQuery.inputStatus : this.user.query.inputStatus;
                    query.education = routeQuery.education ? routeQuery.education : this.user.query.education;
                    query.certificateStatus=routeQuery.certificateStatus ? routeQuery.certificateStatus : this.user.query.certificateStatus;
                    return query;
                },
                "setQuery": function () {
                    var query = this.getQuery();
                    this.user.query.id = query.id;
                    this.user.dataTable.page = Number(query.page);
                    this.user.dataTable.itemsPerPage = Number(query.size);
                    this.user.query.name = query.name;
                    this.user.query.jobType = query.jobType;
                    this.user.query.career = query.career;
                    this.user.query.pay = query.pay;
                    this.user.query.address = query.address;
                    this.user.query.inputStatus = query.inputStatus;
                    this.user.query.birthDate=query.birthDate;
                    this.user.query.education = query.education;
                    this.user.query.certificateStatus = query.certificateStatus;
                },
                "replaceQuery": function () {
                    var query = {},
                    routeQuery = this.$route.query;
                    query.page = String(this.user.dataTable.page);
                    query.size = String(this.user.dataTable.itemsPerPage);
                    query.name = String(this.user.query.name);
                    query.jobType = String(this.user.query.jobType);
                    query.career = String(this.user.query.career);
                    query.pay = String(this.user.query.pay);
                    query.address = String(this.user.query.address);
                    query.inputStatus = String(this.user.query.inputStatus);
                    query.workableDay = String(this.user.query.workableDay);
                    query.birthDate = String(this.user.query.birthDate);
                    query.education = String(this.user.query.education);
                    query.certificateStatus = String(this.user.query.certificateStatus);
                    if (!_.isEqual(query, routeQuery)) {
                        this.$router.replace({"query": query});
                    }
                },
                "search": function () {
                    var self = this;
                    return new Promise(function (resolve, reject) {
                        Promise.resolve()
                            .then(function () {
                                return self.setUserInfoList();
                            })
                            .then(function () {
                                self.replaceQuery();
                            })
                            .then(function () {
                             resolve();
                         });
                    });
                },
                "reset": function () {
                    var self = this;
                    return new Promise(function (resolve, reject) {
                        Promise.resolve()
                            .then(function () {
                            })
                            .then(function () {
                                self.user.query.name = "";
                                self.user.query.jobType = "";
                                self.user.query.career = "";
                                self.user.query.pay = "";
                                self.user.query.address = "";
                                self.user.query.inputStatus = "";
                                self.user.query.education = "";
                                self.user.query.certificateStatus = "";
                            })
                            .then(function () {
                                return self.search();
                            })
                            .then(function () { resolve(); });
                    });
                }
            },
            "mounted": function () {
                Promise.resolve()
                    .then(this.setQuery)
                    .then(this.replaceQuery)
                    .then(this.setUserInfoList);
            }
        });
    });
});