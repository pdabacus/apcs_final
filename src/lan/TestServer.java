// test communication between server and client

package lan;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class TestServer
{

	public static final int PORT = 2222;

	private boolean listening;
	private ServerSocket server;
	private Thread serverThread;

	public TestServer()
	{
		try
		{
			server = new ServerSocket(PORT);
			System.out.println("Server Listening");
			listening = true;
			listen();
		}
		catch (IOException e)
		{
			System.out.println("Error: couldn't bind server to port " + PORT);
		}
	}

	public void listen()
	{
		serverThread = new Thread()
		{
			public void run()
			{
				try
				{
					while (listening)
					{
						System.out.println("Waiting for connection");
						Socket client = server.accept();
						String clientIP = client.getInetAddress().getHostAddress();
						System.out.println("Connection established: " + clientIP);
						client.close();
						System.out.println("Connection closed");
					}
				}
				catch (IOException e)
				{
					System.out.println("Error: Client not accepted");
				}
			}
		};

		serverThread.start();
		
	}

	public void stop()
	{
		if (listening)
		{
			System.out.println("Server Stopped");
			listening = false;
			serverThread.stop();
		}
	}

	public static void main(String[] args) throws InterruptedException
	{
		TestServer server = new TestServer();
		TimeUnit.SECONDS.sleep(100);
		server.stop();
	}

}