<template>
  <div id="Test">
   <button @click="test">test </button>
   <div class="block">
     <el-image :src="url"></el-image>
   </div>
  </div>
</template>

<script>

export default {
  name: 'Test',
  data(){
    return {
      url:"https://cube.elemecdn.com/6/94/4d3ea53c084bad6931a56d5158a48jpeg.jpeg"
    }
  },
  methods: {
      test: function(){
          const url = "/system/res/test";
    
          this.$http.post(url)
                .then(function(response){
                    console.log(response);
                },function(err){
                    console.log(err);
                })
      }
  }

}
</script>

<style>

</style>