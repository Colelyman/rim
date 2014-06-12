/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rimpro.pkg2.pkg0;

/**
 *
 * @author Secretary
 */
public class myException extends Exception {
    private String message = "";
    public myException() {
        super();
    }
    
    public myException(String message) {
        super(message);
        this.message = message;
    }
    
    public myException(Throwable cause) {
        super(cause);
    }
    
    @Override
    public String toString() {
        return message;
    }
    
    @Override
    public String getMessage() {
        return message;
    }
}
