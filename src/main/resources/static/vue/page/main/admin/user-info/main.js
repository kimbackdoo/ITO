var MainAdminPage = Vue.component('main-admin-userInfo-page', function (resolve, reject) {
    axios.get("/vue/page/main/admin/user-info/main.html").then(function (response) {
        resolve({
            "template": response.data,
            "data": function () {
                return {
                    "user": {
                        "dialog": false,
                        "panels": {
                            "search": [0],
                            "list": [0]
                        },
                        "dataTable": {
                            "headers": [
                                {"text": "이름", "value": "name",width:120,cellClass:"text-truncate"},
                                {"text": "성별", "value": "gender",width:120,cellClass:"text-truncate"},
                                {"text": "평가점수", "value": "ratingScore",width:120,cellClass:"text-truncate"},
                                {"text": "전화번호", "value": "phoneNumber", width:120,cellClass:"text-truncate"},
                                {"text": "생년월일(나이)",  "value": "birthDate",width:120, cellClass:"text-truncate"},
                                {"text": "직종",  "value": "jobType",width:120, cellClass:"text-truncate"},
                                {"text": "기술",  "value": "skill",width:120,cellClass:"text-truncate"},
                                {"text": "학력", "value": "education",width:120, cellClass:"text-truncate"},
                                {"text": "경력",  "value": "career",width:120, cellClass:"text-truncate"},
                                {"text": "자격증 유무", "value": "certificateStatus",width:120,cellClass:"text-truncate"},
                                {"text": "희망 급여",  "value": "pay",width:120, cellClass:"text-truncate"},
                                {"text": "지역",  "value": "address",width:120, cellClass:"text-truncate"},
                                {"text": "현재 지원한 프로젝트", "value": "applyProject", "align": "center", "width": "120", cellClass:"text-truncate"},
                                {"text": "프로젝트명", "value": "projectName", "align": "center", "width": "120", cellClass:"text-truncate", "type": "autocomplete"},
                                {"text": "투입여부",  "value": "inputStatus",width:120, cellClass:"text-truncate"},
                                {"text": "업무 가능일",  "value": "workableDay",width:120, cellClass:"text-truncate"}
                            ],
                            "search": false,
                            "totalRows":0,
                            "items": [],
                            "loading": false,
                            "certificateStatus": [
                                 {"text":"보유", "value":"T"},
                                 {"text":"없음", "value":"F"},
                            ],
                            "career1": [
                                {"text":"1년미만", "value": 0},
                                {"text":"1", "value": 1},
                                {"text":"2", "value": 2},
                                {"text":"3", "value": 3},
                                {"text":"4", "value": 4},
                                {"text":"5", "value": 5},
                                {"text":"6", "value": 6},
                                {"text":"7", "value": 8},
                                {"text":"8", "value": 9},
                                {"text":"9", "value": 10},
                                {"text":"10", "value": 11},
                                {"text":"12", "value": 12},
                                {"text":"13", "value": 13},
                                {"text":"14", "value": 14},
                                {"text":"15", "value": 15},
                                {"text":"16", "value": 16},
                                {"text":"17", "value": 17},
                                {"text":"18", "value": 18},
                                {"text":"19", "value": 19},
                                {"text":"20", "value": 20}
                            ],
                            "career2": [
                                {"text":"1", "value": 0.01},
                                {"text":"2", "value": 0.02},
                                {"text":"3", "value": 0.03},
                                {"text":"4", "value": 0.04},
                                {"text":"5", "value": 0.05},
                                {"text":"6", "value": 0.06},
                                {"text":"7", "value": 0.07},
                                {"text":"8", "value": 0.08},
                                {"text":"9", "value": 0.09},
                                {"text":"10", "value": 0.10},
                                {"text":"11", "value": 0.11}
                            ],
                            "checkbox:": [],
                            "cell": {
                                "autocomplete": {
                                    "projectName": {
                                        "items": []
                                    }
                                }
                            }
                        },
                        "query": {
                            "id":0,
                            "name": null,
                            "jobType": null,
                            "skill":null,
                            "skillList":[],
                            "career1": null,
                            "career2": null,
                            "career": null,
                            "local": null,
                            "detailLocal": null,
                            "phoneNumber":null,
                            "education":null,
                            "certificateStatus": null,
                            "minPay": null,
                            "maxPay": null,
                            "address": null,
                            "inputStatus":null,
                            "birthDate":null,
                            "workableDay":null,
                            "status":null,
                            "age":null,
                            "gender":null,
                            "ratingScore":null,
                            "sterm":null,
                            "eterm":null
                        },
                    },
                    "select": {
                        "job": {
                            "items": [
                                {"text": "전체", "value": null}
                            ]
                        },
                        "skill": {
                            "items": [
                                {"text": "전체", "value": null}
                            ]
                        },
                        "localPlace":{
                            "items":[
                                {"text": "전체", "value": null}
                            ]
                        },
                        "detailLocalPlace":{
                            "items":[
                                {"text": "전체", "value": null}
                            ]
                        },
                        "education":{
                            "items":[
                                {"text": "전체", "value": null}
                            ]
                        },
                        "status":{
                            "items":[
                                {"text": "섭외중", "value": "A"},
                                {"text": "섭외완료", "value": "C"},
                                {"text": "인터뷰", "value": "I"},
                                {"text": "투입중", "value": "P"},
                                {"text": "이외", "value": "N"}
                            ]
                        },
                        "gender":{
                            "items":[
                                {"text": "무관", "value": null},
                                {"text": "남자", "value": "M"},
                                {"text": "여자", "value": "F"}
                            ]
                        },
                        "ratingScore":{
                            "items":[
                                {"text":"무관", "value": null},
                                {"text":"1점", "value": 1},
                                {"text":"2점", "value": 2},
                                {"text":"3점", "value": 3},
                                {"text":"4점", "value": 4},
                                {"text":"5점", "value": 5},
                            ]
                        }

                    },
                    "dialog": {
                        "visible": false,
                    }
                };
            },
            "watch": {
                "user.query.jobType": {
                    "handler": async function () {
                        var self = this;
                        let items,
                            skill = self.select.job.items.find(e=>e.value == self.user.query.job);

                        self.select.skill.items = [];
                        items = (await ito.api.common.code.getCodeList({
                            "idStartLike": "004",
                            "status": "T",
                            "skill": skill !== undefined ? skill.text : null,
                            "rowSize": 1000000
                        })).data.items.map(e=>({"text": e.name, "value": e.id}));
                        items = items.filter(e=> {
                            if(e.value.startsWith("00401")) return e.value.length > 7;
                            else return e.value.length > 5;
                        });
                        self.select.skill.items.push(
                            {"text": "전체", "value": null},
                            ...items
                        );
                        self.user.query.skill = [];
                    }
                },
                "user.query.local": {
                    "handler": async function(n, o){
                        let detailLocal = this.select.localPlace.items.find(e=>e.value == this.user.query.localPlace);

                        if(o != null){
                            this.user.query.detailLocal= [];
                        }
                        this.select.detailLocalPlace.items=[];
                        let items = (await ito.api.common.code.getCodeList({
                            "idStartLike":"006",
                            "status": "T",
                            "detailLocal": detailLocal !== undefined ? detailLocal.text : null,
                            "rowSize": 1000000
                        })).data.items.map(e => ({"text": e.name, "value": e.id}))
                        items = items.filter(e => e.value.length > 5)
                        this.select.detailLocalPlace.items.push( {"text": "전체", "value": null}, ...items)
                        console.log(this.select.detailLocalPlace.items)
                    }
                }
            },
            "methods": {
                "inputProject": async function(value) {
                    let check = 1; // 지원한 프로젝트인지 체크하는 변수
                    let projectIdList = (await ito.api.common.projectPerson.getProjectPersonList({"personId": value.item.id})).data.items.map(e=> e.projectId);
                    for(let i=0; i<projectIdList.length; i++) { // 지원한 적인 있는 프로젝트인지 체크
                        if(projectIdList[i] === value.id) {
                            check = 0;
                            break;
                        }
                    }

                    if(check) { // 지원한 적인 없는 프로젝트면 지원
                        if(await ito.confirm("지원하시겠습니까?")) {
                            await ito.api.common.projectPerson.createProjectPerson({
                                "personId": value.item.id,
                                "projectId": value.id,
                                "status": "F",
                            });
                            await ito.alert("지원되었습니다.");
                        }
                    } else { // 지원한적이 있는 프로젝트면 지원 안됨
                        await ito.alert("이미 지원한 프로젝트입니다.");
                    }
                    this.user.dataTable.search = true;
                },
                "delimit": function(v) {
                    let reducer = (a, e) => [...a, ...e.split(/[, ]+/)]
                    this.user.query.skillList = [...new Set(v.reduce(reducer, []))]
                },
                "loadEducation": async function() {
                    var self = this;
                    let items;
                    items = (await ito.api.common.code.getCodeList({
                        "parentId": "007",
                        "sort": ["ranking, asc"],
                        "rowSize": 1000000
                    })).data.items.map(e => ({"text": e.name, "value": e.id}));
                    self.select.education.items.push(...items);
                },
               "loadLocalPlace": async function(){
                    var self = this;
                    let items;
                    items = (await ito.api.common.code.getCodeList({
                        "parentId": "006",
                        "sort": ["ranking, asc"],
                        "rowSize": 1000000
                    })).data.items.map(e => ({"text": e.name, "value": e.id}));
                    self.select.localPlace.items.push(...items);
                },
                "editUserInfo": function(value){
                    this.$router.push({
                        "path": "/main/admin/user-info-form",
                        "query": {
                            "id": value.id
                        }
                       });
                },
                "addUserInfo": function(value){
                    this.$router.push({
                        "path": "/main/admin/user-info-form",
                       });
                },
                "handlePageChange": function (value){
                    return this.currentPage=value;
                },
                "deleteUserInfoList": async function(items){
                    var self = this;
                    let idList = [items.map(e=>e.id)];
                    var params=[];
                    var person;
                    var param={};
                    if(items.length == 0){
                        await ito.alert("삭제할 항목이 없습니다.")
                    }else{
                        if(await ito.confirm("삭제하시겠습니까?")){
                            console.log("idList 값 : "+idList);
                            for(var i in idList){

                                idList[i] = Number(idList[i]);
                                param.personId = idList[i];
                                var project = (await ito.api.common.projectPerson.getProjectPersonList(param)).data

                                var projectPersonParams=[];
                                project.items.forEach(e => {
                                    projectPersonParams.push({"personId": idList[i], "projectId": e.projectId});
                                })
                                //projectPerson 삭제
                                if(Array.isArray(projectPersonParams) && projectPersonParams.length !== 0){
                                    await ito.api.common.projectPerson.removeProjectPersonList(projectPersonParams)
                                }
                                var careerIdList=[],projectId;
                                var careerData = (await ito.api.common.career.getCareerList({
                                    "personId": idList[i]
                                    })).data.items.forEach(e => {
                                        careerIdList.push(e.personCareerId);
                                });
                                if(Array.isArray(careerIdList) && careerIdList.length !== 0) {
                                    //projectCareer 삭제
                                    for(let i =0;i<careerIdList.length;i++){
                                        let projectEntity = (await ito.api.common.projectCareer.getProjectCareerList({"careerId":careerIdList[i]})).data.items;
                                        projectEntity.forEach(e=> { projectId = e.projectId})
                                        await ito.api.common.projectCareer.removeProjectCareer(projectId, careerIdList[i]);
                                    }
                                    //career 삭제
                                    await ito.api.common.career.removeCareerList(careerIdList);
                                }
                                //userPerson 삭제
                                var userId = (await ito.api.common.userPerson.getUserId(idList[i])).data.userId;
                                if(userId != null) await ito.api.common.userPerson.removeUserPerson(userId);
                                person = (await ito.api.common.person.getPerson(idList[i])).data;
                                params.push({
                                    "personDto": person,
                                })
                            }
                           await ito.api.app.profile.removeProfileList(params);
                           //person 삭제
                           await ito.api.common.person.removePersonList(idList);
                           await ito.alert("삭제되었습니다.")
                        }
                        this.user.dataTable.search = true;

                    }
                },

                "loadJobItems": async function() {
                    var self = this;
                    let items;
                    items = (await ito.api.common.code.getCodeList({
                        "parentId": "001",
                        "sort": ["ranking, asc"],
                        "rowSize": 1000000
                    })).data.items.map(e=>({"text": e.name, "value": e.id}));
                    self.select.job.items.push(...items);
                },
                "setUserInfoList": async function (options) {
                    var self = this;
                    var careerValue = String(self.user.query.career1 + self.user.query.career2);
                    var params = {}, data;
                    if(self.user.query.birthDate != null) {
                        var startBirth = moment().subtract(Number(self.user.query.birthDate)-1, "y") .format("YYYY-01-01");
                        var endBirth = moment().subtract(Number(self.user.query.birthDate)-1, "y") .format("YYYY-12-31");
                       }
                    console.log(options);

                    if(options !== undefined) {
                        params.page = options.page;
                        params.rowSize = options.itemsPerPage;
                        params.sort=ito.util.sort(options.sortBy, options.sortDesc);
                    }
/*                    if(self.user.query.workableDay != null){
                        var wd = self.user.query.workableDay;
                        var year = wd.slice(0,4);
                        var month = wd.slice(5,7);
                        var day = wd.slice(8,10);
                        if(Number(month) == 12){
                            year = String(Number(year) +1);
                            month = "01";
                        }else{
                            month = (Number(month) + 1);
                            if(month < 10) month = "0"+String(month);
                            else month = String(month);
                        }
                        wd = year+"-"+month+"-"+day;
                    }
*/
                    params.name = !_.isEmpty(self.user.query.name) ? self.user.query.name : null;
                    params.jobType = !_.isEmpty(self.user.query.jobType) ? self.user.query.jobType : null;
                    params.career = !_.isEmpty(careerValue) && careerValue != 0 ? careerValue : null;
                    params.minPay = self.user.query.pay;
                    params.local = !_.isEmpty(self.user.query.local) ? self.user.query.local : null;
                    params.detailLocal = !_.isEmpty(self.user.query.detailLocal) ? self.user.query.detailLocal : null;
                    params.inputStatus = !_.isEmpty(self.user.query.status) ? self.user.query.status : null;
                    params.education = !_.isEmpty(self.user.query.education) ? self.user.query.education : null;
                    params.certificateStatus = !_.isEmpty(self.user.query.certificateStatus) ? self.user.query.certificateStatus : null;
                    params.skill= !_.isEmpty(self.user.query.skill) ? [self.user.query.skill] : null;
                    params.startBirthDate= !_.isEmpty(startBirth) ? startBirth : null;
                    params.endBirthDate= !_.isEmpty(endBirth) ? endBirth : null;
                    params.gender = !_.isEmpty(self.user.query.gender) ? self.user.query.gender : null;
                    params.ratingScore = self.user.query.ratingScore;
                    params.skillListLike = !_.isEmpty(self.user.query.skillList) ? self.user.query.skillList : [];
//                    params.workableDay = !_.isEmpty(self.user.query.workableDay) ? wd : null;
                    params.sterm = !_.isEmpty(self.user.query.sterm) ? self.user.query.sterm : null;
                    params.eterm = !_.isEmpty(self.user.query.eterm) ? self.user.query.eterm : null;
                    this.user.dataTable.search = false;
                    self.user.dataTable.loading = true;

                    data = (await ito.api.common.person.getPersonList(params)).data;
                    self.user.dataTable.items = data.items;
                    self.user.dataTable.totalRows = data.totalRows;
                    var codeBigData = (await ito.api.common.code.getCodeList()).data.items;

                    let projectList = (await ito.api.common.project.getProjectList({
                    })).data.items.map(e=>({"text": e.name, "value": e.id}));
                    this.user.dataTable.cell.autocomplete.projectName.items.push(...projectList);

                    let projectPersonPromiseList = [];
                    data.items.forEach(e=> {
                        projectPersonPromiseList.push(ito.api.common.projectPerson.getProjectPersonList({"personId": e.id}));
                    });
                    (await Promise.all(projectPersonPromiseList)).forEach(e => {
                        data.items.forEach(el=> {
                            if(e.data.items.length > 0 && el.id === e.data.items[0].personId) {
                                el.applyProject = e.data.items.map(project => project.project.name);
                            }
                        })
                    });


                    self.user.dataTable.items.forEach(e => {
                        switch(e.inputStatus){
                            case 'A':
                                 e.inputStatus = "섭외중"; break;
                            case 'C':
                                 e.inputStatus = "섭외완료"; break;
                            case 'I':
                                 e.inputStatus = "인터뷰"; break;
                            case 'P':
                                 e.inputStatus = "투입중"; break;
                            case 'N':
                                 e.inputStatus = "이외"; break;
                        }
                        switch(e.education){
                            case "00701":
                                 e.education = "학력 무관"; break;
                            case "00702":
                                 e.education = "고등학교 졸업"; break;
                            case "00703":
                                 e.education = "(2~3년제)전문대 졸업"; break;
                            case "00704":
                                 e.education = "(4년제)대학교 졸업"; break;
                            case "00705":
                                 e.education = "석사"; break;
                            case "00706":
                                 e.education = "박사"; break;
                        }
                        for(var i=0; i < codeBigData.length ; i++){
                            if(e.jobType == codeBigData[i].id)
                                 e.jobType = codeBigData[i].name;
                        }
                        if(e.birthDate != null){
                            var v = e.birthDate.slice(0,4);
                            var age = e.birthDate.slice(2,4);
                            if(Number(v) < 2000){
                                var a = 2000 - Number(v);
                                e.birthDate =  age+"년생 "+ "("+(Number(moment().format("YY")) + a + 1)+")";
                            }
                            else{
                                var a = Number(v) - 2000;
                                e.birthDate = age+"년생 " + "("+(a + 1 )+")";
                            }
                        }
                        if(e.detailAddress != null) e.address = e.address + e.detailAddress
                        e.certificateStatus =(e.certificateStatus == "T") ? "있음" : "없음"
                        e.gender = (e.gender === "M") ? "남자" : "여자"
                        e.career = e.career+"년"
                        e.pay = String(e.minPay) +" ~ " +String(e.maxPay)
                        if(e.applyProject !== undefined) { // 가져온 프로젝트 이름에 대한 배열이 undefined 인지 체크하고 아니면 String으로 변환
                            e.applyProject = e.applyProject.join(", ");
                        }

                    });
                    self.user.dataTable.loading = false;

                },
                "search": async function () {
                     this.user.dataTable.search = true;
                },
                "reset": function () {
                    var self = this;
                    return new Promise(function (resolve, reject) {
                        Promise.resolve()
                            .then(function () {
                            })
                            .then(function () {
                                self.user.query.name = null;
                                self.user.query.jobType = null;
                                self.user.query.career = null;
                                self.user.query.career1 = null;
                                self.user.query.career2 = null;
                                self.user.query.pay = null;
                                self.user.query.address = null;
                                self.user.query.inputStatus = null;
                                self.user.query.status = null;
                                self.user.query.education = null;
                                self.user.query.certificateStatus = null;
                                self.user.query.job = null;
                                self.user.query.skill = null;
                                self.user.query.local = null;
                                self.user.query.birthDate = null;
                                self.user.query.detailLocal = null;
                                self.user.query.gender = null;
                                self.user.query.ratingScore = null;
                                self.user.query.skillList = [];

                            })
                            .then(function () {
                                return self.search();
                            })
                            .then(function () { resolve(); });
                    });
                },
                "openDialog": function () {
                    this.dialog.visible = true;
                },
                "fileUpload": async function (dataUpload) {
                    var form = new FormData();
                    store.commit("app/SET_LOADING", true);
                    form.append("file" , dataUpload.selectedFile);

                    var returnType = await ito.api.app.upload.person(form);
                    store.commit("app/SET_LOADING", false);

                    if(returnType.data.returnVal != 'SUCCESS'){
                        await ito.alert(returnType.data.returnMsg);
                    }else{
                        await ito.alert(returnType.data.returnMsg);
                        this.dialog.visible = false;
                        this.user.dataTable.search = true;
                        this.loadJobItems();
                    }
                },
                "userDownloadTable": async function() {
                    let self = this, startBirth, endBirth,
                        careerValue = String(self.user.query.career1 + self.user.query.career2);
                    if(self.user.query.birthDate != null) {
                        startBirth = moment().subtract(Number(self.user.query.birthDate)-1, "y") .format("YYYY-01-01");
                        endBirth = moment().subtract(Number(self.user.query.birthDate)-1, "y") .format("YYYY-12-31");
                    }

                    await ito.api.app.personDownload.downloadPersonListXlsx({
                        "rowSize": 100000000,

                        "name": self.user.query.name,
                        "jobType": self.user.query.jobType,
                        "career": careerValue === "0" ? null : careerValue,
                        "minPay": self.user.query.pay,
                        "local": self.user.query.local,
                        "detailLocal": self.user.query.detailLocal,
                        "inputStatus": self.user.query.status,
                        "gender": self.user.query.gender,
                        "education": self.user.query.education,
                        "certificateStatus": self.user.query.certificateStatus,
                        "skillListLike": self.user.query.skillList,
                        "startBirthDate": startBirth,
                        "endBirthDate": endBirth,
                        "ratingScore": self.user.query.ratingScore,
                    });
                }
            },
            "mounted": async function () {
                Promise.all([
                    this.loadEducation(),
                    this.loadLocalPlace(),
                    this.loadJobItems()
                ]);
            }
        });
    });
});