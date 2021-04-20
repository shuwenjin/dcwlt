<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="参数类型" prop="paramType">
        <el-select v-model="queryParams.paramType" placeholder="请选择参数类型" clearable size="small">
          <el-option
            v-for="dict in paramTypeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="参数key" prop="paramKey">
        <el-input
          v-model="queryParams.paramKey"
          placeholder="请输入参数key"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="参数数值" prop="paramValue">
        <el-input
          v-model="queryParams.paramValue"
          placeholder="请输入参数数值"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="参数描述" prop="paramDesc">
        <el-input
          v-model="queryParams.paramDesc"
          placeholder="请输入参数描述"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="参数状态" prop="paramStatus">
        <el-select v-model="queryParams.paramStatus" placeholder="请选择参数状态" clearable size="small">
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
          v-hasPermi="['system:param:add']"
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
          v-hasPermi="['system:param:edit']"
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
          v-hasPermi="['system:param:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:param:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="paramList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
 <!--     <el-table-column label="参数状态" align="center" prop="id" v-if="columns[0].visible" /> -->
      <el-table-column label="参数类型" align="center" prop="paramType" :formatter="paramTypeFormat" v-if="columns[1].visible" />
      <el-table-column label="参数key" align="center" prop="paramKey" v-if="columns[2].visible" />
      <el-table-column label="参数数值" align="center" prop="paramValue" v-if="columns[3].visible" />
      <el-table-column label="参数描述" align="center" prop="paramDesc" v-if="columns[4].visible" />
      <el-table-column label="参数状态" align="center" prop="paramStatus" :formatter="paramStatusFormat" v-if="columns[5].visible" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:param:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:param:remove']"
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

    <!-- 添加或修改参数配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="参数类型" prop="paramType">
          <el-select v-model="form.paramType" placeholder="请选择参数类型">
            <el-option
              v-for="dict in paramTypeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="参数key" prop="paramKey">
          <el-input v-model="form.paramKey" placeholder="请输入参数key" />
        </el-form-item>
        <el-form-item label="参数数值" prop="paramValue">
          <el-input v-model="form.paramValue" placeholder="请输入参数数值" />
        </el-form-item>
        <el-form-item label="参数描述" prop="paramDesc">
          <el-input v-model="form.paramDesc" placeholder="请输入参数描述" />
        </el-form-item>
        <el-form-item label="参数状态">
          <el-radio-group v-model="form.paramStatus">
            <el-radio
              v-for="dict in paramStatusOptions"
              :key="dict.dictValue"
              :label="parseInt(dict.dictValue)"
            >{{dict.dictLabel}}</el-radio>
          </el-radio-group>
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
import { listParam, getParam, delParam, addParam, updateParam } from "@/api/pay-batch/param";

export default {
  name: "Param",
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
      // 参数配置表格数据
      paramList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 参数类型字典
      paramTypeOptions: [],
      // 参数状态字典
      paramStatusOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        paramType: null,
        paramKey: null,
        paramValue: null,
        paramDesc: null,
        paramStatus: null,
      },
      // 列信息
      columns: [
        { key: 0, label: `参数更新时间`, visible: true },
        { key: 1, label: `参数类型`, visible: true },
        { key: 2, label: `参数key`, visible: true },
        { key: 3, label: `参数数值`, visible: true },
        { key: 4, label: `参数描述`, visible: true },
        { key: 5, label: `参数状态`, visible: true },
      ],
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        paramType: [
          { required: true, message: "参数类型不能为空", trigger: "change" }
        ],
        paramKey: [
          { required: true, message: "参数key不能为空", trigger: "blur" }
        ],
        paramValue: [
          { required: true, message: "参数数值不能为空", trigger: "blur" }
        ],
        paramStatus: [
          { required: true, message: "参数状态不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("param_type").then(response => {
      this.paramTypeOptions = response.data;
    });
    this.getDicts("param_status").then(response => {
      this.paramStatusOptions = response.data;
    });
  },
  methods: {
    /** 查询参数配置列表 */
    getList() {
      this.loading = true;
      listParam(this.queryParams).then(response => {
        this.paramList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 参数类型字典翻译
    paramTypeFormat(row, column) {
      return this.selectDictLabel(this.paramTypeOptions, row.paramType);
    },
    // 参数状态字典翻译
    paramStatusFormat(row, column) {
      return this.selectDictLabel(this.paramStatusOptions, row.paramStatus);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        paramType: null,
        paramKey: null,
        paramValue: null,
        paramDesc: null,
        paramStatus: 0,
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
      this.title = "添加参数配置";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getParam(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改参数配置";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateParam(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addParam(this.form).then(response => {
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
      this.$confirm('是否确认删除参数配置编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delParam(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/param/export', {
        ...this.queryParams
      }, `system_param.xlsx`)
    }
  }
};
</script>
