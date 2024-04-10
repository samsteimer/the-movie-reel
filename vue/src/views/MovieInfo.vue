<template>
    Movie Info View - show movie info
    {{ movie.title }}
</template>

<script>
import MovieService from '../services/MovieService';
import MovieCard from '../components/MovieCard.vue';

export default {

    data() {
        return {
            movie: {}
        }
    },

    components: {
        
    },

    created() {
        MovieService.getMovieByMovieId(this.$route.param.id).then(response => {
            this.movie = response.data
        })
    },

    methods: {

    }

}

</script>