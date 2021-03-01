package com.namu.namulibrary.sign;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.stream.IntStream;

public final class SignMenuFactory {

    private static final int ACTION_INDEX = 9;
    private static final int SIGN_LINES = 4;

    private static final String NBT_FORMAT = "{\"text\":\"%s\"}";
    private static final String NBT_BLOCK_ID = "minecraft:sign";

    private final Plugin plugin;

    private final Map<Player, Menu> inputReceivers;

    /**
     * 표지판 GUI 팩토리
     * @param plugin JavaPlugin
     */
    public SignMenuFactory(Plugin plugin) {
        this.plugin = plugin;
        this.inputReceivers = new HashMap<>();
        this.listen();
    }

    /**
     * 최대 4개의 라인까지 넣어주세요.
     * 넘치면 어떻게 될지는 모릅니다.
     * @param text 문자열 리스트
     * @return Menu
     */
    public Menu newMenu(List<String> text) {
        Objects.requireNonNull(text, "text");
        return new Menu(text);
    }

    /**
     * GUI 리스너
     * Menu -> response 인터페이스 반환 값이 false 인 경우 다시 GUI 오픈
     */
    private void listen() {
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this.plugin, PacketType.Play.Client.UPDATE_SIGN) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                Player player = event.getPlayer();

                Menu menu = inputReceivers.remove(player);

                if (menu == null) {
                    return;
                }
                event.setCancelled(true);

                boolean success = menu.response.test(player, event.getPacket().getStringArrays().read(0));

                if (!success) {
                    Bukkit.getScheduler().runTaskLater(plugin, () -> menu.open(player), 2L);
                }

                player.sendBlockChange(menu.position.toLocation(player.getWorld()), Material.AIR.createBlockData());

            }
        });
    }

    public class Menu {

        private final List<String> text;

        private BiPredicate<Player, String[]> response;
        private boolean reopenIfFail;

        private BlockPosition position;

        Menu(List<String> text) {
            this.text = text;
        }

        protected BlockPosition getPosition() {
            return this.position;
        }

        public boolean opensOnFail() {
            return this.reopenIfFail;
        }

        public Menu reopenIfFail() {
            this.reopenIfFail = true;
            return this;
        }

        /**
         * 리스폰 인터페이스 내에서 리턴하는 boolean 값이
         * true 인 경우 창을 닫고,
         * false 인 경우 다시 GUI 를 엽니다.
         * @param response 리스폰 내에서 사용 될 플레이어와 문자열 배열 ( boolean 타입 반환 )
         * @return Menu
         */
        public Menu response(BiPredicate<Player, String[]> response) {
            this.response = response;
            return this;
        }

        /**
         * 플레이어에게 GUI 를 열어줍니다.
         * @param player 플레이어
         */
        public void open(Player player) {
            Objects.requireNonNull(player, "player");
            Location location = player.getLocation();
            this.position = new BlockPosition(location.getBlockX(), 0, location.getBlockZ());

            player.sendBlockChange(this.position.toLocation(location.getWorld()), Material.OAK_SIGN.createBlockData());

            PacketContainer openSign = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.OPEN_SIGN_EDITOR);
            PacketContainer signData = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.TILE_ENTITY_DATA);

            openSign.getBlockPositionModifier().write(0, this.position);

            NbtCompound signNBT = (NbtCompound) signData.getNbtModifier().read(0);

            IntStream.range(0, SIGN_LINES).forEach(line -> signNBT.put("Text" + (line + 1), text.size() > line ? String.format(NBT_FORMAT, color(text.get(line))) : " "));

            signNBT.put("x", this.position.getX());
            signNBT.put("y", this.position.getY());
            signNBT.put("z", this.position.getZ());
            signNBT.put("id", NBT_BLOCK_ID);

            signData.getBlockPositionModifier().write(0, this.position);
            signData.getIntegers().write(0, ACTION_INDEX);
            signData.getNbtModifier().write(0, signNBT);

            try {
                ProtocolLibrary.getProtocolManager().sendServerPacket(player, signData);
                ProtocolLibrary.getProtocolManager().sendServerPacket(player, openSign);
            } catch (InvocationTargetException exception) {
                exception.printStackTrace();
            }
            inputReceivers.put(player, this);
        }

        private String color(String input) {
            return ChatColor.translateAlternateColorCodes('&', input);
        }
    }
}
