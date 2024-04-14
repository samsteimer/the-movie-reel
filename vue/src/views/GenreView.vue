<template>

<div id="genre-header">
    <h1 id="head-text">Choose Your Favorite Genres</h1>
</div>

    <div id="genre-list">
        <ul>
            <li v-for="genre in genres" :key="genre.genre_id">{{ genre.genre_name }}</li>
        </ul>
    </div>

</template>

<script>
import GenreService from '../services/GenreService';

export default {

data() {
    return {
        genres: [],
    }
},

methods: {

},

created() {
    GenreService.getGenres().then(res => {
        this.genres = res.data;
    })
}

}
</script>


<style>

#genre-header {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

#head-text {
  display: flex;
  color: white;
  font-family: "League Spartan", sans-serif;
  justify-content: center;
  text-shadow: 2px 2px 5px black;
  font-weight: 700;
  font-size: 50px;
  margin-top: 30px;
}

#genre-list > ul {
    display: flex;
    margin-right: 30px;
    justify-content: center;
}

#genre-list > ul > li {
    margin-top: 35px;
    margin-right: 40px;
    white-space: nowrap;
    font-size: 1.6rem;
    text-shadow: 2px 2px 5px black;
    font-weight: 700;
}

#genre-list > ul > li:hover {
    cursor: pointer;
    font-size: 190%;
}

#genre-list > ul > li: {
    color: yellow;
}







</style>