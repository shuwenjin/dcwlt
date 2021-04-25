<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="88px">
      <el-form-item label="报文标识号" prop="msgId">
        <el-input v-model="queryParams.msgId" placeholder="请输入报文标识号" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="平台日期" prop="payDate">
        <el-date-picker clearable size="small" v-model="queryParams.payDate" type="date" value-format="yyyyMMdd"
          placeholder="选择平台日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="平台流水" prop="paySerNo">
        <el-input v-model="queryParams.paySerNo" placeholder="请输入平台流水" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <!--<el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['batch:nonf:add']"
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
          v-hasPermi="['batch:nonf:edit']"
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
          v-hasPermi="['batch:nonf:remove']"
        >删除</el-button>
      </el-col>-->


      <el-col :span="1.5">

        <!--    <el-button
            type="danger"
            plain
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['batch:nonf:remove']"
          >删除</el-button>
        </el-col> -->

        <el-button type="warning" plain icon="el-icon-edit" size="mini" @click="handleLogin"
          v-hasPermi="['batch:nonf:loginin']">登入</el-button>

        <el-button type="warning" plain icon="el-icon-edit" size="mini" @click="handleLoginout"
          v-hasPermi="['batch:nonf:loginout']">登出</el-button>


        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['batch:nonf:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="nonfList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="报文标识号" align="center" prop="msgId" v-if="columns[0].visible" show-overflow-tooltip/>
      <el-table-column label="平台日期" align="center" prop="payDate" width="180" v-if="columns[1].visible">
        <!-- <template slot-scope="scope">
          <span>{{ parseTime(scope.row.payDate, '{y}-{m}-{d}') }}</span>
        </template> -->
      </el-table-column>
      <el-table-column label="平台时间" align="center" prop="payTime" v-if="columns[2].visible" />
      <el-table-column label="平台流水" align="center" prop="paySerNo" v-if="columns[3].visible" />
      <el-table-column label="报文编号" align="center" prop="pkgNo" v-if="columns[4].visible" />
      <el-table-column label="报文方向" align="center" prop="drct" :formatter="drctFormat" v-if="columns[5].visible" />
      <el-table-column label="交易状态" align="center" prop="tradeStatus" v-if="columns[6].visible" />
      <el-table-column label="报文发送时间" align="center" prop="senderDateTime" v-if="columns[7].visible" />
      <el-table-column label="发起机构" align="center" prop="instgDrctPty" v-if="columns[8].visible" />
      <el-table-column label="接收机构" align="center" prop="instddrctpty" v-if="columns[9].visible" />
      <el-table-column label="操作类型" align="center" prop="opterationType" :formatter="opterationTypeFormat"
        v-if="columns[10].visible" />
      <el-table-column label="业务处理状态" align="center" prop="procStatus" :formatter="procStatusFormat"
        v-if="columns[11].visible" />
      <el-table-column label="业务拒绝码" align="center" prop="rejectCode" v-if="columns[12].visible" />
      <el-table-column label="业务拒绝信息" align="center" prop="rejectInfo" v-if="columns[13].visible" />
      <el-table-column label="柜员号" align="center" prop="tlrNo" v-if="columns[14].visible" />
      <el-table-column label="备注" align="center" prop="remark" v-if="columns[15].visible" />
      <el-table-column label="信息内容" align="center" prop="messageContext" v-if="columns[16].visible" />
      <el-table-column label="最后更新日期" align="center" prop="lastUpDate" v-if="columns[17].visible" />
      <el-table-column label="最后更新时间" align="center" prop="lastUpTime" v-if="columns[18].visible" />
      <!--   <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['batch:nonf:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['batch:nonf:remove']"
          >删除</el-button>
        </template>
      </el-table-column>-->
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改非金融登记簿对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
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
    listNonf,
    getNonf,
    delNonf,
    addNonf,
    updateNonf,
    handerLogin,
    loginoutFmtMsgSnd,
    handerLoginout
  } from "@/api/pay-batch/loginlogout";

  export default {
    name: "Nonf",
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
        // 显示搜索条件
        showSearch: true,
        // 总条数
        total: 0,
        // 非金融登记簿表格数据
        nonfList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 报文方向字典
        drctOptions: [],

        msgbizstatusOptions: [],

        loginOptions: [],

        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          msgId: null,
          payDate: null,
          paySerNo: null,
        },
        // 表单参数
        form: {},
        // 列信息
        columns: [{
            key: 0,
            label: `报文标识号`,
            visible: true
          },
          {
            key: 1,
            label: `平台日期`,
            visible: true
          },
          {
            key: 2,
            label: `平台时间`,
            visible: true
          },
          {
            key: 3,
            label: `平台流水`,
            visible: true
          },
          {
            key: 4,
            label: `报文编号`,
            visible: true
          },
          {
            key: 5,
            label: `报文方向`,
            visible: true
          },
          {
            key: 6,
            label: `交易状态`,
            visible: true
          },
          {
            key: 7,
            label: `报文发送时间`,
            visible: true
          },
          {
            key: 8,
            label: `发起机构`,
            visible: true
          },
          {
            key: 9,
            label: `接收机构`,
            visible: true
          },
          {
            key: 10,
            label: `操作类型`,
            visible: true
          },
          {
            key: 11,
            label: `业务处理状态`,
            visible: true
          },
          {
            key: 12,
            label: `业务拒绝码`,
            visible: true
          },
          {
            key: 13,
            label: `业务拒绝信息`,
            visible: true
          },
          {
            key: 14,
            label: `柜员号`,
            visible: true
          },
          {
            key: 15,
            label: `备注`,
            visible: true
          },
          {
            key: 16,
            label: `信息内容`,
            visible: true
          },
          {
            key: 17,
            label: `最后更新日期`,
            visible: true
          },
          {
            key: 18,
            label: `最后更新时间`,
            visible: true
          }
        ],
        // 表单校验
        rules: {
          payDate: [{
            required: true,
            message: "平台日期不能为空",
            trigger: "blur"
          }],
        }
      };
    },
    created() {
      this.getList();
      this.getDicts("drct").then(response => {
        this.drctOptions = response.data;
      });
      this.getDicts("OT0").then(response => {
        this.opterationTypeOptions = response.data;
        console.info(this.opterationTypeOptions);
      });

      this.getDicts("PR").then(response => {
        this.procStatusOptions = response.data;
        console.info(this.procStatusOptions);
      });
    },
    methods: {
      /** 查询非金融登记簿列表 */
      getList() {
        this.loading = true;
        listNonf(this.queryParams).then(response => {
          this.nonfList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      // 报文方向字典翻译
      drctFormat(row, column) {
        return this.selectDictLabel(this.drctOptions, row.drct);
      },

      // 参数类型字典翻译
      opterationTypeFormat(row, column) {

        return this.selectDictLabel(this.opterationTypeOptions, row.opterationType);
      },
      // 参数状态字典翻译
      procStatusFormat(row, column) {

        return this.selectDictLabel(this.procStatusOptions, row.procStatus);
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
      // 表单重置
      reset() {
        this.form = {
          msgId: null,
          payDate: null,
          payTime: null,
          paySerNo: null,
          pkgNo: null,
          drct: null,
          tradeStatus: "0",
          senderDateTime: null,
          instgDrctPty: null,
          instddrctpty: null,
          opterationType: null,
          procStatus: "0",
          rejectCode: null,
          rejectInfo: null,
          tlrNo: null,
          remark: null,
          messageContext: null,
          lastUpDate: null,
          lastUpTime: null
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
        this.ids = selection.map(item => item.msgId)
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "添加非金融登记簿";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const msgId = row.msgId || this.ids
        getNonf(msgId).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改非金融登记簿";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.msgId != null) {
              updateNonf(this.form).then(response => {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addNonf(this.form).then(response => {
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
        const msgIds = row.msgId || this.ids;
        this.$confirm('是否确认删除非金融登记簿编号为"' + msgIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delNonf(msgIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
      },


      /**
       *  "head": {
       *     "tranDate": "20210317",
       *     "tranTime": "103524",
       *     "seqNo": "20210113000122532910308590900000"
       *   }
       */


      /** 登入按钮操作 */
      handleLogin() {

        const trlNo = "11"; //row.msgId || this.ids;
        let opterationType = "OT00";
        this.$confirm('是否登入', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          //登录状态
          //debugger

          //var requestData=this.body(opterationType);
          // console.info("requestData===>"+requestData)
          return loginoutFmtMsgSnd(trlNo, opterationType);
        }).then(() => {
          this.msgSuccess("登入成功");
        })
      },

      /** 登出按钮操作 */
      handleLoginout() {

        var trlNo = "11";
        //退出状态
        let opterationType = "OT01";

        this.$confirm('是否登出', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          //登出状态
          let opterationType = "OT01";

          return loginoutFmtMsgSnd(trlNo, opterationType);
        }).then(() => {
          this.msgSuccess("登出成功");
        })
      },
      /** 导出按钮操作 */
      handleExport() {
        this.download('batch/nonf/export', {
          ...this.queryParams
        }, `batch_nonf.xlsx`)
      }
    }
  };
</script>
