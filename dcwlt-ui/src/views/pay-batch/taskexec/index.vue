<template>
    <div class="app-container">
        <el-form
            :model="queryParams"
            ref="queryForm"
            :inline="true"
            v-show="showSearch"
            label-width="68px">
             <el-form-item label="清算日期">
                 <el-date-picker
                    v-model="queryParams.settleDate"
                    type="date"
                    placeholder="选择日期"
                    value-format="yyyyMMdd"
                    >
                </el-date-picker>
            </el-form-item>
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
                    type="warning"
                    plain
                    icon="el-icon-download"
                    size="mini"
                    @click="handleExport"
                    v-hasPermi="['task:taskgroupexec:export']"
                >导出</el-button>
            </el-col>
            <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="typeList" @selection-change="handleSelectionChange">
            <el-table-column label="序号" type="index" align="center">
                <template slot-scope="scope">
                    <span>{{ (queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1 }}</span>
                </template>
            </el-table-column>
            <el-table-column label="交易批次号" align="center" prop="batchId" >
                 <template slot-scope="scope">
                    <router-link
                        :to="'/task/taskexec/execdetail/'+ scope.row.batchId +'/' + scope.row.taskGroupCode+'/'+scope.row.settleDate"
                        class="link-type">
                        <span>{{ scope.row.batchId }}</span>
                    </router-link>
                </template>
            </el-table-column>
            <el-table-column label="清算日期" align="center" prop="settleDate" />
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

            <el-table-column label="业务代码" align="center"  prop="busiCode" />
            <el-table-column label="业务名称" align="center" prop="busiCodeName"  />
            <el-table-column label="执行状态" align="center" prop="execState"  />
             <el-table-column label="创建时间" align="center" prop="createTime" />
            <el-table-column label="更新时间" align="center" prop="updateTime" />           
        </el-table>

        <pagination
            v-show="total > 0"
            :total="total"
            :page.sync="queryParams.pageNum"
            :limit.sync="queryParams.pageSize"
            @pagination="getList"
        />
    </div>
</template>

<script>
import {taskGroupExecList} from "@/api/pay-batch/taskexec";

export default {
    name: "TaskGroupExec",
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
                settleDate:undefined,
                taskGroupCode:undefined,
                busiCode:undefined,
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
            taskGroupExecList(this.queryParams).then((response) => {
                this.typeList = response.rows;
                this.total = response.total;
                this.loading = false;
            });
        },
        
        /** 搜索按钮操作 */
        handleQuery() {
            this.queryParams.pageNum = 1;
            this.getList();
        },
        /** 重置按钮操作 */
        resetQuery() {
            this.queryParams.settleDate=undefined;
            this.resetForm("queryForm");
            this.handleQuery();
        },
        // 多选框选中数据
        handleSelectionChange(selection) {
            this.taskGroupCodes = selection.map((item) => item.taskGroupCode);
            this.single = selection.length != 1;
            this.multiple = !selection.length;
        },
        /** 导出按钮操作 */
        handleExport() {
            this.download('/pay-batch/taskexec/taskgroupexec/export', {
                ...this.queryParams
            }, `taskgroupexec_${new Date().getTime()}.xlsx`)
        },
        
    },
};
</script>