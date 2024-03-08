<template>
  <div class="home">
    <el-button @click="doclick">默认按钮</el-button>
    {{ a }}
  </div>
</template>

<script>
import {hello} from "@/api/api"

export default {
  name: 'HelloView',
  components: {
    
  },
  methods: {
    async doclick () {
      const res = await hello()
      this.a = res.data
    }
  },
  data() {
    return {
      a: 1
    }
  }
}
</script>