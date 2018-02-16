package com.scarlatti.truefalse;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
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
    public void createTrueScenarios() {
        List<Scenario> scenarios = getScenariosExpectedTrue();

        printScenarios(scenarios);
    }

    @Test
    public void createFalseScenarios() {
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

        List<Scenario> scenarios = new ArrayList<>();

        List<TrueFalse> params = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            params.add(new ADim());
            params.add(new BDim());
            params.add(new CDim());
            params.add(new DDim());
        }

        // need to take each param and make it the false one
        // then change all the OTHERS to each of BOTH true and false sets

        // we'll just use the starting index as the first item
        // and the rest of the original length as the rest
        for (int i = 0; i < params.size(); i++) {

            // the "definitely false" value is:
            List<String> definitelyFalseValues = params.get(i).falseValues();

            for (String param1 : definitelyFalseValues) {
                assignParam2(scenarios, param1, params, i, TrueFalse::trueValues);
                assignParam2(scenarios, param1, params, i, TrueFalse::falseValues);
            }

        }


        return scenarios;
    }

//    private void assignParam1(List<Scenario> scenarios, String param1, List<TrueFalse> params, int i, ValueProvider valueProvider) {
//            assignParam2(scenarios, param1, params, i, TrueFalse::trueValues);
//            assignParam2(scenarios, param1, params, i, TrueFalse::falseValues);
//    }

    private void assignParam2(List<Scenario> scenarios, String param1, List<TrueFalse> params, int i, ValueProvider valueProvider) {
        for (String param2 : valueProvider.provideValues(params.get(i + 1))) {
            assignParam3(scenarios, param1, param2, params, i, TrueFalse::trueValues);
            assignParam3(scenarios, param2, param2, params, i, TrueFalse::falseValues);
        }
    }

    private void assignParam3(List<Scenario> scenarios, String param1, String param2, List<TrueFalse> params, int i, ValueProvider valueProvider) {
        for (String param3 : valueProvider.provideValues(params.get(i + 2))) {
            assignParam4(scenarios, param1, param2, param3, params, i, TrueFalse::trueValues);
            assignParam4(scenarios, param2, param2, param3, params, i, TrueFalse::falseValues);
        }
    }

    private void assignParam4(List<Scenario> scenarios, String param1, String param2, String param3, List<TrueFalse> params, int i, ValueProvider valueProvider) {
        for (String param4 : valueProvider.provideValues(params.get(i + 3))) {
            scenarios.add(new Scenario(
                param1,
                param2,
                param3,
                param4,
                false
            ));
        }
    }

    @FunctionalInterface
    private interface ValueProvider {
        List<String> provideValues(TrueFalse trueFalse);
    }

    private void printScenarios(List<Scenario> scenarios) {
        for (Scenario scenario : scenarios) {
            System.out.println(scenario);
        }

        System.out.println(scenarios.size() + " scenarios");
    }
}
