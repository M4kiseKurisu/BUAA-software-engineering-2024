<template>
    <div class="personContainer">
        <el-avatar :size="70" src="./src/Images/testAvatar.jpg" />
        <span style="font-size: x-large;font-weight: bold;padding-left: 10px;">
            {{ name }}
        </span>
        <div style="margin-left: auto;">
            <el-button text > <img src="../../Images/省略号.png" alt=""> </el-button>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            personId: '',
            head: '',
            name: 'fyb',
        }
    }
}
</script>

<style scoped>
.personContainer {
    width: 100%;
    display: flex;
    align-items: center;
    background-color: rgb(240, 247, 252);
    margin-bottom: 5px;
}
</style>