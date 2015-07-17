package deliverCar;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;

import BL.ClientCar;
import Listeners.ClientListener;

public class SendCar {
	private static int SERVER_PORT = 9090;
	
	public boolean sendACar(ClientCar car) {
		Socket socket;
		ObjectOutputStream outputStream;
		ObjectInputStream inputStream;
		try {
			socket = new Socket("localhost", SERVER_PORT);
			outputStream = new ObjectOutputStream(socket.getOutputStream());
			inputStream = new ObjectInputStream(socket.getInputStream());
			Object temp = inputStream.readObject();
			outputStream.writeObject(car);
			if (!(temp instanceof ClientCar)) {
				temp = inputStream.readObject();
			}
			car = (ClientCar) temp; //Confirm the car generated, else Exception -> false;
		} catch (IOException | ClassNotFoundException | IllegalArgumentException e) {
			return false;
		}
		try {
			inputStream.close();
			outputStream.close();
			socket.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return true;
	}
	
	@SuppressWarnings("finally")
	public int getPumpsNumber() {
		Socket socket;
		ObjectOutputStream outputStream;
		ObjectInputStream inputStream;
		int pumps = 0;
		try {
			socket = new Socket("localhost", SERVER_PORT);
			outputStream = new ObjectOutputStream(socket.getOutputStream());
			inputStream = new ObjectInputStream(socket.getInputStream());
			Object temp = inputStream.readObject();
			if (temp instanceof Integer)
				pumps = (Integer) temp;
			inputStream.close();
			outputStream.close();
			socket.close();
		} catch (IOException | ClassNotFoundException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		return pumps;
	}
	
}
