var FileUploadDialogComponent;
FileUploadDialogComponent = Vue.component('file-upload-dialog-component', async function(resolve) { resolve({
        "template": (await axios.get("/vue/dialog/file-upload/main.html")).data,
        "props": {
            "dialog": {
                "type": Object,
                "default": {
                    "visible": false,
                    "title": "파일 등록",
                    "data": {}
                }
            }
        },
        "data": function() {
            return {
                "dataUpload":{
                    "selectedFile" : null,
                    "selectedFileName" : null,
                    "seletedType" : 1,
                    "uploadReferenceMonth" : moment().format('YYYY-MM'),
                },
            }
        },
        "watch": {
            "dialog.visible": {
                "handler": function (n) {
                    if(!n) {
                        Object.assign(this.$data, this.$options.data.apply(this));
                    }
                }
            }
        },
        "methods": {
            "onButtonClick" : function (){
                this.isSelecting = true
                window.addEventListener('focus', () => {
                    this.isSelecting=false
                }, {once: true})
                this.$refs.uploader.click()
            },
            "onFileChanged": function(e) {
                this.dataUpload.selectedFile = e.target.files[0];
                if(this.dataUpload.selectedFile != undefined && this.dataUpload.selectedFile != null){
                    this.dataUpload.selectedFileName = this.dataUpload.selectedFile.name;
                }else{
                    this.dataUpload.selectedFileName=null;
                }
            },
            "onFileUpload": async function() {
                var self = this, form = new FormData();
                //선택한 파일이 있을때
                if(self.dataUpload.selectedFile != null && self.dataUpload.selectedFile.size > 0){
                    store.commit("app/SET_LOADING", true);
                    form.append("file" , self.dataUpload.selectedFile);
                    form.append("fileType" , self.dataUpload.seletedType);

                    if(self.dataUpload.seletedType == 1 || self.dataUpload.selectedType == 2)
                        form.append("uploadReferenceMonth" , self.dataUpload.uploadReferenceMonth);

                    var returnType = await ito.api.app.dataUpload.uploadDataFile(form);
                    store.commit("app/SET_LOADING", false);

                    if(returnType.data.returnVal != 'success'){
                        await ito.alert(returnType.data.returnMsg);
                    }else{
                        await ito.alert("업로드에 성공했습니다!!");
                        self.fileClear();
                        self.setUserInfoList();
                    }
                }else{
                    await ito.alert("파일을 선택해주세요.");
                }
            },
            "fileClear": function() {
                document.getElementById("hiddenFile").value = "";
                this.dataUpload.selectedFileName = null;
                this.dataUpload.selectedFile = null;
            },
            "clear": function() {
                this.dialog.visible = false;
                this.fileClear();
                self.setUserInfoList();
            },
        },
    });
});