<template>
  <div id="ComplaintService">
    <el-breadcrumb separator="/">
  <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
  <!-- <el-breadcrumb-item><a href="/">活动管理</a></el-breadcrumb-item> -->
  <el-breadcrumb-item>投诉管理</el-breadcrumb-item>
  <el-breadcrumb-item>发起投诉</el-breadcrumb-item>
</el-breadcrumb>
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
  action="none"
  :auto-upload="false"
  list-type="picture-card"
  accept=".jpg"
  :limit="3"
  :on-exceed="handleExceed"
  :on-preview="handlePictureCardPreview"
  :on-remove="handleRemove"
  :on-change="imgChange">
  <i class="el-icon-plus"></i>
</el-upload>
<el-dialog :visible.sync="dialogVisible">
  <img width="100%" :src="dialogImageUrl" alt="">
</el-dialog>
  </el-form-item>
  
  <el-form-item class="buttonForm" style="width:39%;">
    <el-button type="primary" @click="submit">提交</el-button>
  </el-form-item>
</el-form>
  </div>
</template>

<script>

export default {
  name: 'ComplaintService',
  created(){
    this.getUserInformation();
  },
  data() {
      return {
        ruleForm:{
        account:'',
        phone:'',
        complaintContext:'',
        upLoadImgList:[]
        },
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
      // 上传的图片数量超过限制时，调用的函数
      handleExceed(){
        this.$message.error("最多上传3张图片")
      },
      handleRemove(file, fileList) {
        console.log(file, fileList);
        this.ruleForm.upLoadImgList = [];
        for(let j =0;j< fileList.length;j++){
          this.ruleForm.upLoadImgList[j] = fileList[j].raw;
        }
        console.log("remove");
        console.log(this.ruleForm.upLoadImgList);
      },
      handlePictureCardPreview(file) {
        this.dialogImageUrl = file.url;
        console.log(file.url);
        this.dialogVisible = true;
      },
      imgChange(file, fileList) {
        this.ruleForm.upLoadImgList.push(file.raw);
        console.log("imgChange!")
        console.log(this.ruleForm.upLoadImgList);
        console.log(fileList);
      },
      submit(){
          const  fd = new FormData();
          fd.append("phone",this.ruleForm.phone);
          fd.append("complaintContext",this.ruleForm.complaintContext);
          for(let j=0;j<this.ruleForm.upLoadImgList.length;j++){
            fd.append("upLoadImgList",this.ruleForm.upLoadImgList[j]);
          }
          console.log(fd);
          this.$http({
            url: "/system/lifeservice/addcomplaint",
            method: "post",
            data:fd,
            header: {
              "Content-Type":"multipart/form-data"
            }
          }).then((res) =>{
             if(res.data.success){
               this.$message.success("提交投诉成功");
             }else {
               this.$message.error(res.data.message);
             }
          });
      }
  }
}
</script>

<style>

</style>