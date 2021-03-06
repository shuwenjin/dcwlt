<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="运营机构" prop="cshboxInstnid">
        <el-select v-model="queryParams.cshboxInstnid" placeholder="请选择钱柜所属运营机构" clearable size="small">
          <el-option
            v-for="dict in cshboxInstnidOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="告警时间">
        <el-date-picker
          v-model="daterangeWrngTm"
          size="small"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
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
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['pay-batch:wrng:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="wrngList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="钱柜ID" align="center" prop="coopbankWltid" v-if="columns[0].visible"/>
      <el-table-column label="运营机构" align="center" prop="cshboxInstnid" :formatter="cshboxInstnidFormat"
                       v-if="columns[1].visible"/>
      <el-table-column label="钱柜余额预警值" align="center" prop="wrngVal" v-if="columns[2].visible"/>
      <el-table-column label="当前钱柜余额" align="center" prop="amtValue" v-if="columns[3].visible"/>
      <el-table-column label="预警内容" align="center" prop="msgCnt" v-if="columns[4].visible"/>
      <el-table-column label="告警时间" align="center" prop="wrngTm" v-if="columns[5].visible"/>
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
  import {listWrng, getWrng, delWrng, addWrng, updateWrng} from "@/api/pay-batch/wrng";
  import {listParty} from "@/api/pay-batch/cashparty";

  export default {
    name: "Wrng",
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
        // 钱柜余额告警表格数据
        wrngList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 钱柜所属运营机构字典
        cshboxInstnidOptions: [],
        // 告警时间时间范围
        daterangeWrngTm: [],
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          cshboxInstnid: null,
          wrngTm: null
        },
        // 列信息
        columns: [
          {key: 0, label: `合作银行钱柜ID`, visible: true},
          {key: 1, label: `钱柜所属运营机构`, visible: true},
          {key: 2, label: `钱柜余额预警值`, visible: true},
          {key: 3, label: `当前钱柜余额`, visible: true},
          {key: 4, label: `预警内容`, visible: true},
          {key: 5, label: `告警时间`, visible: true},
        ],
        // 表单参数
        form: {},
        // 表单校验
        rules: {}
      };
    },
    created() {
      this.getpaty();
      this.getList();
    },
    methods: {
      /** 查询机构列表 */
      getpaty() {
        this.loading = true;
        listParty(this.query).then(response => {
          this.loading = false;
          //转成字典
          let partys = response.rows;
          response.rows.forEach((item, index) => {
            let dict = {
              dictKey: item.partyid,
              dictLabel: item.partyname,
              dictValue: item.partyid
            };
            this.cshboxInstnidOptions.push(dict);
          });
        });
      },
      /** 查询钱柜余额告警列表 */
      getList() {
        this.loading = true;
        this.queryParams.params = {};
        if (null != this.daterangeWrngTm && '' != this.daterangeWrngTm) {
          this.queryParams.params["beginWrngTm"] = this.daterangeWrngTm[0];
          this.queryParams.params["endWrngTm"] = this.daterangeWrngTm[1];
        }
        listWrng(this.queryParams).then(response => {
          this.wrngList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      // 钱柜所属运营机构字典翻译
      cshboxInstnidFormat(row, column) {
        return this.selectDictLabel(this.cshboxInstnidOptions, row.cshboxInstnid);
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
      // 表单重置
      reset() {
        this.form = {
          coopbankInstnid: null,
          coopbankWltid: null,
          cshboxInstnid: null,
          wrngVal: null,
          amtValue: null,
          msgCnt: null,
          wrngTm: null
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
        this.daterangeWrngTm = [];
        this.resetForm("queryForm");
        this.handleQuery();
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.coopbankWltid)
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },

      /** 导出按钮操作 */
      handleExport() {
        this.download('pay-batch/wrng/export', {
          ...this.queryParams
        }, `pay-batch_wrng.xlsx`)
      }
    }
  };
</script>
