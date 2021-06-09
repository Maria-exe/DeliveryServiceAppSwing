import bll.BaseProduct;
import bll.CompositeProduct;
import bll.DeliveryService;
import bll.MenuItem;
import java.util.HashSet;


public class TestClass {
    public static void main(String[] args) {
        BaseProduct bp = new BaseProduct("sd",23,1,1,1,1,1);
        CompositeProduct cp = new CompositeProduct("daily  menu one");
        HashSet<MenuItem> items = new HashSet<MenuItem>();
        items.add(bp);
        items.add(cp);
        if (items.iterator().hasNext()) {
            if( items.iterator().next() instanceof BaseProduct)
                System.out.println(((BaseProduct) items.iterator().next()).stringToPrint());
        }

        DeliveryService deliveryService = new DeliveryService();
        //deliveryService.addProduct(bp);
       // ArrayList<MenuItem> a = new ArrayList<>(deliveryService.getMenuItems());
       // BaseProduct b =  (BaseProduct) a.get(0);
       // System.out.println(b.stringToPrint());

        deliveryService.importProducts();
        deliveryService.filterProducts(0, 10000, "Calories").stream().forEach(menuItem -> System.out.println(((BaseProduct)menuItem).stringToPrint()));

    }
}
