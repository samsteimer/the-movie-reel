import axios from "axios";

export default {


  getMovieReviewByMovieId(movieId) {
    return axios.get("/movies/" + movieId + "/reviews");
  }, // /<movieId>/reviews

  getMovieReviewById(reviewId) {
    return axios.get("/movies/review/" + reviewId);
  }, // /movies/<reviewId>

  createMovieReview(movieReview) {
    return axios.post("/movies/reviews", movieReview);
  }, // POST /reviews

};