import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Server extends UnicastRemoteObject implements BarycentreRMI {

    protected Server() throws RemoteException { }

    @Override
    public MassPoint calculateBarycentre(ArrayList<MassPoint> massPoints) throws RemoteException {
        System.out.println("Calculating");
        return new Calc(massPoints).calculate();
    }

    @Override
    public long getTime() throws RemoteException {
        return 0;
    }

    public static void main(String[] args) {
        try {
            BarycentreRMI barycentreRMI = new Server();
            String ip = InetAddress.getLocalHost().getHostAddress();
            System.out.println(ip);
            Naming.rebind("//" + ip + ":" + 1223 + "/b", barycentreRMI);
        } catch (Exception ex) {

        }
    }
}
