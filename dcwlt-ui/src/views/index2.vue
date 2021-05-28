<template>
  <div class="app-container">
    <el-card class="box-card">
      <el-row>
        <el-col :span="6">
          城银清状态：
          <el-button class="buttonType" v-if="successButton" type="success" circle>在线</el-button>
          <el-button class="buttonType" v-if="dangerButton" type="danger" circle>离线</el-button>
          <el-button class="buttonType" v-if="warningButton" type="warning" circle>网络异常</el-button>
          <el-button class="buttonType" v-if="HTTP_INIT" type="warning" circle>未知</el-button>
        </el-col>
        <el-col :span="6">
          机构状态：
          <el-button class="buttonType" v-if="ST00" type="danger" circle>设置故障</el-button>
          <el-button class="buttonType" v-if="ST01" type="success" circle>恢复运行</el-button>
          <el-button class="buttonType" v-if="ST02" type="success" circle>已登录</el-button>
          <el-button class="buttonType" v-if="ST03" type="warning" circle>已退出</el-button>
          <el-button class="buttonType" v-if="ST04" type="danger" circle>强制退出</el-button>
          <el-button class="buttonType" v-if="INIT" type="warning" circle>未知</el-button>
        </el-col>
      </el-row>
    </el-card>
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>机构信息</span>
      </div>
      <el-form ref="form" :model="partyInfo" label-width="100px">
        <el-form-item label="机构ID：">{{ partyInfo.partyID }}</el-form-item>
        <el-form-item label="机构状态：">{{ partyInfo.partyStatus }}</el-form-item>
      </el-form>
    </el-card>
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>钱柜信息</span>
      </div>
      <el-table v-loading="loading" :data="partyList">
        <el-table-column label="机构名称" align="center" prop="partyname"/>
        <el-table-column label="机构编码" align="center" prop="partyid"/>
        <el-table-column label="钱柜钱包id" align="center" prop="cashboxid"/>
        <el-table-column label="预警金额" align="center" prop="earlywarningamount"/>
        <el-table-column label="自动入库金额" align="center" prop="automaticstorage"/>
        <el-table-column label="告警自动入库" :formatter="paramStatusFormat" align="center" prop="automaticstuts"/>
      </el-table>
    </el-card>
  </div>
</template>

<script>
  import {listParty} from "@/api/pay-batch/cashparty";
  import {getPartyInfo} from "@/api/pay-batch/party";

  export default {
    name: "Party",
    components: {},
    data() {
      return {
        loading: false,
        successButton: false,
        dangerButton: false,
        warningButton: false,
        HTTP_INIT: true,
        INIT: true,
        ST00: false,
        ST01: false,
        ST02: false,
        ST03: false,
        ST04: false,
        colortype: 1,
        // 参数状态字典
        paramStatusOptions: [],
        // 机构表格数据
        partyList: [],
        // 业务权限变更信息表格数据
        partyauthList: [],
        // 当前用户信息
        sysprofileList: [],
        //本机构基本信息
        partyInfo: [],

        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          partyID: null,
          partyType: "",
          partyName: null,
          partyAlias: null,
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {}
      };
    },
    created() {
      this.getList();
      this.getPartyInfo();
      this.getDicts("automatic_stuts").then(response => {
        this.paramStatusOptions = response.data;
      });
    },
    methods: {
      /**获取按钮信息 */
      getchckReq(opType) {
        chckReq(opType).then(response => {
          if (opType == null) {
            this.colortype = response.code;
            if (this.colortype == 200) {
              this.warningButton = false;
              this.successButton = true;
            } else if (this.colortype == 110) {
              this.warningButton = false;
              this.dangerButton = true;
            }
          }
        });
      },

      /** 查询钱柜 */
      getList() {
        this.loading = true;
        listParty(this.queryParams).then(response => {
          this.partyList = response.rows;
          this.loading = false;
        });
      },
      /** 查询本机构信息 */
      getPartyInfo() {
        this.loading = true;
        getPartyInfo().then(response => {
          this.partyInfo = response.data;
          this.loading = false;
          ST00 = false;
          ST01 = false;
          ST02 = false;
          ST03 = false;
          ST04 = false;
          INIT = false;
          if (this.partyInfo == null) {
            INIT = true;
          } else {
            switch (this.partyInfo.partyStatus) {
              case "ST00":
                ST00 = true;
                break;
              case "ST01":
                ST01 = true;
                break;
              case "ST02":
                ST02 = true;
                break;
              case "ST03":
                ST03 = true;
                break;
              case "ST04":
                ST04 = true;
                break;
              default:
                INIT = true;
            }
          }
        });
      },
      // 参数状态字典翻译
      paramStatusFormat(row, column) {
        return this.selectDictLabel(this.paramStatusOptions, row.automaticstuts);
      }
    }
  };
</script>
<style>
  .buttonType {
    width: 100px;
    height: 100px;
    font-size: 18px;
  }

  .demo-table-expand label {
    width: 90px;
    color: #99a9bf;
  }

  .demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 50%;
  }
</style>
