var DaumPostCode = Vue.component('vue-daum-postcode', function (resolve, reject) {
    axios.get("/resources/vue/component/common/daumpost/vue-daum-postcode.html").then(function (response) {
        resolve({
            "data": function () {
                return {
                    "searchWindow": {
                        "display": 'none',
                        "height": '300px',
                    },
                    "postcode": '',
                    "address": '',
                    "extraAddress": '',
                };
            },
            "methods": {
                "execDaumPostcode": function() {
                    const currentScroll = Math.max(
                        document.body.scrollTop,
                        document.documentElement.scrollTop,
                    );

                    new daum.Postcode({
                        "onComplete": function(data) {
                            if (data.userSelectedType === 'R') this.address = data.roadAddress;
                            else this.address = data.jibunAddress;

                            if (data.userSelectedType === 'R') {
                                if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                                    this.extraAddress += data.bname;
                                }
                                if (data.buildingName !== '' && data.apartment === 'Y') {
                                    this.extraAddress += (this.extraAddress !== '' ? `, ${data.buildingName}` : data.buildingName);
                                }
                                if (this.extraAddress !== '') {
                                    this.extraAddress = ` (${this.extraAddress})`;
                                }
                            } else {
                                this.extraAddress = '';
                            }
                            this.postcode = data.zonecode;
                            this.$refs.extraAddress.focus();
                            this.searchWindow.display = 'none';
                            document.body.scrollTop = currentScroll;
                        },
                        onResize: (size) => {
                            this.searchWindow.height = `${size.height}px`;
                        },
                        width: '100%',
                        height: '100%',
                    }).embed(this.$refs.searchWindow);
                    this.searchWindow.display = 'block';
                },
            },
        })
    });
});