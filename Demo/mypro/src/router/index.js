import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login.vue'
import Main from '../views/Main.vue'
import Home from '../views/Home.vue'

Vue.use(VueRouter)


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
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

//全局守卫，如果用户访问的不是登录页面并且Token是空的，给跳转到login界面
router.beforeEach((to, from, next) => {
    if (to.name != 'Login' && localStorage.getItem("token") == null) {
        next("/login")
    }
    next()
})

const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => err)
}

export default router