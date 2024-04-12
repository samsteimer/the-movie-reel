<template>
    <div id="movie-card">
    <h2>{{ getGenreNames(movies.genres) }}</h2>
    <img id="movie-image" v-bind:src="movies.poster_path">
    <button v-on:click="getMoreInfo">See Movie Info</button>
    </div>
</template>

<script>
import MovieService from '../services/MovieService';


export default {
    data() {
        return {
            movies: {
                title: '',
                overview: '',
                genres: [],
                poster_path: '',
                release_date: ''
            }
        }
    },

    props: ['movie'],



    methods: {

        getMoreInfo() {
            console.log("ive been clicked")
            this.$router.push( { name: 'MovieInfoView', params: {id: 1}});
        },

        getGenreNames(genres) {
            return genres.map(genre => genre.genre_name)
        }


    },

    created() {
        MovieService.getMovieByMovieId(5).then (res => {
            this.movies = res.data;

        })
    }


}


</script>

<style>

#movie-card {
    display: flex;
    flex-direction: column;
    width: 200px;
}



</style>