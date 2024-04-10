<template>
    <!-- 站内通知单元 -->
    <div class="notice-font">{{ this.notice }}</div>

    <div class="notice-seperator" />
</template>

<script>
export default {
    props: ["notice"],
}
</script>

<style scoped>
.notice-font {
    font-size: 14px;
    color: #86909c;
    overflow: hidden;
    word-wrap: break-word;
    margin-right: 16px;
}

.notice-seperator {
    border-top: 1px solid #bbbbbb !important;
    margin-top: 12px !important;
    margin-bottom: 12px !important;
}
</style>