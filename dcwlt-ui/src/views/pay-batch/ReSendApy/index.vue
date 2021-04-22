<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="88px">
      <el-form-item label="报文标识号" prop="msgid">
        <el-input
          v-model="queryParams.msgId"
          placeholder="请输入报文标识号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="平台日期" prop="paydate">
        <el-date-picker clearable size="small"
                        v-model="queryParams.payDate"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="选择平台日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-refresh"
          size="mini"
          @click="handleResend"
          v-hasPermi="['pay-batch:ReSendApy:resend']"
        >机构对账汇总核对重发申请</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['pay-batch:ReSendApy:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="ReSendApyList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="报文标识号" align="center" prop="msgId" v-if="columns[0].visible" show-overflow-tooltip/>
      <el-table-column label="平台日期" align="center" prop="payDate" width="180" v-if="columns[1].visible">
       <!-- <template slot-scope="scope">
          <span>{{ parseTime(scope.row.paydate, '{y}-{m}-{d}') }}</span>
        </template> -->
      </el-table-column>
      <el-table-column label="平台时间" align="center" prop="payTime" v-if="columns[2].visible" />
      <el-table-column label="平台流水" align="center" prop="paySerNo" v-if="columns[3].visible" />
      <el-table-column label="报文编号" align="center" prop="pkgNo" v-if="columns[4].visible" />
      <el-table-column label="报文发送时间" align="center" prop="senderDateTime" v-if="columns[5].visible" />
      <el-table-column label="接收机构" align="center" prop="instdDrctPty" v-if="columns[6].visible" />
      <el-table-column label="业务处理状态" align="center" prop="procStatus" :formatter="procStatusFormat" v-if="columns[7].visible" />
      <el-table-column label="业务拒绝码" align="center" prop="rejectCode" v-if="columns[8].visible" />
      <el-table-column label="业务拒绝信息" align="center" prop="rejectInfo" v-if="columns[9].visible" />
<!--      <el-table-column label="柜员号" align="center" prop="tlrNo" v-if="columns[10].visible" />
 -->
      <el-table-column label="备注" align="center" prop="remark" v-if="columns[11].visible" />
      <el-table-column label="信息内容" align="center" prop="messageContext" v-if="columns[12].visible" />
      <el-table-column label="最后更新日期" align="center" prop="lastUpDate" v-if="columns[13].visible" />
      <el-table-column label="最后更新时间" align="center" prop="lastUpTime" v-if="columns[14].visible" />
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 机构对账汇总核对重发申请 -->
    <el-dialog title="机构对账汇总核对重发申请" :visible.sync="open711" width="700px" append-to-body>
      <el-form ref="form711" :model="form711" :rules="rules711" label-width="120px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="交易批次号" prop="batchId">
              <el-input v-model.trim="form711.batchId" placeholder="请输入交易批次号"/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="resendApply711">确 定</el-button>
        <el-button @click="open711 = false">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
  import { listReSendApy, addReSendApy } from "@/api/pay-batch/ReSendApy";

  export default {
    name: "ReSendApy",
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
        // 交易重发申请表格数据
        ReSendApyList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        open711: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          msgId: null,
          payDate: null
        },
        // 列信息
        columns: [
          { key: 0, label: `报文标识号`, visible: true },
          { key: 1, label: `平台日期`, visible: true },
          { key: 2, label: `平台时间`, visible: true },
          { key: 3, label: `平台流水`, visible: true },
          { key: 4, label: `报文编号`, visible: true },
          { key: 5, label: `报文发送时间`, visible: true },
          { key: 6, label: `接收机构`, visible: true },
          { key: 7, label: `业务处理状态`, visible: true },
          { key: 8, label: `业务拒绝码`, visible: false },
          { key: 9, label: `业务拒绝信息`, visible: false },
          { key: 10, label: `柜员号`, visible: true },
          { key: 11, label: `备注`, visible: false },
          { key: 12, label: `信息内容`, visible: false },
          { key: 13, label: `最后更新日期`, visible: false },
          { key: 14, label: `最后更新时间`, visible: false }
        ],
        // 表单参数
        queryForm: {},
        form711: {},
        rules711: {
          batchId: [
            {required: true, message: "交易批次号不能为空", trigger: "blur"}
          ]
        },
        // 表单校验
        rules: {
        }
      };
    },
    created() {
      this.getList();

      //业务处理状态数据字典
    this.getDicts("PR").then(response => {
      this.procStatusOptions = response.data;
      console.info(this.procStatusOptions);
    });
    },
    methods: {
      /** 查询交易重发申请列表 */
      getList() {
        this.loading = true;
        listReSendApy(this.queryParams).then(response => {
          this.ReSendApyList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },

       //业务处理状态数据字典
      procStatusFormat(row, column) {
        return this.selectDictLabel(this.procStatusOptions, row.procStatus);
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
        this.ids = selection.map(item => item.msgid)
        this.single = selection.length!==1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleResend() {
        this.open711 = true;
      },
      /** 提交按钮 */
      resendApply711() {
        this.$refs["form711"].validate(valid => {
          if (valid) {
            if (this.form711.batchId.length !== 13) {
              this.msgError("交易批次号格式不正确，请检查");
              return;
            }
            addReSendApy(this.form711).then(() => {
              this.msgSuccess("重发成功");
            });
          }
        })
      },
      /** 导出按钮操作 */
      handleExport() {
        this.download('pay-batch/ReSendApy/export', {
          ...this.queryParams
        }, `pay-batch_ReSendApy.xlsx`)
      }
    }
  };
</script>
