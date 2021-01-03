<template>
  <div>main page</div>
</template>

<script>
import request from "../network/request"
export default {
  created() {
      request({
          url: "/test",
          method: "get",
          headers:{
              token: localStorage.getItem("token")
          }
      }).then((res)=>{
          console.log(res)
          //console.log(localStorage.getItem("token"))

      }).catch((err)=>{
          console.log(err)
      })
  },
};
</script>