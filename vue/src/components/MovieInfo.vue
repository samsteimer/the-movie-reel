<template>

    <div id="movie-info-display">
        <div>
            <img id="poster" v-bind:src="movie.poster_path" alt="">
        </div>

        <div id="movie-details">
            
            <h1>{{ movie.title }}</h1>
            <h2>(Released: {{ movie.release_date }})</h2>
            <h3>Overview</h3>
            <p>{{ movie.overview }}</p>
            <img id="add-button" src="../assets/AddToWatchlistButton.png">

            <h2 id="Review-label">Reviews</h2>
            <Review v-for="review in reviews" v-bind:key="review.reviewId" v-bind:review="review"></Review>


        </div>

        
    </div>


</template>


<script>
import MovieService from '../services/MovieService';
import Review from '../components/Review.vue';
import MovieReviewService from '../services/MovieReviewService';


export default {
    data() {
        return {
            movie: {
                title: '',
                overview: '',
                genres: [],
                poster_path: '',
                release_date: ''
            },
             reviews:[]
        }
    },


    methods: {



    },

    components: {
        Review
    },

    created() {
        const movieId = this.$route.params.id
        MovieService.getMovieByMovieId(movieId).then (res => {
            this.movie = res.data;
        });


        const movieIdForReview = this.$route.params.id
        MovieReviewService.getMovieReviewByMovieId(movieIdForReview).then(res=> {
            this.reviews = res.data;
            //console.log(res.data);
        })
    }
}

</script>

<style>
#movie-info-display {
    display: flex;
    margin-top: 75px;
    margin-left: 75px;
}

#poster {
    width: 35rem;
    max-width: 300px;
    height: auto;
    border: 2px solid white;
    border-radius: 5px;
    box-shadow: 2px 2px 5px black;
}

#movie-details {
    margin-left: 30px;
    margin-right: 30px;
    text-shadow: 2px 2px 5px black;
}

#movie-details > h1 {
    font-size: 3rem;
}

#movie-details > h2 {
    font-size: 1.5rem;
    margin-bottom: 20px;
}

#movie-details > h3 {
    font-size: 1.3rem;
}

#movie-details > p {
    font-size: 1.1rem;
}

#add-button {
    width: 15rem;
}

#add-button:hover {
    cursor: pointer;

}

h2#Review-label{
    font-size: 65px;
    font-weight: bold;
    color: yellow;
}

#movie-details > img.star-rating{
    height: 40px;
    max-width: 100%;

}


</style>