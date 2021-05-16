var CareerDialogComponent;
CareerDialogComponent = Vue.component('career-dialog-component', async function(resolve) { resolve({
        "template": (await axios.get("/vue/dialog/career/main.html")).data,
        "props": {
            "dialog": {
                "type": Object,
                "default": {
                    "visible": false,
                    "title": "경력 등록",
                    "data": {}
                }
            }
        },
        "data": function() {
            return {
                "data": {
                    "career": {
                        "personCareerId": null,
                        "name": null,
                        "startPeriod": null,
                        "endPeriod": null,
                        "position": null,
                        "task": null,
                        "pay": null
                    }
                },
            }
        },
        "watch": {
            "dialog.visible": {
                "handler": function (n) {
                    if(n) {
                        this.data.career = _.cloneDeep(this.dialog.data);
                    }else {
                        Object.assign(this.$data, this.$options.data.apply(this));
                    }
                }
            }
        },
        "methods": {
            "initialize": async function() {
                let self = this;
                self.data.career.name = null;
                self.data.career.startPeriod = null;
                self.data.career.endPeriod = null;
                self.data.career.position = null;
                self.data.career.task = null;
                self.data.career.pay = null;
            },
            "saveCareer": function () {
                this.$emit("save", this.data.career);
                this.dialog.visible = false;
            }
        },
    });
});