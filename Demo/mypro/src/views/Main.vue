<template>
  <div id="blog_main">
    <div id="nav_main">
      <div id="nav_main_items">
        <div id="user_logo">
          <el-button @click="GetSelfBlog">Self</el-button>
        </div>
        <div id="btn">
          <el-button type="primary" @click="WriteBlog">写博客</el-button>
        </div>
      </div>
    </div>

    <el-divider />
    <div class="blog" v-for="blog in BlogList" :key="blog.id">
      <el-container>
        <el-header>
          <div
            v-html="blog.blog_title"
            @click="ToDetails(blog.id)"
            style="cursor: pointer"
          ></div
        ></el-header>
        <el-main><div v-html="blog.content"></div></el-main>
      </el-container>
      <el-divider />
    </div>
  </div>
</template>

<script>
import marked from "marked";
import request from "../network/request";
import { mavonEditor } from "mavon-editor";
import "mavon-editor/dist/css/index.css";
export default {
  components: {
    //注册组件
    mavonEditor,
  },
  data() {
    return {
      token: "",
      BlogList: [],
    };
  },
  methods: {
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
    GetSelfBlog() {
      //获取localStorage中的token信息（用来后端校验认证/授权）
      this.token = localStorage.getItem("token");
      request({
        //访问后端的blog_all的api，展示页面中的博客信息
        url: "/api/blog_all_self",
        method: "get",
        headers: {
          //在headers中传递token信息
          token: this.token,
        },
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
  },
  created() {
    //获取localStorage中的token信息（用来后端校验认证/授权）
    this.token = localStorage.getItem("token");
    request({
      //访问后端的blog_all的api，展示页面中的博客信息
      url: "/api/blog_all",
      method: "get",
      headers: {
        //在headers中传递token信息
        token: this.token,
      },
    })
      .then((res) => {
        console.log(res);
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
    console.log(this.$store.state.username);
  },
};
</script>

<style>
.blog {
  margin-top: 25px; /* 设置每条博客之间的间隙 */
}

.el-header {
  background-color: #eeffee;
  color: #333;
  text-align: left;
  line-height: 60px;
}

.el-main {
  background-color: #eee;
  color: #333;
  text-align: left;
  /*line-height: 160px;*/
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

#nav_main_items > #user_logo {
  margin-right: 80px;
  line-height: 40px;
}

#nav_main_items #btn {
  margin-right: 50px;
  display: flex;
  flex-direction: row;
  justify-content: flex-end;
}
</style>