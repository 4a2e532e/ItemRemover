/*
Copyright (c) 2016 4a2e532e

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
