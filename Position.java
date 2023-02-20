package PathFinding;

public class Position {
    private final int x;
    private final int y;
    private final int z;

    public Position(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public int hashCode(){
        return x ^ y << 4 ^ y >> 28 ^ z >> 4 ^ z << 28;
    }

    @Override
    public boolean equals(Object other ){
        if (other instanceof Position){
            var otherPosition = (Position) other;
            return x == otherPosition.x && y == otherPosition.y && z == otherPosition.z;
        }

        return false;
    }

    public static float Distance(Position lhs, Position rhs){
        var deltaX = lhs.x - rhs.x;
        var deltaY = lhs.y - rhs.y;
        var deltaZ = lhs.z - rhs.z;
        return (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
    }
}
