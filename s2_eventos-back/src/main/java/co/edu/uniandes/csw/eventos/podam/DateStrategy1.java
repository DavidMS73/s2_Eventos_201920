/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.podam;

import java.util.Calendar;
import java.util.Date;
import uk.co.jemos.podam.common.AttributeStrategy;

/**
 * Fecha para un día inicial
 * @author Germán David Martínez Solano
 */
public class DateStrategy1 implements AttributeStrategy<Date> {

    @Override
    public Date getValue() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 8);
        c.set(Calendar.HOUR_OF_DAY, c.getActualMinimum(Calendar.HOUR_OF_DAY));
        c.set(Calendar.MINUTE, c.getActualMinimum(Calendar.MINUTE));
        c.set(Calendar.SECOND, c.getActualMinimum(Calendar.SECOND));
        c.set(Calendar.MILLISECOND, c.getActualMinimum(Calendar.MILLISECOND));
        return c.getTime();
    }
}
