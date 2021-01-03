import Axios from "axios";

//网络请求，封装后端的API接口
export default function(config) {
    const instance = Axios.create({
            baseURL: '/api',
            timeout: 5000,
            headers: {

            }
        }) //instance是个promise对象，可以使用then和catch方法执行成功和失败的回调

    return instance(config); //返回instance对象
}