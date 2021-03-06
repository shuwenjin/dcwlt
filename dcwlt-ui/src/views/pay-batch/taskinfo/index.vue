<template>
    <div class="app-container">
        <el-form
            :model="queryParams"
            ref="queryForm"
            :inline="true"
            v-show="showSearch"
            label-width="68px"
        >
            <el-form-item label="分组代码" prop="taskGroupCode">
                <el-input
                    v-model="queryParams.taskGroupCode"
                    placeholder="任务分组代码"
                    clearable
                    size="small"
                    style="width: 160px"
                    @keyup.enter.native="handleQuery"
                />
            </el-form-item>
            <el-form-item label="业务代码" prop="busiCode">
                <el-input
                    v-model="queryParams.busiCode"
                    placeholder="业务代码"
                    clearable
                    size="small"
                    style="width: 160px"
                    @keyup.enter.native="handleQuery"
                />
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
                    v-hasPermi="['task:taskgroupinfo:add']"
                >新增</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button
                    type="danger"
                    plain
                    icon="el-icon-delete"
                    size="mini"
                    :disabled="multiple"
                    @click="handleDelete"
                    v-hasPermi="['task:taskgroupinfo:remove']"
                >删除</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button
                    type="warning"
                    plain
                    icon="el-icon-download"
                    size="mini"
                    @click="handleExport"
                    v-hasPermi="['task:taskgroupinfo:export']"
                >导出</el-button>
            </el-col>
            <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="typeList" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="任务分组代码" align="center" prop="taskGroupCode" />
            <el-table-column label="任务分组名称" align="center" :show-overflow-tooltip="true">
                <template slot-scope="scope">
                    <router-link
                        :to="'/task/taskinfo/infodetail/' + scope.row.taskGroupCode"
                        class="link-type"
                    >
                        <span>{{ scope.row.taskGroupName }}</span>
                    </router-link>
                </template>
            </el-table-column>

            <el-table-column
                label="业务代码"
                align="center"
                prop="busiCode"
                :show-overflow-tooltip="true"
            />

            <el-table-column
                label="业务名称"
                align="center"
                prop="busiCodeName"
                :show-overflow-tooltip="true"
            />

            <el-table-column label="备注" align="center" prop="remark" :show-overflow-tooltip="true" />

            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                <template slot-scope="scope">
                    <el-button
                        size="mini"
                        type="text"
                        icon="el-icon-edit"
                        @click="handleUpdate(scope.row)"
                        v-hasPermi="['task:taskgroupinfo:edit']"
                    >修改</el-button>
                    <el-button
                        size="mini"
                        type="text"
                        icon="el-icon-delete"
                        @click="handleDelete(scope.row)"
                        v-hasPermi="['task:taskgroupinfo:remove']"
                    >删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <pagination
            v-show="total > 0"
            :total="total"
            :page.sync="queryParams.pageNum"
            :limit.sync="queryParams.pageSize"
            @pagination="getList"
        />

        <!-- 添加或修改参数配置对话框 -->
        <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
            <el-form ref="form" :model="form" :rules="rules" label-width="100px">
                <el-form-item label="任务组代码" prop="taskGroupCode">
                    <el-input
                        v-model="form.taskGroupCode"
                        placeholder="请输入任务组代码"
                        :disabled="taskGroupMode"
                    />
                </el-form-item>
                <el-form-item label="任务组名称" prop="taskGroupName">
                    <el-input v-model="form.taskGroupName" placeholder="请输入任务组名称" />
                </el-form-item>
                <el-form-item label="业务代码" prop="busiCode">
                    <el-input v-model="form.busiCode" placeholder="请输入业务代码" />
                </el-form-item>
                <el-form-item label="业务名称" prop="busiCodeName">
                    <el-input v-model="form.busiCodeName" placeholder="请输入业务名称" />
                </el-form-item>
                <el-form-item label="备注" prop="remark">
                    <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"></el-input>
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
import {
    taskGroupInfoList,
    addTaskGroup,
    updateTaskGroup,
    delTaskGroup,
} from "@/api/pay-batch/taskinfo";

