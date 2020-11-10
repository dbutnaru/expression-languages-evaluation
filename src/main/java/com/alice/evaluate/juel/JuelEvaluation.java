/**
 * 
 */
package com.alice.evaluate.juel;

import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import com.alice.evaluate.Inventor;
import com.alice.evaluate.PlaceOfBirth;

import de.odysseus.el.ExpressionFactoryImpl;
import de.odysseus.el.util.SimpleContext;

/**
 * @author dbutnaru
 *
 */
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.MICROSECONDS)
@Measurement(iterations = 100, time = 1, timeUnit = TimeUnit.MICROSECONDS)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Fork(1)
@State(Scope.Benchmark)
public class JuelEvaluation {

    public JuelEvaluation() {
        // TODO Auto-generated constructor stub
    }

    @Benchmark
    public void emptyMethod() {

    }

    @Benchmark
    public void evaluateSimpleString() {
        ExpressionFactory factory = new ExpressionFactoryImpl();
        ValueExpression e = factory.createValueExpression("it is one ", String.class);
        e.getValue(null);
    }

    @Benchmark
    public void beanProperty() {
        ExpressionFactory factory = new ExpressionFactoryImpl();
        ValueExpression e = factory.createValueExpression("${tesla.name}", String.class);
        SimpleContext context = new SimpleContext();
        context.setVariable("tesla", factory.createValueExpression(buildTesla(), Inventor.class));
        System.out.println(e.getValue(context));
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
