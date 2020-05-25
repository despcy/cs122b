<template>
  <div class="search">
<div v-if="this.$route.query.title == null && this.$route.query.year == null && this.$route.query.director == null &&this.$route.query.star == null">
<el-form  label-width="100px">
  <div>
  <el-form-item label="Title">
  <input style="width:100%;height:auto"  v-model="title" @keydown.down="focusSel()"  @keyup.enter="titleInputEnter()" @click="titleInputClick()">
  <div v-if="showMe" class='menu'>

  <select @keyup.enter="selenter()"  ref="sel" v-model="selected" class='menu-item' size=10>
  <option  v-for='(item,idx) in this.items' v-bind:key=idx @click='remoteMethod(item.id)'>{{item.title}}</option>

  </select>
	</div>
  
  </el-form-item>
  </div>
  <el-form-item label="Year">
    <el-input type="number" v-model="year"></el-input>
  </el-form-item>
    <el-form-item label="Director">
    <el-input v-model="director"></el-input>
  </el-form-item>
    <el-form-item label="Star Name">
    <el-input v-model="name"></el-input>
  </el-form-item>
  <el-form-item>
    <el-button type="primary" @click="onSubmit">Search</el-button>
  </el-form-item>
</el-form>
</div>
<div v-else>
<list-view></list-view>
</div>
  </div>
</template>




<script>
// @ is an alias to /src

import ListView from './List.vue'

export default {
  name: 'Search',
  components: {
ListView
  },
  data() {
      return {
          title:'',
          year:'',
          director:'',
          name:'',
          items:[{"id":"12f","title":"fff"},{"id":"126f","title":"fhff"}],
          titlemode:false,
          selected:{},
          lock:false,
          hash:{}
          
      }
    },
  computed: {
    showMe: function() {
      if(this.title.length>=3&&this.titlemode){
        
        return true;
      }
      return false;
    }
  },
  watch: {
    // whenever question changes, this function will run
    selected: function (item) {
      console.log(item+"-selected");
      this.title=item;
    },
    title: function (tit) {
     

      if(tit!=this.selected&&this.showMe==true){
        //request data
         console.log(tit);
          console.log(this.lock);
        if(this.lock==false){
          this.lock=true;
          console.log("locked")
          setTimeout((tit)=>{this.lock=false;console.log("unlock");if(tit!=this.title){this.getSugg();}},300)
          this.getSugg();
        }
        

      }
    }
  },
  methods: {
      onSubmit() {
       // alert(this.form.title+this.form.year+this.form.director+this.form.name)
        if(this.title!='' || this.year!='' || this.director!='' || this.name!=''){
         this.$router.push({name:'Search',query:this.genQuery()})
        }else{
          alert("Empty query!");
        }
      },
      genQuery: function(){
      return {
        title:this.title,
        year:this.year,
        director:this.director,
        star:this.name
      }
    },
    titleInputEnter(){

      this.titlemode=false;
    },
    titleInputClick(){
     
      this.titlemode=true;
    },
    focusSel(){
      this.$refs.sel.focus();
    },
    selenter(){
      var sele=this.selected;
      var rm=this.remoteMethod;
      this.items.forEach(function(entry) {
        if(entry.title==sele){
          console.log(entry);
          rm(entry.id);

          return;
        }
      });

    },
    remoteMethod(query) {
      //go to single movie page
     
      console.log(query);
      this.$router.push({name:'Item',params:{type:'movie',id: query}});
    
    },
    getSugg(){
      //do logic here
      if(this.hash[this.title]!=null){
        this.items=this.hash[this.title];
      }else{
        var qkey=this.title;
          this.axios.get('/api/fsearch?text='+this.title).then(
        response=>{
          if(response.data.message == 0){
            this.items=response.data.data;
            this.hash[qkey]=response.data.data;
          }else if(response.data.message == -1){
            alert('Auth Fail '+response.data.data);
          }else{
            alert(response.data.data);
          }
        }
      )

      }

    }
  }

}
</script>

<style>


/* .menu {
	background-color: #fff;
	list-style: none;
	text-align: left;
  width: 100%;
} */

.menu-item {
	color: #212529;
  width: 100%;
	padding: .25rem 1.5rem;
	transition: color .15s ease-in-out,background-color .15s ease-in-out,border-color .15s ease-in-out,box-shadow .15s ease-in-out;
}

.menu-item:hover {
	background-color: #F4F6F6;
	cursor: pointer;
}


</style>