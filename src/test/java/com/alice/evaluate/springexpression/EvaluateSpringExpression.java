package com.alice.evaluate.springexpression;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

@RunWith(JUnitPlatform.class)
class EvaluateSpringExpression {

    @Test
    @DisplayName("Simple string")
    void testSimpleString() {
        ExpressionParser expressionParser = new SpelExpressionParser();
        Expression expression = expressionParser.parseExpression("'Any string'");
        String result = (String) expression.getValue();

        System.out.println(result);
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
