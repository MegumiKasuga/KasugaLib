package kasuga.lib.core.client.gui.widget;

import com.mojang.blaze3d.vertex.PoseStack;
import kasuga.lib.core.client.gui.enums.DisplayType;
import kasuga.lib.core.client.gui.enums.PositionType;
import kasuga.lib.core.client.gui.SimpleWidget;
import kasuga.lib.core.xml.IXmlObject;

public class DataDrivenWidget extends SimpleWidget {
    private int bgx, bgy, bgWidth, bgHeight;
    public DataDrivenWidget(int pX, int pY, int pWidth, int pHeight, PositionType type, DisplayType displayType) {
        super(pX, pY, pWidth, pHeight, type,displayType);
    }

    public DataDrivenWidget(int width, int height, PositionType type,DisplayType displayType) {
        super(width, height, type, displayType);
    }

    @Override
    public void init() {}

    public void setBackgroundX(int x) {
        this.bgx = this.x + x;
    }

    public void setBackgroundY(int y) {
        this.bgy = this.y + y;
    }

    public void setBackgroundWidth(int width) {
        this.bgWidth = width;
    }

    public void setBackgroundHeight(int height) {
        this.bgHeight = height;
    }

    public void setBackgroundLeft(int left) {
        this.bgx = this.x + left;
    }

    public void setBackgroundTop(int top) {
        this.bgy = this.y + top;
    }

    public void setBackgroundRight(int right) {
        this.bgWidth = this.width + this.x - bgx - right;
    }

    public void setBackgroundBottom(int bottom) {
        this.bgHeight = this.height + this.y - bgy - bottom;
    }

    public void decode(IXmlObject<?> object) {}

    public IXmlObject<?> encode() {
        return null;
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        if (hasBackground()) getBackground().render(x, y, width, height);
    }
}
