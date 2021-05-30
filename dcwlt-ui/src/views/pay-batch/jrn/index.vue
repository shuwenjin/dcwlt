<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="78px">
      <el-form-item label="签约日期" prop="paydatestart">
        <el-date-picker clearable size="small"
                        v-model="queryParams.paydatestart"
                        type="date"
                        value-format="yyyyMMdd"
                        placeholder="选择签约日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="到" prop="paydateend" label-width="28px">
        <el-date-picker clearable size="small"
                        v-model="queryParams.paydateend"
                        type="date"
                        value-format="yyyyMMdd"
                        placeholder="选择签约日期">
        </el-date-picker>
      </el-form-item>
      <!--<el-form-item label="交易状态" prop="trxstatus">-->
        <!--<el-select v-model="queryParams.trxstatus" placeholder="请选择交易状态" clearable size="small">-->
          <!--<el-option-->
            <!--v-for="dict in paramStatusOptions"-->
            <!--:key="dict.dictValue"-->
            <!--:label="dict.dictLabel"-->
            <!--:value="dict.dictValue"-->
          <!--/>-->
        <!--</el-select>-->
      <!--</el-form-item>-->
      <el-form-item label="签约人名称" prop="acctname"label-width="88px">
        <el-input
          v-model="queryParams.acctname"
          placeholder="请输入签约人名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="签约人银行账号" prop="acctid"label-width="118px">
        <el-input
          v-model="queryParams.acctid"
          placeholder="请输入签约人银行账号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="报文标识号" prop="msgid"label-width="88px">
        <el-input
          v-model="queryParams.msgid"
          placeholder="请输入报文标识号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
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
          v-hasPermi="['system:jrn:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="jrnList" @cell-dblclick="showcustomer">
      <el-table-column label="挂接协议号" align="center" prop="signno" v-if="columns[9].visible"/>
      <el-table-column label="签约人银行账户户名" align="center" prop="acctname" v-if="columns[22].visible"/>
      <el-table-column label="签约人银行账户所属机构" align="center" prop="acctptyid" v-if="columns[19].visible"/>
      <el-table-column label="签约人银行账户类型" align="center" prop="accttype" v-if="columns[20].visible"/>
      <el-table-column label="签约人银行账户账号" align="center" prop="acctid" v-if="columns[21].visible"/>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
    <!-- 添加或修改协议对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" label-width="80px" disabled="disabled">
        <el-form-item label="客户姓名" prop="clntChinNm">
          <el-input v-model="form.clntChinNm" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="证件类型" prop="certTp">
          <el-select v-model="form.certTp" placeholder="请选择签约人证件类型">
          </el-select>
        </el-form-item>
        <el-form-item label="证件号码" prop="certNo">
          <el-input v-model="form.certNo" placeholder="请输入签约人证件号码" />
        </el-form-item>
        <el-form-item label="出生年月" prop="brthDay">
          <el-input v-model="form.brthDay" placeholder="出生年月" />
        </el-form-item>
        <el-form-item label="性别" prop="clnGndCd">
          <el-input v-model="form.clnGndCd" placeholder="性别" />
        </el-form-item>
        <el-form-item label="手机号码" prop="mblNo">
          <el-input v-model="form.mblNo" placeholder="请输入银行预留手机号码" />
        </el-form-item>
        <el-form-item label="联系地址" prop="ctcAddr">
          <el-input v-model="form.ctcAddr" placeholder="联系地址" />
        </el-form-item>
        <el-form-item label="邮政编码" prop="pstcd">
          <el-input v-model="form.pstcd" placeholder="邮政编码" />
        </el-form-item>
        <el-form-item label="开户日期" prop="openAcctDt">
          <el-input v-model="form.openAcctDt" placeholder="开户日期" />
        </el-form-item>
        <el-form-item label="开户网点" prop="opnOrgNo">
          <el-input v-model="form.opnOrgNo" placeholder="开户网点" />
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
    import {listJrn, queryCustInfo, getJrn, delJrn, addJrn, updateJrn} from "@/api/pay-batch/jrn";

    export default {
        name: "Jrn",
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
                // 参数状态字典
                paramStatusOptions: [],
                // 总条数
                total: 0,
                // 签约流水表格数据
                jrnList: [],
                // 弹出层标题
                title: "",
                // 是否显示弹出层
                open: false,
                // 查询参数
                queryParams: {
                    pageNum: 1,
                    pageSize: 10,
                    paydatestart: null,
                    paydateend: null,
                    trxstatus: null,
                    acctname: null,
                    acctid: null,
                    msgid: null,
                },
                // 列信息
                columns: [
                    {key: 0, label: `平台日期`, visible: true},
                    {key: 1, label: `平台流水`, visible: true},
                    {key: 2, label: `平台时间`, visible: true},
                    {key: 3, label: `报文标识号`, visible: true},
                    {key: 4, label: `发起机构`, visible: true},
                    {key: 5, label: `接收机构`, visible: true},
                    {key: 6, label: `往来send:发送，recv：接收`, visible: true},
                    {key: 7, label: `管理类型MT01：身份认证,MT02：身份确认MT03：解约申请`, visible: true},
                    {key: 8, label: `签约类型SG00：不签约，SG01：签约`, visible: true},
                    {key: 9, label: `挂接协议号`, visible: true},
                    {key: 10, label: `动态关联码：msg+应答报文流水`, visible: true},
                    {key: 11, label: `动态验证码sm4加密存储`, visible: true},
                    {key: 12, label: `业务处理状态：0-失败,1-成功,2-处理中`, visible: true},
                    {key: 13, label: `业务处理码`, visible: true},
                    {key: 14, label: `业务处理信息`, visible: true},
                    {key: 15, label: `应答报文标识号`, visible: true},
                    {key: 16, label: `应答回执状态`, visible: true},
                    {key: 17, label: `应答业务处理码`, visible: true},
                    {key: 18, label: `应答业务处理信息`, visible: true},
                    {key: 19, label: `签约人银行账户所属机构`, visible: true},
                    {key: 20, label: `签约人银行账户类型`, visible: true},
                    {key: 21, label: `签约人银行账户账号`, visible: true},
                    {key: 22, label: `签约人银行账户户名`, visible: true},
                    {key: 23, label: `签约人证件类型`, visible: true},
                    {key: 24, label: `签约人证件号码`, visible: true},
                    {key: 25, label: `银行预留手机号码`, visible: true},
                    {key: 26, label: `钱包开立所属机构编码`, visible: true},
                    {key: 27, label: `钱包id`, visible: true},
                    {key: 28, label: `钱包类型`, visible: true},
                    {key: 29, label: `钱包等级`, visible: true},
                    {key: 30, label: `最后更新流水`, visible: true},
                    {key: 31, label: `最后更新日期`, visible: true},
                    {key: 32, label: `最后更新时间`, visible: true},
                    {key: 33, label: `备注`, visible: true},
                ],
                // 表单参数
                form: {}
            };
        },
        created() {
            this.getList();
            this.getDicts("trx_status").then(response => {
                this.paramStatusOptions = response.data;
            });
        },
        methods: {
            /** 查询签约流水列表 */
            getList() {
                this.loading = true;
                listJrn(this.queryParams).then(response => {
                    this.jrnList = response.rows;
                    this.total = response.total;
                    this.loading = false;
                });
            },
            reset() {
                this.form = {
                    clntChinNm:null,
                    certTp:null,
                    certNo:null,
                    brthDay:null,
                    clnGndCd:null,
                    mblNo:null,
                    ctcAddr:null,
                    pstcd:null,
                    openAcctDt:null,
                    opnOrgNo:null
                };
                this.resetForm("form");
            },
            showcustomer(row) {
                this.reset();
                queryCustInfo(row.acctid).then(response => {
                    this.form = response.data;
                    this.open = true;
                    this.title = "签约客户信息查询";
                });
            },
            /** 搜索按钮操作 */
            handleQuery() {
                this.queryParams.pageNum = 1;
                this.getList();
            },
            /** 导出按钮操作 */
            handleExport() {
                this.download('system/jrn/export', {
                    ...this.queryParams
                }, `system_jrn.xlsx`)
            }
        }
    };
</script>
