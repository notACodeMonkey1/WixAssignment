package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author moshe
 * This class handle the root url context for the app. It returns the index.html file
 * which contains the form for the client to interact with. 
 */
public class RootHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange he) throws IOException {
        
        File indexHTML = new File("src/views/index.html").getCanonicalFile(); 
        
        if(!indexHTML.exists()){
            String response = "404 (Not Found)\n"
                    + "curretn dir: " + System.getProperty("user.dir");
            he.sendResponseHeaders(404, response.length());
            OutputStream os = he.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }else{       
            he.sendResponseHeaders(200, 0);
            OutputStream os = he.getResponseBody();
            FileInputStream fs = new FileInputStream(indexHTML);
            final byte[] buffer = new byte[0x10000];
            int count = 0;
            while ((count = fs.read(buffer)) >= 0){
                os.write(buffer, 0, count);
            }
            fs.close();
            os.close();
        }
    }
    
}
