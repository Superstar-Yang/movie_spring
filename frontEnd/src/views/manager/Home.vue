<template>
  <div style="display: flex;justify-content: space-evenly">
    <div style="width: 50%">
      <div class="card_title">电影评分榜</div>
      <el-card>
        <div v-for="(item,index) in data.filmList" :key="item.id" style="padding: 20px;display: flex;grid-gap: 10px">
          <div style="font-size: 3rem;font-weight: bold;padding:0 10px">
            <div style="color:gold;" v-if="index === 0">1</div>
            <div style="color:silver" v-else-if="index === 1">2</div>
            <div style="color:peru" v-else-if="index === 2">3</div>
            <div style="color:#ccc" v-else>{{ index + 1 }}</div>
          </div>
          <img :src="item.cover" alt="" style="border-radius: 20px;width: 40%;cursor: pointer"
               @click="goDetail(item.id)">
          <div style="display: flex;flex-direction: column;margin-left: 10px">
            <div style="font-size: 20px;font-weight: bold;">{{ item.name }}</div>
            <el-rate v-model="item.score" show-score allow-half disabled style="margin-bottom: 10px"></el-rate>
            <span style="line-height: 2rem;">{{ item.descr }}</span>
          </div>
        </div>
      </el-card>
    </div>
    <div style="width: 50%">
      <div class="card_title">系统公告</div>
      <el-timeline>
        <el-timeline-item v-for="item in data.timeList" :key="item.id" :timestamp="item.time" placement="top">
          <el-card>
            <h4 style="font-size: 16px;font-weight: bold">{{ item.title }}</h4>
            <p style="color:#ccc">{{ item.content }}</p>
          </el-card>
        </el-timeline-item>
      </el-timeline>
    </div>
  </div>
</template>

<script setup>
import {reactive} from "vue";
import request from "@/utils/request";

const data = reactive({
  user: JSON.parse(localStorage.getItem('system-user') || '{}'),
  timeList: [],
  filmList: []
})
const load = () => {
  request('/notice/selectAll').then(res => {
    data.timeList = res?.data
  })
}
request.get('film/selectRanking').then(res => {
  console.log(res)
  data.filmList = res.data
})
const goDetail = (id) => {
  location.href = '/detail?id=' + id
}
load()
</script>
<style lang="scss" scoped>
.card_title {
  margin: 20px;
  font-weight: bold;
  font-size: 20px
}
</style>
