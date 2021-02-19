<template>
  <div id="FeeMaintain">
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <!-- <el-breadcrumb-item><a href="/">活动管理</a></el-breadcrumb-item> -->
      <el-breadcrumb-item>物业费管理</el-breadcrumb-item>
      <el-breadcrumb-item>物业费维护</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 卡片视图区域 -->
    <el-card>
      <el-row :gutter="20">
        <el-col :span="15">
          <el-input
            v-model="searchForm.houseId"
            placeholder="房屋搜索"
            style="width: 23%"
          >
          </el-input>
          <el-select
            v-model="searchForm.type"
            filterable
            default-first-option
            placeholder="费用类型搜索"
            style="float: left; width: 23%"
          >
            <el-option
              v-for="item in typeOptions"
              :key="item.type"
              :label="item.name"
              :value="item.type"
            >
              <span style="color: #8492a6; font-size: 13px">{{
                item.name
              }}</span>
            </el-option>
          </el-select>
          <el-date-picker
            v-model="searchForm.startDate"
            type="month"
            placeholder="选择开始日期"
            format="yyyy-MM"
            value-format="yyyy-MM"
          >
          </el-date-picker>
          <el-date-picker
            v-model="searchForm.endDate"
            type="month"
            placeholder="选择结束日期"
            format="yyyy-MM"
            value-format="yyyy-MM"
          >
          </el-date-picker>
          <el-button icon="el-icon-search" @click="getFeeList"></el-button>
        </el-col>
        <el-col :span="3">
          <el-upload
            class="upload-demo"
            :on-change="uploadFeeExcel"
            :limit="1"
            action="none"
            :auto-upload="false"
          >
            <el-button size="small" type="primary">点击上传</el-button>
          </el-upload>
        </el-col>
        <el-col :span="2"
          ><el-button size="small" type="primary" @click="openDialog('费用记录增加',null)"
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
      <el-table-column prop="id" label="费用编号" width="150">
      </el-table-column>
      <el-table-column prop="houseId" label="房屋编号" width="120">
      </el-table-column>
      <el-table-column prop="price" label="单价" width="120"> </el-table-column>
      <el-table-column prop="number" label="数量" width="120">
      </el-table-column>
      <el-table-column prop="total" label="总费用" width="120">
      </el-table-column>
      <el-table-column prop="type" label="费用类型" width="140">
      </el-table-column>
      <el-table-column prop="state" label="状态" width="140"> </el-table-column>
      <el-table-column prop="time" label="时间" width="120"> </el-table-column>
      <el-table-column fixed="right" label="操作" width="120">
        <template slot-scope="scope">
          <el-button
            @click="open(scope.$index)"
            type="text"
            size="small"
            :disabled="scope.row.state === '已缴纳'"
            >确认</el-button
          >
          <el-button
            type="text"
            size="small"
            :disabled="scope.row.state === '已缴纳'"
            @click="openDialog('费用修改', scope.$index)"
            >修改</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="this.searchForm.currentPage"
      :page-sizes="[5, 10, 15, 20]"
      :page-size="this.searchForm.size"
      layout="total, sizes, prev, pager, next, jumper"
      :total="this.total"
    >
    </el-pagination>
    <el-dialog :title="dialogTitle" :visible.sync="dialogFormVisible">
      <el-form>
        <el-form-item label="房屋编号" :label-width="formLabelWidth">
          <el-select
            v-model="addForm.houseId"
            clearable
            placeholder="请选择"
            style="float: left; width: 23%"
          >
            <el-option
              v-for="item in houseIdOptions"
              :key="item.id"
              :label="item.id"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="单价" :label-width="formLabelWidth">
          <el-input
            v-model="addForm.price"
            autocomplete="off"
            style="width: 40%"
          ></el-input>
        </el-form-item>
        <el-form-item label="数量" :label-width="formLabelWidth">
          <el-input
            v-model="addForm.number"
            autocomplete="off"
            style="width: 40%"
          ></el-input>
        </el-form-item>
        <el-form-item label="费用类型" :label-width="formLabelWidth">
          <el-select
            v-model="addForm.type"
            filterable
            default-first-option
            placeholder="请选择"
            style="float: left; width: 23%"
          >
            <el-option
              v-for="item in addTypeOptions"
              :key="item.type"
              :label="item.name"
              :value="item.type"
            >
              <span style="color: #8492a6; font-size: 13px">{{
                item.name
              }}</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="时间" :label-width="formLabelWidth">
          <el-date-picker
            v-model="addForm.time"
            type="month"
            placeholder="请选择"
            format="yyyy-MM"
            value-format="yyyy-MM"
          >
          </el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="addFee">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "FeeMaintain",
  created() {
    this.getFeeList();
  },
  data() {
    return {
      //表示当前选中的index
      handlerId: 0,
      tabaleIndex: 0,
      total: 10,
      input3: "",
      dialogTitle: "",
      searchForm: {
        houseId: undefined,
        type: undefined,
        startDate: "",
        endDate: "",
        size: 10,
        currentPage: 1,
      },
      addForm: {
        id: undefined,
        houseId: undefined,
        price: undefined,
        number: undefined,
        type: undefined,
        time: ""
      },
      houseIdOptions: [],
      dialogFormVisible: false,
      excelFile: {},
      tableData: [],
      options: [],
      addTypeOptions: [
        { type: 0, name: "水费" },
        { type: 1, name: "电费" },
        { type: 2, name: "燃气费" },
        { type: 3, name: "物业管理费" },
      ],
      typeOptions: [
        { type: 0, name: "水费" },
        { type: 1, name: "电费" },
        { type: 2, name: "燃气费" },
        { type: 3, name: "物业管理费" },
        { type: undefined, name: "全部" },
      ],
      formLabelWidth: "120px",
    };
  },
  methods: {
    open(index) {
      const feeId = this.tableData[index].id;
      this.$confirm("确认已缴费?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.confirmFee(feeId);
        })
        .catch(() => {
          console.log();
        });
    },
    handleSizeChange(newSize) {
      console.log(newSize);
      this.searchForm.size = newSize;
      this.getComplaintList();
    },
    handleCurrentChange(newPage) {
      console.log(newPage);
      this.searchForm.currentPage = newPage;
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
      this.getFeeList();
    },
    getFeeList() {
      const url = "/system/feeservice/getfeelist";
      this.$http.post(url, this.searchForm).then((res) => {
        if (res.data.success) {
          console.log(res);
          this.tableData = res.data.data.records;
          this.total = res.data.data.total;
          this.contextChange();
        } else {
          this.$message.error(res.data.message);
        }
      });
    },
    getAllHouses() {
      console.log("获取所有房屋");
      const url = "/system/baseinformationadmin/getallhouses";
      this.$http.post(url, this.addForm).then((res) => {
        if (res.data.success) {
          console.log(res.data);
          this.houseIdOptions = res.data.data.houses;
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
      this.getAllHouses();
    },
    addFee() {
      if (this.dialogTitle === "费用记录增加") {
        const url = "/system/feeservice/addfee";
        
        this.$http.post(url, this.addForm).then((res) => {
          if (res.data.success) {
            this.$message.success("添加成功");
          } else {
            this.$message.error(res.data.message);
          }
        });
      } else {
        this.updateFee();
      }
      this.dialogFormVisible = false;
      this.getFeeList();
      
    },
    updateFee() {
      const url = "/system/feeservice/updatefee";
      console.log(this.addForm);
      this.$http.post(url, this.addForm).then((res) => {
        if (res.data.success) this.$message.success("修改成功");
        else {
          this.$message.error(res.data.message);
        }
      });
    },
    contextChange() {
      for (let i = 0; i < this.tableData.length; i++) {
        switch (this.tableData[i].type) {
          case 0:
            this.tableData[i].type = "水费";
            break;
          case 1:
            this.tableData[i].type = "电费";
            break;
          case 2:
            this.tableData[i].type = "燃气费";
            break;
          case 3:
            this.tableData[i].type = "物业管理费";
            break;
        }

        switch (this.tableData[i].state) {
          case 0:
            this.tableData[i].state = "未缴纳";
            break;
          case 1:
            this.tableData[i].state = "已缴纳";
            break;
        }
      }
    },
    typeToNumber(value){
        switch(value) {
            case "水费":
                return 0;
            case "电费":
                return 1;
            case "燃气费":
                return 2;
            case "物业管理费":
                return 3;
        }
    },
    //获取系统所有的用户
    getUser() {
      const url = "/system/baseinformationadmin/getuserinformation";
      this.$http.get(url).then((res) => {
        if (res.data.success) {
          this.handlerId = res.data.data.userInformation.id;
        } else {
          this.$message.error("获取处理者信息失败");
        }
      });
    },
    timeToString(time){
        console.log(time);
        return time.format("yyyy-MM");
    },
    clearAddForm(){
        this.addForm.id = undefined;
        this.addForm.houseId = undefined;
        this.addForm.price = undefined;
        this.addForm.number = undefined;
        this.addForm.type = undefined;
        this.addForm.time = undefined;
    },
    confirmFee(feeId){
         const url = "/system/feeservice/confirmfee";
         this.clearAddForm();
         this.addForm.id = feeId;
          this.$http.post(url,this.addForm)
                    .then((res)=>{
                        if(res.data.success){
                            this.$message.success("物业费确认成功");
                        }else{
                            this.$message.error(res.data.message);
                        }
                    });
            this.getFeeList();
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