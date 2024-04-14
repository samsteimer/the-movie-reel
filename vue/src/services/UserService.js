import axios from "axios";

export default {
  getUser() {
    return axios.get("/profile");
  },
  updateUser(user) {
    return axios.put("/profile", user);
  }, // PUT /profile (auth required)

  getFavoriteMovies() {
    return axios.get("/favorites");
  }, // /favorites (auth required)

  addFavoriteMovie(movieId) {
    return axios.put("/favorites/" + movieId);
  }, // PUT /favorites/<movieId>

  removeFavoriteMovie(movieId) {
    return axios.delete("/favorites/" + movieId);
  }, // DELETE /favorites/<movieId>
};
