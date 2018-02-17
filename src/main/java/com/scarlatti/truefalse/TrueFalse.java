package com.scarlatti.truefalse;

import java.util.ArrayList;
import java.util.List;

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Friday, 2/16/2018
 */
public interface TrueFalse<T> {
    List<T> trueValues();
    List<T> falseValues();

    default List<T> allValues() {
        List<T> allValues = new ArrayList<>();
        allValues.addAll(trueValues());
        allValues.addAll(falseValues());

        return allValues;
    }
}
