import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)


const store = new Vuex.Store({
    state: {
        username: "",
    },
    mutations: {
        SetUserName(state, payload) {
            state.username = payload.username;
        }
    }
})


export default store