<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="任务编号" prop="jobId">
        <el-input
          v-model="queryParams.jobId"
          placeholder="请输入任务编号"
          clearable
          size="small"
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="父实例Id" prop="fid">
        <el-input
          v-model="queryParams.fid"
          placeholder="请输入父实例编号"
          clearable
          size="small"
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="父任务Id" prop="fjobId">
        <el-input
          v-model="queryParams.fjobId"
          placeholder="请输入父任务编号"
          clearable
          size="small"
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="任务名称" prop="jobName">
        <el-input
          v-model="queryParams.jobName"
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
          placeholder="请选择任务组名" 
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
      <el-form-item label="重试状态" prop="retryStatus">
        <el-select 
          v-model="queryParams.retryStatus" 
          placeholder="请选择重试状态" 
          clearable 
          size="small"
          style="width: 240px"
        >
          <el-option
            v-for="dict in retryStatusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="成功状态" prop="retryJobStatus">
        <el-select 
          v-model="queryParams.retryJobStatus" 
          placeholder="请选择重试成功状态" 
          clearable 
          size="small"
          style="width: 240px"
        >
          <el-option
            v-for="dict in retryJobStatusOptions"
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
          @click="handleRetryJobLog"
          v-hasPermi="['monitor:job:query']"
        >日志</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="jobList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="任务编号" align="center" prop="jobId" v-if="columns[0].visible" />
      <el-table-column label="父实例编号" align="center" prop="fid" v-if="columns[1].visible" />
      <el-table-column label="父任务编号" align="center" prop="fjobId" v-if="columns[2].visible" />
      <el-table-column label="任务名称" align="center" prop="jobName" v-if="columns[3].visible" :show-overflow-tooltip="true" />
      <el-table-column label="任务组名" align="center" prop="jobGroup" v-if="columns[4].visible" :formatter="jobGroupFormat" />
      <el-table-column label="调用目标字符串" align="center" prop="invokeTarget" v-if="columns[5].visible" :show-overflow-tooltip="true" />
      <el-table-column label="失败时间" align="center" prop="failTime" v-if="columns[6].visible" >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.failTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="失败重试cron" align="center" prop="retryCron" v-if="columns[7].visible" />
      <el-table-column label="失败重试状态" align="center" v-if="columns[8].visible" :formatter="retryStatusFormat"/>
      <el-table-column label="重试成功状态" align="center" v-if="columns[9].visible" :formatter="retryJobStatusFormat"/>
      <el-table-column label="重试次数" align="center" prop="retryNum" v-if="columns[10].visible" />
      <el-table-column label="重试最大次数" align="center" prop="retryMaxNum" v-if="columns[11].visible" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-caret-right"
            @click="handleRun(scope.row)"
            v-hasPermi="['monitor:job:changeStatus']"
          >执行一次</el-button>
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

    <!-- 任务日志详细 -->
    <el-dialog title="任务详细" :visible.sync="openView" width="700px" append-to-body>
      <el-form ref="form" :model="form" label-width="120px" size="mini">
        <el-row>
          <el-col :span="24">
            <el-form-item label="任务编号：">{{ form.jobId }}</el-form-item>
            <el-form-item label="父实例编号：">{{ form.fid }}</el-form-item>
            <el-form-item label="父任务编号：">{{ form.fjobId }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="任务名称：">{{ form.jobName }}</el-form-item>
            <el-form-item label="任务分组：">{{ jobGroupFormat(form) }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="失败时间：">{{ parseTime(form.failTime) }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="失败重试cron：">{{ form.retryCron }}</el-form-item>
            <el-form-item label="下次执行时间：">{{ parseTime(form.nextRetryValidTime) }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="调用目标方法：">{{ form.invokeTarget }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否并发：">
              <div v-if="form.concurrent == 0">允许</div>
              <div v-else-if="form.concurrent == 1">禁止</div>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="执行策略：">
              <div v-if="form.misfirePolicy == 0">默认策略</div>
              <div v-else-if="form.misfirePolicy == 1">立即执行</div>
              <div v-else-if="form.misfirePolicy == 2">执行一次</div>
              <div v-else-if="form.misfirePolicy == 3">放弃执行</div>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="失败重试状态：">{{ retryStatusFormat(form) }}</el-form-item>
            <el-form-item label="重试成功状态：">{{ retryJobStatusFormat(form) }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="重试次数：">{{ form.retryNum }}</el-form-item>
            <el-form-item label="重试最大次数：">{{ form.retryMaxNum }}</el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="openView = false">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listJob, getJob, delJob, addJob, updateJob, runJob, changeJobStatus, changeRetryJobStatus } from "@/api/monitor/job";

export default {
  name: "Job",
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
      // 定时任务表格数据
      jobList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示详细弹出层
      openView: false,
      // 任务组名字典
      jobGroupOptions: [],
      // 失败重试状态字典
      retryStatusOptions: [],
      // 重试成功状态字典
      retryJobStatusOptions: [],
      // 失败时间范围
      failDateRange: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        jobName: undefined,
        jobGroup: undefined,
        status: undefined,
      },
      // 列信息
      columns: [
        { key: 0, label: `任务编号`, visible: true },
        { key: 1, label: `父实例编号`, visible: true },
        { key: 2, label: `父任务编号`, visible: true },
        { key: 3, label: `任务名称`, visible: true },
        { key: 4, label: `任务组名`, visible: true },
        { key: 5, label: `调用目标字符串`, visible: true },
        { key: 6, label: `失败时间`, visible: true },
        { key: 7, label: `失败重试cron`, visible: true },
        { key: 8, label: `失败重试状态`, visible: true },
        { key: 9, label: `重试成功状态`, visible: true },
        { key: 10, label: `重试次数`, visible: true },
        { key: 11, label: `重试最大次数`, visible: true },
      ],
      // 表单参数
      form: {},
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_job_group").then(response => {
      this.jobGroupOptions = response.data;
    });
    this.getDicts("sys_job_status").then(response => {
      this.retryStatusOptions = response.data;
    });
    this.getDicts("sys_excute_status").then(response => {
      this.retryJobStatusOptions = response.data;
    });
  },
  methods: {
    /** 查询定时任务列表 */
    getList() {
      this.loading = true;
      let search = this.addDateRange(this.queryParams, this.failDateRange, 'FailTime');
      let fids = this.$route.params && this.$route.params.fids;
      if (fids instanceof Array && fids.length > 0) {
        search.params.fids = fids;
      }
      listJob(search, true).then(response => {
        this.jobList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 任务组名字典翻译
    jobGroupFormat(row, column) {
      return this.selectDictLabel(this.jobGroupOptions, row.jobGroup);
    },
    // 失败重试状态字典翻译
    retryStatusFormat(row, column) {
      return this.selectDictLabel(this.retryStatusOptions, row.retryStatus);
    },
    // 失败重试状态字典翻译
    retryJobStatusFormat(row, column) {
      return this.selectDictLabel(this.retryJobStatusOptions, row.retryJobStatus);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        jobId: undefined,
        jobName: undefined,
        jobGroup: undefined,
        invokeTarget: undefined,
        cronExpression: undefined,
        misfirePolicy: 1,
        concurrent: 1,
        status: "0",
        retryCron: undefined,
        retryStatus: "0",
        retryMaxNum: 1,
      };
      this.failDateRange = [];
      this.resetForm("form");
      this.handleQuery();
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
      this.ids = selection.map(item => item.jobId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    
    /* 立即执行一次 */
    handleRun(row) {
      this.$confirm('确认要立即执行一次"' + row.jobName + '"任务吗?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return runJob(row.jobId, row.jobGroup);
        }).then(() => {
          this.msgSuccess("执行成功");
        })
    },
    /** 任务详细信息 */
    handleView(row) {
      getJob(row.jobId).then(response => {
        this.form = response.data;
        this.openView = true;
      });
    },
    /** 任务日志列表查询 */
    handleRetryJobLog() {
      this.$router.push({name: 'RetryLog', params: {jobIds: this.ids}});
    },

    /** 删除按钮操作 */
    handleDelete(row) {
      const jobIds = row.jobId || this.ids;
      this.$confirm('是否确认删除定时任务编号为"' + jobIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delJob(jobIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('schedule/job/retryExport', {
        ...this.queryParams
      }, `retryJob_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
