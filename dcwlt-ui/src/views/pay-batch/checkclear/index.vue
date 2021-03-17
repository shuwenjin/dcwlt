<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="118px">
      <el-form-item label="清算日期" prop="clearDate">
        <el-date-picker clearable size="mini"
          v-model="queryParams.clearDate"
          type="date"
          value-format="yyyyMMdd"
          placeholder="选择清算日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="批次号" prop="batchId">
        <el-input
          v-model="queryParams.batchId"
          placeholder="请输入批次号"
          clearable
          size="mini"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="核对报文标识号" prop="msgId">
        <el-input
          v-model="queryParams.msgId"
          placeholder="请输入核对报文标识号"
          clearable
          size="mini"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="清算报文标识号" prop="clearMsgId">
        <el-input
          v-model="queryParams.clearMsgId"
          placeholder="请输入清算报文标识号"
          clearable
          size="mini"
          @keyup.enter.native="handleQuery"
        />
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
          v-hasPermi="['pay-batch:checkclear:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="checkclearList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="核对报文标识号" align="center" prop="msgId" v-if="columns[0].visible" />
      <el-table-column label="报文发送时间" align="center" prop="senderDateTime" v-if="columns[1].visible" />
      <el-table-column label="发起机构" align="center" prop="instgDrctPty" v-if="columns[2].visible" />
      <el-table-column label="接收机构" align="center" prop="instdDrctPty" v-if="columns[3].visible" />
      <el-table-column label="备注" align="center" prop="remark" v-if="columns[4].visible" />
      <el-table-column label="清算日期" align="center" prop="clearDate" v-if="columns[5].visible">
      </el-table-column>
      <el-table-column label="清算总笔数" align="center" prop="clearCountNum" v-if="columns[6].visible" />
      <el-table-column label="清算借方总金额" align="center" prop="clearDbtTotAmt" v-if="columns[7].visible" />
      <el-table-column label="清算贷方总金额" align="center" prop="clearCbtTotAmt" v-if="columns[8].visible" />
      <el-table-column label="清算场次编号" align="center" prop="clearNetNum" v-if="columns[9].visible" />
      <el-table-column label="清算报文标识号" align="center" prop="clearMsgId" v-if="columns[10].visible" />
      <el-table-column label="清算借贷标识" align="center" prop="clearDrct" v-if="columns[11].visible" />
      <el-table-column label="清算金额" align="center" prop="clearAmt" v-if="columns[12].visible" />
      <el-table-column label="批次号" align="center" prop="batchId" v-if="columns[13].visible" />
      <el-table-column label="批次借贷标识" align="center" prop="batchDrct" v-if="columns[14].visible" />
      <el-table-column label="批次扎差净额" align="center" prop="batchNetAmt" v-if="columns[15].visible" />
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
  </div>
</template>

<script>
import { listCheckclear, getCheckclear, delCheckclear, addCheckclear, updateCheckclear } from "@/api/pay-batch/checkclear";

export default {
  name: "Checkclear",
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
      // 资金调整汇总核对表格数据
      checkclearList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        msgId: null,
        clearDate: null,
        clearMsgId: null,
        batchId: null,
      },
      // 列信息
      columns: [
        { key: 0, label: `核对报文标识号`, visible: true },
        { key: 1, label: `报文发送时间`, visible: true },
        { key: 2, label: `发起机构`, visible: true },
        { key: 3, label: `接收机构`, visible: true },
        { key: 4, label: `备注`, visible: true },
        { key: 5, label: `清算日期`, visible: true },
        { key: 6, label: `清算总笔数`, visible: true },
        { key: 7, label: `清算借方总金额`, visible: true },
        { key: 8, label: `清算贷方总金额`, visible: true },
        { key: 9, label: `清算场次编号`, visible: true },
        { key: 10, label: `清算报文标识号`, visible: true },
        { key: 11, label: `清算借贷标识`, visible: true },
        { key: 12, label: `清算金额`, visible: true },
        { key: 13, label: `批次号`, visible: true },
        { key: 14, label: `批次借贷标识`, visible: true },
        { key: 15, label: `批次扎差净额`, visible: true },
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
    /** 查询资金调整汇总核对列表 */
    getList() {
      this.loading = true;
      listCheckclear(this.queryParams).then(response => {
        this.checkclearList = response.rows;
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
        msgId: null,
        senderDateTime: null,
        instgDrctPty: null,
        instdDrctPty: null,
        remark: null,
        clearDate: null,
        clearCountNum: null,
        clearDbtTotAmt: null,
        clearCbtTotAmt: null,
        clearNetNum: null,
        clearMsgId: null,
        clearDrct: null,
        clearAmt: null,
        batchId: null,
        batchDrct: null,
        batchNetAmt: null
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
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.msgId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.msgId != null) {
            updateCheckclear(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCheckclear(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('pay-batch/checkclear/export', {
        ...this.queryParams
      }, `pay-batch_checkclear.xlsx`)
    }
  }
};
</script>
