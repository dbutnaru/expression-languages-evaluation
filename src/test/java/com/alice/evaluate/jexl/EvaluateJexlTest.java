package com.alice.evaluate.jexl;

import java.util.GregorianCalendar;

import javax.el.BeanELResolver;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlExpression;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.alice.evaluate.Inventor;
import com.alice.evaluate.PlaceOfBirth;

import de.odysseus.el.ExpressionFactoryImpl;
import de.odysseus.el.util.SimpleContext;

class EvaluateJexlTest {

    @Test
    @DisplayName("Simple string")
    void testSimpleString() {
        JexlEngine jexl = new JexlBuilder().create();
        JexlExpression e = jexl.createExpression("'Any string'");
        System.out.println(e.evaluate(null));
    }

    @Test
    @DisplayName("Bean property evaluation")
    void testPropertyEvaluation() {
        ExpressionFactory factory = new ExpressionFactoryImpl();
        ValueExpression e = factory.createValueExpression("${tesla.name}", String.class);
        SimpleContext context = new SimpleContext(new BeanELResolver());
        context.setVariable("tesla", factory.createValueExpression(buildTesla(), Inventor.class));
        //factory.createValueExpression(context, "${tesla}", Inventor.class).setValue(context, buildTesla());
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
