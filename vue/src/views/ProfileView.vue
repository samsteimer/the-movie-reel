<template>
    <h1>My Profile</h1>
    <form id="profile-form">
        <div id="profile-firstname">
            <label for="input-firstname">First Name</label>
            <input id="input-firstname" type="text" name="input-firstname" placeholder="First Name" v-model="user.first_name" />
        </div>
        <div id="profile-lastname">
            <label for="input-lastname">Last Name</label>
            <input id="input-lastname" type="text" name="input-lastname" placeholder="Last Name" v-model="user.last_name" />
        </div>
        <div id="profile-bio">
            <label for="input-bio">Bio</label>
            <textarea id="input-bio" name="input-bio" v-model="user.bio" placeholder="Bio" ></textarea>
        </div>
        <div id="profile-genres">
            <label for="input-genres">Favorite Genres</label>
            <span  v-for="genre in genres" :key="genre.genre_id" >
                <input name="input-genres" type="checkbox" :value="genre.genre_id" v-model="user.genres" /><i ></i>
                {{ genre.genre_name }}
            </span>
        </div>
        <div id="profile-buttons">
            <button class="button-style" @click.prevent="submitUpdate">Save</button>
        </div>
    </form>
</template>

<script scoped>

import GenreService from '../services/GenreService';
import UserService from '../services/UserService';

export default {
    data() {
        return {
            user: {
                id: 0,
                username: "",
                first_name: "",
                last_name: "",
                bio: "",
                genres: [],
            },
            genres: [],
        }
    },

    methods: {
        submitUpdate() {
            UserService.updateUser(this.user).then(res => {
                this.$store.commit("SET_USER", res.data);
            })
        }
    },

    created() {
        // update state object with user
        let {id, username, first_name, last_name, bio, genres} = {...this.$store.state.user};
        this.user = {id, username, first_name, last_name, bio, genres: genres.map(g => g.genre_id)};

        GenreService.getGenres().then(res => this.genres = res.data);
    }

}

</script>

<style scoped>


h1 {
    margin-left: 155px;
    margin-bottom: 10px;
    color: white;
    text-shadow: 2px 2px 5px black;
    font-weight: 700;
    font-size: 2.5em;
}

#profile-form {
    margin-left: 130px;
    width: 60%;
    display: grid;
    pad: 0.35em;
    grid-template-columns: 2fr 3fr;
    grid-template-areas: 
        "firstname bio"
        "lastname bio"
        "genres genres"
        "buttons buttons";
}

#profile-form > div {
    margin-left: 1em;
    margin-top: 1em;
}

#profile-form label {
    display: block;
    padding-left: 0.5em;
    font-size: 1.15em;
}

#profile-form input[type=text],
#profile-form textarea {
    width: 100%;
    padding: 0.75em;
    border: 2px solid #000;
    border-radius: 0.75em;
}

#profile-firstname {
    grid-area: firstname,
}

#profile-lastname {
    grid-area: lastname;
}

#profile-bio {
    grid-area: bio;
}

#profile-bio textarea {
    height: 10em;
}

#profile-genres {
    grid-area: genres;
}

#profile-genres input {
    margin-right: 0.5em;
}

#profile-genres span {
    margin-left: 1em;
    margin-top: 1em;
    display: inline-block;
}



</style>