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
        prop="title"
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
<el-button-group>
  <el-button @click="active++" v-if="cartData.length > 0" type="primary">Next Step<i class="el-icon-arrow-right el-icon-right"></i></el-button>
</el-button-group>
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
<el-button-group>
  <el-button @click="active--"  type="primary" icon="el-icon-arrow-left">Previous Step</el-button>
  <el-button @click="checkout"  type="success">Check out<i class="el-icon-arrow-right el-icon-right"></i></el-button>
</el-button-group>
  </div>
  <div v-else-if="active === 2">
<div align="middle">
  <br>
  <h1 type="success">Success!</h1>
  <br>
<h2>Total: {{totalMoney}}</h2>
<h2>Detail:</h2>
    <el-table
      :data="cartData"
      style="width: 100%">
      <el-table-column
        prop="title"
        label="Title">
      </el-table-column>
      <el-table-column
        prop="quantity"
        label="Quantity">
      </el-table-column>
      <el-table-column
        prop="price"
        label="Price">
      </el-table-column>
      <el-table-column
        label="Sale IDs">
       <template slot-scope="scope">
        <div v-for="(sidata,idx) in cartData[scope.$index].sid" v-bind:key="idx">
          {{sidata}},&nbsp; 
        </div>       
      </template>       
      </el-table-column>
        
    </el-table>
   <br>
  <br>
</div>
  </div>


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
      number:'',
      expire:'',
      userId:'',
      saleID:''

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
    this.axios
      .get('/api/user')
      .then(response => {
        this.userId=response.data.data.id;
      })
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
        this.axios.post('/api/cart/checkout',null,{params:{
          userId:this.userId,
          first:this.first,
          last:this.last,
          number:this.number,
          expire:this.expire
        }}).then(
          response=>{
            if(response.data.message==0){
              this.active=2;
              this.cartData=response.data.data;
              this.saleID=response.data.sid;
            }else if(response.data.message==-1){
              alert("session auth fail "+response.data.data);
            }else{
              alert("Payment Info Error!");
            }
          }
        )
    },
    getCart(){
      this.axios.get('/api/cart/show').then(
        response=>{
          if(response.data.message==0){
            this.cartData=response.data.data;
          }else if(response.data.message==-1){
            alert('AuthFail'+response.data.data);
          }else{
            alert(response.data.data);
          }
        }
      )
    },
    updateCart(){
        const cdata= this.cartData;
        this.axios.post('/api/cart/update',cdata);
      }

  }
}
</script>

