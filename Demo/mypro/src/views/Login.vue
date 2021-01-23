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
      <el-form-item label="验证码" id="captcha_item">
        <img :src="captcha_src" @click="Captcha" />
      </el-form-item>
      <el-form-item id="captcha_input">
        <el-input
          placeholder="请输入验证码"
          v-model="LoginForm.captcha"
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
//导入网络请求包（封装的axios）
import request from "../network/request";

export default {
  data() {
    return {
      captcha_src: "", //验证码路径
      token: "", //Token
      LoginForm: {
        //Form
        username: "", //用户名
        password: "", //密码
        captcha: "", //验证码
        uuid: "", //uuid，后端给的用来校验redis中的验证码
      },
    };
  },
  methods: {
    Captcha() {
      //点击一下，刷新验证码
      request({
        url: "/api/captcha",
      })
        .then((res) => {
          //console.log(res);
          //设置图形验证码的base64编码，直接解析为图片
          this.captcha_src = res.data.captcha_base64;
          //获取uuid，用来给后端查询redis数据库校验验证码
          this.LoginForm.uuid = res.data.uuid;
        })
        .catch((err) => {
          //请求失败打印失败信息
          console.log(err);
        });
    },
    Submit() {
      //提交
      request({
        url: "/api/login", //访问后端登录API
        method: "post", //POST请求
        params: {
          //传递表单的信息，username和password和验证码，以及uuid
          username: this.LoginForm.username,
          password: this.LoginForm.password,
          captcha: this.LoginForm.captcha,
          uuid: this.LoginForm.uuid,
        },
      })
        .then((res) => {
          //响应成功处理的回调函数
          console.log(res);
          this.$store.commit({
            type: "SetUserName",
            username: this.LoginForm.username,
          });
          this.token = res.data.token; //通过后端API返回的接口设置token的值
          localStorage.setItem("token", this.token); //设置本地的token为this.token
          if (res.data.status == true) {
            //如果后端返回的登录成功的状态为true，跳转到main页面
            alert("用户" + this.LoginForm.username + "登录成功");
            this.$router.push("/main"); //跳转到main页面
          } else {
            //根据后端返回的信息给出警示
            if (res.data.reason === "验证码不正确") {
              alert("验证码不正确");
            } else {
              alert("账号或密码错误！");
            }
            this.Captcha(); //刷新验证码
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    Register() {
      //注册，点击注册按钮跳转到注册页面
      this.$router.push("/register");
    },
  },
  created() {
    //生命周期函数，当组件创建
    request({
      //请求后端验证码api，得到主页的验证码
      url: "/api/captcha",
    })
      .then((res) => {
        //console.log(res);
        //将页面中的验证码路径设为后端api返回的base64编码的验证码
        this.captcha_src = res.data.captcha_base64;
        //唯一的uuid，用来传递给后端查询redis做验证码校验
        this.LoginForm.uuid = res.data.uuid;
      })
      .catch((err) => {
        //请求失败打印失败信息
        console.log(err);
      });
    console.log(this.$store.state.username);
  },
};
</script>


<style>
#loginForm {
  margin: auto;
  padding-top: 200px;
  width: 500px;
}

#submit,
#reg {
  margin-left: 60px;
}

#captcha_item {
  display: flex;
}

#captcha_input {
  margin-top: 0px;
  width: 500px;
}
</style>