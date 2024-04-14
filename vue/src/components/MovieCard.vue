<template>
    <div id="movie-card">
        <img id="movie-image" v-bind:src="movies.poster_path">
        <h2>{{ getGenreNames(movies.genres) }}</h2>
        <button v-on:click="getMoreInfo">See Movie Info</button>
    </div>
    <div>
    
    </div>
</template>

<script>
import MovieService from '../services/MovieService';



export default {
    data() {
        return {
            movies: {
                id: '',
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
            this.$router.push( { name: 'MovieInfoView', params: {id: 5 }});
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
#movie-card > h2 {
    align-items: center;
}

#movie-card > img {
    border: 2px solid white;
    border-radius: 5px;
}

#movie-card > button {
    height: 30px;
    border-radius: 20px;
    color: white;
    background-color: #ffda00;
    border: none;
    box-shadow: 2px 2px 5px black;
    font-size: 1rem;
    font-weight: 500;
    text-shadow: 2px 2px 5px black;
    margin-bottom: 30px;
}

#movie-card > button:hover {
    cursor: pointer;
}



</style>