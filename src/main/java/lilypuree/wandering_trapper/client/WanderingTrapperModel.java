package lilypuree.wandering_trapper.client;

import lilypuree.wandering_trapper.compat.IWeaponSelector;
import lilypuree.wandering_trapper.entity.WanderingTrapperEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WanderingTrapperModel<T extends WanderingTrapperEntity> extends EntityModel<T> implements IHasArm, IHasHead {
    public RendererModel head;
    public RendererModel body;
    public RendererModel handR;
    public RendererModel handL;
    public RendererModel legR;
    public RendererModel legL;
    public RendererModel hat;
    public RendererModel nose;
    public RendererModel coat;

    public ArmPose armPose = ArmPose.EMPTY;

    public WanderingTrapperModel(float scale){this(scale, 64, 64);}

    public WanderingTrapperModel(float scale, int textureWidth, int textureHeight){
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        this.body=(new RendererModel(this)).setTextureSize(textureWidth, textureHeight);
        this.body.setRotationPoint(0.0F, 0.0F,0.0F);
        this.body.setTextureOffset(0,38).addBox(-4.0F, 0.0F, -3.0F,8, 12, 6, scale);
        this.coat=(new RendererModel(this)).setTextureSize(textureWidth, textureHeight);
        this.coat.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.coat.setTextureOffset(0,0).addBox(-4.0F,0.5F, -3.0F, 8, 18,6, scale+0.7F);
        this.body.addChild(this.coat);

        this.head=(new RendererModel(this)).setTextureSize(textureWidth, textureHeight);
        this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.head.setTextureOffset(20,20).addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, scale);
        this.hat=(new RendererModel(this)).setTextureSize(textureWidth, textureHeight);
        this.hat.setRotationPoint(0.0F, 0.0F,0.0F);
        this.hat.setTextureOffset(28,0).addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, scale+0.5F);
        this.head.addChild(this.hat);
        this.nose=(new RendererModel(this)).setTextureSize(textureWidth, textureHeight);
        this.nose.setRotationPoint(0.0F, -2.0F, 0.0F);
        this.nose.setTextureOffset(22,0).addBox(-1.0F, -1.0F, -6.0F,2,4,2,scale);
        this.head.addChild(this.nose);

        this.handR=(new RendererModel(this,44,44)).setTextureSize(textureWidth, textureHeight);
        this.handR.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, scale);
        this.handR.setRotationPoint(-5.0F, 2.0F, 0.0F);
        this.handL=(new RendererModel(this,44,44)).setTextureSize(textureWidth, textureHeight);
        this.handL.mirror = true;
        this.handL.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, scale);
        this.handL.setRotationPoint(5.0F, 2.0F, 0.0F);

        this.legR = (new RendererModel(this,28,38)).setTextureSize(textureWidth, textureHeight);
        this.legR.setRotationPoint(-2.0F, 12.0F, 0.0F);
        this.legR.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, scale);
        this.legL = (new RendererModel(this,28,38)).setTextureSize(textureWidth, textureHeight);
        this.legL.mirror = true;
        this.legL.setRotationPoint(2.0F, 12.0F, 0.0F);
        this.legL.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, scale);

    }

    @Override
    public void render(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        this.head.render(scale);
        this.body.render(scale);
        this.handR.render(scale);
        this.handL.render(scale);
        this.legL.render(scale);
        this.legR.render(scale);
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch,float scaleFactor) {
        this.head.rotateAngleY=(float)Math.toRadians(netHeadYaw);
        this.head.rotateAngleX=(float)Math.toRadians(headPitch);
        this.legR.rotateAngleX= MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
        this.legL.rotateAngleX= MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount * 0.5F;
        this.legR.rotateAngleY = 0.0F;
        this.legL.rotateAngleY = 0.0F;

        this.handR.rotateAngleX =MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.2F;
        this.handR.rotateAngleY = 0.0F;
        this.handR.rotateAngleZ = 0.0F;
        this.handL.rotateAngleX =MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.2F;
        this.handL.rotateAngleY = 0.0F;
        this.handL.rotateAngleZ = 0.0F;

        if (this.armPose == ArmPose.BOW_AND_ARROW) {
            this.handR.rotateAngleY = -0.1F + this.head.rotateAngleY;
            this.handR.rotateAngleX = (-(float)Math.PI / 2F) + this.head.rotateAngleX;
            this.handL.rotateAngleX = -0.9424779F + this.head.rotateAngleX;
            this.handL.rotateAngleY = this.head.rotateAngleY - 0.4F;
            this.handL.rotateAngleZ = ((float)Math.PI / 2F);
        }else if(this.armPose == ArmPose.CROSSBOW_CHARGE){
            this.handR.rotateAngleY = -0.3F + this.head.rotateAngleY;
            this.handL.rotateAngleY = 0.6F + this.head.rotateAngleY;
            this.handR.rotateAngleX = (-(float)Math.PI / 2F) + this.head.rotateAngleX + 0.1F;
            this.handL.rotateAngleX = -1.5F + this.head.rotateAngleX;
        }
        else if(this.armPose == ArmPose.ATTACKING){
            float f = MathHelper.sin(this.swingProgress * (float)Math.PI);
            float f1 = MathHelper.sin((1.0F - (1.0F - this.swingProgress) * (1.0F - this.swingProgress)) * (float)Math.PI);

            this.handR.rotateAngleX = -0.4F;
            this.handR.rotateAngleY = 0.0F;
            this.handR.rotateAngleZ = 0.0F;
            this.handL.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.2F;
            this.handL.rotateAngleY = 0.0F;
            this.handL.rotateAngleZ = 0.0F;

            if (entityIn.getPrimaryHand() == HandSide.RIGHT) {
                this.handR.rotateAngleX = -0.4F;
                this.handR.rotateAngleY = 0.0F;
                this.handR.rotateAngleZ = 0.0F;
                this.handL.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.2F;
                this.handL.rotateAngleY = 0.0F;
                this.handL.rotateAngleZ = 0.0F;
                this.handR.rotateAngleX += f * 2.2F - f1 * 0.4F;
                this.handL.rotateAngleX += f * 1.2F - f1 * 0.4F;
            } else {
                this.handR.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.2F;
                this.handR.rotateAngleY = 0.0F;
                this.handR.rotateAngleZ = 0.0F;
                this.handL.rotateAngleX = -0.4F;
                this.handL.rotateAngleY = 0.0F;
                this.handL.rotateAngleZ = 0.0F;
                this.handR.rotateAngleX += f * 1.2F - f1 * 0.4F;
                this.handL.rotateAngleX += f * 2.2F - f1 * 0.4F;
            }
        }

        this.handR.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.handL.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.handR.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
        this.handL.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
    }

    @Override
    public RendererModel func_205072_a() {
        return this.head;
    }

    public RendererModel getArm(HandSide handSide){
        return handSide==HandSide.LEFT ? this.handL:this.handR;
    }


    @Override
    public void postRenderArm(float v, HandSide handSide) {
        this.getArm(handSide).postRender(0.0860F);
    }

    @Override
    public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        this.armPose =ArmPose.EMPTY;
        ItemStack itemStack = entityIn.getHeldItem(Hand.MAIN_HAND);
        IWeaponSelector weaponSelector = WanderingTrapperEntity.weaponSelector;
        if(weaponSelector.isGun() && entityIn.isAggressive()){
            this.armPose = ArmPose.CROSSBOW_CHARGE;
        }
        else if(itemStack.getItem() instanceof BowItem && entityIn.isAggressive()){
            this.armPose = ArmPose.BOW_AND_ARROW;
        }
        else if(entityIn.isAggressive()){
            this.armPose=ArmPose.ATTACKING;
        }
        super.setLivingAnimations(entityIn, limbSwing, limbSwingAmount, partialTick);
    }

    public void setRotateAngle(RendererModel rendererModel, float x, float y, float z){
        rendererModel.rotateAngleZ = z;
        rendererModel.rotateAngleY = y;
        rendererModel.rotateAngleX = z;
    }



    @OnlyIn(Dist.CLIENT)
    public static enum ArmPose{
        EMPTY,
        ATTACKING,
        BOW_AND_ARROW,
        THROW_SPEAR,
        CROSSBOW_CHARGE,
        CROSSBOW_HOLD
    }
}
