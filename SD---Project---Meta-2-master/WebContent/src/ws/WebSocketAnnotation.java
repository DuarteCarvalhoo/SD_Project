package ws;

import rmiserver.ClientHello;
import rmiserver.Hello;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.atomic.AtomicInteger;

import javax.websocket.server.ServerEndpoint;
import javax.websocket.OnOpen;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnError;
import javax.websocket.Session;

@ServerEndpoint(value = "/ws")
public class WebSocketAnnotation extends UnicastRemoteObject implements ClientHello, Serializable {
    private static final AtomicInteger sequence = new AtomicInteger(1);
    private Session session;
    private Hello rmi;

    public WebSocketAnnotation() throws RemoteException {
    }

    @OnOpen
    public void start(Session session) {
        this.session = session;
    }

    @OnClose
    public void end() {
    	// clean up once the WebSocket connection is closed
    }

    @OnMessage
    public void receiveMessage(String message) throws RemoteException {
        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry(7000);
            //registry = LocateRegistry.getRegistry("10.42.0.43",7000);
            this.rmi =(Hello) registry.lookup("Hello");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
        this.rmi.saveWSInfo(message,this, "web");

    }
    
    @OnError
    public void handleError(Throwable t) {
    	t.printStackTrace();
    }

    private void sendMessage(String text) {
    	// uses *this* object's session to call sendText()
    	try {
			this.session.getBasicRemote().sendText(text);
		} catch (IOException e) {
			// clean up once the WebSocket connection is closed
			try {
				this.session.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
    }

    @Override
    public void msg(String s){
        try {
            session.getBasicRemote().sendText(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
