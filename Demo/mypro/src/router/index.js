import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login.vue'
import Home from '../views/Home.vue'
import request from "../network/request"

Vue.use(VueRouter)
const Main = () =>
    import ('../views/Main.vue');

const Register = () =>
    import ("../views/Register.vue")

const routes = [{
        path: '/', //根路径直接重定向到/login
        redirect: '/login'
    },
    {
        path: '/',
        name: 'Home',
        component: Home,
    },
    {
        path: '/login',
        name: 'Login',
        component: Login
    },
    {
        path: '/main',
        name: 'Main',
        component: Main
    },
    {
        path: "/register",
        name: 'Register',
        component: Register
    }
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
                console.log(res)
                    //如果后端返回的接口状态是true，则next
                if (res.data.status == true) {
                    next()
                } else { //如果没有返回true，则回到登录页面
                    next("/login")
                }
            })
            .catch((err) => {
                console.log(err)
                next("/login")
            })
    }
    next()
})

const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => err)
}

export default router