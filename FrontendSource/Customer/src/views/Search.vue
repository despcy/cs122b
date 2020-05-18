<template>
  <div class="search">
<div v-if="this.$route.query.title == null && this.$route.query.year == null && this.$route.query.director == null &&this.$route.query.star == null">
<el-form  label-width="100px">
  <el-form-item label="Title">
    <el-input v-model="title"></el-input>
  </el-form-item>
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
          name:''
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
    }
    }
}
</script>
