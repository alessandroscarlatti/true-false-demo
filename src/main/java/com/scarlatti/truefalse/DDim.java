package com.scarlatti.truefalse;

import java.util.Arrays;
import java.util.List;

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Friday, 2/16/2018
 */
public class DDim implements TrueFalse<String> {
    @Override
    public List<String> trueValues() {
        return Arrays.asList("DDim1", "DDim2", "DDim3");
    }

    @Override
    public List<String> falseValues() {
        return Arrays.asList("DDim98", "DDim99", "DDim100");
    }
}
