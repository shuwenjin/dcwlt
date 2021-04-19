<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="报文标识号" prop="msgid">
        <el-input
          v-model="queryParams.msgid"
          placeholder="请输入报文标识号"
          clearable
          size="small"
        @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="平台日期" prop="paydate">
        <el-date-picker clearable size="small"
                        v-model="queryParams.paydate"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="选择平台日期">
        </el-date-picker>
      </el-form-item>

      <el-form-item label="平台流水" prop="payserno">
        <el-input
          v-model="queryParams.payserno"
          placeholder="请输入平台流水"
          clearable
          size="small"
        @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="报文方向" prop="drct">
        <el-select v-model="queryParams.drct" placeholder="请选择报文方向" clearable size="small">
          <el-option
            v-for="dict in drctOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="发起机构" prop="instgdrctpty">
        <el-input
          v-model="queryParams.instgdrctpty"
          placeholder="请输入发起机构"
          clearable
          size="small"
        @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="接收机构" prop="instddrctpty">
        <el-input
          v-model="queryParams.instddrctpty"
          placeholder="请输入接收机构"
          clearable
          size="small"
        @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini"
        @click="handleQuery">搜索
      </el-button>
      <el-button icon="el-icon-refresh" size="mini"
      @click="resetQuery">重置
    </el-button>
  </el-form-item>
</el-form>

<el-row :gutter="10" class="mb8">




<!--  <el-col :span="1.5">
<!--    <el-button
<!--      type="primary"
<!--      plain
<!--      icon="el-icon-plus"
<!--      size="mini"
<!--    @click="handleAdd"
<!--    v-hasPermi="['system:nonf:add']"-->
<!--    >新增-->
<!--  </el-button>-->
<!--</el-col>-->
<!--<el-col :span="1.5">-->
<!--  <el-button-->
<!--    type="success"-->
<!--    plain-->
<!--    icon="el-icon-edit"-->
<!--    size="mini"-->
<!--    :disabled="single"-->
<!--  @click="handleUpdate"-->
<!--  v-hasPermi="['system:nonf:edit']"-->
<!--  >修改-->
<!--</el-button>-->
<!--  </el-col>-->
<!--<el-col :span="1.5">-->
<!--  <el-button-->
<!--    type="danger"-->
<!--    plain-->
<!--    icon="el-icon-delete"-->
<!--    size="mini"-->
<!--    :disabled="multiple"-->
<!--  @click="handleDelete"-->
<!--  v-hasPermi="['system:nonf:remove']"-->
<!--  >删除-->
<!--</el-button>-->
<!--  </el-col>-->
<el-col :span="1.5">

  <el-button
    type="warning"
    plain
    icon="el-icon-add"
    size="mini"
  @click="handleAdd()"
  v-hasPermi="['system:nonf:add']"
  >自由格式添加
  </el-button>
  <el-button
    type="warning"
    plain
    icon="el-icon-download"
    size="mini"
  @click="handleExport"
  v-hasPermi="['system:nonf:export']"
  >导出
</el-button>
  </el-col>
<right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
<!--<right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>-->
  </el-row>

<el-table v-loading="loading" :data="nonfList" @selection-change="handleSelectionChange">

