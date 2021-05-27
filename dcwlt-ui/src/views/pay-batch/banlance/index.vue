<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="对账日期" prop="chckngdt">
        <el-date-picker clearable size="small"
                        v-model="queryParams.cretertime"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="选择创建时间"
                        @keyup.enter.native="handleQuery">
        </el-date-picker>
      </el-form-item>

      <el-form-item label="钱柜所属运营机构" label-width="128">
        <el-select v-model="queryParams.cshboxinstnid" placeholder="钱柜所属运营机构" clearable size="small">
          <el-option
            v-for="cshparty in paramNameOptions"
            :key="cshparty.partyid"
            :label="cshparty.partyname"
            :value="cshparty.partyid"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">查询</el-button>
      </el-form-item>
    </el-form>


    <el-table v-loading="loading" :data="banlanceList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="id" align="center" prop="id" v-if="columns[0].visible"/>
      <el-table-column label="对账日期" align="center" prop="chckngdt" v-if="columns[1].visible"/>
      <el-table-column label="合作银行钱柜ID" align="center" prop="coopbankwltid" v-if="columns[3].visible"/>
      <el-table-column label="钱柜所属运营机构" align="center" prop="cshboxinstnid" :formatter="paramNameFormat" v-if="columns[4].visible"/>
      <el-table-column label="期初余额货币符号" align="center" prop="initlamtccy" v-if="columns[5].visible"/>
      <el-table-column label="期初余额" align="center" prop="initlamtvalue" v-if="columns[6].visible"/>
      <el-table-column label="借贷标识" align="center" prop="cdtdbtind" v-if="columns[7].visible"/>
      <el-table-column label="借方金额" align="center" prop="dbtcntamtvalue" v-if="columns[9].visible"/>
      <el-table-column label="贷方金额" align="center" prop="cdtcntamtvalue" v-if="columns[11].visible"/>
      <el-table-column label="期末余额" align="center" prop="fnlamtvalue" v-if="columns[13].visible"/>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改钱柜余额对账通知对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="对账日期" prop="chckngdt">
          <el-input v-model="form.chckngdt" placeholder="请输入对账日期"/>
        </el-form-item>
        <el-form-item label="合作银行机构编码" prop="coopbankinstnid">
          <el-input v-model="form.coopbankinstnid" placeholder="请输入合作银行机构编码"/>
        </el-form-item>
        <el-form-item label="合作银行钱柜ID" prop="coopbankwltid">
          <el-input v-model="form.coopbankwltid" placeholder="请输入合作银行钱柜ID"/>
        </el-form-item>
        <el-form-item label="钱柜所属运营机构" prop="cshboxinstnid">
          <el-input v-model="form.cshboxinstnid" placeholder="请输入钱柜所属运营机构"/>
        </el-form-item>
        <el-form-item label="期初余额货币符号" prop="initlamtccy">
          <el-input v-model="form.initlamtccy" placeholder="请输入期初余额货币符号"/>
        </el-form-item>
        <el-form-item label="期初余额" prop="initlamtvalue">
          <el-input v-model="form.initlamtvalue" placeholder="请输入期初余额"/>
        </el-form-item>
        <el-form-item label="借贷标识" prop="cdtdbtind">
          <el-input v-model="form.cdtdbtind" placeholder="请输入借贷标识"/>
        </el-form-item>
        <el-form-item label="借方金额货币符号" prop="dbtcntamtccy">
          <el-input v-model="form.dbtcntamtccy" placeholder="请输入借方金额货币符号"/>
        </el-form-item>
        <el-form-item label="借方金额" prop="dbtcntamtvalue">
          <el-input v-model="form.dbtcntamtvalue" placeholder="请输入借方金额"/>
        </el-form-item>
        <el-form-item label="贷方金额货币符号" prop="cdtcntamtccy">
          <el-input v-model="form.cdtcntamtccy" placeholder="请输入贷方金额货币符号"/>
        </el-form-item>
        <el-form-item label="贷方金额" prop="cdtcntamtvalue">
          <el-input v-model="form.cdtcntamtvalue" placeholder="请输入贷方金额"/>
        </el-form-item>
        <el-form-item label="期末余额货币符号" prop="fnlamtccy">
          <el-input v-model="form.fnlamtccy" placeholder="请输入期末余额货币符号"/>
        </el-form-item>
        <el-form-item label="期末余额" prop="fnlamtvalue">
          <el-input v-model="form.fnlamtvalue" placeholder="请输入期末余额"/>
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
  import {listParty} from "@/api/pay-batch/cashparty";
    import {
        listBanlance,
        getBanlance,
        delBanlance,
        addBanlance,
        updateBanlance
    } from "@/api/pay-batch/banlance";


    export default {
        name: "Banlance",
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
                // 所属机构
                paramNameOptions: [],
                // 显示搜索条件
                showSearch: true,
                // 总条数
                total: 0,
                // 钱柜余额对账通知表格数据
                banlanceList: [],
                // 弹出层标题
                title: "",
                // 查询参数
                query: {
                    pageNum: 1,
                    pageSize: 100
                },
                // 是否显示弹出层
                open: false,
                // 查询参数
                queryParams: {
                    pageNum: 1,
                    pageSize: 10,
                    chckngdt: null,
                    coopbankinstnid: null,
                    coopbankwltid: null,
                    cshboxinstnid: null,
                    initlamtccy: null,
                    initlamtvalue: null,
                    cdtdbtind: null,
                    dbtcntamtccy: null,
                    dbtcntamtvalue: null,
                    cdtcntamtccy: null,
                    cdtcntamtvalue: null,
                    fnlamtccy: null,
                    fnlamtvalue: null
                },
                // 列信息
                columns: [
                    {key: 0, label: `id`, visible: true},
                    {key: 1, label: `对账日期`, visible: true},
                    {key: 2, label: `合作银行机构编码`, visible: true},
                    {key: 3, label: `合作银行钱柜ID`, visible: true},
                    {key: 4, label: `钱柜所属运营机构`, visible: true},
                    {key: 5, label: `期初余额货币符号`, visible: true},
                    {key: 6, label: `期初余额`, visible: true},
                    {key: 7, label: `借贷标识`, visible: true},
                    {key: 8, label: `借方金额货币符号`, visible: true},
                    {key: 9, label: `借方金额`, visible: true},
                    {key: 10, label: `贷方金额货币符号`, visible: true},
                    {key: 11, label: `贷方金额`, visible: true},
                    {key: 12, label: `期末余额货币符号`, visible: true},
                    {key: 13, label: `期末余额`, visible: true},
                ],
                // 表单参数
                form: {},
                // 表单校验
                rules: {}
            };
        },
        created() {
            this.getList();
            this.getpaty();
        },
        methods: {
            /** 查询钱柜余额对账通知列表 */
            getList() {
                this.loading = true;
                listBanlance(this.queryParams).then(response => {
                    this.banlanceList = response.rows;
                    this.total = response.total;
                    this.loading = false;
                });
            },
            /** 查询钱柜余额对账通知列表 */
            getpaty() {
                this.loading = true;
                listParty(this.query).then(response => {
                    this.paramNameOptions = response.rows;
                });
            },
            // 查询钱柜余额对账通知字典翻译
            paramNameFormat(row, column) {
                for (let i = 0; i < this.paramNameOptions.length; i++) {
                    var obj=this.paramNameOptions[i];
                    if(obj.partyid==row.cshboxinstnid){
                        return this.paramNameOptions[i].partyname;
                    }
                }
                return row.cshboxinstnid;
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
                    chckngdt: null,
                    coopbankinstnid: null,
                    coopbankwltid: null,
                    cshboxinstnid: null,
                    initlamtccy: null,
                    initlamtvalue: null,
                    cdtdbtind: null,
                    dbtcntamtccy: null,
                    dbtcntamtvalue: null,
                    cdtcntamtccy: null,
                    cdtcntamtvalue: null,
                    fnlamtccy: null,
                    fnlamtvalue: null
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
                this.single = selection.length !== 1
                this.multiple = !selection.length
            },
            /** 新增按钮操作 */
            handleAdd() {
                this.reset();
                this.open = true;
                this.title = "添加钱柜余额对账通知";
            },
            /** 修改按钮操作 */
            handleUpdate(row) {
                this.reset();
                const id = row.id || this.ids
                getBanlance(id).then(response => {
                    this.form = response.data;
                    this.open = true;
                    this.title = "修改钱柜余额对账通知";
                });
            },
            /** 提交按钮 */
            submitForm() {
                this.$refs["form"].validate(valid => {
                    if (valid) {
                        if (this.form.id != null) {
                            updateBanlance(this.form).then(response => {
                                this.msgSuccess("修改成功");
                                this.open = false;
                                this.getList();
                            });
                        } else {
                            addBanlance(this.form).then(response => {
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
                this.$confirm('是否确认删除钱柜余额对账通知编号为"' + ids + '"的数据项?', "警告", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning"
                }).then(function () {
                    return delBanlance(ids);
                }).then(() => {
                    this.getList();
                    this.msgSuccess("删除成功");
                })
            },
            /** 导出按钮操作 */
            handleExport() {
                this.download('system/banlance/export', {
                    ...this.queryParams
                }, `system_banlance.xlsx`)
            }
        }
    };
</script>
