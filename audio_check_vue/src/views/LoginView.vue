<template>
  <div>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <el-row>
      <el-col :span="9">
        <br>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div slot="header" style="text-align: center; font-size: 24px">
            音频质量检测系统
          </div>
          <el-form :label-position="'left'" label-width="80px" :model="loginForm" :rules="rules" ref="loginForm">
            <el-form-item label="用户名" prop="name">
              <el-input v-model="loginForm.name"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input v-model="loginForm.password" show-password></el-input>
            </el-form-item>
            <el-form-item>
              <el-col :span="2">
                <br>
              </el-col>
              <el-col :span="10">
                <el-button @click="register()">注册</el-button>
              </el-col>
              <el-col :span="12">
                <el-button type="primary" @click="submitForm()">登录</el-button>
              </el-col>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import Cookies from "js-cookie";
import {login} from "@/api/api"

export default {
  name: 'LoginView',
  methods: {
   submitForm() {
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          login(this.loginForm.name, this.$md5(this.loginForm.password))
          .then((res) => {
            Cookies.set("username", this.loginForm.name, { expires: 30 });
            sessionStorage.setItem('tokenValue', res.data);
            this.$router.push("/main")
          }).catch((error) => {
          });
        } else {
          return false;
        }
      });
    },
    register() {
      this.$router.push("/register")
    }
  },
  data() {
    return {
      loginForm: {
        name: '',
        password: '',
      },
      rules: {
        name: [
          { required: true, message: '请输入用户名' },
        ],
        password: [
          { required: true, message: '请输入密码' },
        ]
      }
    }
  },
  created() {
    if(sessionStorage.getItem("tokenValue")){
      this.$router.push("/main")
    }
    this.loginForm.name = Cookies.get("username");
  }
}
</script>

<style scoped>

</style>
