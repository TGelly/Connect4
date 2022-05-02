package projet;

import java.util.Scanner;
import org.junit.*;

public class testfile{

    Grid G;
    Scanner scan = new Scanner(System.in);

    @Before
    public void setUp(){
        G = new Grid(6,7,scan);
    }

    // This test only checks if the Grid.initialize() method doesn't throw any Exception
    @Test
    public void testInitialize(){
        G.initialize();
    }
}