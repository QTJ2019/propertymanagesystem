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
  <el-form-item label="账号" >
    <el-input v-model="ruleForm.account" style="width:40%;" maxlength=19></el-input>
  </el-form-item>
  <el-form-item label="手机号">
    <el-input  v-model="ruleForm.phone" style="width:40%;" disabled></el-input>
  </el-form-item>
  <el-form-item label="旧密码" prop="oldPass">
    <el-input v-model.number="ruleForm.oldPass" style="width:40%;"></el-input>
  </el-form-item>
  <el-form-item label="密码" prop="pass">
    <el-input type="password" v-model="ruleForm.pass" autocomplete="off" style="width:40%;"></el-input>
  </el-form-item>
  <el-form-item label="确认密码" prop="checkPass">
    <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off" style="width:40%;"></el-input>
  </el-form-item>
  
  <el-form-item class="buttonForm" style="width:39%;">
    <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
  </el-form-item>
</el-form>

  </div>
</template>

<script>

export default {
  name: 'UserInformation',
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
        phone:'15521072883',
        oldPass:'123',
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
            alert('submit!');
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
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