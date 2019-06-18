import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class Concurrente extends ForkJoinPool {
    public List<MassPoint> puntos;
    public List<MassPoint> finalMassPoints;
    public int cuerposPorHilo = 5000;
    public int maxHilos = 100;
    public int cuentaHilos = 1;
    String fileName = "1M.txt";
    public long tiempoTotal = 0;
    public Concurrente(int cuerposPorHilo, int maxHilos, List<MassPoint> puntos) {
        this.cuerposPorHilo = cuerposPorHilo;
        this.maxHilos = maxHilos;
        this.fileName = fileName;
        this.puntos = puntos;
        finalMassPoints = new ArrayList<MassPoint>();
        long init = System.nanoTime();
        CalcTask calcTask1 = new CalcTask(puntos);
        invoke(calcTask1);
        calculate();
        long end = System.nanoTime() - init;
        this.tiempoTotal = end;
        System.out.println("ASD: " + tiempoTotal);

    }

    public Concurrente() {
        this.start();
    }

    public void start() {
        Calc cl = new Calc(fileName);
        finalMassPoints = new ArrayList<MassPoint>();
        this.puntos = cl.puntos;
        //CalcTask calcTask1 = new CalcTask((ArrayList<MassPoint>)puntos.subList(0, middle));

        //CalcTask calcTask2 = new CalcTask((ArrayList<MassPoint>)puntos.subList(middle + 1, puntos.size() -1));
        long init = System.nanoTime();
        CalcTask calcTask1 = new CalcTask(puntos);
        invoke(calcTask1);
        calculate();
        long end = System.nanoTime() - init;
        this.tiempoTotal = end;
        System.out.printf("ASD: " + tiempoTotal);

    }

    public void calculate() {
        while (puntos.size() > 1) {
            ArrayList<MassPoint> newPuntos = new ArrayList<>();
            // System.out.println("asdasD:" + puntos.size());
            for (int i = 0; i < puntos.size()-1; i+=2) {
                newPuntos = new ArrayList<>(newPuntos);
                MassPoint pto = Calc.avgPointsWeighted(puntos.get(i), puntos.get(i+1));
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


    private class CalcTask extends RecursiveAction {
        // private static final long serialVersionUID = -749935388568367268L;
        private List<MassPoint> massPoints;
        public MassPoint resultado;

        public CalcTask(List<MassPoint> massPoints) {
            this.massPoints = massPoints;
        }

        @Override
        protected void compute() {
            cuentaHilos++;
            //System.out.println(massPoints.size());
            if (this.massPoints.size() <= cuerposPorHilo || cuentaHilos + 2 >= maxHilos) {
                finalMassPoints.add(calculate());
                cuentaHilos--;
            } else {
                int middle = massPoints.size() / 2;
                CalcTask tsk1 = new CalcTask(massPoints.subList(0, middle));
                CalcTask tsk2 = new CalcTask(massPoints.subList(middle + 1, massPoints.size() - 1));
                invokeAll(tsk1, tsk2);
            }
        }

        public MassPoint calculate() {
            while (massPoints.size() > 1) {
                ArrayList<MassPoint> newPuntos = new ArrayList<>();
                // System.out.println("asdasD:" + puntos.size());
                for (int i = 0; i < massPoints.size()-1; i+=2) {
                    newPuntos = new ArrayList<>(newPuntos);
                    MassPoint pto = Calc.avgPointsWeighted(massPoints.get(i), massPoints.get(i+1));
                    newPuntos.add(pto );
                    //newPuntos.add(avgPointsWeighted(puntos.get(i), puntos.get(i+1)));
                }

                if (massPoints.size()%2!=0) {
                    newPuntos = new ArrayList<>(newPuntos);
                    newPuntos.add(massPoints.get(massPoints.size()-1));
                }

                massPoints = newPuntos;
            }
            return massPoints.get(0);
        }
    }
}
