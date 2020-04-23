<template>
  <div class="shop">
<el-steps :space="200" :active="active" simple>
  <el-step title="Cart Items" icon="el-icon-shopping-cart-full"></el-step>
  <el-step title="Payment Information" icon="el-icon-bank-card"></el-step>
  <el-step title="Confirm" icon="el-icon-check"></el-step>
</el-steps>

  <div v-if="active === 0">
    <el-table
      :data="cartData"
      style="width: 100%">
      <el-table-column
        prop="movieTitle"
        label="Title">
      </el-table-column>
      <el-table-column
        prop="price"
        label="Price">
      </el-table-column>
      <el-table-column
        label="Quantity">
      <template slot-scope="scope">
        <el-input-number v-model="cartData[scope.$index].quantity" @change="handleQuantity(scope.$index)" :min="1" :max="100"></el-input-number>       
      </template>

      </el-table-column>
      <el-table-column
        label="Price Total">
      <template slot-scope="scope">
        {{cartData[scope.$index].quantity*cartData[scope.$index].price}}    
      </template>

      </el-table-column>
    <el-table-column
      label="Operations">
      <template slot-scope="scope">
        <el-button
          size="mini"
          type="danger"
          @click="handleDelete(scope.$index)">Delete</el-button>
      </template>
    </el-table-column>  
  </el-table>
  <el-row type="flex"><h3>Total: {{totalMoney}}</h3></el-row>
  </div>
  <div v-else-if="active === 1">
      <el-row type="flex"><h2>Total: {{totalMoney}}</h2></el-row>
<el-form  label-width="150px">
  <el-form-item label="First Name">
    <el-input v-model="first"></el-input>
  </el-form-item>
  <el-form-item label="LastName">
    <el-input  v-model="last"></el-input>
  </el-form-item>
    <el-form-item  label="Card Number">
    <el-input type="number" v-model="number"></el-input>
  </el-form-item>
    <el-form-item label="Expiration date">
    <el-input v-model="expire"></el-input>
  </el-form-item>

</el-form>

  </div>
  <div v-else-if="active === 2">
<div align="middle">
  <br>
  <br>
 <el-button type="success" @click="checkout" round>Check Out</el-button>
   <br>
  <br>
</div>
  </div>

  <el-button-group>
  <el-button @click="active--" v-if="cartData.length > 0 && active != 0" type="primary" icon="el-icon-arrow-left">Previous Step</el-button>
  <el-button @click="active++" v-if="cartData.length > 0 && active != 2" type="primary">Next Step<i class="el-icon-arrow-right el-icon-right"></i></el-button>
</el-button-group>
  </div>
</template>




<script>
// @ is an alias to /src


export default {
  name: 'Shop',
  components: {

  },
  data: function(){
    return {
      active:0,
      cartData:[],
      first:'',
      last:'',
      number:0,
      expire:''
    }
  },
  computed:{
    totalMoney: function(){
      var count=0;
      for(let i=0;i<this.cartData.length;i++){
        count+=this.cartData[i].price*this.cartData[i].quantity;
      }
      return count;
    }
  },
  mounted() {
    this.getCart();
  },
  methods: {
    handleDelete(index){
      //var data=cartData[index];
      this.cartData.splice(index,1);
      this.updateCart();
    },
    handleQuantity(index){
      console.log(index);
      this.updateCart();
    },
    checkout(){
        this.axios.post('/api/cart/checkout',{
          first:this.first,
          last:this.last,
          number:this.number,
          expire:this.expire
        }).then(
          response=>{
            if(response.message==0){
              alert('Success!');
              this.getCart();
            }else if(response.message==-1){
              alert("auth fail "+response.data);
            }else{
              alert(response.data);
            }
          }
        )
    },
    getCart(){
      this.axios.get('/api/cart/show').then(
        response=>{
          if(response.message==0){
            this.cartData=response.data;
          }else if(response.message==-1){
            alert('AuthFail'+response.data);
          }else{
            alert(response.data);
          }
        }
      )
    },
    updateCart(){
        const cdata= this.cartData;
        this.axios.post('/api/cart/update',{data:cdata});
      }

  }
}
</script>

