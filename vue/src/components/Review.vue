<template>
    <div class="review-text">"{{review.movieReview}}"</div>
    
    <img id="star-review" v-for="i in Array(review.starRating)" :key="i" src="/src/assets/star.png" alt="star_rating" class="star-rating">
    <br>
</template>

<script scoped>

export default {
    props: ['review'],
    data() {
        return {

        }
    }
    
}

</script>

<style scoped>

.star-rating {
    height: 40px;
    max-width: 100%;
}

#star-review {
    margin-bottom: 30px;
}


</style>