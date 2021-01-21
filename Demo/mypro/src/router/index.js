import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login.vue'
import Home from '../views/Home.vue'
import request from "../network/request"

import store from '../store/index'

Vue.use(VueRouter)

const Main = () => //主页面，显示博客主页面等信息
    import ('../views/Main.vue');

const Register = () => //注册页面
    import ("../views/Register.vue")

const WriteBlog = () => //写博客页面
    import ("../views/Blog/MyBlog_Write.vue")

const BlogDetails = () => //博客详情页
    import ("../views/Blog/MyBlog_Details.vue")

const EditBlog = () =>
    import ("../views/Blog/MyBlog_Edit.vue") //编辑博客界面

const routes = [{ //设置重定向路径为登录页面
        path: '/', //根路径直接重定向到/login
        redirect: '/login'
    },
    { //Home
        path: '/home',
        name: 'Home',
        component: Home,
    },
    { //登录页面
        path: '/login',
        name: 'Login',
        component: Login
    },
    { //main主页面
        path: '/main',
        name: 'Main',
        component: Main
    },
    { //注册页面
        path: "/register",
        name: 'Register',
        component: Register
    },
    { //写博客页面
        path: '/writeblog',
        name: 'WriteBlog',
        component: WriteBlog
    },
    { //博客详情页面
        path: '/blogdetails',
        name: 'BlogDetails',
        component: BlogDetails
    },
    { //编辑页面
        path: '/editblog',
        name: 'EditBlog',
        component: EditBlog
    },
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

//全局守卫，如果用户访问的不是登录页面并且未登录，给跳转到login界面
router.beforeEach((to, from, next) => {
    //如果访问登录页或者注册页，直接放行
    if (to.name == "Login" || to.name == "Register") {
        next()
    } else { //如果访问的不是注册页或者是登录页，先发送网络请求
        request({
                url: "/api/verify", //访问认证授权的接口
                method: "get",
                headers: {
                    token: localStorage.getItem("token")
                },
            })
            .then((res) => {
                //console.log(res)
                //如果后端返回的接口状态是true，则next
                if (res.data.status == true) {
                    store.commit({
                        type: "SetUserName",
                        username: res.data.username,
                    })
                    next()
                } else { //如果没有返回true，则回到登录页面
                    next("/login")
                }
            })
            .catch((err) => { //打印异常并跳转到next页面
                console.log(err)
                next("/login")
            })
    }
    next() //next，必须调用，才能进行下一步
})

//避免使用this.$router.push()报错
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => err)
}

export default router //导出路由