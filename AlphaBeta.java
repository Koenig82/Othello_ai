import java.util.LinkedList;

public class AlphaBeta implements OthelloAlgorithm {

    private int depth;
    long timeLimit;
    private Evaluator evaluator = new Evaluator();

    public AlphaBeta(int depth, long timeLimit) {

        this.depth = depth;
        this.timeLimit = timeLimit;
    }

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
                    //simulate moves from maximum node
                    OthelloPosition newMinState = position.makeMove(action);
                    value = Integer.max(value, maximum(newMinState, depth, alpha, beta));
                    if(System.currentTimeMillis() > timeLimit){
                        return new OthelloAction(9,9);
                    }
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
                    //simulate moves from minimum node
                    OthelloPosition newMinState = position.makeMove(action);
                    value = Integer.min(value, minimum(newMinState, depth, alpha, beta));
                    if(System.currentTimeMillis() > timeLimit){
                        return new OthelloAction(9,9);
                    }
                    beta = Integer.min(beta, value);
                    if(value < bestAction.getValue()){
                        bestAction = action;
                        bestAction.setValue(value);
                    }
                } catch (IllegalMoveException e) {
                    e.printStackTrace();
                }
            }
            return bestAction;
        }
    }

    private int maximum(OthelloPosition position, int depth, int alpha, int beta){

        LinkedList<OthelloAction> actions = position.getMoves();

        if(depth == 0 || actions.isEmpty()){
            return evaluator.evaluate(position);
        }

        int value = Integer.MAX_VALUE;
        for (OthelloAction action : actions) {
            try {
                //simulate moves from minimum node
                OthelloPosition newMinState = position.makeMove(action);
                value = Integer.min(value, minimum(newMinState, depth-1, alpha, beta));
                if(System.currentTimeMillis() > timeLimit){
                    return 0;
                }
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

    private int minimum(OthelloPosition position, int depth, int alpha, int beta){

        LinkedList<OthelloAction> actions = position.getMoves();

        if(depth == 0 || actions.isEmpty()){
            return evaluator.evaluate(position);
        }

        int value = Integer.MIN_VALUE;
        for (OthelloAction action : actions) {
            try {
                //simulate moves from maximum node
                OthelloPosition newMinState = position.makeMove(action);
                value = Integer.max(value, maximum(newMinState, depth-1, alpha, beta));
                if(System.currentTimeMillis() > timeLimit){
                    return 0;
                }
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
