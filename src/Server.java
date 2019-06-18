import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Server extends UnicastRemoteObject implements BarycentreRMI {

    protected Server() throws RemoteException { }

    @Override
    public MassPoint calculateBarycentre(ArrayList<MassPoint> massPoints) throws RemoteException {
        return new Calc(massPoints).calculate();
    }

}
