import axios from "axios";
//import MovieReviewService from "./MovieReviewService";

export default {


  getMovieReviewByMovieId(movieId) {
    return axios.get("/" + movieId + "/reviews");
  }, // /<movieId>/reviews

  getMovieReviewById(reviewId) {
    return axios.get("/review/" + reviewId);
  }, // /movies/<reviewId>

  createMovieReview(movieId) {
    return axios.post("/reviews");
  }, // POST /reviews
};