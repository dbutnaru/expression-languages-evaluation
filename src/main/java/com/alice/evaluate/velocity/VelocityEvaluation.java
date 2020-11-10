/**
 * 
 */
package com.alice.evaluate.velocity;

import com.alice.evaluate.Inventor;
import com.alice.evaluate.PlaceOfBirth;
import com.alice.evaluate.Society;
import com.alice.evaluate.VelocityHelper;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.StringResourceLoader;
import org.openjdk.jmh.annotations.*;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author dbutnaru
 *
 */
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.MICROSECONDS)
@Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.MICROSECONDS)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Fork(1)
@State(Scope.Benchmark)
public class VelocityEvaluation {

    private Inventor tesla;
    private Society society;
    private VelocityHelper velocityHelper;

    public VelocityEvaluation() {
        velocityHelper = new VelocityHelper(velocityEngine());
        tesla = buildTesla();
        society = buildSociety();
    }

    @Benchmark
    public void emptyMethod() {

    }

    @Benchmark
    public void evaluateSimpleString() {
        Map<String, Object> velocityContextParams = new HashMap<>();
        velocityContextParams.put("simple_string","Any string");
        velocityHelper.evaluate("simple_string",velocityContextParams);
    }

    @Benchmark
    public void methodInvocation() {
        Map<String, Object> velocityContextParams = new HashMap<>();
        velocityContextParams.put("simple_string","Any string");
        velocityHelper.evaluate("simple_string",velocityContextParams).concat("!");
    }

    @Benchmark
    public void getterInvocation() {
        Map<String, Object> velocityContextParams = new HashMap<>();
        velocityContextParams.put("simple_string","Any string");
        velocityHelper.evaluate("simple_string",velocityContextParams).getBytes();
    }

    @Benchmark
    public void nestedProperties() {
        Map<String, Object> velocityContextParams = new HashMap<>();
        velocityContextParams.put("simple_string","Any string");
        int bytes = velocityHelper.evaluate("simple_string",velocityContextParams).getBytes().length;
    }

    @Benchmark
    public void beanProperty() {
        Map<String, Object> velocityContextParams = new HashMap<>();
        velocityContextParams.put("tesla", tesla);
        velocityHelper.evaluate("$tesla.name",velocityContextParams);
    }

    @Benchmark
    public void numericOpWithBeanProperty() {
        Map<String, Object> velocityContextParams = new HashMap<>();
        velocityContextParams.put("tesla", tesla);
        int year = Integer.parseInt(velocityHelper.evaluate("$tesla.birthdate.year",velocityContextParams)) + 87;
    }

    @Benchmark
    public void accesArrayBeanProperty() {
        Map<String, Object> velocityContextParams = new HashMap<>();
        velocityContextParams.put("tesla", tesla);
        velocityHelper.evaluate("$tesla.inventions[3]", velocityContextParams);
    }

    @Benchmark
    public void accesBeanPropertyFromList() {
        Map<String, Object> velocityContextParams = new HashMap<>();
        velocityContextParams.put("society", society);
        velocityHelper.evaluate("$society.members[0].name", velocityContextParams);
    }

    @Benchmark
    public void listAndArrayNavigation() {
        Map<String, Object> velocityContextParams = new HashMap<>();
        velocityContextParams.put("society", society);
        velocityHelper.evaluate("$society.members[0].inventions[6]", velocityContextParams);
    }

    @Benchmark
    public void mapAndBeanNavigation() {
        Map<String, Object> velocityContextParams = new HashMap<>();
        velocityContextParams.put("society", society);
        velocityHelper.evaluate("$society.officers[\"president\"].placeOfBirth.city", velocityContextParams);
    }

    private VelocityEngine velocityEngine() {
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(Velocity.RESOURCE_LOADER, "string");
        velocityEngine.addProperty("string.resource.loader.class", StringResourceLoader.class.getName());
        velocityEngine.addProperty("string.resource.loader.repository.static", "false");
        velocityEngine.init();
        return velocityEngine;
    }

    private Inventor buildTesla() {
        GregorianCalendar c = new GregorianCalendar();
        c.set(1856, 7, 9);

        Inventor tesla = new Inventor("Nikola Tesla", c.getTime(), "Serbian");
        String[] inventions = new String[7];
        inventions[0] = "AC Electrical System";
        inventions[1] = "Hydroelectric Power Plant";
        inventions[2] = "Tesla Coil";
        inventions[3] = "Induction motor";
        inventions[4] = "Bladeless turbine";
        inventions[5] = "Wardenclyffe";
        inventions[6] = "Wireless communication";
        tesla.setInventions(inventions);
        tesla.setPlaceOfBirth(new PlaceOfBirth("Smiljan,", "Serbia"));
        return tesla;
    }

    private Society buildSociety() {
        Society society = new Society();
        Inventor tesla = buildTesla();
        society.getMembers().add(tesla);
        society.getOfficers().put(Society.President, tesla);
        return society;
    }

}
