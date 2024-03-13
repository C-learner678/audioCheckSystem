<template>
  <div style="margin-left: 60px; margin-top: 30px;">
    支持以下运算符：<br>
    1.或运算符|<br>
    例：科学|科技<br>
    匹配出现“科学”、“科技”两个词之一的音频。<br>
    2.和运算符&<br>
    例：科学&科技<br>
    匹配同时出现“科学”、“科技”两个词的音频。<br>
    3.非运算符!<br>
    例：!科学<br>
    匹配没有出现“科学”一词的音频。<br>
    4.后运算符#<br>
    例：科学#发展#5<br>
    匹配“发展”在“科学”之后且两个词之间距离不超过5个字符的音频，比如“科学正在发展”。<br>
    例：科学#发展<br>
    相当于“科学#发展#10”。<br>
    5.括号运算符()<br>
    例：(科学|科技)&(发展|进步)<br>
    用于规定优先级。<br>
  </div>
</template>

<script>
export default {
  name: 'RuleDoc',
  methods: {

  },
  data() {
    return {

    }
  }
}
</script>
