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
    <el-input placeholder="请输入内容" v-model="input3" class="input-with-select">
    <el-button slot="append" icon="el-icon-search"></el-button>
  </el-input>
  </el-col>
  <el-col :span="4"><el-button type="primary">添加用户</el-button></el-col>
</el-row>
</el-card>

<el-table
    :data="tableData"
    height="320"
    border
    style="width: 100%;hight: 60%">
    <!-- 索引列 -->
    <el-table-column type="index" label="序号"></el-table-column>
    <el-table-column
      prop="id"
      label="房屋编号"
      width="150">
    </el-table-column>
    <el-table-column
      prop="unit"
      label="单元号"
      width="120">
    </el-table-column>
    <el-table-column
      prop="building"
      label="楼栋"
      width="120">
    </el-table-column>
    <el-table-column
      prop="floor"
      label="楼层"
      width="120">
    </el-table-column>
    <el-table-column
      prop="room"
      label="室"
      width="120">
    </el-table-column>
    <el-table-column
      prop="owner"
      label="房屋所有者"
      width="300">
    </el-table-column>
    <el-table-column
      fixed="right"
      label="操作"
      width="120">
      <template slot-scope="scope">
        <el-button @click="getOwners(scope.$index,0)" type="text" size="small">绑定</el-button>
        <el-button type="text" size="small" @click="getOwners(scope.$index,1)">取消绑定</el-button>
      </template>
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

  <el-dialog title="绑定取消" :visible.sync="dialogFormVisible">
  <el-form>
      <el-form-item label="已绑定的业主" :label-width="formLabelWidth">
      <el-input v-model="owners" autocomplete="off" style="width:65%;" disabled></el-input>
    </el-form-item>
    <el-form-item label="需取消的业主" :label-width="formLabelWidth">
      <el-select
    v-model="form.phones"
    multiple
    filterable
    default-first-option
    placeholder="请选择"
    style="float:left;width:65%;">
    <el-option
      v-for="item in options"
      :key="item.phone"
      :label="item.account"
      :value="item.phone"
      >
      <span style="float: left">{{ item.account }}</span>
      <span style="float: right; color: #8492a6; font-size: 13px">{{ item.phone }}</span>
    </el-option>
  </el-select>
    </el-form-item>
  </el-form>
  <div slot="footer" class="dialog-footer">
    <el-button @click="dialogFormVisible = false">取 消</el-button>
    <el-button type="primary" @click="deleteOwners()">确 定</el-button>
  </div>
</el-dialog>
<el-dialog title="绑定" :visible.sync="addDialogFormVisible">
  <el-form>
      <el-form-item label="已绑定的业主" :label-width="formLabelWidth">
      <el-input v-model="owners" autocomplete="off" style="width:65%;" disabled></el-input>
    </el-form-item>
    <el-form-item label="需增加的业主" :label-width="formLabelWidth">
      <el-select
    v-model="form.phones"
    multiple
    filterable
    default-first-option
    placeholder="请选择"
    style="float:left;width:65%;">
    <el-option
      v-for="item in options"
      :key="item.phone"
      :label="item.account"
      :value="item.phone"
      >
      <span style="float: left">{{ item.account }}</span>
      <span style="float: right; color: #8492a6; font-size: 13px">{{ item.phone }}</span>
    </el-option>
  </el-select>
    </el-form-item>
  </el-form>
  <div slot="footer" class="dialog-footer">
    <el-button @click="addDialogFormVisible = false">取 消</el-button>
    <el-button type="primary" @click="addOwners()">确 定</el-button>
  </div>
</el-dialog>
  </div>
</template>

<script>

export default {
  name: 'HouseInformation',
  created(){
    this.getHouseList();
  },
  data(){
      return {
          //表示当前选中的index
          tabaleIndex:0,
          currentPage:1,
          size:10,
          total:10,
          input3: '',
          tableData: [],
        options: [
          ],
        owners: "",
        form:{
            houseId:"",
             //绑定或取消绑定操作中，选中的owner
            phones:[]
        },
        dialogFormVisible: false,
        addDialogFormVisible: false,
        formLabelWidth: '120px'
      }
  },
  methods: {
      handleSizeChange(newSize){
          console.log(newSize);
          this.size = newSize;
          this.getHouseList();
      },
      handleCurrentChange(newPage){
          console.log(newPage);
          this.currentPage = newPage;
          this.getHouseList();
      },
    //   填充房屋信息
      getHouseList(){
          const url = "/system/baseinformationadmin/gethouseinformation" +"?currentPage="+this.currentPage+"&size="+this.size;         
          this.$http.get(url)
                    .then((res)=>{
                        if(res.data.success){
                            this.tableData = res.data.data.houses;
                            this.total = res.data.data.total;
                        }else {
                            this.$message.error(res.data.message);
                        }
                    });
                
      },

    //   index是表格所选列在tableData中的序号，
    //   type=0表示绑定，type=1表示取消绑定
    //   作用：获取某个house的业主
      getOwners(index,type){
        /*
        1、根据index获取房屋id
        2、房屋id为参数，向后端请求业主列表数据
        3、将后端返回的数据写到value中
        */
    //    const houseId = this.tableData[index].id;
    //    const url = "/system/baseinformationadmin/getowners"+
    //                "?houseId="+houseId;
    //    this.$http.get(url)
    //              .then((res)=>{
    //                  if(res.data.success){
    //                      this.owners = res.data.data.owners;
    //                  }else {
    //                      this.$message.error("获取业主信息失败");
    //                  }
                     
    //              });
        this.owners = this.tableData[index].owner;
        this.tabaleIndex = index;
        this.getUsers();
          //显示对话框
          if(type===0){
              this.addDialogFormVisible = true;
          }
          if(type ===1){
            this.dialogFormVisible = true;
          }
      },
      //获取系统所有的用户
      getUsers(){
          const url = "/system/baseinformationadmin/getusers";
          this.$http.get(url)
                    .then((res)=>{
                        if(res.data.success){
                            this.options = res.data.data.users;
                        }else{
                            this.$message.error("获取系统用户失败");
                        }
                    });
      },
      //   index是表格所选列在tableData中的序号，增加某个house的业主
      addOwners(){
          this.form.houseId = this.tableData[this.tabaleIndex].id+"";
          const url = "/system/baseinformationadmin/addownersofhouse";
          console.log(this.form);
          this.$http.post(url,this.form)
                    .then((res)=>{
                        if(res.data.success){
                            this.$message.success("绑定成功");
                        }else{
                            this.$message.error(res.data.message);
                        }
                        this.form.phones=[];
                        this.getHouseList();
                    });
                    
                    this.addDialogFormVisible = false;
      },
       //   index是表格所选列在tableData中的序号，减去某个house的业主
      deleteOwners(){
          this.form.houseId = this.tableData[this.tabaleIndex].id+"";
          const url = "/system/baseinformationadmin/deletebyhouseidandphones";
           console.log(this.form);
          this.$http.post(url,this.form)
                    .then((res)=>{
                        if(res.data.success){
                            this.$message.success("操作成功");
                        }else{
                            this.$message.error(res.data.message);
                        }
                      this.form.phones = [];  
                      this.getHouseList();
                    });
           //清空选择框中的内容
           
            this.dialogFormVisible = false;
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