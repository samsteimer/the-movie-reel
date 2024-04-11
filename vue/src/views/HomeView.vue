<template>

  <div class="home">


    <div id="home-header">
    <h1 id="head-text">Help Me Find A Movie</h1>
    <router-link id="genre-link" v-bind:to="{ name: 'Genres'}" ><img id="genre-button" src="../assets/ClickHereButton.png" alt="Click Here"></router-link>
  </div>
    

    
  </div>
</template>

<script>
export default {
};
</script>

<style>

#home-header {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

#head-text {
  display: flex;
  color: white;
  font-family: "League Spartan", sans-serif;
  justify-content: center;
  text-shadow: 2px 2px 5px black;
  font-weight: 700;
  font-size: 50px;
  margin-top: 30px;
}

#genre-link {
  display: flex;
  width: 350px;
}






</style>
