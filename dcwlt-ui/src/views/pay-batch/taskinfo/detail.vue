<template>
    <div class="app-container">
        <el-form :model="queryParams" ref="queryForm" v-show="showSearch" :inline="true">
            <el-form-item label="任务编码" prop="taskCode">
                <el-input
                    v-model="queryParams.taskCode"
                    placeholder="任务编码"
                    clearable
                    size="small"
                    @keyup.enter.native="handleQuery"
                />
            </el-form-item>
            <el-form-item label="任务名称" prop="taskName">
                <el-input
                    v-model="queryParams.taskName"
                    placeholder="任务名称"
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
            <el-col :span="1.5">
                <el-button
                    type="primary"
                    plain
                    icon="el-icon-plus"
                    size="mini"
                    @click="handleAdd"
                    v-hasPermi="['task:taskinfo:add']"
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
                    v-hasPermi="['task:taskinfo:remove']"
                >删除</el-button>
            </el-col>
            <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="dataList" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="任务组编码" align="center" prop="taskGroupCode" />
            <el-table-column label="任务编码" align="center" prop="taskCode" />
            <el-table-column label="任务名称" align="center" prop="taskName" width="230px" />
            <el-table-column label="任务类型" align="center" prop="taskType" />
            <el-table-column label="任务类" align="center" prop="taskClassName" width="300px" />
            <el-table-column label="运行参数" align="center" prop="execParam" />
            <el-table-column label="任务状态" align="center" prop="taskState" :formatter="statusFormat" />
            <el-table-column label="顺序" align="center" prop="taskIndex" />

            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                <template slot-scope="scope">
                    <el-button
                        size="mini"
                        type="text"
                        icon="el-icon-edit"
                        @click="handleUpdate(scope.row)"
                        v-hasPermi="['task:taskinfo:edit']"
                    >修改</el-button>
                    <el-button
                        size="mini"
                        type="text"
                        icon="el-icon-delete"
                        @click="handleDelete(scope.row)"
                        v-hasPermi="['task:taskinfo:remove']"
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

        <!-- 添加或修改对话框 -->
        <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
            <el-form ref="form" :model="form" :rules="rules" label-width="90px">
                <el-form-item label="任务组代码">
                    <el-input v-model="form.taskGroupCode" :disabled="true" />
                </el-form-item>
                <el-form-item label="任务代码" prop="taskCode">
                    <el-input v-model="form.taskCode" placeholder="请输入任务代码" :disabled="!isAdd" />
                </el-form-item>
                <el-form-item label="任务名称" prop="taskName">
                    <el-input v-model="form.taskName" placeholder="请输入任务名称" />
                </el-form-item>
                <el-form-item label="任务类型" prop="taskType">
                    <el-input v-model="form.taskType" placeholder="请输入任务类型" />
                </el-form-item>
                <el-form-item label="任务类" prop="taskClassName">
                    <el-input v-model="form.taskClassName" placeholder="请输入任务类" />
                </el-form-item>
                <el-form-item label="运行参数" prop="execParam">
                    <el-input v-model="form.execParam" type="textarea" placeholder="请输入任务运行参数" />
                </el-form-item>
                <el-form-item label="任务状态" prop="taskState">                   
                    <el-radio-group v-model="form.taskState">
                        <el-radio
                            v-for="dict in statusOptions"
                            :key="dict.dictValue"
                            :label="dict.dictValue"
                            >{{dict.dictLabel}}
                        </el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="任务顺序" prop="taskIndex">
                    <el-input-number v-model="form.taskIndex" controls-position="right" :min="0" />
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
import { taskInfoList,addTaskInfo,updateTaskInfo,delTaskInfo } from "@/api/pay-batch/taskinfo";

