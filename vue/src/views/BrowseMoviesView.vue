<template>
    
    <div id="browse-title">
    <h1 id="head-text">Browse All Movies</h1>
    </div>

    <MovieCard />

<!-- <h2>Genres</h2>
<ul>
    <li v-for="genre in genres" :key="genre.genre_id">{{ genre.genre_name }}</li>
</ul>

<h2>Comedies</h2>
<ul>
    <li v-for="movie in comedyMovies" :key="movie.movie_id">{{ movie.title }}</li>
</ul> -->
</template>

<script>
import MovieCard from '../components/MovieCard.vue';
import MovieService from '../services/MovieService';
import GenreService from '../services/GenreService';

export default {

    components: {
        MovieCard
    },

    data() {
        return {
            genres: [],
            comedyMovies: [],
        }
    },

    computed() {

    },

    methods: {
        
    },

    created() {
        GenreService.getGenres().then(res => {
            this.genres = res.data;
            const comedyGenre = this.genres.find(genre => genre.genre_name == "Comedy");
            
            MovieService.getMoviesByGenreId(comedyGenre.genre_id).then(res => {
                this.comedyMovies = res.data;
            })
        })
    }

}
</script>

