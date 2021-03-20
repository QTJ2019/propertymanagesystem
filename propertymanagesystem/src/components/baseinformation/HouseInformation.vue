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
    <el-input placeholder="单元查询" v-model="houseForm.unit" class="input-with-select" style="width:20%;"></el-input>
     <el-input placeholder="楼栋查询" v-model="houseForm.building" class="input-with-select" style="width:20%;"></el-input>
       <el-input placeholder="楼层查询" v-model="houseForm.floor" class="input-with-select" style="width:20%;"></el-input>
         <el-input placeholder="室查询" v-model="houseForm.room" class="input-with-select" style="width:20%;"></el-input>
    <el-button  icon="el-icon-search" style="float:left;" @click="getHouseList"></el-button>
  </el-col>
  <el-col :span="3">
          <el-upload
            class="upload-demo"
            :on-change="uploadHouseExcel"
            :limit="1"
            action="none"
            accept=".xlsx, .xls"
            :auto-upload="false"
          >
            <el-button size="small" type="primary">点击上传</el-button>
          </el-upload>
        </el-col>
  <el-col :span="2"
          ><el-button size="small" type="primary" @click="dialogAddHouseFormVisible = true"
            >增加</el-button
          ></el-col
        >
</el-row>
</el-card>

<el-table
    :data="tableData"
    height="500"
    border
    style="width: 100%;hight: 60%">
    <!-- 索引列 -->
    <el-table-column type="index" label="序号"></el-table-column>
    <el-table-column
      prop="id"
      label="房屋编号"
      width="110">
    </el-table-column>
    <el-table-column
      prop="unit"
      label="单元号"
      width="110">
    </el-table-column>
    <el-table-column
      prop="building"
      label="楼栋"
      width="110">
    </el-table-column>
    <el-table-column
      prop="floor"
      label="楼层"
      width="110">
    </el-table-column>
    <el-table-column
      prop="room"
      label="室"
      width="110">
    </el-table-column>
    <el-table-column
      prop="owner"
      label="房屋所有者"
      width="300">
    </el-table-column>
    <el-table-column
      fixed="right"
      label="操作"
      width="200">
      <template slot-scope="scope">
        <el-button @click="getOwners(scope.$index,0)" type="text" size="small">绑定</el-button>
        <el-button type="text" size="small" @click="getOwners(scope.$index,1)">取消绑定</el-button>
        <el-button @click="openUpdateHouseDialog(scope.$index)" type="text" size="small">修改房屋</el-button>
      </template>
    </el-table-column>
  </el-table>

  <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="houseForm.currentPage"
      :page-sizes="[5, 10, 15, 20]"
      :page-size="houseForm.size"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total">
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
<el-dialog title="增加房屋" :visible.sync="dialogAddHouseFormVisible">
  <el-form >
    <el-form-item label="单元" :label-width="formLabelWidth">
      <el-input v-model="addHouseForm.unit" autocomplete="off" style="width:40%;"></el-input>
    </el-form-item>
    <el-form-item label="楼栋" :label-width="formLabelWidth">
      <el-input v-model="addHouseForm.building" autocomplete="off" style="width:40%;"></el-input>
    </el-form-item>
    <el-form-item label="楼层" :label-width="formLabelWidth">
      <el-input v-model="addHouseForm.floor" autocomplete="off" style="width:40%;"></el-input>
    </el-form-item>
    <el-form-item label="室" :label-width="formLabelWidth" >
      <el-input v-model="addHouseForm.room" autocomplete="off" style="width: 40%"></el-input>
    </el-form-item>
  </el-form>
  <div slot="footer" class="dialog-footer">
    <el-button @click="dialogAddHouseFormVisible = false">取 消</el-button>
    <el-button type="primary" @click="addHouse">确 定</el-button>
  </div>
