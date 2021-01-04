<template>
  <div>main page</div>
</template>

<script>
import request from "../network/request";
export default {
  data() {
    return {
      token: "",
    };
  },
  created() {
    this.token = localStorage.getItem("token");
    request({
      url: "/api/test",
      method: "get",
      headers: {
        token: this.token,
      },
    })
      .then((res) => {
        console.log(res);
        //console.log(localStorage.getItem("token"))
      })
      .catch((err) => {
        console.log(err);
      });
  },
};
</script>