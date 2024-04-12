<template>

  <div class="home">
    <h1>Home</h1>
    <p>You must be authenticated to see this</p>
    <h2>New Releases</h2>
    <ul>
    <li v-for="movie in newReleasesMovies" :key="movie.movie_id">{{ movie.title }}</li>
    </ul>
    <h2>Now Showing</h2>
    <ul>
    <li v-for="movie in nowShowingMovies" :key="movie.movie_id">{{ movie.title }}</li>
    </ul>

    <!--Title and link to see movies by genre-->
    <div id="home-header">
    <h1 id="head-text">Help Me Find A Movie</h1>
    <router-link id="genre-link" v-bind:to="{ name: 'Genres'}" ><img id="genre-button" src="../assets/ClickHereButton.png" alt="Click Here"></router-link>
  </div>
    
    
  </div>
</template>

<script>
import MovieListService from '../services/MovieListService';
export default {
  data() {
    return {
      nowShowingMovies: [],
      newReleasesMovies: [],
    }
  },
  computed() {

  },
  methods: {

  },
  created() {
    MovieListService.getMovieListMovies('Now Showing').then(res => {
      this.nowShowingMovies = res.data;
    });

    MovieListService.getMovieListMovies('New Releases').then(res => {
      this.newReleasesMovies = res.data;
    });


  },
  mounted() {

  }
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
