var VacationDialogComponent;
VacationDialogComponent = Vue.component('vacation-dialog-component', async function(resolve) {
    resolve({
        "template": (await axios.get("/vue/dialog/vacation/main.html")).data,
        "props": {
            "dialog": {
                "type": Object,
                "default": {
                    "visible": false,
                    "title": "휴가 신청서",
                    "data": {}
                }
            }
        },
        "data": function() {
            return {
                "vacation": {
                    "name": null,
                    "emergencyNum": null,
                    "department": null,
                    "takingUser": null,
                    "sterm": null,
                    "eterm": null,
                    "detail": null,
                    "type": null
                },
                "select": {
                    "type": {
                        "items": [
                            {"text": "월차", "value": "M"},
                            {"text": "연차", "value": "N"},
                            {"text": "반차", "value": "O"},
                            {"text": "병가", "value": "S"},
                            {"text": "기타", "value": "E"},
                        ]
                    }
                }
            };
        },
        "watch": {
            "dialog.visible": {
                "handler": function(n) {
                    if(n) {
                        this.vacation = _.cloneDeep(this.dialog.data);
                        this.vacation.type = "M";
                    } else {
                        Object.assign(this.$data, this.$options.data.apply(this));
                    }
                }
            }
        },
        "methods": {
            "initialize": async function() {
                let self = this;
                self.vacation.name = null;
                self.vacation.emergencyNum = null;
                self.vacation.department = null;
                self.vacation.takingUser = null;
                self.vacation.sterm = null;
                self.vacation.eterm = null;
                self.vacation.detail = null;
                self.vacation.type = null;
            },
            "saveVacation": function() {
                this.$emit("save", this.vacation);
                this.dialog.visible = false;
            }
        },
    });
});