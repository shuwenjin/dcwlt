<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="88px">
      <el-form-item label="访问时间" prop="payDate">
        <el-date-picker clearable size="small"
                        v-model="queryParams.payDate"
                        type="date"
                        value-format="yyyyMMdd"
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
      <el-form-item label="交易批次号" prop="batchId">
        <el-input
          v-model="queryParams.batchId"
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
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
      <!--      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>-->
    </el-row>

    <el-table v-loading="loading" :data="transdtlList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="平台日期" align="center" key="payDate" prop="payDate" v-if="columns[0].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="平台流水" align="center" key="paySerno" prop="paySerno" v-if="columns[1].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="往来标识" align="center" key="direct" prop="direct" v-if="columns[2].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="通道状态" align="center" key="pathProcStatus" prop="pathProcStatus" v-if="columns[3].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="通道流水" align="center" key="payPathSerno" prop="payPathSerno" v-if="columns[4].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="核心处理状态" align="center" key="coreProcStatus" prop="coreProcStatus"
                       v-if="columns[5].visible" :show-overflow-tooltip="true"/>
      <el-table-column label="核心拒绝码" align="center" key="coreRetCode" prop="coreRetCode" v-if="columns[6].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="核心拒绝信息" align="center" key="coreRetMsg" prop="coreRetMsg" v-if="columns[7].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="业务状态" align="center" key="trxStatus" prop="trxStatus" v-if="columns[8].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="业务处理码" align="center" key="trxRetCode" prop="trxRetCode" v-if="columns[9].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="业务处理信息" align="center" key="trxRetMsg" prop="trxRetMsg" v-if="columns[10].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleView(scope.row,scope.index)"
            v-hasPermi="['pay-batch:transdtl:query']"
          >详细</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 金融交易详情 -->
    <el-dialog title="金融交易详情" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" label-width="150px" size="mini">
        <el-row>
          <el-col :span="12">
            <el-form-item label="平台日期">{{ form.payDate }}</el-form-item>
            <el-form-item label="平台流水">{{ form.paySerno }}</el-form-item>
            <el-form-item label="平台时间">{{ form.payTime }}</el-form-item>
            <el-form-item label="往来标识">{{ form.direct }}</el-form-item>
            <el-form-item label="收付标识">{{ form.payFlag }}</el-form-item>
            <el-form-item label="操作步骤">{{ form.operStep }}</el-form-item>
            <el-form-item label="操作状态">{{ form.operStatus }}</el-form-item>
            <el-form-item label="业务状态">{{ form.trxStatus }}</el-form-item>
            <el-form-item label="业务处理码">{{ form.trxRetCode }}</el-form-item>
            <el-form-item label="业务处理信息">{{ form.trxRetMsg }}</el-form-item>
            <el-form-item label="核心处理状态">{{ form.coreProcStatus }}</el-form-item>
            <el-form-item label="核心请求日期">{{ form.coreReqDate }}</el-form-item>
            <el-form-item label="核心请求流水">{{ form.coreReqSerno }}</el-form-item>
            <el-form-item label="核心返回日期">{{ form.coreAcctDate }}</el-form-item>
            <el-form-item label="核心返回流水">{{ form.coreSerno }}</el-form-item>
            <el-form-item label="核心拒绝码">{{ form.coreRetCode }}</el-form-item>
            <el-form-item label="核心拒绝信息">{{ form.coreRetMsg }}</el-form-item>
            <el-form-item label="通道流水">{{ form.payPathSerno }}</el-form-item>
            <el-form-item label="通道日期时间">{{ form.payPathDateTime }}</el-form-item>
            <el-form-item label="通道状态">{{ form.pathProcStatus }}</el-form-item>
            <el-form-item label="通道回执业务状态">{{ form.payPathRspStatus }}</el-form-item>
            <el-form-item label="通道返回码">{{ form.payPathRetCode }}</el-form-item>
            <el-form-item label="通道返回信息">{{ form.payPathRetMsg }}</el-form-item>
            <el-form-item label="通道返回日期">{{ form.payPathRetDate }}</el-form-item>
            <el-form-item label="通道返回流水">{{ form.payPathRetSerno }}</el-form-item>
            <el-form-item label="交易批次号">{{ form.batchId }}</el-form-item>
            <el-form-item label="渠道大类">{{ form.busiChnl }}</el-form-item>
            <el-form-item label="渠道中类">{{ form.busiChnl2 }}</el-form-item>
            <el-form-item label="渠道日期">{{ form.busiSysDate }}</el-form-item>
            <el-form-item label="渠道流水">{{ form.busiSysSerno }}</el-form-item>
            <el-form-item label="渠道时间">{{ form.busiSysTime }}</el-form-item>
            <el-form-item label="报文编号">{{ form.msgType }}</el-form-item>
            <el-form-item label="业务类型">{{ form.busiType }}</el-form-item>
            <el-form-item label="业务种类">{{ form.busiKind }}</el-form-item>
            <el-form-item label="发起机构">{{ form.instgPty }}</el-form-item>
            <el-form-item label="接收机构">{{ form.instdPty }}</el-form-item>
            <el-form-item label="交易金额">{{ form.amount }}</el-form-item>
            <el-form-item label="交易资金来源">{{ form.tradeFundSource }}</el-form-item>
            <el-form-item label="交易用途">{{ form.tradePurpose }}</el-form-item>
            <el-form-item label="付款人钱包所属机构">{{ form.payerPtyId }}</el-form-item>
            <el-form-item label="付款人名称">{{ form.payerName }}</el-form-item>
            <el-form-item label="付款人账户类型">{{ form.payerAcctType }}</el-form-item>
            <el-form-item label="付款人账户账号">{{ form.payerAcct }}</el-form-item>
            <el-form-item label="付款人钱包ID">{{ form.payerWalletId }}</el-form-item>
            <el-form-item label="付款人钱包名称">{{ form.payerWalletName }}</el-form-item>
            <el-form-item label="付款人钱包等级">{{ form.payerWalletLv }}</el-form-item>
            <el-form-item label="付款人钱包类型">{{ form.payerWalletType }}</el-form-item>
            <el-form-item label="收款人账户所属机构">{{ form.payeePtyId }}</el-form-item>
            <el-form-item label="收款人名称">{{ form.payeeName }}</el-form-item>
            <el-form-item label="收款人账户类型">{{ form.payeeAcctType }}</el-form-item>
            <el-form-item label="收款人账户账号">{{ form.payeeAcct }}</el-form-item>
            <el-form-item label="收款人钱包ID">{{ form.payeeWalletId }}</el-form-item>
            <el-form-item label="收款人钱包名称">{{ form.payeeWalletName }}</el-form-item>
            <el-form-item label="收款人钱包等级">{{ form.payeeWalletLv }}</el-form-item>
            <el-form-item label="收款人钱包类型">{{ form.payeeWalletType }}</el-form-item>
            <el-form-item label="挂接协议号">{{ form.protocolNum }}</el-form-item>
            <el-form-item label="币种">{{ form.ccy }}</el-form-item>
            <el-form-item label="柜员号">{{ form.tellerNo }}</el-form-item>
            <el-form-item label="分行号">{{ form.zoneNo }}</el-form-item>
            <el-form-item label="交易行所号">{{ form.brno }}</el-form-item>
            <el-form-item label="账务行所">{{ form.acctBrno }}</el-form-item>
            <el-form-item label="源发起渠道大类">{{ form.origChnl }}</el-form-item>
            <el-form-item label="源发起渠道中类">{{ form.origChnl2 }}</el-form-item>
            <el-form-item label="源发起渠道细分">{{ form.origChnlDtl }}</el-form-item>
            <el-form-item label="原报文编号">{{ form.origMsgType }}</el-form-item>
            <el-form-item label="原业务通道日期">{{ form.origPayPathDate }}</el-form-item>
            <el-form-item label="原业务通道流水">{{ form.origPayPathSerno }}</el-form-item>
            <el-form-item label="摘要码">{{ form.summary }}</el-form-item>
            <el-form-item label="端对端标志">{{ form.endToEndID }}</el-form-item>
            <el-form-item label="最后更新流水">{{ form.lastUpJrnno }}</el-form-item>
            <el-form-item label="最后更新日期">{{ form.lastUpDate }}</el-form-item>
            <el-form-item label="最后更新时间">{{ form.lastUpTime }}</el-form-item>
            <el-form-item label="附言">{{ form.narraTive }}</el-form-item>
            <el-form-item label="备注">{{ form.remark }}</el-form-item>
          </el-col>
