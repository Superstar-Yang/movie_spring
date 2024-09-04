<template>
  <div style="display: flex;align-items:flex-start;grid-gap: 20px">
    <div class="card" style="padding:20px;flex:1;border-radius: 10px;">
      <div style="display:flex;flex:1;justify-content: space-between">
        <div style="flex:1;font-weight: bold;font-size: 20px;">{{data.film.name}}</div>
        <div>
          <el-button type="primary" plain>写短评</el-button>
          <el-button type="success" plain>写长评</el-button>
        </div>
      </div>
      <div>
        <el-rate v-model="data.film.score" disabled allow-half show-score />
      </div>
      <div style="display: flex;grid-gap: 20px">
        <img :src="data.film.cover" alt="" style="width: 200px;border-radius: 15px" />
        <div style="flex:1;display: flex;flex-direction: column;justify-content: space-around">
          <div><strong>导演：</strong>{{data.film.director}}</div>
          <div><strong>主演：</strong>{{data.film.actors}}</div>
          <div><strong>类型：</strong>{{data.film.categoryName}}</div>
          <div><strong>国家地区：</strong>{{data.film.country}}</div>
          <div><strong>语言：</strong>{{data.film.language}}</div>
          <div><strong>上映时间：</strong>{{data.film.date}}</div>
          <div><strong>片场：</strong>{{data.film.duration}}</div>
          <div><strong>IMDB：</strong>{{data.film.imdb}}</div>
        </div>
      </div>
      <div style="margin-top: 20px"><span style="font-size: 20px;font-weight: bold;color:#1967e3;">{{data.film.name}}</span>的剧情简介</div>
      <div style="margin-top: 5px;color:#666;text-align: justify">{{data.film.descr}}</div>
    </div>
    <div class="card" style="width: 300px;font-weight: bold;font-size: 20px;border-radius: 10px">
      <div>推荐电影</div>
      <div style="cursor:pointer;padding:10px;margin-bottom: 10px" v-for="item in data.reCommendList" :key="item.id" @click="goDetail(item.id)">
        <img :src="item.cover" alt="" style="border-radius: 10px">
        <div style="margin: 5px 0">{{item.name}}</div>
        <div>
          <el-rate disabled v-model="item.score" allow-half show-score />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
  import {reactive} from "vue";
  import request from "@/utils/request";
  import router from "@/router";

  console.log(router)
  const data = reactive({
    id:router.currentRoute.value.query.id,
    film:{},
    reCommendList:[]
  })
  request.get('/film/selectById/'+data.id).then(res=>{
    data.film = res.data
  })
  request.get('film/selectRecommend/'+data.id).then(res=>{
    data.reCommendList = res.data
  })
  const goDetail = (id)=>{
    location.href = '/detail?id='+id
  }
</script>

<style lang="scss" scoped>

</style>
