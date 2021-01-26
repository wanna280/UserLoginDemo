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
          <div v-show="canBeEdited" class="nav_btn_self">
            <el-button @click="ToDelete">删除</el-button>
            <el-button @click="ToEdit">编辑</el-button>
          </div>
        </div>
      </div>
      <el-divider />
      <div>
        <article v-html="blog.content"></article>
      </div>
      <el-divider />
      <logo-items :blog_id="blog.id"></logo-items>
      <comment-text-area :blog_id="blog.id"></comment-text-area>
      <comment :blog_id="blog.id"></comment>
    </div>
  </div>
</template>
<script>
import marked from "marked";
import request from "../../network/request";
import LogoItems from "../../components/logo_items/LogoItems.vue";
import Comment from "../../components/comments/Comment.vue";
import CommentTextArea from "../../components/comments/Comment-TextArea.vue";
export default {
  components: { LogoItems, Comment, CommentTextArea },
  data() {
    return {
      blog: {
        id: 0, //id
        username: "", //用户名
        title: "", //标题
        content: "", //内容
        publishTime: "", //发布时间
      },
      editBtn_Show: this.EditBtn_Show, //是否显示编辑按钮和删除按钮
    };
  },
  created() {
    //获取Query中传递的参数blog_id，并转换成为int
    this.blog.id = parseInt(this.$route.query.blog_id);
    this.WatchingsUp(); //观看量增加
    this.GetBlogById(this.blog.id); //获取博客信息
  },
  methods: {
    ToDelete() {
      //删除博客，访问后端的API
      request({
        url: "/api/blog/delete/" + this.blog.id,
        method: "DELETE",
      })
        .then((res) => {
          if (res.data.code == 200) {
            alert("此博客删除成功！");
            this.$router.go(-1);
          }
          console.log(res);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    ToEdit() {
      //跳转到Edit页面
      this.$router.push({
        path: "/editblog",
        query: {
          blog_id: this.blog.id,
        },
      });
    },
    GetBlogById(id) {
      //根据Id获取后端数据
      request({
        //根据id显示具体博客的详细信息
        url: "/api/blog",
        method: "GET",
        params: {
          //参数是blog的id
          blog_id: id,
        },
      })
        .then((res) => {
          //console.log(res);
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
    WatchingsUp() {
      request({
        url: "/api/logoItems/watchings/increasing/" + this.blog.id,
      })
        .then((res) => {
          console.log(res);
        })
        .catch((err) => {
          console.log(err);
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
  mounted() {},
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
  margin-left: 400px;
}
</style>