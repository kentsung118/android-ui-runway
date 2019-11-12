package com.android.ui.kent.demo.framwork.okhttp.vo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by songzhukai on 2019-11-13.
 */
public class Bean {

    @SerializedName("outstr")
    public String outstr;
    @SerializedName("instr")
    public String instr;
    @SerializedName("reason")
    public String reason;
    @SerializedName("error_code")
    public int error_code;

    public String getOutstr() {
        return outstr;
    }

    public void setOutstr(String outstr) {
        this.outstr = outstr;
    }

    public String getInstr() {
        return instr;
    }

    public void setInstr(String instr) {
        this.instr = instr;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    @Override
    public String toString() {
        return "Bean{" +
                "outstr='" + outstr + '\'' +
                ", instr='" + instr + '\'' +
                ", reason='" + reason + '\'' +
                ", error_code=" + error_code +
                '}';
    }
}
