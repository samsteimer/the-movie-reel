<template>
    <div id="now-showing-header">
    <img id="now-showing" src="../assets/NowShowing.png" alt="Now-Showing">
    </div>
</template>

<script scoped>
import MovieService from '../services/MovieService';


export default {

    data() {
        return {
            movies: []
        }
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
}


</style>