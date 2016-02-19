/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JsfEmailActivation;

import java.sql.Statement;
import java.util.UUID;

/**
 *
 * @author ilkaygunel
 */
public class Test {
    public static void main(String[] args) {
        System.err.println(UUID.randomUUID().toString());
    }
    public void metot()
    {
        System.out.println(Statement.RETURN_GENERATED_KEYS);
    }
}
