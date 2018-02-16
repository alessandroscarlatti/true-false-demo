package com.scarlatti.truefalse;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Friday, 2/16/2018
 */
public class TestTrueFalse {

    /**
     * Print the scenarios that we generate from the true/false cases.
     * So we are looking at scenarios of:
     * A | B | C | D
     *
     * each have their own true and false values sets.
     *
     * the scenario only applies when all are true.
     *
     * So we build a set of scenarios that are expected true
     * and we build a set of scenarios that are expected false
     */
    @Test
    public void createScenarios() {
        List<Scenario> scenarios = getScenariosExpectedTrue();

        printScenarios(scenarios);
    }

    /**
     * This is the easy one!
     * @return scenarios expected to be true
     */
    private List<Scenario> getScenariosExpectedTrue() {
        List<Scenario> scenarios = new ArrayList<>();

        for (String adim : new ADim().trueValues()) {
            for (String bdim : new BDim().trueValues()) {
                for (String cdim : new CDim().trueValues()) {
                    for (String dDim : new DDim().trueValues()) {
                        Scenario scenario = new Scenario();
                        scenario.setAdim(adim);
                        scenario.setBdim(bdim);
                        scenario.setCdim(cdim);
                        scenario.setDdim(dDim);
                        scenario.setExpectedToSucceed(true);

                        scenarios.add(scenario);
                    }
                }
            }
        }

        return scenarios;
    }

    /**
     * This is probably the longer one, definitely the more difficult one!
     * @return scenarios expected to be false
     */
    private List<Scenario> getScenariosExpectedFalse() {
        return null;
    }

    private void printScenarios(List<Scenario> scenarios) {
        for (Scenario scenario : scenarios) {
            System.out.println(scenario);
        }
    }
}
