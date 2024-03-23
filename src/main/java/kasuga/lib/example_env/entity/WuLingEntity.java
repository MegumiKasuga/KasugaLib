package kasuga.lib.example_env.entity;

import kasuga.lib.core.client.render.RendererUtil;
import kasuga.lib.example_env.AllExampleElements;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WuLingEntity extends LivingEntity {
    public DoorControl doorControl = new DoorControl();

    public WuLingEntity(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    // public WuLingEntity(Level world) {
        // this(AllExampleElements.wuling.getType(), world);
    // }

    public static AttributeSupplier.Builder createAttributes() {
        return LivingEntity.createLivingAttributes().add(Attributes.MAX_HEALTH, 100D);
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return List.of();
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot pSlot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemSlot(EquipmentSlot pSlot, ItemStack pStack) {}

    @Override
    public void tick() {
        super.tick();
        if(level().isClientSide())
            clientTick();
        else
            serverTick();
    }

    @Override
    public void move(MoverType pType, Vec3 pPos) {
        super.move(pType, pPos);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        CompoundTag door = new CompoundTag();
        doorControl.write(door);
        pCompound.put("door", door);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if(pCompound.contains("door"))
            doorControl.read(pCompound.getCompound("door"));
    }

    public void serverTick() {

    }
    public void clientTick() {

    }

    @Override
    public void playerTouch(Player pPlayer) {
        super.playerTouch(pPlayer);
    }

    @Override
    public InteractionResult interactAt(Player pPlayer, Vec3 pVec, InteractionHand pHand) {
        Vec3 position = this.position();
        Vec3 playerPos = pPlayer.position();
        float angle = (float) RendererUtil.getVecHorizontalAngles(playerPos, position);
        float carAngle = this.getYRot();
        float relativeAngle = (angle - carAngle);
        if(relativeAngle > -10 && relativeAngle < 20) {
            doorControl.setMidBack(!doorControl.isMidBack());
        } else if (relativeAngle > 20 && relativeAngle < 90) {
            doorControl.setRightBack(!doorControl.isRightBack());
        } else if (relativeAngle > 90 && relativeAngle < 140) {
            doorControl.setRightFront(!doorControl.isRightFront());
        } else if (relativeAngle > -90 && relativeAngle < -10) {
            doorControl.setLeftBack(!doorControl.isLeftBack());
        } else if (relativeAngle > 220 && relativeAngle < 270) {
            doorControl.setLeftFront(!doorControl.isLeftFront());
        }
        return InteractionResult.SUCCESS;
    }

    public void onDrive(Player driver) {}

    @Override
    public HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }
}
