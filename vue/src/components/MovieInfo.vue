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
            <div v-if="$store.state.token != ''">
            <button class="button-style" v-if="!favoriteMovieIds.includes(movie.movie_id)" @click.prevent="addFavoriteMovie(movie.movie_id)">Add to WatchList</button>
            <button class="button-style" v-else @click.prevent="removeFavoriteMovie(movie.movie_id)">Remove from WatchList</button>
            <h2 id="Review-label">Reviews</h2>
            <Review v-for="review in reviews" v-bind:key="review.reviewId" v-bind:review="review"></Review>
            <br>
            <br>
            <div>
                <button>Add a review</button>
            </div>
    <form id="review-add-form">
        <!-- <div id="review-text"> -->
            <label for="input-review-text">Enter Review:</label>
        <textarea  name="movie-review-text" id="review-text" cols="100" rows="5" value = "Add your review" onfocus="this.value=''"> </textarea>

        <div class="star-selector">
            <StarSelector></StarSelector>
        </div>
        <div id="review-button">
            <button v-on:click="getMoreInfo" @click.prevent="submitUpdate">Save</button>
        </div>
    </form>
        </div>        
    </div>
    </div>

</template>


<script>
import MovieService from '../services/MovieService';
import UserService from '../services/UserService';
import Review from '../components/Review.vue';
import MovieReviewService from '../services/MovieReviewService';
import StarSelector from '../components/StarSelector.vue'



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
            favoriteMovieIds: [],
            reviews:[],
            mockData: {
                field: "Five Stars",
                field2: "World"
            },
   
        }
    },

    methods: {
        addFavoriteMovie(movieId) {
            UserService.addFavoriteMovie(movieId).then(res => {
                if (res.status == 200) {
                    this.favoriteMovieIds.push(res.data.movie_id);
                }
            })
        },
        removeFavoriteMovie(movieId) {
            UserService.removeFavoriteMovie(movieId).then(res => {
                if (res.status == 200) {
                    this.favoriteMovieIds = this.favoriteMovieIds.filter(m => m != movieId);
                }
            })
        }

    },

    components: {
        Review, StarSelector
        
    },

    created() {
        const movieId = this.$route.params.id
        MovieService.getMovieByMovieId(movieId).then (res => {
            this.movie = res.data;
        });

        UserService.getFavoriteMovies().then(res => {
            if (res.data) {
                this.favoriteMovieIds = res.data?.map(f => f.movie_id);
            }
        });

        const movieIdForReview = this.$route.params.id;
        MovieReviewService.getMovieReviewByMovieId(movieIdForReview).then(res=> {
            this.reviews = res.data;
            //console.log(res.data);
        });
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
    margin-top: 15px;
}


#movie-details button {
    margin: 0.75em 0;
    background-color: #ffb62e;
    border: none;
    border-radius: 1.5em;
    padding: 0.35em 1em;
    font-size: 1.15em;
    cursor: pointer;
}

h2#Review-label{
    font-size: 65px;
    font-weight: bold;
    color: yellow;
}

   

.mock {
    width: 300px;
    margin: 1em 0;
    padding: 1em;
    border: 2px solid black;
    border-radius: 2em;
}

#review-text{
    /* width: 100%; */
  height: 150px;
  padding: 12px 20px;
  box-sizing: border-box;
  border: 2px solid #ccc;
  border-radius: 4px;
  background-color: #f8f8f8;
  font-size: 16px;
  resize: none;
  box-shadow: none;
}

</style>