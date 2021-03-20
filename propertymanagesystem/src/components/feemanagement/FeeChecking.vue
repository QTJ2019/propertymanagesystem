<template>
  <div id="FeeMaintain">
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <!-- <el-breadcrumb-item><a href="/">活动管理</a></el-breadcrumb-item> -->
      <el-breadcrumb-item>物业费管理</el-breadcrumb-item>
      <el-breadcrumb-item>物业费查看</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 卡片视图区域 -->
    <el-card>
      <el-row :gutter="20">
        <el-col :span="15">
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
          <el-button icon="el-icon-search" @click="getFeeList" style="float:left;"></el-button>
        </el-col>
      </el-row>
    </el-card>

    <el-table
      :data="tableData"
      height="500"
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
  </div>
</template>

<script>
export default {
  name: "FeeChecking",
  created() {
    this.getFeeList();
  },
  data() {
    return {
      //表示当前选中的index
      handlerId: 0,
      tabaleIndex: 0,
      total: 10,
      searchForm: {
        phone: undefined,
        type: undefined,
        startDate: "",
        endDate: "",
        size: 10,
        currentPage: 1,
      },
      houseIdOptions: [],
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
    handleSizeChange(newSize) {
      console.log(newSize);
      this.searchForm.size = newSize;
      this.getFeeList();
    },
    handleCurrentChange(newPage) {
      console.log(newPage);
      this.searchForm.currentPage = newPage;
      this.getFeeList();
    },
    getFeeList() {
        this.searchForm.phone = window.sessionStorage.getItem("phone");
      const url = "/system/feeservice/getuserfee";
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