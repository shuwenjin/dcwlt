package com.dcits.dcwlt.common.pay.type;

/**
 * 跨行标识
 */
public enum OutOrgTypeEnum {

    /**
     * 行内标识
     */
    InnerOrg(0),
    /**
     * 行外标识
     */
    OutOrg(1);

    private int type;

    OutOrgTypeEnum(int i) {
        this.type = i;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
