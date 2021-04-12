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
                                {"text": "이름", "value": "name","align": "center", cellClass:"text-truncate"},
                                {"text": "전화번호", "value": "phoneNumber","align": "center", cellClass:"text-truncate"},
                                {"text": "성별","align": "center", "value": "gender"},
                                {"text": "생년월일(나이)", "align": "center", "value": "birthDate", cellClass:"text-truncate"},
                                {"text": "직종","align": "center",  "value": "jobType"},
                                {"text": "기술", "align": "center", "value": "skill"},
                                {"text": "학력", "align": "center","value": "education"},
                                {"text": "경력",  "align": "center","value": "career"},
                                {"text": "자격증 유무","align": "center", "value": "certificateStatus"},
                                {"text": "희망 급여(최저)","align": "center",  "value": "minPay"},
                                {"text": "희망 급여(최고)","align": "center",  "value": "maxPay"},
                                {"text": "실제 급여" ,"align": "center","width": 170, "value": "actualPay","type": "textField"},
                                {"text": "업무 시작 가능일",  "value": "workableDay", cellClass:"text-truncate"},
                                {"text": "투입 여부" ,"align": "center", "value": "edit","type": "button"},


//                                {"text": "현황",  "value": "inputStatus"},
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
                                {"text": "이름", "value": "name"},
                                {"text": "전화번호", "value": "phoneNumber"},
                                {"text": "성별", "value": "gender"},
                                {"text": "생년월일(나이)",  "value": "birthDate", cellClass:"text-truncate"},
                                {"text": "직종",  "value": "jobType"},
                                {"text": "기술",  "value": "skill"},
                                {"text": "학력", "value": "education"},
                                {"text": "경력",  "value": "career"},
                                {"text": "자격증 유무", "value": "certificateStatus"},
                                {"text": "실제 급여",  "value": "actualPay"},
                                {"text": "업무 시작 가능일",  "value": "workableDay"},
//                                {"text": "현황",  "value": "inputStatus"},
                            ],
                            "items":[],
                            "totalRows": 0,
                            "loading":false,

                        }
                    }
                }
            },
            "watch": {

            },
            "methods":{
                "setActualPay": async function(item){
                    var self = this;
                    console.log(item.value);
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
                    await ito.api.app.involvement.createInvolvement(involveParam);
//                    await ito.api.common.projectPerson.modifyProjectPerson(personId,projectId,param);
                    await ito.alert("완료 되었습니다.");
                    await this.setConfirmPersonInfo();
                    await this.setPersonInfo();

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
                            var projectParam=[];
                             for(var i in deleteList){
                                    projectParam.push({"personId": deleteList[i],"projectId": projectId });
                             }

                            await ito.api.common.projectPerson.removeProjectPersonList(projectParam)
                            await ito.alert("삭제되었습니다.")
                        }
                        await this.setConfirmPersonInfo()
                        await this.setPersonInfo();
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
                                    console.log(self.project.items.skill)

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
                        var params = {}, count=0;
                        params.projectId = !_.isEmpty(projectId) ? projectId : null;
                        self.confirmPerson.dataTable.loading = true;
                        var data = (await ito.api.common.projectPerson.getProjectPersonList(params)).data
                        self.confirmPerson.dataTable.items = [];

/*                        data.items.forEach(e=> {
                            if(e.status == "T"){
                                 let personId = e.person.id;
                                 var careerId = await ito.api.app.involvement.getCareerIdList(projectId, personId);
                                 var careerData = await ito.api.common.career.getCareer(careerId);
                                 e.actualPay = careerData.pay;
                                 self.confirmPerson.dataTable.items.push(e.person);
                                 count++;
                            }
                        });
*/
                        for(var i =0; i < data.items.length ; i++ ){
                            if(data.items[i].status == "T"){
                                 let personId = data.items[i].person.id;
                                 let careerId = (await ito.api.app.involvement.getCareerIdList(projectId, personId)).data;
                                 var careerData = (await ito.api.common.career.getCareer(careerId)).data;
                                 data.items[i].person.actualPay = careerData.pay;
                                console.log(data.items[i].actualPay);
                                 self.confirmPerson.dataTable.items.push(data.items[i].person);
                                 count++;
                            }
                        }

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
                        self.confirmPerson.dataTable.totalRows = count;
                        self.confirmPerson.dataTable.loading = false;
                  },

                "setPersonInfo": async function(options){
                    var self = this;
                    var projectId = await self.$route.query.id;
                    var params = {}, count=0;
                    params.projectId = !_.isEmpty(projectId) ? projectId : null;
                    self.person.dataTable.loading = true;
                    var data = (await ito.api.common.projectPerson.getProjectPersonList(params)).data
                    self.person.dataTable.items = [];
                    data.items.forEach(e=> {
                        if(e.status == "F")  {
                            self.person.dataTable.items.push(e.person);
                             count++;
                            }
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
                    console.log(self.person.dataTable.items);
                    self.person.dataTable.totalRows = count;
                    console.log(" items 값  출력      " + self.person.dataTable.items)
                    self.person.dataTable.loading = false;
                },
            },
            "mounted": async function () {
                var self = this;
                await self.setProjectInfo();
                await self.setPersonInfo();
                await self.setConfirmPersonInfo();
            }
        });
    });

});
