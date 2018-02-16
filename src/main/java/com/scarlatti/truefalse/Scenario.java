package com.scarlatti.truefalse;

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Friday, 2/16/2018
 */
public class Scenario {
    private String adim;
    private String bdim;
    private String cdim;
    private String ddim;
    private boolean expectedToSucceed;

    @Override
    public String toString() {
        return "[" + adim + ", " + bdim + ", " + cdim + ", " + ddim + ", " + expectedToSucceed + "] ";
    }

    public String getAdim() {
        return adim;
    }

    public void setAdim(String adim) {
        this.adim = adim;
    }

    public String getBdim() {
        return bdim;
    }

    public void setBdim(String bdim) {
        this.bdim = bdim;
    }

    public String getCdim() {
        return cdim;
    }

    public void setCdim(String cdim) {
        this.cdim = cdim;
    }

    public String getDdim() {
        return ddim;
    }

    public void setDdim(String ddim) {
        this.ddim = ddim;
    }

    public boolean isExpectedToSucceed() {
        return expectedToSucceed;
    }

    public void setExpectedToSucceed(boolean expectedToSucceed) {
        this.expectedToSucceed = expectedToSucceed;
    }
}
