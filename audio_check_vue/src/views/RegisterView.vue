<template>
  <div style="margin-top: 200px;">
    <el-row>
      <el-col :span="9">
        <br>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div slot="header" style="text-align: center; font-size: 24px">
            注册
          </div>
          <el-form :label-position="'left'" label-width="80px" :model="registerForm" :rules="rules" ref="registerForm">
            <el-form-item label="用户名" prop="name">
              <el-input v-model="registerForm.name"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input v-model="registerForm.password" show-password></el-input>
            </el-form-item>
            <el-form-item label="确认密码" prop="password2">
              <el-input v-model="registerForm.password2" show-password></el-input>
            </el-form-item>
            <el-form-item>
              <el-col :span="6">
                <br>
              </el-col>
              <el-col :span="9">
                <el-button type="primary" @click="submitForm()">注册</el-button>
              </el-col>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import Vue from 'vue'
import {register} from "@/api/api"

export default {
  name: 'RegisterView',
  methods: {
   submitForm() {
      this.$refs.registerForm.validate((valid) => {
        if (valid) {
          if(this.registerForm.password != this.registerForm.password2){
            Vue.prototype.$message.error('两次输入的密码不一致！')
            return false;
          }
          register(this.registerForm.name, this.$md5(this.registerForm.password))
          .then((res) => {
            Vue.prototype.$message({
              message: '注册成功，请登录',
              type: 'success'
            });
            this.$router.push("/login")
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
      registerForm: {
        name: '',
        password: '',
        password2: '',
      },
      rules: {
        name: [
          { required: true, message: '请输入用户名' },
          { max: 50, message: '长度不超过50个字符' }
        ],
        password: [
          { required: true, message: '请输入密码' },
          { min: 6, max: 30, message: '长度在6到30个字符之间' }
        ],
        password2: [
          { required: true, message: '请再次输入密码' },
        ]
      }
    }
  },
}
</script>

