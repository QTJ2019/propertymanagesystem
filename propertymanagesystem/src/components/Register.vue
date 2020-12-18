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
          prop="email"
          
          :rules="[
            { required: true, message: '请输入邮箱地址', trigger: 'blur' },
            {
              type: 'email',
              message: '请输入正确的邮箱地址',
              trigger: ['blur', 'change'],
            },
          ]"
        >
          <el-input 
          autocomplete="off"
          v-model="ruleForm.email"
          placeholder="邮箱" >
          </el-input>
        </el-form-item>

        <el-form-item >
          <el-input 
          v-model="ruleForm.code"
          autocomplete="off"
          placeholder="验证码"></el-input>
        </el-form-item>
        <el-form-item  prop="account">
          <el-input 
          v-model="ruleForm.account"
          autocomplete="off"
          placeholder="账号"></el-input>
        </el-form-item>
        <el-form-item  prop="pass">
          <el-input
            type="password"
            v-model="ruleForm.pass"
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
  name: "Register",
  data() {
    const checkAccount = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("账号不能为空"));
      }

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
    const validatePass = (rule, value, callback) => {
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
      } else if (value !== this.ruleForm.pass) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      ruleForm: {
        email: "",
        code: "",
        pass: "",
        checkPass: "",
        account: "",
      },
      rules: {
        pass: [{ validator: validatePass, trigger: "blur" }],
        checkPass: [{ validator: validatePass2, trigger: "blur" }],
        account: [{ validator: checkAccount, trigger: "blur" }],
      },
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        console.log(this.ruleForm.code);
        console.log(this.ruleForm.email);
        if (valid) {
          alert("submit!");
        } else {
          console.log("error submit!!");
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