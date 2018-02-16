package com.scarlatti.truefalse;

import java.util.List;

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Friday, 2/16/2018
 */
public class BadQualifier implements Qualifier {

    private List<String> adims = new ADim().trueValues();
    private List<String> bdims = new BDim().trueValues();
    private List<String> cdims = new CDim().trueValues();
    private List<String> ddims = new DDim().trueValues();

    @Override
    public boolean qualify(String adim, String bdim, String cdim, String ddim) {
        return (
            adims.contains(adim) &&
            bdims.contains(bdim) &&
            cdims.contains(cdim) &&
            ddims.contains(ddim) || ddims.contains(adim)
        );
    }
}
