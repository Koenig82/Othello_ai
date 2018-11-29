public class Othello {

    public static void main(String[] argv){
        OthelloPosition pos;
        //System.out.println(Integer.parseInt(argv[1]));
        if(argv.length > 1){
            pos = new OthelloPosition(argv[0]);
        }else{
            //pos= new OthelloPosition("WEEEEEEEEEEEEEEEEEEEEEEEEEEXEEEEEEEEXOEEEEEEEEEEEEEEEEEEEEEEEEEEE");
            //pos= new OthelloPosition("WEEEEEEEEEEEEEEEEEEEEEEEEEEEOXEEEEEEXOEEEEEEEEEEEEEEEEEEEEEEEEEEE");
            pos = new OthelloPosition("WOOOOOOOEEXXOXXXEEXEOOEEEXEEOOEEEEEEOOEEEEEEOEEEEEEEEEEEEEEEEEEEE");
        }

        //pos.initialize();
        //pos.illustrate();

        AlphaBeta algorithm = new AlphaBeta();
        OthelloAction bestAction = algorithm.evaluate(pos);
        bestAction.print();
        /*try {
            if(!bestAction.isPassMove()){
                System.out.println(pos.getMoves());
                System.out.println(pos.playerToMove);
                pos = pos.makeMove(bestAction);

            }else{
                if(pos.toMove()){
                    pos.playerToMove = false;
                }else{
                    pos.playerToMove = true;
                }
            }
        } catch (IllegalMoveException e) {
            e.printStackTrace();
        }
        pos.illustrate();

        for(int i = 0;i<16;i++){
            bestAction = algorithm.evaluate(pos);
            bestAction.print();
            try {
                if(i == 1){
                    System.out.println(pos);
                }
                if(!bestAction.isPassMove()){
                    System.out.println(pos.getMoves());
                    System.out.println(pos.playerToMove);
                    pos = pos.makeMove(bestAction);
                }else{
                    if(pos.toMove()){
                        pos.playerToMove = false;
                    }else{
                        pos.playerToMove = true;
                    }
                }
            } catch (IllegalMoveException e) {
                e.printStackTrace();
            }
            pos.illustrate();
        }*/

        //System.out.println("("+bestAction.row+","+bestAction.column+")");


        /*LinkedList<OthelloAction> actions = pos.getMoves();

        System.out.println(actions);
        try {
            OthelloPosition newMove = pos.makeMove(actions.getLast());
            newMove.illustrate();
            for(int i = 0; i < 5;i++){
                actions = newMove.getMoves();
                System.out.println(actions);

                newMove = newMove.makeMove(actions.getFirst());
                newMove.illustrate();
            }
            actions = newMove.getMoves();
            System.out.println(actions);

        } catch (IllegalMoveException e) {
            e.printStackTrace();
        }*/
    }
}
