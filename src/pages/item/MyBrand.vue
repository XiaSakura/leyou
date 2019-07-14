<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
  <div>
    <v-card>
      <!--卡片的头部-->
      <v-card-title>
        <v-btn color="primary" @click="triggerBrand()">新增</v-btn>
        <!--空间隔离组件-->
        <v-spacer/>
        <v-flex xs4>
          <v-text-field label="输入关键字搜索" hide-details v-model="search" append-icon="search"></v-text-field>
        </v-flex>
      </v-card-title>
      <v-divider/>
      <v-data-table
        :headers=" headers"
        :items="brands"
        :search="search"
        :pagination.sync="pagination"
        :total-items="totalBrands"
        :loading="loading"
        class="elevation-1"
      >
        <template slot="items" slot-scope="props">
          <td>{{ props.item.id }}</td>
          <td class="text-xs-center">{{ props.item.name }}</td>
          <td class="text-xs-center">
            <img v-if="props.item.image" :src="props.item.image" width="130" height="40">
            <span v-else>无</span>
          </td>
          <td class="text-xs-center">{{ props.item.letter }}</td>
          <td class="text-xs-center">
            <v-btn color="info" @click="editBrand(props.item)">编辑</v-btn>
            <v-btn color="warning" @click="deleteBrand(props.item.id)">删除</v-btn>
          </td>
        </template>
      </v-data-table>
    </v-card>
    <!--对话框 用于更新和插入品牌-->
    <v-dialog max-width="500" v-model="show" persistent>
      <v-card>
        <!--对话框的标题-->
        <v-toolbar dense dark color="primary">
          <v-toolbar-title>{{isEdit?"修改":"新增"}}品牌</v-toolbar-title>
          <v-spacer/>
          <v-btn icon @click="triggerBrand()">
            <v-icon>close</v-icon>
          </v-btn>
        </v-toolbar>
          <!--对话框的内容，表单-->
        <v-card-text class="px-5">
          <MyBrandForm @close="triggerBrand" :oldBrand="oldBrand" :isEdit="isEdit"></MyBrandForm>
        </v-card-text>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
  import MyBrandForm from './MyBrandForm';

  export default {
    name: "MyBrand",
    data() {
      return {
        search: '', // 搜索过滤字段
        totalBrands: 0, //总条数
        brands: [], //当前页品牌数据
        loading: true, //加载中
        pagination: {}, //分页信息
        headers: [ //头信息 value这个需要和响应信息里面的字段一样
          {text: 'id', align: 'center', value: 'id'},
          {text: '名称', align: 'center', sortable: false, value: 'name'},
          {text: 'LOGO', align: 'center', sortable: false, value: 'image'},
          {text: '首字母', align: 'center', value: 'letter', sortable: true,},
          {text: '操作', align: 'center', value: 'id', sortable: false}
        ],
        show: false,//控制对话框的显示,
        oldBrand: {}, //用于接收用来编辑的brand数据
        isEdit: false, //是否是编辑
      }
    },
    mounted() { // 渲染后执行
      // 查询数据
      this.getDataFromServer();
    },
    watch: {
      pagination: { //监控paination属性
        deep: true,
        handler(val) {
          // 变化后的回调函数，这里我们再次调用getDataFromServer即可
          this.getDataFromServer();
        }
      },
      search() {
        this.pagination.page = 1; //防止当我们页码跳到后面的时候 搜索不到结果 pagination的监控就触发了
      }
    },
    methods: {
      getDataFromServer() {
        //向后台发起请求
        this.$http.get("/item/brand/page", {
          params: {
            key: this.search,
            page: this.pagination.page, //当前页
            rows: this.pagination.rowsPerPage,//每页大小
            sortBy: this.pagination.sortBy, //排序字段
            desc: this.pagination.descending, //是否降序
          }
        }).then(resp => {
          this.brands = resp.data.items;
          this.totalBrands = resp.data.totalPage;
          // 完成赋值后，把加载状态赋值为false
          this.loading = false;
        })
      },
      triggerBrand() {
        //设置弹窗可见
        this.show = !this.show;
        // 把oldBrand变为null
        this.oldBrand = null;
        this.isEdit = false;
        this.getDataFromServer();
      },
      editBrand(oldBrand) {
        //控制弹窗可见
        this.show = true;
        // 根据品牌信息查询商品分类
        this.$http.get("/item/category/bid/" + oldBrand.id).then(({data}) => {
          // 控制弹窗可见：
          this.show = true;
          // 获取要编辑的brand
          this.oldBrand = oldBrand;
          // 回显商品分类
          this.oldBrand.categories = data;
          this.isEdit = true;
        });
      },
      deleteBrand(bid) {
        this.$message.confirm('确认要删除？')
          .then(() => {
            // 如果要添加，则将specifications初始化为空
            this.$http.delete("/item/brand/" + bid).then(() => {
              this.$message.success("删除成功了");
              this.getDataFromServer();
            }).catch(() => {
              this.$message.error("删除失败了")
            })
          })
      }
    },
    components: {
      MyBrandForm
    }
  }

</script>

<style scoped>

</style>