export default {
    name: "TaskGroupinfo",
    data() {
        return {
            // 遮罩层
            loading: true,
            // 选中数组
            taskGroupCodes: [],
            // 非单个禁用
            single: true,
            // 非多个禁用
            multiple: true,
            // 显示搜索条件
            showSearch: true,
            // 总条数
            total: 0,
            // 字典表格数据
            typeList: [],
            // 弹出层标题
            title: "",
            // 是否显示弹出层
            open: false,
            // 查询参数
            queryParams: {
                taskGroupCode: undefined,
                busiCode: undefined,
                pageNum: 1,
                pageSize: 10,
            },
            //添加 false，修改 true
            taskGroupMode: false,
            // 表单参数
            form: {},
            // 表单校验
            rules: {
                taskGroupCode: [
                    {
                        required: true,
                        message: "任务组代码不能为空",
                        trigger: "blur",
                    },
                ],
                taskGroupName: [
                    {
                        required: true,
                        message: "任务组名称不能为空",
                        trigger: "blur",
                    },
                ],
                busiCode:[
                  {
                    required:true,
                    message:"业务代码不能空",
                    trigger: "blur"
                  }
                ],
                busiCodeName:[
                  {
                    required:true,
                    message:"业务名称不能空",
                    trigger: "blur"
                  }
                ],
                remark: [
                  {
                    required:false,
                    message:"备注不能空",
                    trigger: "blur"
                  }
                ]
            },
        };
    },
    created() {
        this.getList();
    },
    methods: {
        /** 查询字典类型列表 */
        getList() {
            this.loading = true;
            taskGroupInfoList(this.queryParams).then((response) => {
                this.typeList = response.rows;
                this.total = response.total;
                this.loading = false;
            });
        },

        // 取消按钮
        cancel() {
            this.reset();
            this.open = false;
        },
        // 表单重置
        reset() {   
            this.form={
              taskGroupCode: undefined,
              taskGroupName: undefined,
              busiCode: undefined,
              busiCodeName: undefined,
              remark: undefined
            }         
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
        /** 新增按钮操作 */
        handleAdd() {
            this.reset();
            this.open = true;
            this.taskGroupMode = false;
            this.title = "添加任务信息组";
        },
        // 多选框选中数据
        handleSelectionChange(selection) {
            this.taskGroupCodes = selection.map((item) => item.taskGroupCode);
            this.single = selection.length != 1;
            this.multiple = !selection.length;
        },
        /** 修改按钮操作 */
        handleUpdate(row) {
            this.reset();
            this.form = JSON.parse(JSON.stringify(row));
            this.taskGroupMode = true;
            this.title = "修改任务组信息";
            this.open = true;
        },
        /** 提交按钮 */
        submitForm: function () {
            this.$refs["form"].validate((valid) => {
                if (valid) {
                    if (this.taskGroupMode) {
                        updateTaskGroup(this.form).then((response) => {
                            this.msgSuccess("修改成功");
                            this.open = false;
                            this.getList();
                        });
                    } else {
                        addTaskGroup(this.form).then((response) => {
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
            const taskGroupCodes = row.taskGroupCode || this.taskGroupCodes;
            this.$confirm(
                '是否确认删除任务组编号为"' + taskGroupCodes + '"的数据?',
                "警告",
                {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning",
                }
            )
                .then(function () {
                    return delTaskGroup(taskGroupCodes);
                })
                .then(() => {
                    this.getList();
                    this.msgSuccess("删除成功");
                });
        },
        /** 导出按钮操作 */
        handleExport() {
            this.download('/pay-batch/taskinfo/taskgroupinfo/export', {
                ...this.queryParams
            }, `taskgroupInfo_${new Date().getTime()}.xlsx`)
        },
    },
};
</script>