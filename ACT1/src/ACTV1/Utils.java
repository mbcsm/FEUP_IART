package ACTV1;

import java.util.ArrayList;
import java.util.List;

class Utils {
    /**
     * Goes to the final node and recursevelly calls getParent()
     * by doing this it will build the move needed to get to the final position
     * @param currentNode - End Node
     * @return path - path to the final node
     */
    List<Node> getPath(Node currentNode) {
        System.out.println("PATH FOUND");
        List<Node> path = new ArrayList<>();
        path.add(currentNode);
        Node parent;
        while ((parent = currentNode.getParent()) != null) {
            path.add(0, parent);
            currentNode = parent;
        }

        path.remove(0);

        return path;
    }
}
