<template>
    <div class="line-container flex-layout">
        <label class="input-title">{{ this.input_title }}</label>
        <input v-model="this.content"
            :class="{ 'input-box': true, 'input-box-warning': this.is_warning }" @blur="this.checkContent"
            :type="this.input_type"/>
    </div>
    <div class="warning-container flex-layout">
        <div v-if="this.is_warning" class="input-warning">{{ this.warning_content }}</div>
    </div>
</template>

<script>
import { input_check } from './input_check.js';

export default {
    props:["input_title", "input_type"],
    data() {
        return {
            content: "",
            is_warning: false,
            warning_content: "",
        }
    },
    methods: {
        checkContent() {
            this.warning_content = input_check(this.content, this.input_type, this.input_title);
            this.is_warning = (this.warning_content != true);
        },
        showWarning(info) {
            this.warning_content = info;
            this.is_warning = true;
        }
    }
}
</script>

<style scoped>
@import './login-card.css';
</style>