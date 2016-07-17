package itemRemover;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class InventoryFilterListener implements Listener {

    private ArrayList<ItemStack> itemsToRemove = new ArrayList<ItemStack>();

    public void markItemForRemoval(ItemStack item){
        itemsToRemove.add(item);

        Bukkit.getConsoleSender().sendMessage(IRMain.prefix+ChatColor.GREEN+"Marked " + ChatColor.DARK_GREEN + item + ChatColor.GREEN + " for removal");
    }

    public void clearRemovalList(){
        itemsToRemove.clear();
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event){

        Bukkit.getConsoleSender().sendMessage(IRMain.prefix+"Scanning inventory "+event.getInventory().getHolder());

        ItemStack[] inventoryContent = event.getInventory().getContents();

        for(ItemStack item: inventoryContent){
            if(shouldItemBeRemoved(item)){
                Bukkit.getConsoleSender().sendMessage(IRMain.prefix+"Found "+item+" removing it");
                event.getInventory().remove(item);
            }
        }
    }

    private boolean shouldItemBeRemoved(ItemStack itemInInventory){
        for(ItemStack removableItem: itemsToRemove){

            if(itemInInventory != null && itemInInventory.getType() == removableItem.getType()){
                ItemMeta removableMeta = removableItem.getItemMeta();
                ItemMeta inInventoryMeta = itemInInventory.getItemMeta();

                if(inInventoryMeta.getEnchants().equals(removableMeta.getEnchants())){

                    if(inInventoryMeta.hasLore() && removableMeta.hasLore()){

                        if(inInventoryMeta.getLore().equals(removableMeta.getLore())){
                            return true;
                        }
                    }else if(!inInventoryMeta.hasLore() && !removableMeta.hasLore()){
                        System.out.println(""+removableMeta.getLore());
                        return true;
                    }
                }
            }
        }

        return false;
    }

}
