<template>
    <div id="movie-card">
        <router-link v-bind:to="{ name: 'MovieInfoView', params: { id: movie.movie_id}}">
        <img id="movie-image" :src="movie.poster_path"></router-link>
        <button class="button-style" v-on:click="getMoreInfo">See Movie Info</button>
        <slot></slot>
    </div>
</template>

<script>

export default {
    props: ['movie'],


    methods: {
        
        getMoreInfo(movie) {
            const movieId = parseInt(this.movie.movie_id)
            this.$router.push( { name: 'MovieInfoView', params: {id: movieId}});
        }
    }

}

</script>

<style scoped>

#movie-card {
    display: flex;
    flex-direction: column;
    width: 200px;
    margin-right: 20px;
    margin-left: 20px;
}
#movie-card > h2 {
    align-items: center;
}

#movie-card > img {
    border: 2px solid white;
    border-radius: 5px;
}

#movie-image:active {
    transform: scale(.98)
}


#movie-card > button:hover {
    cursor: pointer;
}

#movie-image {
    margin-top: 20px;
    border: 2px solid white;
    border-radius: 5px;
    box-shadow: 2px 2px 5px black;
}

</style>