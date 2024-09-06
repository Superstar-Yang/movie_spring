<template>
  <div style="display: flex;align-items:flex-start;grid-gap: 20px">
    <div style="display: flex;flex-direction: column;justify-content: space-between;width: 80%;">
      <div class="card" style="padding:20px;border-radius: 10px;margin-bottom: 10px;">
        <div style="display:flex;flex:1;justify-content: space-between">
          <div style="flex:1;font-weight: bold;font-size: 20px;">{{ data.film.name }}</div>
          <div>
            <el-button type="primary" plain @click="addComment('短评')">写短评</el-button>
            <el-button type="success" plain @click="addComment('长评')">写长评</el-button>
          </div>
        </div>
        <div>
          <el-rate v-model="data.film.score" disabled allow-half show-score/>
        </div>
        <div style="display: flex;grid-gap: 20px">
          <img :src="data.film.cover" alt="" style="width: 200px;border-radius: 15px"/>
          <div style="flex:1;display: flex;flex-direction: column;justify-content: space-around">
            <div><strong>导演：</strong>{{ data.film.director }}</div>
            <div><strong>主演：</strong>{{ data.film.actors }}</div>
            <div><strong>类型：</strong>{{ data.film.categoryName }}</div>
            <div><strong>国家地区：</strong>{{ data.film.country }}</div>
            <div><strong>语言：</strong>{{ data.film.language }}</div>
            <div><strong>上映时间：</strong>{{ data.film.date }}</div>
            <div><strong>片场：</strong>{{ data.film.duration }}</div>
            <div><strong>IMDB：</strong>{{ data.film.imdb }}</div>
          </div>
        </div>
        <div style="margin-top: 20px"><span
            style="font-size: 20px;font-weight: bold;color:#1967e3;">{{ data.film.name }}</span>的剧情简介
        </div>
        <div style="margin-top: 5px;color:#3f2b96;;line-height: 2rem;text-indent: 2rem">{{ data.film.descr }}</div>
      </div>
      <div class="card" style="padding:20px;border-radius: 10px;margin-bottom: 10px;">
        <span style="font-size: 20px;font-weight: bold;color:#179113;">{{
            data.film.name
          }}“短评”</span><span> ...(全部{{ data.shortTotal }}条)</span>
        <div style="padding:20px;" v-for="item in data.commentShortList" :key="item.id">
          <div style="display: flex;align-items: center;">
            <strong><i>{{ item.userName }}</i></strong>
            <div style="margin:0 10px">
              <el-rate allow-half v-model="item.score" disabled/>
            </div>
            <span style="color:#ccc">{{ item.time }}</span>
          </div>
          <div style="margin:-10px;padding:10px 0;color:#3f2b96;text-indent: 2rem;line-height: 2rem;" class="line1">
            {{ item.comment }}
          </div>
        </div>
        <el-pagination
            v-model:current-page="data.shortPageNum"
            v-model:page-size="data.shortPageSize"
            :page-sizes="[5, 10, 15, 20]"
            layout="prev, pager, next, sizes,  jumper,'->',total"
            :total="data.shortTotal"
            @current-change="loadCommentShort('短评')"
            style="padding:20px"
        />
      </div>
      <div class="card" style="padding:20px;border-radius: 10px;margin-bottom: 10px;">
        <span style="font-size: 20px;font-weight: bold;color:#974f4f;">{{
            data.film.name
          }}“长评”</span><span> ...(全部{{ data.longTotal }}条)</span>
        <div style="padding:20px;" v-for="item in data.commentLongList" :key="item.id">
          <div style="display: flex;align-items: center;">
            <strong><i>{{ item.userName }}</i></strong>
            <div style="margin:0 10px">
              <el-rate allow-half v-model="item.score" disabled/>
            </div>
            <span style="color:#ccc">{{ item.time }}</span>
          </div>
          <div class="line3" style="padding:10px 0;color:#3f2b96;line-height: 3rem;"
               v-html="item.comment"/>
          <el-button style="margin-top: -10px;color:#3f2b96;cursor: pointer;" @click="lookMore(item.comment)">
            查看更多...
          </el-button>
        </div>
        <el-pagination
            v-model:current-page="data.shortPageNum"
            v-model:page-size="data.shortPageSize"
            :page-sizes="[5, 10, 15, 20]"
            layout="prev, pager, next, sizes,  jumper,'->',total"
            :total="data.shortTotal"
            @current-change="loadCommentShort('长评')"
            style="padding:20px"
        />
      </div>
    </div>
    <div class="card" style="width: 300px;font-weight: bold;font-size: 20px;border-radius: 10px">
      <div>推荐电影</div>
      <div style="cursor:pointer;padding:10px;margin-bottom: 10px" v-for="item in data.reCommendList" :key="item.id"
           @click="goDetail(item.id)">
        <img :src="item.cover" alt="" style="border-radius: 10px">
        <div style="margin: 5px 0">{{ item.name }}</div>
        <div>
          <el-rate disabled v-model="item.score" allow-half show-score/>
        </div>
      </div>
    </div>
    <el-dialog title="评论信息" width="70%" v-model="data.formVisible" :close-on-click-modal="false" destroy-on-close>
      <el-form :model="data.form" label-width="100px" style="padding-right: 50px" label-suffix=":">
        <el-form-item label="打分" prop="score">
          <el-rate v-model="data.form.score" show-score allow-half></el-rate>
        </el-form-item>
        <el-form-item label="内容" prop="comment" v-if="data.form.type === '短评'">
          <el-input type="textarea" v-model="data.form.comment" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="内容" prop="comment" v-if="data.form.type === '长评'">
          <div style="border: 1px solid #ccc">
            <Toolbar
                style="border-bottom: 1px solid #ccc"
                :editor="editorRef"
                :defaultConfig="editorConfig"
                :mode="mode"
            />
            <Editor
                style="height: 500px; overflow-y: hidden;"
                v-model="data.form.comment"
                :defaultConfig="editorConfig"
                :mode="mode"
                @onCreated="handleCreated"
            />
          </div>
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
       <el-button @click="data.formVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">保 存</el-button>
      </span>
      </template>
    </el-dialog>
    <el-dialog title="长评内容" width="70%" v-model="data.viewVisible" :close-on-click-modal="false" destroy-on-close>
      <div style="line-height: 2rem;color:#7f7fd5;width: 100%" v-html="data.comment"></div>
      <template #footer>
      <span class="dialog-footer">
       <el-button @click="data.viewVisible = false">关 闭</el-button>
      </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import {reactive, onBeforeUnmount, shallowRef} from "vue";
