<template>
    Admin View - allow admin to add or remove movies
    <div>
    <form class="add-movie-form" v-on:submit.prevent="addMovie">
        <input class="title-input" placeholder="Title" type="text" v-model="newMovie.title">
        <input class="poster-input" placeholder="Poster URL" type="text" v-model="newMovie.poster_path">
        <input class="overview-input" placeholder="Overview" type="text" v-model="newMovie.overview">
        <input class="release-input" placeholder="Release Date" type="text" v-model="newMovie.release_date">
        <label for="genre">Choose a genre</label>
        <select id="genre" name="genre" v-model="selectedGenres">
            <option v-for="genre in genres" :key="genre.genreId" :value="genre.genreId">{{ genre.genreName }}</option>
        </select>
        <input type="submit" value="Save">
    </form>
    </div>
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
                { genreId: 1, genreName: 'Thriller' },
                { genreId: 2, genreName: 'Sci-Fi' },
                { genreId: 3, genreName: 'Drama' },
                { genreId: 4, genreName: 'Comedy' },
                { genreId: 5, genreName: 'Action' },
                { genreId: 6, genreName: 'Documentary' },
                { genreId: 7, genreName: 'Romance' },
                { genreId: 8, genreName: 'Animated' },
                { genreId: 9, genreName: 'Family' },

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
        genreId: this.selectedGenres,
        genreName: this.genres.find(genre => genre.genreId === this.selectedGenres).genreName
    });
                
       

    console.log('New Movie:', this.newMovie);

    // Call movieService.createMovie to create the movie
    movieService.createMovie(this.newMovie)
        .then(response => {
            console.log("Movie created successfully:", response.data);
            
            // Call the addGenreToMovie method after creating the movie
            movieService.addGenreToMovie(this.newMovie)
                .then(() => {
                    console.log('Genres added successfully');
                    
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
                    console.error('Error adding genres:', error);
                });
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
            this.newMovie.genres = movie.genres;


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