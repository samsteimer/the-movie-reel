<template>
  <div id="login">
    <form v-on:submit.prevent="login">
      <h1 id="sign-in">Please Sign In</h1>
      <div role="alert" v-if="invalidCredentials">
        Invalid username and password!
      </div>
      <div role="alert" v-if="this.$route.query.registration">
        Thank you for registering, please sign in.
      </div>
      <div class="form-input-group">
        <label for="username">Username</label>
        <input type="text" id="username" v-model="user.username" required autofocus />
      </div>
      <div class="form-input-group">
        <label for="password">Password</label>
        <input type="password" id="password" v-model="user.password" required />
      </div>
      <button type="submit">Sign in</button>
      <p>
      <router-link id="need-account" v-bind:to="{ name: 'register' }">Need an account? Sign up.</router-link></p>
    </form>
  </div>
</template>

<script>
import authService from "../services/AuthService";
import userService from "../services/UserService";

export default {
  components: {},
  data() {
    return {
      user: {
        username: "",
        password: ""
      },
      invalidCredentials: false
    };
  },
  methods: {
    login() {
      authService
        .login(this.user)
        .then(response => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            // this.$store.commit("SET_USER", response.data.user);
            userService.getUser().then(res => {
              this.$store.commit("SET_USER", res.data);
              this.$router.push("/");
            })
          }
        })
        .catch(error => {
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    }
  }
};
</script>

<style scoped>

#login {
  margin-top: 50px;
  margin-left: 75px
}

#need-account {
  font-size: 1.3rem;
}
.form-input-group {
  font-size: 1.5rem;
  margin-bottom: 1rem;
}
label {
  margin-right: 1rem;
}

#sign-in {
  font-size: 2rem;
}
</style>