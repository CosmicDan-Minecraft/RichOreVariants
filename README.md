# RichOreVariants

Adds "Rich ore" versions of existing (automatically detected) ore blocks that are much harder to harvest but drop much more ore. DIY worldgen.

Pretty basic mod, designed for modpack makers. It scans the ore dictionary for all blocks that have a tag starting with "ore" and creates "Rich" versions. The blocks are created dynamically/automagically (thanks to Choonster for help with the model copy code).

## Feature list

* Adds Rich-versions of ore for all detected ore blocks (scans the ore dictionary for entries beginning with "ore")
* Rich ore will drop multiple versions of their base ore when broken (the multiple amount is configurable, default x10)
* Rich ore hardness is a multiple of the base ore hardness (also configurable)
* Preferred Mod ID's can be configured, so those particular-mod ores will be the base for the Rich ore (I.e. what they will look like, drop and have hardness based on)
* No world generation. It's designed for modpacks, so use COFH or whatever your favorite ore generator is.
* All configuration made possible in Forge mod config GUI

# Compatibility

Mod is pretty simple so there shouldn't be any major problems.

The Rich ore block models are created - or copied, rather - from their base blocks. If a mod-added ore block has some funky non-standard rendering or model, it might not work.