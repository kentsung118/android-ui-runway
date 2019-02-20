package com.android.ui.kent.demo.udp.vo;

/**
 * Created by Kent Song on 2019/2/19.
 */
public class PonInfo {

    private String wifyMac;
    private String onuMac;
    private PON pon;
    private WAN wan;


    public String getWifyMac() {
        return wifyMac;
    }

    public void setWifyMac(String wifyMac) {
        this.wifyMac = wifyMac;
    }

    public String getOnuMac() {
        return onuMac;
    }

    public void setOnuMac(String onuMac) {
        this.onuMac = onuMac;
    }

    public PON getPon() {
        return pon;
    }

    public void setPon(PON pon) {
        this.pon = pon;
    }

    public WAN getWan() {
        return wan;
    }

    public void setWan(WAN wan) {
        this.wan = wan;
    }

    public static class PON {
        String mac;
        String temperature; //温度
        String voltage; // 供电电压
        String electricCurrent; // 偏置电流
        String sendOpticPower; //发送光功率
        String reciveOpticPower; //接收光功率

        private PON(Builder builder) {
            setMac(builder.mac);
            setTemperature(builder.temperature);
            setVoltage(builder.voltage);
            setElectricCurrent(builder.electricCurrent);
            setSendOpticPower(builder.sendOpticPower);
            setReciveOpticPower(builder.reciveOpticPower);
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public String getMac() {
            return mac;
        }

        public void setMac(String mac) {
            this.mac = mac;
        }

        public String getTemperature() {
            return temperature;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }

        public String getVoltage() {
            return voltage;
        }

        public void setVoltage(String voltage) {
            this.voltage = voltage;
        }

        public String getElectricCurrent() {
            return electricCurrent;
        }

        public void setElectricCurrent(String electricCurrent) {
            this.electricCurrent = electricCurrent;
        }

        public String getSendOpticPower() {
            return sendOpticPower;
        }

        public void setSendOpticPower(String sendOpticPower) {
            this.sendOpticPower = sendOpticPower;
        }

        public String getReciveOpticPower() {
            return reciveOpticPower;
        }

        public void setReciveOpticPower(String reciveOpticPower) {
            this.reciveOpticPower = reciveOpticPower;
        }


        public static final class Builder {
            private String mac;
            private String temperature;
            private String voltage;
            private String electricCurrent;
            private String sendOpticPower;
            private String reciveOpticPower;

            private Builder() {
            }

            public Builder setMac(String val) {
                mac = val;
                return this;
            }

            public Builder setTemperature(String val) {
                temperature = val;
                return this;
            }

            public Builder setVoltage(String val) {
                voltage = val;
                return this;
            }

            public Builder setElectricCurrent(String val) {
                electricCurrent = val;
                return this;
            }

            public Builder setSendOpticPower(String val) {
                sendOpticPower = val;
                return this;
            }

            public Builder setReciveOpticPower(String val) {
                reciveOpticPower = val;
                return this;
            }

            public PON build() {
                return new PON(this);
            }
        }
    }

    public static class WAN {
        String ip;
        String subMask; //子网掩码
        String gateway; //网关
        String dns;

        private WAN(Builder builder) {
            ip = builder.ip;
            subMask = builder.subMask;
            gateway = builder.gateway;
            dns = builder.dns;
        }

        public static Builder newBuilder() {
            return new Builder();
        }


        public static final class Builder {
            private String ip;
            private String subMask;
            private String gateway;
            private String dns;

            private Builder() {
            }

            public Builder setIp(String val) {
                ip = val;
                return this;
            }

            public Builder setSubMask(String val) {
                subMask = val;
                return this;
            }

            public Builder setGateway(String val) {
                gateway = val;
                return this;
            }

            public Builder setDns(String val) {
                dns = val;
                return this;
            }

            public WAN build() {
                return new WAN(this);
            }
        }
    }

}
