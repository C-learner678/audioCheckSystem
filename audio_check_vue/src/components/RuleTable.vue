<template>
  <div>
    <br>
    <el-row>
      <el-col :span="2"><br></el-col>
      <el-col :span="20">
        <div align="right">
          <el-button type="primary" align="right" @click="createFormVisible = true">新增</el-button>
        </div>
        <el-table :data="ruleData"
          style="width: 100%">
          <el-table-column prop="name" label="规则名称" align="center" width="160">
          </el-table-column>
          <el-table-column prop="context" label="规则内容" align="center" width="320">
          </el-table-column>
          <el-table-column prop="description" label="规则描述" align="center" width="320">
          </el-table-column>
          <el-table-column label="操作" align="center" width="180">
            <el-button type="text" size="small">查看</el-button>
            <el-button type="text" size="small">编辑</el-button>
            <el-button type="text" size="small">删除</el-button>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
    <el-dialog title="新增匹配规则" :visible.sync="createFormVisible">
      <el-form :label-position="'left'" label-width="80px" :model="createRuleForm" :rules="rules" ref="createRuleForm">
        <el-form-item label="规则名称" prop="name">
          <el-input v-model="createRuleForm.name"></el-input>
        </el-form-item>
        <el-form-item label="规则内容" type="textarea" prop="context">
          <el-input type="textarea" v-model="createRuleForm.context"></el-input>
        </el-form-item>
        <el-form-item label="规则描述" prop="description">
          <el-input type="textarea" v-model="createRuleForm.description"></el-input>
        </el-form-item>
        <el-form-item>
          <el-col :span="16">
            <br>
          </el-col>
          <el-col :span="4">
            <el-button @click="createFormVisible = false">取消</el-button>
          </el-col>
          <el-col :span="4">
            <el-button type="primary" @click="submitCreateForm()">确定</el-button>
          </el-col>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import Vue from 'vue'
import {getRuleList, createRule} from "@/api/api"

export default {
  name: 'RuleTable',
  methods: {
    submitCreateForm(){
      this.$refs.createRuleForm.validate((valid) => {
        if (valid) {
          createRule(this.createRuleForm.name, this.createRuleForm.context, this.createRuleForm.description)
          .then((res) => {
            this.createFormVisible = false
            Vue.prototype.$message({
              message: '新增匹配规则成功',
              type: 'success'
            });
            getRuleList()
            .then((res) => {
              this.ruleData = res.data
            }).catch((error) => {
            });
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
      ruleData: [],
      createFormVisible: false,
      createRuleForm: {
        name: '',
        context: '',
        description: ''
      },
      rules: {
        name: [
          { required: true, message: '请输入匹配名称' },
          { max: 100, message: '长度不超过100个字符' }
        ],
        context: [
          { required: true, message: '请输入匹配内容' },
          { max: 500, message: '长度不超过500个字符' }
        ],
        description: [
          { max: 500, message: '长度不超过500个字符' }
        ]
      }
    }
  },
  created(){
    getRuleList()
    .then((res) => {
      this.ruleData = res.data
    }).catch((error) => {
    });
  }
}
</script>

<style scoped>

</style>
