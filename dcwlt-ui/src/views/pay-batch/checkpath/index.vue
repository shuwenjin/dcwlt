<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="平台日期" prop="paydate">
        <el-date-picker clearable size="small"
                        v-model="queryParams.payDate"
                        type="date"
                        value-format="yyyyMMdd"
                        placeholder="选择平台日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="交易批次号" prop="batchid">
        <el-input
          v-model="queryParams.batchId"
          placeholder="请输入交易批次号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="报文编号" prop="msgtype">
        <el-input
          v-model="queryParams.msgType"
          placeholder="请输入报文编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="业务状态" prop="msgbizstatus">
        <el-select v-model="queryParams.msgBizStatus" placeholder="请选择业务状态" clearable size="small">
          <el-option
            v-for="dict in msgbizstatusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="对账标识" prop="checkstatus">
        <el-select v-model="queryParams.checkStatus" placeholder="请选择对账标识" clearable size="small">
          <el-option
            v-for="dict in checkstatusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-s-operation"
          size="mini"
          @click="handleDetail"
          :disabled="single"
          v-hasPermi="['pay-batch:checkpath:list']"
        >对账明细</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['pay-batch:checkpath:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="checkpathList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="平台日期" align="center" prop="payDate" v-if="columns[0].visible" />
      <el-table-column label="平台流水" align="center" prop="paySerno" v-if="columns[1].visible" />
      <el-table-column label="平台时间" align="center" prop="payTime" v-if="columns[2].visible" />
      <el-table-column label="报文标识号" align="center" prop="msgId" v-if="columns[3].visible" />
      <el-table-column label="报文发送时间" align="center" prop="senderDateTime" v-if="columns[4].visible" />
      <el-table-column label="发起机构" align="center" prop="instgDrctPty" v-if="columns[5].visible" />
      <el-table-column label="接收机构" align="center" prop="instdDrctPty" v-if="columns[6].visible" />
      <el-table-column label="备注" align="center" prop="remark" v-if="columns[7].visible" />
      <el-table-column label="数字信封" align="center" prop="digitalEnvelope" v-if="columns[8].visible" />
      <el-table-column label="批次日期" align="center" prop="batchDate" v-if="columns[9].visible" />
      <el-table-column label="交易批次号" align="center" prop="batchId" v-if="columns[10].visible" />
      <el-table-column label="总笔数" align="center" prop="countNum" v-if="columns[11].visible" />
      <el-table-column label="总金额" align="center" prop="countAmt" v-if="columns[12].visible" >
          <template slot-scope="scope">
            <span>{{ parseMoney(scope.row.countAmt) }}</span>
          </template>
        </el-table-column>
      <el-table-column label="货币代码" align="center" prop="ccy" v-if="columns[13].visible" />
      <el-table-column label="付款笔数" align="center" prop="dBITCountNum" v-if="columns[14].visible" />
      <el-table-column label="付款金额" align="center" prop="dBITCountAmt" v-if="columns[15].visible" >
          <template slot-scope="scope">
            <span>{{ parseMoney(scope.row.dBITCountAmt) }}</span>
          </template>
        </el-table-column>
      <el-table-column label="收款笔数" align="center" prop="cRDTCountNum" v-if="columns[16].visible" />
      <el-table-column label="收款金额" align="center" prop="cRDTCountAmt" v-if="columns[17].visible" >
          <template slot-scope="scope">
            <span>{{ parseMoney(scope.row.cRDTCountAmt) }}</span>
          </template>
        </el-table-column>
      <el-table-column label="报文编号" align="center" prop="msgType" v-if="columns[18].visible" />
      <el-table-column label="业务状态" align="center" prop="msgBizStatus" :formatter="msgbizstatusFormat" v-if="columns[19].visible" />
      <el-table-column label="总笔数" align="center" prop="msgCountNum" v-if="columns[20].visible" />
      <el-table-column label="总金额" align="center" prop="msgCountAmt" v-if="columns[21].visible" >
          <template slot-scope="scope">
            <span>{{ parseMoney(scope.row.msgCountAmt) }}</span>
          </template>
        </el-table-column>
      <el-table-column label="付款笔数" align="center" prop="msgDBITCountNum" v-if="columns[22].visible" />
      <el-table-column label="付款金额" align="center" prop="msgDBITCountAmt" v-if="columns[23].visible" >
          <template slot-scope="scope">
            <span>{{ parseMoney(scope.row.msgDBITCountAmt) }}</span>
          </template>
        </el-table-column>
      <el-table-column label="收款笔数" align="center" prop="msgCRDTCountNum" v-if="columns[24].visible" />
      <el-table-column label="收款金额" align="center" prop="msgCRDTCountAmt" v-if="columns[25].visible" >
          <template slot-scope="scope">
            <span>{{ parseMoney(scope.row.msgCRDTCountAmt) }}</span>
          </template>
        </el-table-column>
      <el-table-column label="对账标识" align="center" prop="checkStatus" :formatter="checkstatusFormat" v-if="columns[26].visible" />
      <el-table-column label="最后更新日期" align="center" prop="lastUpDate" v-if="columns[27].visible" />
      <el-table-column label="最后更新时间" align="center" prop="lastUpTime" v-if="columns[28].visible" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="primary" plain

            @click="executeSend801(scope.row)"

          >手动差错</el-button>
         <el-button
            size="mini"
            type="primary" plain
           @click="executereconciliation(scope.row)"

          >重新对账</el-button>
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



    <!-- 添加或修改对账汇总对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">

        <el-form-item label="差错类型" prop="paramType">
        <!--  <el-input v-model="form.paramType" placeholder="请输入参数id" /> -->
         <el-select v-model="form.paramType" placeholder="请选择差错类型">
           <!-- <el-option label="请选择字典生成" value="" /> -->
            <el-option
              v-for="dict in paramTypeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>

      <el-form-item label="平台日期" prop="paydate">
        <el-input v-model="form.paydate" placeholder="请输入平台日期" />
      </el-form-item>
      <el-form-item label="平台流水" prop="paySerno">
        <el-input v-model="form.paySerno" placeholder="请输入平台流水" />
      </el-form-item>
      <el-form-item label="交易批次号" prop="batchid">
        <el-input t v-model="form.batchid" placeholder="请输入交易批次" />
      </el-form-item>

      <el-form-item label="差错贷记调整原因码" prop="paramType">
      <!--  <el-input v-model="form.paramType" placeholder="请输入参数id" /> -->
       <el-select v-model="form.paramType" placeholder="差错贷记调整原因码">
         <!-- <el-option label="请选择字典生成" value="" /> -->
          <el-option
            v-for="dict in paramTypeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>

      <el-form-item label="差错原因说明" prop="paramDesc">
        <el-input type="textarea" rows=2 v-model="form.paramDesc" placeholder="请输入参数描述" />
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
import { listCheckpath, getCheckpath, delCheckpath, addCheckpath, updateCheckpath,send801,executeReconciliation } from "@/api/pay-batch/checkpath";

export default {
  name: "Checkpath",
  components: {
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中id数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 选中行数据
      selectRow: {},
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 对账汇总表格数据
      checkpathList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 业务状态字典
      msgbizstatusOptions: [],
      // 对账标识字典
      checkstatusOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        payDate: null,
        batchId: null,
        msgType: null,
        msgBizStatus: null,
        checkStatus: null,
      },
      // 列信息
      columns: [
        { key: 0, label: `平台日期`, visible: false },
        { key: 1, label: `平台流水`, visible: false },
        { key: 2, label: `平台时间`, visible: false },
        { key: 3, label: `报文标识号`, visible: false },
        { key: 4, label: `报文发送时间`, visible: true },
        { key: 5, label: `发起机构`, visible: true },
        { key: 6, label: `接收机构`, visible: true },
        { key: 7, label: `备注`, visible: false },
        { key: 8, label: `数字信封`, visible: false },
        { key: 9, label: `批次日期`, visible: true },
        { key: 10, label: `交易批次号`, visible: true },
        { key: 11, label: `总笔数`, visible: true },
        { key: 12, label: `总金额`, visible: true },
        { key: 13, label: `货币代码`, visible: false },
        { key: 14, label: `付款笔数`, visible: false },
        { key: 15, label: `付款金额`, visible: false },
        { key: 16, label: `收款笔数`, visible: false },
        { key: 17, label: `收款金额`, visible: false },
        { key: 18, label: `报文编号`, visible: true },
        { key: 19, label: `业务状态`, visible: false },
        { key: 20, label: `总笔数`, visible: false },
        { key: 21, label: `总金额`, visible: false },
        { key: 22, label: `付款笔数`, visible: false },
        { key: 23, label: `付款金额`, visible: false },
        { key: 24, label: `收款笔数`, visible: false },
        { key: 25, label: `收款金额`, visible: false },
        { key: 26, label: `对账标识`, visible: false },
        { key: 27, label: `最后更新日期`, visible: false },
        { key: 28, label: `最后更新时间`, visible: false },
      ],
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        paydate: [
          { required: true, message: "平台日期不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("msgbizstatus").then(response => {
      this.msgbizstatusOptions = response.data;
    });
    this.getDicts("checkstatus").then(response => {
      this.checkstatusOptions = response.data;
    });
  },
  methods: {
    /** 查询对账汇总列表 */
    getList() {
      this.loading = true;
      listCheckpath(this.queryParams).then(response => {
        this.checkpathList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 业务状态字典翻译
    msgbizstatusFormat(row, column) {
      return this.selectDictLabel(this.msgbizstatusOptions, row.msgbizstatus);
    },
    // 对账标识字典翻译
    checkstatusFormat(row, column) {
      return this.selectDictLabel(this.checkstatusOptions, row.checkstatus);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        paydate: null,
        payserno: null,
        paytime: null,
        msgid: null,
        senderdatetime: null,
        instgdrctpty: null,
        instddrctpty: null,
        remark: null,
        digitalenvelope: null,
        batchdate: null,
        batchid: null,
        countnum: null,
        countamt: null,
        ccy: null,
        dbitcountnum: null,
        dbitcountamt: null,
        crdtcountnum: null,
        crdtcountamt: null,
        msgtype: null,
        msgbizstatus: null,
        msgcountnum: null,
        msgcountamt: null,
        msgdbitcountnum: null,
        msgdbitcountamt: null,
        msgcrdtcountnum: null,
        msgcrdtcountamt: null,
        checkstatus: null,
        lastupdate: null,
        lastuptime: null
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
      this.ids = selection.map(item => item.paydate)
      // 单选时选中的一行数据
      if (selection && selection.length > 0) {
        this.selectRow = selection[0];
      }
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加对账汇总";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const paydate = row.paydate || this.ids
      getCheckpath(paydate).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改对账汇总";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.paydate != null) {
            updateCheckpath(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCheckpath(this.form).then(response => {
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
      const paydates = row.paydate || this.ids;
      this.$confirm('是否确认删除对账汇总编号为"' + paydates + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delCheckpath(paydates);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('pay-batch/checkpath/export', {
        ...this.queryParams
      }, `pay-batch_checkpath.xlsx`)
    },


    executeSend801(row){
      console.log("row==="+row)
      send801(row).then(response=>{
        this.msgSuccess("手动差错成功");
      });
    },

    executereconciliation(row){
      console.log("row==="+row)
      executeReconciliation(row).then(response=>{
        this.msgSuccess("重新对账成功");
      });
    },

    /** 对账明细按钮操作 */
    handleDetail() {
      this.$router.push({name: 'Checkpathdtl', params: {data: this.selectRow}});

  },
  }
};
</script>
