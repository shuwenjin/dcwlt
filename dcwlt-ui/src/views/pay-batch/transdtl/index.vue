<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
<!--      <el-form-item label="平台日期" prop="payDate">-->
<!--        <el-input-->
<!--          v-model="queryParams.payDate"-->
<!--          placeholder="请输入平台日期"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
      <el-form-item label="访问时间" prop="payDate">
        <el-date-picker clearable size="small"
                        v-model="queryParams.payDate"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请输入平台日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="平台流水" prop="paySerno">
        <el-input
          v-model="queryParams.paySerno"
          placeholder="请输入平台流水"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="通道流水" prop="payPathSerno">
        <el-input
          v-model="queryParams.payPathSerno"
          placeholder="请输入通道流水"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="交易批次号" prop="batchid">
        <el-input
          v-model="queryParams.batchid"
          placeholder="请输入交易批次号"
          clearable
          size="small"
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
          v-hasPermi="['system:transdtl:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
<!--      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>-->
    </el-row>

    <el-table v-loading="loading" :data="transdtlList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="平台日期" align="center" key="payDate" prop="payDate" v-if="columns[0].visible" :show-overflow-tooltip="true"/>
      <el-table-column label="平台流水" align="center" key="paySerno" prop="paySerno" v-if="columns[1].visible" :show-overflow-tooltip="true"/>
      <el-table-column label="交易批次号" align="center" key="batchId" prop="batchId" v-if="columns[2].visible" :show-overflow-tooltip="true"/>
      <el-table-column label="报文编号" align="center" key="msgType" prop="msgType" v-if="columns[3].visible" :show-overflow-tooltip="true"/>
      <el-table-column label="通道流水" align="center" key="payPathSerno" prop="payPathSerno" v-if="columns[4].visible" :show-overflow-tooltip="true"/>
      <el-table-column label="发起机构" align="center" key="instgPty" prop="instgPty" v-if="columns[5].visible" :show-overflow-tooltip="true"/>
      <el-table-column label="接收机构" align="center" key="instdPty" prop="instdPty" v-if="columns[6].visible" :show-overflow-tooltip="true"/>
      <el-table-column label="收款人名称" align="center" key="payeeName" prop="payeeName" v-if="columns[7].visible" :show-overflow-tooltip="true"/>
      <el-table-column label="柜员号" align="center" key="tellerNo" prop="tellerNo" v-if="columns[8].visible" :show-overflow-tooltip="true"/>
      <el-table-column label="分行号" align="center" key="zoneNo" prop="zoneNo" v-if="columns[9].visible" :show-overflow-tooltip="true"/>
      <el-table-column label="最后更新日期" align="center" key="lastUpDate" prop="lastUpDate" v-if="columns[10].visible" :show-overflow-tooltip="true"/>
      <el-table-column label="最后更新时间" align="center" key="lastUpTime" prop="lastUpTime" v-if="columns[11].visible" :show-overflow-tooltip="true"/>
      <el-table-column label="币种" align="center" key="ccy" prop="ccy" v-if="columns[12].visible" :show-overflow-tooltip="true"/>
      <el-table-column label="业务状态" align="center" key="trxStatus" prop="trxStatus" v-if="columns[13].visible" :show-overflow-tooltip="true"/>
      <el-table-column label="业务处理码" align="center" key="trxRetCode" prop="trxRetCode" v-if="columns[14].visible" :show-overflow-tooltip="true"/>
      <el-table-column label="业务处理信息" align="center" key="trxRetMsg" prop="trxRetMsg" v-if="columns[15].visible" :show-overflow-tooltip="true"/>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改金融交易登记对话框 -->
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
import { listTransdtl, getTransdtl, delTransdtl, addTransdtl, updateTransdtl } from "@/api/pay-batch/transdtl";

export default {
  name: "Transdtl",
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
      // 金融交易登记表格数据
      transdtlList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        payDate: null,
        paySerno: null,
        payPathSerno: null,
        batchid: null,
        msgType: null,
      },
      // 表单参数
      form: {},
      columns: [
        { key: 0, label: `平台日期`, visible: true },
        { key: 1, label: `平台流水`, visible: true },
        { key: 2, label: `交易批次号`, visible: true },
        { key: 3, label: `报文编号`, visible: true },
        { key: 4, label: `通道流水`, visible: true },
        { key: 5, label: `发起机构`, visible: true },
        { key: 6, label: `接收机构`, visible: true },
        { key: 7, label: `收款人名称`, visible: true },
        { key: 8, label: `柜员号`, visible: true },
        { key: 9, label: `分行号`, visible: true },
        { key: 10, label: `最后更新日期`, visible: true },
        { key: 11, label: `最后更新时间`, visible: true },
        { key: 12, label: `币种`, visible: true },
        { key: 13, label: `业务状态`, visible: true },
        { key: 14, label: `业务处理码`, visible: true },
        { key: 15, label: `业务处理信息`, visible: true }
      ],
      // 表单校验
      rules: {
        payDate: [
          { required: true, message: "平台日期不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询金融交易登记列表 */
    getList() {
      this.loading = true;
      listTransdtl(this.queryParams).then(response => {
        this.transdtlList = response.rows;
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
        payDate: null,
        paySerno: null,
        paytime: null,
        direct: null,
        payflag: null,
        operstep: null,
        operstatus: "0",
        trxstatus: "0",
        trxretcode: null,
        trxretmsg: null,
        coreprocstatus: "0",
        corereqdate: null,
        corereqserno: null,
        coreacctdate: null,
        coreserno: null,
        coreretcode: null,
        coreretmsg: null,
        paypathdatetime: null,
        payPathSerno: null,
        pathprocstatus: "0",
        paypathrspstatus: "0",
        paypathretcode: null,
        paypathretmsg: null,
        paypathretdate: null,
        paypathretserno: null,
        batchid: null,
        busichnl: null,
        busichnl2: null,
        busisysdate: null,
        busisysserno: null,
        busisystime: null,
        msgType: null,
        busitype: null,
        busikind: null,
        instgpty: null,
        instdpty: null,
        amount: null,
        tradefundsource: null,
        tradepurpose: null,
        payerptyid: null,
        payername: null,
        payeraccttype: null,
        payeracct: null,
        payerwalletid: null,
        payerwalletname: null,
        payerwalletlv: null,
        payerwallettype: null,
        payeeptyid: null,
        payeename: null,
        payeeaccttype: null,
        payeeacct: null,
        payeewalletid: null,
        payeewalletname: null,
        payeewalletlv: null,
        payeewallettype: null,
        protocolnum: null,
        ccy: null,
        tellerno: null,
        zoneno: null,
        brno: null,
        acctbrno: null,
        origchnl: null,
        origchnl2: null,
        origchnldtl: null,
        origmsgtype: null,
        origpaypathdate: null,
        origpaypathserno: null,
        summary: null,
        endtoendid: null,
        lastupjrnno: null,
        lastupdate: null,
        lastuptime: null,
        narrative: null,
        remark: null
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
      this.ids = selection.map(item => item.payDate)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加金融交易登记";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const payDate = row.payDate || this.ids
      getTransdtl(payDate).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改金融交易登记";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.payDate != null) {
            updateTransdtl(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addTransdtl(this.form).then(response => {
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
      const paydates = row.payDate || this.ids;
      this.$confirm('是否确认删除金融交易登记编号为"' + paydates + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delTransdtl(paydates);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('dcwlt-pay-batch/transdtl/export', {
        ...this.queryParams
      }, `system_transdtl.xlsx`)
    }
  }
};
</script>
