<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="平台日期" prop="payDate">
        <el-input
          v-model="queryParams.payDate"
          placeholder="请输入平台日期"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="平台流水" prop="paySerNo">
        <el-input
          v-model="queryParams.paySerNo"
          placeholder="请输入平台流水"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="挂接协议号" prop="signNo" label-width="100px">
        <el-input
          v-model="queryParams.signNo"
          placeholder="请输入挂接协议号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="签约人证件号码" prop="idNo" label-width="120px">
        <el-input
          v-model="queryParams.idNo"
          placeholder="请输入签约人证件号码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="钱包id" prop="walletId">
        <el-input
          v-model="queryParams.walletId"
          placeholder="请输入钱包id"
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

  <!--  <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:signjrn:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:signjrn:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:signjrn:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:signjrn:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row> -->

    <el-table v-loading="loading" :data="signjrnList" @selection-change="handleSelectionChange">
      <el-table-column label="平台日期" align="center" prop="payDate" v-if="columns[0].visible" />
      <el-table-column label="平台流水" align="center" prop="paySerNo" v-if="columns[1].visible" />
      <el-table-column label="平台时间" align="center" prop="payTime" v-if="columns[2].visible" />
      <el-table-column label="报文标识号" align="center" prop="msgId" v-if="columns[3].visible" />
      <el-table-column label="发起机构" align="center" prop="instGpTy" v-if="columns[4].visible" />
      <el-table-column label="接收机构" align="center" prop="instDpTy" v-if="columns[5].visible" />
      <el-table-column label="往来" align="center" prop="direct" :formatter="directFormat" v-if="columns[6].visible" />
      <el-table-column label="管理类型" align="center" prop="manageType" :formatter="managetypeFormat" v-if="columns[7].visible" />
      <el-table-column label="签约类型" align="center" prop="signType" :formatter="signtypeFormat" v-if="columns[8].visible" />
      <el-table-column label="挂接协议号" align="center" prop="signNo" v-if="columns[9].visible" />
      <el-table-column label="动态关联码" align="center" prop="msgSendCode" v-if="columns[10].visible" />
      <el-table-column label="动态验证码" align="center" prop="msgVerifyCode" v-if="columns[11].visible" />
      <el-table-column label="业务处理状态" align="center" prop="trxStatus" :formatter="trxstatusFormat" v-if="columns[12].visible" />
      <el-table-column label="业务处理码" align="center" prop="trxRetCode" v-if="columns[13].visible" />
      <el-table-column label="业务处理信息" align="center" prop="trxRetMsg" v-if="columns[14].visible" />
      <el-table-column label="应答报文标识号" align="center" prop="rspMsgId" v-if="columns[15].visible" />
      <el-table-column label="应答回执状态" align="center" prop="rspStatus" v-if="columns[16].visible" />
      <el-table-column label="应答业务处理码" align="center" prop="rspCode" v-if="columns[17].visible" />
      <el-table-column label="应答业务处理信息" align="center" prop="rspMsg" v-if="columns[18].visible" />
      <el-table-column label="签约人银行账户所属机构" align="center" prop="acctPtyId" v-if="columns[19].visible" />
      <el-table-column label="签约人银行账户类型" align="center" prop="acctType" v-if="columns[20].visible" />
      <el-table-column label="签约人银行账户账号" align="center" prop="acctId" v-if="columns[21].visible" />
      <el-table-column label="签约人银行账户户名" align="center" prop="acctName" v-if="columns[22].visible" />
      <el-table-column label="签约人证件类型" align="center" prop="idType" v-if="columns[23].visible" />
      <el-table-column label="签约人证件号码" align="center" prop="idNo" v-if="columns[24].visible" />
      <el-table-column label="银行预留手机号码" align="center" prop="telephone" v-if="columns[25].visible" />
      <el-table-column label="钱包开立所属机构编码" align="center" prop="walletPtyId" v-if="columns[26].visible" />
      <el-table-column label="钱包id" align="center" prop="walletId" v-if="columns[27].visible" />
      <el-table-column label="钱包类型" align="center" prop="walletType" :formatter="wallettypeFormat" v-if="columns[28].visible" />
      <el-table-column label="钱包等级" align="center" prop="walletLevel" :formatter="walletlevelFormat" v-if="columns[29].visible" />
      <el-table-column label="最后更新流水" align="center" prop="lastUpJrnNo" v-if="columns[30].visible" />
      <el-table-column label="最后更新日期" align="center" prop="lastUpDate" v-if="columns[31].visible" />
      <el-table-column label="最后更新时间" align="center" prop="lastUpTime" v-if="columns[32].visible" />
      <el-table-column label="备注" align="center" prop="remark" v-if="columns[33].visible" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改signjrn对话框 -->
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
import { listSignjrn, getSignjrn, delSignjrn, addSignjrn, updateSignjrn } from "@/api/system/signjrn";

