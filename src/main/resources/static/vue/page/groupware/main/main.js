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
                    "focus": '',
                    "events": [],
                    "names": []
                }
            };
        },
        "methods": {
            "prev": function() {
                this.$refs.calendar.prev();
            },
            "next": function() {
                this.$refs.calendar.next();
            },
            "loadCalendar": function({start, end}) {
                let events = [];
                let min = new Date(`${start.date}T00:00:00`),
                    max = new Date(`${end.date}T23:59:59`),
                    days = (max.getTime() - min.getTime()) / 86400000,
                    eventCount = this.rnd(days, days+20)

                for(let i=0; i<eventCount; i++) {
                    let allDay = this.rnd(0,3) === 0,
                        firstTimeStamp = this.rnd(min.getTime(), max.getTime()),
                        first = new Date(firstTimeStamp - (firstTimeStamp % 900000)),
                        secondTimeStamp = this.rnd(2, allDay ? 288 : 8) * 900000,
                        second = new Date(first.getTime() + secondTimeStamp);

                    events.push({
                        "start": first,
                        "end": second,
                        "timed": !allDay,
                    });
                }
                this.calendar.events = events;
            },
            "rnd": function(a, b) {
                return Math.floor((b-a+1) * Math.random() + a);
            },
        },
    });
});