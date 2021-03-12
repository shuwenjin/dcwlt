package com.dcits.dcwlt.common.pay.enums;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>批量任务代码</p>
 */
public enum TaskCodeEnum {

	CD001("CD001","CD001-金融登记簿抽数"),
	CS001("CS001","CS001-抽数前置任务检查"),
	CS002("CS002","CS002-汇总对账处理"),
	CS003("CS003","CS003-汇总对账结果处理"),
	CT001("CT001","CT001-对账明细文件转存"),
	CT002("CT002","CT002-金融开放平台申请文件"),
	CT003("CT003","CT003-检查明细文件是否收齐"),
	CT004("CT004","CT004-解析明细文件入库任务"),
	CT005("CT005","CT005-抽数前置任务检查"),
	CT006("CT006","CT006-明细对账任务"),
	CT007("CT007","CT007-明细对账结果处理任务"),
	CW001("CW001","CW001-明细对账不平结果重对账抽数任务"),
	CW002("CW002","CW002-明细对账不平结果重对账")
    ;

    private static final Set<String> enumSet = new HashSet<>(16);

    static {
        Arrays.asList(TaskCodeEnum.values()).forEach(e -> enumSet.add(e.getCode()));
    }

    public static boolean hasEnum(String code){
        return enumSet.contains(code);
    }


    private String code;

    private String desc;

    TaskCodeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
    
    public boolean equalCode(String targetCode) {
    	return code.equals(targetCode);
    }
}
