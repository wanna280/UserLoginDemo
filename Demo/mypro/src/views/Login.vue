<template>
  <div id="loginForm">
    <el-form label-width="100px" class="demo-ruleForm">
      <el-form-item label="用户名">
        <el-input
          v-model="LoginForm.username"
          placeholder="请输入用户名"
          class="form"
        ></el-input>
      </el-form-item>
      <el-form-item label="密码">
        <el-input
          placeholder="请输入密码"
          v-model="LoginForm.password"
          show-password
          class="form"
        ></el-input>
      </el-form-item>
      <el-form-item id="btn">
        <el-button
          type="success"
          id="submit"
          class="form_submit"
          @click="Submit"
          >登录</el-button
        >
        <el-button type="primary" id="reg" class="form_reg" @click="Register"
          >注册</el-button
        >
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import request from "../network/request";

export default {
  data() {
    return {
      token: "", //Token
      LoginForm: {
        username: "",
        password: "",
      },
    };
  },
  methods: {
    Submit() {
      request({
        url: "/api/login", //访问后端登录API
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
    Register() {
      this.$router.push("/register");
    },
  },
};
</script>


<style>
#loginForm {
  margin: auto;
  padding-top: 200px;
  width: 500px;
}

#submit,#reg{
  margin-left: 60px;
}
</style>