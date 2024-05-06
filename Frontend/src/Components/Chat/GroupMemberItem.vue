<template>
    <div style="width: 50px;height: 60px;margin-bottom: 5px;margin-right: 5px;" @click = "goToShowPersonInfomation">
        <div style="width: 50px;height: 50px;display: flex;align-items: center;justify-content: center;">
            <img src="../../Images/testAvatar.jpg" alt=""
                    style="height: 45px;aspect-ratio: 1/1 ;border-radius: 50%;">
        </div>
        <div style="width: 50px;height: 10px;display: flex;align-items: center;justify-content: center;">
            {{ partName }}
        </div>
    </div>
</template>

<script>
export default{
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
            if(this.memberName.length > 5){
                name = this.memberName.substring(0,5);
                name = name + "...";
                return name;
            } else {
                return this.memberName;
            }
        }
    },
    methods:{
        goToShowPersonInfomation(){
            this.$router.push({ path: "/MainPage/Course_Center/ShowPersonalInformation/" + this.memberId});
        }
    }
}
</script>

<style>

</style>