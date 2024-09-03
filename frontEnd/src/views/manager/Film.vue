<template>
  <div>

    <div class="card" style="margin-bottom: 5px;">
      <el-input v-model="data.name" style="width: 300px; margin-right: 10px"
                placeholder="请输入电影名称查询"></el-input>
      <el-button type="primary" @click="load">查询</el-button>
      <el-button type="info" style="margin: 0 10px" @click="reset">重置</el-button>
    </div>

    <div class="card" style="margin-bottom: 5px">
      <div style="margin-bottom: 10px">
        <el-button type="primary" @click="handleAdd">新增</el-button>
      </div>
      <el-table :data="data.tableData" stripe fit border>
        <el-table-column label="名称" prop="name" show-overflow-tooltip />
        <el-table-column label="封面">
          <template #default="scope">
            <el-image :src="scope.row.cover" style="width: 40px; height: 40px; border-radius: 10px"></el-image>
          </template>
        </el-table-column>
        <el-table-column label="描述" prop="descr" show-overflow-tooltip />
        <el-table-column label="年份" prop="year"/>
        <el-table-column label="导演" prop="director"/>
        <el-table-column label="演员" prop="actors" show-overflow-tooltip/>
        <el-table-column label="分类" prop="categoryId"/>
        <el-table-column label="国家" prop="country"/>
        <el-table-column label="语言" prop="language"/>
        <el-table-column label="上映时间" prop="date"/>
        <el-table-column label="片场" prop="duration"/>
        <el-table-column label="IMDB" prop="imdb"/>
        <el-table-column label="操作" align="center" width="160">
          <template #default="scope">
            <el-button type="primary" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button type="danger" @click="handleDelete(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div class="card">
      <el-pagination background :page-sizes=[10,20,30,40] layout="sizes, prev, pager, next, jumper,->,total"
                     v-model:page-size="data.pageSize" v-model:current-page="data.pageNum" :total="data.total"/>
    </div>

    <el-dialog title="信息" width="40%" v-model="data.formVisible" :close-on-click-modal="false" destroy-on-close>
      <el-form :model="data.form" label-width="100px" style="padding-right: 50px" label-suffix=":">
        <el-form-item label="名称" prop="name">
          <el-input v-model="data.form.name" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="封面" prop="cover">
          <el-upload :action="uploadUrl" list-type="picture" :on-success="handleImgSuccess">
            <el-button type="primary">上传图片</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="描述" prop="descr">
          <el-input rows="5" type="textarea" v-model="data.form.descr" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="年份" prop="year">
          <el-input v-model="data.form.year" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="导演" prop="director">
          <el-input  v-model="data.form.director" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="演员" prop="actors">
          <el-input rows="4" type="textarea" v-model="data.form.actors" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="分类" prop="categoryId">
          <el-input v-model="data.form.categoryId" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="国家" prop="country">
          <el-input v-model="data.form.country" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="语言" prop="language">
          <el-input v-model="data.form.language" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="上映时间" prop="date">
          <el-input v-model="data.form.date" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="片场" prop="duration">
          <el-input v-model="data.form.duration" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="IMDB" prop="imdb">
          <el-input v-model="data.form.imdb" autocomplete="off"/>
        </el-form-item>
      </el-form>
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

// 文件上传的接口地址
const uploadUrl = import.meta.env.VITE_BASE_URL + '/files/upload'

const data = reactive({
  pageNum: 1,
  pageSize: 10,
  total: 0,
  formVisible: false,
  form: {},
  tableData: [],
  name: null
})

// 分页查询
const load = () => {
  request.get('/film/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      name: data.name
    }
  }).then(res => {
    data.tableData = res.data?.list
    data.total = res.data?.total
  })
}

// 新增
const handleAdd = () => {
  data.form = {}
  data.formVisible = true
}

// 编辑
const handleEdit = (row) => {
  data.form = JSON.parse(JSON.stringify(row))
  data.formVisible = true
}

// 新增保存
const add = () => {
  request.post('/film/add', data.form).then(res => {
    if (res.status === 200) {
      load()
      ElMessage.success('操作成功')
      data.formVisible = false
    } else {
      ElMessage.error(res.statusText)
    }
  })
}

// 编辑保存
const update = () => {
  request.put('/film/update', data.form).then(res => {
    if (res.status === 200) {
      load()
      ElMessage.success('操作成功')
      data.formVisible = false
    } else {
      ElMessage.error(res.statusText)
    }
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
    request.delete('/film/delete/' + id).then(res => {
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
  data.name = null
  load()
}

// 处理文件上传的钩子
const handleImgSuccess = (res) => {
  data.form.avatar = res.data  // res.data就是文件上传返回的文件路径，获取到路径后赋值表单的属性
}

load()
</script>