</el-dialog>
<el-dialog title="修改房屋" :visible.sync="dialogUpdateHouseFormVisible">
  <el-form >
    <el-form-item label="单元" :label-width="formLabelWidth">
      <el-input v-model="addHouseForm.unit" autocomplete="off" style="width:40%;"></el-input>
    </el-form-item>
    <el-form-item label="楼栋" :label-width="formLabelWidth">
      <el-input v-model="addHouseForm.building" autocomplete="off" style="width:40%;"></el-input>
    </el-form-item>
    <el-form-item label="楼层" :label-width="formLabelWidth">
      <el-input v-model="addHouseForm.floor" autocomplete="off" style="width:40%;"></el-input>
    </el-form-item>
    <el-form-item label="室" :label-width="formLabelWidth" >
      <el-input v-model="addHouseForm.room" autocomplete="off" style="width: 40%"></el-input>
    </el-form-item>
  </el-form>
  <div slot="footer" class="dialog-footer">
    <el-button @click="dialogUpdateHouseFormVisible = false">取 消</el-button>
    <el-button type="primary" @click="updateHouse">确 定</el-button>
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
        houseForm:{
          currentPage:1,
          size:10,
          unit:undefined,
          building:undefined,
          floor:undefined,
          room:undefined
        },
        //该表单也可以用来修改
        addHouseForm:{
          id:undefined,
          unit:undefined,
          building:undefined,
          floor:undefined,
          room:undefined
        },
        dialogFormVisible: false,
        dialogUpdateHouseFormVisible:false,
        addDialogFormVisible: false,
        dialogAddHouseFormVisible:false,
        formLabelWidth: '120px'
      }
  },
  methods: {
      handleSizeChange(newSize){
          console.log(newSize);
          this.houseForm.size = newSize;
          this.getHouseList();
      },
      handleCurrentChange(newPage){
          console.log(newPage);
          this.houseForm.currentPage = newPage;
          this.getHouseList();
      },
    //   填充房屋信息
      getHouseList(){
          const url = "/system/baseinformationadmin/gethouseinformation";         
          this.$http.post(url,this.houseForm)
                    .then((res)=>{
      
                      if(res===undefined || res.data===undefined){
                        this.$message.error("获取房屋信息失败");
                      }else{
                        if(res.data.success){
                          console.log(res);
                            this.tableData = res.data.data.houses;
                            this.total = res.data.data.total;
                        }else {
                            this.$message.error(res.data.message);
                        }
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
      },
      uploadHouseExcel(files, fileList) {
      if (files.length + fileList.length > 1) {
        this.$message.error(
          "只能上传1个文件,本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件"
        );
        return;
      }
      const fd = new FormData();
      fd.append("file", fileList[0].raw);
      const url = "/system/baseinformationadmin/uploadhouseexcel";
      this.$http({
        url: url,
        method: "post",
        data: fd,
        header: {
          "Content-Type": "multipart/form-data",
        },
      }).then((res) => {
        if (res.data.success) {
          this.$message.success("导入成功");
        } else {
          this.$message.error(res.data.message);
        }
      });
      while (fileList.length > 0) {
        fileList.pop();
      }
      this.getHouseList();
    },
      addHouse(){
        this.addHouseForm.id = undefined;
        const url = "/system/baseinformationadmin/addhouse";
        this.$http.post(url,this.addHouseForm)
                  .then(res =>{
                    if (res == undefined || res.data == undefined){
                      this.$message.error("添加失败");
                    }else {
                      if(res.data.success){
                        this.$message.success("添加成功");
                      }else{
                        this.$message.error(res.data.message);
                      }
                    }
                  })
        this.dialogAddHouseFormVisible = false;
        this.getHouseList();
      },

    openUpdateHouseDialog(index){
    this.dialogUpdateHouseFormVisible = true;
    this.addHouseForm.id = this.tableData[index].id;
     this.addHouseForm.unit = this.tableData[index].unit;
     this.addHouseForm.building = this.tableData[index].building;
     this.addHouseForm.floor = this.tableData[index].floor;
     this.addHouseForm.room = this.tableData[index].room;
  },

updateHouse(){
     const url = "/system/baseinformationadmin/updatehouse";
     this.$http.post(url,this.addHouseForm)
               .then(res => {
                 if(res === undefined || res.data === undefined){
                   this.$message.error("更新失败");
                 }else {
                   if(res.data.success){
                     this.$message.success("更新成功");
                   }else {
                     this.$message.error(res.data.message);
                   }
                 }
               });
    this.getHouseList();
    this.dialogUpdateHouseFormVisible = false;
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