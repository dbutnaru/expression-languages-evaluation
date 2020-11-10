package com.alice.evaluate.juel;

import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import de.odysseus.el.ExpressionFactoryImpl;

@RunWith(JUnitPlatform.class)
class EvaluateJuelTest {

    @Test
    @DisplayName("Simple string")
    void testSimpleString() {
        ExpressionFactory factory = new ExpressionFactoryImpl();
        
        ValueExpression e = factory.createValueExpression("it is one ", String.class);

        System.out.println(e.getValue(null));
    }

    @Test
    @DisplayName("Bean property evaluation")
    void testPropertyEvaluation() {
        ExpressionFactory factory = new ExpressionFactoryImpl();
        
        ValueExpression e = factory.createValueExpression("it is one ", String.class);

        System.out.println(e.getValue(null));
    }
}
