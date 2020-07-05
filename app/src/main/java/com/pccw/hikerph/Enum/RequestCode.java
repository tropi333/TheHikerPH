package com.pccw.hikerph.Enum;

public enum RequestCode {

    SELECT_PIC_GALLERY(1000),
    ADD_HIKE_SUCCESS(00002);

    private final int code;

    RequestCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
