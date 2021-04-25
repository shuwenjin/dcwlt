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
      <el-form-item label="证书类型" prop="certType">
        <el-select v-model="queryParams.certType" placeholder="请选择证书类型" clearable size="small">
          <el-option
            v-for="dict in certTypeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="证书状态" prop="certStatus">
        <el-select v-model="queryParams.certStatus" placeholder="请选择证书状态" clearable size="small">
          <el-option
            v-for="dict in certStatusOptions"
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
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['pay-batch:certs:add']"
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
          v-hasPermi="['pay-batch:certs:edit']"
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
          v-hasPermi="['pay-batch:certs:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['pay-batch:certs:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="certsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="机构编码" align="center" prop="partyId" v-if="columns[0].visible" />
      <el-table-column label="证书类型" align="center" prop="certType" :formatter="certTypeFormat" v-if="columns[1].visible" />
      <el-table-column label="证书状态" align="center" prop="certStatus" :formatter="certStatusFormat" v-if="columns[2].visible" />
      <el-table-column label="证书编号" align="center" prop="certNo" v-if="columns[3].visible" />
      <el-table-column label="公钥" align="center" prop="publicKey" v-if="columns[4].visible" />
      <el-table-column label="私钥" align="center" prop="privateKey" v-if="columns[5].visible" />
      <el-table-column label="生效时间" align="center" prop="effectTime" v-if="columns[6].visible" />
      <el-table-column label="失效时间" align="center" prop="expiredTime" v-if="columns[7].visible" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180" v-if="columns[8].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新时间" align="center" prop="updateTime" width="180" v-if="columns[9].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['pay-batch:certs:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['pay-batch:certs:remove']"
          >删除</el-button>
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

    <!-- 添加或修改证书管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">

        <el-form-item label="机构编码" prop="partyId">
          <el-input v-model="form.partyId" placeholder="请输入机构编码" />
        </el-form-item>
        <el-form-item label="证书类型" prop="certType">
          <el-select v-model="form.certType" placeholder="请选择证书类型">
            <el-option
              v-for="dict in certTypeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="证书状态" prop="certStatus">
          <el-select v-model="form.certStatus" placeholder="请选择证书状态">
            <el-option
              v-for="dict in certStatusOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="证书编号" prop="certNo">
          <el-input v-model="form.certNo" placeholder="请输入证书编号" />
        </el-form-item>
        <el-form-item label="公钥" prop="publicKey">
          <el-input v-model="form.publicKey" placeholder="请输入公钥" />
        </el-form-item>
        <el-form-item label="私钥" prop="privateKey">
          <el-input v-model="form.privateKey" placeholder="请输入私钥" />
        </el-form-item>
        <el-form-item label="生效时间" prop="effectTime">
          <el-input v-model="form.effectTime" placeholder="请输入生效时间" />
        </el-form-item>
        <el-form-item label="失效时间" prop="expiredTime">
          <el-input v-model="form.expiredTime" placeholder="请输入失效时间" />
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
import { listCerts, getCerts, delCerts, addCerts, updateCerts } from "@/api/pay-batch/certs";

export default {
  name: "Certs",
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
      // 证书管理表格数据
      certsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 证书类型字典
      certTypeOptions: [],
      // 证书状态字典
      certStatusOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        partyId: null,
        certType: null,
        certStatus: null,
      },
      // 列信息
      columns: [
        { key: 0, label: `机构编码`, visible: true },
        { key: 1, label: `证书类型`, visible: true },
        { key: 2, label: `证书状态`, visible: true },
        { key: 3, label: `证书编号`, visible: true },
        { key: 4, label: `公钥`, visible: false },
        { key: 5, label: `私钥`, visible: false },
        { key: 6, label: `生效时间`, visible: false },
        { key: 7, label: `失效时间`, visible: true },
        { key: 8, label: `创建时间`, visible: false },
        { key: 9, label: `更新时间`, visible: false },
      ],
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        certType: [
          { required: true, message: "证书类型不能为空", trigger: "change" }
        ],
        certStatus: [
          { required: true, message: "证书状态不能为空", trigger: "change" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("certificate_type_code").then(response => {
      this.certTypeOptions = response.data;
    });
    this.getDicts("certificate_change_code").then(response => {
      this.certStatusOptions = response.data;
    });
  },
  methods: {
    /** 查询证书管理列表 */
    getList() {
      this.loading = true;
      listCerts(this.queryParams).then(response => {
        this.certsList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 证书类型字典翻译
    certTypeFormat(row, column) {
      return this.selectDictLabel(this.certTypeOptions, row.certType);
    },
    // 证书状态字典翻译
    certStatusFormat(row, column) {
      return this.selectDictLabel(this.certStatusOptions, row.certStatus);
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
        certType: null,
        certStatus: null,
        certNo: null,
        publicKey: null,
        privateKey: null,
        effectTime: null,
        expiredTime: null,
        createTime: null,
        updateTime: null
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
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加证书管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getCerts(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改证书管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateCerts(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCerts(this.form).then(response => {
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
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除证书管理编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delCerts(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('pay-batch/certs/export', {
        ...this.queryParams
      }, `pay-batch_certs.xlsx`)
    }
  }
};
</script>
