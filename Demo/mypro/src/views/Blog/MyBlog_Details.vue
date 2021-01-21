<template>
  <div id="details_main">
    <div class="blog">
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
          <div v-show="canBeEdited">
            <el-button @click="ToEdit">编辑</el-button>
          </div>
        </div>
      </div>
      <el-divider />
      <div>
        <article v-html="blog.content"></article>
      </div>
    </div>
  </div>
</template>
<script>
import marked from "marked";
import request from "../../network/request";
import { mavonEditor } from "mavon-editor";
import "mavon-editor/dist/css/index.css";
export default {
  data() {
    return {
      blog: {
        id: 0, //id
        username: "", //用户名
        title: "", //标题
        content: "", //内容
        publishTime: "", //发布时间
      },
      editBtn_Show: this.EditBtn_Show,
    };
  },
  created() {
    this.blog.id = this.$route.query.blog_id;
    request({
      //根据id显示具体博客的详细信息
      url: "/api/blog",
      method: "GET",
      params: {
        //参数是blog的id
        blog_id: this.blog.id,
      },
      headers: {
        //传递token
        token: localStorage.getItem("token"),
      },
    })
      .then((res) => {
        console.log(res);
        this.blog.username = res.data.username;
        this.blog.blog_title = res.data.blog_title;

        //将内容部分的markdown转换为html
        this.blog.content = marked(res.data.content);

        this.blog.id = res.data.id;
        this.blog.publishTime = res.data.publishTime;
      })
      .catch((err) => {
        console.log(err);
      });
  },
  methods: {
    ToEdit() {
      this.$router.push({
        path: "/editblog",
        query: {
          blog_id: this.blog.id,
        },
      });
    },
  },
  computed: {
    //判断是否显示编辑按钮
    canBeEdited: function () {
      if (this.$store.state.username == this.blog.username) {
        return true;
      }
      return false;
    },
  },
};
</script>

<style>
#details_main {
  width: 800px;
  margin-left: auto;
  margin-right: auto;
}
#nav_blog {
  display: flex;
}

#nav_title {
  margin-left: 0px;
}

#nav_btn {
  margin-left: 500px;
}
</style>