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
import request from "../network/request"; //导入axios

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
            callback();
          }
        }
      }, 500);
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.RegForm.pass) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      captcha_src: "",
      RegForm: {
        pass: "",
        checkPass: "",
        username: "",
        captcha: "",
        uuid: "",
      },
      rules: {
        pass: [{ validator: validatePass, trigger: "blur" }],
        checkPass: [{ validator: validatePass2, trigger: "blur" }],
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
          this.captcha_src = res.data.captcha_base64;
          this.RegForm.uuid = res.data.uuid;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    GoBack() {
      this.$router.go(-1); //回退到上一个页面
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          request({
            url: "/api/register",
            params: {
              username: this.RegForm.username,
              password: this.RegForm.checkPass,
              captcha: this.RegForm.captcha,
              uuid: this.RegForm.uuid,
            },
          })
            .then((res) => {
              console.log(res);
              var status = res.data.status; //获取后端返回的状态
              //console.log(status);
              if (status == true) {
                alert("用户" + this.RegForm.username + "注册成功");
                this.$router.push("/login");
              } else {
                alert(
                  "用户" +
                    this.RegForm.username +
                    "注册失败，原因是：" +
                    res.data.reason
                );
                this.Captcha()
              }
            })
            .catch((err) => {
              console.log(err);
            });
        } else {
          console.log("提交失败，清检查输入的信息");
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
  },
  created() {
    request({
      url: "/api/captcha",
    })
      .then((res) => {
        //console.log(res);
        this.captcha_src = res.data.captcha_base64;
        this.RegForm.uuid = res.data.uuid;
      })
      .catch((err) => {
        console.log(err);
      });
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