package rafradek.spin.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class SwordRender {
    private static final ItemStack woodSword = new ItemStack(Items.WOODEN_SWORD);
    private static final ItemStack stoneSword = new ItemStack(Items.STONE_SWORD);
    private static final ItemStack ironSword = new ItemStack(Items.IRON_SWORD);
    private static final ItemStack diamondSword = new ItemStack(Items.DIAMOND_SWORD);
    private static final ItemStack goldSword = new ItemStack(Items.GOLDEN_SWORD);

    public static void render(Entity entity, double range, double x, double y, double z) {
        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(x, y+1, z);
            int t = entity.ticksExisted * 10;
            GlStateManager.pushMatrix();
            {
                GlStateManager.translate(0, 0.5, 0);
                GlStateManager.rotate(-(t > 0 ? t % 360 : 0), 0, 1, 0);
                drawRing(entity.world, range);
            }
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            {
                GlStateManager.rotate((t > 0 ? t % 360 : 0), 0, 1, 0);
                drawRing(entity.world, range);
            }
            GlStateManager.popMatrix();
        }
        GlStateManager.popMatrix();
    }

    private static void drawRing(World world, double range) {
        RenderItem itemRender = Minecraft.getMinecraft().getRenderItem();
        for (int r = 0; r <= range + 1; r++) {
            GlStateManager.pushMatrix();
            {
                GlStateManager.translate(Math.cos(r) * range, 0, Math.sin(r) * range);
                GlStateManager.rotate(60, 1, 0, 0);
                ItemStack stack = getSword(r == 0 ? 0 : r % 5);
                itemRender.renderItem(stack, itemRender.getItemModelWithOverrides(stack, world, null));
            }
            GlStateManager.popMatrix();
        }
    }

    private static ItemStack getSword(int index) {
        switch (index) {
            default:
            case 0:
                return woodSword;
            case 1:
                return stoneSword;
            case 2:
                return ironSword;
            case 3:
                return diamondSword;
            case 4:
                return goldSword;
        }
    }
}
