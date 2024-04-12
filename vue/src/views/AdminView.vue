<template>
    Admin View - allow admin to add or remove movies
    <form class="add-movie-form" v-on:submit.prevent="addMovie">
        <input class="title-input" placeholder="Title" type="text" v-model="newMovie.title">
        <input class="poster-input" placeholder="Poster URL" type="text" v-model="newMovie.posterPath">
        <input class="overview-input" placeholder="Overview" type="text" v-model="newMovie.overview">
        <input class="release-input" placeholder="Release Date" type="text" v-model="newMovie.releaseDate">
        <input type="submit" value="Save">
    </form>


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
            }
        }
    },

    methods: {
        addMovie() {
            movieService.createMovie(this.newMovie).then(response => {
                this.$router.push({name: 'browse'})
            });

        }
    }
}

</script>