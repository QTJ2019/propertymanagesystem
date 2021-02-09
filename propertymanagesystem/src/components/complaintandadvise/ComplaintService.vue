<template>
  <div id="ComplaintService">
      <el-form :model="ruleForm" status-icon  ref="ruleForm" label-width="100px" class="demo-ruleForm">
  <el-form-item label="用户名" >
    <el-input v-model="ruleForm.account" style="width:40%;" maxlength=19 disabled></el-input>
  </el-form-item>
  <el-form-item label="手机号">
    <el-input  v-model="ruleForm.phone" style="width:40%;" disabled></el-input>
  </el-form-item>
  <el-form-item label="投诉内容">
    <el-input type="textarea" placeholder="请输入您的投诉内容" v-model="ruleForm.complaintContext"></el-input>
  </el-form-item>
  <el-form-item>
      <el-upload
  action="https://jsonplaceholder.typicode.com/posts/"
  list-type="picture-card"
  :limit="3"
  :on-exceed="handleExceed"
  :on-preview="handlePictureCardPreview"
  :on-remove="handleRemove">
  <i class="el-icon-plus"></i>
</el-upload>
<el-dialog :visible.sync="dialogVisible">
  <img width="100%" :src="dialogImageUrl" alt="">
</el-dialog>
  </el-form-item>
  
  <el-form-item class="buttonForm" style="width:39%;">
    <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
  </el-form-item>
</el-form>
  </div>
</template>

<script>

export default {
  name: 'ComplaintService',
  data() {
      return {
      ruleForm: {
        account:'',
        phone:'',
        complaintContext:''
        } ,
        dialogVisible: false,
        dialogImageUrl:""
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
      },
      getUserInformation(){
          const url = "/system/baseinformationadmin/getuserinformation"
          console.log("getUserInformation内部");
          this.$http.get(url)
                    .then((res) =>{
                        console.log(res);
                        const response = res.data;
                        if(response.success){
                            this.ruleForm.account = response.data.userInformation.account;
                            this.ruleForm.phone = response.data.userInformation.phone;
                        }else {
                            this.$message.error(response.message);
                        }
                    });
      },
      handleExceed(){
        this.$message.error("最多上传3张图片")
      },
      handleRemove(file, fileList) {
        console.log(file, fileList);
      },
      handlePictureCardPreview(file) {
        this.dialogImageUrl = file.url;
        console.log(file.url);
        this.dialogVisible = true;
      }
  }

}
</script>

<style>

</style>