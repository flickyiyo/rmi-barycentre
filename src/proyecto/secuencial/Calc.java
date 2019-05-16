package proyecto.secuencial;

import proyecto.MassPoint;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Calc {
    public ArrayList<MassPoint> puntos;
    private BufferedReader reader;

    public Calc(ArrayList<MassPoint> massPoints) {
        this.puntos = massPoints;
    }

    public Calc(String file) {
        puntos = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader("/Users/yiyo/go-pingpong/barycenter/concurrent/" + file));
            readLineByLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readLineByLine() throws IOException {
        String line = reader.readLine();
        while (line != null) {
            parseLine(line);
            line = reader.readLine();
        }
        reader.close();
    }

    public void parseLine(String line) {
        String[] tokens = line.split(":");
        float x = Float.parseFloat(tokens[0]);
        float y = Float.parseFloat(tokens[1]);
        float z = Float.parseFloat(tokens[2]);
        float mass = Float.parseFloat(tokens[3]);
        puntos.add(new MassPoint(x, y, z, mass));
    }

    public static MassPoint addMassPoint(MassPoint a, MassPoint b) {
        return new MassPoint(
                a.getX() + b.getX(),
                a.getY() + b.getY(),
                a.getZ() + b.getZ(),
                a.getMasa() + b.getMasa()
        );
    }

    public static MassPoint avgMassPoint(MassPoint a, MassPoint b) {
        MassPoint sum = addMassPoint(a, b);
        return new MassPoint(
                sum.getX() / 2,
                sum.getY() / 2,
                sum.getZ() / 2,
                sum.getMasa()
        );
    }

    public static MassPoint toWeightedSubspace(MassPoint a) {
        return new MassPoint(
                a.getX() * a.getMasa(),
                a.getY() * a.getMasa(),
                a.getZ() * a.getMasa(),
                a.getMasa()
        );
    }

    public static MassPoint fromWeightedSubspace(MassPoint a) {
        return new MassPoint(
                a.getX() / a.getMasa(),
                a.getY() / a.getMasa(),
                a.getZ() / a.getMasa(),
                a.getMasa()
        );
    }

    public static MassPoint avgPointsWeighted(MassPoint a, MassPoint b) {
        MassPoint sumA = toWeightedSubspace(a);
        MassPoint sumB = toWeightedSubspace(b);
        return fromWeightedSubspace(avgMassPoint(sumA, sumB));
    }

    public void calculate() {
        while (puntos.size() > 1) {
            ArrayList<MassPoint> newPuntos = new ArrayList<>();
            // System.out.println("asdasD:" + puntos.size());
            for (int i = 0; i < puntos.size()-1; i+=2) {
                newPuntos = new ArrayList<>(newPuntos);
                MassPoint pto =avgPointsWeighted(puntos.get(i), puntos.get(i+1));
                newPuntos.add(pto );
                //newPuntos.add(avgPointsWeighted(puntos.get(i), puntos.get(i+1)));
            }

            if (puntos.size()%2!=0) {
                newPuntos = new ArrayList<>(newPuntos);
                newPuntos.add(puntos.get(puntos.size()-1));
            }

            puntos = newPuntos;
        }
        System.out.println(puntos.get(0).getX()
                + " : " +puntos.get(0).getY()
                + " : " +puntos.get(0).getZ()
                + " : " + puntos.get(0).getMasa()
        );
    }

    public void multithreadCalculate() {

    }
}
