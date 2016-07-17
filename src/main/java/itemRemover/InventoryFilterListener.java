package itemRemover;

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

        System.out.println("Marked "+item+" for removal");
    }

    public void clearRemovalList(){
        itemsToRemove.clear();
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event){

        System.out.println("Scanning inventory "+event.getInventory().getHolder());

        ItemStack[] inventoryContent = event.getInventory().getContents();

        for(ItemStack item: inventoryContent){
            if(shouldItemBeRemoved(item)){
                System.out.println("Found "+item+" removing it");
                item.setType(Material.STICK);
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
                        System.out.println("Lore is present");

                        if(inInventoryMeta.getLore().equals(removableMeta.getLore())){
                            System.out.println("Lore matches");
                            return true;
                        }
                    }else if(!inInventoryMeta.hasLore() && !removableMeta.hasLore()){
                        System.out.println("No lore found");
                        System.out.println(""+removableMeta.getLore());
                        return true;
                    }
                }
            }
        }

        return false;
    }

}
