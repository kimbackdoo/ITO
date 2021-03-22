var InputProfileCareerMainComponent;
InputProfileCareerMainComponent = Vue.component('inputProfile-career-main-component', async function(resolve) {
    resolve({
        "template": (await axios.get("/vue/page/main/user/input-profile/main-career.html")).data,
        "data": function() {
            return {
                "career": {
                    "selected": [],
                    "dataTable": {
                        "headers": [
                            {"text": "프로젝트명", "value": "name"},
                            {"text": "기간", "value": "period"},
                            {"text": "직급", "value": "rank"},
                            {"text": "담당업무", "value": "job"},
                            {"text": "월급여", "value": "salary"},
                        ]
                    }
                },

            }
        },
        "methods": {

        }
    });
});
