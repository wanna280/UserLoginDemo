<template>
  <div>
    <div class="blog">
      <div>
        <h2>UserName: {{ blog.username }}</h2>
      </div>
      <div>
        <h3>publishTime: {{ blog.publishTime }}</h3>
      </div>
      <div>
        <h3>Title: {{ blog.blog_title }}</h3>
      </div>
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
  methods: {},
};
</script>