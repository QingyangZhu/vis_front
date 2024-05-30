<template>
  <div>
    <header>
      <h1>会计信息可视化分析平台</h1>
    </header>
    <section class="container">
      <!-- 左容器 -->
      <section class="left-container">
        <ItemPage>
          <ItemOne />
        </ItemPage>
        <ItemPage>
          <ItemTwo />
        </ItemPage>
      </section>

      <!-- 中容器 -->
      <section class="mid-container">
        <MidItemPage>
          <ItemMidUp></ItemMidUp>
          <ItemMid></ItemMid>
        </MidItemPage>
      </section>

      <!-- 右容器 -->
      <section class="right-container">
        <ItemPage>
          <ItemThree />
        </ItemPage>
        <ItemPage>
          <ItemFour />
        </ItemPage>
      </section>
      
    </section>
  </div>
</template>

<script>
import ItemPage from "@/components/itemPage.vue";

import ItemOne from "@/components/itemOne.vue";
import ItemTwo from "@/components/itemTwo.vue";
import ItemThree from "@/components/itemThree.vue";
import ItemFour from "@/components/itemFour.vue";
import ItemMid from "@/components/itemMid.vue";
import MidItemPage from "@/components/midItemPage.vue";
import ItemMidUp from '@/components/itemMidUp.vue';


export default {
  components: {
    ItemPage,ItemOne,ItemTwo,ItemThree,ItemFour,ItemMid,MidItemPage,ItemMidUp
  },
  
  
}
</script>

<style >
header {
  height: 1rem;
  width: 100%;
  background-color: rgba(0,0,255,.2);
}
h1{
  font-size: .375rem;
  color: #fff;
  line-height: 1rem;
  text-align: center;
}
.container{
  min-width: 1200px;
  max-width: 2048px;
  margin: 0 auto;
  padding: 0.125rem 0.125rem 0;
  display: flex;
}
/* 设置左右容器的份数 */
.left-container{
  flex: 2.5;
  border: 1px solid #ff0000;
  background-color: rgba(255,0,0,.2);
}
.right-container{
  flex: 2.5;
  border: 1px solid #ff0000;
  background-color: rgba(0,255,0,.2);
}
.mid-container{
  flex: 5;
  border: 1px solid #ff0000;
  background-color: rgba(0,0,255,.2);
  height: 10.5rem;
  padding: 0.125rem;
  margin: 0.125rem;
}

</style>