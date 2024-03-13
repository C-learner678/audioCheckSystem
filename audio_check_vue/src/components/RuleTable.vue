<template>
  <div>
    <br>
    <el-row>
      <el-col :span="1"><br></el-col>
      <el-col :span="22">
        <div>
          <el-row>
            <el-col :span="2">
              <div style="margin-left: 12px; margin-top: 8px">
                规则名称
              </div>
            </el-col>
            <el-col :span="4">
              <el-input v-model="searchName1"></el-input>
            </el-col>
            <el-col :span="16">
              <el-button type="primary" @click="search" style="margin-left: 20px;">搜索</el-button>
            </el-col>
            <el-col :span="2">
              <el-button type="primary" @click="createFormVisible = true" :align="'right'">新增</el-button>
            </el-col>
          </el-row>
        </div>
        <el-table :data="ruleData" style="width: 100%">
          <el-table-column prop="name" label="规则名称" :align="'center'" width="100" show-overflow-tooltip>
          </el-table-column>
          <el-table-column prop="context" label="规则内容" :align="'center'" width="450" show-overflow-tooltip>
          </el-table-column>
          <el-table-column prop="description" label="规则描述" :align="'center'" width="450" show-overflow-tooltip>
          </el-table-column>
          <el-table-column label="操作" :align="'center'" width="100">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="editRuleClick(scope.row)">编辑</el-button>
              <el-button type="text" size="small" @click="deleteRuleClick(scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination layout="prev, pager, next" :total="totalNum" :page-size="pageSize" @current-change="pageChange" :align="'center'">
        </el-pagination>
      </el-col>
    </el-row>
    <!-- 新增表单（点击按钮时显示） -->
    <el-dialog title="新增匹配规则" :visible.sync="createFormVisible">
      <el-form :label-position="'left'" label-width="80px" :model="createRuleForm" :rules="createRules" ref="createRuleForm">
        <el-form-item label="规则名称" prop="name">
          <el-input v-model="createRuleForm.name"></el-input>
        </el-form-item>
        <el-form-item label="规则内容" type="textarea" prop="context">
          <el-input type="textarea" v-model="createRuleForm.context" :rows="'3'"></el-input>
        </el-form-item>
        <el-form-item label="规则描述" prop="description">
          <el-input type="textarea" v-model="createRuleForm.description" :rows="'3'"></el-input>
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
    <!-- 编辑表单（点击按钮时显示） -->
    <el-dialog title="修改匹配规则" :visible.sync="editFormVisible">
      <el-form :label-position="'left'" label-width="80px" :model="editRuleForm" :rules="editRules" ref="editRuleForm">
        <el-form-item label="规则名称" prop="name">
          <el-input v-model="editRuleForm.name"></el-input>
        </el-form-item>
        <el-form-item label="规则内容" type="textarea" prop="context">
          <el-input type="textarea" v-model="editRuleForm.context" :rows="'3'"></el-input>
        </el-form-item>
        <el-form-item label="规则描述" prop="description">
          <el-input type="textarea" v-model="editRuleForm.description" :rows="'3'"></el-input>
        </el-form-item>
        <el-form-item>
          <el-col :span="16">
            <br>
          </el-col>
          <el-col :span="4">
            <el-button @click="editFormVisible = false">取消</el-button>
          </el-col>
          <el-col :span="4">
            <el-button type="primary" @click="submitEditForm()">确定</el-button>
          </el-col>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import Vue from 'vue'
import {getRuleList, createRule, editRule, deleteRule} from "@/api/api"

export default {
  name: 'RuleTable',
  methods: {
    //查询
    getRuleListFunc(){
      getRuleList(this.searchName, this.currentPage, this.pageSize)
      .then((res) => {
        this.ruleData = res.data.records
        this.totalNum = res.data.total
      }).catch((error) => {
      });
    },
    //点击搜索按钮时，修改searchName并查询
    search(){
      this.searchName = this.searchName1
      this.getRuleListFunc()
    },
    //换页时，查询
    pageChange(current){
      this.currentPage = current
      this.getRuleListFunc()
    },
    //提交新增表单
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
            this.getRuleListFunc()
          }).catch((error) => {
          });
        } else {
          return false;
        }
      });
    },
    //删除
    deleteRuleClick(row){
      Vue.prototype.$confirm('确定要删除这条规则吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteRule(row.id).
        then((res) => {
          Vue.prototype.$message({
            type: 'success',
            message: '删除匹配规则成功!'
          });
          this.getRuleListFunc();
        }).catch((error) => {
        });
      }).catch(() => {
        Vue.prototype.$message({
          type: 'info',
          message: '已取消删除'
        });          
      });
    },
    //打开编辑表单
    editRuleClick(row){
      this.editFormVisible = true
      this.editRuleForm.id = row.id
      this.editRuleForm.name = row.name
      this.editRuleForm.context = row.context
      this.editRuleForm.description = row.description
    },
    //提交编辑表单
    submitEditForm(){
      this.$refs.editRuleForm.validate((valid) => {
        if (valid) {
          editRule(this.editRuleForm.id, this.editRuleForm.name, this.editRuleForm.context, this.editRuleForm.description)
          .then((res) => {
            this.editFormVisible = false
            Vue.prototype.$message({
              message: '修改匹配规则成功',
              type: 'success'
            });
            this.getRuleListFunc()
          }).catch((error) => {
          });
        } else {
          return false;
        }
      });
    }
  },
  data() {
    return {
      ruleData: [],
      pageSize: 8,
      totalNum: 1,
      currentPage: 1,
      searchName: '',
      searchName1: '',
      createFormVisible: false,
      createRuleForm: {
        name: '',
        context: '',
        description: ''
      },
      createRules: {
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
      },
      editFormVisible: false,
      editRuleForm: {
        id: 0,
        name: '',
        context: '',
        description: ''
      },
      editRules: {
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
      },
    }
  },
  created(){
    //启动时，查询
    this.getRuleListFunc()
  }
}
</script>
