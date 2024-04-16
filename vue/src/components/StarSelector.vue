<template>
  <br>
      <div class="stars">
      <img v-for="(star, index) in 5" :src="star <= selectedRating ? filledStar : emptyStar" :key="index" alt="star">
    </div>
  <div class="star-selector">
    <select v-model="selectedRating">
      <option v-for="rating in ratings" :value="rating.value" :key="rating.value">
        {{ rating.label }}
      </option>
    </select>

  </div>
</template>

<script>
export default {
  data() {
    return {
      selectedRating: 1,
      filledStar: '/src/assets/star.png',
      emptyStar: '/src/assets/emptyStar.png',
      ratings: [
        { value: 1, label: '1 Star' },
        { value: 2, label: '2 Stars' },
        { value: 3, label: '3 Stars' },
        { value: 4, label: '4 Stars' },
        { value: 5, label: '5 Stars' }
      ]
    };
  }

};
</script>

<style scoped>

.star-selector {
  display: flex;
  align-items: center;
}

.stars img {
  width: 30px; 
  height: 30px;
}
</style>
