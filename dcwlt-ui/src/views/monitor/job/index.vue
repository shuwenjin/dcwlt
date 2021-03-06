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
        <el-select style="width: 240px" v-model="queryParams.jobGroup" placeholder="请选择任务组名" clearable size="small">
          <el-option
            v-for="dict in jobGroupOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="任务状态" prop="status">
        <el-select style="width: 240px" v-model="queryParams.status" placeholder="请选择任务状态" clearable size="small">
          <el-option
            v-for="dict in statusOptions"
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
          v-hasPermi="['monitor:job:add']"
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
          v-hasPermi="['monitor:job:edit']"
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
          @click="handleJobLog"
          v-hasPermi="['monitor:job:query']"
        >日志</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-caret-right"
          size="mini"
          @click="handleManualRun"
          v-hasPermi="['monitor:job:changeStatus']"
        >手动执行方法</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="jobList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="任务编号" align="center" prop="jobId" v-if="columns[0].visible" />
      <el-table-column label="任务名称" align="center" prop="jobName" v-if="columns[1].visible" :show-overflow-tooltip="true" />
      <el-table-column label="任务组名" align="center" prop="jobGroup" v-if="columns[2].visible" :formatter="jobGroupFormat" />
      <el-table-column label="调用目标字符串" align="center" prop="invokeTarget" v-if="columns[3].visible" :show-overflow-tooltip="true" />
      <el-table-column label="cron执行表达式" align="center" prop="cronExpression" v-if="columns[4].visible" :show-overflow-tooltip="true" />
      <el-table-column label="状态" v-if="columns[5].visible" align="center">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.status"
            active-value="0"
            inactive-value="1"
            @change="handleStatusChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="失败重试cron" align="center" prop="retryCron" v-if="columns[6].visible" />
      <el-table-column label="失败重试状态" align="center" v-if="columns[7].visible">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.retryStatus"
            active-value="0"
            inactive-value="1"
            @change="handleRetryStatusChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="重试最大次数" align="center" prop="retryMaxNum" v-if="columns[8].visible" />
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

    <!-- 添加或修改定时任务对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="任务名称" prop="jobName">
              <el-input v-model.trim="form.jobName" placeholder="请输入任务名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="任务分组" prop="jobGroup">
              <el-select v-model="form.jobGroup" placeholder="请选择">
                <el-option
                  v-for="dict in jobGroupOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item prop="invokeTarget">
              <span slot="label">
                调用方法
                <el-tooltip placement="top">
                  <div slot="content">
                    Bean调用示例：ryTask.ryParams('ry')
                    <br />Class类调用示例：com.dcits.dcwlt.quartz.task.RyTask.ryParams('ry')
                    <br />参数说明：支持字符串，布尔类型，长整型，浮点型，整型
                  </div>
                  <i class="el-icon-question"></i>
                </el-tooltip>
              </span>
              <el-input v-model.trim="form.invokeTarget" placeholder="请输入调用目标字符串" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="cron表达式" prop="cronExpression">
              <el-input v-model.trim="form.cronExpression" placeholder="请输入cron执行表达式" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否并发" prop="concurrent">
              <el-radio-group v-model="form.concurrent" size="small">
                <el-radio-button label="0">允许</el-radio-button>
                <el-radio-button label="1">禁止</el-radio-button>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="错误策略" prop="misfirePolicy">
              <el-radio-group v-model="form.misfirePolicy" size="small">
                <el-radio-button label="1">立即执行</el-radio-button>
                <el-radio-button label="2">执行一次</el-radio-button>
                <el-radio-button label="3">放弃执行</el-radio-button>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in statusOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{dict.dictLabel}}</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="失败重试cron" prop="retryCron">
              <el-input v-model.trim="form.retryCron" placeholder="请输入失败重试cron" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="失败重试状态">
              <el-radio-group v-model="form.retryStatus">
                <el-radio
                  v-for="dict in retryStatusOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{dict.dictLabel}}</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="重试最大次数" prop="retryMaxNum">
              <el-input-number v-model="form.retryMaxNum" controls-position="right" :min="1" :max="100" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-link :style="{marginRight: '150px'}" type="primary" href="https://cron.qqe2.com" target="_blank">在线生成cron表达式</el-link>
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>



    <!-- 任务日志详细 -->
    <el-dialog title="任务详细" :visible.sync="openView" width="700px" append-to-body>
      <el-form ref="form" :model="form" label-width="120px" size="mini">
        <el-row>
          <el-col :span="24">
            <el-form-item label="任务编号：">{{ form.jobId }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="任务名称：">{{ form.jobName }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="任务分组：">{{ jobGroupFormat(form) }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="调用目标方法：">{{ form.invokeTarget }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="创建时间：">{{ form.createTime }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="下次执行时间：">{{ parseTime(form.nextValidTime) }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="cron表达式：">{{ form.cronExpression }}</el-form-item>
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
            <el-form-item label="任务状态：">
              <div v-if="form.status == 0">正常</div>
              <div v-else-if="form.status == 1">暂停</div>
            </el-form-item>
            <el-form-item label="失败重试cron：">{{ form.retryCron }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="失败重试状态：">
              <div v-if="form.retryStatus == 0">正常</div>
              <div v-else-if="form.retryStatus == 1">暂停</div>
            </el-form-item>
            <el-form-item label="重试最大次数：">{{ form.retryMaxNum }}</el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-link :style="{marginRight: '200px'}" type="primary" href="https://cron.qqe2.com" target="_blank">在线生成cron表达式</el-link>
        <el-button @click="openView = false">关 闭</el-button>
      </div>
    </el-dialog>

    <!-- 手动执行方法 -->
    <el-dialog 
      title="手动执行方法" 
      :visible.sync="manualRunOpen" 
      @close="manualRunCancel" 
      width="700px" 
      append-to-body
      :close-on-click-modal="false"
    >
      <el-form ref="manualRunForm" :model="manualRunForm" :rules="manualRunRules" label-width="120px">
        <el-row>
          <el-col :span="24">
            <el-form-item prop="invokeTarget">
              <span slot="label">
                调用方法
                <el-tooltip placement="top">
                  <div slot="content">
                    Bean调用示例：ryTask.ryParams('ry')
                    <br />Class类调用示例：com.dcits.dcwlt.quartz.task.RyTask.ryParams('ry')
                    <br />参数说明：支持字符串，布尔类型，长整型，浮点型，整型
                  </div>
                  <i class="el-icon-question"></i>
                </el-tooltip>
              </span>
              <el-input v-model.trim="manualRunForm.invokeTarget" placeholder="请输入调用目标字符串" />
            </el-form-item>
          </el-col>
					<el-col :span="24">
            <el-form-item label="调用方法：" v-if="this.taskResult && (JSON.stringify(this.taskResult) != '{}')">{{ this.taskResult && this.taskResult.invokeTarget }}</el-form-item>
            <el-form-item label="执行状态：" v-if="this.taskResult && (JSON.stringify(this.taskResult) != '{}')">{{ (this.taskResult && this.taskResult.success) ? '成功' : '失败' }}</el-form-item>
            <el-form-item label="执行返回值：" v-if="this.taskResult && (JSON.stringify(this.taskResult) != '{}')" >{{ this.taskResult && this.taskResult.ret }}</el-form-item>
            <el-form-item label="异常信息：" v-if="this.taskResult && this.taskResult.success === false"  >{{ this.taskResult && this.taskResult.message }}</el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="manualRunSubmit" :loading="excuteLoading">执 行</el-button>
        <el-button @click="manualRunCancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listJob, getJob, delJob, addJob, updateJob, runJob, changeJobStatus, changeRetryJobStatus, manualRun } from "@/api/monitor/job";

export default {
  name: "Job",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 执行按钮遮罩层
      excuteLoading: false,
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
      // 状态字典
      statusOptions: [],
      // 失败重试状态字典
      retryStatusOptions: [],
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
        { key: 1, label: `任务名称`, visible: true },
        { key: 2, label: `任务组名`, visible: true },
        { key: 3, label: `调用目标字符串`, visible: true },
        { key: 4, label: `cron执行表达式`, visible: true },
        { key: 5, label: `状态`, visible: true },
        { key: 6, label: `失败重试cron`, visible: true },
        { key: 7, label: `失败重试状态`, visible: true },
        { key: 8, label: `重试最大次数`, visible: true },
        { key: 9, label: `创建时间`, visible: true },
      ],
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        jobName: [
          { required: true, message: "任务名称不能为空", trigger: "blur" }
        ],
        invokeTarget: [
          { required: true, message: "调用目标字符串不能为空", trigger: "blur" }
        ],
        cronExpression: [
          { required: true, message: "cron执行表达式不能为空", trigger: "blur" }
        ],
        retryCron: [
          { required: true, message: "失败重试cron不能为空", trigger: "blur" }
        ],
        retryMaxNum: [
          { required: true, message: "重试最大次数不能为空", trigger: "blur" }
        ],
      },
      // 是否显示手动执行方法弹出层
      manualRunOpen: false,
      // 手动执行方法表单
      manualRunForm: {},
      // 手动执行方法表单校验
      manualRunRules: {
        invokeTarget: [
          { required: true, message: "调用目标字符串不能为空", trigger: "blur" }
        ],
      },
      // 手动执行方法返回值
      taskResult: {},
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_job_group").then(response => {
      this.jobGroupOptions = response.data;
    });
    this.getDicts("sys_job_status").then(response => {
      this.statusOptions = response.data;
      this.retryStatusOptions = response.data;
    });
  },
  methods: {
    /** 查询定时任务列表 */
    getList() {
      this.loading = true;
      listJob(this.queryParams).then(response => {
        this.jobList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 任务组名字典翻译
    jobGroupFormat(row, column) {
      return this.selectDictLabel(this.jobGroupOptions, row.jobGroup);
    },
    // 状态字典翻译
    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
    },
    // 失败重试状态字典翻译
    retryStatusFormat(row, column) {
      return this.selectDictLabel(this.retryStatusOptions, row.retryStatus);
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
        misfirePolicy: 3,
        concurrent: 1,
        status: "0",
        retryCron: undefined,
        retryStatus: "0",
        retryMaxNum: 1,
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
      this.ids = selection.map(item => item.jobId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    // 任务状态修改
    handleStatusChange(row) {
      let text = row.status === "0" ? "启用" : "停用";
      this.$confirm('确认要"' + text + '""' + row.jobName + '"任务吗?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return changeJobStatus(row.jobId, row.status);
        }).then(() => {
          this.msgSuccess(text + "成功");
        }).catch(function() {
          row.status = row.status === "0" ? "1" : "0";
        });
    },
    // 失败重试状态修改
    handleRetryStatusChange(row) {
      let text = row.retryStatus === "0" ? "启用" : "停用";
      this.$confirm('确认要"' + text + '""' + row.jobName + '"失败重试功能吗?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return changeRetryJobStatus(row.jobId, row.retryStatus);
        }).then(() => {
          this.msgSuccess(text + "成功");
        }).catch(function() {
          row.retryStatus = row.retryStatus === "0" ? "1" : "0";
        });
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
    handleJobLog() {
      this.$router.push({name: 'JobLog', params: {jobIds: this.ids}});
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加任务";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const jobId = row.jobId || this.ids;
      getJob(jobId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改任务";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.jobId != undefined) {
            updateJob(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addJob(this.form).then(response => {
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
      this.download('schedule/job/export', {
        ...this.queryParams
      }, `job_${new Date().getTime()}.xlsx`)
    },
    /** 打开手动执行方法弹窗 */
    handleManualRun() {
      this.manualRunOpen = true;
      this.excuteLoading = false;
    },
    /** 关闭手动执行方法弹窗 */
    manualRunCancel() {
      this.manualRunOpen = false;
      this.manualRunForm = {};
      this.taskResult = {};
      this.excuteLoading = false;
    },
    /** 手动执行方法 */
    manualRunSubmit() {
      this.excuteLoading = true;
      this.$refs["manualRunForm"].validate(valid => {
        if (valid) {
          manualRun(this.manualRunForm.invokeTarget).then(response => {
            this.taskResult = response;
            this.excuteLoading = false;
          }, error => {
            console.error(error);
            this.excuteLoading = false;
          });
        }
      });
    }
  }
};
</script>
