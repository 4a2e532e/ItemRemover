package itemRemover;

import org.bukkit.plugin.java.JavaPlugin;

public class IRMain extends JavaPlugin {

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
