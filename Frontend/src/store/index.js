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
        },
        userLogout(state) {
            state.id = null;
            sessionStorage.removeItem('id');
        }
    },
    actions: {
    },
    getters: {
    },
    modules: {
    }
})