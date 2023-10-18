public class Main {

    public static void main(String[] args) {
        var parser = new ArgParser(args);
        var wichtler = new Wichtler(parser.getSenderAddress(), parser.getPassword(), parser.getUsers());

//        wichtler.setDebug(true);
        wichtler.process();
    }
}
