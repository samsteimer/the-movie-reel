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
        <input type="text" id="username" v-model="user.username" placeholder="Username" required autofocus />
      </div>
      <div class="form-input-group">
        <label for="password">Password</label>
        <input type="password" id="password" v-model="user.password" placeholder="Password" required />
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
              this.$router.go(-1);
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

#need-account {
  font-size: 0.95em !important;
}

#login {
  width: 40%;
  min-width: 450px;
  margin: 2em auto;
  text-align: center;
  /* margin-top: 50px;
  margin-left: 75px */
}

#login a {
  color: white !important;
}

#login label {
  display: block;
  font-size: 0.65em;
  font-weight: 600;
}

#login input[type=text],
#login input[type=password] {
    margin: 0.5em;
    width: 60%;
    padding: 0.75em;
    border: 2px solid #000;
    border-radius: 0.75em;
}

#login button {
    margin: 0.75em 0;
    padding: 0.35em 3em;
    background-color: #ffb62e;
    box-shadow: 2px 2px 5px black;
    border: none;
    border-radius: 1.5em;
    font-size: 1.15em;
    cursor: pointer;
}

#login button:active {
  transform: scale(.98);
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