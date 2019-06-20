import java.net.InetAddress;
import java.rmi.Naming;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ClienteConsola {
    public static List<MassPoint> generateList(int number) {
        List<MassPoint> tmp = new ArrayList<>();
        Random rnd = new Random();
        for (int i = 0; i < number; i++) {
            float x = rnd.nextFloat();
            float y = rnd.nextFloat();
            float z = rnd.nextFloat();
            float mass = rnd.nextFloat();
            tmp.add(new MassPoint(x, y, z, mass));
        }
        System.out.println("GENERATED");
        return tmp;
    }

    public static void main(String[] args) {
        List<MassPoint> massPoints = generateList(100_000);

        try {
            String ip = InetAddress.getLocalHost().getHostAddress();
            System.out.println(ip);
            BarycentreRMI barycentreRMI = (BarycentreRMI) Naming.lookup("//" + ip + ":" +1223+ "/b");
            MassPoint massPoint = barycentreRMI.calculateBarycentre((ArrayList<MassPoint>) massPoints);
            System.out.println(massPoint);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
