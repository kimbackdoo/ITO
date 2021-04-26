var GroupwareMainPage;
GroupwareMainPage = Vue.component('groupware-main-page', async function (resolve) {
    resolve({
        "template": (await axios.get("/vue/page/groupware/main/main.html")).data,
        "data": function() {
            return {
                "calendar": {
                    "panels": {
                        "list": [0]
                    },
                    "selectedEvent": {},
                    "selectedElement": null,
                    "selectedOpen": false,
                    "events": [],
                    "names": [],
                    "move": '',
                },
                "dialogVacation": {
                    "visible": false,
                    "title": "휴가 신청서",
                    "data": {}
                }
            };
        },
        "methods": {
            "prevCalendar": function() {
                this.$refs.calendar.prev();
            },
            "nextCalendar": function() {
                this.$refs.calendar.next();
            },
            "showEvent": function({nativeEvent, event}) {
                console.log(nativeEvent, event);
            },
            "loadCalendar": async function({start, end}) {
                let userName, color, vacationList;
                vacationList = (await ito.api.app.vacation.getVacationList({
                    "rowSize": 100000000,
                    "sterm": start.date,
                    "eterm": end.date
                })).data.items;

                console.log(vacationList);

                for(let i=0; i<vacationList.length; i++) {
                    userName = (await ito.api.common.user.getUser(vacationList[i].userId)).data.username;
                    switch(vacationList[i].type) {
                        case "M": color="green"; break;
                        case "O": color="deep-purple"; break;
                        case "S": color="grey darken-1"; break;
                        case "E": color="orange"; break;
                    }

                    this.calendar.events.push({
                        "name": userName,
                        "start": vacationList[i].sterm,
                        "end": vacationList[i].eterm,
                        "color": color,
                    });
                }
                console.log(this.calendar.events);
            },
            "saveNotice": async function(data) {
                console.log(data);
                data.userId = store.state.app.user.id;
                if(await ito.confirm("저장하시겠습니까?")) {
                    await ito.api.app.vacation.createVacation(data);
                    await ito.alert("저장되었습니다.");

                    this.loadCalendar();
                }
            },
            "registerNotice": function() {
                this.$router.push({
                    "path": "/groupware/notices/details"
                });
            },
            "openVacationDialog": function() {
                this.dialogVacation.data = {};
                this.dialogVacation.visible = true;
            }
        },
    });
});