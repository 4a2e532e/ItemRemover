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
