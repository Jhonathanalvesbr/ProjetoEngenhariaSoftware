/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetoengenhariasoftware;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.commons.net.ftp.FTPClient;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jhonathan Alves
 */
public class Ftp {

    private FTPClient ftp = new FTPClient();
    
    public void conectar(String diretorio){
        try {
            ftp.connect("100.65.176.17", 2221);
            ftp.login("francis", "francis");
            ftp.makeDirectory(diretorio);
            ftp.changeWorkingDirectory(diretorio);
            
        } catch (IOException ex) {
            Logger.getLogger(Ftp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }        
    
    public void upload(String nome, String diretorio, String diretorioFtp) throws FileNotFoundException, IOException 
    {   
        ftp.makeDirectory(diretorioFtp);
        ftp.changeWorkingDirectory(diretorioFtp);
        String[] tamanho = ftp.listNames();
            int i =0;
            for (String f : tamanho){
                i++;
            }
        FileInputStream arqEnviar = new FileInputStream (diretorio);
            ftp.storeFile (i+"-"+nome, arqEnviar);
            
    }
    
    public void download(String diretorioCliente,String diretorio, int selecao, String diretorioFtp) throws FileNotFoundException, IOException
    {
        ftp.changeWorkingDirectory(diretorioFtp);
        
        int i = 0;
        String arquivoDownload = null;
        String[] arq = ftp.listNames();
            System.out.println ("Listando arquivos: \n");
            int x = 0;
            for (String f : arq){
                if(i == x++)
                {
                    arquivoDownload = f;
                    break;
                }
                
                    arquivoDownload = f;
                
                     
            }
            
        FileOutputStream fos = new FileOutputStream(diretorioCliente+arquivoDownload);
        System.out.println(diretorioCliente+arquivoDownload);
        
            if (ftp.retrieveFile(arquivoDownload, fos))
            {        
                System.out.println("Download efetuado com sucesso!");
            }     
            else
                  System.out.println ("Erro ao efetuar download do arquivo.");
    }
}

