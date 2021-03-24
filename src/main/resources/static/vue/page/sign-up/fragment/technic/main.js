var SignUpMainTechnicFragment;
SignUpMainTechnicFragment = Vue.component("sign-up-main-technic-fragment", async function (resolve) { resolve({
    "template": (await axios.get("/vue/page/sign-up/fragment/technic/main.html")).data,
    "data": function () {
        return {
        };
    },
    "watch": {
    },
    "methods": {
    },
    "created": async function () {
    }
}); });