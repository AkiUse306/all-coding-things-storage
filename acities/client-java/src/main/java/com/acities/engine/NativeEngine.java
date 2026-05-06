package com.acities.engine;

public final class NativeEngine {
    static {
        try {
            System.loadLibrary("acities_engine");
        } catch (UnsatisfiedLinkError error) {
            System.err.println("[acities] Native engine library failed to load: " + error.getMessage());
        }
    }

    private NativeEngine() {
    }

    public static native void initialize();
    public static native void tick(double deltaSeconds);
    public static native void shutdown();
    public static native void setObstacle(int x, int y, boolean obstacle);
    public static native float[] findPath(float startX, float startY, float goalX, float goalY);
}
