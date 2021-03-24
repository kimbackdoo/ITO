var SettingCodeMainPage;
SettingCodeMainPage = Vue.component("setting-code-main-page", async function (resolve) { resolve({
    "template": (await axios.get("/vue/page/setting/code/main.html")).data,
    "data": function () {
        return {
            "data": {
                "codeList": [],
                "code": {"status": null}
            },
            "treeview": {
                "codeList": {
                    "items": [],
                    "open": [],
                    "active": []
                }
            },
            "autocomplete": {
                "parentId": {
                    "items": []
                }
            },
            "btn": {
                "saveCode": {
                    "loading": false
                },
                "removeCode": {
                    "loading": false
                }
            }
        };
    },
    "watch": {
        "data.codeList": {
            "handler": function (newValue, oldValue) {
                this.treeview.codeList.items = this.flatArrayToNestedArray(newValue, "id", "parentId");
                this.treeview.codeList.open = _.cloneDeep(newValue);
                this.autocomplete.parentId.items = _.cloneDeep(newValue);

            }
        },
        "treeview.codeList.active": {
            "handler": function (newValue, oldValue) {
                this.data.code = newValue[0] ? _.cloneDeep(newValue[0]) : {"status": null};
            }
        }
    },
    "methods": {
        "loadCodeList": async function () {
            var codeList;
            codeList = (await ito.api.common.code.getCodeList({
                "page": 1,
                "rowSize": 100000000,
            })).data.items;
            codeList.forEach(e=>{
                e.namePath=e.name + ' - ' + e.id;
                e.db = 'T';
                if(codeList.find(el => e.id === el.parentId)) {
                    e.childrenCount = 1;
                }
            });
            this.data.codeList = codeList;
        },
        "saveCode": async function () {
            var code;
            code = this.data.code;
            this.btn.saveCode.loading = true;
            if (await ito.confirm("저장 하시겠습니까?")) {
                if (code.id) {
                    code = (await ito.api.common.code.modifyCode(code.id, code)).data;
                } else {
                    console.log(code);
                    code = (await ito.api.common.code.createCode(code)).data;
                }
                await ito.alert("저장 되었습니다.");
                await this.loadTreeCodeList();
                this.treeview.codeList.active = [code];
            }
            this.btn.saveCode.loading = false;
        },
        "removeCode": async function () {
            var code;
            code = this.data.code;
            this.btn.removeCode.loading = true;
            if (await ito.confirm("삭제 하시겠습니까?")) {
                await ito.api.common.code.removeCode(code.id);
                await ito.alert("삭제 되었습니다.");
                await this.loadCodeList();
                this.treeview.codeList.active = [];
            }
            this.btn.removeCode.loading = false;
        },
        "flatArrayToNestedArray": function (flatArray, id, parentId, children) {
            var nestedArray,
                map,
                flat,
                i;
            flatArray = _.cloneDeep(flatArray);
            children = children ? children : "children";
            nestedArray = [];
            map = {};
            for (i = 0; i < flatArray.length; i++) {
                map[flatArray[i][id]] = i;
                flatArray[i][children] = [];
            }
            for (i = 0; i < flatArray.length; i++) {
                flat = flatArray[i];
                if (flat[parentId]) {
                    flatArray[map[flat[parentId]]][children].push(flat);
                } else {
                    nestedArray.push(flat);
                }
            }
            return nestedArray;
        }
    },
    "mounted": async function () {
        Promise.all([
            this.loadCodeList()
        ]);
    }
}); });