import request from "@/utils/request";
import router from "@/router";
import {ElMessage} from "element-plus";
import '@wangeditor/editor/dist/css/style.css'
import {Editor, Toolbar} from '@wangeditor/editor-for-vue'

const data = reactive({
  id: router.currentRoute.value.query.id,
  film: {},
  reCommendList: [],
  formVisible: false,
  form: {},
  commentShortList: [],
  shortPageNum: 1,
  shortPageSize: 5,
  longPageNum: 1,
  longPageSize: 5,
  commentLongList: [],
  shortTotal: 0,
  user: JSON.parse(localStorage.getItem('system-user') || '{}'),
  viewVisible: false,
  comment: null
})
/*wangEditor5初始化*/
const baseUrl = import.meta.env.VITE_BASE_URL;
const editorRef = shallowRef() //编辑器实例必须用shallowRef
const mode = 'default'
const editorConfig = {MENU_CONF: {}}
/*图片上传配置*/
editorConfig.MENU_CONF['uploadImage'] = {
  server: baseUrl + '/movie_system/files/wang/upload', //服务端图片上传接口
  fieldName: 'file' //服务端图片上传接口参数
}
/*组件销毁时，也及时销毁编辑器，否则可能会造成内存泄漏*/
onBeforeUnmount(() => {
  const editor = editorRef.value;
  if (editor === null) return
  editor?.destroy()
})
/*记录editor实例*/
const handleCreated = (editor) => {
  editorRef.value = editor
}
request.get('/film/selectById/' + data.id).then(res => {
  data.film = res.data
})
request.get('film/selectRecommend/' + data.id).then(res => {
  data.reCommendList = res.data
})
const goDetail = (id) => {
  location.href = '/detail?id=' + id
}
const addComment = (type) => {
  data.form = {type: type}
  data.formVisible = true
}
const save = () => {
  data.form.userId = data.user.id
  data.form.filmId = data.id
  request.post('/comment/add', data.form).then(res => {
    if (res.status === 200) {
      ElMessage.success('评论成功')
      data.formVisible = false
      data.form?.type === '短评' ? loadCommentShort(data.form?.type) : loadCommentLong(data.form?.type)
    } else {
      ElMessage.error(res.statusText)
    }
  })
}
const loadCommentLong = (type) => {
  request.get('comment/selectPage', {
    params: {
      pageNum: data.longPageNum,
      pageSize: data.longPageSize,
      type,
      filmId: data.id
    }
  }).then(res => {
    data.commentLongList = res.data?.list
    data.longTotal = res.data.total
  })

}
const loadCommentShort = (type) => {
  request.get('comment/selectPage', {
    params: {
      pageNum: data.shortPageNum,
      pageSize: data.shortPageSize,
      type,
      filmId: data.id
    }
  }).then(res => {
    data.commentShortList = res.data?.list
    data.shortTotal = res.data.total
  })
}
const lookMore = (comment) => {
  data.comment = comment
  data.viewVisible = true
}
loadCommentShort('短评')
loadCommentLong('长评')
</script>

<style lang="scss">
.image-wrapper img {
  width: 100%;
}
</style>
