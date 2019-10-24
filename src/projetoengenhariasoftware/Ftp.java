/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetoengenhariasoftware;

import java.io.IOException;
import org.apache.commons.net.ftp.FTPClient;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jhonathan Alves
 */
public class Ftp {

    FTPClient ftp = new FTPClient();
    
    public void conectar(){
        try {
            ftp.connect("192.168.1.137", 2221);
            ftp.login("francis", "francis");
        } catch (IOException ex) {
            Logger.getLogger(Ftp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }        
    
    
}

