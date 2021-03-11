var InputProfileMainComponent = Vue.component('inputProfile-main-component', function(resolve, reject) {
   axios.get("/resources/vue/component/app/input-profile/main.html").then(function(response) {
      resolve({
         "template": response.data,
         "data": function () {
            return {
               "inputProfile": {
                  "panels": {
                     "form": [0]
                  },
                  "form": {
                     "item": {
                        "name": null,
                        "birth": null,
                        "email": null,
                        "career": null,
                     },
                  },
                  "skill": ["test1", "test2", "test3", "test4", "test5"],
                  "open": { "show": null }
               }
            };
         },
         "computed": {
         },
         "watch": {
         },
         "methods": {
            "createProfile": function (data) {
               console.log(data);
               return axios({
                  "url": "/api/common/profile",
                  "method": "post",
                  "data": data
               });
            },
            "saveProfile": async function () {
               var self = this;
               if(util.confirm({"text": "등록하시겠습니까?"})) {
                  var data = self.inputProfile.form.item;
                  self.createProfile(data);
                  util.alert({"text": "등록되었습니다!"});
               }
               /*return new Promise(function (resolve, reject) {
                  Promise.resolve()
                      .then(function () {
                         return util.confirm({"text": "등록하시겠습니까?"});
                      })
                      .then(function (isConfirmed) {

                      })
                      .then(function () { resolve(); });
               });*/
            },
         },
         "mounted": function() {
         }
      });
   });
});