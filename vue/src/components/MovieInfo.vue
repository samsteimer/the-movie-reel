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
            <button class="button-style" v-if="$store.state.isAdmin" @click.prevent="deleteMovie">Delete Movie</button>
                <h2 id="Review-label">Reviews</h2>
                <Review v-for="review in reviews" v-bind:key="review.reviewId" v-bind:review="review"></Review>
                <br>
                <br>
                <div>
                    <button id="add-review-btn" @click="toggleShowForm">Add a review</button>
                </div>
                <form id="review-add-form" v-show="showForm">
                    <label for="movie-review-text">Enter Review:</label>
                    <textarea v-model="review.movieReview" name="movie-review-text" id="review-text" cols="100" rows="5" ></textarea>
                    <div class="star-selector">
                        <div class="stars">
                            <img v-for="(star, index) in 5" 
                                @click="setStarRating(star)" 
                                :src="star <= review.starRating ? filledStar : emptyStar"
                                :key="index" alt="star">
                       <br>
                            </div>
                        <br>
                    </div>
                    <div class="star-selector">
                            <select v-model="review.starRating">
                                <option v-for="rating in ratings" :value="rating.value" :key="rating.value">
                                    {{ rating.label }}
                                </option>
                            </select>
                        </div>
                        <br>
                    <div id="review-button">
                        <button v-on:click.prevent="addMovieReview"  id="submit-review-btn">Save</button>
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


export default {
    data() {
        return {
            showForm: false,
            //textBoxValue: "",
            filledStar: '/src/assets/star.png',
            emptyStar: '/src/assets/emptyStar.png',
            movie: {
                title: '',
                overview: '',
                genres: [],
                poster_path: '',
                release_date: ''
            },
            favoriteMovieIds: [],
            reviews: [],
            ratings: [
                { value: 1, label: '1 Star' },
                { value: 2, label: '2 Stars' },
                { value: 3, label: '3 Stars' },
                { value: 4, label: '4 Stars' },
                { value: 5, label: '5 Stars' }
            ],
            review: {
                movieReview: "",
                starRating: 5,
                movieId: 0,
                userId: 0
            }
        }
    },

    methods: {
        setStarRating(star) {
            this.review.starRating = star;
        },

        toggleShowForm() {
            this.showForm = !this.showForm
        },

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
        },
        addMovieReview(){
            MovieReviewService.createMovieReview(this.review).then(res =>{
                if (res.status == 200) {
                    this.reviews.push(res.data);
                    this.showForm = false;
                    this.review = {
                        movieReview: "",
                        starRating: 5,
                        movieId: 0,
                        userId: 0
                    }
                }
            })
        },

        deleteMovie() {
            MovieService.deleteMovieById(this.movie.movie_id).then(res => {
                if (res.status == 200) this.$router.push({name: 'home'});
            });
        },
        clearText(event) {
            event.target.value = '';
        }
    },

    components: {
        Review

    },

    created() {
        const movieId = this.$route.params.id;
        
        this.review.userId = this.$store.state.user.id;
        this.review.movieId = parseInt(movieId);

        MovieService.getMovieByMovieId(movieId).then(res => {
            this.movie = res.data;
        });

        UserService.getFavoriteMovies().then(res => {
            if (res.data) {
                this.favoriteMovieIds = res.data?.map(f => f.movie_id);
            }
        });

        const movieIdForReview = this.$route.params.id;
        MovieReviewService.getMovieReviewByMovieId(movieIdForReview).then(res => {
            this.reviews = res.data;
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

#movie-details>h1 {
    font-size: 3rem;
}

#movie-details>h2 {
    font-size: 1.5rem;
    margin-bottom: 20px;
}

#movie-details>h3 {
    font-size: 1.3rem;
}

#movie-details>p {
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

h2#Review-label {
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

#review-text {
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

.star-selector {
    display: flex;
    align-items: center;
}

.stars img {
    width: 30px;
    height: 30px;
}

#add-review-btn {
    margin: 0.75em 0;
    background-color: #ffb62e;
    box-shadow: 2px 2px 5px black;
    border-radius: 1.5em;
    padding: 0.35em 1em;
    font-size: 1.15em;
    cursor: pointer;
    color: white;
    text-shadow: 2px 2px 5px black;
}

#submit-review-btn{
    margin: 0.75em 0;
    background-color: #ffb62e;
    box-shadow: 2px 2px 5px black;
    border-radius: 1.5em;
    padding: 0.35em 1em;
    font-size: 1.15em;
    cursor: pointer;
    color: white;
    text-shadow: 2px 2px 5px black; 
}
</style>