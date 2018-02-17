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
public class ADim implements TrueFalse<String> {

    @Override
    public List<String> trueValues() {
        return Arrays.asList("ADim1", "ADim2", "ADim3");
    }

    @Override
    public List<String> falseValues() {
        return Arrays.asList("ADim98", "ADim99", "ADim100");
    }
}
