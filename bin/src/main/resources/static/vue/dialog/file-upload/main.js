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
                    "selectedFileName" : null
                },
            }
        },
        "watch": {
            "dialog.visible": {
                "handler": function (n) {
                    if(!n) {
                        this.fileClear();
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
            "onFileUpload" : async function() {
                var self = this;

                if(self.dataUpload.selectedFile != null && self.dataUpload.selectedFile.size > 0) {
                    this.$emit("upload", self.dataUpload);
                } else {
                    await ito.alert('파일을 선택해주세요.');
                }
            },
            "fileClear": function() {
                document.getElementById("hiddenFile").value = "";
                this.dataUpload.selectedFileName = null;
                this.dataUpload.selectedFile = null;
            },
            "clear": function() {
                this.dialog.visible = false;
            },
        },
    });
});