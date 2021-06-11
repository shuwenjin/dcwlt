<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="88px">
      <el-form-item label="平台日期" prop="paydate">
        <el-date-picker clearable size="small" v-model="queryParams.workdate" type="date" value-format="yyyyMMdd"
          placeholder="选择平台日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="交易批次号" prop="batchid">
        <el-input v-model="queryParams.batchId" placeholder="请输入交易批次号" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8">
      <right-toolbar @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>
    <el-table v-loading="loading" :data="checkpathdtlList">
      <el-table-column label="业务日期" align="center" prop="workdate" v-if="columns[0].visible" />
      <el-table-column label="报文标识号" align="center" prop="msgId" v-if="columns[1].visible" />
      <el-table-column label="交易批次号" align="center" prop="batchId" v-if="columns[2].visible" />
      <el-table-column label="分片号" align="center" prop="splitNum" v-if="columns[3].visible" />
      <el-table-column label="文件名" align="center" prop="fileName" v-if="columns[4].visible" />
      <el-table-column label="业务处理时间" align="center" prop="dtlBizTime" v-if="columns[5].visible" />
      <el-table-column label="报文编号" align="center" prop="msgType" v-if="columns[6].visible" />
      <el-table-column label="明细的报文标识号" align="center" prop="dtlMsgId" v-if="columns[7].visible" />
      <el-table-column label="发起机构" align="center" prop="instgDrctPty" v-if="columns[8].visible" />
      <el-table-column label="付款机构编码" align="center" prop="dbitparty" v-if="columns[9].visible" />
      <el-table-column label="收款机构编码" align="center" prop="crdtparty" v-if="columns[10].visible" />
      <el-table-column label="货币代码" align="center" prop="ccy" v-if="columns[11].visible" />
      <el-table-column label="金额" align="center" prop="amount" v-if="columns[12].visible">
        <template slot-scope="scope">
          <span>{{ parseMoney(scope.row.amount) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="业务状态" align="center" prop="dtlBizStatus" v-if="columns[13].visible" />
      <el-table-column label="交易描述信息" align="center" prop="dtlDesc" v-if="columns[14].visible" />
      <el-table-column label="收款人名称" align="center" prop="payeeName" v-if="columns[15].visible" />
      <el-table-column label="收款人账号" align="center" prop="payeeAccount" v-if="columns[16].visible" />
      <el-table-column label="收款人钱包ID" align="center" prop="payeeWalletId" v-if="columns[17].visible" />
      <el-table-column label="付款人账号" align="center" prop="payerAccount" v-if="columns[18].visible" />
      <el-table-column label="原报文编号" align="center" prop="ognlMsgType" v-if="columns[19].visible" />
      <el-table-column label="原报文标识号" align="center" prop="ognlMsgId" v-if="columns[20].visible" />
      <el-table-column label="对账标识" align="center" prop="checkStatus" v-if="columns[21].visible" />
      <el-table-column label="最后更新日期" align="center" prop="lastUpDate" v-if="columns[22].visible" />
      <el-table-column label="最后更新时间" align="center" prop="lastUpTime" v-if="columns[23].visible" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />
  </div>
</template>
<script>
  // import {listCheckpathdetails} from "@/api/pay-batch/checkpathdtl";
  import {
    send801,
    listCheckpathdtl,
    executereconciliation,
    querySingle,
    listCheckpathdetails
  } from "@/api/pay-batch/checkpathdtl";

  export default {
    name: "Checkpathdetails",
    components: {},
    data() {
      return {
        // 遮罩层
        loading: true,
        // 显示搜索条件
        showSearch: true,
        // 总条数
        total: 0,
        // // 对账明细表格数据
        checkpathdtlList: [],
        drOptions: [],
        otOptions: [],
        querySingleParamer: {
          msgId: null,
          dtlMsgId: null,
        },

        execute801paramers: {
          msgId: null,
          disputeReason: null,
          disputeReasonCode: null,
          operType: null,
          payDate: null,
          batchId: null,
          msgTp: null,
          paySerno: null,
          checkstatus: null,
          instgPty: null,
        },
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          batchId: null,
          workdate: null,
          dtlBizStatus: null
        },
        // 列信息
        columns: [{
            key: 0,
            label: '业务日期',
            visible: true
          },
          {
            key: 1,
            label: '报文标识号',
            visible: true
          },
          {
            key: 2,
            label: '交易批次号',
            visible: true
          },
          {
            key: 3,
            label: '分片号',
            visible: false
          },
          {
            key: 4,
            label: '文件名',
            visible: false
          },
          {
            key: 5,
            label: '业务处理时间',
            visible: true
          },
          {
            key: 6,
            label: '报文编号',
            visible: true
          },
          {
            key: 7,
            label: '明细的报文标识号',
            visible: false
          },
          {
            key: 8,
            label: '发起机构',
            visible: true
          },
          {
            key: 9,
            label: '付款机构编码',
            visible: true
          },
          {
            key: 10,
            label: '收款机构编码',
            visible: true
          },
          {
            key: 11,
            label: '货币代码',
            visible: false
          },
          {
            key: 12,
            label: '金额',
            visible: true
          },
          {
            key: 13,
            label: '业务状态',
            visible: false
          },
          {
            key: 14,
            label: '交易描述信息',
            visible: false
          },
          {
            key: 15,
            label: '收款人名称',
            visible: false
          },
          {
            key: 16,
            label: '收款人账号',
            visible: false
          },
          {
            key: 17,
            label: '收款人钱包ID',
            visible: false
          },
          {
            key: 18,
            label: '付款人账号',
            visible: false
          },
          {
            key: 19,
            label: '原报文编号',
            visible: false
          },
          {
            key: 20,
            label: '原报文标识号',
            visible: false
          },
          {
            key: 21,
            label: '对账标识',
            visible: false
          },
          {
            key: 22,
            label: '最后更新日期',
            visible: false
          },
          {
            key: 23,
            label: '最后更新时间',
            visible: false
          },

        ],

        // 表单参数
        form: {},
        // 表单校验
        rules: {
          // paydate: [
          //   { required: true, message: "平台日期不能为空", trigger: "blur" }
          // ],
        }
      };
    },
    created() {
    const batchId = this.$route.params && this.$route.params.batchId;
      this.getList(batchId);
      this.getDicts("DR").then(response => {
        this.drOptions = response.data;
      });
      this.getDicts("OT").then(response => {
        this.otOptions = response.data;
      });
    },
    methods: {
      /** 查询对账明细列表 */
      getList(batchId) {
        this.loading = true;
        this.queryParams.batchId = batchId;
        listCheckpathdetails(this.queryParams).then(response => {
          this.checkpathdtlList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1;
        this.getList();
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.queryParams = {
          paydate: null,
          batchId: null
        };
        this.resetForm("queryForm");
        this.handleQuery();
      },

      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        this.querySingleParamer.msgId = row.msgId;
        this.querySingleParamer.dtlMsgId = row.dtlMsgId;
        querySingle(this.querySingleParamer).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "差错处理";
        });
      },

      /** 提交按钮 */
      submitForm() {

       // console.log(this.form)
        this.execute801paramers.msgId = this.form.msgId;
        this.execute801paramers.disputeReason = this.form.disputeReason;
        this.execute801paramers.disputeReasonCode = this.form.disputeReasonCode;
        this.execute801paramers.operType = this.form.operType;
        this.execute801paramers.payDate = this.form.workdate;
        this.execute801paramers.batchId = this.form.batchId;
        this.execute801paramers.msgTp = this.form.msgType;
        this.execute801paramers.paySerno = this.form.amount;
        this.execute801paramers.checkstatus = this.form.checkstatus;
        this.execute801paramers.instgPty = this.form.instgDrctPty;
        send801(this.execute801paramers).then(response => {
          this.msgSuccess("新增成功");

          this.open = false;
          this.getList();
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
          id: null,
          paramType: null,
          paramKey: null,
          paramValue: null,
          paramDesc: null,
          paramStatus: 0,
          createTime: null,
          updateTime: null
        };
        this.resetForm("form");
      },

    }
  };
</script>
