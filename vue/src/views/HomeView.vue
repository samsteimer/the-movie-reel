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
