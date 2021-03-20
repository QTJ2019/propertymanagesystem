<template>
  <div id="GateRecordMaintain">
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <!-- <el-breadcrumb-item><a href="/">活动管理</a></el-breadcrumb-item> -->
      <el-breadcrumb-item>系统管理</el-breadcrumb-item>
      <el-breadcrumb-item>管理人员维护</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 卡片视图区域 -->
    <el-card>
      <el-row :gutter="20">
        <el-col :span="15">
          <el-input
            v-model="searchForm.gate"
            placeholder="门岗搜索"
            style="width: 20%"
          >
          </el-input>
          <el-select
            v-model="searchForm.type"
            filterable
            default-first-option
            placeholder="出入类型搜索"
            style="float: left; width: 20%"
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
            v-model="searchForm.start"
            type="datetime"
            placeholder="请选择"
            format="yyyy-MM-dd HH:mm:ss"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
          <el-date-picker
            v-model="searchForm.end"
            type="datetime"
            placeholder="请选择"
            format="yyyy-MM-dd HH:mm:ss"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
          <el-button icon="el-icon-search" @click="getGateRecordList" style="float:left;"></el-button>
        </el-col>
        <el-col :span="3">
          <el-upload
            class="upload-demo"
            :on-change="uploadGateRecordExcel"
            :limit="1"
            action="none"
            :auto-upload="false"
            accept=".xlsx, .xls"
          >
            <el-button size="small" type="primary">点击上传</el-button>
          </el-upload>
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
      <el-table-column prop="id" label="出入记录编号" width="150">
      </el-table-column>
      <el-table-column prop="gate" label="门岗" width="120">
      </el-table-column>
      <el-table-column prop="type" label="类型" width="120"> </el-table-column>
      <el-table-column prop="time" label="时间" width="160" value-format="yyyy-MM-dd HH:mm:ss">
      </el-table-column>
    </el-table>

    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="searchForm.currentPage"
      :page-sizes="[5, 10, 15, 20]"
      :page-size="searchForm.size"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
    >
    </el-pagination>
   
  </div>
</template>

<script>
export default {
  name: "GateRecordMaintain",
  created() {
    this.getGateRecordList();
  },
  data() {
    return {
      //表示当前选中的index
      handlerId: 0,
      tabaleIndex: 0,
      total: 10,
      searchForm: {
          currentPage:1,
          size:10,
          gate:"",
          type:undefined,
          start:"",
          end:""
      },
      typeOptions: [{type:0,name:"出"},{type:1,name:"入"},{type:undefined,name:"全部"}],
      tableData: [],
      formLabelWidth: "120px"
    };
  },
  methods: {
    handleSizeChange(newSize) {
      console.log(newSize);
      this.searchForm.size = newSize;
      this.getGateRecordList();
    },
    handleCurrentChange(newPage) {
      console.log(newPage);
      this.searchForm.currentPage = newPage;
      this.getGateRecordList();
    },
    uploadGateRecordExcel(files, fileList) {
      if (files.length + fileList.length > 1) {
        this.$message.error(
          "只能上传1个文件,本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件"
        );
        return;
      }
      const fd = new FormData();
      fd.append("file", fileList[0].raw);
      const url = "/system/securityadmin/uploadgaterecord";
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
      this.getGateRecordList();
    },
    getGateRecordList() {
      const url = "/system/securityadmin/getgaterecords";
      this.$http.post(url,this.searchForm).then((res) => {
        if (res.data.success) {
          console.log(res);
          this.tableData = res.data.data.gateRecords;
          this.total = res.data.data.total;
          this.contextChange();
        } else {
          this.$message.error(res.data.message);
        }
      });
    },
    contextChange() {
      for (let i = 0; i < this.tableData.length; i++) {

        switch (this.tableData[i].type) {
          case 0:
            this.tableData[i].type = "出";
            break;
          case 1:
            this.tableData[i].type = "入";
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