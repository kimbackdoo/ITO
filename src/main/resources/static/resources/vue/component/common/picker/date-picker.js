var DatePickerComponent = Vue.component('date-picker-component', function (resolve, reject) {
    axios.get("/resources/vue/component/common/picker/date-picker.html").then(function (response) {
        resolve({
            "template": response.data,
            "props": {
                "appendIcon": {
                    "type": String,
                    "default": undefined
                },
                "value": {
                    "type": String,
                    "default": ""
                },
                "label": {
                    "type": String,
                    "default": ""
                },
                "dense": {
                    "type": Boolean,
                    "default": false
                },
                "dayFormat": {
                    "type": Function,
                    "default": function (value) {
                        return moment(value, "YYYY-MM-DD").format("D");
                    }
                },
            },
            "data": function () {
                return {
                    "dialog": false
                };
            },
            "watch": {
                "value": {
                    "handler": function (newValue, oldValue) {
                        this.$emit("input", newValue);
                    }
                },
                "dialog": {
                    "handler": function (newValue, oldValue) {
                        newValue && this.$nextTick(function () {
                            this.$refs.datePicker.tableDate = this.value
                                    ? this.value.substring(0, 7)
                                    : moment().format('YYYY-MM');
                            this.$refs.datePicker.activePicker = "DATE";
                        });
                    }
                }
            }
        });
    });
});