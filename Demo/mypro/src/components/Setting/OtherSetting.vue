<template>
  <div id="otherSetting_main">
    <div class="other_items">
      <div><div>账号名称</div></div>
      <div style="width:250px">Wanna</div>
    </div>
    <div class="other_items">
      <div><div>密码设置</div></div>
      <div>
        <el-input
          style="width: 250px"
          placeholder="请输入密码"
          v-model="password"
        ></el-input>
      </div>
    </div>
    <div class="other_items">
      <el-button type="primary">提交表单</el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: "OtherSetting",
  props: {},
  data() {
    return {
      password: "",
    };
  },
  methods: {},
  // 生命周期 - 创建完成
  created() {},

  // DOM挂载完毕
  mounted() {},
};
</script>

<style>
#otherSetting_main > div {
  margin-bottom: 30px;
}

.other_items {
  display: flex;
  flex-direction: row;
  justify-content: space-around;
}
</style>