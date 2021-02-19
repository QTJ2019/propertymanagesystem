<template>
  <div id="ManagerMaintain">
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <!-- <el-breadcrumb-item><a href="/">活动管理</a></el-breadcrumb-item> -->
      <el-breadcrumb-item>系统管理</el-breadcrumb-item>
      <el-breadcrumb-item>管理人员维护</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 卡片视图区域 -->
    <el-card>
      <el-row :gutter="20">
        <el-col :span="11">
          <el-input
            v-model="phone"
            placeholder="手机号搜索"
            style="width: 23%"
          >
          </el-input>
          
          <el-button icon="el-icon-search" @click="getManagerList" style="float:left;"></el-button>
        </el-col>
        
        <el-col :span="2"
          ><el-button size="small" type="primary" @click="openDialog('管理人员增加',null)"
            >增加</el-button
          ></el-col
        >
      </el-row>
    </el-card>

    <el-table
      :data="tableData"
      height="320"
      border
      style="width: 100%; hight: 60%"
    >
      <!-- 索引列 -->
      <el-table-column type="index" label="序号"></el-table-column>
      <el-table-column prop="id" label="管理人员编号" width="150">
      </el-table-column>
      <el-table-column prop="account" label="用户名" width="120">
      </el-table-column>
      <el-table-column prop="phone" label="手机号" width="120"> </el-table-column>
      <el-table-column prop="state" label="状态" width="120">
      </el-table-column>
      <el-table-column  label="操作" width="120">
        <template slot-scope="scope">
          <el-button
            @click="open(scope.$index)"
            type="text"
            size="small"
            :disabled="scope.row.state === '已禁用'"
            >禁用</el-button
          >
          <el-button
            type="text"
            size="small"
            :disabled="scope.row.state === '已启用'"
            @click="open( scope.$index)"
            >启用</el-button
          >
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
      :total="this.total"
    >
    </el-pagination>
    <el-dialog :title="dialogTitle" :visible.sync="dialogFormVisible">
      <el-form>
        <el-form-item label="用户名" :label-width="formLabelWidth">
             <el-input
            v-model="addForm.account"
            autocomplete="off"
            style="width: 40%"
          ></el-input>
        </el-form-item>
        <el-form-item label="密码" :label-width="formLabelWidth">
          <el-input
           type="password"
            v-model="addForm.password"
            autocomplete="off"
            style="width: 40%"
          ></el-input>
        </el-form-item>
        <el-form-item label="确认密码" :label-width="formLabelWidth">
          <el-input
           type="password"
            v-model="addForm.checkPassword"
            autocomplete="off"
            style="width: 40%"
          ></el-input>
        </el-form-item>
        <el-form-item label="手机号" :label-width="formLabelWidth">
          <el-input
            v-model="addForm.phone"
            autocomplete="off"
            style="width: 40%"
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="addManager">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "ManagerMaintain",
  created() {
    this.getManagerList();
  },
  data() {
    return {
      //表示当前选中的index
      phone: "",
      handlerId: 0,
      tabaleIndex: 0,
      total: 10,
      size: 10,
      currentPage: 1,
      input3: "",
      dialogTitle: "",
      addForm: {
          id: undefined,
        account: undefined,
        password: undefined,
        checkPassword: undefined,
        phone: undefined,
        state: undefined
      },
      houseIdOptions: [],
      dialogFormVisible: false,
      tableData: [],
      options: [],
      formLabelWidth: "120px",
    };
  },
  methods: {
    open(index) {
      const managerId = this.tableData[index].id;
      let newState = undefined;
      if(this.tableData[index].state === "已启用" ){
         newState = 0; 
      }
      if (this.tableData[index].state === "已禁用"){
          newState = 1;
      }
      this.$confirm("确认改变状态?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.chanageManagerState(managerId,newState);
        })
        .catch(() => {
          console.log();
        });
    },
    handleSizeChange(newSize) {
      console.log(newSize);
      this.size = newSize;
      this.getComplaintList();
    },
    handleCurrentChange(newPage) {
      console.log(newPage);
      this.currentPage = newPage;
      this.getComplaintList();
    },
    uploadFeeExcel(files, fileList) {
      if (files.length + fileList.length > 1) {
        this.$message.error(
          "只能上传1个文件,本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件"
        );
        return;
      }
      const fd = new FormData();
      fd.append("file", fileList[0].raw);
      const url = "/system/feeservice/uploadfeeexcel";
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
      this.getManagerList();
    },
    getManagerList() {
      const url = "/system/systemadmin/getmanagerusers?currentPage="+this.currentPage+"&size="+this.size+"&phone="+this.phone;
      this.$http.get(url).then((res) => {
        if (res.data.success) {
          console.log(res);
          this.tableData = res.data.data.managerUsers;
          this.total = res.data.data.total;
          this.contextChange();
        } else {
          this.$message.error(res.data.message);
        }
      });
    },
    openDialog(title, index) {
      this.dialogTitle = title;
      this.tabaleIndex = index;
      this.dialogFormVisible = true;
      if (title === "费用修改") {
        this.addForm.id = this.tableData[index].id;
        this.addForm.houseId = this.tableData[index].houseId;
        this.addForm.price = this.tableData[index].price;
        this.addForm.number = this.tableData[index].number;
        this.addForm.type = this.typeToNumber(this.tableData[index].type);
        this.addForm.time = this.tableData[index].time;
      }
      if (title ==="费用记录增加"){
          this.addForm.id = undefined;
      }
    },
    addManager() {
        const url = "/system/systemadmin/addmanageruser";
        if(this.addForm.account === undefined || this.addForm.phone === undefined || this.addForm.password===undefined || this.addForm.checkPassword===undefined){
            this.$message.error("请填写完整");
            return false;
        }
        if(this.addForm.password !== this.addForm.checkPassword){
            this.$message.error("两次密码不一致");
            return false;
        }
        this.$http.post(url, this.addForm).then((res) => {
          if (res.data.success) {
            this.$message.success("添加成功");
          } else {
            this.$message.error(res.data.message);
          }
        });
      
      this.dialogFormVisible = false;
      this.clearAddForm();
      this.getManagerList();
      
    },
    contextChange() {
      for (let i = 0; i < this.tableData.length; i++) {

        switch (this.tableData[i].state) {
          case 0:
            this.tableData[i].state = "已禁用";
            break;
          case 1:
            this.tableData[i].state = "已启用";
            break;
        }
      }
    },
    clearAddForm(){
        this.addForm.id = undefined;
        this.addForm.state = undefined;
        this.addForm.account = undefined;
        this.addForm.password = undefined;
        this.addForm.checkPassword = undefined;
        this.addForm.phone = undefined;
    },
    chanageManagerState(managerId,newState){
         const url = "/system/systemadmin/updatemanageruser";
         this.clearAddForm();
         this.addForm.id = managerId;
         this.addForm.state = newState;
          this.$http.post(url,this.addForm)
                    .then((res)=>{
                        if(res.data.success){
                            this.$message.success("状态改变成功");
                        }else{
                            this.$message.error(res.data.message);
                        }
                    });
            this.getManagerList();
    }

  },
};
</script>

<style>
/* .el-input {
     padding-bottom: 20px;
 } */

.el-select-dropdown__item {
  width: 100% !important;
}

.el-date-editor {
  width: 23% !important;
}
</style>