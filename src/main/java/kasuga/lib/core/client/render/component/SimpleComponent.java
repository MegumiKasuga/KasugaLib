package kasuga.lib.core.client.render.component;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import kasuga.lib.core.client.render.SimpleColor;
import kasuga.lib.core.client.render.PoseContext;
import kasuga.lib.core.client.render.RendererUtil;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class SimpleComponent {
    public static final float DEFAULT_WORLD_ZOOM = .2f;
    private float zoom = DEFAULT_WORLD_ZOOM;
    Component content;
    Font font;
    SimpleColor color;
    PoseContext context = PoseContext.of();
    public SimpleComponent(Component content) {
        this.content = content;
        this.color = SimpleColor.fromRGBInt(0);
    }

    public Component getContent() {
        return content;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public Font getFont() {
        return font;
    }

    public void zoom(float zoom) {
        this.zoom = zoom;
    }

    public float getZoom() {
        return zoom;
    }

    public void translate(double x, double y, double z) {
        context.translate(x, - y, z);
    }

    public void rotateY(float yRot) {
        context.rotateY(yRot);
    }

    public void rotateZ(float yRot) {
        context.rotateZ(yRot);
    }

    public void rotateX(float yRot) {
        context.rotateX(yRot);
    }

    public void scale(float x, float y, float z) {
        context.scale(x, y, z);
    }

    public void lockMovement() {
        this.context.setLock(true);
    }

    public void unlockMovement() {
        this.context.setLock(false);
    }

    public boolean isMovementLocked() {
        return this.context.isLocked();
    }

    public void shouldAutoClearMovement(boolean flag) {
        context.setAutoClear(flag);
    }

    public boolean isAutoClearMovements() {
        return context.isAutoClear();
    }

    public void setColor(int r, int g, int b, int a) {
        this.color = SimpleColor.fromRGBA(r, g, b, a);
    }

    public void setColor(SimpleColor color) {
        this.color = color;
    }

    public SimpleColor getColor() {
        return color;
    }

    public void turnToPlayer(@Nullable Player player, Vec3 position) {
        this.rotateY((float) RendererUtil.getVecHorizontalAngles(position, player == null ? position : player.getEyePosition()) - 180);
    }

    public void renderInWorld(PoseStack pose, MultiBufferSource buffer, int xOffset, int yOffset, int light) {
        render(pose, buffer, xOffset, yOffset, light, true);
    }

    public void renderInWorld(PoseStack pose, MultiBufferSource buffer, int light) {
        render(pose, buffer, 0, 0, light, true);
    }

    public void renderCenteredInWorld(PoseStack pose, MultiBufferSource buffer, int xOffset, int yOffset, int light) {
        render(pose, buffer, xOffset - font.width(content)/2, yOffset - font.lineHeight/2, light, true);
    }

    public void renderCenteredInWorld(PoseStack pose, MultiBufferSource buffer, int light) {
        renderCenteredInWorld(pose, buffer, 0, 0, light);
    }

    public void renderInGUI(PoseStack pose, MultiBufferSource buffer, int xOffset, int yOffset, int light) {
        render(pose, buffer, xOffset, yOffset, light, false);
    }

    public void renderInGUI(PoseStack pose, MultiBufferSource buffer, int light) {
        renderInGUI(pose, buffer, 0, 0, light);
    }

    public void renderCenteredInGUI(PoseStack pose, MultiBufferSource buffer, int xOffset, int yOffset, int light) {
        render(pose, buffer, xOffset - font.width(content)/2, yOffset - font.lineHeight/2, light, false);
    }

    public void renderCenteredInGUI(PoseStack pose, MultiBufferSource buffer, int light) {
        renderCenteredInGUI(pose, buffer, 0, 0, light);
    }


    public void render(PoseStack pose, MultiBufferSource buffer, int xOffset, int yOffset, int light, boolean inWorld) {
        if(font == null) return;
        boolean shouldPush = pose.clear();
        Matrix4f last = null;
        if(shouldPush) {
            last = pose.last().pose();
            pose.popPose();
        }
        pose.pushPose();
        pose.scale(1.0f, -1.0f, 1.0f);
        pose.translate(.5f, 0, .5f);
        context.apply(pose);
        if(inWorld){
            pose.scale(zoom, zoom, zoom);
            font.drawInBatch(content, xOffset, yOffset, color.getRGB(), false,
                    pose.last().pose(), buffer, true, 0xffffff, light);
        } else {
            font.draw(pose, content, xOffset, yOffset, color.getRGB());
        }
        pose.popPose();
        if(shouldPush) {
            pose.pushPose();
            pose.mulPoseMatrix(last);
        }
    }
}
