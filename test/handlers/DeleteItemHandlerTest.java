package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.URL;
import java.net.URLConnection;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author moshe
 * 
 * This class runs a test to verify the delete context handler. It attempts to 
 * delete an invalid note and expects it to fail. It makes sure the handle method 
 * works properly. 
 * 
 * It does not test the actual database call as that is the job of the 
 * MySQLAccess test class.
 * 
 */
public class DeleteItemHandlerTest {
    
    public DeleteItemHandlerTest() {
    }

    /**
     * Test of handle method, of class DeleteItemHandler.
     */
    @Test
    public void testHandle() throws Exception {
        System.out.println("handle");
        
        // Create the server and add handler
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(9000), 0);
        httpServer.createContext("/deleteitem", new DeleteItemHandler());
        
        // Start the server
        httpServer.start();
        
        // Verify the client code. Insert an invalid id an expect fail.
        URL url = new URL("http://localhost:9000/deleteitem?-4");
        URLConnection conn = url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        assertEquals("Must enter a valid ID", in.readLine());

        // stop the server
        httpServer.stop(0);
    }

    /**
     * Test of parseQuery method, of class DeleteItemHandler.
     */
    @Test
    public void testParseQuery() throws Exception {
        System.out.println("parseQuery");
        String query = "note=hello+world";
        String expResult = "hello world";
        String result = new AddItemHandler().parseQuery(query);
        assertEquals(expResult, result);
    }
    
}
