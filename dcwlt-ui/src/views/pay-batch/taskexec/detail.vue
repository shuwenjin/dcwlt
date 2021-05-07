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
            <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="dataList">
            <el-table-column label="序号" type="index" align="center">
                <template slot-scope="scope">
                    <span>{{ (queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1 }}</span>
                    </template>
            </el-table-column>
            <el-table-column label="交易批次号" align="center" prop="batchId" width="130px" />
            <el-table-column label="清算日期" align="center" prop="settleDate" width="90px" />
            <el-table-column label="任务组代码" align="center" prop="taskGroupCode" width="90px" />
            <el-table-column label="任务组名称" align="center" prop="taskGroupName" width="90px" />
            <el-table-column label="任务编码" align="center" prop="taskCode" />
            <el-table-column label="任务名称" align="center" prop="taskName" />
            <el-table-column label="业务编码" align="center" prop="busiCode" />
            <el-table-column label="业务名称" align="center" prop="busiCodeName"  />
            <el-table-column label="任务类型" align="center" prop="taskType" />
            <el-table-column label="任务类" align="center" prop="taskClassName" width="120px" show-overflow-tooltip />
            <el-table-column label="运行参数" align="center" prop="execParam" />
            <el-table-column label="执行状态" align="center" prop="execState" />
            <el-table-column label="顺序" align="center" prop="taskIndex" />
            <el-table-column label="创建时间" align="center" prop="startTime" />
            <el-table-column label="更新时间" align="center" prop="endTime" />
        </el-table>

        <pagination
            v-show="total>0"
            :total="total"
            :page.sync="queryParams.pageNum"
            :limit.sync="queryParams.pageSize"
            @pagination="getList"
        />        
    </div>
</template>

<script>
import { taskExecList} from "@/api/pay-batch/taskexec";

export default {
    name: "TaskExecList",
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
        const batchId= this.$route.params && this.$route.params.batchId;
        const settleDate= this.$route.params && this.$route.params.settleDate;
        const taskGroupCode = this.$route.params && this.$route.params.taskGroupCode;
        this.queryParams.batchId = batchId;
        this.queryParams.settleDate = settleDate;
        this.queryParams.taskGroupCode = taskGroupCode;


        this.getList();      
    },
    methods: {
        /** 查询任务信息数据列表 */
        getList() {
            this.loading = true;
            taskExecList(this.queryParams).then((response) => {
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