<template>
    <div style="width: (100/3)%;">
        <img :src="headImg" style="max-width: 80%;aspect-ratio: 1/1 ; border: 1px solid darkgray;border-radius: 10%;">
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

    },
    created() {
        //console.log(this.personId);
        this.GetInfomation();
    }
}
</script>

<style></style>