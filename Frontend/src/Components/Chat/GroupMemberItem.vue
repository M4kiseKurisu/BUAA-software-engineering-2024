<template>
    <div style="width: 55px;height: 60px;margin-bottom: 5px;margin-right: 5px;" @click = "goToShowPersonInfomation">
        <div style="width: 50px;height: 50px;display: flex;align-items: center;justify-content: center;">
            <img :src="memberAvatar" alt=""
                    style="height: 45px;aspect-ratio: 1/1 ;border-radius: 50%;">
        </div>
        <div style="width: 50px;height: 10px;display: flex;align-items: center;justify-content: center;font-size: small;">
            {{ partName }}
        </div>
    </div>
</template>

<script>
import { objectPick } from '@vueuse/shared';
import axios from 'axios';
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
export default{
    props:{
        memberInfo:{
            type: Object,
            default: null,
        },

    },
    data(){
        return {
            memberId: 1,
            memberAvatar: '',
            memberName: 'bojiang',
        }
    },
    computed:{
        partName(){
            var name;
            if(this.memberName.length > 3){
                name = this.memberName.substring(0,3);
                name = name + "...";
                return name;
            } else {
                return this.memberName;
            }
        }
    },
    methods:{
        goToShowPersonInfomation(){
            //this.$router.push({ path: "/MainPage/Course_Center/ShowPersonalInformation/" + this.memberId});
            let routeUrl = this.$router.resolve({
                path: "/MainPage/Course_Center/ShowPersonalInformation/"+ this.memberId ,
            });
            window.open(routeUrl.href, '_blank');
        }
    },
    created(){
        if(this.memberInfo != null){
            this.memberId = this.memberInfo.user_id;
            this.memberAvatar = this.memberInfo.image;
            this.memberName = this.memberInfo.name;
        }
    },
}
</script>

<style>

</style>