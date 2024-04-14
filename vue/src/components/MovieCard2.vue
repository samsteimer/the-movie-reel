<template>
    <div id="movie-card">
        <img id="movie-image" :src="movie.posterPath">
        <h2>{{ movie.title }}</h2>
        <slot></slot>
    </div>
</template>

<script>

export default {
    props: ['movie'],
}

</script>

<style scoped>

#movie-card {
    width: 200px;
    min-width: 200px;
    margin: 0.5em;
}

#movie-card > h2 {
    margin: 0.25em;
    align-items: center;
    font-size: 1.25em;
    font-weight: 600;
    text-align: center;
}

#movie-image {
    display: block;
    min-height: 300px;
    border: 0.15em solid white;
    border-radius: 0.5em;
}

</style>