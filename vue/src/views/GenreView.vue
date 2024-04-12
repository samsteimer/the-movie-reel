<template>

<div id="genre-header">
    <h1 id="head-text">Choose Your Favorite Genres</h1>
</div>

    <div id="genre-list">
        <ul>Action</ul>
        <ul>Comedy</ul>
        <ul>Sci-Fi</ul>
        <ul>Thriller</ul>
        <ul>Drama</ul>
        <ul>Romance</ul>
    </div>

</template>

<style>

#genre-header {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

#head-text {
  display: flex;
  color: white;
  font-family: "League Spartan", sans-serif;
  justify-content: center;
  text-shadow: 2px 2px 5px black;
  font-weight: 700;
  font-size: 50px;
  margin-top: 30px;
}

#genre-list {
    margin-top: 35px;
    display: flex;
    justify-content: space-around;
    font-size: 1.6rem;
    text-shadow: 2px 2px 5px black;
    font-weight: 700;
}

#genre-list > ul:hover {
    cursor: pointer;
    font-size: 110%;
}








</style>