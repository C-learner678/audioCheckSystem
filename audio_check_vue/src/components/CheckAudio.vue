<template>
  <div>
    <div style="margin-left: 60px; margin-top: 30px;">
      <el-button type="primary" @click="chooseRuleVisible = true">选择匹配规则</el-button><br><br>
      当前规则：{{ ruleContext }}<br><br>
      <el-button type="primary" @click="matchRule">搜索匹配规则</el-button><br><br>
      <el-table :data="matchResult" style="width: 100%">
        <el-table-column prop="name" label="文件名" :align="'center'" width="150" show-overflow-tooltip>
        </el-table-column>
        <el-table-column prop="subPatterns" label="成功匹配的子规则" :align="'center'" width="350" show-overflow-tooltip>
        </el-table-column>
        <el-table-column label="操作" :align="'center'" width="100">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="showInfo(scope.row)">查看详情</el-button>
            </template>
          </el-table-column>
      </el-table>
    </div>
    <!-- 选择规则（点击按钮时显示） -->
    <el-dialog title="选择规则" :visible.sync="chooseRuleVisible">
      <div>
        <el-row>
          <el-col :span="3">
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
        </el-row>
      </div>
      <el-table :data="ruleData" style="width: 100%">
        <el-table-column prop="name" label="规则名称" :align="'center'" width="100" show-overflow-tooltip>
        </el-table-column>
        <el-table-column prop="context" label="规则内容" :align="'center'" width="250" show-overflow-tooltip>
        </el-table-column>
        <el-table-column prop="description" label="规则描述" :align="'center'" width="250" show-overflow-tooltip>
        </el-table-column>
        <el-table-column label="操作" :align="'center'" width="100">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="editRuleClick(scope.row)">选择</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination layout="prev, pager, next" :total="totalNum" :page-size="pageSize" @current-change="pageChange" :align="'center'">
      </el-pagination>
    </el-dialog>
    <!-- 查看详情点击按钮时显示） -->
    <el-dialog title="匹配详情" :visible.sync="InfoVisible">
      <el-table :data="matchResultInfo" style="width: 100%">
        <el-table-column prop="subPattern" label="子规则" :align="'center'" width="200" show-overflow-tooltip>
        </el-table-column>
        <el-table-column prop="text" label="匹配内容" :align="'center'" width="500" show-overflow-tooltip>
          <template slot-scope="scope">
            <div v-html='scope.row.text'></div>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
import {getRuleList, match} from "@/api/api"

export default {
  name: 'CheckAudio',
  methods: {
    //查询规则
    getRuleListFunc(){
      getRuleList(this.searchName, this.currentPage, this.pageSize)
      .then((res) => {
        this.ruleData = res.data.records
        this.totalNum = res.data.total
      }).catch((error) => {
      });
    },
    //点击搜索按钮时，修改searchName并查询规则
    search(){
      this.searchName = this.searchName1
      this.getRuleListFunc()
    },
    //换页时，查询规则
    pageChange(current){
      this.currentPage = current
      this.getRuleListFunc()
    },
    //选择规则
    editRuleClick(row){
      this.ruleContext = row.context
      this.chooseRuleVisible = false
    },
    //搜索
    matchRule(){
      if(this.ruleContext == ''){
        this.$message.error("质检规则不能为空！")
        return false;
      }
      match(this.ruleContext)
      .then((res) => {
        this.matchResult = res.data.list
      }).catch((error) => {
      })
    },
    //查看详情
    showInfo(row){
      this.matchResultInfo = row.matchData
      this.InfoVisible = true
    }
  },
  data() {
    return {
      chooseRuleVisible: false,
      ruleData: [],
      pageSize: 6,
      totalNum: 1,
      currentPage: 1,
      searchName: '',
      searchName1: '',
      ruleContext: '',
      matchResult: [],
      InfoVisible: false,
      matchResultInfo: []
    }
  },
  created(){
    //启动时，查询
    this.getRuleListFunc()
  }
}
</script>
