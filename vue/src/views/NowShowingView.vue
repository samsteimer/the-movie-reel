<template>
    <div id="now-showing-header">
    <img id="now-showing" src="../assets/NowShowing.png" alt="Now-Showing">
    </div>

    <div id="movie-card">
        <MovieCard v-for="movie in movies" :key="movie.movie_id" :movie="movie" />

    </div>
</template>

<script scoped>
import MovieService from '../services/MovieService';
import MovieCard from '../components/MovieCard.vue';


export default {

    data() {
        return {
            movies: []
        }
    },

    components: {
        MovieCard
    },

    created() {
        let genreId = this.$route.params?.genreId;
        if (!genreId) {
            this.$router.back();
        }
        if (genreId) {
            MovieService.getMoviesByGenreId(genreId).then(res => {
                if (res.status == 200 && res.data) {
                    this.movies = res.data;
                }
            });
        }
    }

}

</script>

<style>

#now-showing-header {
    display: flex;
    justify-content: center;
}

#now-showing {
    display: flex;
    width: 35em;
    margin-bottom: 50px;
}

#movie-card {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
}


</style>np