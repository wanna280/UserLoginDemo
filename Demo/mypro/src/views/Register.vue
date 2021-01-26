<template>
  <div id="regForm">
    <el-form
      :model="RegForm"
      status-icon
      :rules="rules"
      ref="RegForm"
      label-width="100px"
      class="demo-RegForm"
    >
      <el-form-item label="用户名" prop="username">
        <el-input
          placeholder="请输入用户名"
          v-model="RegForm.username"
        ></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="pass">
        <el-input
          placeholder="请输入密码"
          type="password"
          v-model="RegForm.pass"
          autocomplete="off"
        ></el-input>
      </el-form-item>
      <el-form-item label="确认密码" prop="checkPass">
        <el-input
          placeholder="请再次输入密码"
          type="password"
          v-model="RegForm.checkPass"
          autocomplete="off"
        ></el-input>
      </el-form-item>
      <el-form-item label="验证码" id="captcha_item">
        <img :src="captcha_src" @click="Captcha" />
      </el-form-item>
      <el-form-item id="captcha_input">
        <el-input
          placeholder="请输入验证码"
          v-model="RegForm.captcha"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('RegForm')"
          >提交</el-button
        >
        <el-button @click="resetForm('RegForm')">重置</el-button>
        <el-button @click="GoBack">返回</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
//导入网络请求包(封装的axios)
import request from "../network/request";

export default {
  data() {
    var checkUserName = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("用户名不能为空"));
      }
      setTimeout(() => {
        if (value.toString().length < 5 || value.toString().length > 16) {
          //用户名长度不能小于5或者大于16，否则给出警示
          callback(new Error("用户名长度必须在5-16位之间"));
        } else {
          //已经是5-16位了，进行正则匹配
          var regu = RegExp(/^[a-zA-Z]{1,16}.{2,15}$/);
          var status = regu.test(value);
          if (!status) {
            callback(new Error("用户名必须以字母开头"));
          } else {
            var regu_1 = RegExp(/^[a-zA-Z]{1,16}[0-9a-zA-Z_]{1,15}$/);
            var status_1 = regu_1.test(value);
            if (!status_1) {
              //如果用户使用了其他符号，给出警示
              callback(
                new Error(
                  "用户名可以使用数字、字母和下划线，但不能使用其他符号"
                )
              );
            } else {
              callback();
            }
          }
        }
      }, 500);
    };
    var validatePass = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("用户名不能为空"));
      }
      setTimeout(() => {
        if (value.toString().length < 5 || value.toString().length > 16) {
          //用户名长度不能小于5或者大于16，否则给出警示
          callback(new Error("密码长度必须在5-16位之间"));
        } else {
          //已经是5-16位了，进行正则匹配
          var regu_1 = RegExp(/^[0-9a-zA-Z_]{5,16}$/);
          var status_1 = regu_1.test(value);
          if (!status_1) {
            //如果用户使用了其他符号，给出警示
            callback(
              new Error("密码可以使用数字、字母和下划线，但不能使用其他符号")
            );
          } else {
            callback(); //callback
          }
        }
      }, 500);
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === "") {
        //如果是空，给出警示
        callback(new Error("请再次输入密码"));
      } else if (value !== this.RegForm.pass) {
        //如果两次密码不等，给出警示
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      captcha_src: "", //验证码src
      RegForm: {
        //注册表单
        pass: "", //密码
        checkPass: "", //重复密码
        username: "", //用户名
        captcha: "", //验证码
        uuid: "", //uuid
      },
      rules: {
        //规则，用来校验用户名和密码的输入情况
        //校验密码
        pass: [{ validator: validatePass, trigger: "blur" }],
        //校验重复输入的密码
        checkPass: [{ validator: validatePass2, trigger: "blur" }],
        //用户名
        username: [{ validator: checkUserName, trigger: "blur" }],
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
          //设置图形验证码的src
          this.captcha_src = res.data.captcha_base64;
          //获取uuid
          this.RegForm.uuid = res.data.uuid;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    GoBack() {
      //回退页面
      this.$router.go(-1); //回退到上一个页面
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          //如果合法，请求后端的注册api
          request({
            url: "/api/register",
            params: {
              username: this.RegForm.username, //用户名
              password: this.RegForm.checkPass, //密码
              captcha: this.RegForm.captcha, //验证码
              uuid: this.RegForm.uuid, //uuid
            },
          })
            .then((res) => {
              console.log(res);
              var status = res.data.status; //获取后端返回的状态
              if (status == true) {
                alert("用户" + this.RegForm.username + "注册成功");
                this.$router.push("/login");
              } else {
                //只要注册请求失败，就刷新验证码
                alert(
                  //弹框打印吃鱼来注册失败的原因，原因由后端给出
                  "用户" +
                    this.RegForm.username +
                    "注册失败，原因是：" +
                    res.data.reason
                );
                this.Captcha(); //刷新验证码
              }
            })
            .catch((err) => {
              console.log(err);
            });
        } else {
          console.log("提交失败，请检查输入的信息");
          return false;
        }
      });
    },
    resetForm(formName) {
      //重置按钮，按下按钮重置表单中的信息
      this.$refs[formName].resetFields();
    },
  },
  created() {
    this.Captcha(); //获取验证码
  },
};
</script>


<style>
#regForm {
  margin: auto;
  padding-top: 200px;
  height: 200px;
  width: 400px;
}

#captcha_input {
  margin-top: 0px;
  width: 400px;
}
</style>