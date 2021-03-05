<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="机构编码" prop="partyID">
        <el-input
          v-model="queryParams.partyID"
          placeholder="请输入机构编码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="机构类型" prop="partyType">
        <el-select v-model="queryParams.partyType" placeholder="请选择机构类型" clearable size="small">
          <el-option
            v-for="dict in partytypeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="机构名称" prop="partyName">
        <el-input
          v-model="queryParams.partyName"
          placeholder="请输入机构名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="机构标识" prop="partyAlias">
        <el-input
          v-model="queryParams.partyAlias"
          placeholder="请输入机构标识"
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
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="primary"-->
      <!--          plain-->
      <!--          icon="el-icon-plus"-->
      <!--          size="mini"-->
      <!--          @click="handleAdd"-->
      <!--          v-hasPermi="['pay-batch:party:add']"-->
      <!--        >新增</el-button>-->
      <!--      </el-col>-->
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="success"-->
      <!--          plain-->
      <!--          icon="el-icon-edit"-->
      <!--          size="mini"-->
      <!--          :disabled="single"-->
      <!--          @click="handleUpdate"-->
      <!--          v-hasPermi="['pay-batch:party:edit']"-->
      <!--        >修改</el-button>-->
      <!--      </el-col>-->
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="danger"-->
      <!--          plain-->
      <!--          icon="el-icon-delete"-->
      <!--          size="mini"-->
      <!--          :disabled="multiple"-->
      <!--          @click="handleDelete"-->
      <!--          v-hasPermi="['pay-batch:party:remove']"-->
      <!--        >删除</el-button>-->
      <!--      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['pay-batch:party:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="partyList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="机构编码" align="center" prop="partyID" v-if="columns[0].visible":show-overflow-tooltip="true"/>
      <el-table-column label="机构类型" align="center" prop="partyType" :formatter="partytypeFormat" v-if="columns[1].visible":show-overflow-tooltip="true"/>
      <el-table-column label="机构名称" align="center" prop="partyName" v-if="columns[2].visible":show-overflow-tooltip="true"/>
      <el-table-column label="机构标识" align="center" prop="partyAlias" v-if="columns[3].visible":show-overflow-tooltip="true"/>
      <el-table-column label="机构状态" align="center" prop="partyStatus" v-if="columns[4].visible":show-overflow-tooltip="true"/>
      <el-table-column label="撤销状态" align="center" prop="status" v-if="columns[5].visible":show-overflow-tooltip="true"/>
      <el-table-column label="联系人" align="center" prop="contact" v-if="columns[6].visible":show-overflow-tooltip="true"/>
      <el-table-column label="电话" align="center" prop="telephone" v-if="columns[7].visible":show-overflow-tooltip="true"/>
      <el-table-column label="邮箱" align="center" prop="mail" v-if="columns[8].visible":show-overflow-tooltip="true"/>
      <el-table-column label="传真号" align="center" prop="fax" v-if="columns[9].visible":show-overflow-tooltip="true"/>
      <el-table-column label="失效日期" align="center" prop="effectDate" v-if="columns[10].visible":show-overflow-tooltip="true"/>
      <el-table-column label="失效日期" align="center" prop="inEffectiveDate" v-if="columns[11].visible":show-overflow-tooltip="true"/>
      <el-table-column label="变更期数" align="center" prop="changeNumber" v-if="columns[12].visible":show-overflow-tooltip="true"/>
      <el-table-column label="变更记录条目" align="center" prop="changeCircleTimes" v-if="columns[13].visible":show-overflow-tooltip="true"/>
      <el-table-column label="最后更新日期" align="center" prop="lastUpDate" v-if="columns[14].visible":show-overflow-tooltip="true"/>
      <el-table-column label="最后更新时间" align="center" prop="lastUpTime" v-if="columns[15].visible":show-overflow-tooltip="true"/>
<!--      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" v-if="columns[16].visible":show-overflow-tooltip="true">-->
<!--        <template slot-scope="scope">-->
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-edit"-->
<!--            @click="handleUpdate(scope.row)"-->
<!--            v-hasPermi="['pay-batch:party:edit']"-->
<!--          >修改-->
<!--          </el-button>-->
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-delete"-->
<!--            @click="handleDelete(scope.row)"-->
<!--            v-hasPermi="['pay-batch:party:remove']"-->
<!--          >删除-->
<!--          </el-button>-->
<!--        </template>-->
<!--      </el-table-column>-->
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改机构对话框 -->
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
  import {listParty, getParty, delParty, addParty, updateParty} from "@/api/pay-batch/party";

  export default {
    name: "Party",
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
        // 机构表格数据
        partyList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 机构类型字典
        partytypeOptions: [],
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          partyID: null,
          partyType: "",
          partyName: null,
          partyAlias: null,
        },
        // 列信息
        columns: [
          {key: 0, label: `机构编码`, visible: true},
          {key: 1, label: `机构类型`, visible: true},
          {key: 2, label: `机构名称`, visible: true},
          {key: 3, label: `机构标识`, visible: true},
          {key: 4, label: `机构状态`, visible: true},
          {key: 5, label: `撤销状态`, visible: true},
          {key: 6, label: `联系人`, visible: true},
          {key: 7, label: `电话`, visible: true},
          {key: 8, label: `邮件`, visible: true},
          {key: 9, label: `传真号`, visible: true},
          {key: 10, label: `生效日期`, visible: true},
          {key: 11, label: `失效日期`, visible: true},
          {key: 12, label: `变更期数`, visible: true},
          {key: 13, label: `变更记录条目`, visible: true},
          {key: 14, label: `最后更新日期`, visible: true},
          {key: 15, label: `最后更新时间`, visible: true}
        ],
        // 表单参数
        form: {},
        // 表单校验
        rules: {}
      };
    },
    created() {
      this.getList();
      this.getDicts("partyType").then(response => {
        this.partytypeOptions = response.data;
      });
    },
    methods: {
      /** 查询机构列表 */
      getList() {
        this.loading = true;
        listParty(this.addDateRange(this.queryParams)).then(response => {
          this.partyList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      // 机构类型字典翻译
      partytypeFormat(row, column) {
        return this.selectDictLabel(this.partytypeOptions, row.partyType);
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
      // 表单重置
      reset() {
        this.form = {
          partyID: null,
          partyType: null,
          partyName: null,
          partyAlias: null,
          partyStatus: "0",
          status: "0",
          contact: null,
          telephone: null,
          mail: null,
          fax: null,
          effectDate: null,
          inEffectiveDate: null,
          changeNumber: null,
          changeCircleTimes: null,
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
        this.ids = selection.map(item => item.partyID)
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "添加机构";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const partyID = row.partyID || this.ids
        getParty(partyID).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改机构";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.partyID != null) {
              updateParty(this.form).then(response => {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addParty(this.form).then(response => {
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
        const partyids = row.partyID || this.ids;
        this.$confirm('是否确认删除机构编号为"' + partyids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return delParty(partyids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
      },
      /** 导出按钮操作 */
      handleExport() {
        this.download('pay-batch/party/export', {
          ...this.queryParams
        }, `pay-batch_party.xlsx`)
      }
    }
  };
</script>
