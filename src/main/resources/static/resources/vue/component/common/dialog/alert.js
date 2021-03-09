var DialogAlertComponent = Vue.component('dialog-alert-component', function (resolve, reject) {
    axios.get("/resources/vue/component/common/dialog/alert.html").then(function (response) {
        resolve({
            "template": response.data,
            "props": {
                "value": {
                    "type": Object,
                    "default": {
                        "visible": false,
                        "title": "Alert",
                        "text": "",
                        "callback": function () {}
                    }
                }
            },
            "watch": {
                "value.visible": {
                    "handler": function (newValue, oldValue) {
                        if (!newValue) {
                            this.value.callback();
                            this.value.title = "Alert";
                            this.value.text = "";
                            this.value.callback = function () {};
                        }
                        this.$emit("input", this.value);
                    },
                    "deep": true
                }
            },
            "updated": function () {
                var self = this;
                if (self.value.visible) {
                    self.$nextTick(function () {
                        self.$refs.alertOk.$el.focus();
                    });
                }
            }
        });
    });
});