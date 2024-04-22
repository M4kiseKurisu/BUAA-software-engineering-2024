// vuex 仓库
import { createStore } from 'vuex'

export default createStore({
    state: {
        id: null
    },
    mutations: {
        userLogin(state, data) {
            state.id = data.id;
            sessionStorage.setItem('id', JSON.stringify(data.id));
        }
    },
    actions: {
    },
    getters: {
    },
    modules: {
    }
})