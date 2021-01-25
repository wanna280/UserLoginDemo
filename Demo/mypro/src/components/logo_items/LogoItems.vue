<template>
  <div class="logo_items">
    <div v-if="true">
      <img src="../../assets/icon/comment.svg" width="20px" />
      <div style="width: 40px">{{ comment_numbers }}</div>
    </div>
    <div v-if="thumbsup_status" @click="ThumbsUp">
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
    blog_id: Number,
  },
  data() {
    return {
      blog_Id: 0,
      thumbsup_status: false,
      comment_numbers: 0,
      thumbsup_numbers: 0,
      watching_numbers: 0,
      //true表示不能继续点赞，false表示还能继续点赞
      is_thumbsup: true,
    };
  },
  methods: {
    ThumbsUp() {
      //如果后端返回true，就不能继续点赞了
      //如果后端返回false，就能继续点赞
      request({
        url: "/api/logoItems/thumbsup/isthumbsup/" + this.blog_Id,
        method: "GET",
        headers: {
          token: localStorage.getItem("token"),
        },
      })
        .then((res) => {
          this.is_thumbsup = res.data.status;
          if (this.is_thumbsup == false) {
            if (this.thumbsup_status == true) {
              this.thumbsup_numbers -= 1;
              request({
                url: "/api/logoItems/thumbsup/decreasing/" + this.blog_Id,
                headers: {
                  token: localStorage.getItem("token"),
                },
              })
                .then((res) => {
                  console.log(res);
                })
                .catch((err) => {
                  console.log(err);
                });
              //提交给后台
            } else {
              request({
                url: "/api/logoItems/thumbsup/increasing/" + this.blog_Id,
                headers: {
                  token: localStorage.getItem("token"),
                },
              })
                .then((res) => {
                  console.log(res);
                })
                .catch((err) => {
                  console.log(err);
                });
              //提交给后台
            }
            //点赞状态取反
            this.thumbsup_status = !this.thumbsup_status;
          } else {
            this.thumbsup_status = false;
            alert("当天已经点过赞了！");
          }
          //console.log(res);
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
