<template>
  <div>
    <div
      class="comments"
      v-for="comment in blog_comment_List"
      :key="comment.id"
    >
      <div class="comment-wrap">
        <div class="photo">
          <div
            class="avatar"
            style="background-image: url('/assets/coolgirl.jpg')"
          ></div>
        </div>
        <div class="comment-block">
          <p class="comment-text">
            {{ comment.content }}
          </p>
          <div class="bottom-comment">
            <div class="comment-date">
              {{ comment.publishTime.substring(0, 10) }}
            </div>
            <ul class="comment-actions">
              <li class="complain">Complain</li>
              <li class="reply">Reply</li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import request from "../../network/request";
export default {
  name: "Comment",
  props: {
    blog_id: Number,
  },
  data() {
    return {
      blog_comment_List: [],
    };
  },
  methods: {},
  // 生命周期 - 创建完成
  created() {
    request({
      url: "/api/blog/comments/" + this.$props.blog_id,
      method: "GET",
      headers: {
        token: localStorage.getItem("token"),
      },
    })
      .then((res) => {
        this.blog_comment_List = res.data.data;
        console.log(res);
      })
      .catch((err) => {
        console.log(err);
      });
  },

  // DOM挂载完毕
  mounted() {},
};
</script>
<style>
.blog_comment_list {
  display: flex;
  flex-direction: row;
  justify-content: space-around;
}

input,
textarea {
  outline: none;
  border: none;
  display: block;
  margin: 0;
  padding: 0;
  -webkit-font-smoothing: antialiased;
  font-family: "PT Sans", "Helvetica Neue", "Helvetica", "Roboto", "Arial",
    sans-serif;
  font-size: 1rem;
  color: #555f77;
}
input::-webkit-input-placeholder,
textarea::-webkit-input-placeholder {
  color: #ced2db;
}
input::-moz-placeholder,
textarea::-moz-placeholder {
  color: #ced2db;
}
input:-moz-placeholder,
textarea:-moz-placeholder {
  color: #ced2db;
}
input:-ms-input-placeholder,
textarea:-ms-input-placeholder {
  color: #ced2db;
}
p {
  line-height: 1.3125rem;
}
.comments {
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
.photo {
  padding-top: 0.625rem;
  display: table-cell;
  width: 3.5rem;
}
.photo .avatar {
  height: 2.25rem;
  width: 2.25rem;
  border-radius: 50%;
  background-size: contain;
}
.comment-block {
  padding: 1rem;
  background-color: #fff;
  display: table-cell;
  vertical-align: top;
  border-radius: 0.1875rem;
  box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.08);
}
.comment-block textarea {
  width: 100%;
  max-width: 100%;
}
.comment-text {
  margin-bottom: 1.25rem;
}
.bottom-comment {
  color: #acb4c2;
  font-size: 0.875rem;
}
.comment-date {
  float: left;
}
.comment-actions {
  float: right;
}
.comment-actions li {
  display: inline;
}
.comment-actions li.complain {
  padding-right: 0.625rem;
  border-right: 1px solid #e1e5eb;
}
.comment-actions li.reply {
  padding-left: 0.625rem;
}
</style>