<el-table-column type="selection" width="55" align="center"/>
<el-table-column label="报文标识号" align="center" prop="msgid" v-if="columns[0].visible" :show-overflow-tooltip="true"/>
<el-table-column label="平台日期" align="center" prop="paydate" v-if="columns[1].visible" :show-overflow-tooltip="true"/>
<el-table-column label="平台时间" align="center" prop="paytime" v-if="columns[2].visible" :show-overflow-tooltip="true"/>
<el-table-column label="平台流水" align="center" prop="payserno" v-if="columns[3].visible" :show-overflow-tooltip="true"/>
<el-table-column label="报文编号" align="center" prop="pkgno" v-if="columns[4].visible" :show-overflow-tooltip="true"/>
<el-table-column label="报文方向" align="center" prop="drct" v-if="columns[5].visible" :show-overflow-tooltip="true"/>
<el-table-column label="交易状态" align="center" prop="tradestatus" v-if="columns[6].visible" :show-overflow-tooltip="true"/>
<el-table-column label="报文发送时间" align="center" prop="senderdatetime" v-if="columns[7].visible" :show-overflow-tooltip="true"/>
<el-table-column label="发起机构" align="center" prop="instgdrctpty" v-if="columns[8].visible" :show-overflow-tooltip="true"/>
<el-table-column label="接收机构" align="center" prop="instddrctpty" v-if="columns[9].visible" :show-overflow-tooltip="true"/>
<el-table-column label="操作类型" align="center" prop="opterationtype" v-if="columns[10].visible" :show-overflow-tooltip="true"/>
<el-table-column label="业务处理状态" align="center" prop="procstatus" v-if="columns[11].visible" :show-overflow-tooltip="true"/>
<el-table-column label="业务拒绝码" align="center" prop="rejectcode" v-if="columns[12].visible" :show-overflow-tooltip="true"/>
<el-table-column label="业务拒绝信息" align="center" prop="rejectinfo" v-if="columns[13].visible" :show-overflow-tooltip="true"/>
<el-table-column label="柜员号" align="center" prop="tlrno" v-if="columns[14].visible" :show-overflow-tooltip="true"/>
<el-table-column label="备注" align="center" prop="remark" v-if="columns[15].visible" :show-overflow-tooltip="true"/>
<el-table-column label="信息内容" align="center" prop="messageContext" v-if="columns[16].visible" :show-overflow-tooltip="true"/>
<el-table-column label="最后更新日期" align="center" prop="lastupdate" v-if="columns[17].visible" :show-overflow-tooltip="true"/>
<el-table-column label="最后更新时间" align="center" prop="lastuptime"v-if="columns[18].visible" :show-overflow-tooltip="true"/>

<!--<el-table-column label="操作" align="center" class-name="small-padding fixed-width">-->
<!--  <template slot-scope="scope">-->
<!--    <el-button-->
<!--      size="mini"-->
<!--      type="text"-->
<!--      icon="el-icon-edit"-->
<!--    @click="handleUpdate(scope.row)"-->
<!--    v-hasPermi="['system:nonf:edit']"-->
<!--    >修改-->
<!--  </el-button>-->
<!--  <el-button-->
<!--    size="mini"-->
<!--    type="text"-->
<!--    icon="el-icon-delete"-->
<!--  @click="handleDelete(scope.row)"-->
<!--  v-hasPermi="['system:nonf:remove']"-->
<!--  >删除-->
<!--</el-button>-->
<!--  </template>-->
<!--  </el-table-column>-->
  </el-table>

<pagination
  v-show="total>0"
  :total="total"
  :page.sync="queryParams.pageNum"
  :limit.sync="queryParams.pageSize"
  @pagination="getList"
  />

  <!-- 添加或修改非金融登记簿对话框 -->
<el-dialog :title="title" :visible.sync="open" width="650px" append-to-body>
  <el-form ref="form" :model="form" :rules="rules" label-width="80px">
    <el-row>



 <!--     <el-col :span="24">
        <el-form-item label="柜员号" prop="tlrNo">
          <el-input v-model.trim="form.tlrNo" placeholder="请输入柜员号Id" />
        </el-form-item>
      </el-col> -->



     <el-col :span="24">
       <el-form-item label="接收机构" prop="instdDrctPty">
         <el-select v-model.trim="form.instdDrctPty" placeholder="请选择接收机构" clearable size="small">
           <el-option
             v-for="dict in partList"
             :key="dict.partyID"
             :value="dict.partyID"
            :label="dict.partyID"
           >{{dict.partyID}}-{{dict.partyName}}</el-option>
         </el-select>
       </el-form-item>
       </el-col>




      <el-col :span="24">
        <el-form-item label="消息内容" prop="messageContext">
          <el-input type="textarea" :rows="3" v-model.trim="form.messageContext" placeholder="请输入消息内容" />
        </el-form-item>
      </el-col>
      </el-row>
  </el-form>
  <div slot="footer" class="dialog-footer">
    <el-button type="primary"
    @click="submitForm">确 定
  </el-button>
  <el-button
  @click="cancel">取 消
</el-button>
  </div>
  </el-dialog>
  </div>
  </template>