export default {
    name: "TaskInfoList",
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
            // 字典表格数据
            dataList: [],
            // 默认字典类型
            defaultDictType: "",
            // 弹出层标题
            title: "",
            // 是否显示弹出层
            open: false,
            // 任务状态数据字典
            statusOptions: [],
            // 任务分组类型字典
            taskTypeOptions:[],
            //是否添加
            isAdd: true,
            // 查询参数
            queryParams: {
                pageNum: 1,
                pageSize: 10,
                taskCode:undefined,
                taskName:undefined,
                taskGroupCode: undefined,
            },
            // 表单参数
            form: {},
            // 表单校验
            rules: {
                taskCode: [{
                        required: true,
                        message: "任务代码不能为空",
                        trigger: "blur",
                    },
                ],
                taskName:[
                  {
                    required: true,
                    message:"业务名称不能空",
                    trigger: "blur"
                  }
                ],
                taskClassName:[
                  {
                    required: true,
                    message:"任务类不能空",
                    trigger: "blur"
                  }
                ],
                taskType: [
                  {
                    required: true,
                    message:"任务类型不能空",
                    trigger: "blur"
                  }],
                taskIndex: [
                  {
                    required: true,
                    message:"任务顺序不能空",
                    trigger: "blur"
                  }],
                taskState: [
                  {
                    required: true,
                    message:"任务状态不能空",
                    trigger: "blur"
                  }]
            }
        };
    },
    created() {
        const taskGroupCode =
            this.$route.params && this.$route.params.taskGroupCode;
        this.queryParams.taskGroupCode = taskGroupCode;

        this.getList();
        this.getDicts("sys_job_status").then(response => {
                this.statusOptions = response.data;
            });
        this.getDicts("sys_job_group").then(response => {
                this.taskTypeOptions = response.data;
            });            
    },
    methods: {
        /** 查询任务信息数据列表 */
        getList() {
            this.loading = true;
            taskInfoList(this.queryParams).then((response) => {
                this.dataList = response.rows;
                this.total = response.total;
                this.loading = false;
            });
        },
        // 数据状态字典翻译
        statusFormat(row, column) {
            return this.selectDictLabel(this.statusOptions, row.taskState);
        },
        // 数据状态字典翻译
        taskTypeFormat(row, column) {
            return this.selectDictLabel(this.taskTypeOptions, row.taskState);
        },
        // 取消按钮
        cancel() {
            this.open = false;
            this.reset();
        },
        // 表单重置
        reset() {
            this.form={
                taskCode:undefined,
                taskName:undefined,
                taskGroupCode: undefined,
                taskClassName:undefined,
                taskType:undefined,
                execParam:undefined,
                taskState:"0",
                taskIndex:0
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
            this.isAdd=true;            
            this.title = "添加任务信息";
            this.form.taskGroupCode = this.queryParams.taskGroupCode;
            this.open = true;
        },
        // 多选框选中数据
        handleSelectionChange(selection) {
            this.ids = selection.map((item) => item.taskCode);
            this.single = selection.length != 1;
            this.multiple = !selection.length;
        },
        /** 修改按钮操作 */
        handleUpdate(row) {
            this.reset();           
            this.form=  JSON.parse(JSON.stringify(row));
            this.isAdd=false;
            this.title = "修改任务信息";
            this.open = true;        
        },
        /** 提交按钮 */
        submitForm: function () {
            this.$refs["form"].validate((valid) => {
                if (valid) {
                    if (!this.isAdd) {
                        updateTaskInfo(this.form).then((response) => {
                            this.msgSuccess("修改成功");
                            this.open = false;
                            this.getList();
                        });

                    } else {
                        addTaskInfo(this.form).then((response) => {
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
            const taskCodes = row.taskCode || this.ids;
            this.$confirm(
                '是否确认删除任务编码为"' + taskCodes + '"的数据?',
                "警告",
                {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning",
                }
            )
                .then(function () {
                    return delTaskInfo(taskCodes);
                })
                .then(() => {
                    this.getList();
                    this.msgSuccess("删除成功");
                });
        }
    }
};
</script>