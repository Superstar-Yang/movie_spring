<template>
  <div>

    <div class="card" style="margin-bottom: 5px;">
      <el-input v-model="data.filmName" style="width: 300px; margin-right: 10px"
                placeholder="请输入电影名称查询"></el-input>
      <el-button type="primary" @click="load">查询</el-button>
      <el-button type="info" style="margin: 0 10px" @click="reset">重置</el-button>
    </div>

    <div class="card" style="margin-bottom: 5px">
      <el-table :data="data.tableData" stripe>
        <el-table-column label="电影名称" prop="filmName"/>
        <el-table-column label="评分" prop="score">
          <template #default="scope">
            <el-rate disabled v-model="scope.row.score" allow-half/>
          </template>
        </el-table-column>
        <el-table-column label="评论" prop="comment">
          <template #default="scope">
            <el-button @click="preview(scope.row.comment)">查看评论</el-button>
          </template>
        </el-table-column>
        <el-table-column label="用户名称" prop="userName"/>
        <el-table-column label="时间" prop="time"/>
        <el-table-column label="类型" prop="type">
          <template #default="scope">
            <el-tag type="primary" v-if="scope.row.type === '短评'">短评</el-tag>
            <el-tag type="success" v-if="scope.row.type === '长评'">长评</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" width="160">
          <template #default="scope">
            <el-button type="danger" @click="handleDelete(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div class="card">
      <el-pagination background :page-sizes=[10,20,30,40] layout=" sizes, prev, pager, next, jumper,->,total"
                     v-model:page-size="data.pageSize" v-model:current-page="data.pageNum" :total="data.total"/>
    </div>

    <el-dialog title="系统公告" width="40%" v-model="data.formVisible" :close-on-click-modal="false" destroy-on-close>
      <div v-html="data.commentContext"></div>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="data.formVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">保 存</el-button>
      </span>
      </template>
    </el-dialog>

  </div>
</template>

<script setup>
import request from "@/utils/request";
import {reactive} from "vue";
import {ElMessageBox, ElMessage} from "element-plus";

const data = reactive({
  pageNum: 1,
  pageSize: 10,
  total: 0,
  formVisible: false,
  form: {},
  tableData: [],
  filmName: null,
  commentContext: null,
  user: JSON.parse(localStorage.getItem('system-user') || '{}'),
})

// 分页查询
const load = () => {
  request.get('/comment/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      filmName: data.filmName,
      userId: data.user.role === 'ADMIN' ? null : data.user.id
    }
  }).then(res => {
    data.tableData = res.data?.list
    data.total = res.data?.total
  })
}

// 弹窗保存
const save = () => {
  // data.form有id就是更新，没有就是新增
  data.form.id ? update() : add()
}

// 删除
const handleDelete = (id) => {
  ElMessageBox.confirm('删除后数据无法恢复，您确定删除吗?', '删除确认', {type: 'warning'}).then(res => {
    request.delete('/comment/delete/' + id).then(res => {
      if (res.status === 200) {
        load()
        ElMessage.success('操作成功')
      } else {
        ElMessage.error(res.statusText)
      }
    })
  }).catch(err => {
  })
}

// 重置
const reset = () => {
  data.filmName = null
  load()
}

const preview = (comment) => {
  data.formVisible = true
  data.commentContext = comment
}

load()
</script>
