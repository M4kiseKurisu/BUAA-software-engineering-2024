<template>
    <div style="width: (100/3)%; margin-top : 5px" @click="goToShowPersonInfomation()">
        <img :src="headImg" style="width: 80%;aspect-ratio: 1/1 ; border: 1px solid darkgray;border-radius: 10%;">
    </div>
</template>

<script>
import axios from 'axios';
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
export default {
    props: {
        personId: {
            type: Number,
            default: 1,
        }
    },
    data() {
        return {
            headImg: '',
            //personId: '',
            personName: '博酱',
        }
    },
    methods: {
        GetInfomation() {
            axios({
                method: "GET",
                url: "/api/user/social/others",
                params: { id: this.personId }
            }).then((result) => {
                console.log(result);
                this.headImg = result.data.user_avatar;
                this.personName = result.data.name;
            });
        },
        goToShowPersonInfomation() {
            //this.$router.push({ path: "/MainPage/Course_Center/ShowPersonalInformation/" });
            let routeUrl = this.$router.resolve({
                path: "/MainPage/Course_Center/ShowPersonalInformation/"+ this.personId ,
            });
            window.open(routeUrl.href, '_blank');
        }
    },

    created() {
        //console.log(this.personId);
        this.GetInfomation();
    }
}
</script>

<style></style>