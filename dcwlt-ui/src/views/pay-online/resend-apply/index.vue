<template>
    <div class="app-container">
        <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
                <el-button
                    type="primary"
                    plain
                    size="mini"
                    @click="showSend711"
                >机构对账汇总核对重发申请</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button
                    type="primary"
                    plain
                    size="mini"
                    @click="showSend713"
                >资金调整汇总核对重发申请</el-button>
            </el-col>
        </el-row>
        <!-- 机构对账汇总核对重发申请 -->
        <el-dialog title="机构对账汇总核对重发申请" :visible.sync="open711" width="700px" append-to-body>
            <el-form ref="form711" :model="form711" :rules="rules711" label-width="120px">
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="交易批次号" prop="batchId">
                            <el-input v-model.trim="form711.batchId" placeholder="请输入交易批次号" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="对账日期" prop="checkDate">
                            <el-input v-model.trim="form711.checkDate" placeholder="请输入对账日期" />
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="resendApply711">确 定</el-button>
                <el-button @click="cancel711">取 消</el-button>
            </div>
        </el-dialog>
        <!-- 资金调整汇总核对重发申请 -->
        <el-dialog title="资金调整汇总核对重发申请" :visible.sync="open713" width="700px" append-to-body>
            <el-form ref="form713" :model="form713" :rules="rules713" label-width="120px">
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="清算日期" prop="clearDate">
                            <el-input v-model.trim="form713.clearDate" placeholder="请输入清算日期" />
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="resendApply713">确 定</el-button>
                <el-button @click="cancel713">取 消</el-button>
            </div>
        </el-dialog>
    </div>
</template>
<script>
import { resendApply } from '@/api/pay-online/resendApply'

export default {
  name: "ResendApply",
  data() {
    return {
      // 遮罩层
      loading: true,
      open711: false,
      open713: false,
      // 表单参数
      form711: {},
      form713: {},
      // 表单校验
      rules711: {
        batchId: [
          { required: true, message: "交易批次号不能为空", trigger: "blur" }
        ],
        checkDate: [
          { required: true, message: "对账日期不能为空", trigger: "blur" }
        ]
      },
      rules713: {
        clearDate: [
          { required: true, message: "清算日期不能为空", trigger: "blur" }
        ],
      },
    };
  },
  created() {

  },
  methods: {
    /** 弹出重发711窗口 */
    showSend711() {
        this.resetForm("form711");
        this.open711 = true;
    },
    /** 弹出重发713窗口 */
    showSend713() {
        this.resetForm("form713");
        this.open713 = true;
    },
    cancel711() {
        this.open711 = false;
    },
    cancel713() {
        this.open713 = false;
    },
    resendApply711() {
        this.$refs["form711"].validate(valid => {
            if (valid) {
                resendApply("dcep.711.001.01").then(() => {
                    this.msgSuccess("重发成功");
                });
            }
        })
    },
    resendApply713() {
        this.$refs["form713"].validate(valid => {
            if (valid) {
                resendApply("dcep.713.001.01").then(() => {
                    this.msgSuccess("重发成功");
                });
            }
        })
    },
  }
};
</script>
