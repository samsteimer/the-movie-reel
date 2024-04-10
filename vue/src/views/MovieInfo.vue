<template>
    Movie Info View - show movie info
    {{ movie.title }}
    
</template>

<script>
import movieService from '../services/MovieService';
import MovieCard from '../components/MovieCard.vue';

export default {

    data() {
        return {
            movie: {
                apiMoveId: 0,
                title: '',
                posterPath: '',
                backdropPath: '',
                imbdId: 0,
                homepage: '',
                overview: '',
                releaseDate: '',
                runtime: 0
            }
            
        }
    },

    components: {
        
    },

    created() {
        let id = parseInt(this.$route.params.id);
        movieService.getMovieByMovieId(id)
            .then(response => {
                this.movie = response.data;
            })

    },

}

</script>