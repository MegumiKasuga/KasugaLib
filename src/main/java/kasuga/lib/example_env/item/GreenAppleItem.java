package kasuga.lib.example_env.item;

import kasuga.lib.core.base.CustomRenderedItem;
import kasuga.lib.example_env.client.item.renderer.GreenAppleItemRenderer;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;

public class GreenAppleItem extends CustomRenderedItem {

    public GreenAppleItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public BlockEntityWithoutLevelRenderer getCustomItemRenderer(BlockEntityRenderDispatcher dispatcher, EntityModelSet modelSet) {
        return new GreenAppleItemRenderer(dispatcher, modelSet);
    }
}