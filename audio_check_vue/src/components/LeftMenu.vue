<template>
  <div>
    <el-row>
      <el-col :span="4">
        <div v-if="index=='1'">
          <el-menu default-active="1" @select="handleSelect" 
            background-color="#545c64" text-color="#fff" active-text-color="#ffd04b">
            <el-menu-item index="1">
              <i class="el-icon-menu"></i>
              <span slot="title">规则设置</span>
            </el-menu-item>
          </el-menu>
        </div>
        <div v-else-if="index=='2'">
          <el-menu default-active="1" @select="handleSelect" 
            background-color="#545c64" text-color="#fff" active-text-color="#ffd04b">
            <el-menu-item index="1">
              <i class="el-icon-menu"></i>
              <span slot="title">文件上传</span>
            </el-menu-item>
          </el-menu>
        </div>
        <div v-else-if="index=='3'">
          <el-menu default-active="1" @select="handleSelect" 
            background-color="#545c64" text-color="#fff" active-text-color="#ffd04b">
            <el-menu-item index="1">
              <i class="el-icon-menu"></i>
              <span slot="title">音频检测</span>
            </el-menu-item>
          </el-menu>
        </div>
        <div v-else-if="index=='4'">
          <el-menu default-active="1" @select="handleSelect" 
            background-color="#545c64" text-color="#fff" active-text-color="#ffd04b">
            <el-menu-item index="1">
              <i class="el-icon-menu"></i>
              <span slot="title">修改密码</span>
            </el-menu-item>
          </el-menu>
        </div>
      </el-col>
      <el-col :span="20">
        <div v-if="index=='4' && leftIndex=='1'">
          123
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: 'LeftMenu',
  methods: {
    handleSelect(key, keyPath) {
      this.leftIndex = key
    },
  },
  props: ['index'],
  data() {
    return {
      leftIndex: '1'
    }
  }
}
</script>

<style scoped>

</style>
