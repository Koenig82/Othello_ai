public class Othello {

    public static void main(String[] argv){

        long timeLimit = System.currentTimeMillis() + ((long) Integer.parseInt(argv[1])) * 1000 - 100;;
        OthelloPosition pos = new OthelloPosition(argv[0]);;

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
