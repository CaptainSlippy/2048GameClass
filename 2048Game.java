import java.util.*;
public class Game {
    private boolean myWin = false;
    private boolean myLose = false;
    public int myScore = 0;
    private String key="";
    Board board =  new Board();
    
    public void left() {
        key="left";
        boolean needAddTile = false;
        for (int i = 0; i < 4; i++) {
            Tile[] line = board.getLine(i);
            Tile[] merged = mergeLine(moveLine(line));
            board.setLine(i, merged);
            if (!needAddTile && !compare(line, merged)) {
                needAddTile = true;
            }
        }
        if (needAddTile) {
            addTile();
        }
    }
    
    public void right() {
        key="right";
        boolean needAddTile = false;
        for (int i = 0; i < 4; i++) {
            Tile[] line = board.getLine(i);
            Tile[] merged = mergeLine(moveLine(line));
            board.setLine(i, merged);
            if (!needAddTile && !compare(line, merged)) {
                needAddTile = true;
            }
        }
        if (needAddTile) {
            addTile();
        }
    }
    
    public void up() {
        key="up";
        boolean needAddTile = false;
        for(int i =0; i< 4;i++){
            Tile[] line = board.up_getLine(i);
            Tile[] merged= mergeLine(moveLine(line));
            if(!needAddTile && !compare(line, merged)) {
                needAddTile = true;
            }
            board.up_setLine(i,merged);
        }
        if(needAddTile){
            addTile();
        }
   }    
    
    public void down() {
        key="down";
        boolean needAddTile = false;
        for(int i =0; i< 4;i++){
            Tile[] line = board.down_getLine(i);
            Tile[] merged= mergeLine(moveLine(line));
            if(!needAddTile && !compare(line, merged)) {
            needAddTile = true;
            }
            board.down_setLine(i,merged);
        }
        if(needAddTile){
            addTile();
        }
    }  
    
    private void addTile() {
        List<Tile> list = board.availableSpace();
        if (!board.availableSpace().isEmpty()) {
            int index = (int) (Math.random() * list.size()) % list.size();
        Tile emptyTile = list.get(index);
        emptyTile.value = Math.random() < 0.9 ? 2 : 4;
        }
    }
    
    private boolean isFull() {
        return board.availableSpace().size() == 0;
    }
    
    public boolean canMove() {
        if (!isFull()) {
            return true;
        }
        
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                Tile t = board.tileAt(x, y);
                if ((x < 3 && t.value == board.tileAt(x + 1, y).value)|| ((y < 3) && t.value == board.tileAt(x, y + 1).value)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean compare(Tile[] line1, Tile[] line2) {
       for (int i = 0; i < line1.length; i++) {
            if (line1[i].value != line2[i].value) {
                return false;
            }
        }
        return true;
    }
    
    
    private Tile[] moveLine(Tile[] oldLine) {
        LinkedList<Tile> l = new LinkedList<Tile>();
        for (int i = 0; i < 4; i++) {
            if (!oldLine[i].isEmpty())
                l.addLast(oldLine[i]);
            }
        if (l.size() == 0) {
            return oldLine;
            } 
        else {
            Tile[] newLine = new Tile[4];
            ensureSize(l, 4);
            for (int i = 0; i < 4; i++) {
                newLine[i] = l.removeFirst();
            }
            return newLine;
            }
        }
    
    private Tile[] mergeLine(Tile[] oldLine) {
        LinkedList<Tile> list = new LinkedList<Tile>();
        for (int i = 0; i < 4 && !oldLine[i].isEmpty(); i++) {
            int num = oldLine[i].value;
            if (i < 3 && oldLine[i].value == oldLine[i + 1].value) {
                num *= 2;
                myScore += num;
                int ourTarget = 2048;
                if (num == ourTarget) {
                    myWin = true;
                }
                i++;
            }
            list.add(new Tile(num));
        }
        if (list.size() == 0) {
            return oldLine;
        } 
        else {
            if(key.equals("right"))
                ensureSize(list,4,0);
            else
                ensureSize(list,4);
            return list.toArray(new Tile[4]);
        }
    }
    
    
    private void ensureSize(java.util.List<Tile> l, int s) {
        while (l.size() != s) {
            l.add(new Tile());
        }
    }
    
    private void ensureSize(java.util.List<Tile> l, int s,int p) {
        while (l.size() != s) {
            l.add(p,new Tile());
        }
    }
    
        public boolean getWin() {
    	return myWin;
    }
    
    public boolean getLose() {
    	return myLose;
    }
    
    public void setWin(boolean b) {
    	myWin = b;
    }
    
    public void setLose(boolean b) {
    	myLose = b;
    }
    
    public void resetGame() {
        myScore = 0;
        myWin = false;
        myLose = false;
        board = new Board();
        addTile();
        addTile();
    }
    
    public Tile[] getBoard() {
    	return board.getTiles();
    }
}

public class Board {

	private Tile[] myTiles;
	
	public Board() {
		myTiles = new Tile[4 * 4];
		for (int i = 0; i < myTiles.length; i++) {
			myTiles[i] = new Tile();
		}
	}
	
    public Tile[] getLine(int index) {
        Tile[] result = new Tile[4];
        for (int i = 0; i < 4; i++) {
            result[i] = tileAt(i, index);
        }
        return result;
    }
    
    public void setLine(int index, Tile[] re) {
        System.arraycopy(re, 0, myTiles, index * 4, 4);
    }
    
    public Tile[] up_getLine(int index) {
        Tile[] result = new Tile[4];
        for (int i = 0; i < 4; i++) {
            result[i] = tileAt(index,i);
        }
        return result;
    }
    
    public void up_setLine(int index, Tile[] re) {
        for(int i=0;i<4;i++){
            myTiles[(index)+(i*4)].value=re[i].value;
        }    
    }
    
    public Tile[] down_getLine(int index) {
        Tile[] result = new Tile[4];
        for (int i = 0; i < 4; i++) {
            result[i] = tileAt(index,3-i);
        }
        return result;
        }
    
    public void down_setLine(int index, Tile[] re) {
        for(int i=0;i<4;i++){
            myTiles[(index)+(i*4)].value=re[3-i].value;
        }    
    }
    
    public Tile tileAt(int x, int y) {
        return myTiles[x + y * 4];
    }
    
    public List<Tile> availableSpace() {
        final List<Tile> list = new ArrayList<Tile>(16);
        for (Tile t : myTiles) {
            if (t.isEmpty()) {
                list.add(t);
            }
        }
        return list;
    }
    
	public Tile[] getTiles() {
		return myTiles;
	}
	
}
