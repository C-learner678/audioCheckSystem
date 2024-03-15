<template>
  <div>
    <br>
    <el-row>
      <el-col :span="1"><br></el-col>
      <el-col :span="22">
        <el-table :data="audioData" style="width: 100%">
          <el-table-column prop="name" label="文件名" :align="'center'" width="150" show-overflow-tooltip>
          </el-table-column>
          <el-table-column prop="context" label="音频内容" :align="'center'" width="650" show-overflow-tooltip>
          </el-table-column>
          <el-table-column prop="uploadTime" label="上传时间" :align="'center'" width="200" type="date" show-overflow-tooltip>
          </el-table-column>
          <el-table-column label="操作" :align="'center'" width="150">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="showInfo(scope.row)">查看详情</el-button>
              <el-button type="text" size="small" @click="deleteClick(scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
    <!-- 查看详情（点击按钮时显示） -->
    <el-dialog title="音频内容" :visible.sync="infoVisible">
      {{ context }}
    </el-dialog>
  </div>
</template>

<script>
import {searchDoc, deleteDoc} from "@/api/api"

export default {
  name: 'AudioTable',
  methods: {
    //查询
    getAudioListFunc(){
      searchDoc()
      .then((res) => {
        this.audioData = res.data.list
      }).catch((error) => {
      });
    },
    //查看详情
    showInfo(row){
      this.infoVisible = true
      this.context = row.context
    },
    //删除
    deleteClick(row){
      deleteDoc(row.id)
      .then((res) => {
        this.$message({
          type: 'success',
          message: '删除文件成功！'
        })
        this.getAudioListFunc();
      }).catch((error) => {
      });
    }
  },
  data() {
    return {
      audioData: [],
      infoVisible: false,
      context: ''
    }
  },
  created(){
    this.getAudioListFunc()
  }
}
</script>
