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
 * @author Daniele Betancurth Dorado
 */
public class CorreoStrategy implements AttributeStrategy<String>{

    @Override
    public String getValue() {
        return RandomStringUtils.randomAlphanumeric(12)+"@uniandes.edu.co";
    }
}
