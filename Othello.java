public class Othello {

    public static void main(String[] argv){

        if(argv.length != 2){
            System.err.println("Program should have 2 arguments. Position string and a time limit");
            return;
        }else if(argv[0].length() != 65){
            System.err.println("The position string has an invalid length");
            return;
        }

        long timeLimit = System.currentTimeMillis() + ((long) Integer.parseInt(argv[1])) * 1000 - 100;
        OthelloPosition pos = new OthelloPosition(argv[0]);

        int depth = 0;
        AlphaBeta alphaBeta = new AlphaBeta(depth, timeLimit);
        OthelloAction newAction;
        OthelloAction selectedAction = new OthelloAction(0,0,true);

        //Search for better moves with increased depth until the time limit
        while (System.currentTimeMillis() < alphaBeta.timeLimit) {
            alphaBeta.setSearchDepth(depth++);
            newAction = alphaBeta.evaluate(pos);
            if(newAction.row != 9){
                selectedAction = newAction;
            }else{
                break;
            }
        }
        selectedAction.print();
    }
}
