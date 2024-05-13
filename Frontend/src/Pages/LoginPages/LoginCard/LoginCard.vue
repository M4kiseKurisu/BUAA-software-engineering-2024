<template>
    <div :class="{'card-size': screen_width > 900, 'card-size-small': screen_width <= 900, 'flex-layout': true}">
        <div v-if="screen_width > 900" class="half-content flex-layout">
            <img class="full-content" :src="login_picture" fit="fit" />
        </div>

        <div :class="{'half-content': screen_width > 900, 'full-content': screen_width <= 900, 'flex-layout': true, 'column-center': true }">
            <card_content v-if="this.show_page === 1" @click-link="change_page"/>
            <card_register v-if="this.show_page === 2" @click-link="change_page"/>
            <card_findback v-if="this.show_page === 3" @click-link="change_page"/>
        </div>
    </div>
</template>

<script>
import card_content from "./content.vue";
import card_register from "./register.vue"
import card_findback from "./findback.vue"
export default {
    components: {
        card_content,
        card_register,
        card_findback,
    },
    data() {
        return {
            show_page: 1,
            login_picture: "./src/Pages/LoginPages/LoginCard/asset_login_picture.png",
            screen_width: 0,
        }
    },
    methods: {
        change_page(page) {
            this.show_page = page;
        },
        handle_resize() { // 更新屏幕宽度
            this.screen_width = window.innerWidth; 
        },
    },
    mounted() { // 监听窗口大小变化
        this.screen_width = window.innerWidth;
        window.addEventListener('resize', this.handle_resize);
    },
    beforeDestroy() { // 移除监听
        window.removeEventListener('resize', this.handle_resize);
    },
}
</script>

<style>
@import './login-card.css';
</style>