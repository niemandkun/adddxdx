package tech.niemandkun.opengl.math;

public class Quaternion {
    private float r;
    private float i;
    private float j;
    private float k;

    private Quaternion(float r, float i, float j, float k) {
        this.r = r;
        this.i = i;
        this.j = j;
        this.k = k;
    }

    public Quaternion dot(Quaternion v) {
        Quaternion u = this;

        return new Quaternion(
                u.r * v.r - u.i * v.i - u.j * v.j - u.k * v.k,
                u.r * v.i + u.i * v.r + u.j * v.k - u.k * v.j,
                u.r * v.j - u.i * v.k + u.j * v.r + u.k * v.i,
                u.r * v.k + u.i * v.j - u.j * v.i + u.k * v.r
        );
    }

    public Vector3 apply(Vector3 vector) {
        Quaternion vec = new Quaternion(0, vector.getX(), vector.getY(), vector.getZ());
        Quaternion rot = dot(vec).dot(inverse());
        return new Vector3(rot.i, rot.j, rot.k);
    }

    public Quaternion rotateX(float x) {
        return dot(fromEulerAngles(x, 0, 0));
    }

    public Quaternion rotateY(float y) {
        return dot(fromEulerAngles(0, y, 0));
    }

    public Quaternion rotateZ(float z) {
        return dot(fromEulerAngles(0, 0, z));
    }

    public Quaternion rotate(float x, float y, float z) {
        return dot(fromEulerAngles(x, y, z));
    }

    public Quaternion rotate(Vector3 eulerAngles) {
        return dot(fromEulerAngles(eulerAngles));
    }

    public Quaternion inverse() {
        return new Quaternion(r, -i, -j, -k);
    }

    public float norm() {
        return (float) Math.sqrt(1 / (r * r + i * i + j * j + k * k));
    }

    public Matrix4 getRotationMatrix() {
        float s = 1 / (r * r + i * i + j * j + k * k);

        return new Matrix4(
                1 - 2 * s * (j * j + k * k), 2 * s * (i * j - k * r), 2 * s * (i * k + j * r), 0,
                2 * s * (i * j + k * r), 1 - 2 * s * (i * i + k * k), 2 * s * (j * k - i * r), 0,
                2 * s * (i * k - j * r), 2 * s * (j * k + i * r), 1 - 2 * s * (i * i + j * j), 0,
                0, 0, 0, 1
        );
    }

    public static Quaternion fromEulerAngles(Vector3 eulerAngles) {
        return fromEulerAngles(eulerAngles.getX(), eulerAngles.getY(), eulerAngles.getZ());
    }

    public static Quaternion fromEulerAngles(float x, float y, float z) {
        double cosZ = Math.cos(z * 0.5);
        double sinZ = Math.sin(z * 0.5);
        double cosX = Math.cos(x * 0.5);
        double sinX = Math.sin(x * 0.5);
        double cosY = Math.cos(y * 0.5);
        double sinY = Math.sin(y * 0.5);

        double r = cosZ * cosX * cosY + sinZ * sinX * sinY;
        double i = cosZ * sinX * cosY - sinZ * cosX * sinY;
        double j = cosZ * cosX * sinY + sinZ * sinX * cosY;
        double k = sinZ * cosX * cosY - cosZ * sinX * sinY;

        return new Quaternion((float) r, (float) i, (float) j, (float) k);
    }

    public Vector3 toEulerAngles() {
        double t0 = 2 * (r * i + j * k);
        double t1 = 1 - 2 * (i * i + j * j);
        double x = Math.atan2(t0, t1);

        double t2 = 2 * (r * j - k * i);
        t2 = t2 > 1 ? 1 : t2;
        t2 = t2 < -1 ? -1 : t2;
        double y = Math.asin(t2);

        double t3 = 2 * (r * k + i * j);
        double t4 = 1 - 2 * (j * j + k * k);
        double z = Math.atan2(t3, t4);

        return new Vector3((float) x, (float) y, (float) z);
    }
}
