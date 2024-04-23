<template>
    <div style="width: (100/3)%;">
        <img src="../../Images/testAvatar.jpg"
                        style="max-width: 80%;aspect-ratio: 1/1 ; border: 1px solid darkgray;border-radius: 10%;">
    </div>
</template>

<script>
import axios from 'axios';
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
export default{
    props:{

    },
    data(){
        return {
            headImg: '',
            personId: '',
            personName: '博酱',
        }
    },
    methods:{
        GetInfomation(){
            
        }
    }
}
</script>

<style>

</style>