<template>
  <div class="register_container"> 
        <div class="register_box"> 
            <ul class="resp-tabs-list"> 
                <li class="register-item-left"><h2> <span>注册</span> </h2></li>
                <router-link to="/login" tag="li" class="register-item-right"><h2> <span>登录</span> </h2></router-link>
            </ul>
            注册
        </div>
    </div>
</template>

<script>
export default {
    name:"Register"
}
</script>

<style>
.register_container {
        background-color: #2b4b6b;
        height: 100%;
        
    }

.register_box {
    width: 400px;
    height: 345px;
    border-radius: 8px;
    background: #ffffff;
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%,-50%);
}

ul {
    margin: 0px;
    padding: 0px;
    overflow: hidden;
}
.resp-tabs-list {
    list-style: none;
    text-align: center;
}

.register-item-left {
    float: left;
    color: #4d6e8f;
    background: #ffffff;
}

.register-item-right {
    float: right;
    color: #ffffff;
     background-color:  #4d6e8f;
}

 li {
    
    font-size: 1em;
    cursor: pointer;
    margin: 0;
    outline: none;
    transition: all 0.3s ease-out;
    width: 50%;
    padding:  0;
}



</style>