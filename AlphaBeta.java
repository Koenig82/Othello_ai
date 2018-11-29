import java.util.LinkedList;

public class AlphaBeta implements OthelloAlgorithm {
    int depth = 4;
    Evaluator evaluator = new Evaluator();
    @Override
    public void setEvaluator(OthelloEvaluator evaluator) {

    }

    @Override
    public OthelloAction evaluate(OthelloPosition position){

        LinkedList<OthelloAction> actions = position.getMoves();
        OthelloAction bestAction = new OthelloAction(0,0);

        if(position.toMove()){
            bestAction.setValue(Integer.MIN_VALUE);
        }else{
            bestAction.setValue(Integer.MAX_VALUE);
        }
        if(actions.isEmpty()){
            bestAction.setPassMove(true);
            return bestAction;
        }
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        if(position.toMove()){
            int value = Integer.MIN_VALUE;
            for (OthelloAction action : actions) {
                try {
                    //simulera moves från MAX
                    OthelloPosition newMinState = position.makeMove(action);
                    value = Integer.max(value, maximum(newMinState, depth, alpha, beta));
                    alpha = Integer.max(alpha, value);
                    if(value > bestAction.getValue()){
                        bestAction = action;
                        bestAction.setValue(value);
                    }
                } catch (IllegalMoveException e) {
                    e.printStackTrace();
                }
            }
            return bestAction;
        }else{

            int value = Integer.MAX_VALUE;
            for (OthelloAction action : actions) {
                try {
                    //simulera moves från MIN
                    OthelloPosition newMinState = position.makeMove(action);
                    value = Integer.min(value, minimum(newMinState, depth, alpha, beta));
                    beta = Integer.min(beta, value);
                    if(value < bestAction.getValue()){
                        bestAction = action;
                        bestAction.setValue(value);
                    }
                    if(alpha >= beta){
                        break;
                    }
                } catch (IllegalMoveException e) {
                    e.printStackTrace();
                }
            }
            return bestAction;
        }


    }

    public int maximum(OthelloPosition position,int depth, int alpha, int beta){
        LinkedList<OthelloAction> actions = position.getMoves();
        if(depth == 0){
            return evaluator.evaluate(position);
        }else if( actions.isEmpty()){
            return 5000;
        }

        int value = Integer.MAX_VALUE;
        //depth--;
        for (OthelloAction action : actions) {
            try {
                //simulera moves från MIN
                OthelloPosition newMinState = position.makeMove(action);
                value = Integer.min(value, minimum(newMinState, depth-1, alpha, beta));
                beta = Integer.min(beta, value);
                if(alpha >= beta){
                    break;
                }
            } catch (IllegalMoveException e) {
                e.printStackTrace();
            }
        }
        return value;


    }

    public int minimum(OthelloPosition position,int depth, int alpha, int beta){
        LinkedList<OthelloAction> actions = position.getMoves();
        if(depth == 0){
            return evaluator.evaluate(position);
        }else if( actions.isEmpty()){
            return -5000;
        }

        int value = Integer.MIN_VALUE;
        //depth--;
        for (OthelloAction action : actions) {
            try {
                //simulera moves från MAX
                OthelloPosition newMinState = position.makeMove(action);
                value = Integer.max(value, maximum(newMinState, depth-1, alpha, beta));
                alpha = Integer.max(alpha, value);
                if(alpha >= beta){
                    break;
                }
            } catch (IllegalMoveException e) {
                e.printStackTrace();
            }
        }
        return value;

    }


    @Override
    public void setSearchDepth(int depth) {
        this.depth = depth;
    }

}
