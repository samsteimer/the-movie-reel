import axios from "axios";

export default {

    getMovieByMovieId(id) {
        return axios.get('/movie_info/' + id);
    }
}