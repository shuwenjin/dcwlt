<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="机构编码" prop="partyId">
        <el-input
          v-model="queryParams.partyId"
          placeholder="请输入机构编码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="报文编号" prop="msgType">
        <el-input
          v-model="queryParams.msgType"
          placeholder="请输入报文编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="业务类型" prop="tradeCtgyCode">
        <el-select v-model="queryParams.tradeCtgyCode" placeholder="请选业务类型" clearable size="small">
          <el-option
            v-for="dict in tradeCtgyCodeOptions"
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
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:partyauth:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="partyauthList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="机构编码" align="center" prop="partyId" v-if="columns[0].visible"/>
      <el-table-column label="报文编号" align="center" prop="msgType" v-if="columns[1].visible"/>
      <el-table-column label="业务类型" align="center" prop="tradeCtgyCode" :formatter="tradeCtgyCodeFormat"
                       v-if="columns[2].visible"/>
      <el-table-column label="发送权限标识" align="center" prop="sendAuth" :formatter="sendAuthFormat" v-if="columns[3].visible"/>
      <el-table-column label="接收权限标识" align="center" prop="recvAuth" :formatter="recvAuthFormat" v-if="columns[4].visible"/>
      <el-table-column label="状态" align="center" prop="status" :formatter="statusFormat" v-if="columns[5].visible"/>
      <el-table-column label="生效日期" align="center" prop="effectDate" v-if="columns[6].visible"/>
      <el-table-column label="失效日期" align="center" prop="inEffectiveDate" v-if="columns[7].visible"/>
      <el-table-column label="最后更新日期" align="center" prop="lastUpDate" v-if="columns[8].visible"/>
      <el-table-column label="最后更新时间" align="center" prop="lastUpTime" v-if="columns[9].visible"/>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改业务权限变更信息对话框 -->
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
  import {listPartyauth, getPartyauth, delPartyauth, addPartyauth, updatePartyauth} from "@/api/pay-batch/partyauth";

  export default {
    name: "Partyauth",
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
        // 业务权限变更信息表格数据
        partyauthList: [],
        tradeCtgyCodeOptions: [],
        AuthrtySgnCdOptions: [],
        statusOptions: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          partyId: null,
          msgType: null,
          tradeCtgyCode: null,
        },
        // 列信息
        columns: [
          {key: 0, label: `机构编码`, visible: true},
          {key: 1, label: `报文编号`, visible: true},
          {key: 2, label: `业务类型`, visible: true},
          {key: 3, label: `发起权限标识`, visible: true},
          {key: 4, label: `接收权限标识`, visible: true},
          {key: 5, label: `撤销状态`, visible: true},
          {key: 6, label: `生效日期`, visible: false},
          {key: 7, label: `失效日期`, visible: false},
          {key: 8, label: `最后更新日期`, visible: false},
          {key: 9, label: `最后更新时间`, visible: false}
        ],
        // 表单参数
        form: {},
        // 表单校验
        rules: {}
      };
    },
    created() {
      this.getList();
      this.getDicts("TrxBizTp").then(response => {
        this.tradeCtgyCodeOptions = response.data;
      });
      this.getDicts("AuthrtySgnCd").then(response => {
        this.AuthrtySgnCdOptions = response.data;
      });
      this.getDicts("party_auth_status").then(response => {
        this.statusOptions = response.data;
      });
    },
    methods: {
      // 业务类型字典翻译
      tradeCtgyCodeFormat(row, column) {
        return this.selectDictLabel(this.tradeCtgyCodeOptions, row.tradeCtgyCode);
      },
      // 发送权限标识字典翻译
      sendAuthFormat(row, column) {
        return this.selectDictLabel(this.AuthrtySgnCdOptions, row.sendAuth);
      },
      // 接收权限标识字典翻译
      recvAuthFormat(row, column) {
        return this.selectDictLabel(this.AuthrtySgnCdOptions, row.recvAuth);
      },
      // 接收权限标识字典翻译
      statusFormat(row, column) {
        return this.selectDictLabel(this.statusOptions, row.status);
      },

      /** 查询业务权限变更信息列表 */
      getList() {
        this.loading = true;
        listPartyauth(this.queryParams).then(response => {
          this.partyauthList = response.rows;
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
          partyId: null,
          msgType: null,
          tradeCtgyCode: null,
          sendAuth: null,
          recvAuth: null,
          status: "0",
          effectDate: null,
          inEffectiveDate: null,
          lastUpDate: null,
          lastUpTime: null
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
        this.ids = selection.map(item => item.partyId)
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "添加业务权限变更信息";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const partyId = row.partyId || this.ids
        getPartyauth(partyId).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改业务权限变更信息";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.partyId != null) {
              updatePartyauth(this.form).then(response => {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addPartyauth(this.form).then(response => {
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
        const partyIds = row.partyId || this.ids;
        this.$confirm('是否确认删除业务权限变更信息编号为"' + partyIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return delPartyauth(partyIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
      },
      /** 导出按钮操作 */
      handleExport() {
        this.download('/pay-batch/partyauth/export', {
          ...this.queryParams
        }, `system_partyauth.xlsx`)
      }
    }
  };
</script>
