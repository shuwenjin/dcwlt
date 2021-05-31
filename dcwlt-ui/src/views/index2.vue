<template>
  <div class="app-container">
    <el-card class="box-card" align="center">
      <el-row>
        <el-col :span="12">
          <span>城银清状态：</span>
          <el-button class="buttonType" :type="this.http_type" onclick="checkReqHttp()" circle>{{http_stat}}</el-button>
        </el-col>
        <el-col :span="12">
          <span>机构状态：</span>
          <el-button class="buttonType" :type="this.bank_type" onclick="getPartyInfos()" circle>{{bank_stat}}</el-button>
        </el-col>
      </el-row>
    </el-card>
    <br/>
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
  import {getPartyInfo, checkReq} from "@/api/pay-batch/party";

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
        // http探测状态
        http_type: "warning",
        http_stat: "未知",
        //本机构状态
        bank_type: "warning",
        bank_stat: "未知",

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
      this.checkReqHttp();
      this.getPartyInfos();
      this.getDicts("automatic_stuts").then(response => {
        this.paramStatusOptions = response.data;
      });
    },
    methods: {
      /**获取按钮信息 */
      checkReqHttp() {
        checkReq().then(response => {
          this.http_stat = response.msg;
          switch (this.http_stat) {
            case "ONLN":
              this.http_type= "success";
              this.http_stat= "在线";
              break;
            case "OFLN":
              this.http_type= "danger";
              this.http_stat= "离线";
              break;
            case "UNLG":
              this.http_type= "warning";
              this.http_stat= "没有登录";
              break;
            case "AUTH":
              this.http_type= "warning";
              this.http_stat= "无权访问";
              break;
            case "EXCP":
              this.http_type= "danger";
              this.http_stat= "链路不通";
              break;
            default:
              this.http_type= "warning";
              this.http_stat= "未知";
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
      getPartyInfos() {
        this.loading = true;
        getPartyInfo().then(response => {
          this.partyInfo = response.data;
          this.loading = false;
          if (this.partyInfo == null) {
            this.bank_type = "warning";
            this.bank_stat = "未知";
          } else {
            switch (this.partyInfo.partyStatus) {
              case "ST00":
                this.bank_type = "danger";
                this.bank_stat = "设置故障";
                break;
              case "ST01":
                this.bank_type = "success";
                this.bank_stat = "恢复运行";
                break;
              case "ST02":
                this.bank_type = "success";
                this.bank_stat = "已登录";
                break;
              case "ST03":
                this.bank_type = "warning";
                this.bank_stat = "已退出";
                break;
              case "ST04":
                this.bank_type = "danger";
                this.bank_stat = "强制退出";
                break;
              default:
                this.bank_type = "warning";
                this.bank_stat = "未知";
            }
          }
          ST02 = true;
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
