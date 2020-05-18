<template>
  <div class="browse">
<div v-if="this.$route.query.genre==null&&this.$route.query.alphabet==null">
    <el-divider content-position="left">Browse By Alphabet</el-divider>
    <span>
       <el-row :gutter="20">
        <div v-for="(k,idx) in alphabet" v-bind:key=idx>
             <el-col :span="2">
        <el-link @click="onSubmit('alphabet',k)" type="danger"><h3>{{k}}</h3></el-link>
             </el-col>
        </div>
       </el-row>
    </span>
    <el-divider content-position="left">Browse By Genre</el-divider>
    <span>
       <el-row :gutter="20">
        <div v-for="(k,idx) in data" v-bind:key=idx>
             <el-col :span="5">
        <el-link @click="onSubmit('genre',k)" type="danger"><h3>{{k}}</h3></el-link>
             </el-col>
        </div>
       </el-row>
    </span>

</div>
<div v-else>
<list-view></list-view>
</div>
  </div>
</template>




<script>

import ListView from './List.vue'
export default {
  name: 'Browse',
  components: {
ListView
  },
  data() {
      return {
        alphabet:['0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','*'],
        data:{}
      }
    },
mounted() {
    this.axios.get('/api/genres').then(
        response=>{
          if(response.data.message == 0){
            this.data=response.data.data;
          }else if(response.data.message == -1){
            alert('Auth Fail '+response.data.data);
          }else{
            alert(response.data.data);
          }
        }
      )

  },
  methods: {
      onSubmit(type,key) {
       // alert(this.form.title+this.form.year+this.form.director+this.form.name)
       if(type=='alphabet'){
         this.$router.push({name:'Browse',query:{
           alphabet:key
         }})
       }else{
         this.$router.push({name:'Browse',query:{
           genre:key
         }})
       }

      }
    }
}
</script>
