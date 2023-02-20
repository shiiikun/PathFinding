package PathFinding;

import java.util.*;

public class Algorithm {

    public static Position[] AStar(Position start, Position end, HashMap<Position, List<Position>> graph){
        var visitedPositions = new HashSet<Position>();
        var openList = new OpenList(36);
        var startNode = new AStarNode(start, null, end);
        openList.addOrDecrease(startNode);

        while (openList.getCount() > 0){
            var currNode = openList.remove();
            var currPosition = currNode.position;
            if (currPosition.equals(end)){
                Stack<Position> pathStack = new Stack<>();
                while (currNode != null){
                    pathStack.push(currNode.position);
                    currNode = currNode.parent;
                }

                var path = new Position[pathStack.size()];
                for (int i = 0; i < path.length; ++i){
                    path[i] = pathStack.pop();
                }
                return path;
            }

            visitedPositions.add(currPosition);
            var currNeighbors = graph.getOrDefault(currPosition, null);
            if (currNeighbors == null)
                continue;

            for (var position : currNeighbors){
                if (visitedPositions.contains(position))
                    continue;

                var node = new AStarNode(position, currNode, end);
                openList.addOrDecrease(node);
            }
        }

        return null;
    }
}
