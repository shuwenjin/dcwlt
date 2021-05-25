<template>
  <div class="app-container">
    <Account></Account>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:cashparty:add']"
        >新增
        </el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="partyList">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="机构名称" align="center" prop="partyname" v-if="columns[1].visible"/>
      <el-table-column label="机构编码" align="center" prop="partyid" v-if="columns[2].visible"/>
      <el-table-column label="钱柜钱包id" align="center" prop="cashboxid" v-if="columns[3].visible"/>
      <el-table-column label="预警金额" align="center" prop="earlywarningamount" v-if="columns[9].visible"/>
      <el-table-column label="自动入库金额" align="center" prop="automaticstorage" v-if="columns[10].visible"/>
      <el-table-column label="是否自动入库" :formatter="paramStatusFormat" align="center" prop="automaticstuts"
                       v-if="columns[11].visible"/>
      <el-table-column label="更新人" align="center" prop="updatename" v-if="columns[8].visible"/>
      <el-table-column label="创建人" align="center" prop="cretername" v-if="columns[6].visible"/>
      <el-table-column label="创建时间" align="center" prop="cretertime" width="180" v-if="columns[7].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.cretertime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="最后更新日期" align="center" prop="lastupdate" v-if="columns[4].visible"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:cashparty:edit']">修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate_warn(scope.row)"
            v-hasPermi="['system:cashparty:warn']">预警余额设置
          </el-button>
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

    <!-- 添加或修改运营机构钱柜参数对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-form-item label="机构名称" prop="partyname">
          <el-input v-model="form.partyname" placeholder="请输入机构名称"/>
        </el-form-item>
        <el-form-item label="机构编码" prop="partyid">
          <el-input v-model="form.partyid" placeholder="请输入机构编码"/>
        </el-form-item>
        <el-form-item label="钱柜钱包id" prop="cashboxid">
          <el-input v-model="form.cashboxid" placeholder="请输入钱柜钱包id"/>
        </el-form-item>
        <el-form-item label="预警金额" prop="earlywarningamount">
          <el-input-number :disabled="true" v-model="form.earlywarningamount" placeholder="请输入预警金额"/>
        </el-form-item>
        <el-form-item label="自动入库金额" prop="automaticstorage">
          <el-input-number v-model="form.automaticstorage" placeholder="请输入自动入库金额"/>
        </el-form-item>
        <el-form-item label="是否自动入库" prop="automaticstuts">
          <el-select v-model="form.automaticstuts" placeholder="请选择">
            <el-option
              v-for="dict in paramStatusOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 修改运营机构钱柜余额预警参数对话框 -->
    <el-dialog :title="title" :visible.sync="open_warn" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-form-item label="机构名称" prop="partyname">
          <el-input :disabled="true" v-model="form.partyname" placeholder="请输入机构名称"/>
        </el-form-item>
        <el-form-item label="机构编码" prop="partyid">
          <el-input :disabled="true" v-model="form.partyid" placeholder="请输入机构编码"/>
        </el-form-item>
        <el-form-item label="钱柜钱包id" prop="cashboxid">
          <el-input :disabled="true" v-model="form.cashboxid" placeholder="请输入钱柜钱包id"/>
        </el-form-item>
        <el-form-item label="预警金额" prop="earlywarningamount">
          <el-input-number v-model="form.earlywarningamount" placeholder="请输入预警金额"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFormWarn">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
    import {listParty, getParty, delParty, addParty, updateParty,updatePartyWarn} from "@/api/pay-batch/cashparty";


    export default {
        name: "Party",
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
                paramStatusOptions: [],
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
                    {key: 0, label: `id`, visible: true},
                    {key: 1, label: `机构名称`, visible: true},
                    {key: 2, label: `机构编码`, visible: true},
                    {key: 3, label: `钱柜钱包id`, visible: true},
                    {key: 4, label: `最后更新日期`, visible: false},
                    {key: 5, label: `最后更新时间`, visible: false},
                    {key: 6, label: `创建人`, visible: false},
                    {key: 7, label: `创建时间`, visible: false},
                    {key: 8, label: `更新人`, visible: false},
                    {key: 9, label: `预警金额`, visible: true},
                    {key: 10, label: `自动入库金额`, visible: true},
                    {key: 11, label: `是否自动入库`, visible: true},
                ],
                // 表单参数
                form: {},
                // 表单校验
                rules: {
                    partyid: [
                        {required: true, message: "机构编码不能为空", trigger: "blur"}
                    ],
                    partyname: [
                        {required: true, message: "机构名称不能为空", trigger: "blur"}
                    ],
                    cashboxid: [
                        {required: true, message: "钱柜钱包id不能为空", trigger: "blur"}
                    ],
                    earlywarningamount: [
                        {required: true, message: "预警金额不能为空", trigger: "blur"}
                    ],
                    automaticstorage: [
                        {required: true, message: "自动入库金额不能为空", trigger: "blur"}
                    ],
                    automaticstuts: [
                        {required: true, message: "是否自动入库不能为空", trigger: "blur"}
                    ],
                }
            };
        },
        created() {
            this.getList();
            this.getDicts("automatic_stuts").then(response => {
                this.paramStatusOptions = response.data;
            });
        },
        methods: {
            /** 查询运营机构钱柜参数列表 */
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
            /** 搜索按钮操作 */
            handleQuery() {
                this.queryParams.pageNum = 1;
                this.getList();
            },
            // 参数状态字典翻译
            paramStatusFormat(row, column) {
                return this.selectDictLabel(this.paramStatusOptions, row.automaticstuts);
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
                this.title = "添加运营机构钱柜参数";
                this.form.automaticstuts = this.paramStatusOptions[0].dictValue;
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
                  addParty(this.form).then(response => {
                    this.msgSuccess("新增成功");
                    this.open = false;
                    this.open_warn = false;
                    this.getList();
                  });
                }
              }
            });
          },
          /** 提交按钮 */
          submitFormWarn() {
            this.$refs["form"].validate(valid => {
              if (valid) {
                updatePartyWarn(this.form).then(response => {
                    this.msgSuccess("设置成功");
                    this.open = false;
                    this.open_warn = false;
                    this.getList();
                  });
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
            }
        }
    };
</script>
