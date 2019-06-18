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
            BarycentreRMI barycentreRMI = (Server) Naming.lookup("//" + "157.230.151.160:1223" + "/barycentre");
            barycentreRMI.calculateBarycentre((ArrayList<MassPoint>) massPoints);
        } catch (Exception ex) {

        }
    }
}
