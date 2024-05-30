<template>
  <div class="item">
    <!-- 每个小图通用的插槽 -->
    <slot></slot>
  </div>
</template>

<script>
export default {

}
</script>

<style>
.item{
  height: 5rem;
  border: 1px solid blue;
  margin: .15rem;
  background-color: rgba(12,130,255,.85);
}

</style>