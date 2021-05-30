<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
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
      <el-form-item label="卡号" prop="acctid">
        <el-input
          v-model="queryParams.acctid"
          placeholder="请输入卡号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="账户名称" prop="acctname">
        <el-input
          v-model="queryParams.acctname"
          placeholder="请输入账户名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="证件号码" prop="idno">
        <el-input
          v-model="queryParams.idno"
          placeholder="请输入证件号码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="签约状态" prop="signstatus">
        <el-select v-model="queryParams.signstatus" placeholder="请选择签约状态" clearable size="small">
          <el-option
            v-for="dict in paramStatusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
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
          v-hasPermi="['system:signinfo:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>
    <el-table v-loading="loading" :data="signinfoList"   @cell-dblclick="showcustomer">
      <el-table-column label="挂接协议号" align="center" prop="signno" v-if="columns[3].visible"/>
      <el-table-column label="签约卡号" align="center" prop="acctid" v-if="columns[7].visible"/>
      <el-table-column label="账户名称" align="center" prop="acctname" v-if="columns[8].visible"/>
      <el-table-column label="证件号码" align="center" prop="idno" v-if="columns[10].visible"/>
      <el-table-column label="签约人银行" align="center" prop="acctptyid" v-if="columns[5].visible"/>
      <el-table-column label="签约状态" align="center":formatter="paramStatusFormat" prop="signstatus" v-if="columns[4].visible"/>
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
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
    import {listSigninfo, queryCustInfo,getSigninfo, delSigninfo, addSigninfo, updateSigninfo} from "@/api/pay-batch/signinfo";

    export default {
        name: "Signinfo",
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
                // 协议表格数据
                signinfoList: [],
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
                    acctid: null,
                    acctname: null,
                    idno: null,
                    signstatus: null,
                },
                // 列信息
                columns: [
                    {key: 0, label: `平台日期`, visible: true},
                    {key: 1, label: `平台流水`, visible: true},
                    {key: 2, label: `平台时间`, visible: true},
                    {key: 3, label: `挂接协议号`, visible: true},
                    {key: 4, label: `协议状态N：签约状态C：解约状态`, visible: true},
                    {key: 5, label: `签约人银行账户所属机构`, visible: true},
                    {key: 6, label: `签约人银行账户类型`, visible: true},
                    {key: 7, label: `签约人银行账户账号`, visible: true},
                    {key: 8, label: `签约人银行账户户名`, visible: true},
                    {key: 9, label: `签约人证件类型`, visible: true},
                    {key: 10, label: `签约人证件号码`, visible: true},
                    {key: 11, label: `银行预留手机号码`, visible: true},
                    {key: 12, label: `钱包开立所属机构编码`, visible: true},
                    {key: 13, label: `钱包id`, visible: true},
                    {key: 14, label: `钱包类型，WT01：个人钱包，WT02：子个人钱包，WT03：硬件钱包，WT09：对公钱包，WT10：子对公钱包`, visible: true},
                    {key: 15, label: `钱包等级WL01：一类钱包，WL02：二类钱包，WL03：三类钱包，WL04：四类钱包`, visible: true},
                    {key: 16, label: `最后更新流水`, visible: true},
                    {key: 17, label: `最后更新日期`, visible: true},
                    {key: 18, label: `最后更新时间`, visible: true},
                ],
                // 表单参数
                form: {}
            };
        },
        created() {
            this.getList();
            this.getDicts("sign_status").then(response => {
                this.paramStatusOptions = response.data;
            });
        },
        methods: {
            /** 查询协议列表 */
            getList() {
                this.loading = true;
                listSigninfo(this.queryParams).then(response => {
                    this.signinfoList = response.rows;
                    this.total = response.total;
                    this.loading = false;
                });
            },
            showcustomer(row) {
                this.reset();
                queryCustInfo(row.acctid).then(response => {
                    this.form = response.data;
                    this.open = true;
                    this.title = "签约客户信息查询";
                });
            },
            // 参数状态字典翻译
            paramStatusFormat(row, column) {
                return this.selectDictLabel(this.paramStatusOptions, row.signstatus);
            },
            /** 搜索按钮操作 */
            handleQuery() {
                this.queryParams.pageNum = 1;
                this.getList();
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
                    signno: null,
                    signstatus: "0",
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
                    lastuptime: null
                };
                this.resetForm("form");
            },
            /** 导出按钮操作 */
            handleExport() {
                this.download('system/signinfo/export', {
                    ...this.queryParams
                }, `system_signinfo.xlsx`)
            }
        }
    };
</script>
