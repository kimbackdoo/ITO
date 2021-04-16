var MainAdminProjectDetailPage = Vue.component('main-admin-project-detail-page', function (resolve, reject) {
    axios.get("/vue/page/main/admin/project-list/main-detail.html").then(function (response) {
         resolve({
            "template": response.data,
            "data": function() {
                return {
                    "project": {
                        "panels": {
                            "search": [0],
                            "list": [0]
                        },
                        "items": {
                            "id":null,
                            "name": "",
                            "skill": "",
                            "degree":"",
                            "career":"",
                            "sterm": "",
                            "eterm": "",
                            "local": "",
                            "detailLocal": "",
                            "postcode": "",
                            "address":"",
                            "detailAddress": "",
                            "prsnl":"",
                            "status":"",
                            "slary":"",
                            "limitDate":"",
                        }
                    },
                    "person":{

                        "dataTable": {
                            "headers": [
                                {"text": "이름","width": 120, "value": "name","align": "center", cellClass:"text-truncate"},
                                {"text": "전화번호","width": 120, "value": "phoneNumber","align": "center", cellClass:"text-truncate"},
                                {"text": "성별","width": 120,"align": "center", "value": "gender",cellClass:"text-truncate"},
                                {"text": "생년월일(나이)","width": 120, "align": "center", "value": "birthDate", cellClass:"text-truncate"},
                                {"text": "직종","width": 120,"align": "center",  "value": "jobType",cellClass:"text-truncate"},
                                {"text": "기술","width": 120, "align": "center", "value": "skill",cellClass:"text-truncate"},
                                {"text": "학력","width": 120, "align": "center","value": "education",cellClass:"text-truncate"},
                                {"text": "경력","width": 120,  "align": "center","value": "career",cellClass:"text-truncate"},
                                {"text": "자격증 유무","width": 120,"align": "center", "value": "certificateStatus",cellClass:"text-truncate"},
                                {"text": "희망 급여(최저)","width": 120,"align": "center",  "value": "minPay",cellClass:"text-truncate"},
                                {"text": "희망 급여(최고)","width": 120,"align": "center",  "value": "maxPay",cellClass:"text-truncate"},
                                {"text": "실제 급여" ,"width": 120,"align": "center","width": 170, "value": "actualPay","type": "textField",cellClass:"text-truncate"},
                                {"text": "업무 시작 가능일", "width": 120, "value": "workableDay", cellClass:"text-truncate"},
                                {"text": "투입 여부" ,"width": 120,"align": "center", "value": "edit","type": "button",cellClass:"text-truncate"},
                            ],
                            "cell": {
                                "button": {
                                    "edit": {
                                        "title": "투입하기"
                                    }
                                },
                                "textField": {
                                    "actualPay": {
                                        "suffix": "만원"
                                    }
                                }
                            },
                            "actualPay": "",
                            "items":[],
                            "textField": null,
                            "totalRows": 0,
                            "loading":false,
                        },
                    },
                    "confirmPerson":{
                        "dataTable": {
                            "headers": [
                                {"text": "이름","width": 120, "value": "name","align": "center", cellClass:"text-truncate"},
                                {"text": "전화번호","width": 120, "value": "phoneNumber","align": "center", cellClass:"text-truncate"},
                                {"text": "성별","width": 120, "value": "gender","align": "center", cellClass:"text-truncate"},
                                {"text": "생년월일(나이)","width": 120,  "value": "birthDate", cellClass:"text-truncate",},
                                {"text": "직종","width": 120,  "value": "jobType","align": "center", cellClass:"text-truncate"},
                                {"text": "기술","width": 120,  "value": "skill","align": "center", cellClass:"text-truncate"},
                                {"text": "학력","width": 120, "value": "education","align": "center", cellClass:"text-truncate"},
                                {"text": "경력","width": 120,  "value": "career","align": "center", cellClass:"text-truncate"},
                                {"text": "자격증 유무","width": 120, "value": "certificateStatus","align": "center", cellClass:"text-truncate"},
                                {"text": "실제 급여","width": 120,  "value": "actualPay","align": "center", cellClass:"text-truncate"},
                                {"text": "업무 시작 가능일","width": 120,  "value": "workableDay","align": "center", cellClass:"text-truncate"},
                            ],
                            "items":[],
                            "totalRows": 0,
                            "loading":false,

                        }
                    },
                    "availablePerson":{
                        "dataTable": {
                            "headers": [
                                {"text": "이름", "value": "name","width": 120,"align": "center", cellClass:"text-truncate"},
                                {"text": "평가점수", "width": 120,"value": "ratingScore","align": "center", cellClass:"text-truncate"},
                                {"text": "메모", "width": 120,"value": "memo","align": "center", cellClass:"text-truncate"},
                                {"text": "성별","width": 120,"align": "center", "value": "gender",cellClass:"text-truncate"},
                                {"text": "생년월일(나이)","width": 120, "align": "center", "value": "birthDate", cellClass:"text-truncate"},
                                {"text": "전화번호", "width": 120,"value": "phoneNumber","align": "center", cellClass:"text-truncate"},
                                {"text": "직종","width": 120,"align": "center",  "value": "jobType",cellClass:"text-truncate"},
                                {"text": "기술","width": 120, "align": "center", "value": "skill",cellClass:"text-truncate"},
                                {"text": "학력","width": 120, "align": "center","value": "education",cellClass:"text-truncate"},
                                {"text": "경력","width": 120,  "align": "center","value": "career",cellClass:"text-truncate"},
                                {"text": "자격증 유무","width": 120,"align": "center", "value": "certificateStatus",cellClass:"text-truncate"},
                                {"text": "희망 급여(최저)","width": 120,"align": "center",  "value": "minPay",cellClass:"text-truncate"},
                                {"text": "희망 급여(최고)","width": 120,"align": "center",  "value": "maxPay",cellClass:"text-truncate"},
                                {"text": "실제 급여" ,"align": "center","width": 170, "value": "actualPay","type": "textField",cellClass:"text-truncate"},
                                {"text": "업무 시작 가능일", "width": 120, "value": "workableDay", cellClass:"text-truncate"},
                                {"text": "투입 여부", "width": 120,"align": "center", "value": "edit","type": "button",cellClass:"text-truncate"},
                            ],
                            "cell": {
                                "button": {
                                    "edit": {
                                        "title": "투입하기"
                                    }
                                },
                                "textField": {
                                    "actualPay": {
                                        "suffix": "만원"
                                    }
                                }
                            },
                            "actualPay": "",
                            "items":[],
                            "textField": null,
                            "totalRows": 0,
                            "loading":false,
                        }
                    }
                }
            },
            "watch": {

            },
            "methods":{
                "editUserInfo": function(value){
                    this.$router.push({
                        "path": "/main/admin/user-info-form",
                        "query": {
                            "id": value.id
                        }
                       });
                },
                "setActualPay": async function(item){
                    var self = this;
                    self.person.dataTable.actualPay = item.value;

                },
                "editProjectInfo": function(value){
                    this.$router.push({
                        "path": "/main/admin/project-info-form",
                        "query": {
                            "id": value.item.id
                        }
                    });
                },
                //프로젝트- 가용 인력 엑셀 다운로드
                "availableDownloadTable": async function() {
                    await ito.api.app.personDownload.downloadPersonListXlsx({
                        "rowSize": 100000000,
                        "workableDay": moment().add("1", "M").format("YYYY-MM-DD"),
                    }, "available");
                },
                "confirmDownloadTable": async function(){
                    let self = this;
                    var projectId = await self.$route.query.id;
                    await ito.api.app.personDownload.downloadPersonListXlsx({
                        "rowSize": 100000000,
                        "projectId": projectId,
                        "status": "T"
                    }, "confirm");
                },
                "applyDownloadTable": async function(){
                    let self = this;
                    var projectId = await self.$route.query.id;
                    await ito.api.app.personDownload.downloadPersonListXlsx({
                        "rowSize": 100000000,
                        "projectId": projectId,
                        "status": "F"
                    }, "apply");
                },

                "clickInputStatus": async function(value){
                    let personId = value.item.id;
                    var projectId = this.project.items.id;
                    var param = {
                            "personId": personId,
                            "projectId": projectId,
                            "status": "T",
                    };
                    var pay = this.person.dataTable.actualPay;

                    var involveParam = {
                        "projectPersonDto":param,
                        "careerDto": {
                            "personId": personId,
                            "name": this.project.items.name,
                            "startPeriod": this.project.items.sterm,
                            "endPeriod": this.project.items.eterm,
                            "position": "테스트_대리",
                            "task": value.item.jobType,
                            "pay": pay,
                        }
                    };
                    await ito.alert("투입 시키겠습니까?");
                   var valueProjectPerson = (await ito.api.common.projectPerson.getProjectPersonList({
                        "personId": personId,
                        "status": "T"
                    })).data;
                    var projectNameList = "";
                    if(valueProjectPerson.items.length != 0){
                        valueProjectPerson.items.forEach( e=> {
                            projectNameList += e.project.name + " ";
                        })
                    }
                   var a =valueProjectPerson ? true : false;
                    if(projectNameList !== "" ){
                        await ito.alert("이미 [ " + projectNameList +" ] 프로젝트에 확정 되었습니다.");
                    }
                    else{
                        await ito.api.app.involvement.createInvolvement(involveParam);
                        var personData = (await ito.api.common.person.getPerson(personId)).data
                        var new_date = moment(this.project.items.eterm).add(1,"days").format("YYYY-MM-DD");
                        personData.workableDay = new_date;
                        personData.inputStatus="P"
                        await ito.api.common.person.modifyPerson(personId, personData);
                        await ito.alert("완료 되었습니다.");
                        await this.setConfirmPersonInfo();
                        await this.setPersonInfo();
                        await this.setAvailablePersonInfo();
                    }
                },
                "deleteConfirmPersonInfoList": async function(items){
                    var projectId = this.project.items.id;
                    var self = this;
                    var param = {};
                    let deleteList = items.map(e => e.id);

                    if(items.length == 0){
                        await ito.alert("삭제할 항목이 없습니다.")
                    }else {
                        if(await ito.confirm("삭제하시겠습니까?")){
                             for(var i in deleteList){
                                    let careerId = (await ito.api.app.involvement.getCareerIdList(projectId, deleteList[i])).data;
                                    var involveParam = {
                                        "projectPersonDto":{
                                            "personId": deleteList[i],
                                            "projectId": projectId,
                                            "status": "F",
                                        },
                                        "careerDto": {
                                            "personCareerId": Number(careerId),
                                            "personId": deleteList[i],
                                            "name": this.project.items.name,
                                            "startPeriod": this.project.items.sterm,
                                            "endPeriod": this.project.items.eterm,
                                        }
                                    }
                                    await ito.api.app.involvement.removeInvolvement(involveParam);
                                    var personData = (await ito.api.common.person.getPerson(deleteList[i])).data
                                    personData.workableDay = (moment().format("YYYY-MM-DD"));
                                    personData.inputStatus = "N";
                                    await ito.api.common.person.modifyPerson(deleteList[i], personData);
                             }
                            await ito.alert("삭제되었습니다.")
                        }
                        await this.setConfirmPersonInfo();
                        await this.setPersonInfo();
                        await this.setAvailablePersonInfo();
                    }
                },

                "setProjectInfo": async function(){
                    var self =this;
                    var id = await self.$route.query.id;
                        console.log(id);
                        return new Promise(function (resolve, reject) {
                            Promise.resolve()
                                .then(function () {
                                    return ito.api.common.project.getProject(id);
                                })
                                .then(function (response) {
                                    self.project.items = response.data;

                                    self.project.items.career += "년 이상"
                                    self.project.items.prsnl += "명"
                                    if(self.project.items.detailAddress != null) self.project.items.address += " "+self.project.items.detailAddress;
                                    switch(self.project.items.status){
                                        case 'A':
                                             self.project.items.status = "섭외중"; break;
                                        case 'C':
                                             self.project.items.status = "섭외완료"; break;
                                        case 'I':
                                             self.project.items.status = "인터뷰"; break;
                                        case 'P':
                                             self.project.items.status = "투입중"; break;
                                        case 'N':
                                             self.project.items.status = "미정"; break;
                                    }
                                    switch(self.project.items.degree){
                                        case "00701":
                                             self.project.items.degree = "학력 무관"; break;
                                        case "00702":
                                             self.project.items.degree = "고등학교 졸업"; break;
                                        case "00703":
                                             self.project.items.degree = "(2~3년제)전문대 졸업"; break;
                                        case "00704":
                                             self.project.items.degree = "(4년제)대학교 졸업"; break;
                                        case "00705":
                                             self.project.items.degree = "석사"; break;
                                        case "00706":
                                             self.project.items.degree = "박사"; break;
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
                                })
                                .then(function () { resolve(); });
                        });
                },
                "setConfirmPersonInfo": async function(options){
                        var self = this;
                        var projectId = await self.$route.query.id;
                        var params = {};
                        params.projectId = !_.isEmpty(projectId) ? projectId : null;
                        params.status="T"
                        if(options !== undefined) {
                            params.page = options.page;
                            params.rowSize = options.itemsPerPage;
                            params.sort=ito.util.sort(options.sortBy, options.sortDesc);
                        }
                        self.confirmPerson.dataTable.loading = true;
                        var data = (await ito.api.common.projectPerson.getProjectPersonList(params)).data;
                        self.confirmPerson.dataTable.items = [];

                        var param=[];
                        for(var i =0; i < data.items.length ; i++ ){
                                 let personId = data.items[i].person.id;
                                 let careerId = (await ito.api.app.involvement.getCareerIdList(projectId, personId)).data;
                                 var careerData = (await ito.api.common.career.getCareer(careerId)).data;
                                 data.items[i].person.actualPay = careerData.pay;
                                 param.push(data.items[i].person);
                        }
                        self.confirmPerson.dataTable.items=param;

                        var codeBigData = (await ito.api.common.code.getCodeList()).data.items;

                        self.confirmPerson.dataTable.items.forEach(e => {

                            if(e.gender == "M") e.gender = "남자";
                            else e.gender = "여자";

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
                                     e.inputStatus = "미정"; break;
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
                            e.career = e.career+"년"
                            e.pay = String(e.minPay) +" ~ " +String(e.maxPay)

                        })
                        self.confirmPerson.dataTable.totalRows = data.totalRows;
                        self.confirmPerson.dataTable.loading = false;
                  },
                "setAvailablePersonInfo": async function(options){
                    var self = this;
                    var projectId = await self.$route.query.id;

                    var params = {}, count=0, availablePersonList;
                    if(options !== undefined) {
                        params.page = options.page;
                        params.rowSize = options.itemsPerPage;
                        params.sort=ito.util.sort(options.sortBy, options.sortDesc);
                    }
                    params.projectId = !_.isEmpty(projectId) ? projectId : null;
                    params.workableDay= moment().add("1", "M").format("YYYY-MM-DD")
                    self.availablePerson.dataTable.loading = true;
                    self.availablePerson.dataTable.items=[];

                    var data = (await ito.api.common.person.getPersonList(params)).data;

                    self.availablePerson.dataTable.items = data.items;
                    self.availablePerson.dataTable.totalRows = data.totalRows;

                    var codeBigData = (await ito.api.common.code.getCodeList()).data.items;

                    self.availablePerson.dataTable.items.forEach(e => {

                        if(e.gender == "M") e.gender = "남자";
                        else e.gender = "여자";

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
                                 e.inputStatus = "미정"; break;
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
                        e.career = e.career+"년"
                        e.pay = String(e.minPay) +" ~ " +String(e.maxPay)

                    })
                    self.availablePerson.dataTable.loading = false;
                },

                "setPersonInfo": async function(options){
                    var self = this;
                    var projectId = await self.$route.query.id;
                    var params = {};
                    params.projectId = !_.isEmpty(projectId) ? projectId : null;
                    params.status="F";
                    self.person.dataTable.loading = true;
                    if(options !== undefined) {
                        params.page = options.page;
                        params.rowSize = options.itemsPerPage;
                        params.sort=ito.util.sort(options.sortBy, options.sortDesc);
                    }
                    var data = (await ito.api.common.projectPerson.getProjectPersonList(params)).data
                    self.person.dataTable.items = [];
                    data.items.forEach(e=> {
                            self.person.dataTable.items.push(e.person);
                    });

                    var codeBigData = (await ito.api.common.code.getCodeList()).data.items;

                    self.person.dataTable.items.forEach(e => {

                        if(e.gender == "M") e.gender = "남자";
                        else e.gender = "여자";

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
                                 e.inputStatus = "미정"; break;
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
                        e.career = e.career+"년"
                        e.pay = String(e.minPay) +" ~ " +String(e.maxPay)

                    })
                    self.person.dataTable.totalRows = data.totalRows;
                    self.person.dataTable.loading = false;
                },
            },
            "mounted": async function () {
                var self = this;
                await self.setProjectInfo();
                await self.setPersonInfo();
                await self.setConfirmPersonInfo();
                await self.setAvailablePersonInfo();
            }
        });
    });

});
