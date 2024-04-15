<template>
    
    <form id="add-movie-form" v-on:submit.prevent="addMovie">
        <div>
            <input class="title-input" placeholder="Title" type="text" v-model="newMovie.title">
        </div>
        <div>
            <input class="poster-input" placeholder="Poster URL" type="text" v-model="newMovie.poster_path">
        </div>
        <div>
            <input class="overview-input" placeholder="Overview" type="text" v-model="newMovie.overview">
        </div>
        <div>
            <input class="overview-input" placeholder="Overview" type="text" v-model="newMovie.overview">
        </div>
        <div>
            <input class="release-input" placeholder="Release Date" type="text" v-model="newMovie.release_date">
        </div>
        <div>
            <label for="genre">Choose a genre</label>
            <select id="genre" name="genre" v-model="selectedGenres">
            <option v-for="genre in genres" :key="genre.genre_id" :value="genre.genre_id">{{ genre.genre_name }}</option>
        </select>
        </div>

        
        
       
        
        <input type="submit" value="Save">
    </form>
   
    <div>
        <h1>Search for a movie</h1>
        <input class="search-input" type="text" v-model="searchTitle" @keyup.enter="search()" >
    </div>

    <div v-if="searchResults && searchResults.results && searchResults.results.length > 0">
        <h2>Search Results</h2>
        <ul>
            <li v-for="movie in searchResults.results" :key="movie.id">
                <h3>{{ movie.title }}</h3>
                <p>{{ movie.overview }}</p>
                <p>Release Date: {{ movie.release_date }}</p>
                <img v-if="movie.poster_path" :src="movie.poster_path" alt="Movie Poster" style="max-width: 200px;">
                <span v-else>No poster available</span>
                <label for="genre">Choose a genre</label>
                <select id="genre" name="genre" v-model="selectedGenres">
                    <option v-for="genre in genres" :key="genre.genre_id" :value="genre.genre_id">{{ genre.genre_name }}</option>
                </select>
                
                <button @click="addMovieFromApi(movie)">Add Movie</button>
            </li>
        </ul>
    </div>
</template>

<script>
import movieService from '../services/MovieService';

export default {
    data() {
        return {
            newMovie: {
                title: '',
                overview: '',
                genres: [],
                poster_path: '',
                release_date: ''         
            },

            genres: [
                { genre_id: 1, genre_name: 'Thriller' },
                { genre_id: 2, genre_name: 'Sci-Fi' },
                { genre_id: 3, genre_name: 'Drama' },
                { genre_id: 4, genre_name: 'Comedy' },
                { genre_id: 5, genre_name: 'Action' },
                { genre_id: 6, genre_name: 'Documentary' },
                { genre_id: 7, genre_name: 'Romance' },
                { genre_id: 8, genre_name: 'Animated' },
                { genre_id: 9, genre_name: 'Family' },

            ],

            selectedGenres: [],

            searchTitle: '',
            searchResults: {
                results: []
            }
        }
    },

    methods: {

        addMovie(movie) {
            // Clear the existing genres array in newMovie
            this.newMovie.genres = [];

            console.log("Selected Genres:", this.selectedGenres);
            console.log('Genres:', JSON.stringify(this.genres) );
            this.newMovie.genres.push({
                genre_id: this.selectedGenres,
                genre_name: this.genres.find(genre => genre.genre_id === this.selectedGenres).genre_name
            });

            console.log('New Movie:', this.newMovie);

            // Call movieService.createMovie to create the movie
            movieService.createMovie(this.newMovie)
                .then(response => {
                    console.log("Movie created successfully:", response.data);            
                            // Reset newMovie object after adding the movie and genres
                            this.newMovie = {
                                title: '',
                                overview: '',
                                genres: [],
                                poster_path: '',
                                release_date: ''  
                            };
                            this.selectedGenres = [];
                      
                })
                .catch(error => {
                    console.error('Error creating movie:', error);
                });
        },

        addMovieFromApi(movie) {
            this.newMovie.title = movie.title;
            this.newMovie.poster_path = movie.poster_path;
            this.newMovie.overview = movie.overview;
            this.newMovie.release_date = movie.release_date;
           
            console.log("Added movie:", this.newMovie)
            this.addMovie();
        },

        search() {
            movieService.searchMoviesFromApi(this.searchTitle).then(response => {
                console.log("Search response:", response);
                this.searchResults = response.data;
                
            }).catch(error => {
                console.error('Error searching movies:', error)
            });
        },
        
    }
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

#add-movie-form {
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

#add-movie-form > div {
    margin-left: 1em;
    margin-top: 1em;
}

#add-movie-form label {
    display: block;
    padding-left: 0.5em;
    font-size: 1.15em;
}

#add-movie-form input[type=text],
#add-movie-form textarea {
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