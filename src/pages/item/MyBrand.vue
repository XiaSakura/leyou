<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
  <div>
    <v-card>
      <!--卡片的头部-->
      <v-card-title>
        <v-btn color="primary">新增</v-btn>
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
            <v-btn color="info">编辑</v-btn>
            <v-btn color="warning">删除</v-btn>
          </td>
        </template>
      </v-data-table>
    </v-card>
  </div>
</template>

<script>
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
        show:false; //控制对话框的显示
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
      search(){
        this.pagination.page=1; //防止当我们页码跳到后面的时候 搜索不到结果 pagination的监控就触发了
      }
    },
  methods: {
    getDataFromServer()
    {
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
        this.totalBrands = resp.data.total;
        // 完成赋值后，把加载状态赋值为false
        this.loading = false;
      })
    }
  }
  }

</script>

<style scoped>

</style>
