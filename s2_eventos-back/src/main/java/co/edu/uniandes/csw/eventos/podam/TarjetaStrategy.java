/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.podam;

import org.apache.commons.lang3.RandomStringUtils;
import uk.co.jemos.podam.common.AttributeStrategy;

/**
 *
 * @author Samuel Osorio y Germán Martínez
 */
public class TarjetaStrategy implements AttributeStrategy<String> {

    @Override
    /**
     * Retorna una tarjeta válida
     */
    public String getValue() {
        return RandomStringUtils.randomNumeric(16);
    }
}
