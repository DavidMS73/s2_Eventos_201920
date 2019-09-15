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
 * @author Germán David Martínez Solano
 */
public class IntegerPositiveStrategy implements AttributeStrategy<Integer> {

    SecureRandom sr = new SecureRandom();

    /**
     * Retorna un número entero positivo válido
     *
     * @return entero válido
     */
    public Integer getValue() {
        return sr.nextInt(10 ^ 9) + 1;
    }
}
