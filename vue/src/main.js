import { createApp } from 'vue'
import CapstoneApp from './App.vue'
import { createStore } from './store'
import router from './router'
import axios from 'axios'

/* sets the base url for server API communication with axios */
axios.defaults.baseURL = import.meta.env.VITE_REMOTE_API;

/*
 * The authorization header is set for axios when you login but what happens when 
 * you come back or the page is refreshed. When that happens you need to check 
 * for the token in local storage and if it exists you should set the header 
 * so that it will be attached to each request.
 */
let currentToken = localStorage.getItem('token');
let currentUser = {};
let currentIsAdmin = false;

try {
  currentUser = JSON.parse(localStorage.getItem('user'));
  currentIsAdmin = JSON.parse(localStorage.getItem("isAdmin"));
} catch {
  localStorage.removeItem('token');
  localStorage.removeItem('user');
  localStorage.removeItem("isAdmin");
  currentToken = '';
}

if (currentToken) {
  // Set token axios requests
  axios.defaults.headers.common['Authorization'] = `Bearer ${currentToken}`;
}

// Create the Vuex store passing in the stored credentials
const store = createStore(currentToken, currentUser, currentIsAdmin);

const app = createApp(CapstoneApp);
app.use(store);
app.use(router);
app.mount('#app');