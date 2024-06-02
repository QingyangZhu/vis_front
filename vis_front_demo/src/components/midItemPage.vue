<template>
    <div class="itemMid">
      <!-- 每个小图通用的插槽 -->
      <slot></slot>
    </div>
  </template>
  
  <script>
  export default {
  
  }
  </script>
  
  <style>
  .itemMid{
    height: 10rem;
    border: 1px solid blue;
    margin: .25rem;
    background-color: rgba(12,130,255,.7);
   /*  background-color: rgba(19, 117, 215, 0.551); */
  }
  
  </style>