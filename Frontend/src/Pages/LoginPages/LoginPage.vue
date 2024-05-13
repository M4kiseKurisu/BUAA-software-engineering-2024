<template>
    <div class="LoginPage">
        <LoginCard />
    </div>
</template>

<script>
import LoginCard from "./LoginCard/LoginCard.vue"
export default {
    components: {
        LoginCard,
    }
}
</script>

<style scoped>
.LoginPage {
    width: 100vw;
    height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
    background-image: url("../../../src/Images/loginBackground.png");
    background-size: cover;
    background-position: center;
}
</style>
