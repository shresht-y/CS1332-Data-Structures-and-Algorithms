public class addTwo {
    public static void main(String[] args) {

    }

    public int[] addTwo(int[] input) {
        for (int i = 0; i < input.length; i++) {
            if (i % 2 == 0) {
                newArray[i] = input[i] + 2;

            } else {
                newArray[i] = input[i];
            }
        }
        return newArray;
    }

}
