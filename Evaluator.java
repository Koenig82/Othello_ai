public class Evaluator implements OthelloEvaluator {

    int coinValue;
    int mobility;
    int edgeValue;
    int stability;
    @Override
    public int evaluate(OthelloPosition position) {
        coinValue = 0;
        edgeValue = 0;
        int white = 0;
        int black = 0;
        //position.illustrate();
        //int playerMovesNumberScore = position.getMoves().size();

        /*OthelloPosition opponentPosition = position.clone();
        if(opponentPosition.getMoves().isEmpty()){
            //if()
            return 0;
        }*/

        for(int i = 1; i <= position.BOARD_SIZE; i++){
            for(int j = 1; j <= position.BOARD_SIZE;j++){
                if(position.board[i][j] == 'W'){
                    white++;
                    white = addEdgeScore(white, i, j);
                }else if(position.board[i][j] == 'B'){
                    black++;
                    black = addEdgeScore(black, i, j);
                }
            }
        }

        return white-black;
    }

    private int addEdgeScore(int counter, int i, int j) {
        if((i == 1 || i == 8) && (j == 1 || j == 8)){
            counter += 10;
        }
        else if(i == 1 || i == 8){
            counter += 3;
        }
        else if(j == 1 || j == 8){
            counter += 3;
        }
        return counter;
    }

}
