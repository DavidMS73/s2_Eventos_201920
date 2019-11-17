/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.podam;

import java.security.SecureRandom;
import uk.co.jemos.podam.common.AttributeStrategy;

/**
 *
 * @author Daniel Betancurth Dorado
 */
public class LongPositiveStrategy implements AttributeStrategy<Long> {

    SecureRandom sr = new SecureRandom();

    /**
     * Retorna un número entero positivo válido
     *
     * @return entero válido
     */
    @Override
    public Long getValue() {
        long leftLimit = 1L;
        return leftLimit + sr.nextLong();
    }
}
