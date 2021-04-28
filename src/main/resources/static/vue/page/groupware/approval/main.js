var GroupwareApprovalPage;
GroupwareApprovalPage = Vue.component('groupware-approval-page', async function(resolve) {
   resolve({
       "template": (await axios.get("/vue/page/groupware/approval/main.html")).data,
       "data": function() {
           return {

           };
       },
       "methods": {
           "saveApproval": async function() {
               let userId = store.state.app.user.id;

               if(await ito.confirm("승인하시겠습니까?")) {
                   switch (userId) {
                       case 1:
                           break;
                       case 2:
                           break;
                       case 3:
                           break;
                   }

                   await ito.alert("승인되었습니다.");
               }
           },
           "rejectApproval": async function() {
               if(await ito.confirm("거절하시겠습니까?")) {
                   await ito.alert("거절되었습니다.");
                   this.$router.push({
                       "path": "/groupware/main"
                   });
               }
           }
       }
   });
});