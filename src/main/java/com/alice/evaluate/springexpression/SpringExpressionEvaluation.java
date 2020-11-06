/**
 * 
 */
package com.alice.evaluate.springexpression;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.alice.evaluate.Inventor;
import com.alice.evaluate.PlaceOfBirth;
import com.alice.evaluate.Society;

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
public class SpringExpressionEvaluation {

    private EvaluationContext teslaContext;
    private EvaluationContext societyContext;

    public SpringExpressionEvaluation() {
        teslaContext = buldTeslaContext();
        societyContext = buildSocietyContext();
    }

    @Benchmark
    public void emptyMethod() {

    }

    @Benchmark
    public void evaluateSimpleString() {
        ExpressionParser expressionParser = new SpelExpressionParser();
        Expression expression = expressionParser.parseExpression("'Any string'");
        expression.getValue();
    }

    @Benchmark
    public void methodInvocation() {
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression("'Hello World'.concat('!')");
        exp.getValue();
    }

    @Benchmark
    public void getterInvocation() {
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression("'Hello World'.bytes");
        exp.getValue();
    }

    @Benchmark
    public void nestedProperties() {
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression("'Hello World'.bytes.length");
        exp.getValue();
    }

    @Benchmark
    public void beanProperty() {
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression("name");
        exp.getValue(teslaContext);
    }

    @Benchmark
    public void numericOpWithBeanProperty() {
        ExpressionParser parser = new SpelExpressionParser();
        parser.parseExpression("Birthdate.Year + 1900").getValue(teslaContext);
    }

    @Benchmark
    public void accesArrayBeanProperty() {
        ExpressionParser parser = new SpelExpressionParser();
        parser.parseExpression("inventions[3]").getValue(teslaContext, String.class);
    }

    @Benchmark
    public void accesBeanPropertyFromList() {
        ExpressionParser parser = new SpelExpressionParser();
        parser.parseExpression("Members[0].Name").getValue(societyContext, String.class);
    }

    @Benchmark
    public void listAndArrayNavigation() {
        ExpressionParser parser = new SpelExpressionParser();
        parser.parseExpression("Members[0].Inventions[6]").getValue(societyContext, String.class);
    }

    @Benchmark
    public void mapAndBeanNavigation() {
        ExpressionParser parser = new SpelExpressionParser();
        parser.parseExpression("Officers['president'].PlaceOfBirth.City").getValue(societyContext, String.class);
    }

    private EvaluationContext buldTeslaContext() {
        Inventor tesla = buildTesla();
        EvaluationContext context = new StandardEvaluationContext(tesla);
        return context;
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

    private EvaluationContext buildSocietyContext() {
        Society society = buildSociety();
        EvaluationContext context = new StandardEvaluationContext(society);
        return context;
    }
}
