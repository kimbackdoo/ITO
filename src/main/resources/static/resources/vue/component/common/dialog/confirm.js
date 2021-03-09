var DialogConfirmComponent = Vue.component('dialog-confirm-component', function (resolve, reject) {
    axios.get("/resources/vue/component/common/dialog/confirm.html").then(function (response) {
        resolve({
            "template": response.data,
            "props": {
                "value": {
                    "type": Object,
                    "default": {
                        "visible": false,
                        "title": "Confirm",
                        "text": "",
                        "callback": function () {}
                    }
                }
            },
            "watch": {
                "value.visible": {
                    "handler": function (newValue, oldValue) {
                        if (!newValue) {
                            this.value.title = "Confirm";
                            this.value.text = "";
                            this.value.callback = function () {};
                        }
                        this.$emit("input", this.value);
                    },
                    "deep": true
                }
            },
            "methods": {
                "yes": function () {
                    this.value.visible = false;
                    this.value.callback(true);
                },
                "no": function () {
                    this.value.visible = false;
                    this.value.callback(false);
                }
            },
            "updated": function () {
                var self = this;
                if (self.value.visible) {
                    self.$nextTick(function () {
                        self.$refs.confirmOk.$el.focus();
                    });
                }
            }
        });
    });
});