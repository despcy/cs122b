<template>
  <div class="hello">
<h1>Hello, {{info.admin}}</h1>

<div>
<el-divider content-position="left"><h2>Insert Movie</h2></el-divider>
<el-form label-width="120px" >
  <el-form-item label="Movie Title" required>
    <el-input v-model="movie.title"></el-input>
  </el-form-item>
  <el-form-item label="Movie Year" required>
    <el-input v-model="movie.year"></el-input>
  </el-form-item>
  <el-form-item label="Movie Director" required>
    <el-input v-model="movie.director"></el-input>
  </el-form-item>
  <el-form-item label="Movie Star" required>
    <el-input v-model="movie.star"></el-input>
  </el-form-item>
  <el-form-item label="Movie Genre" required>
    <el-input v-model="movie.genre"></el-input>
  </el-form-item>
  <el-form-item>
    <el-button type="primary" @click="submitMovieForm()">Submit</el-button>
  </el-form-item>
</el-form>
</div>
<div>
<el-divider content-position="left"><h2>Insert Star</h2></el-divider>
<el-form label-width="120px" >
  <el-form-item label="Star Name" required>
    <el-input v-model="star.name"></el-input>
  </el-form-item>
  <el-form-item label="Star Birthday" >
    <el-input v-model="star.birth"></el-input>
  </el-form-item>
  <el-form-item>
    <el-button type="primary" @click="submitStarForm()">Submit</el-button>
  </el-form-item>
</el-form>
</div>
<div>
<el-divider content-position="left"><h2>DB INFO</h2></el-divider>
<div v-for="(table,idx) in info.tables" v-bind:key="idx">
  <h2>{{table.name}}: </h2>
    <el-table
      :data="table.attr"
      style="width: 100%; margin-top: 20px">
      <el-table-column
        prop="name"
        label="Field">
      </el-table-column>
      <el-table-column
        prop="type"
        label="Type">
      </el-table-column>

    </el-table>

  </div>
</div> 
<el-button type="danger" @click="logout()">Logout</el-button>
  </div>
</template>


<script>

export default {
  name: 'HelloWorld',
  data: function(){
    return {
      info:{},
      star:{
        name:"",
        birth:""
      },
      movie:{
        title:"",
        year:"",
        director:"",
        star:"",
        genre:""
      }
    }
  },
  mounted() {
    this.axios.get('/api/dash/show').then(
      response=>{
        if(response.data.message==0){
          this.info=response.data.data;
        }else if(response.data.message==-1){
          alert("auth error!");
        }else{
          alert(response.data.data);
        }
      }
    )

  },
  methods: {
      submitMovieForm: function(){
        this.axios.post('/api/dash/addMovie',null,{params:{
                title:this.movie.title,
                year:this.movie.year,
                director:this.movie.director,
                star:this.movie.star,
                genre:this.movie.genre
        }}).then(
                response=>{
                    if(response.data.message==0){
                        alert("Success!");
                                this.movie.title="";
        this.movie.year="";
        this.movie.director="";
        this.movie.star="";
        this.movie.genre="";
                    }else if(response.data.message == -1){
            alert('Auth Fail '+response.data.data);
          }else{
            alert(response.data);
          }   })     


      },
      submitStarForm: function(){
        this.axios.post('/api/dash/addStar',null,{params:{
                name:this.star.name,
                birth:this.star.birth,
      
        }}).then(
                response=>{
                    if(response.data.message==0){
                        alert("Success!");
        this.star.name="";
        this.star.birth="";
                    }else if(response.data.message == -1){
            alert('Auth Fail '+response.data.data);
          }else{
            alert(response.data.data);
          }   }) 

      },
      logout(){
  this.axios.get('/api/logout').then(
    response=>{
      if(response.data.message==-1){
        window.location.href = "/login";
      }else{
        alert("Logout Failed!");
      }
    }
  )
}
  },
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
