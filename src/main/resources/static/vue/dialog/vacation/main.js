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
                    "phone": null,
                    "department": null,
                    "position": null,
                    "startPeriod": null,
                    "endPeriod": null,
                    "detailContent": null,
                    "division": null
                },
                "select": {
                    "division": {
                        "items": [
                            {"text": "월차", "value": "m"},
                            {"text": "반차", "value": "o"},
                            {"text": "병가", "value": "s"},
                            {"text": "기타", "value": "e"},
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
                self.vacation.phone = null;
                self.vacation.department = null;
                self.vacation.position = null;
                self.vacation.startPeriod = null;
                self.vacation.endPeriod = null;
                self.vacation.detailContent = null;
                self.vacation.division = null;
            },
            "saveVacation": function() {
                this.$emit("save", this.vacation);
                this.dialog.visible = false;
            }
        },
    });
});