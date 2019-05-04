import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import sun.misc.IOUtils;

public class Server {
	private ServerSocket tcpListener;
	private int port;
	 
	public Server(int port) {
		this.port = port;
	}
	
	public void run() throws IOException {
		this.tcpListener = new ServerSocket(this.port);
		
		System.out.println("Listening on: http://localhost:" + this.port);
		
		while(true) {
			Socket clientConnection = this.tcpListener.accept();
			InputStream input = clientConnection.getInputStream();
			
			BufferedInputStream bis = new BufferedInputStream(input);
			ByteArrayOutputStream buf = new ByteArrayOutputStream();
			int resultInt = bis.read();
			while(resultInt != -1) {
			    buf.write((byte) resultInt);
			    resultInt = bis.read();
			    
			}
			
			OutputStream out = clientConnection.getOutputStream();
			out.write(HttpProtocolTest.getResult(buf.toString("UTF-8")).getBytes());
			input.close();
			out.close();
			clientConnection.close();
		}
	}
	
	
}
