<template>
  <div>
    <el-row>
      <el-col :span="2"><br></el-col>
      <el-col :span="10">
        <el-card style="margin-top: 60px;">
          <div :align="'center'" style="font-size: 24px">音频文件上传识别</div>
          <br>
          <el-form :model="uploadForm" ref="uploadForm" label-width="80px" :label-position="'left'">
            <el-form-item label="文件格式" prop="format">
              <el-select v-model="uploadForm.format">
                <el-option label="pcm" value="pcm"></el-option>
                <el-option label="其他" value="others"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="采样率" prop="rate" v-if="this.uploadForm.format=='pcm'">
              <el-select v-model="uploadForm.rate">
                <el-option label="16000" value="16000"></el-option>
                <el-option label="8000" value="8000"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="选择文件">
              <el-upload
                ref="upload"
                multiple
                action="http://localhost:8080/uploadFile/"
                :headers="headers"
                :before-upload="beforeUpload"
                :on-error="onError"
                :on-success="onSuccess"
                :show-file-list="false"
                style="margin-top: 10px">
                <el-button slot="trigger" size="small" type="primary">上传文件</el-button>
                <div slot="tip" class="el-upload__tip">文件大小不超过5MB</div>
              </el-upload>
              <el-tag v-for="(file, index) in uploadForm.fileList" :key="file.fileName" closable :type="''" @close="removeTag(index)"
                style="margin-right: 10px">
                {{file.fileName}}
              </el-tag>
            </el-form-item>
            <el-form-item>
              <el-row>
                <el-col :span="7"><br></el-col>
                <el-button type="primary" @click="submitForm()">识别</el-button>
              </el-row>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
    </el-row>
    <el-dialog :title="'识别结果'" :visible.sync="openDialog">
      <el-row>
        <el-col :span="2"><br></el-col>
        <el-col :span="20">
          <el-table :data="result">
            <el-table-column prop="fileName" label="文件名称" :align="'center'" width="150" show-overflow-tooltip>
            </el-table-column>
            <el-table-column prop="context" label="文件内容" :align="'center'" width="450" show-overflow-tooltip>
            </el-table-column>
          </el-table>
        </el-col>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button @click="openDialog = false">取消</el-button>
        <el-button type="primary" @click="addDocs">添加到数据库</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { recognizeFile, addDoc } from '@/api/api'

export default {
  name: 'AudioUpload',
  methods: {
    beforeUpload(file) {
      if (file.size / 1024 / 1024 > 5) {
        this.$message.error('上传文件大小不能超过 5MB!')
        return false
      }
    },
    onError(){
      this.$message.error("文件上传失败")
    },
    onSuccess(res, file){
      if(res.code != 200){
        this.$message.error("文件上传失败")
        return false
      }else{
        this.uploadForm.fileList.push({
          fileName: file.name,
          serverFileName: res.data
        })
      }
    },
    removeTag(index){
      this.uploadForm.fileList.splice(index, 1)
    },
    submitForm(){
      if(this.uploadForm.fileList.length === 0){
        this.$message.error('至少选取一个文件!')
        return false
      }
      if(this.uploadForm.format == 'pcm'){
        for(let i = 0; i < this.uploadForm.fileList.length; ++i){
          let fileName = this.uploadForm.fileList[i].fileName
          let extension = fileName.substring(fileName.lastIndexOf("."))
          if(extension != '.pcm'){
            this.$message.error('文件格式不正确!')
            return false;
          }
        }
      }else{
        for(let i = 0; i < this.uploadForm.fileList.length; ++i){
          let fileName = this.uploadForm.fileList[i].fileName
          let extension = fileName.substring(fileName.lastIndexOf("."))
          if(extension == '.pcm'){
            this.$message.error('文件格式不正确!')
            return false;
          }
        }      
      }
      recognizeFile(this.uploadForm.format, this.uploadForm.rate, this.uploadForm.fileList)
      .then((res) => {
        this.uploadForm.fileList = []
        this.$message.success('识别完成')
        this.openDialog = true
        this.result = res.data
      }).catch((error) => {
      });
    },
    addDocs(){
      addDoc(0, this.result)
      .then((res) => {
        this.$message.success('添加完成')
        this.result = []
        this.openDialog = false
      }).catch((error) => {
      });
    }
  },
  data() {
    return {
      headers: {
        token: ''
      },
      uploadForm: {
        format: 'pcm',
        rate: '16000',
        fileList: [],
      },
      openDialog: false,
      result: [],
      category_id: 0
    }
  },
  created(){
    this.headers.token = sessionStorage.getItem("tokenValue")
  }
}
</script>