<!--          <el-col :span="12">-->
<!--            <el-form-item label="操作时间：">{{ parseTime(form.operTime) }}</el-form-item>-->
<!--          </el-col>-->
<!--          <el-col :span="24">
            <el-form-item label="异常信息：" v-if="form.status === 1">{{ form.errorMsg }}</el-form-item>
          </el-col>-->
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="open = false">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import {listTransdtl, getTransdtl, delTransdtl, addTransdtl, updateTransdtl} from "@/api/pay-batch/transdtl";

  export default {
    name: "transdtl",
    components: {},
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
          direct: null
        },
        // 表单参数
        form: {},
        columns: [
          {key: 0, label: `平台日期`, visible: true},
          {key: 1, label: `平台流水`, visible: true},
          {key: 2, label: `往来标识`, visible: true},
          {key: 3, label: `通道状态`, visible: true},
          {key: 4, label: `通道流水`, visible: true},
          {key: 5, label: `核心处理状态`, visible: true},
          {key: 6, label: `核心拒绝码`, visible: true},
          {key: 7, label: `核心拒绝信息`, visible: true},
          {key: 8, label: `业务状态`, visible: true},
          {key: 9, label: `业务处理码`, visible: true},
          {key: 10, label: `业务处理信息`, visible: true},
        ],
        // 表单校验
        rules: {
          payDate: [
            {required: true, message: "平台日期不能为空", trigger: "blur"}
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
          batchId: null,
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
        this.single = selection.length !== 1
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
      /** 详细按钮操作 */
      handleView(row) {
        this.open = true;
        this.form = row;
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
        }).then(function () {
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
