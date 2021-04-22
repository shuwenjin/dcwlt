<template>
  <div class="app-container">
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
        <template slot-scope="scope">

          <!--当前状态不是“SAME” 就显示，进行调账-->
          <el-button  v-bind:disabled="scope.row.checkStatus=='SAME'" size="mini" type="primary" plain @click="handleUpdate(scope.row)">手动差错</el-button>
        </template>
      </el-table-column>

    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />



    <!-- 添加或修改对账汇总对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="650px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" size="mini" label-width="80px">

        <el-form-item label="差错类型" prop="operType">
          <el-select v-model="form.operType" placeholder="请选择差错类型">
            <el-option v-for="dict in drOptions" :key="dict.dictValue" :label="dict.dictLabel" :value="dict.dictValue">
              {{dict.dictValue}}-{{dict.dictLabel}}</el-option>
          </el-select>
        </el-form-item>


        <el-form-item label="平台日期" prop="workdate">
          <el-input v-model="form.workdate" placeholder="请输入平台日期" />
        </el-form-item>
        <el-form-item label="平台流水" prop="paySerno">
          <el-input v-model="form.amount" placeholder="请输入平台流水" />
        </el-form-item>
        <el-form-item label="交易批次号" prop="batchId">
          <el-input t v-model="form.batchId" placeholder="请输入交易批次" />
        </el-form-item>


        <el-form-item label="差错贷记调整原因码" size="mini" prop="disputeReasonCode">
          <el-select v-model="form.disputeReasonCode" placeholder="差错贷记调整原因码">

            <el-option v-for="dict in otOptions" :key="dict.dictValue" :label="dict.dictLabel" :value="dict.dictValue">
              {{dict.dictValue}}-{{dict.dictLabel}}</el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="差错原因说明" size="mini" prop="disputeReason">
          <el-input type="textarea" rows=3 v-model="form.disputeReason" placeholder="差错原因说明" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import {
    send801,
    listCheckpathdtl,
    executereconciliation,
    querySingle
  } from "@/api/pay-batch/checkpathdtl";


  export default {
    name: "Checkpath",
    components: {},
    data() {
      return {
        // 遮罩层
        loading: true,
        // 总条数
        total: 0,
        // 对账汇总表格数据
        checkpathdtlList: [],

        // 弹出层标题
        title: "",
        drOptions: [],
        otOptions: [],
        form: {
          workdate: '',
          amount: '',
          batchId: ''
        },

        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          batchId: null,
          workdate: null,
        },

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
            visible: false
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
      this.getList();
      this.getDicts("DR").then(response => {
        this.drOptions = response.data;
      });
      this.getDicts("OT").then(response => {
        this.otOptions = response.data;
      });
    },
    methods: {
      /** 查询对账汇总列表 */
      getList() {
        this.loading = true;
        let data = (this.$route.params && this.$route.params.data) || {};
        this.queryParams.batchId = data.batchId;
        this.queryParams.workdate = data.batchDate;
        listCheckpathdtl(this.queryParams).then(response => {
          this.checkpathdtlList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },

      /** 手动差错操作 */
      send801() {
        this.reset();
        this.open = true;
        this.title = "手动差错";
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

      //   /** 手动差错操作 */
      //   executereconciliation(row){
      //     console.log("row==="+row)
      //     executeReconciliation(row).then(response=>{
      //       this.msgSuccess("手动差错成功");
      //     });
      // },



      /** 提交按钮 */
      submitForm() {

        // console.log(this.form)
        // this.execute801paramers.msgId = this.form.msgId;
        // this.execute801paramers.disputeReason = this.form.disputeReason;
        // this.execute801paramers.disputeReasonCode = this.form.disputeReasonCode;
        // this.execute801paramers.operType = this.form.operType;
        // this.execute801paramers.payDate = this.form.workdate;
        // this.execute801paramers.batchId = this.form.batchId;
        // this.execute801paramers.msgTp = this.form.msgType;
        // this.execute801paramers.paySerno = this.form.amount;
        // this.execute801paramers.checkstatus = this.form.checkstatus;
        // this.execute801paramers.instgPty = this.form.instgDrctPty;
        send801(this.form).then(response => {
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
