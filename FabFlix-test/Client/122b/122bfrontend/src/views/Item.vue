<template>
    <div v-if="$route.params.type === 'movie'">

<el-card class="box-card" shadow="always">
  <div slot="header" class="clearfix">
    <span><h1>{{movieData.title}}</h1></span>
  </div>
  <div class="text item">
   <h4>Year:&nbsp;&nbsp;&nbsp;&nbsp;{{movieData.year}}</h4>  
  </div>
    <div class="text item">
   <h4>director:&nbsp;&nbsp;&nbsp;&nbsp;{{movieData.director}}</h4>  
  </div>

  <div class="text item">
   <h4>Rating:&nbsp;&nbsp;&nbsp;&nbsp;{{movieData.rating}}</h4>  
  </div>
    <div class="text item">

   <el-divider content-position="left"><h4>Stars:</h4></el-divider>
   <div v-for="(star, idx) in movieData.stars" v-bind:key="idx">
<el-link type="success"  @click="starClick(star.id)" ><h4>{{star.name}}</h4></el-link>
  
   </div>  


  </div>

    <div class="text item">
   <el-divider content-position="left"><h4>Genres:</h4></el-divider>
    <div v-for="(genre, idx) in movieData.genres" v-bind:key="idx">
<h4>{{genre}}&nbsp;</h4>

   </div>
  </div>

     <el-divider></el-divider>
           <el-button
          type="primary"
           round
          @click="handleAddCart()">ADD TO CART</el-button>
</el-card>

    </div>
    <div v-else-if="$route.params.type === 'star'">


<el-card class="box-card" shadow="always">
  <div slot="header" class="clearfix">
    <span><h1>{{starData.name}}</h1></span>
  </div>
    <div class="text item"><h4>BirthYear: {{starData.birthYear}}</h4></div>
   <el-divider content-position="left"><h4>Movies:</h4></el-divider>
   <div v-for="(movie, idx) in starData.movies" v-bind:key="idx">
<el-link type="success"  @click="movieClick(movie.id)" ><h4>{{movie.name}}</h4></el-link>

   </div> 
</el-card>
</div>
</template>

<script>

export default {
    name: 'Item',
    components:{

    },
    data: function(){
        return {
            movieData:{},
            starData:{}
        }
    },
    mounted() {
            this.requestData();

    },
    methods: {
        starClick(sid){
            this.$router.replace({name:'Item',params:{type:'star',id: sid}})
            this.requestData();
        },
         movieClick(mid){
            this.$router.replace({name:'Item',params:{type:'movie',id: mid}})
            this.requestData();
        },
        handleAddCart(){
            //post
            this.axios.post('/api/cart/add',{
                movieId:this.movieData.id,
                movieTitle:this.movieData.title
            }).then(
                response=>{
                    if(response.message==0){
                        alert("Success!");
                    }else if(response.message == -1){
            alert('Auth Fail '+response.data);
          }else{
            alert(response.data);
          }
                }
            )
        },
        requestData(){
                var url='';
    if(this.$route.params.type === 'movie'){
        url='/api/movie?movieId='+this.$route.params.query.id;
    }else{
        url='/api/star?starId='+this.$route.params.query.id;
    }

    this.axios.get(url).then(
        response=>{
          if(response.message == 0){
    if(this.$route.params.type === 'movie'){
       this.movieData=response.data;
    }else{
        this.starData=response.data;
    }
          }else if(response.message == -1){
            alert('Auth Fail '+response.data);
          }else{
            alert(response.data);
          }
        }
      )
        }
    }

}
</script>