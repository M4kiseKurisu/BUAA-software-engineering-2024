<template>
  <div class="img-container">
    <div class="img-container-it">
      <img class="img-of-creator" src="../../Images/testAvatar.jpg" alt="name">
    </div>
    <div class="name-container">
      <span>姓名</span>
    </div>

  </div>
</template>
<script>
export default {
  //props: ["img","name"],
}
</script>
<style>
.img-container{
  display: block;
  //justify-content: center;
  //background: #8c939d;
  width: 100%;
}
.img-container-it{
  display: flex;
  justify-content: center;
}
.img-of-creator{
  max-width: 80%;
  aspect-ratio: 1/1;
  border: 1px solid darkgray;
  border-radius: 5%;
}
.name-container{
  display: flex;
  justify-content: center;
  font-size: 16px;
}
</style>
