<template>
    Admin View - allow admin to add or remove movies
    <div>
    <form class="add-movie-form" v-on:submit.prevent="addMovie">
        <input class="title-input" placeholder="Title" type="text" v-model="newMovie.title">
        <input class="poster-input" placeholder="Poster URL" type="text" v-model="newMovie.posterPath">
        <input class="overview-input" placeholder="Overview" type="text" v-model="newMovie.overview">
        <input class="release-input" placeholder="Release Date" type="text" v-model="newMovie.releaseDate">
        <input type="submit" value="Save">
    </form>
    </div>
    <div>
        <h1>Search for a movie</h1>
        <input class="search-input" type="text" v-model="searchTitle" @keyup.enter="search()" >
    </div>

    <div v-if="searchResults && searchResults.results && searchResults.results.length > 0">
        <h2>Search Results</h2>
        <ul>
            <li v-for="movie in searchResults.results" :key="movie.id">
                <h3>{{ movie.title }}</h3>
                <p>{{ movie.overview }}</p>
                <p>Release Date: {{ movie.release_date }}</p>
                <img v-if="movie.poster_path" :src="movie.poster_path" alt="Movie Poster" style="max-width: 200px;">
                <span v-else>No poster available</span>
                <button @click="addMovieFromApi(movie)">Add Movie</button>
            </li>
        </ul>

    </div>

    



</template>

<script>

import movieService from '../services/MovieService';

export default {
    data() {
        return {
            newMovie: {
                title: '',
                posterPath: '',
                overview: '',
                releaseDate: ''
            },

            searchTitle: '',
            searchResults: {
                results: []
            }
        }
    },

    methods: {
        addMovie(movie) {
            movieService.createMovie(this.newMovie).then(response => {
                this.$router.push({name: 'browse'})
            });
        },

        addMovieFromApi(movie) {
            this.newMovie.title = movie.title;
            this.newMovie.posterPath = movie.poster_path;
            this.newMovie.overview = movie.overview;
            this.newMovie.releaseDate = movie.release_date;


            console.log("Added movie:", this.newMovie)
            this.addMovie();
        },

        search() {
            movieService.searchMoviesFromApi(this.searchTitle).then(response => {
                console.log("Search response:", response);
                this.searchResults = response.data;
                
            }).catch(error => {
                console.error('Error searching movies:', error)
            });
        },
        
    }
}

</script>