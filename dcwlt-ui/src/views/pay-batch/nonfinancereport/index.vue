<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="报表日期">
        <el-date-picker
          v-model="daterangeReportDate"
          size="small"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['pay-batch:nonfinancereport:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="nonfinancereportList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="绑卡数量" align="center" prop="tiedCardNumber" v-if="columns[0].visible" />
      <el-table-column label="解绑数量" align="center" prop="untieNumber" v-if="columns[1].visible" />
      <el-table-column label="报表日期" align="center" prop="reportDate" v-if="columns[2].visible" />
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改非金融交易统计报表对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listNonfinancereport, getNonfinancereport, delNonfinancereport, addNonfinancereport, updateNonfinancereport } from "@/api/pay-batch/nonfinancereport";

export default {
  name: "Nonfinancereport",
  components: {
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 非金融交易统计报表表格数据
      nonfinancereportList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 报表日期时间范围
      daterangeReportDate: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        reportDate: null
      },
      // 列信息
      columns: [
        { key: 0, label: `绑卡数量`, visible: true },
        { key: 1, label: `解绑数量`, visible: true },
        { key: 2, label: `报表日期`, visible: true },
      ],
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询非金融交易统计报表列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeReportDate && '' != this.daterangeReportDate) {
        this.queryParams.params["beginReportDate"] = this.daterangeReportDate[0];
        this.queryParams.params["endReportDate"] = this.daterangeReportDate[1];
      }
      listNonfinancereport(this.queryParams).then(response => {
        this.nonfinancereportList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        tiedCardNumber: null,
        untieNumber: null,
        reportDate: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangeReportDate = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.reportDate)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加非金融交易统计报表";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const reportDate = row.reportDate || this.ids
      getNonfinancereport(reportDate).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改非金融交易统计报表";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.reportDate != null) {
            updateNonfinancereport(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addNonfinancereport(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const reportDates = row.reportDate || this.ids;
      this.$confirm('是否确认删除非金融交易统计报表编号为"' + reportDates + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delNonfinancereport(reportDates);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('pay-batch/nonfinancereport/export', {
        ...this.queryParams
      }, `pay-batch_nonfinancereport.xlsx`)
    }
  }
};
</script>
