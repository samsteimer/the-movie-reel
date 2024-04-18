<template>
    <h1>Add Custom Movie</h1>
    <form id="add-movie-form">
        <div id="add-title">
            <label for="title-input">Title</label>
            <input class="title-input" placeholder="Title" type="text" v-model="newMovie.title">
        </div>
        <div id="add-poster">
            <label for="poster-input">Poster URL</label>
            <input class="poster-input" placeholder="Poster URL" type="text" v-model="newMovie.poster_path">
        </div>
        <div id="add-release">
            <label for="release-input">Release Date</label>
            <input class="release-input" placeholder="Release Date" type="text" v-model="newMovie.release_date">
        </div>
        <div id="add-overview">
            <label for="overview-input">Overview</label>
            <textarea class="overview-input" placeholder="Overview" type="text" v-model="newMovie.overview"></textarea>
        </div>
        <div id="add-genre">
            <label for="genre-checkbox">Genres</label>
            <span v-for="genre in genres" :key="genre.genre_id" >
                <input name="genre" type="checkbox" :value="{ genre_id: genre.genre_id, genre_name: genre.genre_name}" v-model="selectedGenres">
                {{ genre.genre_name }}
            </span>
           

        </div>
        <div id="add-buttons">
            <button class="button-style" @click.prevent="addMovie">Save</button>
        </div>

    </form>
   
    <h1>Search for a Movie by Title</h1>
    
    <div id="search-movies">
        <input class="search-input" placeholder="Title" type="text" v-model="searchTitle" @keyup.enter="search()">
        <h2>Results ordered by popularity</h2>
    </div>
    
    <div id="returned-movies" v-if="searchResults && searchResults.results && searchResults.results.length > 0">
        
        <ul id="movie-info-display">
            <li v-for="movie in searchResults.results" :key="movie.id">
                
                <div id="full-movie">
                
                    <div id="image">
                        <img id="poster" v-if="movie.poster_path" :src="movie.poster_path" alt="Movie Poster" style="max-width: 200px;">
                        <span v-else>No poster available</span>
                    </div>

                    <div id="movie-details">
                        <h2>{{ movie.title }}</h2>
                        <h3>Released: {{ movie.release_date }}</h3>
                        <h3>Overview</h3>
                        <p>{{ movie.overview }}</p>
                    </div>
                    
                    <div id="api-genre">
                        <h2>Select Genres:</h2>
                        <label for="genre-checkbox">
                        <span v-for="genre in genres" :key="genre.genre_id" >
                            <input name="genre" type="checkbox" :value="{ genre_id: genre.genre_id, genre_name: genre.genre_name}" v-model="selectedGenres">
                            {{ genre.genre_name }}
                        </span>
                        </label>
                    </div>
                    <div id="add-button">
                        <button class="button-style" @click="addMovieFromApi(movie)">Add Movie</button>
                    </div>
                    
                </div>
                
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

            for (let i = 0; i < this.selectedGenres.length; i++) {
               this.newMovie.genres.push(this.selectedGenres[i]);
            }

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

                            alert('Movie added to database.');
                      
                })
                .catch(error => {
                    console.error('Error creating movie:', error);
                    // Reset newMovie after error
                    this.newMovie = {
                                title: '',
                                overview: '',
                                genres: [],
                                poster_path: '',
                                release_date: ''  
                            };
                            this.selectedGenres = [];
                    
                    alert('Error creating movie. Please try again.');

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
    margin-left: px;
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
        "title overview"
        "poster overview"
        "release overview"
        "genre genre"
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
#add-movie-form textarea,
#add-movie-form select,
#full-movie select {
    width: 100%;
    padding: 0.75em;
    border: 2px solid #000;
    border-radius: 0.75em;
}

#add-title {
    grid-area: title,
}

#add-poster {
    grid-area: poster,
}

#add-overview {
    grid-area: overview;
}

#add-overview textarea {
    height: 10em;
}

#add-genre {
    grid-area: genre;
}


#search-movies {
    margin-left: 130px;
    padding-bottom: 20px;
}

#search-movies input {
    width: 75%;
    padding: 0.75em;
    border: 2px solid #000;
    border-radius: 0.75em;
}

#returned-movies {
    display: flex;
    flex-direction: column;
    
}

#movie-info-display {
    display: flex;
    flex-direction: column;
    margin-top: 35px;
    margin-left: 75px;
}

#full-movie {
    display:grid;
    margin-bottom: 35px;
    grid-template-columns: 2fr 3fr;
    grid-template-areas: 
        "image movie-details"
        "api-genre movie-details"
        "api-add-movie movie-details";
    
}

#api-genre {
    grid-area: api-genre;
}

#api-add-movie {
    grid-area: api-add-movie; 
}

#poster {
    grid-area: image;
    width: 35rem;
    max-width: 300px;
    height: auto;
    border: 2px solid white;
    border-radius: 5px;
    box-shadow: 2px 2px 5px black;
}

#movie-details {
    grid-area: movie-details;
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

#add-genre span {
    margin-left: 1em;
    margin-top: 1em;
    display: inline-block;
}

#api-genre span {
    margin-left: 1em;
    margin-top: 1em;
    display: inline-block;
}


</style>