<template>
  <div class="card" style="margin-bottom: 5px;">
    <el-input v-model="data.name" style="width: 300px; margin-right: 10px" placeholder="请输入名称查询"></el-input>
    <el-button type="primary" @click="load">查询</el-button>
    <el-button type="info" style="margin: 0 10px" @click="reset">重置</el-button>
  </div>
  <div class="card">
    <el-button :class="{'active':data.categoryId === null}" @click="changeCategory(null)">全部</el-button>
    <el-button :class="{'active':data.categoryId === item.id}" v-for="item in data.cateGoryList" :key="item.id" @click="changeCategory(item.id)">{{ item.name }}
    </el-button>
  </div>
  <div class="card">
    <el-row :gutter="20">
      <el-col :span="6" v-for="item in data.filmData" :key="item.id" @click="goDetail(item.id)">
        <img :src="item.cover" alt="" style="width: 100%;height:400px;border-radius: 10px;cursor: pointer">
        <div class="line1" style="margin:5px 0;font-size:13px;font-weight: bold;">{{ item.name }}</div>
        <div style="margin:5px 0;display: flex;align-items: center;">
          <el-rate v-model="item.score" disabled allow-half show-score>{{ item.score }}</el-rate>
          <div style="flex: 1;text-align: right;color:#1967e3">{{item.commentNum}}人评论</div>
        </div>
        <div class="line3" style="margin: 10px 0;font-size:13px;">{{item.descr}}</div>
      </el-col>
    </el-row>
  </div>
  <div class="card">
    <el-pagination background :page-sizes=[8,16,24,32] layout=" sizes, prev, pager, next, jumper,->,total"
                   v-model:page-size="data.pageSize" v-model:current-page="data.pageNum" :total="data.total"/>
  </div>
</template>

<script setup>
import {reactive} from "vue";
import request from "@/utils/request";

const data = reactive({
  name: null,
  pageNum: 1,
  pageSize: 8,
  total: 0,
  filmData: [],
  categoryId: null,
  cateGoryList: []
})
const loadCategory = () => request.get('/category/selectPage').then(res => {
  data.cateGoryList = res.data.list
})
loadCategory()
const changeCategory = (categoryId) => {
  data.categoryId = categoryId
  load()
}
const load = () => {
  request.get('/film/selectPage', {
    params: {
          name: data.name,
          pageNum: data.pageNum,
          pageSize: data.pageSize,
          categoryId: data.categoryId
        }
  }).then(res => {
    data.filmData = res.data.list
    data.total = res.data.total
  })
}
load()
const reset = () => {
  data.name = null;
  load()
}

const goDetail = (id)=>{
  location.href = '/detail?id='+id
}

</script>

<style lang="scss" scoped>
.active {
  color: #fff;
  background: #3f2b96;
}
</style>
