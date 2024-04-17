<template>
    <div id="movie-review">
    <h2>{{ getStarRating(movieReview.star_rating) }}</h2>
    <img id="star-image" v-bind:src="src/assets/star.png">
    </div>
</template>

<script>
import MovieService from '../services/MovieReviewService';


export default {
    data() {
        return {
            movieReview: {
                review: '',
                movie_id: '',
                review_id: '',
                star_rating: '',
                user_id: ''
            }
        }
    },




    methods: {

        getMoreInfo() {
            console.log("ive been clicked")
            this.$router.push( { name: 'MovieInfoView', params: {id: 1}});
        },

        getStarRating(movie_id) {
            // return genres.map(genre => genre.genre_name)
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