export default {
  name: "Signjrn",
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
      // signjrn表格数据
      signjrnList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 往来send:发送，recv：接收字典
      directOptions: [],
      // 管理类型MT01：身份认证,MT02：身份确认MT03：解约申请字典
      managetypeOptions: [],
      // 签约类型SG00：不签约，SG01：签约字典
      signtypeOptions: [],
      // 业务处理状态：0-失败,1-成功,2-处理中字典
      trxstatusOptions: [],
      // 钱包类型字典
      wallettypeOptions: [],
      // 钱包等级字典
      walletlevelOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        paydate: null,
        payserno: null,
        signno: null,
        idno: null,
        walletid: null,
      },
      // 列信息
      columns: [
        { key: 0, label: `平台日期`, visible: true },
        { key: 1, label: `平台流水`, visible: true },
        { key: 2, label: `平台时间`, visible: true },
        { key: 3, label: `报文标识号`, visible: true },
        { key: 4, label: `发起机构`, visible: true },
        { key: 5, label: `接收机构`, visible: true },
        { key: 6, label: `往来send:发送，recv：接收`, visible: true },
        { key: 7, label: `管理类型MT01：身份认证,MT02：身份确认MT03：解约申请`, visible: true },
        { key: 8, label: `签约类型SG00：不签约，SG01：签约`, visible: true },
        { key: 9, label: `挂接协议号`, visible: true },
        { key: 10, label: `动态关联码：msg+应答报文流水`, visible: true },
        { key: 11, label: `动态验证码sm4加密存储`, visible: true },
        { key: 12, label: `业务处理状态：0-失败,1-成功,2-处理中`, visible: true },
        { key: 13, label: `业务处理码`, visible: true },
        { key: 14, label: `业务处理信息`, visible: true },
        { key: 15, label: `应答报文标识号`, visible: true },
        { key: 16, label: `应答回执状态`, visible: true },
        { key: 17, label: `应答业务处理码`, visible: true },
        { key: 18, label: `应答业务处理信息`, visible: true },
        { key: 19, label: `签约人银行账户所属机构`, visible: true },
        { key: 20, label: `签约人银行账户类型`, visible: true },
        { key: 21, label: `签约人银行账户账号`, visible: true },
        { key: 22, label: `签约人银行账户户名`, visible: true },
        { key: 23, label: `签约人证件类型`, visible: true },
        { key: 24, label: `签约人证件号码`, visible: true },
        { key: 25, label: `银行预留手机号码`, visible: true },
        { key: 26, label: `钱包开立所属机构编码`, visible: true },
        { key: 27, label: `钱包id`, visible: true },
        { key: 28, label: `钱包类型`, visible: true },
        { key: 29, label: `钱包等级`, visible: true },
        { key: 30, label: `最后更新流水`, visible: true },
        { key: 31, label: `最后更新日期`, visible: true },
        { key: 32, label: `最后更新时间`, visible: true },
        { key: 33, label: `备注`, visible: true },
      ],
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        paytime: [
          { required: true, message: "平台时间不能为空", trigger: "blur" }
        ],
        msgid: [
          { required: true, message: "报文标识号不能为空", trigger: "blur" }
        ],
        instgpty: [
          { required: true, message: "发起机构不能为空", trigger: "blur" }
        ],
        instdpty: [
          { required: true, message: "接收机构不能为空", trigger: "blur" }
        ],
        direct: [
          { required: true, message: "往来send:发送，recv：接收不能为空", trigger: "change" }
        ],
        managetype: [
          { required: true, message: "管理类型MT01：身份认证,MT02：身份确认MT03：解约申请不能为空", trigger: "change" }
        ],
        signtype: [
          { required: true, message: "签约类型SG00：不签约，SG01：签约不能为空", trigger: "change" }
        ],
        acctptyid: [
          { required: true, message: "签约人银行账户所属机构不能为空", trigger: "blur" }
        ],
        accttype: [
          { required: true, message: "签约人银行账户类型不能为空", trigger: "blur" }
        ],
        acctid: [
          { required: true, message: "签约人银行账户账号不能为空", trigger: "blur" }
        ],
        acctname: [
          { required: true, message: "签约人银行账户户名不能为空", trigger: "blur" }
        ],
        idtype: [
          { required: true, message: "签约人证件类型不能为空", trigger: "blur" }
        ],
        idno: [
          { required: true, message: "签约人证件号码不能为空", trigger: "blur" }
        ],
        telephone: [
          { required: true, message: "银行预留手机号码不能为空", trigger: "blur" }
        ],
        walletptyid: [
          { required: true, message: "钱包开立所属机构编码不能为空", trigger: "blur" }
        ],
        walletid: [
          { required: true, message: "钱包id不能为空", trigger: "blur" }
        ],
        wallettype: [
          { required: true, message: "钱包类型不能为空", trigger: "change" }
        ],
        walletlevel: [
          { required: true, message: "钱包等级不能为空", trigger: "change" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("direct").then(response => {
      this.directOptions = response.data;
    });
    this.getDicts("manageType").then(response => {
      this.managetypeOptions = response.data;
    });
    this.getDicts("signType").then(response => {
      this.signtypeOptions = response.data;
    });
    this.getDicts("trxStatus").then(response => {
      this.trxstatusOptions = response.data;
    });
    this.getDicts("wallettype").then(response => {
      this.wallettypeOptions = response.data;
    });
    this.getDicts("walletlevel").then(response => {
      this.walletlevelOptions = response.data;
    });
  },
  methods: {
    /** 查询signjrn列表 */
    getList() {
      this.loading = true;
      listSignjrn(this.queryParams).then(response => {
        this.signjrnList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 往来send:发送，recv：接收字典翻译
    directFormat(row, column) {
      return this.selectDictLabel(this.directOptions, row.direct);
    },
    // 管理类型MT01：身份认证,MT02：身份确认MT03：解约申请字典翻译
    managetypeFormat(row, column) {
      return this.selectDictLabel(this.managetypeOptions, row.manageType);
    },
    // 签约类型SG00：不签约，SG01：签约字典翻译
    signtypeFormat(row, column) {
      return this.selectDictLabel(this.signtypeOptions, row.signType);
    },
    // 业务处理状态：0-失败,1-成功,2-处理中字典翻译
    trxstatusFormat(row, column) {
      return this.selectDictLabel(this.trxstatusOptions, row.trxStatus);
    },
    // 钱包类型字典翻译
    wallettypeFormat(row, column) {
      return this.selectDictLabel(this.wallettypeOptions, row.walletType);
    },
    // 钱包等级字典翻译
    walletlevelFormat(row, column) {
      return this.selectDictLabel(this.walletlevelOptions, row.walletLevel);
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
        instgpty: null,
        instdpty: null,
        direct: null,
        managetype: null,
        signtype: null,
        signno: null,
        msgsendcode: null,
        msgverifycode: null,
        trxstatus: null,
        trxretcode: null,
        trxretmsg: null,
        rspmsgid: null,
        rspstatus: null,
        rspcode: null,
        rspmsg: null,
        acctptyid: null,
        accttype: null,
        acctid: null,
        acctname: null,
        idtype: null,
        idno: null,
        telephone: null,
        walletptyid: null,
        walletid: null,
        wallettype: null,
        walletlevel: null,
        lastupjrnno: null,
        lastupdate: null,
        lastuptime: null,
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
      this.ids = selection.map(item => item.paydate)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加signjrn";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const paydate = row.paydate || this.ids
      getSignjrn(paydate).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改signjrn";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.paydate != null) {
            updateSignjrn(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSignjrn(this.form).then(response => {
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
      this.$confirm('是否确认删除signjrn编号为"' + paydates + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delSignjrn(paydates);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/signjrn/export', {
        ...this.queryParams
      }, `system_signjrn.xlsx`)
    }
  }
};
</script>
