package Image;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        try {
            ImageFirst.runTests("komagome");
        } catch (NullPointerException e) {
            System.out.println("NULL POINTER EXCEPTION IN IMAGE_FIRST! SKIPPING!");
        }

        Thread.sleep(10_000);
        try {
            ImageSecond.runTests("komagome");
        } catch (NullPointerException e) {
            System.out.println("NULL POINTER EXCEPTION IN IMAGE_SECOND! SKIPPING!");
        }

        Thread.sleep(10_000);
        try {
            ImageThird.runTests("komagome");
        } catch (NullPointerException e) {
            System.out.println("NULL POINTER EXCEPTION IN IMAGE_THIRD! SKIPPING!");
        }

        Thread.sleep(10_000);
        try {
            ImageFourth.runTests("komagome");
        } catch (NullPointerException e) {
            System.out.println("NULL POINTER EXCEPTION IN IMAGE_FOURTH! SKIPPING!");
        }
    }
}
