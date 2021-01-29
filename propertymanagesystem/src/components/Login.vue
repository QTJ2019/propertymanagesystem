<template>
  <div class="login_container">
    <div class="login_box">
      <ul class="resp-tabs-list">
        <router-link to="/register" tag="li" class="resp-tab-item-left"
          ><h2><span>注册</span></h2></router-link
        >
        <li class="resp-tab-item-right">
          <h2><span>登录</span></h2>
        </li>
      </ul>
      <el-form
        :model="ruleForm"
        status-icon
        :rules="rules"
        ref="ruleForm"
        label-width="0px"
        class="demo-ruleForm"
      >
        <el-form-item  prop="user">
          <el-input
            v-model="ruleForm.user"
            autocomplete="off"
            placeholder="用户名"
          ></el-input>
        </el-form-item>
        <el-form-item  prop="pass">
          <el-input
            type="password"
            v-model="ruleForm.pass"
            autocomplete="off"
            placeholder="密码"
          ></el-input>
        </el-form-item>
    
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')"
            >登录</el-button
          >
          <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
      const checkAge = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('年龄不能为空'));
        }
        setTimeout(() => {
          if (!Number.isInteger(value)) {
            callback(new Error('请输入数字值'));
          } else {
            if (value < 18) {
              callback(new Error('必须年满18岁'));
            } else {
              callback();
            }
          }
        }, 1000);
      };
      const validateUser = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入用户名'));
        } else{
            callback();
        }
      };
      const validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入密码'));
        } else {
          callback();
        }
      };
      return {
        ruleForm: {
          user: '',
          pass: '',
          age: ''
        },
        rules: {
          user: [
            { validator: validateUser, trigger: 'blur' }
          ],
          pass: [
            { validator: validatePass, trigger: 'blur' }
          ],
          age: [
            { validator: checkAge, trigger: 'blur' }
          ]
        }
      };
    },
     methods: {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            const url = "/system/process?username="+this.ruleForm.user+"&password="+this.ruleForm.pass;
            this.$http.post(url)
                 .then(function(response){
                     if(response.data.success){
                       console.log(response);
                       const jsessionid = response.data.data.jsessionid;
                       window.sessionStorage.setItem('jsessionid',jsessionid);
                       this.$message.success("登录成功");
                       this.$router.push("/home");
                     } else
                     this.$message.error(response.data.data.message);
                    
                 }.bind(this))
            
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
.login_container {
  background-color: #2b4b6b;
  height: 100%;
}

.login_box {
  width: 400px;
  height: 345px;
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

.resp-tab-item-left {
  float: left;
  color: #ffffff;
  background-color: #4d6e8f;
  font-size: 1em;
  cursor: pointer;
  margin: 0;
  outline: none;
  transition: all 0.3s ease-out;
  width: 50%;
  padding: 0;
}

.resp-tab-item-right {
  float: right;
  color: #4d6e8f;
  background-color: #ffffff;
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