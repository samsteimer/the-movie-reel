<template>
    <div id="movie-info-display">
        <div>
            <img id="poster" v-bind:src="movie.poster_path" alt="">
        </div>
        <div id="movie-details">
            <h2 id="movie-title">{{ movie.title }}</h2>
            <h3>(Released: {{ movie.release_date }})</h3>
            <h3>Overview</h3>
            <p>{{ movie.overview }}</p>
            <button id="back-btn" @click="$router.go(-1)" class="button-style" >Back To List</button>
            <div id="rev-container">
                <div v-if="$store.state.token != ''">
                <button class="button-style" v-if="!favoriteMovieIds.includes(movie.movie_id)" @click.prevent="addFavoriteMovie(movie.movie_id)">Add to WatchList</button>
                <button class="button-style" v-else @click.prevent="removeFavoriteMovie(movie.movie_id)">Remove from WatchList</button>
                </div>
                <button id="delete-button" class="button-style" v-if="$store.state.isAdmin" @click.prevent="deleteMovie">Delete Movie</button>
                <h2 id="Review-label">Reviews</h2>
                <Review id="reviews" v-for="review in reviews" v-bind:key="review.reviewId" v-bind:review="review"></Review>
                
                <div>
                    <button v-if="$store.state.token != ''" class="button-style" id="add-review-btn" @click="toggleShowForm">Add a review</button>
                    <button v-else @click="$router.push({ name: 'login'})" class="button-style ">Login to Leave A Review</button>
                </div>
                <form id="review-add-form" v-show="showForm">
                    <div id="review-head">
                    <label for="movie-review-text">Enter Review:</label>
                    <textarea v-model="review.movieReview" name="movie-review-text" id="review-text" cols="100" rows="5" ></textarea>
                    </div>
                    <div class="star-selector">
                        <div class="stars">
                            <img v-for="(star, index) in 5" 
                                @click="setStarRating(star)" 
                                :src="star <= review.starRating ? filledStar : emptyStar"
                                :key="index" alt="star">
                        </div>
                    </div>
                    <div class="star-selector">
                        <select id="selector" v-model="review.starRating">
                            <option v-for="rating in ratings" :value="rating.value" :key="rating.value">
                                {{ rating.label }}
                            </option>
                        </select>
                    </div>
                    <div id="review-button">
                        <button class="button-style" v-on:click.prevent="addMovieReview"  id="submit-review-btn">Save</button>
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

#reviews {
    margin-bottom: 20px;
}

#review-head {
    display: flex;
    flex-direction: column;
    font-weight: 500;
    font-size: 1.2rem;
    margin-bottom: 20px;
}

#rev-container {
    margin-top: 15px;
    margin-bottom: 30px;
}

#back-btn {
    margin-top: 15px;
}

#delete-button {
    margin-top: 15px;
}

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

#movie-details>h2 {
    font-size: 3rem;
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

h2#Review-label{
    font-size: 55px;
    font-family: 'League Spartan', sans-serif;
    font-weight: bold;
    color: #ffb300;
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

#selector {
    font-size: 1.4rem;
    font-family: 'League Spartan', sans-serif;
    color: black;
    background-color: white;
}
.star-selector {
    display: flex;
    align-items: center;
    margin-bottom: 15px;
}

.stars img {
    width: 30px;
    height: 30px;
}

</style>