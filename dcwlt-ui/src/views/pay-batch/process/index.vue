<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" id="leftcorner" v-show="showSearch"
             label-width="88px">
      <el-form-item label="出入库类型" prop="OprTp">
        <el-select v-model="queryParams.OprTp" placeholder="请选择出入库类型" size="small">
          <el-option
            v-for="dict in paramOprTpOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="运营机构" prop="CshBoxInstnId">
        <el-select v-model="queryParams.CshBoxInstnId" placeholder="运营机构" clearable size="small">
          <el-option
            v-for="cshparty in paramNameOptions"
            :key="cshparty.partyid"
            :label="cshparty.partyname"
            :value="cshparty.partyid"
          />
        </el-select>
      </el-form-item>

        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索
          </el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置
          </el-button>
        </el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8">

      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd()"
                   v-hasPermi="['batch:nonf:loginin']">出入库</el-button>


        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
                   v-hasPermi="['batch:nonf:export']">导出</el-button>
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          :disabled="single"
          @click="buttonbankRev(1)"
        >出入库重发
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="buttonbankRev(2)"
        >核心状态同步
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="single"
          @click="buttonbankRev(3)"
        >手工冲账
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="processList" @selection-change="handleCurrentChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="表id" align="center" prop="id" v-if="columns[0].visible,false"/>
      <el-table-column label="入库出库类型" align="center" :formatter="paramOprTpFormat" prop="oprtp" v-if="columns[1].visible"/>
      <el-table-column label="借贷标识" align="center" prop="cdtdbtind" v-if="columns[2].visible"/>
      <el-table-column label="出入库货币" align="center" prop="amtccy" v-if="columns[3].visible,false"/>
      <el-table-column label="出入库金额" align="center" prop="amtvalue" v-if="columns[4].visible"/>
      <el-table-column label="合作银行机构编码" align="center" prop="coopbankinstnid" v-if="columns[5].visible"/>
      <el-table-column label="合作银行钱柜ID" align="center" prop="coopbankwltid" v-if="columns[6].visible"/>
      <el-table-column label="钱柜所属运营机构" align="center" prop="cshboxinstnid" v-if="columns[7].visible"/>
      <el-table-column label="城银清交易状态" align="center" prop="prcsts" :formatter="paramStatusFormat"
                       v-if="columns[12].visible"/>
      <el-table-column label="核心处理状态" align="center" prop="corests" v-if="columns[20].visible"/>
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
        <el-form-item label="出入库类型" prop="OprTp">
          <el-select v-model="queryParams.OprTp" placeholder="请选择出入库类型">
            <el-option
              v-for="dict in paramOprTpOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="金额" prop="AmtValue">
          <el-input
            v-model="queryParams.AmtValue"
            placeholder="请输金额"
            clearable
            size="small"
          />
        </el-form-item>
        <el-form-item label="运营机构"  prop="CshBoxInstnId">
          <el-select v-model="queryParams.CshBoxInstnId" placeholder="运营机构" clearable size="small">
            <el-option
              v-for="cshparty in paramNameOptions"
              :key="cshparty.partyid"
              :label="cshparty.partyname"
              :value="cshparty.partyid"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定
        </el-button>
        <el-button @click="cancel">取 消
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import {listParty} from "@/api/pay-batch/cashparty";
  import {
    listProcess,
    getProcess,
    delProcess,
    addProcess,
    updateProcess,
    sendCashbox,
    bankRev,
    changeApprovalStuts
  } from "@/api/pay-batch/process";


  export default {
    name: "Party",
    data() {
      return {
        // 遮罩层
        loading: true,
        // 选中数组
        ids: [],
        // 选中数组
        id: null,
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
        // 显示搜索条件
        showSearch: true,
        currentRow:null,
        // 所属机构
        paramNameOptions: [],
        paramStatusOptions: [],
        // 参数状态字典
        paramOprTpOptions: [],
        // 总条数
        total: 0,
        // 运营机构钱柜参数表格数据
        partyList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        open_warn: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10
        },
        // 列信息
        columns: [
          {key: 0, label: `表id`, visible: true},
          {key: 1, label: `入库出库类型`, visible: true},
          {key: 2, label: `入库出库借贷标识`, visible: true},
          {key: 3, label: `出入库金额货币符号`, visible: true},
          {key: 4, label: `出入库金额`, visible: true},
          {key: 5, label: `合作银行机构编码`, visible: true},
          {key: 6, label: `合作银行钱柜ID`, visible: true},
          {key: 7, label: `钱柜所属运营机构`, visible: true},
          {key: 8, label: `额度凭证`, visible: true},
          {key: 9, label: `报文标识号`, visible: true},
          {key: 10, label: `业务请求时间`, visible: true},
          {key: 11, label: `业务响应时间`, visible: true},
          {key: 12, label: `业务状态`, visible: true},
          {key: 13, label: `业务回执状态`, visible: true},
          {key: 14, label: `业务回执状态`, visible: true},
          {key: 15, label: `业务拒绝`, visible: true},
          {key: 16, label: `业务拒绝信息`, visible: true},
          {key: 17, label: `清算报文标识号`, visible: true},
          {key: 18, label: `清算金额货币符号`, visible: true},
          {key: 19, label: `清算金额`, visible: true},
          {key: 20, label: `核心处理状态`, visible: true},
        ],
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          OprTp: [
            {required: false, message: "出入库类型不能为空", trigger: "blur"}
          ],
          AmtCcy: [
            {required: false, message: "币种不能为空", trigger: "blur"}
          ],
          AmtValue: [
            {required: false, message: "金额不能为空", trigger: "blur"}
          ],
          CshBoxInstnId: [
            {required: false, message: "运营机构不能为空", trigger: "blur"}
          ],
        }
      };
    },
    created() {
      this.getList();
      this.getpaty();
      this.getDicts("oprTp_stuts").then(response => {
        this.paramOprTpOptions = response.data;
      });
    },
    methods: {
      /** 查询运营机构钱柜参数列表 */
      getList() {
        this.loading = true;
        listProcess(this.queryParams).then(response => {
          this.processList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.open_warn = false;
        this.reset();
      },
      // 表单重置
      reset() {
        this.form = {
          id: null,
          partyname: null,
          partyid: null,
          cashboxid: null,
          lastupdate: null,
          lastuptime: null,
          cretername: null,
          cretertime: null,
          updatename: null,
          earlywarningamount: null,
          automaticstorage: null,
          automaticstuts: null
        };
        this.resetForm("form");
      },
      // 多选框选中数据
      handleCurrentChange(selection) {
        this.ids = selection.map(item => item.id)
        this.single = selection.length!=1
        this.multiple = !selection.length

      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1;
        this.getList();
      },
      // 参数状态字典翻译
      paramOprTpFormat(row, column) {
        return this.selectDictLabel(this.paramOprTpOptions, row.oprtp);
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.resetForm("queryForm");
        this.handleQuery();
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "出入库新增";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getParty(id).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改运营机构钱柜参数";
        });
      },
      /** 修改按钮操作 */
      handleUpdate_warn(row) {
        this.reset();
        const id = row.id || this.ids
        getParty(id).then(response => {
          this.form = response.data;
          this.open_warn = true;
          this.title = "钱柜余额预警申请";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != null) {
              updateParty(this.form).then(response => {
                this.msgSuccess("修改成功");
                this.open = false;
                this.open_warn = false;
                this.getList();
              });
            } else {
                let map={
                  "OprTp": this.queryParams.OprTp,
                  "AmtValue": this.queryParams.AmtValue,
                  "CshBoxInstnId": this.queryParams.CshBoxInstnId
                }
                sendCashbox(JSON.stringify(map)).then(response => {
                  this.msgSuccess("出入库成功");
                  this.open = false;
                  this.getList();
                });

            }
          }
        });
      },

      /** 删除按钮操作 */
      handleDelete(row) {
        const ids = row.id || this.ids;
        this.$confirm('是否确认删除运营机构钱柜参数编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return delParty(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
      },

      /** 查询钱柜余额对账通知列表 */
      getpaty() {
        this.loading = true;
        listParty(this.query).then(response => {
          this.paramNameOptions = response.rows;
        });
      },

      /** 出入库重发，核心状态同步，手工冲账 */
      buttonbankRev(type) {
        changeApprovalStuts(type,this.ids).then(response => {
          this.getList();
          this.msgSuccess("操作成功");
        });
      },
    }
  };
</script>
