package com.alice.evaluate.springexpression;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.openjdk.jmh.annotations.Benchmark;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

@RunWith(JUnitPlatform.class)
class EvaluateSpringExpressionTest {

    /*
     * 
     *     @Benchmark
    public void autoTypeConversion() {
        class Simple {
            public List<Boolean> booleanList = new ArrayList<Boolean>();
        }
                
        Simple simple = new Simple();

        simple.booleanList.add(true);

        StandardEvaluationContext simpleContext = new StandardEvaluationContext(simple);
        ExpressionParser parser = new SpelExpressionParser();
        // false is passed in here as a string.  SpEL and the conversion service will 
        // correctly recognize that it needs to be a Boolean and convert it
        parser.parseExpression("booleanList[0]").setValue(simpleContext, "false");

        // b will be false
        Boolean b = simple.booleanList.get(0);
    }
     * 
     * */
    @Test
    @DisplayName("Simple string")
    void testSimpleString() {
        ExpressionParser parser = new SpelExpressionParser();
//TODO
        // evals to "Hello World"
        String helloWorld = (String) parser.parseExpression("'Hello World'").getValue();

        double avogadrosNumber = (Double) parser.parseExpression("6.0221415E+23").getValue();

        // evals to 2147483647
        int maxValue = (Integer) parser.parseExpression("0x7FFFFFFF").getValue();

        boolean trueValue = (Boolean) parser.parseExpression("true").getValue();

        Object nullValue = parser.parseExpression("null").getValue();
    }

    @Test
    public void test2() {
        Car car = new Car();
        car.setMake("Good manufacturer");
        car.setModel("Model 3");
        car.setYearOfProduction(2014);

        ExpressionParser expressionParser = new SpelExpressionParser();
        org.springframework.expression.Expression expression = expressionParser
                .parseExpression("'Any string' + ' ' + model");

        EvaluationContext context = new StandardEvaluationContext(car);
        String result = (String) expression.getValue(context);

        System.out.println(result);
    }

    @Test
    public void test3() {
        Car car = new Car();
        car.setMake("Good manufacturer");
        car.setModel("Model 3");
        car.setYearOfProduction(2014);

        ExpressionParser expressionParser = new SpelExpressionParser();
        org.springframework.expression.Expression expression = expressionParser
                .parseExpression("'Any string' + ' ' + #car.model");

        EvaluationContext context = new StandardEvaluationContext();
        context.setVariable("car", car);
        String result = (String) expression.getValue(context);

        System.out.println(result);
    }

    class Car {
        private String make;
        private String model;
        private Integer yearOfProduction;

        public String getMake() {
            return make;
        }

        public void setMake(String make) {
            this.make = make;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public Integer getYearOfProduction() {
            return yearOfProduction;
        }

        public void setYearOfProduction(Integer yearOfProduction) {
            this.yearOfProduction = yearOfProduction;
        }

    }
}
