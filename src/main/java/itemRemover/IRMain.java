package itemRemover;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class IRMain extends JavaPlugin {

    public static String prefix = ChatColor.AQUA+"[ItemRemover] "+ChatColor.RESET;

    private ItemLoader itemLoader;
    private InventoryFilterListener inventoryFilterListener;

    @Override
    public void onEnable(){
        inventoryFilterListener = new InventoryFilterListener();
        getServer().getPluginManager().registerEvents(inventoryFilterListener, this);

        itemLoader = new ItemLoader(this, inventoryFilterListener);
        itemLoader.load();

    }

    @Override
    public void onDisable(){

    }
}
