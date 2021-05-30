<!--<template>-->
  <!--<div class="app-container">-->
    <!--<el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="69px">-->
      <!--<el-form-item label="挂接协议号" prop="signNo" label-width="100px">-->
        <!--<el-input-->
          <!--v-model="queryParams.signNo"-->
          <!--placeholder="请输入挂接协议号"-->
          <!--clearable-->
          <!--size="small"-->
          <!--@keyup.enter.native="handleQuery"-->
        <!--/>-->
      <!--</el-form-item>-->
      <!--<el-form-item label="协议状态" prop="signStatus">-->
        <!--<el-select v-model="queryParams.signStatus" placeholder="请选择协议状态N：签约状态C：解约状态" clearable size="small" >-->
          <!--<el-option-->
            <!--v-for="dict in signstatusOptions"-->
            <!--:key="dict.dictValue"-->
            <!--:label="dict.dictLabel"-->
            <!--:value="dict.dictValue"-->
          <!--/>-->
        <!--</el-select>-->
      <!--</el-form-item>-->
      <!--<el-form-item label="签约人证件号码" prop="idNo" label-width="120px">-->
        <!--<el-input-->
          <!--v-model="queryParams.idNo"-->
          <!--placeholder="请输入签约人证件号码"-->
          <!--clearable-->
          <!--size="small"-->
          <!--@keyup.enter.native="handleQuery"-->
        <!--/>-->
      <!--</el-form-item>-->
      <!--<el-form-item label="钱包id" prop="walletId">-->
        <!--<el-input-->
          <!--v-model="queryParams.walletId"-->
          <!--placeholder="请输入钱包id"-->
          <!--clearable-->
          <!--size="small"-->
          <!--@keyup.enter.native="handleQuery"-->
        <!--/>-->
      <!--</el-form-item>-->
      <!--<el-form-item>-->
        <!--<el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>-->
        <!--<el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>-->
      <!--</el-form-item>-->
    <!--</el-form>-->

    <!--&lt;!&ndash; <el-row :gutter="10" class="mb8">-->
      <!--<el-col :span="1.5">-->
        <!--<el-button-->
          <!--type="primary"-->
          <!--plain-->
          <!--icon="el-icon-plus"-->
          <!--size="mini"-->
          <!--@click="handleAdd"-->
          <!--v-hasPermi="['system:signinfo:add']"-->
        <!--&gt;新增</el-button>-->
      <!--</el-col>-->
      <!--<el-col :span="1.5">-->
        <!--<el-button-->
          <!--type="success"-->
          <!--plain-->
          <!--icon="el-icon-edit"-->
          <!--size="mini"-->
          <!--:disabled="single"-->
          <!--@click="handleUpdate"-->
          <!--v-hasPermi="['system:signinfo:edit']"-->
        <!--&gt;修改</el-button>-->
      <!--</el-col>-->
      <!--<el-col :span="1.5">-->
        <!--<el-button-->
          <!--type="danger"-->
          <!--plain-->
          <!--icon="el-icon-delete"-->
          <!--size="mini"-->
          <!--:disabled="multiple"-->
          <!--@click="handleDelete"-->
          <!--v-hasPermi="['system:signinfo:remove']"-->
        <!--&gt;删除</el-button>-->
      <!--</el-col>-->
      <!--<el-col :span="1.5">-->
        <!--<el-button-->
          <!--type="warning"-->
          <!--plain-->
          <!--icon="el-icon-download"-->
          <!--size="mini"-->
          <!--@click="handleExport"-->
          <!--v-hasPermi="['system:signinfo:export']"-->
        <!--&gt;导出</el-button>-->
      <!--</el-col>-->
      <!--<right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>-->
    <!--</el-row> &ndash;&gt;-->

    <!--<el-table v-loading="loading" :data="signinfoList" @selection-change="handleSelectionChange">-->
      <!--<el-table-column label="平台日期" align="center" prop="payDate" v-if="columns[0].visible" />-->
      <!--<el-table-column label="平台流水" align="center" prop="paySerNo" v-if="columns[1].visible" />-->
      <!--<el-table-column label="平台时间" align="center" prop="payTime" v-if="columns[2].visible" />-->
      <!--<el-table-column label="挂接协议号" align="center" prop="signNo" v-if="columns[3].visible" />-->
      <!--<el-table-column label="协议状态" align="center" prop="signStatus" :formatter="signstatusFormat" v-if="columns[4].visible" />-->
      <!--<el-table-column label="签约人银行账户所属机构" align="center" prop="acctPtyId" v-if="columns[5].visible" />-->
      <!--<el-table-column label="签约人银行账户类型" align="center" prop="acctType" v-if="columns[6].visible" />-->
      <!--<el-table-column label="签约人银行账户账号" align="center" prop="acctId" v-if="columns[7].visible" />-->
      <!--<el-table-column label="签约人银行账户户名" align="center" prop="acctName" v-if="columns[8].visible" />-->
      <!--<el-table-column label="签约人证件类型" align="center" prop="idType" v-if="columns[9].visible" />-->
      <!--<el-table-column label="签约人证件号码" align="center" prop="idNo" v-if="columns[10].visible" />-->
      <!--<el-table-column label="银行预留手机号码" align="center" prop="telephone" v-if="columns[11].visible" />-->
      <!--<el-table-column label="钱包开立所属机构编码" align="center" prop="walletPtyId" v-if="columns[12].visible" />-->
      <!--<el-table-column label="钱包id" align="center" prop="walletId" v-if="columns[13].visible" />-->
      <!--<el-table-column label="钱包类型" align="center" prop="walletType" :formatter="wallettypeFormat" v-if="columns[14].visible" />-->
      <!--<el-table-column label="钱包等级" align="center" prop="walletLevel" :formatter="walletlevelFormat" v-if="columns[15].visible" />-->
      <!--<el-table-column label="最后更新流水" align="center" prop="lastUpJrnNo" v-if="columns[16].visible" />-->
      <!--<el-table-column label="最后更新日期" align="center" prop="lastUpDate" v-if="columns[17].visible" />-->
      <!--<el-table-column label="最后更新时间" align="center" prop="lastUpTime" v-if="columns[18].visible" />-->
      <!--</el-table-column>-->
    <!--</el-table>-->

    <!--<pagination-->
      <!--v-show="total>0"-->
      <!--:total="total"-->
      <!--:page.sync="queryParams.pageNum"-->
      <!--:limit.sync="queryParams.pageSize"-->
      <!--@pagination="getList"-->
    <!--/>-->

    <!--&lt;!&ndash; 添加或修改协议对话框 &ndash;&gt;-->
    <!--<el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>-->
      <!--<el-form ref="form" :model="form" :rules="rules" label-width="80px">-->
      <!--</el-form>-->
      <!--<div slot="footer" class="dialog-footer">-->
        <!--<el-button type="primary" @click="submitForm">确 定</el-button>-->
        <!--<el-button @click="cancel">取 消</el-button>-->
      <!--</div>-->
    <!--</el-dialog>-->
  <!--</div>-->
