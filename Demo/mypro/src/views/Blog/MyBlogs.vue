<template>
  <div id="blog_main">
    <div id="nav_main">
      <el-menu class="el-menu-demo" mode="horizontal" id="nav_items">
        <el-menu-item index="1" @click="WriteBlog">写博客</el-menu-item>
        <el-submenu index="2">
          <template slot="title">
            <img
              :src="logo_src"
              height="40px"
              width="40px"
              style="border-radius: 50%"
          /></template>
          <el-menu-item index="2-1" @click="ToSetting()">关于设置</el-menu-item>
          <el-menu-item index="2-2" @click="ToMain()">返回首页</el-menu-item>
          <el-menu-item index="2-3">退出登录</el-menu-item>
        </el-submenu>
      </el-menu>
    </div>
    <div class="blog" v-for="blog in BlogList" :key="blog.id">
      <el-container>
        <el-header>
          <div
            v-html="blog.blog_title"
            @click="ToDetails(blog.id)"
            style="cursor: pointer"
          ></div
        ></el-header>
        <el-main>
          <div v-html="blog.content.substring(0, 200)"></div>
        </el-main>
        <logo-items :blog_id="blog.id"></logo-items>
        <el-divider />
      </el-container>
    </div>
  </div>
</template>

<script>
import marked from "marked";
import request from "../../network/request";
import { mavonEditor } from "mavon-editor";
import "mavon-editor/dist/css/index.css";
import LogoItems from "../../components/logo_items/LogoItems.vue";
export default {
  components: {
    //注册组件
    mavonEditor,
    LogoItems,
  },
  data() {
    return {
      logo_src: "",
      token: "",
      BlogList: [],
    };
  },
  methods: {
    ToSetting() {
      this.$router.push("/setting");
    },
    WriteBlog() {
      //跳转到写博客界面
      this.$router.push("/writeblog");
    },
    ToDetails(blog_id) {
      //根据传递的参数查询后端数据库
      //console.log(blog_id);
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
    GetSelfBlogs() {
      request({
        //访问后端的blog_all的api，展示页面中的博客信息
        url: "/api/blog_all_self",
        method: "get",
      })
        .then((res) => {
          console.log(res);
          this.BlogList = [];
          var bloglist = res.data; //得到博客的列表
          for (var i = 0; i < bloglist.length; i++) {
            this.BlogList.push(bloglist[i]);
            //将内容部分的markdown转换为html
            this.BlogList[i].content = marked(this.BlogList[i].content);
          }
        })
        .catch((err) => {
          //请求失败打印失败信息
          console.log(err);
        });
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
  created() {
    this.GetSelfBlogs(); //获取自己的博客
    this.GetSelfLogo(); //获取自己的头像
  },
};
</script>

<style>
.blog {
  margin-top: 25px; /* 设置每条博客之间的间隙 */
}

.el-header {
  background-color: #eee;
  color: #333;
  text-align: left;
  line-height: 60px;
}

.el-main {
  color: #333;
  text-align: left;
}

.blog > .el-container {
  margin-left: auto;
  margin-right: auto;
  margin-bottom: 40px;
  width: 1200px;
}

#nav_main_items {
  display: flex;
  flex-direction: row;
  justify-content: flex-end;
}

#nav_items {
  display: flex;
  flex-direction: row;
  justify-content: flex-end;
}
</style>