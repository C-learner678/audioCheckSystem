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
            <el-menu-item index="2">
              <i class="el-icon-menu"></i>
              <span slot="title">规则说明</span>
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
            <el-menu-item index="2">
              <i class="el-icon-menu"></i>
              <span slot="title">文件管理</span>
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
        <div v-if="index=='1' && leftIndex=='1'">
          <rule-table></rule-table>
        </div>
        <div v-else-if="index=='1' && leftIndex=='2'">
          <rule-doc></rule-doc>
        </div>
        <div v-else-if="index=='2' && leftIndex=='1'">
          <audio-upload></audio-upload>
        </div>
        <div v-else-if="index=='2' && leftIndex=='2'">
          <audio-table></audio-table>
        </div>
        <div v-else-if="index=='3' && leftIndex=='1'">
          <check-audio></check-audio>
        </div>
        <div v-else-if="index=='4' && leftIndex=='1'">
          <modify-password></modify-password>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import RuleTable from './RuleTable.vue';
import RuleDoc from './RuleDoc.vue'
import AudioUpload from './AudioUpload.vue'
import AudioTable from './AudioTable.vue';
import ModifyPassword from './ModifyPassword.vue'; 
import CheckAudio from './CheckAudio.vue'

export default {
  name: 'LeftMenu',
  components: {
    RuleTable,
    RuleDoc,
    AudioUpload,
    ModifyPassword,
    AudioTable,
    CheckAudio
  },
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
