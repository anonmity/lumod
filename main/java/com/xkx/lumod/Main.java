package com.xkx.lumod;


import net.minecraft.Util;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod("lumod")
@Mod.EventBusSubscriber
public class Main {
    @SubscribeEvent
    public static void joinIn(PlayerEvent.PlayerLoggedInEvent event) {  //登录的初始化
        Player player =event.getPlayer();
        String info = player.getName().getContents();
        Level level = player.level;
        player.sendMessage(new TextComponent("You you you, this not is " +  info.toString() + " horse? " + "some day not see, too la!"), Util.NIL_UUID);
        player.sendMessage(new TextComponent("This message is send by" + (level.isClientSide()? "CLIENT" : "SERVER")), Util.NIL_UUID);

        Container a = player.getInventory(); //获取物品栏
        a.clearContent(); // 清空玩家物品栏
        ItemStack hoe = new ItemStack(Items.DIAMOND_HOE);  // 设定玩家初始物品
        ItemStack seed = new ItemStack(Items.WHEAT_SEEDS, 64);
        ItemStack boneMeal = new ItemStack(Items.BONE_MEAL, 64);
        ItemStack sword = new ItemStack(Items.WOODEN_SWORD);
        a.setItem(0, hoe); //把指定位置换为该物体
        a.setItem(1, seed);
        a.setItem(2, boneMeal);
        a.setItem(3, sword);
        player.sendMessage(new TextComponent("Your inventory has been initialized successfully!"), Util.NIL_UUID);  // 发送消息告知玩家已经设定完毕
    }

    @SubscribeEvent
    public static void rightClickItem(PlayerInteractEvent.RightClickItem event){  //持剑右击传送
        Player player = event.getPlayer();
        Level level = player.level;
        if (!level.isClientSide()){
            ItemStack ItemStack = event.getItemStack();
            Item item = ItemStack.getItem();
            if (item instanceof SwordItem){
                HitResult hitResult = player.pick(20, 0,false);//获取玩家焦点信息
                Vec3 vec3 = hitResult.getLocation();
                player.teleportTo(vec3.x, vec3.y, vec3.z);
            }
        }
    }


}
