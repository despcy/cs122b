<template>
  <div class="list">
<!-- //Browse By
//Page Number
//sort By

//table


//page
list -->

<el-row :span="30" type="flex">
  <el-col :span="20"><div class="grid-content">
      <el-pagination
      @size-change="onPageSizeChange"
      @current-change="onPageChange"
      :page-sizes="[10,25,50,100]"
      :current-page=this.page
      :page-size=this.pagesize
      hide-on-single-page:true
      background:true
      layout="sizes, prev, pager, next"
      :total=tableData.totalItem>
    </el-pagination>
    </div></el-col>
  <el-col :span="5" ><div class="grid-content" justify="end">
      <el-select v-model="sort" clearable:false @change="onSortChange"  placeholder="Browse By">
      <el-option
      label="Title then Rating"
      value="title_rating">
      </el-option>
       <el-option
      label="Rating then Title"
      value="rating_title">
      </el-option>
  </el-select>
  
    </div></el-col>


      <el-col :span="5" >
        <div class="grid-content" justify="end">
      <el-select v-model="order" clearable:false @change="onOrderChange"  placeholder="Order By">
      <el-option
      label="Asending,Asending"
      value="asc_asc">
      </el-option>
      <el-option
      label="Asending,Descending"
      value="asc_desc">
      </el-option>
      <el-option
      label="Descending,Descending"
      value="desc_desc">
      </el-option>
      <el-option
      label="Descending,Asending"
      value="desc_asc">
      </el-option>
  </el-select>
    </div>
    </el-col>
</el-row>




    <br>

    <el-table
      :data="tableData.movies"
      style="width: 100%">
      <el-table-column
        type="index">
      </el-table-column>
      <el-table-column
        label="Title">
        <template slot-scope="scope">

             <el-link type="success"  @click="onMovieClick(scope.$index)" >{{tableData.movies[scope.$index].title}}</el-link>
   
    
        </template>          
      </el-table-column>
      <el-table-column
        prop="year"
        label="Year">
      </el-table-column>
      <el-table-column
        prop="director"
        label="Director">
      </el-table-column>            
      <el-table-column
        label="Genres">
        <template slot-scope="scope">
          
        <div v-for="(genreitem,idx) in tableData.movies[scope.$index].genres" v-bind:key="idx">
          <el-col :span="6">
             <el-tag @click="onGenreClick(genreitem.name)" type="success">{{genreitem.name}}</el-tag>
           </el-col>
         </div>      
    
        </template>  
      </el-table-column>
      <el-table-column
        label="Stars">
        <template slot-scope="scope">
        <div v-for="(staritem,idx) in tableData.movies[scope.$index].stars" v-bind:key="idx">
           <li>
           <el-link type="warning"  @click="onStarClick(staritem.id)" >{{staritem.name}}</el-link>
           </li>
        </div>      
        </template>  
      </el-table-column>
      <el-table-column
        prop="rating"
        label="Rating">
      </el-table-column>   
      <el-table-column
      label="Add To Shopping Cart">
      <template slot-scope="scope">
        <el-button
          type="primary"
           round
          @click="handleAddCart(scope.$index)">ADD</el-button>
      </template>
    </el-table-column>         
    </el-table>

  </div>


</template>




<script>
// @ is an alias to /src


export default {
  name: 'List',
  components: {

  },
  methods:{
    handleAddCart: function(index){
  
      
            //post
            this.axios.post('/api/cart/add',null,{params:{
                movieId:this.tableData.movies[index].id,
                movieTitle:this.tableData.movies[index].title
            }}).then(
                response=>{
                    if(response.data.message==0){
                        alert("Success!");
                    }else if(response.data.message == -1){
            alert('Auth Fail '+response.data.data);
          }else{
            alert(response.data.data);
          }
                }
            )
        
        
    },
    onGenreClick: function(genName){
       this.$router.push({name:'Browse',query:{
           genre:genName
         }})

       
      location.reload();
    },  
    onMovieClick: function(index){

        this.$router.push({name:'Item',params:{type:'movie',id: this.tableData.movies[index].id}});
    },
    onStarClick: function(starId){
        this.$router.push({name:'Item',params:{type:'star',id: starId}});
 
    },
    onPageSizeChange: function(newSize){
      this.pagesize=newSize;
      this.$router.replace({query: this.genQuery()})
      this.requestData();
    },
    onPageChange: function(pageNum){
      this.page=pageNum;
      this.$router.replace({query: this.genQuery()})
      this.requestData();
    },
    onOrderChange: function(od){
       this.order=od;
        this.$router.replace({query: this.genQuery()})
        this.requestData();
    },
    onSortChange: function(field){
      
      // this.$route.query.sort=field;
      this.sort=field;
      this.$router.replace({query: this.genQuery()})
      this.requestData();
    },
    genQuery: function(){
      return {
        genre:this.genre,
        alphabet:this.alphabet,
        year:this.year,
        title:this.title,
        director:this.director,
        star:this.star,
        sort:this.sort,
        order:this.order,
        page:this.page,
        pagesize:this.pagesize
      }
    }, 
    requestData(){
        var url='';

    if(this.$route.name == 'Search'){
            url='/api/search?page='+this.page+'&pagesize='+this.pagesize+'&sort='+this.sort+'&order='+this.order+'&title='+this.title+'&year='+this.year+'&director='+this.director+'&star='+this.star;

    }else if(this.genre != null){
      console.log(this.genre);
      url='/api/list?page='+this.page+'&pagesize='+this.pagesize+'&sort='+this.sort+'&order='+this.order+'&genre='+this.genre;
    }else if(this.alphabet != null){
      url='/api/listalpha?page='+this.page+'&pagesize='+this.pagesize+'&sort='+this.sort+'&order='+this.order+'&alphabet='+this.alphabet;
    }
    console.log(url);
    this.axios.get(url).then(
      response=>{
        if(response.data.message == 0){
          this.tableData=response.data.data;
        }else if(response.data.message == -1){
          alert("auth fail!"+response.data.data);
        }else{
          alert(response.data.data);
        }
      }
    )
    }   
  },
  mounted() {

    this.genre=this.$route.query.genre;
    this.alphabet=this.$route.query.alphabet;
    this.year=this.$route.query.year;
    this.title=this.$route.query.title;
    this.director=this.$route.query.director;
    this.star=this.$route.query.star;
    if(this.$route.query.sort!=null){
    this.sort=this.$route.query.sort;
    }
    if(this.$route.query.order!=null){
    this.order=this.$route.query.order;
    }
    if(this.$route.query.page!=null){
    this.page=parseInt(this.$route.query.page);
    }
    if(this.$route.query.pagesize!=null){
    this.pagesize=parseInt(this.$route.query.pagesize);
    }

//=========Perform query
    this.requestData();

  },

  data: function() {
    return {
        genre:'',
        alphabet:'',
        year:'',
        title:'',
        director:'',
        star:'',
        sort:'title_rating',
        order:'asc_asc',
        page:1,
        pagesize:10,
        tableData: []

    }
  }
}


</script>
