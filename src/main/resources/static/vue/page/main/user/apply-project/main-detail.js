var ApplyProjectDetailMainComponent;
ApplyProjectDetailMainComponent = Vue.component('applyProject-detail-main-component', async function(resolve) {
   resolve({
       "template": (await axios.get("/vue/page/main/user/apply-project/main-detail.html")).data,
        "data": function() {
           return {
               "project": {
                   "panels": {
                       "list": [0],
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
               let self = this,
                   project = (await ito.api.common.project.getProject(queryId)).data;

               switch (project.status) {
                   case 'P': project.statusName = "투입"; break;
                   case 'I': project.statusName = "면접"; break;
                   case 'C': project.statusName = "완료"; break;
                   case 'A': project.statusName = "섭외"; break;
               }

               self.project.items = project;
           },
           "applyProject": async function() {
               let personId, projectId
               personId = store.state.app.person.id;
               projectId = this.$route.query.id;

               if(await ito.confirm("지원하시겠습니까?")) {
                   await ito.api.common.projectPerson.createProjectPerson({
                       "personId": personId,
                       "projectId": projectId
                   });
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