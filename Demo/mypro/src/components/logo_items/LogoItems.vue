<template>
  <div class="logo_items">
    <div v-if="true">
      <img src="../../assets/icon/comment.svg" width="20px" />
      <div style="width: 40px">{{ comment_numbers }}</div>
    </div>
    <div v-if="thumbsup_status" @click="ThumbsDown">
      <img src="../../assets/icon/thumbsup_true.svg" width="20px" />
      <div style="width: 40px">{{ thumbsup_numbers }}</div>
    </div>
    <div v-if="!thumbsup_status" @click="ThumbsUp">
      <img src="../../assets/icon/thumbsup_false.svg" width="20px" />
      <div style="width: 40px">{{ thumbsup_numbers }}</div>
    </div>
    <div v-if="true">
      <img src="../../assets/icon/watching.svg" width="20px" />
      <div style="width: 40px">{{ watching_numbers }}</div>
    </div>
  </div>
</template>

<script>
import request from "../../network/request";
export default {
  name: "LogoItems",
  props: {
    blog_id: Number, //属性是blog_id，Number类型的
  },
  data() {
    return {
      blog_Id: 0,
      //false显示白色logo，true显示黑色logo
      thumbsup_status: false,
      comment_numbers: 0,
      thumbsup_numbers: 0,
      watching_numbers: 0,
    };
  },
  methods: {
    //当点赞是黑色logo时
    ThumbsDown() {
      alert("您今天已经点过赞了~请明天再来~");
    },
    //当点赞是白色logo时
    ThumbsUp() {
      //点赞数+1，那么后端Redis就必须新增一个key
      this.thumbsup_numbers += 1;
      request({
        url: "/api/logoItems/thumbsup/thumbsup/" + this.blog_Id,
        headers: {
          token: localStorage.getItem("token"),
        },
      })
        .then((res) => {
          //console.log(res);
          this.thumbsup_status = !this.thumbsup_status;
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
  // 生命周期 - 创建完成
  created() {
    this.blog_Id = this.$props.blog_id;
    request({
      url: "/api/logoItems/get/" + this.blog_Id,
      method: "GET",
      headers: {
        token: localStorage.getItem("token"),
      },
    })
      .then((res) => {
        //console.log(res);
        this.comment_numbers = res.data.comment_numbers;
        this.thumbsup_numbers = res.data.thumbsup_numbers;
        this.watching_numbers = res.data.watching_numbers;
      })
      .catch((err) => {
        console.log(err);
      });

    request({
      url: "/api/logoItems/thumbsup/isthumbsup/" + this.blog_Id,
      headers: {
        token: localStorage.getItem("token"),
      },
    })
      .then((res) => {
        //Redis中有，就把赞logo设为黑色的
        //Redis中没有，就把赞logo设为白色的
        this.thumbsup_status = res.data.status;
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
.logo_items {
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
}

.logo_items > div {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}
</style>
