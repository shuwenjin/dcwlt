<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="任务编号" prop="jobId">
        <el-input
          v-model.trim="queryParams.jobId"
          placeholder="请输入任务编号"
          clearable
          size="small"
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="任务名称" prop="jobName">
        <el-input
          v-model.trim="queryParams.jobName"
          placeholder="请输入任务名称"
          clearable
          size="small"
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="任务组名" prop="jobGroup">
        <el-select
          v-model="queryParams.jobGroup"
          placeholder="请任务组名"
          clearable
          size="small"
          style="width: 240px"
        >
          <el-option
            v-for="dict in jobGroupOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="执行状态" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="请选择执行状态"
          clearable
          size="small"
          style="width: 240px"
        >
          <el-option
            v-for="dict in statusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="执行时间">
        <el-date-picker
          v-model="dateRange"
          size="small"
          style="width: 240px"
          value-format="yyyy-MM-dd HH:mm:ss"
          type="datetimerange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="失败时间">
        <el-date-picker
          v-model="failDateRange"
          size="small"
          style="width: 240px"
          value-format="yyyy-MM-dd HH:mm:ss"
          type="datetimerange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['monitor:job:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          @click="handleClean"
          v-hasPermi="['monitor:job:remove']"
        >清空</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['monitor:job:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-s-operation"
          size="mini"
          @click="handleRetryJob"
          v-hasPermi="['monitor:job:query']"
        >重试定时任务</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="jobLogList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="日志编号" align="center" prop="jobLogId" v-if="columns[0].visible" />
      <el-table-column label="任务编号" align="center" prop="jobId" v-if="columns[1].visible" />
      <el-table-column label="任务名称" align="center" prop="jobName" v-if="columns[2].visible" :show-overflow-tooltip="true" />
      <el-table-column label="任务组名" align="center" prop="jobGroup" v-if="columns[3].visible" :formatter="jobGroupFormat" :show-overflow-tooltip="true" />
      <el-table-column label="调用目标字符串" align="center" prop="invokeTarget" v-if="columns[4].visible" :show-overflow-tooltip="true" />
      <el-table-column label="日志信息" align="center" prop="jobMessage" v-if="columns[5].visible" :show-overflow-tooltip="true" />
      <el-table-column label="执行时间" align="center" prop="startTime" v-if="columns[6].visible" >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="执行状态" align="center" prop="status" v-if="columns[7].visible" :formatter="statusFormat" width="80"/>
      <el-table-column label="失败时间" align="center" prop="failTime" v-if="columns[8].visible" >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.failTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" v-if="columns[9].visible" >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleView(scope.row)"
            v-hasPermi="['monitor:job:query']"
          >详细</el-button>
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

    <!-- 调度日志详细 -->
    <el-dialog title="调度日志详细" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" label-width="100px" size="mini">
        <el-row>
          <el-col :span="24">
            <el-form-item label="日志编号：">{{ form.jobLogId }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="任务编号：">{{ form.jobId }}</el-form-item>
            <el-form-item label="任务名称：">{{ form.jobName }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="任务分组：">{{ form.jobGroup }}</el-form-item>
            <el-form-item label="执行时间：">{{ form.startTime }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="调用方法：">{{ form.invokeTarget }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="日志信息：">{{ form.jobMessage }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="执行状态：">
              <div v-if="form.status == 0">正常</div>
              <div v-else-if="form.status == 1">失败</div>
            </el-form-item>
            <el-form-item label="失败时间：">{{ form.failTime }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="异常信息：" v-if="form.status == 1">{{ form.exceptionInfo }}</el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="open = false">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listJobLog, delJobLog, cleanJobLog } from "@/api/monitor/jobLog";

export default {
  name: "JobLog",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 调度日志表格数据
      jobLogList: [],
      // 是否显示弹出层
      open: false,
      // 执行时间范围
      dateRange: [],
      // 执行失败时间
      failDateRange: [],
      // 表单参数
      form: {},
      // 执行状态字典
      statusOptions: [],
      // 任务组名字典
      jobGroupOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        jobName: undefined,
        jobGroup: undefined,
        status: undefined
      },
      // 列信息
      columns: [
        { key: 0, label: `日志编号`, visible: true },
        { key: 1, label: `任务编号`, visible: true },
        { key: 2, label: `任务名称`, visible: true },
        { key: 3, label: `任务组名`, visible: true },
        { key: 4, label: `调用目标字符串`, visible: true },
        { key: 5, label: `日志信息`, visible: true },
        { key: 6, label: `执行时间`, visible: true },
        { key: 7, label: `执行状态`, visible: true },
        { key: 8, label: `失败时间`, visible: true },
        { key: 9, label: `创建时间`, visible: true },
      ],
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_excute_status").then(response => {
      this.statusOptions = response.data;
    });
    this.getDicts("sys_job_group").then(response => {
      this.jobGroupOptions = response.data;
    });
  },
  methods: {
    /** 查询调度日志列表 */
    getList() {
      this.loading = true;
      let search = this.addDateRange(this.queryParams, this.dateRange);
      search = this.addDateRange(search, this.failDateRange, 'FailTime');
      let jobIds = this.$route.params && this.$route.params.jobIds;
      if (jobIds instanceof Array && jobIds.length > 0) {
        search.params.jobIds = jobIds;
      }
      listJobLog(search).then(response => {
          this.jobLogList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    // 执行状态字典翻译
    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
    },
    // 任务组名字典翻译
    jobGroupFormat(row, column) {
      return this.selectDictLabel(this.jobGroupOptions, row.jobGroup);
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      this.failDateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.jobLogId);
      this.multiple = !selection.length;
    },
    /** 详细按钮操作 */
    handleView(row) {
      this.open = true;
      this.form = row;
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const jobLogIds = this.ids;
      this.$confirm('是否确认删除调度日志编号为"' + jobLogIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delJobLog(jobLogIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
    },
    /** 清空按钮操作 */
    handleClean() {
      this.$confirm("是否确认清空所有调度日志数据项?", "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return cleanJobLog();
        }).then(() => {
          this.getList();
          this.msgSuccess("清空成功");
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('schedule/job/log/export', {
        ...this.queryParams
      }, `log_${new Date().getTime()}.xlsx`)
    },
    /** 任务日志列表查询 */
    handleRetryJob() {
      this.$router.push({name: 'RetryJob', params: {fids: this.ids}});
    },
  }
};
</script>
