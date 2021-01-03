<template>
  <div>
    <div id="loginForm">
      <el-input
        v-model="LoginForm.username"
        placeholder="请输入内容"
        class="form"
      ></el-input>
      <el-input
        placeholder="请输入密码"
        v-model="LoginForm.password"
        show-password
        class="form"
      ></el-input>
      <el-button type="submit" id="submit" class="form" @click="submit"
        >Submit</el-button
      >
    </div>
    <router-view></router-view>
  </div>
</template>

<script>
import request from "../network/request";

export default {
  data() {
    return {
      token: "",
      LoginForm: {
        username: "wanna",
        password: "123456",
      },
    };
  },
  methods: {
    submit() {
      request({
        url: "/login", //访问后端登录API
        method: "post", //POST请求
        params: {
          //传递表单的信息，username和password
          username: this.LoginForm.username,
          password: this.LoginForm.password,
        },
      })
        .then((res) => {
          //响应成功处理的回调函数
          console.log(res);
          this.token = res.data.token; //通过后端API返回的接口设置token的值
          //console.log(this.token)
          localStorage.setItem("token", this.token); //设置本地的token为this.token
          if (res.data.status == true) {
            //如果后端返回的登录成功的状态为true，跳转到main页面

            alert("用户" + this.LoginForm.username + "登录成功");
            this.$router.push("/main"); //跳转到main页面
          } else {
            //如果返回的是false
            alert("账号或密码错误！");
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};
</script>


<style>
.form {
  margin-top: 10px;
}

#loginForm {
  width: 400px;
  height: 200px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

#submit {
  margin-top: 10px;
}
</style>