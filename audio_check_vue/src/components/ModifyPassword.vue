<template>
  <div style="margin-top: 160px;">
    <el-row>
      <el-col :span="8">
        <br>
      </el-col>
      <el-col :span="7">
        <el-card>
          <el-form :label-position="'left'" label-width="100px" :model="modifyForm" :rules="rules" ref="modifyForm">
            <el-form-item label="旧密码" prop="oldPassword">
              <el-input v-model="modifyForm.oldPassword" show-password></el-input>
            </el-form-item>
            <el-form-item label="新密码" prop="newPassword">
              <el-input v-model="modifyForm.newPassword" show-password></el-input>
            </el-form-item>
            <el-form-item label="确认新密码" prop="newPassword2">
              <el-input v-model="modifyForm.newPassword2" show-password></el-input>
            </el-form-item>
            <el-form-item>
              <el-col :span="6">
                <br>
              </el-col>
              <el-col :span="9">
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
import Vue from 'vue'
import {modifyPassword} from "@/api/api"

export default {
  name: 'ModifyPassword',
  methods: {
    submitForm() {
      this.$refs.modifyForm.validate((valid) => {
        if (valid) {
          if(this.modifyForm.newPassword != this.modifyForm.newPassword2){
            Vue.prototype.$message.error('两次输入的密码不一致！')
            return false;
          }
          modifyPassword(this.$md5(this.modifyForm.oldPassword), this.$md5(this.modifyForm.newPassword))
          .then((res) => {
            sessionStorage.clear()
            Vue.prototype.$message({
              message: '修改成功，请重新登录',
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
  },
  data() {
    return {
      modifyForm: {
        oldPassword: '',
        newPassword: '',
        newPassword2: ''
      },
      rules: {
        oldPassword: [
          { required: true, message: '请输入旧密码' },
        ],
        newPassword: [
          { required: true, message: '请输入新密码' },
        ],
        newPassword2: [
          { required: true, message: '请再次输入新密码' },
        ]
      }
    }
  }
}
</script>

