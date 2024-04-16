<template>

<div id="genre-header">
    <h1 id="head-text">Choose Your Favorite Genres</h1>
</div>

    <div id="genre-list">
        <ul>
            <li v-for="genre in genres" :key="genre.genre_id" @click="toggleSpotlight($event, genre.genre_id)">
                <!-- <img id="spotlight" src="../assets/YellowSpotlight.png" alt="Spotlight">  -->
                <span>{{ genre.genre_name }}</span>
            </li>
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

    toggleSpotlight(event, genreId) {
        event.currentTarget.classList.toggle('active');
        this.$router.push({name: 'NowShowing', params: {genreId: genreId}})
    }

},

created() {
    GenreService.getGenres().then(res => {
        this.genres = res.data;
    })
}

}
</script>


<style scoped>

li {
    position: relative;
}

/* #spotlight {
    display: none;
} */

/* .active #spotlight {
} */



#spotlight {
    display: block;
    /* position: absolute;
    top: 50%; 
    left: 50%;  */
    /* transform: translate(-50%, -50%);  */
    width: 125px;
    min-width: 125px; 
}

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
    width: 95%;
    margin: 0 auto;
    min-width: 500px;
    text-align: center;
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    align-items: center;
}

#genre-list > ul > li {
    display: flex;
    align-items: flex-end;
    justify-content: center;
    text-align: center;
    width: 135px;
    height: 105px;
    margin-top: 35px;
    margin-right: 40px;
    /* white-space: nowrap; */
    font-size: 1.6rem;
    text-shadow: 2px 2px 5px black;
    font-weight: 700;
}

#genre-list > ul > li:hover {
    cursor: pointer;
    font-size: 190%;
    background-image: url('../assets/YellowSpotlight.png');
    background-position: center;
    background-repeat: no-repeat;
    background-size: contain;
}

#genre-list > ul > li:active {
    transform: scale(.98);
}







</style>