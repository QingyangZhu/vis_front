<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
    
  </div>
</template>

<script>
import { data2018 } from '../data/data.js';
export default {
  name: 'HelloWorld',
  data () {
    const neo_data2018 = data2018.find(item => item.理想).理想.流动比率;
    return {

      msg: neo_data2018
    }
    
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
