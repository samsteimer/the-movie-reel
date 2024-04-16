<template>
    <div v-if="favoriteMovies.length">
        <h1>My Favorites</h1>
        <div id="list-movies">
            <MovieCard v-for="movie in favoriteMovies" :key="movie.movie_id" :movie="movie">
                <button class="button-style" @click="() => removeFavoriteMovie(movie.movie_id)">Remove From WatchList</button>
            </MovieCard>
        </div>
    </div>

    <div v-if="filteredSuggestedMovies.length">
        <h1>Recommended Movies</h1>
        <div id="list-movies">
            <MovieCard v-for="movie in filteredSuggestedMovies" 
                :key="movie.movie_id" :movie="movie">
                    <button class="button-style" @click="() => addFavoriteMovie(movie.movie_id)">Add To WatchList</button>
            </MovieCard>
        </div>
    </div>

</template>

<script scoped>
import UserService from '../services/UserService';
import MovieListService from '../services/MovieListService';

import MovieCard from '../components/MovieCard.vue';
import MovieService from '../services/MovieService';

export default {
    data: () => ({
        suggestedMovies: [],
        favoriteMovies: []
    }),
    computed: {
        filteredSuggestedMovies() {
            return this.suggestedMovies.filter(m => !this.favoriteMovies.map(n => n.movie_id).includes(m.movie_id));
        }
    },
    methods: {
        addFavoriteMovie(movieId) {
            UserService.addFavoriteMovie(movieId).then(res => {
                if (res.status == 200) {
                    this.favoriteMovies.push(res.data);
                }
            })
        },
        removeFavoriteMovie(movieId) {
            UserService.removeFavoriteMovie(movieId).then(res => {
                if (res.status == 200) {
                    this.favoriteMovies = this.favoriteMovies.filter(m => m.movie_id != movieId);
                }
            })
        }
    },
    created() {
        const genres = this.$store.state.user.genres;
        if (genres) {
            MovieService.getMoviesByGenreIds(genres.map(g => g.genre_id)).then(res => {
                if (res.data.length) {
                    this.suggestedMovies = res.data;
                } else {
                    MovieListService.getMovieListMovies("Now Showing").then(res => {
                        this.suggestedMovies = res.data;
                    })
                }
            })
        }

        UserService.getFavoriteMovies().then(res => {
            if (res.data) {
                this.favoriteMovies = res.data;
            }   
        })

            
    },
    components: { MovieCard }
}

</script>

<style >
h1 {
    margin-bottom: 0.5em;
    color: white;
    text-shadow: 2px 2px 5px black;
    font-weight: 700;
    font-size: 2.5em;
    text-align: center;
}

#list-movies {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
}









</style>