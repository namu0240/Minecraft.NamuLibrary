package com.namu.namulibrary.json;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

public class JsonHover implements JsonTextObj {

    private StringBuilder base;

    /**
     * JsonTextBuilder 에 추가하여 사용함.
     * Hover_Item 의 경우 렉을 유발할 수 있으므로 비동기 메서드로 보내기.
     * @param action HoverAction
     * @param obj ShowItem 의 경우 ItemStack ShowText 의 경우 String
     */
    public JsonHover(HoverAction action, Object obj) {
        String value;
        if(action.equals(HoverAction.SHOW_ITEM)) {
            ItemStack item = (ItemStack) obj;
            Map<Enchantment,Integer> enMap = item.getEnchantments();
            StringBuilder en = new StringBuilder();
            for(Enchantment enchant : enMap.keySet()) {
                en.append("{id:\\\"").append(enchant.getKey().getKey().toLowerCase()).append("\\\",lvl:").append(enMap.get(enchant) + 1).append("s}").append(",");
            }
            String enV = en.toString();
            if(enV.length() > 0) enV = enV.substring(0, enV.length()-1);
            List<String> lore = item.getItemMeta().getLore();
            StringBuilder lo = new StringBuilder();
            if(lore.size() > 0) { lo.append("Lore:["); }
            for(String line : lore) {
                lo.append("'{\\\"text\\\":\\\"").append(line).append("\\\"}'").append(",");
            }
            String loV = lo.toString();
            if(loV.length() > 0) loV = loV.substring(0, loV.length()-1)+"]";
            StringBuilder temp = new StringBuilder("{\"id\":\"")
                    .append(item.getType().toString().toLowerCase())
                    .append("\",\"count\":1,\"tag\":\"")
                    .append(
                            enV.length() > 0 ? "{Enchantments:["+enV+"]," : "{"
                    );
            item.getItemMeta().getDisplayName();
            temp.append("display:{Name:'{\\\"text\\\":\\\"")
                    .append(item.getItemMeta().getDisplayName())
                    .append("\\\"}'");
            if(loV.length() > 0) {
                temp
                        .append(",")
                        .append(loV)
                        .append("}");
            } else {
                temp.append("}");
            }
            temp.append("}\"}");
            value = temp.toString();
        } else {
            value = "\""+(String) obj+"\"";
        }
        base = new StringBuilder(",\"hoverEvent\":{\"action\":\"")
                .append(action.toString().toLowerCase())
                .append("\",\"contents\":")
                .append(value)
                .append("}");
    }

    @Override
    public String toJsonText() {
        base.append("}");
        return base.toString();
    }

    @Override
    public JsonTextObj add(JsonTextObj obj) {
        if(obj.addAble()) base.append(obj.getBase());
        return this;
    }

    @Override
    public String getBase() {
        return base.toString();
    }

    @Override
    public boolean addAble() {
        return true;
    }

    public enum HoverAction {
        SHOW_TEXT, SHOW_ITEM
    }
}
