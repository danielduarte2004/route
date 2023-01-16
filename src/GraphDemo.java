import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

/**
 * Demonstrates the calculation of shortest paths in the US Highway
 * network, showing the functionality of GraphProcessor and using
 * Visualize
 * To do: Add your name(s) as authors
 */
public class GraphDemo {
    public static void main(String[] args) throws Exception {
        Scanner s = new Scanner(System.in);
        
        System.out.println();
        System.out.println("Enter the name of the city you want to start with:");
        String firstCity = s.nextLine();
        
        System.out.println();
        System.out.println("Enter the name of the city you want to go to:");
        String secondCity = s.nextLine();
        
        GraphProcessor gp = new GraphProcessor();
        GraphDemo gd = new GraphDemo();
        gp.initialize(new FileInputStream(new File("data/usa.graph")));

        Point firstCityCoord = gd.getPoint(firstCity);
        Point secondCityCoord = gd.getPoint(secondCity);
        
        /** START TIME */
        long startTime = System.nanoTime();

        Point a = gp.nearestPoint(firstCityCoord);
        Point b = gp.nearestPoint(secondCityCoord);
        
        List<Point> bestPath = gp.route(a, b);
        double distance = gp.routeDistance(bestPath);
        
        long elapsedNanos = System.nanoTime() - startTime; 
        /** END TIME */

        System.out.println();
        System.out.println("Nearest point to " + firstCity + " is " + a);
        System.out.println("Nearest point to " + secondCity + " is " + b);
        System.out.printf("The total length of the route is %.2f miles\n", distance);
        System.out.printf("It took %.2f ms for the code to get nearest points, route, and get distance.\n", elapsedNanos/1000000.0);

        
        Visualize v = new Visualize("data/usa.vis", "images/usa.png");
        v.drawPoint(a);
        v.drawPoint(b);

        v.drawRoute(bestPath);
    }

    private Point getPoint(String name) throws Exception {
      Scanner reader;
      reader = new Scanner(new File("data/uscities.csv"));
      
      while (reader.hasNextLine()) {
        try {
          String[] info = reader.nextLine().split(",");
          String cityName = info[0] + "," + info[1];
          if(!name.equals(cityName))
            continue;
          double lat = Double.parseDouble(info[2]);
          double longitude = Double.parseDouble(info[3]);
          
          reader.close();
          return new Point(lat, longitude);
        } catch(Exception e) {
          continue;    
        }
      }

      return null;
    }
}