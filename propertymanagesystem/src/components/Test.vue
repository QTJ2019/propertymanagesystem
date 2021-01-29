<template>
  <div id="Test">
   <button @click="test">test </button>
  </div>
</template>

<script>

export default {
  name: 'Test',
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