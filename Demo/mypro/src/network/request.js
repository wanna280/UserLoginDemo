import Axios from "axios";

//网络请求，封装后端的API接口
export default function(config) {
    const instance = Axios.create({
            baseURL: '/', //baseURL = /
            timeout: 5000, //Timeout=5s
            headers: { //headers

            }
        }) //instance是个promise对象，可以使用then和catch方法执行成功和失败的回调

    instance.interceptors.request.use((config) => {
        //使用拦截器，给每次网络请求加上token
        const token = localStorage.getItem("token");
        if (token) { //如果携带了token，加上token
            config.headers.token = token;
        } else { //如果没有携带token

        }
        return config;
    }, (err) => {
        console.log(err)
        return Promise.reject(err)
    })

    return instance(config); //返回instance对象
}