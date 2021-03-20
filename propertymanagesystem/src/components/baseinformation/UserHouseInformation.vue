<template>
  <div id="HouseInformation">
  <el-breadcrumb separator="/">
  <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
  <!-- <el-breadcrumb-item><a href="/">活动管理</a></el-breadcrumb-item> -->
  <el-breadcrumb-item>基本信息管理</el-breadcrumb-item>
  <el-breadcrumb-item>房屋信息维护</el-breadcrumb-item>
</el-breadcrumb>

<!-- 卡片视图区域 -->
<el-card >
 
  <el-row :gutter="20">
  <el-col :span="15">
    <el-input placeholder="手机号查询" v-model="input" class="input-with-select" style="width:35%;">
    <el-button slot="append" icon="el-icon-search" @click="getUserHouseList"></el-button>
  </el-input>
  </el-col>
</el-row>
</el-card>

<el-table
    :data="tableData"
    height="500"
    border
    style="width: 100%;">
    <!-- 索引列 -->
    <el-table-column type="index" label="序号"></el-table-column>
    <el-table-column
      prop="account"
      label="用户名"
      width="160">
    </el-table-column>
    <el-table-column
      prop="phone"
      label="手机"
      width="160">
    </el-table-column>
    <el-table-column
      prop="address"
      label="房屋"
      width="400">
    </el-table-column>
    
  </el-table>

  <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="this.currentPage"
      :page-sizes="[5, 10, 15, 20]"
      :page-size="this.size"
      layout="total, sizes, prev, pager, next, jumper"
      :total="this.total">
    </el-pagination>

  
  </div>
</template>

<script>

export default {
  name: 'UserHouseInformation',
  created(){
    this.getUserHouseList();
  },
  data(){
      return {
          //表示当前选中的index
          tabaleIndex:0,
          currentPage:1,
          size:10,
          total:0,
          phone:undefined,
          input: '',
          tableData: [],
        owners: "",
        formLabelWidth: '120px'
      }
  },
  methods: {
      handleSizeChange(newSize){
          console.log(newSize);
          this.size = newSize;
          this.getUserHouseList();
      },
      handleCurrentChange(newPage){
          console.log(newPage);
          this.currentPage = newPage;
          this.getUserHouseList();
      },
    //   填充房屋信息
      getUserHouseList(){
          const url = "/system/baseinformationadmin/getuserhouse" +"?currentPage="+this.currentPage+"&size="+this.size+ "&phone="+this.input;    
          this.$http.get(url)
                    .then((res)=>{
                        console.log(res);
                        if(res.data.success){
                            this.tableData = res.data.data.userHouses;
                            this.total = res.data.data.total;
                        }else {
                            this.$message.error(res.data.message);
                        }
                    });
                
      }
  }

}
</script>

<style>
 /* .el-input {
     padding-bottom: 20px;
 } */

 .el-select-dropdown__item {
     width:100% !important;
 }
</style>