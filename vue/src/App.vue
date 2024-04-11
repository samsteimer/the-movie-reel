<template>
  <div id="capstone-app">

    <div id="nav" class="header"> 

      <a href="../src/views/HomeView.vue"><img id="header-logo" src="../src/assets/LogoConcept.png" alt="HeaderLogo"></a>

      
      <router-link id="logout-link" v-bind:to="{ name: 'logout' }">Logout</router-link>
      <router-link id="profile-link" v-bind:to="{ name: 'Profile'}">My Profile</router-link>
      <router-link id="watchlist-link" v-bind:to="{ name: 'WatchList'}" >My Watchlist</router-link>
      <router-link id="browse-link" v-bind:to="{ name: 'WatchList'}">Browse Movies</router-link>   <!--Needs linked to browse movies page not created yet-->
    </div>

    <router-view />
  </div>
</template>

<!-- 
v-if=$store.state.token != ''
add back on to router-links after testing -->


<style>

.header {
  display: flex;
  font-family: "League Spartan", sans-serif;
  font-weight: 700;
  font-size: x-large;
  margin-top: 10px;
}

 #home-link, #logout-link, #profile-link, #watchlist-link, #browse-link {
    margin-top: 40px;
    margin-right: 30px; 
}

#header-logo {
    height: 125px;
    margin-right: 30px;
    
}

</style>
