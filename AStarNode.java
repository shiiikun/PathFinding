package PathFinding;

public class AStarNode implements Comparable<AStarNode> {
    private float gn;
    private float hn;

    public Position position;
    public AStarNode parent;

    public AStarNode(Position position, AStarNode parent, Position endPosition) {
        this.position = position;
        this.parent = parent;
        gn = (parent == null) ? 0 : (parent.gn + Position.Distance(position, parent.position));
        hn = Position.Distance(position, endPosition);
    }

    public float getFn(){
        return gn + hn;
    }

    @Override
    public int hashCode(){
        return position.hashCode();
    }

    @Override
    public boolean equals(Object other){
        if (other instanceof  AStarNode){
            var otherNode = (AStarNode) other;
            return position.equals(otherNode.position);
        }

        return false;
    }

    @Override
    public int compareTo(AStarNode other) {
        var deltaFn = getFn() - other.getFn();
        if (deltaFn > 0)
            return 1;
        else if (deltaFn < 0)
            return -1;
        return 0;
    }
}
