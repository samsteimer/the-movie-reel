<template>

  <div class="home">

    <div id="home-header">
    <h1 id="head-text">Help Me Find A Movie</h1>
    <router-link id="genre-link" v-bind:to="{ name: 'Genres'}" ><img id="genre-button" src="../assets/ClickHereButton.png"></router-link>
  </div>

    <h2 id="featured">Featured Movies</h2>
    <ul id="home-movies">
      <MovieCard v-for="movie in newReleasesMovies" :key="movie.movie_id" :movie="movie" />
    </ul>
  
    <!-- <ul>
    <li v-for="movie in nowShowingMovies" :key="movie.movie_id">{{ movie.title }}</li>
    </ul> -->

    <!--Title and link to see movies by genre-->
    
    
    
  </div>
</template>

<script>
import MovieListService from '../services/MovieListService';
import MovieCard from '../components/MovieCard.vue';
export default {

  components: {
    MovieCard
  },

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
  width: 225px;
}

#home-movies {
  display: flex;
  justify-content: space-around;
}

#home-movies > li {
  display: flex;
  border: 2px solid;
  border-radius: 5px;
  margin-bottom: 75px;
  box-shadow: 2px 2px 4px #000000;
}
#featured {
  display: flex;
  font-family: "League Spartan", sans-serif;
  text-shadow: 2px 2px 5px black;
  font-weight: 700;
  font-size: 2.5rem;
  margin-left: 40px;
}






</style>
