<template>
    Individual movie info

    <h1>{{ movie.title }}</h1>
</template>

<script>
import MovieService from '../services/MovieService';


export default {
    props: ['movie'],

    data() {
        return {

        }
    },

    methods: {

    }
}


</script>