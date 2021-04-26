<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      label-width="100px"
    >
      <el-form-item label="异常登记流水" prop="exceptSerNO">
        <el-input
          v-model="queryParams.exceptSerNO"
          placeholder="请输入异常登记流水"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="异常交易场景" prop="exceptScenario">
        <el-input
          v-model="queryParams.exceptScenario"
          placeholder="请输入异常交易场景 "
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置</el-button
        >
      </el-form-item>
    </el-form>
    <el-table
      v-loading="loading"
      :data="list.slice((pageNum - 1) * pageSize, pageNum * pageSize)"
      style="width: 100%"
    >
      <el-table-column label="序号" type="index" align="center">
        <template slot-scope="scope">
          <span>{{ (pageNum - 1) * pageSize + scope.$index + 1 }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="异常登记流水"
        align="center"
        prop="exceptSerNO"
        width="120"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        label="异常登记日期"
        align="center"
        prop="exceptDate"
        width="100"
      />
      <el-table-column
        label="异常交易场景"
        align="center"
        prop="exceptScenario"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        label="异常请求信息"
        align="center"
        prop="excepParams"
        width="180"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        label="异常内容"
        align="center"
        prop="excepContext"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        label="异常登记时间"
        align="center"
        prop="exceptTime"
        width="180"
      />

      <el-table-column
        label="更新日期"
        align="center"
        prop="lastUpDate"
        width="90"
      />

      <el-table-column
        label="更新时间"
        align="center"
        prop="lastUpTime"
        width="90"
      />
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="pageNum"
      :limit.sync="pageSize"
    />
  </div>
</template>

<script>
import { list } from "@/api/monitor/exmonitor";

export default {
  name: "Exmonitor",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 总条数
      total: 0,
      // 表格数据
      list: [],
      pageNum: 1,
      pageSize: 10,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询登录日志列表 */
    getList() {
      this.loading = true;
      list(this.queryParams).then((response) => {
        this.list = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
  },
};
</script>

