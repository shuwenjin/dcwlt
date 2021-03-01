<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="机构类型" prop="partytype">
        <el-select v-model="queryParams.partytype" placeholder="请选择机构类型" clearable size="small">
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="机构名称" prop="partyname">
        <el-input
          v-model="queryParams.partyname"
          placeholder="请输入机构名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="机构标识" prop="partyalias">
        <el-input
          v-model="queryParams.partyalias"
          placeholder="请输入机构标识"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="机构状态" prop="partystatus">
        <el-select v-model="queryParams.partystatus" placeholder="请选择机构状态" clearable size="small">
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="撤销状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择撤销状态" clearable size="small">
          <el-option label="请选择字典生成" value="" />
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
          v-hasPermi="['system:party:add']"
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
          v-hasPermi="['system:party:edit']"
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
          v-hasPermi="['system:party:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:party:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="partyList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="机构编码" align="center" prop="partyid" v-if="columns[0].visible"/>
      <el-table-column label="机构类型" align="center" prop="partytype" v-if="columns[1].visible"/>
      <el-table-column label="机构名称" align="center" prop="partyname" v-if="columns[2].visible"/>
      <el-table-column label="机构标识" align="center" prop="partyalias" v-if="columns[3].visible"/>
      <el-table-column label="机构状态" align="center" prop="partystatus" v-if="columns[4].visible"/>
      <el-table-column label="撤销状态" align="center" prop="status" v-if="columns[5].visible"/>
      <el-table-column label="联系人" align="center" prop="contact" v-if="columns[6].visible"/>
      <el-table-column label="电话" align="center" prop="telephone" v-if="columns[7].visible"/>
      <el-table-column label="邮箱" align="center" prop="mail" v-if="columns[8].visible"/>
      <el-table-column label="传真号" align="center" prop="fax" v-if="columns[9].visible"/>
      <el-table-column label="失效日期" align="center" prop="effectdate" v-if="columns[10].visible"/>
      <el-table-column label="失效日期" align="center" prop="ineffectivedate" v-if="columns[11].visible"/>
      <el-table-column label="变更期数" align="center" prop="changenumber" v-if="columns[12].visible"/>
      <el-table-column label="变更记录条目" align="center" prop="changecircletimes" v-if="columns[13].visible"/>
      <el-table-column label="最后更新日期" align="center" prop="lastupdate" v-if="columns[15].visible"/>
      <el-table-column label="最后更新时间" align="center" prop="lastuptime" v-if="columns[15].visible"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:party:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:party:remove']"
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

    <!-- 添加或修改机构对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="机构类型" prop="partytype">
          <el-select v-model="form.partytype" placeholder="请选择机构类型">
            <el-option label="AAA" value="AAA" />
            <el-option label="BBB" value="BBB" />
          </el-select>
        </el-form-item>
        <el-form-item label="机构ID" prop="partyname">
          <el-input v-model="form.partyid" placeholder="请输入机构ID" />
        </el-form-item>
        <el-form-item label="机构名称" prop="partyname">
          <el-input v-model="form.partyname" placeholder="请输入机构名称" />
        </el-form-item>
        <el-form-item label="机构标识" prop="partyalias">
          <el-input v-model="form.partyalias" placeholder="请输入机构标识" />
        </el-form-item>
        <el-form-item label="机构状态">
          <el-radio-group v-model="form.partystatus">
            <el-radio label="1">请选择字典生成</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="撤销状态">
          <el-radio-group v-model="form.status">
            <el-radio label="1">请选择字典生成</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="联系人" prop="contact">
          <el-input v-model="form.contact" placeholder="请输入联系人" />
        </el-form-item>
        <el-form-item label="电话" prop="telephone">
          <el-input v-model="form.telephone" placeholder="请输入电话" />
        </el-form-item>
        <el-form-item label="邮箱" prop="mail">
          <el-input v-model="form.mail" placeholder="请输入邮箱" />
        </el-form-item>
        <el-form-item label="传真号" prop="fax">
          <el-input v-model="form.fax" placeholder="请输入传真号" />
        </el-form-item>
        <el-form-item label="失效日期" prop="effectdate">
          <el-input v-model="form.effectdate" placeholder="请输入失效日期" />
        </el-form-item>
        <el-form-item label="失效日期" prop="ineffectivedate">
          <el-input v-model="form.ineffectivedate" placeholder="请输入失效日期" />
        </el-form-item>
        <el-form-item label="变更期数" prop="changenumber">
          <el-input v-model="form.changenumber" placeholder="请输入变更期数" />
        </el-form-item>
        <el-form-item label="变更记录条目" prop="changecircletimes">
          <el-input v-model="form.changecircletimes" placeholder="请输入变更记录条目" />
        </el-form-item>
        <el-form-item label="最后更新日期" prop="lastupdate">
          <el-input v-model="form.lastupdate" placeholder="请输入最后更新日期" />
        </el-form-item>
        <el-form-item label="最后更新时间" prop="lastuptime">
          <el-input v-model="form.lastuptime" placeholder="请输入最后更新时间" />
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
import { listParty, getParty, delParty, addParty, updateParty } from "@/api/system/party";

export default {
  name: "Party",
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
      // 机构表格数据
      partyList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        partytype: null,
        partyname: null,
        partyalias: null,
        partystatus: null,
        status: null,
        contact: null,
        telephone: null,
        mail: null,
        fax: null,
        effectdate: null,
        ineffectivedate: null,
        changenumber: null,
        changecircletimes: null,
        lastupdate: null,
        lastuptime: null
      },
      // 列信息
      columns: [
        { key: 0,  label: `机构编码`, visible: true },
        { key: 1,  label: `机构类型`, visible: true },
        { key: 2,  label: `机构名称`, visible: true },
        { key: 3,  label: `机构标识`, visible: true },
        { key: 4,  label: `机构状态`, visible: true },
        { key: 5,  label: `撤销状态`, visible: true },
        { key: 6,  label: `联系人`, visible: true },
        { key: 7,  label: `电话`, visible: true },
        { key: 8,  label: `邮箱`, visible: true },
        { key: 9,  label: `传真号`, visible: true },
        { key: 10, label: `失效日期`, visible: true },
        { key: 11, label: `失效日期`, visible: true },
        { key: 12, label: `变更期数`, visible: true },
        { key: 13, label: `变更记录条`, visible: true },
        { key: 14, label: `最后更新日期`, visible: true },
        { key: 15, label: `最后更新时间`, visible: true }
      ],
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        partytype: [
          { required: true, message: "机构类型不能为空", trigger: "change" }
        ],
        partystatus: [
          { required: true, message: "机构状态不能为空", trigger: "blur" }
        ],
        status: [
          { required: true, message: "撤销状态不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询机构列表 */
    getList() {
      this.loading = true;
      listParty(this.queryParams).then(response => {
        this.partyList = response.rows;
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
        partyid: null,
        partytype: null,
        partyname: null,
        partyalias: null,
        partystatus: "0",
        status: "0",
        contact: null,
        telephone: null,
        mail: null,
        fax: null,
        effectdate: null,
        ineffectivedate: null,
        changenumber: null,
        changecircletimes: null,
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
      this.ids = selection.map(item => item.partyid)
      this.single = selection.length!==1
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
      const partyid = row.partyid || this.ids
      getParty(partyid).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改机构";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.partyid != null) {
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
      const partyids = row.partyid || this.ids;
      this.$confirm('是否确认删除机构编号为"' + partyids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delParty(partyids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/party/export', {
        ...this.queryParams
      }, `system_party.xlsx`)
    }
  }
};
</script>
