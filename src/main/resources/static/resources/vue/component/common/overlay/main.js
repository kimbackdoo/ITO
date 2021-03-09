var OverlayMainComponent = Vue.component('overlay-main-component', function (resolve, reject) {
    axios.get("/resources/vue/component/common/overlay/main.html").then(function (response) {
        resolve({
            "template": response.data,
            "props": {
                "value": {
                    "type": Object,
                    "default": null
                }
            }
        });
    });
});