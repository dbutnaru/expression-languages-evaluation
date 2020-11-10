package com.alice.evaluate.jexl;

import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.jexl3.MapContext;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import com.alice.evaluate.Inventor;
import com.alice.evaluate.PlaceOfBirth;

@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.MICROSECONDS)
@Measurement(iterations = 100, time = 1, timeUnit = TimeUnit.MICROSECONDS)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Fork(1)
@State(Scope.Benchmark)
public class JexlEvaluation {

    private Inventor tesla;
    
    public JexlEvaluation() {
        tesla = buildTesla();
    }

    @Benchmark
    public void emptyMethod() {

    }

    @Benchmark
    public void evaluateSimpleString() {
        JexlEngine jexl = new JexlBuilder().create();
        JexlExpression e = jexl.createExpression("'Any string'");
        e.evaluate(null);
    }

    @Benchmark
    public void beanProperty() {
        JexlEngine jexl = new JexlBuilder().create();
        JexlExpression e = jexl.createExpression("tesla.name");
        JexlContext context = new MapContext();
        context.set("tesla", tesla);
        System.out.println(e.evaluate(context));
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
}
