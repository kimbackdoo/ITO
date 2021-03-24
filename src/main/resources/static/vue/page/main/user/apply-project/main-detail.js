var ApplyProjectDetailMainComponent;
ApplyProjectDetailMainComponent = Vue.component('applyProject-detail-main-component', async function(resolve) {
   resolve({
       "template": (await axios.get("/vue/page/main/user/apply-project/main-detail.html")).data,
        "data": function() {
           return {
               "project": {
                   "dataTable": {
                       "headers": [
                           {"text": "이름", "value": "name"},
                           {"text": "직종", "value": "job"},
                           {"text": "기술", "value": "skill"},
                           {"text": "경력기간", "value": "career"},
                           {"text": "희망급여", "value": "pay"},
                           {"text": "투입여부", "value": "status"},
                           {"text": "메모", "value": "memo"},
                       ]
                   },
                   "items": [],
               }
           }
        },
       "methods": {
           "init": async function() {
               await this.loadProjectList();
           },
           "setProject": async function(queryId) {
               let self = this;
               return new Promise(function (resolve, reject) {
                   Promise.resolve()
                       .then(function() {
                           return ito.api.common.project.getProject(queryId);
                       })
                       .then(function(response) {
                           self.project.items = response.data;
                           console.log(self.project.items);
                       })
                       .then(function() { resolve(); });
               });
           },
           "applyProject": async function() {
               if(await ito.confirm("지원하시겠습니까?")) {
                   await ito.alert("지원되었습니다.");
               }
           }
       },
       "mounted": function() {
           let queryId = this.$route.query.id;
           if(queryId != undefined && queryId != null) {
               Promise.resolve()
                   .then(this.setProject(queryId));
           }
       }
   });
});