<template>
    <div class="review-text">"{{review.movieReview}}"</div>
    <br>
    <img v-for="i in Array(review.starRating)" :key="i" src="/src/assets/star.png" alt="star_rating" class="star-rating">
    <!-- <div class="review-user">{{ review.userId }}</div> -->
</template>

<script scoped>

export default {
    props: ['review'],
    data() {
        return {
            // review: {
            //     "movieReview": "Its Star Wars.... say no more",
            //     "starRating": 4,
            //     "movieId": 2,
            //     "userId": 1,
            //     "reviewId": 4
            // }
        }
    }
    
}

</script>