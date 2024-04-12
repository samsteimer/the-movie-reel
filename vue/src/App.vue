<template>
  <div id="capstone-app">

    <div id="nav" class="header"> 


      <router-link to="/" id="home-link">
  <img id="header-logo" src="../src/assets/LogoConcept.png" alt="HeaderLogo">
</router-link>

      
      <router-link id="logout-link" v-bind:to="{ name: 'logout' }" v-if="$store.state.token !=''">Logout</router-link>
      <router-link id="login-link" v-bind:to="{ name: 'login'}" v-else>Login</router-link>
      <router-link id="profile-link" v-bind:to="{ name: 'Profile'}" v-if="$store.state.token !=''">My Profile</router-link>
      <router-link id="watchlist-link" v-bind:to="{ name: 'WatchList'}" v-if="$store.state.token !=''">My Watchlist</router-link>
      <router-link id="browse-link" v-bind:to="{ name: 'browse'}" >Browse Movies</router-link>  
    </div>

    <router-view />
  </div>
</template>

<!-- 
v-if=$store.state.token != ''
add back on to router-links after testing -->


<style>


#capstone-app {
  background-image: url('../src/assets/Red Background.png'); 
  background-attachment: scroll;
  background-size: cover; 
  
}

.header {
  display: flex;
  font-family: "League Spartan", sans-serif;
  text-shadow: 2px 2px 5px black;
  font-weight: 700;
  font-size: x-large;
}

  #logout-link, #profile-link, #watchlist-link, #browse-link, #login-link {
    color: white;
    margin-top: 40px;
    margin-right: 30px; 
}

#logout-link:hover, #profile-link:hover, #watchlist-link:hover, #browse-link:hover, #login-link:hover {
  font-size: 110%;
}

#header-logo {
    height: 125px;
    margin-right: 30px;  
}


</style>
