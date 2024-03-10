<template>
  <div>
    <div>
      <el-menu :default-active="activeIndex" mode="horizontal" @select="handleSelect" 
        background-color="#545c64" text-color="#fff" active-text-color="#ffd04b">
        <el-menu-item index="1">质检规则</el-menu-item>
        <el-menu-item index="2">音频上传</el-menu-item>
        <el-menu-item index="3">质量检测</el-menu-item>
        <el-menu-item index="4">用户管理</el-menu-item>
        <div style="text-align: right; font-size: 15px; margin-top: 12px; color: #ffffff">
          当前用户： {{ userName }}&ensp;&ensp;&ensp;
          <el-button type="text" @click="logout1">登出</el-button>&ensp;&ensp;&ensp;&ensp;
        </div>
      </el-menu>
    </div>
    <div>
      <left-menu :index="activeIndex"></left-menu>
    </div>
  </div>
</template>

<script>
import LeftMenu from '@/components/LeftMenu.vue';
import { checkLogin, logout } from '@/api/api';

export default {
  name: 'MainView',
  components: {
    LeftMenu
  },
  methods: {
    handleSelect(key, keyPath) {
      this.activeIndex = key
    },
    logout1() {
      logout().then((res) => {
        sessionStorage.clear()
        this.$router.push("/login")
      }).catch((error) => {
        sessionStorage.clear()
        this.$router.push("/login")
      });
    }
  },
  data() {
    return {
      userName: '',
      activeIndex: '1'
    }
  },
  created() {
    if(!sessionStorage.getItem("tokenValue")){
      this.$router.push("/login")
    }else{
      checkLogin()
      .then((res) => {
        this.userName = res.data
      }).catch((error) => {
        sessionStorage.clear()
        this.$router.push("/login")
      });
    }
  }
}
</script>

<style scoped>

</style>
