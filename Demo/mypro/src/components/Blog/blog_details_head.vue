<!---  --->
<style lang = '' scoped>
</style>

<template>
  <div>
    <div>
      <h1>{{ blog.blog_title }}</h1>
    </div>
    <el-divider />
    <div id="nav_blog">
      <div id="nav_title">
        <div>用户: {{ blog.username }}</div>
        <div>发布日期：{{ blog.publishTime.substring(0, 10) }}</div>
      </div>
      <div id="nav_btn">
        <div v-show="canBeEdited" class="nav_btn_self">
          <el-button @click="ToDelete">删除</el-button>
          <el-button @click="ToEdit">编辑</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {};
  },
  methods: {},
  // 生命周期 - 创建完成
  created() {},

  // DOM挂载完毕
  mounted() {},
};
</script>
