var ApplyProjectMainComponent;
ApplyProjectMainComponent = Vue.component('applyProject-main-component', async function(resolve) {
    resolve({
        "template": (await axios.get("/vue/page/main/user/apply-project/main.html")).data,
        "data": function() {
            return {

            };
        },
    });
});