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
        List<Scenario> scenarios = getScenariosExpectedFalse();
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
        List<Scenario> falseScenarios = getScenariosExpectedFalse();

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

    private List<Scenario> getScenariosExpectedTrue() {
        List<Scenario> scenarios = new ArrayList<>();

        // iterate over all the combinations, but use a paramProvider list and indices
        // (and just make sure that the chosen index for false always provides
        // false values)

        List<ParameterDefinition> params2 = Arrays.asList(
            new ParameterDefinition(new ADim(), Scenario::setAdim),
            new ParameterDefinition(new BDim(), Scenario::setBdim),
            new ParameterDefinition(new CDim(), Scenario::setCdim),
            new ParameterDefinition(new DDim(), Scenario::setDdim)
        );

        List<ParameterProvider> parameterProviders = new ArrayList<>();

        for (int j = 0; j < params2.size(); j++) {
            ParameterDefinition param = params2.get(j);
            parameterProviders.add(new ParameterProvider(
                param.parameter::trueValues,
                param.parameterSetter
            ));
        }

        iterateThisParam(0, parameterProviders, new Scenario(true), scenarios);

        return scenarios;
    }

    /**
     * This is probably the longer one, definitely the more difficult one!
     *
     * @return scenarios expected to be false
     */
    private List<Scenario> getScenariosExpectedFalse() {
        List<Scenario> scenarios = new ArrayList<>();

        // iterate over all the combinations, but use a paramProvider list and indices
        // (and just make sure that the chosen index for false always provides
        // false values)

        List<ParameterDefinition> params = Arrays.asList(
            new ParameterDefinition(new ADim(), Scenario::setAdim),
            new ParameterDefinition(new BDim(), Scenario::setBdim),
            new ParameterDefinition(new CDim(), Scenario::setCdim),
            new ParameterDefinition(new DDim(), Scenario::setDdim)
        );

        // this is the parameter we are as the
        // definitely false one, all the others should
        // be all possible values
        for (int indexOfDefinitelyFalseParam = 0; indexOfDefinitelyFalseParam < params.size(); indexOfDefinitelyFalseParam++) {
            List<ParameterProvider> parameterProviders = new ArrayList<>();

            for (int i = 0; i < params.size(); i++) {
                ParameterDefinition param = params.get(i);
                parameterProviders.add(
                    new ParameterProvider(
                        (indexOfDefinitelyFalseParam == i) ?
                            param.parameter::falseValues :
                            param.parameter::allValues,
                        param.parameterSetter
                    )
                );
            }

            iterateThisParam(0, parameterProviders, new Scenario(), scenarios);
        }

        return scenarios;
    }

    private void iterateThisParam(int i, List<ParameterProvider> params, Scenario workingScenario, List<Scenario> scenarios) {
        // if this would be past the last parameter, we are at the end of the loop,
        // so it is time to build the new scenario and add it to the list.
        if (i == params.size()) {
            scenarios.add(new Scenario(workingScenario));
            return;
        }

        // for each of the values provided
        // this may be all values, or it may be only false values.

        for (String value : params.get(i).setProvider.provideSet()) {
            params.get(i).parameterSetter.setValueOnScenario(workingScenario, value);

            iterateThisParam(i + 1, params, workingScenario, scenarios);
        }
    }

    private static class ParameterDefinition {
        TrueFalse parameter;
        ParameterSetter parameterSetter;

        ParameterDefinition(TrueFalse parameter, ParameterSetter parameterSetter) {
            this.parameter = parameter;
            this.parameterSetter = parameterSetter;
        }
    }

    private static class ParameterProvider {
        SetProvider setProvider;
        ParameterSetter parameterSetter;

        ParameterProvider(SetProvider setProvider, ParameterSetter parameterSetter) {
            this.setProvider = setProvider;
            this.parameterSetter = parameterSetter;
        }
    }

    @FunctionalInterface
    interface SetProvider {
        List<String> provideSet();
    }

    @FunctionalInterface
    interface ParameterSetter {
        void setValueOnScenario(Scenario scenario, String value);
    }

    private void printScenarios(List<Scenario> scenarios) {
        for (Scenario scenario : scenarios) {
            System.out.println(scenario);
        }

        System.out.println(scenarios.size() + " scenarios");
    }
}
