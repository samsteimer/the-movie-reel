import axios from "axios";
//import MovieReviewService from "./MovieReviewService";

export default {


  getMovieReviewByMovieId(movieId) {
    return axios.get("/movies/" + movieId + "/reviews");
  }, // /<movieId>/reviews

  getMovieReviewById(reviewId) {
    return axios.get("/movies/review/" + reviewId);
  }, // /movies/<reviewId>

  createMovieReview(movieId) {
    return axios.post("/movies/reviews");
  }, // POST /reviews
};