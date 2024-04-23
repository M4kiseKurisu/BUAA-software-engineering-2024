<template>
    <div style="width: (100/3)%;">
        <el-avatar :size = '75' src="./src/Images/testAvatar.jpg" shape="square" fit="cover"/>
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