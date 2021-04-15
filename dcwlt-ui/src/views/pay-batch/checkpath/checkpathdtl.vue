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
        <el-table-column label="金额" align="center" prop="amount" v-if="columns[12].visible" >
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
import {listCheckpathdtl} from "@/api/pay-batch/checkpathdtl";


export default {
  name: "Checkpath",
  components: {
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 总条数
      total: 0,
      // 对账汇总表格数据
      checkpathdtlList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        batchId: null,
        workdate: null,
      },
      // 列信息
      columns: [
        { key: 0, label: '业务日期', visible: true },
        { key: 1, label: '报文标识号', visible: true },
        { key: 2, label: '交易批次号', visible: false },
        { key: 3, label: '分片号', visible: false },
        { key: 4, label: '文件名', visible: false },
        { key: 5, label: '业务处理时间', visible: true },
        { key: 6, label: '报文编号', visible: true },
        { key: 7, label: '明细的报文标识号', visible: false },
        { key: 8, label: '发起机构', visible: true },
        { key: 9, label: '付款机构编码', visible: true },
        { key: 10, label: '收款机构编码', visible: true },
        { key: 11, label: '货币代码', visible: false },
        { key: 12, label: '金额', visible: true },
        { key: 13, label: '业务状态', visible: false },
        { key: 14, label: '交易描述信息', visible: false },
        { key: 15, label: '收款人名称', visible: false },
        { key: 16, label: '收款人账号', visible: false },
        { key: 17, label: '收款人钱包ID', visible: false },
        { key: 18, label: '付款人账号', visible: false },
        { key: 19, label: '原报文编号', visible: false },
        { key: 20, label: '原报文标识号', visible: false },
        { key: 21, label: '对账标识', visible: false },
        { key: 22, label: '最后更新日期', visible: false },
        { key: 23, label: '最后更新时间', visible: false },
      ],
    };
  },
  created() {
    this.getList();
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
  }
};
</script>
