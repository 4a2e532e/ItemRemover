# ItemRemover
[![CircleCI](https://circleci.com/gh/4a2e532e/ItemRemover.svg?style=svg)](https://circleci.com/gh/4a2e532e/ItemRemover)

A spigot plugin to globally remove items from all inventories

THIS PROJECT IS NOT FINISHED AND SHOULD NOT BE USED, YET!

# Installation:
1. Download the latest release and put it in your plugins folder.
2. Inside the plugins folder, create a new folder called "ItemRemover" (without quotation marks).
3. Inside the ItemRemover folder, create a new file called "ItemsToRemove.yml" (without quotation marks).
4. Add all items you want to remove to ItemsToRemove.yml like this:

```yaml
item1:
  Material: DIRT
  Enchantments:
    PROTECTION_ENVIRONMENTAL: 10
    THORNS: 1
  Lore:
    - line1
    - line2
    - line3
    - etc
    
item2:
  Material: DIAMOND_SWORD
  Lore:
    - 'Super awesome sword'
    - 'Some other text'
  Enchantments:
    KNOCKBACK: 3
  
```
