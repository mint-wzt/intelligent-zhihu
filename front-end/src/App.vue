<template>
  <div id="app" class="background">
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'app',

}
</script>

<style>
  .background {
    min-height: 1000px;
    background-image: url(/sign_bg.png);
  }
</style>