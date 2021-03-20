<template>
  <div id="ComplaintHandleService">
  <el-breadcrumb separator="/">
  <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
  <!-- <el-breadcrumb-item><a href="/">活动管理</a></el-breadcrumb-item> -->
  <el-breadcrumb-item>投诉管理</el-breadcrumb-item>
  <el-breadcrumb-item>投诉查看</el-breadcrumb-item>
</el-breadcrumb>

<!-- 卡片视图区域 -->
<el-card >
 
  <el-row :gutter="20">
  
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
      label="投诉编号"
      width="150">
    </el-table-column>
    <el-table-column
      prop="userPhone"
      label="用户手机号"
      width="120">
    </el-table-column>
    <el-table-column
      prop="context"
      label="投诉内容"
      width="300">
    </el-table-column>
    <el-table-column
      prop="state"
      label="状态"
      width="120">
    </el-table-column>
    <el-table-column
      prop="createdTime"
      label="创建时间"
      width="140">
    </el-table-column>
    <el-table-column
      prop="handledTime"
      label="处理时间"
      width="140">
    </el-table-column>
    <el-table-column
      prop="handlerId"
      label="处理人编码"
      width="120">
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
  name: 'ComplaintChecking',
  created(){
    this.getComplaintList();
  },
  data(){
      return {
          //表示当前选中的index
          handlerId:0,
          tabaleIndex:0,
          currentPage:1,
          size:10,
          total:10,
          phone: '',
          tableData: [],
        options: [
          ],
        form:{
            houseId:"",
             //绑定或取消绑定操作中，选中的owner
            phones:[]
        },
        formLabelWidth: '120px'
      }
  },
  methods: {
      open(index) {
        const complaintId = this.tableData[index].id;
        this.$confirm('该条投诉已被处理完毕?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.confirmComplaint(complaintId);
        }).catch(() => {   
            console.log();      
        });
      },
      handleSizeChange(newSize){
          console.log(newSize);
          this.size = newSize;
          this.getComplaintList();
      },
      handleCurrentChange(newPage){
          console.log(newPage);
          this.currentPage = newPage;
          this.getComplaintList();
      },
    //   填充投诉信息
      getComplaintList(){
          this.phone = window.sessionStorage.getItem("phone");
          const url = "/system/lifeservice/getusercomplaintrecords" +"?currentPage="+this.currentPage+"&size="+this.size+"&phone="+this.phone;         
          this.$http.get(url)
                    .then((res)=>{
                        if(res.data.success){
                            console.log(res);
                            this.tableData = res.data.data.complaints;
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