<template>
  <el-container class="home-container">
    <!-- 头部区域-->
    <el-header>
      <div>
        <img src="../assets/huizhang.jpg" alt="" />
        <span>物业管理系统</span>
      </div>
      <el-button type="info" @click="logout"> 退出登录 </el-button>
    </el-header>

    <!-- 侧边栏区域-->
    <el-container>
      <!-- 侧边栏菜单区域-->
      <!-- unique-opened表示每次只展开一个菜单 -->
      <el-aside :width="isCollapse ? '64px':'200px'">
          <div class="toggle-button" @click="toggleCollapse">|||</div>
        <el-menu
          background-color=" #333744"
          text-color="#fff"
          active-text-color="#ffd04b"
          unique-opened
          style="border-right-width:0px;"
          :collapse ="isCollapse"
          :collapse-transition="false"
          router
          :default-active="activePath"
        >
         <!-- 一级菜单 -->
         <!-- 每个菜单都有一个index值，通过这个index值选定菜单 -->
         <!-- item.id是数值加''转为字符 -->
          <el-submenu :style="{width:(isCollapse?'64px':'200px')}" :index="item.menuId+''" v-for="item in menuList" :key="item.menuId">
              <!-- 一级菜单模板区域 -->
            <template slot="title">
                <!-- 图标 -->
              <i class="el-icon-location"></i>
              <span class="navigation">{{item.menuName}}</span>
            </template>
            <!-- 二级菜单 -->
            <!-- 不需要用router-link，因为这个组件会根据index来实现router的功能 -->
            <el-menu-item :index="'/'+subItem.path"  v-for="subItem in item.children" :key="subItem.menuId" 
                @click="saveNavState('/'+subItem.path)"
            >
                <template slot="title">
                <!-- 图标 -->
              <!-- <i class="el-icon-location"></i> -->
              <span class="navigation2">{{subItem.menuName}}</span>
            </template>
             </el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>
     
     
      <!-- 右侧主体区域-->

      <!-- 表格都在这里输出-->
      <el-main>
        <!-- 显示内容的表格 -->
        主题区域
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  name: "Home",
  data() {
    return {
        //左侧菜单数据
        menuList:[],
        //存放图标
        iconsObj:{
            "5":"iconfont iconuser",
            "1":"iconfont iconuser",
            "4":"iconfont iconmessage",
            "3":"iconfont iconcomponent",
            "2":"iconfont iconcomponent"
        },
        isCollapse:false,  //用于控制侧边栏的伸缩
        activePath:"",//用于保存侧边栏的激活状态
    };
  },

  //声明周期函数，在组件加载完成前会被调用
  created(){
      this.getMenuList();
      this.activePath = window.sessionStorage.getItem('activePath');
  },

  methods: {
    logout() {
      window.sessionStorage.clear();
      //发送退出请求给后端
      const url = "/system/logout";
      this.$http.get(url).then(function(res){
          console.log(res);
      });
      this.$message.success("退出成功");
      this.$router.push("/login");
    },
    //该函数负责加载菜单
    async getMenuList(){
       const url = "/system/menu/getmenulist";
        this.$http.post(url)
                  .then((res)=>{
                      const data = res.data;
                       console.log(data);
                      if(data.success){
                          this.menuList = data.data.menuList;    
                      }else{
                          this.$message.error(data.message);
                      }
                  });
    },

    //控制侧边栏菜单的折叠与展开
    toggleCollapse(){
        this.isCollapse = !this.isCollapse;
    },

    //保存链接的激活状态
    saveNavState(activePath){
        window.sessionStorage.setItem('activePath',activePath);
        this.activePath = activePath;
    }
  }
}
</script>

<style >
.home-container {
  height: 100%;
}


.el-header {
  display: flex;
  justify-content: space-between;
  padding-left: 0;
  align-items: center;
  background-color: skyblue;
  color: #fff;
  font-size: 20px;
}

.el-header div {
  display: flex;
  align-items: center;
}

.el-header div span {
  margin-left: 15px;
}

.el-aside {
  background-color: #333744;
  border-right: none;
}

 .el-submenu{
    width: 200px;
}

.el-icon-location{
     float: left;
     padding-top: 15px;
}

.navigation{
    float: left;
}

.navigation2{
    float: left;
}


.el-main {
  background-color: rgb(253, 253, 250);
}

.toggle-button {
    background-color: #4a5064 ;
    font-size: 10px;
    line-height: 24px;
    color: #fff;
    text-align: center;
    letter-spacing: 0.2em;
    cursor: pointer;
}
</style>