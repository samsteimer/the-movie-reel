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
            <button v-if="!favoriteMovieIds.includes(movie.movie_id)" @click.prevent="addFavoriteMovie(movie.movie_id)">Add to WatchList</button>
            <button v-else @click.prevent="removeFavoriteMovie(movie.movie_id)">Remove from WatchList</button>
            <!-- <img id="add-button" src="../assets/AddToWatchlistButton.png"> -->
        </div>        
    </div>

</template>


<script>
import MovieService from '../services/MovieService';
import UserService from '../services/UserService';

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
            favoriteMovieIds: []
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

    created() {
        const movieId = this.$route.params.id
        MovieService.getMovieByMovieId(movieId).then (res => {
            this.movie = res.data;
        });

        UserService.getFavoriteMovies().then(res => {
            if (res.data) {
                this.favoriteMovieIds = res.data?.map(f => f.movie_id);
            }   
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

</style>