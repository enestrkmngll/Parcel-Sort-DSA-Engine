package DataStructures;

public class TerminalRotator {
    private static class TerminalNode {
        String cityName;
        TerminalNode next;

        TerminalNode(String cityName) {
            this.cityName = cityName;
            this.next=null;
        }
    }

    private TerminalNode head;
    private TerminalNode current;
        
    public TerminalRotator(String[] cityList) {
        initializeFromCityList(cityList);
    }

    public void initializeFromCityList(String[] cityArray){
        if (cityArray == null) {
            head = null;
            current = null;
                return;
        }

        head = null;
        current = null; 

        head = new TerminalNode(cityArray[0]);
        current = head;
            
        TerminalNode prev = head;
        for (int i = 1; i < cityArray.length; i++) {
            TerminalNode newNode = new TerminalNode(cityArray[i]);
            prev.next = newNode;
            prev = newNode;
                
        }
        prev.next = head;
    }

    public void advanceTerminal() { 
        if (current != null) {
            current = current.next;
                
        }
    }

    public String getActiveTerminal() { 
        return (current != null) ? current.cityName : null;
    }    
}
