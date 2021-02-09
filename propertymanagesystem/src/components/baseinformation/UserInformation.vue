<template>
  <div id="UserInformation">
      <!-- 面包屑导航区 -->
 <el-breadcrumb separator="/">
  <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
  <!-- <el-breadcrumb-item><a href="/">活动管理</a></el-breadcrumb-item> -->
  <el-breadcrumb-item>基本信息管理</el-breadcrumb-item>
  <el-breadcrumb-item>个人信息维护</el-breadcrumb-item>
</el-breadcrumb>

<el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
  <el-form-item label="用户名" >
    <el-input v-model="ruleForm.account" style="width:40%;" maxlength=19></el-input>
  </el-form-item>
  <el-form-item label="手机号">
    <el-input  v-model="ruleForm.phone" style="width:40%;" disabled></el-input>
  </el-form-item>
  <el-form-item label="旧密码" prop="oldPass">
    <el-input type="password" v-model.number="ruleForm.oldPass" style="width:40%;"></el-input>
  </el-form-item>
  <el-form-item label="密码" prop="pass">
    <el-input type="password" v-model="ruleForm.pass" autocomplete="off" style="width:40%;"></el-input>
  </el-form-item>
  <el-form-item label="确认密码" prop="checkPass">
    <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off" style="width:40%;"></el-input>
  </el-form-item>
  
  <el-form-item class="buttonForm" style="width:39%;">
    <el-button type="primary" @click="submitForm('ruleForm')">修改</el-button>
  </el-form-item>
</el-form>

  </div>
</template>

<script>
export default {
  name: 'UserInformation',
  created(){
      console.log("created的内部");
      this.getUserInformation();
  },
  data() {
    
      const validatePass = (rule, value, callback) => {
          if (this.ruleForm.checkPass !== '') {
            this.$refs.ruleForm.validateField('checkPass');
          }
          callback();
        
      };
      const validatePass2 = (rule, value, callback) => {
        if (value !== this.ruleForm.pass) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };
      return {
        ruleForm: {
        account:'',
        phone:'',
        oldPass:'',
        pass: '',
        checkPass: '',
        },
        rules: {
          pass: [
            { validator: validatePass, trigger: 'blur' }
          ],
          checkPass: [
            { validator: validatePass2, trigger: 'blur' }
          ]
        }
      };
    },
     methods: {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
              const url = "/system/baseinformationadmin/updateuserinformation";
              this.$http.post(url,this.ruleForm)
                        .then((res)=>{
                            if(res.data.success){
                                if(this.ruleForm.pass===''){
                                     this.$message.success("修改成功");
                                } 
                                else{
                                
                                this.logout();
                                this.$message.success(res.data.message);
                                }
                                
                            }else {
                                this.$message.error(res.data.message);
                            }
                        }) 
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
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
    //   与Home.vue的logout是一样的
      logout(){
           window.sessionStorage.clear();
      //发送退出请求给后端
      const url = "/system/logout";
      this.$http.get(url).then(function(res){
          console.log(res);
      });
      this.$message.success("退出成功");
      this.$router.push("/login");
      }
    }
  }
</script>

<style>
    
    .el-input {
        
        float: left;
    }

    .buttonForm {
        float: left;
    }
</style>