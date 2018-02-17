package com.scarlatti.truefalse;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
     * <p>
     * each have their own true and false values sets.
     * <p>
     * the scenario only applies when all are true.
     * <p>
     * So we build a set of scenarios that are expected true
     * and we build a set of scenarios that are expected false
     */
    @Test
    public void createTrueScenarios() {
        List<Scenario> scenarios = getScenariosExpectedTrue();
        assertEquals(81, scenarios.size());

        printScenarios(scenarios);
    }

    @Test
    public void createFalseScenarios() {
        List<Scenario> scenarios = getScenariosExpectedFalse2();
        assertEquals(2592, scenarios.size());

        printScenarios(scenarios);
    }

    @Test
    public void testGoodQualifierTrue() {
        testQualifierTrue(new GoodQualifier());
    }

    @Test
    public void testGoodQualifierFalse() {
        int failures = testQualifierFalse(new GoodQualifier());
        assertEquals(0, failures);
    }

    @Test
    public void testBadQualifierTrue() {
        testQualifierTrue(new BadQualifier());
    }

    @Test
    public void testBadQualifierFalse() {
        int failures = testQualifierFalse(new BadQualifier());
        assertTrue(failures > 0);
    }


    public void testQualifierTrue(Qualifier qualifier) {

        List<Scenario> trueScenarios = getScenariosExpectedTrue();

        for (Scenario scenario : trueScenarios) {
            assertTrue(qualifier.qualify(
                scenario.getAdim(),
                scenario.getBdim(),
                scenario.getCdim(),
                scenario.getDdim()
            ));
        }
    }

    public int testQualifierFalse(Qualifier qualifier) {
        List<Scenario> falseScenarios = getScenariosExpectedFalse2();

        int failures = 0;

        for (Scenario scenario : falseScenarios) {
            try {
                assertFalse(qualifier.qualify(
                    scenario.getAdim(),
                    scenario.getBdim(),
                    scenario.getCdim(),
                    scenario.getDdim()
                ));
            } catch (AssertionError e) {
                System.err.println("Assertion failed!" + scenario);
                failures++;
            }
        }

        return failures;
    }

    /**
     * This is the easy one!
     *
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
     *
     * @return scenarios expected to be false
     */
    private List<Scenario> getScenariosExpectedFalse2() {
        List<Scenario> scenarios = new ArrayList<>();

        // iterate over all the combinations, but use a paramProvider list and indices
        // (and just make sure that the chosen index for false always provides
        // false values)

        List<ParamsHelper2> params2 = Arrays.asList(
            new ParamsHelper2(new ADim(), Scenario::setAdim),
            new ParamsHelper2(new BDim(), Scenario::setBdim),
            new ParamsHelper2(new CDim(), Scenario::setCdim),
            new ParamsHelper2(new DDim(), Scenario::setDdim)
        );

        // this is the parameter we are as the
        // definitely false one, all the others should
        // be all possible values
        for (int i = 0; i < params2.size(); i++) {

            List<ParamsHelper3> paramsHelper3s = new ArrayList<>();

            for (int j = 0; j < params2.size(); j++) {
                paramsHelper3s.add(new ParamsHelper3(
                    (i == j) ? params2.get(j).trueFalse::falseValues : params2.get(j).trueFalse::allValues,
                    params2.get(j).scenarioSetter
                ));
            }

            iterateThisParam(0, paramsHelper3s, new Scenario(), scenarios);
        }

        return scenarios;
    }

    private void iterateThisParam(int i, List<ParamsHelper3> params, Scenario workingScenario, List<Scenario> scenarios) {
        if (i == params.size()) {
            scenarios.add(new Scenario(workingScenario));
            return;
        }

        // for each of the values provided
        // this may be all values, or it may be only false values.

        for (String value : params.get(i).setProvider.provideSet()) {
            params.get(i).scenarioSetter.setValueOnScenario(workingScenario, value);

            iterateThisParam(i + 1, params, workingScenario, scenarios);
        }
    }

    private static class ParamsHelper2 {
        TrueFalse trueFalse;
        ScenarioSetter scenarioSetter;

        public ParamsHelper2(TrueFalse trueFalse, ScenarioSetter scenarioSetter) {
            this.trueFalse = trueFalse;
            this.scenarioSetter = scenarioSetter;
        }
    }

    private static class ParamsHelper3 {
        SetProvider setProvider;
        ScenarioSetter scenarioSetter;

        public ParamsHelper3(SetProvider setProvider, ScenarioSetter scenarioSetter) {
            this.setProvider = setProvider;
            this.scenarioSetter = scenarioSetter;
        }
    }

    @FunctionalInterface
    interface SetProvider {
        List<String> provideSet();
    }

    @FunctionalInterface
    interface ScenarioSetter {
        void setValueOnScenario(Scenario scenario, String value);
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
