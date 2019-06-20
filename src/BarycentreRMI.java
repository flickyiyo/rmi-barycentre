import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface BarycentreRMI extends Remote {
    public MassPoint calculateBarycentre(ArrayList<MassPoint> massPoints) throws RemoteException;
    public long getTime() throws RemoteException;
}
