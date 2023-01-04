import list.LinkedList;

public class Main {
    public static void main(String[] args) {

        LinkedList<String> customList = new LinkedList<>();
        customList.add("1");
        customList.add("2");
        customList.add("3");
        customList.add("4");

        System.out.println(customList);

        customList.removeAt(2);

        System.out.println("remove item: " + customList);

    }
}