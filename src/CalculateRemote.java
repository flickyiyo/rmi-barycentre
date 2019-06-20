import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class CalculateRemote extends ForkJoinPool {
    BarycentreRMI b1, b2;
    ArrayList<MassPoint> m1, m2;
    public CalculateRemote(BarycentreRMI b1, BarycentreRMI b2, ArrayList<MassPoint> m1, ArrayList<MassPoint> m2) {
        this.b1 = b1;
        this.b2 = b2;
        this.m1 = m1;
        this.m2 = m2;
        LLamadas l = new LLamadas(true, null, null);
    }
    private class LLamadas extends RecursiveAction {
        private boolean flag = true;
        public boolean isprimary = false;
        public MassPoint mp;
        BarycentreRMI b;
        ArrayList<MassPoint> mpts;
        public LLamadas(boolean flag, BarycentreRMI b, ArrayList<MassPoint> mpts) {
            this.flag = flag;
            this.b = b;
            this.mpts = mpts;
        }
        @Override
        protected void compute() {
            if (flag) {
                LLamadas l1 = new LLamadas(false, b1, m1);
                LLamadas l2 = new LLamadas(false, b2, m2);
                invokeAll(l1, l2);
                ArrayList<MassPoint> massPoints = new ArrayList<>();
                massPoints.add(l1.mp);
                massPoints.add(l2.mp);
                mp = new Calc(massPoints).calculate();
            } else {
                try {
                    mp = b.calculateBarycentre(this.mpts);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
