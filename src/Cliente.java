
import javax.swing.*;
import java.net.InetAddress;
import java.rmi.Naming;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cliente  extends JFrame{
    public static void main(String[] args) {
        new Cliente();
    }

    int numeroMaxHilos = 20;
    int numeroMaxCuerposPorHilo = 1000;
    JLabel lblTxtNumeroMaxHilos;
    JLabel lblTxtNumeroMaxCuerpos;
    JTextField txtNumeroMaxHilos;
    JTextField txtNumeroMaxCuerpos;
    JTextField txtNumeroCuerpos;
    JButton start;
    JLabel tiempoLineal;
    JLabel tiempoConcurrente;
    JLabel diferencia;
    int numeroCuerpos = 10000;

    public Cliente() {
        this.setBounds(100, 100, 500, 500);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        txtNumeroMaxCuerpos = new JTextField();
        txtNumeroMaxCuerpos.setBounds(200, 50, 200, 30);
        lblTxtNumeroMaxCuerpos = new JLabel("Numero de cuerpos / hilo");
        lblTxtNumeroMaxCuerpos.setBounds(10, 50, 200, 30);
        txtNumeroMaxCuerpos.setText("1000");
        add(lblTxtNumeroMaxCuerpos);
        add(txtNumeroMaxCuerpos);
        txtNumeroMaxHilos = new JTextField();
        txtNumeroMaxHilos.setBounds(200, 100, 200, 30);
        txtNumeroMaxHilos.setText("20");
        lblTxtNumeroMaxHilos = new JLabel("Numero max hilos");
        lblTxtNumeroMaxHilos.setBounds(10, 100, 200, 30);
        add(lblTxtNumeroMaxHilos);
        add(txtNumeroMaxHilos);
        tiempoConcurrente = new JLabel("Tiempo concurrente: 0");
        tiempoConcurrente.setBounds(10, 150, 250, 30);
        add(tiempoConcurrente);
        tiempoLineal= new JLabel("Tiempo lineak: 0");
        tiempoLineal.setBounds(250, 150, 250, 30);
        add(tiempoLineal);
        start = new JButton("Calcular");
        start.setBounds(200, 300, 100, 30);
        diferencia = new JLabel("Diferencia: 0");
        diferencia.setBounds(10, 200, 250, 30);
        add(diferencia);
        add(start);
        start.addActionListener(e -> calcular());
        txtNumeroMaxHilos.addActionListener(e -> cambiarNumeroHilos());
        txtNumeroMaxCuerpos.addActionListener(e -> cambiarNumeroCuerpos());
        txtNumeroCuerpos = new JTextField("10000");
        txtNumeroCuerpos.setBounds(10, 5, 200, 30);
        add(txtNumeroCuerpos);
        txtNumeroCuerpos.addActionListener(e -> {
            try {
                this.numeroCuerpos = Integer.parseInt(txtNumeroCuerpos.getText());
            }catch (Exception ex) {}
        });
        this.setVisible(true);

        /*/ Lineal
        calc = new Calc("10k.txt");
        long a = System.nanoTime();
        calc.calculate();
        System.out.println(System.nanoTime() - a);
        //*/

        // new Concurrente();
    }

    public void cambiarNumeroHilos() {
        try {
            int val = Integer.parseInt(txtNumeroMaxHilos.getText());
            this.numeroMaxHilos = val;
        } catch (Exception ex) {
            System.out.println("FAILING");
        }
    }
    public void cambiarNumeroCuerpos() {
        try {
            int val = Integer.parseInt(txtNumeroMaxCuerpos.getText());
            this.numeroMaxCuerposPorHilo = val;
        } catch (Exception ex) {
            System.out.println("FAILING");
        }
    }

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

    public static ArrayList<MassPoint> generateArrayList(int number) {
        ArrayList<MassPoint> tmp = new ArrayList<>();
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

    public void calcular() {
        System.out.println(numeroCuerpos);
        Calc calc = new Calc(generateArrayList(numeroCuerpos));
        long a = System.nanoTime();
        calc.calculate();
        long end = System.nanoTime();
        long tiempoL = end-a;
        this.tiempoLineal.setText("Tiempo lineal: " + String.valueOf((end - a)));
        //*/
        ArrayList<MassPoint> massPoints = generateArrayList(numeroCuerpos);
        try {
            String ip = InetAddress.getLocalHost().getHostAddress();
            System.out.println(ip);
            BarycentreRMI b1 = (BarycentreRMI) Naming.lookup("//" + ip + ":" +1223+ "/b");
            BarycentreRMI b2 = (BarycentreRMI) Naming.lookup("//" + ip + ":" +1223+"/b");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //*/
    }

}

