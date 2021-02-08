<!---  --->
<template>
  <div>
    <el-container>
      <el-header
        ><el-menu class="el-menu-demo" mode="horizontal" id="nav_items">
          <el-menu-item index="1" @click="WriteBlog">写博客</el-menu-item>
          <el-submenu index="2">
            <template slot="title">
              <img
                :src="logo_src"
                height="40px"
                width="40px"
                style="border-radius: 50%"
            /></template>
            <el-menu-item index="2-1" @click="ToSetting()"
              >关于设置</el-menu-item
            >
            <el-menu-item index="2-2" @click="ToMain()">返回首页</el-menu-item>
            <el-menu-item index="2-3" @click="Exit()">退出登录</el-menu-item>
          </el-submenu>
        </el-menu></el-header
      >
      <el-container>
        <el-aside width="250px" class="setting_aside">
          <el-row class="tac">
            <el-col style="width: 250px">
              <el-menu class="el-menu-vertical-demo">
                <el-menu-item index="1" @click="BaseShow">
                  <i class="el-icon-menu"></i>
                  <span slot="title">基础设置</span>
                </el-menu-item>
                <el-menu-item index="2" @click="ProfileShow">
                  <i class="el-icon-document"></i>
                  <span slot="title">个人资料</span>
                </el-menu-item>
                <el-menu-item index="3" @click="OtherShow">
                  <i class="el-icon-setting"></i>
                  <span slot="title">其他设置</span>
                </el-menu-item>
              </el-menu>
            </el-col>
          </el-row>
        </el-aside>
        <el-main class="setting_main"
          ><base-setting
            :logo_Src="logo_src"
            v-if="showSetting.isBaseSettingShow"
          ></base-setting>
          <profile-setting
            v-if="showSetting.isProfileSettingShow"
          ></profile-setting>
          <other-setting v-if="showSetting.isOtherSettingShow"></other-setting>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import request from "../../network/request";
import BaseSetting from "../../components/Setting/baseSetting.vue";
import ProfileSetting from "../../components/Setting/ProfileSetting.vue";
import OtherSetting from "../../components/Setting/OtherSetting.vue";
export default {
  components: {
    BaseSetting,
    ProfileSetting,
    OtherSetting,
  },
  data() {
    return {
      logo_src: "",
      token: "",
      BlogList: [],
      showSetting: {
        isBaseSettingShow: false,
        isProfileSettingShow: false,
        isOtherSettingShow: false,
      },
    };
  },
  methods: {
    Exit() {
      localStorage.removeItem("token"); //删掉token
      this.$router.replace("/login");
    },
    BaseShow() {
      this.showSetting.isBaseSettingShow = true;
      this.showSetting.isProfileSettingShow = false;
      this.showSetting.isOtherSettingShow = false;
    },
    ProfileShow() {
      this.showSetting.isBaseSettingShow = false;
      this.showSetting.isProfileSettingShow = true;
      this.showSetting.isOtherSettingShow = false;
    },
    OtherShow() {
      this.showSetting.isBaseSettingShow = false;
      this.showSetting.isProfileSettingShow = false;
      this.showSetting.isOtherSettingShow = true;
    },
    ToSetting() {
      this.$router.push("/setting");
    },
    WriteBlog() {
      //跳转到写博客界面
      this.$router.push("/writeblog");
    },
    ToDetails(blog_id) {
      //根据传递的参数查询后端数据库
      console.log(blog_id);
      this.$router.push({
        path: "blogdetails",
        query: {
          blog_id: blog_id,
        },
      });
    },
    ToMain() {
      this.$router.push("/main");
    },
    GetSelfLogo() {
      //获取自己的头像
      request({
        url: "/api/file/getlogo",
        responseType: "arraybuffer",
      })
        .then((res) => {
          //将后端的图片转换为base64去显示
          this.logo_src =
            "data:image/png;base64," +
            btoa(
              new Uint8Array(res.data).reduce(
                (data, byte) => data + String.fromCharCode(byte),
                ""
              )
            );
          // console.log(res);
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
  // 生命周期 - 创建完成
  created() {
    this.GetSelfLogo(); //获取自己的头像
  },

  // DOM挂载完毕
  mounted() {},
};
</script>

<style>
#nav_items {
  display: flex;
  flex-direction: row;
  justify-content: flex-end;
}

.setting_aside {
  margin-left: 250px;
  height: 678px;
}

.setting_main {
  margin-left: 0px;
  margin-right: 250px;
  height: 678px;
}
</style>