<script>
  import { listNonf, getNonf, delNonf, addNonf, updateNonf,exportNonf,queryPartyList } from "@/api/pay-batch/freefrmt";

  export default {
  name: "Nonf",
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
  // 非金融登记簿表格数据
  nonfList: [],

  partList: [],

  // 弹出层标题
  title: "",
  // 是否显示弹出层
  open: false,
  // 报文方向字典
  drctOptions: [],
  // 查询参数
  queryParams: {
  pageNum: 1,
  pageSize: 10,
  msgid: null,
  paydate: null,
  payserno: null,
  drct: null,
  instgdrctpty: null,
  instddrctpty: null,
  },
  // 表单参数
  form: {},
  columns: [
  { key: 0, label: `报文标识号`, visible: true },
  { key: 1, label: `平台日期`, visible: true },
  { key: 2, label: `平台时间`, visible: true },
  { key: 3, label: `平台流水`, visible: true },
  { key: 4, label: `报文编号`, visible: true },
  { key: 5, label: `报文方向`, visible: true },
  { key: 6, label: `交易状态`, visible: true },
  { key: 7, label: `报文发送时间`, visible: true },
  { key: 8, label: `发起机构`, visible: true },
  { key: 9, label: `接收机构`, visible: true },
  { key: 10, label: `操作类型`, visible: true },
  { key: 11, label: `业务处理状态`, visible: true },
  { key: 12, label: `业务拒绝码`, visible: true },
  { key: 13, label: `业务拒绝信息`, visible: true },
  { key: 14, label: `柜员号`, visible: true },
  { key: 15, label: `备注`, visible: true },
  { key: 16, label: `信息内容`, visible: true },
  { key: 17, label: `最后更新日期`, visible: true },
  { key: 18, label: `最后更新时间`, visible: true }
  ],
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
  this.getPartyList();
  this.getDicts("drct").then(response => {
  this.drctOptions = response.data;
  });

  // this.queryPartyList().then(response=>{
  //   this.retryStatusOptions=response.data.rows;
  // });

  },
  methods: {


  /** 查询非金融登记簿列表 */
  getList() {
  this.loading = true;
  listNonf(this.queryParams).then(response => {
  this.nonfList = response.rows;
  this.total = response.total;
  this.loading = false;
  });
  },


  getPartyList(){

 //   this.loading=true;
    queryPartyList().then(response=>{

    this.partList=response.rows;
 //   console.log("partList===>"+partList);
    });
  },

  // 报文方向字典翻译
  drctFormat(row, column) {
  return this.selectDictLabel(this.drctOptions, row.drct);
  },
  // 取消按钮
  cancel() {
  this.open = false;
  this.reset();
  },
  // 表单重置
  reset() {
  this.form = {
  msgid: null,
  paydate: null,
  paytime: null,
  payserno: null,
  pkgno: null,
  drct: null,
  tradestatus: "0",
  senderdatetime: null,
  instgdrctpty: null,
  instddrctpty: null,
  opterationtype: null,
  procstatus: "0",
  rejectcode: null,
  rejectinfo: null,
  tlrno: null,
  remark: null,
  messageContext: null,
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
  this.ids = selection.map(item => item.msgid)
  this.single = selection.length!==1
  this.multiple = !selection.length
  },
  /** 新增按钮操作 */
  handleAdd() {
  this.reset();
  this.open = true;
  this.title = "发送自由格式报文";
  },
  /** 修改按钮操作 */
  handleUpdate(row) {
  this.reset();
  const msgid = row.msgid || this.ids
  getNonf(msgid).then(response => {
  this.form = response.data;
  this.open = true;
  this.title = "修改非金融登记簿";
  });
  },
  /** 提交按钮 */
  submitForm() {
  this.$refs["form"].validate(valid => {
  if (valid) {
  if (this.form.msgid != null) {
  updateNonf(this.form).then(response => {
  this.msgSuccess("修改成功");
  this.open = false;
  this.getList();
  });
  } else {
  addNonf(this.form).then(response => {
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
  const msgids = row.msgid || this.ids;
  this.$confirm('是否确认删除非金融登记簿编号为"' + msgids + '"的数据项?', "警告", {
  confirmButtonText: "确定",
  cancelButtonText: "取消",
  type: "warning"
  }).then(function() {
  return delNonf(msgids);
  }).then(() => {
  this.getList();
  this.msgSuccess("删除成功");
  })
  },
  /** 导出按钮操作 */
  handleExport() {
  this.download('dcwlt-pay-batch/nonf/export', {
  ...this.queryParams
  }, `nonf.xlsx`)
  }
  }
  };
</script>
