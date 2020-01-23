package lilypuree.wandering_trapper.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import lilypuree.wandering_trapper.entity.WanderingTrapperEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WanderingTrapperModel<T extends WanderingTrapperEntity> extends EntityModel<T> implements IHasArm, IHasHead {
    public ModelRenderer head;
    public ModelRenderer body;
    public ModelRenderer handR;
    public ModelRenderer handL;
    public ModelRenderer legR;
    public ModelRenderer legL;
    public ModelRenderer hat;
    public ModelRenderer nose;
    public ModelRenderer coat;

    public ArmPose armPose = ArmPose.EMPTY;

    public WanderingTrapperModel(float scale){this(scale, 64, 64);}

    public WanderingTrapperModel(float scale, int textureWidth, int textureHeight){
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        this.body=(new ModelRenderer(this)).setTextureSize(textureWidth, textureHeight);
        this.body.setRotationPoint(0.0F, 0.0F,0.0F);
        this.body.setTextureOffset(0,38).func_228301_a_(-4.0F, 0.0F, -3.0F,8.0F, 12.0F, 6.0F, scale);
        this.coat=(new ModelRenderer(this)).setTextureSize(textureWidth, textureHeight);
        this.coat.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.coat.setTextureOffset(0,0).func_228301_a_(-4.0F,0.5F, -3.0F, 8.0F, 18.0F,6.0F, scale+0.7F);
        this.body.addChild(this.coat);

        this.head=(new ModelRenderer(this)).setTextureSize(textureWidth, textureHeight);
        this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.head.setTextureOffset(20,20).func_228301_a_(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, scale);
        this.hat=(new ModelRenderer(this)).setTextureSize(textureWidth, textureHeight);
        this.hat.setRotationPoint(0.0F, 0.0F,0.0F);
        this.hat.setTextureOffset(28,0).func_228301_a_(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, scale+0.5F);
        this.head.addChild(this.hat);
        this.nose=(new ModelRenderer(this)).setTextureSize(textureWidth, textureHeight);
        this.nose.setRotationPoint(0.0F, -2.0F, 0.0F);
        this.nose.setTextureOffset(22,0).func_228301_a_(-1.0F, -1.0F, -6.0F,2.0F,4.0F,2.0F,scale);
        this.head.addChild(this.nose);

        this.handR=(new ModelRenderer(this,44,44)).setTextureSize(textureWidth, textureHeight);
        this.handR.func_228301_a_(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, scale);
        this.handR.setRotationPoint(-5.0F, 2.0F, 0.0F);
        this.handL=(new ModelRenderer(this,44,44)).setTextureSize(textureWidth, textureHeight);
        this.handL.mirror = true;
        this.handL.func_228301_a_(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, scale);
        this.handL.setRotationPoint(5.0F, 2.0F, 0.0F);

        this.legR = (new ModelRenderer(this,28,38)).setTextureSize(textureWidth, textureHeight);
        this.legR.setRotationPoint(-2.0F, 12.0F, 0.0F);
        this.legR.func_228301_a_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, scale);
        this.legL = (new ModelRenderer(this,28,38)).setTextureSize(textureWidth, textureHeight);
        this.legL.mirror = true;
        this.legL.setRotationPoint(2.0F, 12.0F, 0.0F);
        this.legL.func_228301_a_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, scale);

    }


    @Override
    public void func_225598_a_(MatrixStack p_225598_1_, IVertexBuilder p_225598_2_, int p_225598_3_, int p_225598_4_, float p_225598_5_, float p_225598_6_, float p_225598_7_, float p_225598_8_) {
        this.head.func_228309_a_(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
        this.body.func_228309_a_(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
        this.handR.func_228309_a_(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
        this.handL.func_228309_a_(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
        this.legL.func_228309_a_(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
        this.legR.func_228309_a_(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
    }

    @Override
    public void func_225597_a_(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
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
        }else if(this.armPose == ArmPose.ATTACKING){
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
    public ModelRenderer func_205072_a() {
        return this.head;
    }

    public ModelRenderer getArm(HandSide handSide){
        return handSide==HandSide.LEFT ? this.handL:this.handR;
    }

    @Override
    public void func_225599_a_(HandSide side, MatrixStack scale) {
        this.getArm(side).func_228307_a_(scale);
    }

    @Override
    public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        this.armPose =ArmPose.EMPTY;
        ItemStack itemStack = entityIn.getHeldItem(Hand.MAIN_HAND);
        if(itemStack.getItem() instanceof BowItem && entityIn.isAggressive()){
            this.armPose = ArmPose.BOW_AND_ARROW;
        }else if(entityIn.isAggressive()){
            this.armPose=ArmPose.ATTACKING;
        }
        super.setLivingAnimations(entityIn, limbSwing, limbSwingAmount, partialTick);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z){
        modelRenderer.rotateAngleZ = z;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleX = z;
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
