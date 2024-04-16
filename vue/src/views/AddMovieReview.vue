<template>
     <MovieInfo/>
    <h1>Add Movie Review</h1>
    <form id="review-add-form">
        <div id="review-text">
            <label for="input-review-text">Enter Review:</label>
            <textarea id="input-text" type="text" name="input-text" v-model="user.first_name"></textarea>
        </div>
        <div id="star-rating">
            <label for="star-rating">Star Rating</label>
            <input id="drop-down-star-rating" type="drop-down" name="drop-down-star-rating" v-model="user.last_name" />
        </div>
        <div id="review-button">
            <router-link v-bind:to="{ name: 'MovieInfoView', params: { id: review.movieId}}"><img id="movie-image" v-bind:src="movies.poster_path"></router-link>
            <button v-on:click="getMoreInfo" @click.prevent="submitUpdate">Save</button>
        </div>
    </form>
</template>

<script scoped>

import MovieInfo from '../components/MovieInfo.vue';
import UserService from '../services/UserService';
import MovieReviewService from '../services/MovieReviewService';

export default {
    data() {
        return {
            review: {
                "movieReview": "Its Star Wars.... say no more",
                "starRating": 4,
                "movieId": 2,
                "userId": 1,
                "reviewId": 4
            }
        
        }
    },

    methods: {
        submitUpdate() {
            MovieReviewService.createMovieReview(this.review).then(res => {
                this.review = res.data;
            })
        },

        getMoreInfo(review) {
            const movieId = parseInt(this.review.movie_id)
            this.$router.push( { name: 'MovieInfoView', params: {id: movieId}});
        }
    },

    created() {
        //TODO ask MT what the below item is for.
        // update state object with user
        let {id, username, first_name, last_name, bio, genres} = {...this.$store.state.user};
        this.user = {id, username, first_name, last_name, bio, genres: genres.map(g => g.genre_id)};

        GenreService.getGenres().then(res => this.genres = res.data);
    },

    components: {
        MovieInfo
    },

}

</script>

<style scoped>

h1 {
    margin-left: 155px;
    margin-bottom: 10px;
    color: white;
    text-shadow: 2px 2px 5px black;
    font-weight: 700;
    font-size: 2.5em;
}

#profile-form {
    margin-left: 130px;
    width: 60%;
    display: grid;
    pad: 0.35em;
    grid-template-columns: 2fr 3fr;
    grid-template-areas: 
        "firstname bio"
        "lastname bio"
        "genres genres"
        "buttons buttons";
}

#profile-form > div {
    margin-left: 1em;
    margin-top: 1em;
}

#profile-form label {
    display: block;
    padding-left: 0.5em;
    font-size: 1.15em;
}

#profile-form input[type=text],
#profile-form textarea {
    width: 100%;
    padding: 0.75em;
    border: 2px solid #000;
    border-radius: 0.75em;
}

#profile-firstname {
    grid-area: firstname,
}

#profile-lastname {
    grid-area: lastname;
}

#profile-bio {
    grid-area: bio;
}

#profile-bio textarea {
    height: 10em;
}

#profile-genres {
    grid-area: genres;
}

#profile-genres input {
    margin-right: 0.5em;
}

#profile-genres span {
    margin-left: 1em;
    margin-top: 1em;
    display: inline-block;
}

#profile-buttons button {
    margin: 0.75em 0;
    background-color: #ffb62e;
    border: none;
    border-radius: 1.5em;
    padding: 0.35em 1em;
    font-size: 1.15em;
    cursor: pointer;
}

</style>