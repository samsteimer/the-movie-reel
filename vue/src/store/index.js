import { createStore as _createStore } from "vuex";
import axios from "axios";

export function createStore(currentToken, currentUser, currentIsAdmin) {
  let store = _createStore({
    state: {
      token: currentToken || "",
      user: currentUser || {},
      isAdmin: currentIsAdmin || false,
    },
    mutations: {
      SET_AUTH_TOKEN(state, token) {
        state.token = token;
        localStorage.setItem("token", token);
        axios.defaults.headers.common["Authorization"] = `Bearer ${token}`;
      },
      SET_USER(state, user) {
        state.user = user;
        const isAdmin = user.authorities.some(
          (role) => role.name === "ROLE_ADMIN"
        );
        state.isAdmin = isAdmin;
        localStorage.setItem("user", JSON.stringify(user));
        localStorage.setItem("isAdmin", JSON.stringify(isAdmin));
      },
      LOGOUT(state) {
        localStorage.removeItem("token");
        localStorage.removeItem("user");
        localStorage.removeItem("isAdmin");
        state.token = "";
        state.user = {};
        state.isAdmin = false;
        axios.defaults.headers.common = {};
      },
    },
  });
  return store;
}
