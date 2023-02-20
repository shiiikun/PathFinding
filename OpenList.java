package PathFinding;

import java.util.HashMap;
import java.util.NoSuchElementException;

public class OpenList {
    private AStarNode[] backingArray;
    private HashMap<AStarNode, Integer> backingMap;
    private int capacity;
    private int count;

    public int getCount(){
        return count;
    }

    public OpenList(int capacity)
    {
        this.capacity = capacity;
        backingArray = new AStarNode[this.capacity];
        backingMap = new HashMap<AStarNode, Integer>(this.capacity);
        count = 0;
    }

    private static int getParent(int index)
    {
        return (index - 1) / 2;
    }

    private static int getLeft(int index){
        return 2 * index + 1;
    }

    private static int getRight(int index){
        return 2 * index + 2;
    }

    private void resize()
    {
        capacity *= 2;
        var newArray = new AStarNode[capacity];
        System.arraycopy(backingArray, 0, newArray, 0, backingArray.length);
        backingArray = newArray;
    }

    private void swap(int lhs, int rhs)
    {
        if (lhs >= count || rhs >= count)
            throw new IllegalArgumentException("Index out of range.");

        var leftNode = backingArray[lhs];
        var rightNode = backingArray[rhs];
        backingArray[lhs] = rightNode;
        backingArray[rhs] = leftNode;
        backingMap.put(leftNode, rhs);
        backingMap.put(rightNode, lhs);
    }

    private void upHeap(int index)
    {
        while (index != 0 && backingArray[index].compareTo(backingArray[getParent(index)]) < 0)
        {
            swap(index, getParent(index));
            index = getParent(index);
        }
    }

    private void heapify(int index)
    {
        var lhs = getLeft(index);
        var rhs = getRight(index);
        var smallest = index;
        if (lhs < count && backingArray[lhs].compareTo(backingArray[smallest]) < 0)
            smallest = lhs;
        if (rhs < count && backingArray[rhs].compareTo(backingArray[smallest]) < 0)
            smallest = rhs;
        if (smallest == index)
            return;

        swap(index, smallest);
        heapify(smallest);
    }

    private void insert(AStarNode newNode)
    {
        if (count == capacity)
            resize();

        backingArray[count] = newNode;
        backingMap.put(newNode, count);
        upHeap(count);
        count++;
    }

    private boolean replace(int index, AStarNode newNode)
    {
        if (index >= count)
            throw new IllegalArgumentException("Index out of range.");

        var oldNode = backingArray[index];
        if (oldNode.compareTo(newNode) < 0)
            return false;

        backingArray[index] = newNode;
        upHeap(index);
        return true;
    }

    public AStarNode remove()
    {
        if (count == 0)
            throw new NoSuchElementException("Open list is empty.");

        count--;
        var ret = backingArray[0];
        backingMap.remove(ret);
        if (count != 0)
        {
            backingArray[0] = backingArray[count];
            backingMap.put(backingArray[0], 0);
            heapify(0);
        }
        return ret;
    }

    public boolean addOrDecrease(AStarNode newNode)
    {
        var index = backingMap.getOrDefault(newNode, -1);
        if (index != -1)
            return replace(index, newNode);

        insert(newNode);
        return true;
    }
}
