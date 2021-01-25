<template>
  <div id="profileSetting_main">
    <div class="profile_items" id="sex_item">
      <div>性别信息</div>
      <div style="width: 250px">
        <el-checkbox-group v-model="checkedSex" :min="0" :max="1">
          <el-checkbox
            v-for="sex_item in sex"
            :label="sex_item"
            :key="sex_item"
            >{{ sex_item }}</el-checkbox
          >
        </el-checkbox-group>
      </div>
    </div>
    <div class="profile_items">
      <div>个人简介</div>
      <div>
        <el-input
          style="width: 250px"
          type="textarea"
          :rows="4"
          placeholder="请输入内容"
          v-model="textarea"
        >
        </el-input>
      </div>
    </div>
    <div class="profile_items">
      <div>个人网站</div>
      <div>
        <el-input
          style="width: 250px"
          placeholder="请输入网站"
          v-model="website"
        >
        </el-input>
      </div>
    </div>
    <div class="profile_items">
      <div>个人QQ</div>
      <div>
        <el-input style="width: 250px" placeholder="请输入QQ" v-model="qq">
        </el-input>
      </div>
    </div>
    <div class="profile_items">
      <div>个人微信</div>
      <div>
        <el-input
          style="width: 250px"
          placeholder="请输入微信"
          v-model="wechat"
        >
        </el-input>
      </div>
    </div>
    <div class="profile_items">
      <div>个人微博</div>
      <div>
        <el-input
          style="width: 250px"
          placeholder="请输入微博"
          v-model="website"
        >
        </el-input>
      </div>
    </div>
    <div class="profile_items">
      <el-button type="primary">提交表单</el-button>
    </div>
  </div>
</template>

<script>
const sexOptions = ["男", "女", "保密"];
export default {
  name: "ProfileSetting",
  props: {},
  data() {
    return {
      checkedSex: [],
      sex: sexOptions,
      textarea: "",
      website: "",
      qq: "",
      wechat: "",
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
.profile_items {
  display: flex;
  flex-direction: row;
  justify-content: space-around;
}

#profileSetting_main > div {
  margin-bottom: 30px;
}
</style>