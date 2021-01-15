<template>
  <div>
    <div id="blog_title">
      <el-input
        type="textarea"
        autosize
        placeholder="请输入标题"
        v-model="Blog.blog_title"
      >
      </el-input>
    </div>
    <div id="blog_write">
      <mavon-editor v-model="Blog.content" ref="md" style="min-height: 600px" />
    </div>
    <div id="btn">
      <el-button type="primary" @click="Submit">提交</el-button>
      <el-button @click="Reset">重置</el-button>
    </div>
  </div>
</template>

<script>
//导入网络请求包（封装的axios）和markdown输入组件
import request from "../../network/request";
import { mavonEditor } from "mavon-editor";
import "mavon-editor/dist/css/index.css";
export default {
  components: {
    //注册组件
    mavonEditor,
  },
  data() {
    return {
      Blog: {
        blog_title: "", //博客的标题
        content: "", //博客的内容
      },
    };
  },
  methods: {
    Submit() {
      // 提交到数据库
      // console.log(this.Blog.content);
      // console.log(this.Blog.blog_title)
      if (this.Blog.blog_title == "") {
        alert("标题不能为空！");
        return;
      } else if (this.Blog.content == "") {
        alert("内容不能为空！");
        return;
      } else {
      }

      request({
        url: "/api/blog",
        method: "post",
        headers: {
          token: localStorage.getItem("token"), //传递token
        },
        params: {
          blog_title: this.Blog.blog_title, //传递title
          content: this.Blog.content, //传递content
        },
      })
        .then((res) => {
          console.log(res);
          if (res.data.status == true) {
            //后端返回true，提交成功，否则提交失败
            alert("提交成功");
            this.$router.push("/main");
          } else {
            alert("提交失败");
          }
        })
        .catch((err) => {
          //请求失败，打印失败原因
          console.log(err);
        });
    },
    Reset(fromForm) {
      //重置按钮，清空表单
      this.Blog.content = "";
      this.Blog.blog_title = "";
    },
  },
};
</script>

<style>
/*设置提交的按钮为居中*/
#btn {
  width: 200px;
  margin-top: 20px;
  margin-left: auto;
  margin-right: auto;
}
</style>