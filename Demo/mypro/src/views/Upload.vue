<template>
  <div id="upload_main">
    <el-upload
      :limit="1"
      class="upload-demo"
      ref="upload"
      action
      :on-preview="handlePreview"
      :on-remove="handleRemove"
      :file-list="fileList"
      :auto-upload="false"
      :http-request="UploadSubmit"
    >
      <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
      <el-button
        style="margin-left: 10px"
        size="small"
        type="success"
        @click="submitUpload"
        >上传到服务器</el-button
      >
      <div slot="tip" class="el-upload__tip">
        只能上传jpg/png文件，且不超过1MB
      </div>
    </el-upload>
    <img :src="logo_src" />
  </div>
</template>
<script>
import request from '../network/request.js'
export default {
  data() {
    return {
      fileList: [],
      logo_src: "",
    };
  },
  methods: {
    UploadSubmit(param) {
      var file = param.file;
      //console.log(param.file);
      var file_form = new FormData(); //获取上传表单（文件）
      file_form.append("file", file);
      request({
        url: "/api/file/upload/logo",
        method: "POST",
        data: file_form,
      })
        .then((res) => {
          if (res.data.status == true) {
            //如果后端返回了true，表明文件上传成功，否则文件上传失败
            alert("上传文件成功");
          } else {
            alert("上传文件失败，原因是" + res.data.reason);
          }
          console.log(res);
          this.$router.push("/setting");
        })
        .catch((err) => {
          console.log(err);
        });
    },
    submitUpload() {
      this.$refs.upload.submit();
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      console.log(file);
    },
  },
  created() {},
};
</script>

<style>
#upload_main {
  margin-top: 300px;
  margin-left: auto;
  margin-right: auto;
}
</style>