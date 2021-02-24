<template>
  <div class="register_container">
    <div class="register_box">
      <ul class="resp-tabs-list">
        <li class="register-item-left">
          <h2><span>注册</span></h2>
        </li>
        <router-link to="/login" tag="li" class="register-item-right"
          ><h2><span>登录</span></h2></router-link
        >
      </ul>

      <el-form
        :model="ruleForm"
        status-icon
        :rules="rules"
        ref="ruleForm"
        label-width="0px"
        class="demo-ruleForm"
      >
        <el-form-item
          prop="phone"
          
          
        >
          <el-input 
          autocomplete="off"
          v-model="ruleForm.phone"
          placeholder="手机号" >
          </el-input>
        </el-form-item>

        <!-- <el-form-item >
          <el-input 
          v-model="ruleForm.code"
          autocomplete="off"
          placeholder="验证码"></el-input>
        </el-form-item> -->
        <el-form-item  prop="account">
          <el-input 
          v-model="ruleForm.account"
          autocomplete="off"
          placeholder="账号"></el-input>
        </el-form-item>
        <el-form-item  prop="password">
          <el-input
            type="password"
            v-model="ruleForm.password"
            autocomplete="off"
            placeholder="密码"
          ></el-input>
        </el-form-item>
        <el-form-item  prop="checkPass">
          <el-input
            type="password"
            v-model="ruleForm.checkPass"
            autocomplete="off"
            placeholder="确认密码"
          ></el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')"
            >提交</el-button
          >
          <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  // name后面配置的是文件的名称
  name: "Register" ,
  data() {
    const checkAccount = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("账号不能为空"));
      }
      callback();
      // setTimeout(() => {
      //   if (!Number.isInteger(value)) {
      //     callback(new Error('请输入数字值'));
      //   } else {
      //     if (value < 18) {
      //       callback(new Error('必须年满18岁'));
      //     } else {
      //       callback();
      //     }
      //   }
      // }, 1000);
    };
    const validatePhone = (rule,value,callback) => {
      if(value === ""){
        callback(new Error("请输入手机号"));
      }else if(this.ruleForm.phone.length != 11) {
        callback(new Error("请输入正确的手机号"));
      } else {
        callback();
      }
    };
    const validatePassword = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else {
        if (this.ruleForm.checkPass !== "") {
          this.$refs.ruleForm.validateField("checkPass");
        }
        callback();
      }
    };
    const validatePass2 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.ruleForm.password) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      ruleForm: {
        phone: "",
       // code: "",
        password: "",
        checkPass: "",
        account: "",
      },
      rules: {
        phone:[{validator:validatePhone,trigger:"blur"}],
        password: [{ validator: validatePassword, trigger: "blur" }],
        checkPass: [{ validator: validatePass2, trigger: "blur" }],
       account: [{ validator: checkAccount, trigger: "blur" }],
      },
    };
  },
  methods: {
    submitForm(formName) {
      console.log("niiao");
      this.$refs[formName].validate((valid) => {
        console.log(valid);
        if (valid) {
          console.log("password:"+this.ruleForm.password);
          console.log("checkPass:"+ this.ruleForm.checkPass);
          const publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCIiVEXKFYF+/0OAMcvHTiQBGDo7mRiafYpM0wKpmoFUHs3U+rWsfvg+kfsFs+LV1hVOo8fIGxT5cc4/ufB7nnteRnckWCZh0hilt16HDl7r6PYdq2N2WKol/gj0Jgm93E7DybTEzuOQGMQlbg5jLhsI9gwj22lv2kIv7WGvUL9JwIDAQAB";
         this.ruleForm.password = this.$getRsaCode(this.ruleForm.password,publicKey);
         this.ruleForm.checkPass = "";
         const url = "/system/register";
         this.$http.post(url,this.ruleForm)
                   .then(res =>{
                     if(res.data.success){
                       this.$message.success("注册成功");
                       this.$router.push("/login");
                     }else{
                       this.$message.error(res.data.message);
                     }
                   })
         
        } else {
          this.$message.error("注册失败");
    
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
  },
};
</script>

<style>
.register_container {
  background-color: #2b4b6b;
  height: 100%;
}

.register_box {
  width: 400px;
  height: 500px;
  border-radius: 8px;
  background: #ffffff;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}

ul {
  margin: 0px;
  padding: 0px;
  overflow: hidden;
}
.resp-tabs-list {
  list-style: none;
  text-align: center;
}

.register-item-left {
  float: left;
  color: #4d6e8f;
  background: #ffffff;
}

.register-item-right {
  float: right;
  color: #ffffff;
  background-color: #4d6e8f;
}

li {
  font-size: 1em;
  cursor: pointer;
  margin: 0;
  outline: none;
  transition: all 0.3s ease-out;
  width: 50%;
  padding: 0;
}

.demo-ruleForm {
    margin: 50px 0 ;
    
}

.demo-ruleForm .el-form-item {
    padding-left: 30px;
    width: 85%;
}
</style>