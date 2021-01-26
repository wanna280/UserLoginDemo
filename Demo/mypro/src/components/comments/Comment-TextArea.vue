<template>
  <div>
    <div class="comment-wrap">
      <div class="textarea-left">
        <div
          class="avatar-textarea"
          style="background-image: url('/assets/coolboy.jpg')"
        ></div>
      </div>

      <div class="comments_textarea">
        <div class="comment-block-textarea">
          <form action="">
            <textarea
              name=""
              id=""
              cols="88"
              rows="3"
              placeholder="Say somthing..."
              v-model="comment.content"
            ></textarea>
          </form>
          <el-button type="primary" id="send_comment" @click="SendComment"
            >发送</el-button
          >
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import request from "../../network/request.js";
export default {
  name: "CommentTextArea",
  props: {
    blog_id: Number,
  },
  data() {
    return {
      comment: {
        content: "", //对应表单中的内容
        blog_Id: "", //博客的ID
      },
    };
  },
  methods: {
    SendComment() {
      request({
        url:
          "/api/blog/comments/" +
          this.$props.blog_id +
          "/" +
          this.comment.content,
        method: "GET",
        headers: {
          token: localStorage.getItem("token"),
        },
      })
        .then((res) => {
          //后端返回状态200表示发送成功，不然表示发送失败并给出失败原因
          if (res.data.code == 200) {
            alert(res.data.msg); //打印成功信息
            this.$router.go(0); //刷新界面
          } else {
            alert(res.data.msg + "," + "请稍后再试");
          }
          //   console.log(res);
        })
        .catch((err) => {
          console.log(err);
        });
      //   console.log(this.comment.content);
      //   console.log(this.$props.blog_id);
    },
  },
  // 生命周期 - 创建完成
  created() {},

  // DOM挂载完毕
  mounted() {},
};
</script>

<style>
#send_comment {
  /** 按钮的摆放位置 */
  margin-left: 600px;
}

.comments_textarea {
  margin: 2.5rem auto 0;
  max-width: 60.75rem;
  padding: 0 1.25rem;
}

.comments_textarea {
  margin: 2.5rem auto 0;
  max-width: 60.75rem;
  padding: 0 1.25rem;
}
.comment-wrap {
  margin-bottom: 1.25rem;
  display: table;
  width: 100%;
  min-height: 5.3125rem;
}
.textarea-left {
  padding-top: 0.625rem;
  display: table-cell;
  width: 3.5rem;
}
.textarea-left .avatar-textarea {
  height: 2.25rem;
  width: 2.25rem;
  border-radius: 50%;
  background-size: contain;
}
.comment-block-textarea {
  padding: 1rem;
  background-color: #fff;
  display: table-cell;
  vertical-align: top;
  border-radius: 0.1875rem;
  box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.08);
}
.comment-block-textarea textarea {
  width: 100%;
  max-width: 100%;
}
</style>
