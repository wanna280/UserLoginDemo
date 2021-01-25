<template>
  <div id="baseSetting_main">
    <div class="logo">
      <div>
        <el-avatar :size="100" :src="logo_src" shape="circle"></el-avatar>
      </div>
      <div>
        <el-button type="primary" @click="ToUploadLogo">上传头像</el-button>
      </div>
    </div>
    <div class="base_items">
      <div>昵称</div>
      <div style="width: 400px">
        <el-input v-model="nickName" placeholder="请输入昵称"></el-input>
      </div>
    </div>
    <div class="base_items">
      <div>邮箱</div>
      <div style="width: 400px">
        <el-input v-model="mail" placeholder="请输入邮箱"></el-input>
      </div>
    </div>
    <div class="base_items">
      <el-button type="primary">提交表单</el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: "baseSetting",
  props: {
    logo_Src: String,
  },
  data() {
    return {
      logo_src: this.$props.logo_Src,
      nickName: "",
      mail: "",
    };
  },
  methods: {
    ToUploadLogo() {
      this.$router.push("/upload");
    },
  },
  // 生命周期 - 创建完成
  created() {},

  // DOM挂载完毕
  mounted() {},
};
</script>


<style>
#baseSetting_main > div {
  margin-bottom: 30px;
}

.logo {
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  line-height: 100px;
}

.base_items {
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  line-height: 40px;
}
</style>