import axios from "axios";

export default {
  getGenres() {
    return axios.get("/genre");
  },
};
