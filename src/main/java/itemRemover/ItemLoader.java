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
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.List;

public class ItemLoader {

    private File itemsFile;
    private FileConfiguration itemsConfig;
    private Plugin plugin;
    private InventoryFilterListener filterListener;

    ItemLoader(Plugin p, InventoryFilterListener fl){
        plugin = p;
        filterListener = fl;

        itemsFile = new File(plugin.getDataFolder(), "ItemsToRemove.yml");
        itemsConfig = YamlConfiguration.loadConfiguration(itemsFile);
    }

    public void load(){
        for(String key: itemsConfig.getKeys(false)){

            ConfigurationSection configSection = itemsConfig.getConfigurationSection(key);
            filterListener.markItemForRemoval(createItemStack(configSection));
        }
    }

    private ItemStack createItemStack(ConfigurationSection configSection){
        ItemStack itemStack = new ItemStack(Material.valueOf(configSection.getString("Material")));
        ItemMeta meta = itemStack.getItemMeta();

        meta.setLore((List<String>) configSection.getList("Lore"));

        ConfigurationSection enchantments = configSection.getConfigurationSection("Enchantments");

        if(enchantments != null){
            for(String enchant: enchantments.getKeys(false)){
                if(Enchantment.getByName(enchant) != null){
                    meta.addEnchant(Enchantment.getByName(enchant), enchantments.getInt(enchant), true);
                }
            }
        }

        itemStack.setItemMeta(meta);

        return itemStack;
    }
}