<!--</template>-->

<!--<script>-->
<!--import { listSigninfo, getSigninfo, delSigninfo, addSigninfo, updateSigninfo } from "@/api/system/signinfo";-->

<!--export default {-->
  <!--name: "Signinfo",-->
  <!--components: {-->
  <!--},-->
  <!--data() {-->
    <!--return {-->
      <!--// 遮罩层-->
      <!--loading: true,-->
      <!--// 选中数组-->
      <!--ids: [],-->
      <!--// 非单个禁用-->
      <!--single: true,-->
      <!--// 非多个禁用-->
      <!--multiple: true,-->
      <!--// 显示搜索条件-->
      <!--showSearch: true,-->
      <!--// 总条数-->
      <!--total: 0,-->
      <!--// 协议表格数据-->
      <!--signinfoList: [],-->
      <!--// 弹出层标题-->
      <!--title: "",-->
      <!--// 是否显示弹出层-->
      <!--open: false,-->
      <!--// 协议状态N：签约状态C：解约状态字典-->
      <!--signstatusOptions: [],-->
      <!--// 钱包类型，WT01：个人钱包，WT02：子个人钱包，WT03：硬件钱包，WT09：对公钱包，WT10：子对公钱包字典-->
      <!--wallettypeOptions: [],-->
      <!--// 钱包等级WL01：一类钱包，WL02：二类钱包，WL03：三类钱包，WL04：四类钱包字典-->
      <!--walletlevelOptions: [],-->
      <!--// 查询参数-->
      <!--queryParams: {-->
        <!--pageNum: 1,-->
        <!--pageSize: 10,-->
        <!--signno: null,-->
        <!--signstatus: null,-->
        <!--idno: null,-->
        <!--walletid: null,-->
      <!--},-->
      <!--// 列信息-->
      <!--columns: [-->
        <!--{ key: 0, label: `平台日期`, visible: true },-->
        <!--{ key: 1, label: `平台流水`, visible: true },-->
        <!--{ key: 2, label: `平台时间`, visible: true },-->
        <!--{ key: 3, label: `挂接协议号`, visible: true },-->
        <!--{ key: 4, label: `协议状态N：签约状态C：解约状态`, visible: true },-->
        <!--{ key: 5, label: `签约人银行账户所属机构`, visible: true },-->
        <!--{ key: 6, label: `签约人银行账户类型`, visible: true },-->
        <!--{ key: 7, label: `签约人银行账户账号`, visible: true },-->
        <!--{ key: 8, label: `签约人银行账户户名`, visible: true },-->
        <!--{ key: 9, label: `签约人证件类型`, visible: true },-->
        <!--{ key: 10, label: `签约人证件号码`, visible: true },-->
        <!--{ key: 11, label: `银行预留手机号码`, visible: true },-->
        <!--{ key: 12, label: `钱包开立所属机构编码`, visible: true },-->
        <!--{ key: 13, label: `钱包id`, visible: true },-->
        <!--{ key: 14, label: `钱包类型，WT01：个人钱包，WT02：子个人钱包，WT03：硬件钱包，WT09：对公钱包，WT10：子对公钱包`, visible: true },-->
        <!--{ key: 15, label: `钱包等级WL01：一类钱包，WL02：二类钱包，WL03：三类钱包，WL04：四类钱包`, visible: true },-->
        <!--{ key: 16, label: `最后更新流水`, visible: true },-->
        <!--{ key: 17, label: `最后更新日期`, visible: true },-->
        <!--{ key: 18, label: `最后更新时间`, visible: true },-->
      <!--],-->
      <!--// 表单参数-->
      <!--form: {},-->
      <!--// 表单校验-->
      <!--rules: {-->
        <!--paydate: [-->
          <!--{ required: true, message: "平台日期不能为空", trigger: "blur" }-->
        <!--],-->
        <!--payserno: [-->
          <!--{ required: true, message: "平台流水不能为空", trigger: "blur" }-->
        <!--],-->
        <!--paytime: [-->
          <!--{ required: true, message: "平台时间不能为空", trigger: "blur" }-->
        <!--],-->
        <!--signstatus: [-->
          <!--{ required: true, message: "协议状态N：签约状态C：解约状态不能为空", trigger: "change" }-->
        <!--],-->
        <!--acctptyid: [-->
          <!--{ required: true, message: "签约人银行账户所属机构不能为空", trigger: "blur" }-->
        <!--],-->
        <!--accttype: [-->
          <!--{ required: true, message: "签约人银行账户类型不能为空", trigger: "blur" }-->
        <!--],-->
        <!--acctid: [-->
          <!--{ required: true, message: "签约人银行账户账号不能为空", trigger: "blur" }-->
        <!--],-->
        <!--acctname: [-->
          <!--{ required: true, message: "签约人银行账户户名不能为空", trigger: "blur" }-->
        <!--],-->
        <!--idtype: [-->
          <!--{ required: true, message: "签约人证件类型不能为空", trigger: "blur" }-->
        <!--],-->
        <!--idno: [-->
          <!--{ required: true, message: "签约人证件号码不能为空", trigger: "blur" }-->
        <!--],-->
        <!--telephone: [-->
          <!--{ required: true, message: "银行预留手机号码不能为空", trigger: "blur" }-->
        <!--],-->
        <!--walletptyid: [-->
          <!--{ required: true, message: "钱包开立所属机构编码不能为空", trigger: "blur" }-->
        <!--],-->
        <!--walletid: [-->
          <!--{ required: true, message: "钱包id不能为空", trigger: "blur" }-->
        <!--],-->
        <!--wallettype: [-->
          <!--{ required: true, message: "钱包类型，WT01：个人钱包，WT02：子个人钱包，WT03：硬件钱包，WT09：对公钱包，WT10：子对公钱包不能为空", trigger: "change" }-->
        <!--],-->
      <!--}-->
    <!--};-->
  <!--},-->
  <!--created() {-->
    <!--this.getList();-->
    <!--this.getDicts("signStatus").then(response => {-->
      <!--this.signstatusOptions = response.data;-->
    <!--});-->
    <!--this.getDicts("wallettype").then(response => {-->
      <!--this.wallettypeOptions = response.data;-->
    <!--});-->
    <!--this.getDicts("walletlevel").then(response => {-->
      <!--this.walletlevelOptions = response.data;-->
    <!--});-->
  <!--},-->
  <!--methods: {-->
    <!--/** 查询协议列表 */-->
    <!--getList() {-->
      <!--this.loading = true;-->
      <!--listSigninfo(this.queryParams).then(response => {-->
        <!--this.signinfoList = response.rows;-->
        <!--this.total = response.total;-->
        <!--this.loading = false;-->
      <!--});-->
    <!--},-->
    <!--// 协议状态N：签约状态C：解约状态字典翻译-->
    <!--signstatusFormat(row, column) {-->
      <!--return this.selectDictLabel(this.signstatusOptions, row.signStatus);-->
    <!--},-->
    <!--// 钱包类型，WT01：个人钱包，WT02：子个人钱包，WT03：硬件钱包，WT09：对公钱包，WT10：子对公钱包字典翻译-->
    <!--wallettypeFormat(row, column) {-->
      <!--return this.selectDictLabel(this.wallettypeOptions, row.walletType);-->
    <!--},-->
    <!--// 钱包等级WL01：一类钱包，WL02：二类钱包，WL03：三类钱包，WL04：四类钱包字典翻译-->
    <!--walletlevelFormat(row, column) {-->
      <!--return this.selectDictLabel(this.walletlevelOptions, row.walletLevel);-->
    <!--},-->
    <!--// 取消按钮-->
    <!--cancel() {-->
      <!--this.open = false;-->
      <!--this.reset();-->
    <!--},-->
    <!--// 表单重置-->
    <!--reset() {-->
      <!--this.form = {-->
        <!--paydate: null,-->
        <!--payserno: null,-->
        <!--paytime: null,-->
        <!--signno: null,-->
        <!--signstatus: null,-->
        <!--acctptyid: null,-->
        <!--accttype: null,-->
        <!--acctid: null,-->
        <!--acctname: null,-->
        <!--idtype: null,-->
        <!--idno: null,-->
        <!--telephone: null,-->
        <!--walletptyid: null,-->
        <!--walletid: null,-->
        <!--wallettype: null,-->
        <!--walletlevel: null,-->
        <!--lastupjrnno: null,-->
        <!--lastupdate: null,-->
        <!--lastuptime: null-->
      <!--};-->
      <!--this.resetForm("form");-->
    <!--},-->
    <!--/** 搜索按钮操作 */-->
    <!--handleQuery() {-->
      <!--this.queryParams.pageNum = 1;-->
      <!--this.getList();-->
    <!--},-->
    <!--/** 重置按钮操作 */-->
    <!--resetQuery() {-->
      <!--this.resetForm("queryForm");-->
      <!--this.handleQuery();-->
    <!--},-->
    <!--// 多选框选中数据-->
    <!--handleSelectionChange(selection) {-->
      <!--this.ids = selection.map(item => item.signno)-->
      <!--this.single = selection.length!==1-->
      <!--this.multiple = !selection.length-->
    <!--},-->
    <!--/** 新增按钮操作 */-->
    <!--handleAdd() {-->
      <!--this.reset();-->
      <!--this.open = true;-->
      <!--this.title = "添加协议";-->
    <!--},-->
    <!--/** 修改按钮操作 */-->
    <!--handleUpdate(row) {-->
      <!--this.reset();-->
      <!--const signno = row.signno || this.ids-->
      <!--getSigninfo(signno).then(response => {-->
        <!--this.form = response.data;-->
        <!--this.open = true;-->
        <!--this.title = "修改协议";-->
      <!--});-->
    <!--},-->
    <!--/** 提交按钮 */-->
    <!--submitForm() {-->
      <!--this.$refs["form"].validate(valid => {-->
        <!--if (valid) {-->
          <!--if (this.form.signno != null) {-->
            <!--updateSigninfo(this.form).then(response => {-->
              <!--this.msgSuccess("修改成功");-->
              <!--this.open = false;-->
              <!--this.getList();-->
            <!--});-->
          <!--} else {-->
            <!--addSigninfo(this.form).then(response => {-->
              <!--this.msgSuccess("新增成功");-->
              <!--this.open = false;-->
              <!--this.getList();-->
            <!--});-->
          <!--}-->
        <!--}-->
      <!--});-->
    <!--},-->
    <!--/** 删除按钮操作 */-->
    <!--handleDelete(row) {-->
      <!--const signnos = row.signno || this.ids;-->
      <!--this.$confirm('是否确认删除协议编号为"' + signnos + '"的数据项?', "警告", {-->
          <!--confirmButtonText: "确定",-->
          <!--cancelButtonText: "取消",-->
          <!--type: "warning"-->
        <!--}).then(function() {-->
          <!--return delSigninfo(signnos);-->
        <!--}).then(() => {-->
          <!--this.getList();-->
          <!--this.msgSuccess("删除成功");-->
        <!--})-->
    <!--},-->
    <!--/** 导出按钮操作 */-->
    <!--handleExport() {-->
      <!--this.download('system/signinfo/export', {-->
        <!--...this.queryParams-->
      <!--}, `system_signinfo.xlsx`)-->
    <!--}-->
  <!--}-->
<!--};-->
<!--</script>-->
