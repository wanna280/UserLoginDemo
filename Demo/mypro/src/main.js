import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import store from "../src/store/index"

Vue.use(ElementUI)

Vue.config.productionTip = false

new Vue({
    router,
    store: store,
    render: function(h) { return h(App) },

}).$mount('#app')