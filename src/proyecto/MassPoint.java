package proyecto;

public class MassPoint {
    private float x, y, z, masa;

    public MassPoint(float x, float y, float z, float masa) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.masa = masa;
    }

    public float getMasa() {
        return masa;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }
}
