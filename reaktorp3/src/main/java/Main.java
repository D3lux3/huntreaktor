import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {


    private static Node[][] space = new Node[500][500];
    private static Node start;
    private static ArrayList<Node> goals = new ArrayList<>();

    private static void initNeuralNet() {
        for (int y = 0; y < space.length; y++) {
            for (int x = 0; x < space[0].length; x++) {
                space[y][x] = new Node(x,y, Type.EMPTY);
            }
        }
    }

    public static void main(String args[] ) {
        initNeuralNet();
        try {
            Scanner fileScanner = new Scanner(new File("data.txt"));
            while (fileScanner.hasNext()) {
                String line  = fileScanner.nextLine();
                String splitted[] = line.split(" ");
                String splittedRoute[] = (splitted.length > 1 ? splitted[1].split(",") : new String[]{"asd"});

                int x = Integer.valueOf(splitted[0].split(",")[0]);
                int y = Integer.valueOf(splitted[0].split(",")[1]);
                space[y][x] = new Node(x,y, Type.WALKABLE);
                for (String s : splittedRoute) {
                    switch (s) {
                        case "L":
                            x = x - 1;
                            break;
                        case "R":
                            x = x + 1;
                            break;
                        case "U":
                            y = y - 1;
                            break;
                        case "D":
                            y = y + 1;
                            break;
                        case "X":
                            space[y][x] = new Node(x,y, Type.WALL);
                            break;
                        case "S":
                            start = new Node(x,y, Type.START);
                            space[y][x] = new Node(x,y, Type.START);
                            break;
                        case "F":
                            goals.add(new Node(x,y, Type.GOAL));
                            space[y][x] = new Node(x,y, Type.GOAL);;
                            break;
                    }
                    if (space[y][x].getType() == Type.EMPTY) {
                        space[y][x] = new Node(x,y, Type.WALKABLE);
                    }
                }

            }

        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
        printNeuralNet(space);
        findNeighbours();
        bfs();
        printNeuralRoute();
    }


    private static void connectNeighbours(Node first, Node second) {
        space[first.getY()][first.getX()].addNeighbour(second);
        space[second.getY()][second.getX()].addNeighbour(first);
    }

    private static boolean getType(int x, int y) {
        if (space[y][x].getType() == Type.START ||
                space[y][x].getType() == Type.GOAL ||
                space[y][x].getType() == Type.WALKABLE) {
            return true;
        }
        return false;
    }

    private static void findNeighbours() {
        for (int y = 0; y < space.length; y++) {
            for (int x = 0; x < space[0].length; x++) {
                if (checkParams(x,y) && getType(x, y)) {
                    if (checkParams(x + 1, y) &&  getType(x + 1,y)) {
                        connectNeighbours(space[y][x],space [y][x + 1]);
                    }

                    if (checkParams(x - 1, y) && getType(x - 1,y)) {
                        connectNeighbours(space[y][x],space [y][x - 1]);
                    }

                    if (checkParams(x, y + 1) && getType(x,y + 1)) {
                        connectNeighbours(space[y][x],space [y + 1][x]);
                    }

                    if (checkParams(x, y - 1) && getType(x,y - 1)) {
                        connectNeighbours(space[y][x],space [y - 1][x]);
                    }

                }
            }
        }
    }

    private static boolean checkParams(int x, int y) {
        if (x < 500 && x >= 0
                && y < 500 && y >= 0) {
            return true;
        }
        return false;
    }

    private static void printNeuralNet(Node[][] net) {
        for (int y = 0; y < net.length; y++) {
            for (int x = 0; x < net[0].length; x++) {
                System.out.print(net[y][x]);
            }
            System.out.println();
        }
    }

    private static void bfs() {
        boolean visited[][] = new boolean[500][500];
        Queue<Node> q = new ArrayDeque();
        q.add(space[start.getY()][start.getX()]);

        space[start.getY()][start.getX()].setDistance(0);
        visited[start.getY()][start.getX()] = true;

        while(!q.isEmpty()) {
            Node node = q.poll();

            for (Node neighbour : node.getNeighbours()) {
                if (visited[neighbour.getY()][neighbour.getX()]){
                    continue;
                }
                q.add(neighbour);
                visited[neighbour.getY()][neighbour.getX()] = true;
                neighbour.setParent(node);
                if (neighbour.getType() == Type.GOAL) {
                    System.out.println("Goal!! x: " + neighbour.getX() + " y: " + neighbour.getY());
                }
                neighbour.setDistance(node.getDistance() + 1);
            }

        }
    }

    private static void printNeuralRoute() {
        for (Node g : goals) {
            Node node = space[g.getY()][g.getX()];
            ArrayList<Node> route = new ArrayList<>();
            while (node != null) {
                route.add(node);
                node = node.getParent();
            }
            Collections.reverse(route);
            for (int i = 0; i < route.size(); i++) {
                if (i + 1 < route.size()) {
                    if (route.get(i).getX() + 1 == route.get(i + 1).getX()) {
                        System.out.print("R,");
                    }
                    if (route.get(i).getX() - 1 == route.get(i + 1).getX()) {
                        System.out.print("L,");
                    }
                    if (route.get(i).getY() + 1 == route.get(i + 1).getY()) {
                        System.out.print("D,");
                    }
                    if (route.get(i).getY() - 1 == route.get(i + 1).getY()) {
                        System.out.print("U,");
                    }
                }
            }

        }
    }


}
