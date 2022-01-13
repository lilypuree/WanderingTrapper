package lilypuree.wandering_trapper.client;

import com.mojang.blaze3d.vertex.PoseStack;
import lilypuree.wandering_trapper.Constants;
import lilypuree.wandering_trapper.compat.IWeaponSelector;
import lilypuree.wandering_trapper.entity.WanderingTrapperEntity;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;

public class WanderingTrapperModel<T extends WanderingTrapperEntity> extends HierarchicalModel<T> implements ArmedModel, HeadedModel {
    public static ModelLayerLocation TRAPPER_LAYER = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "wandering_trapper"), "main");

    private final ModelPart root;
    public ModelPart head;
    public ModelPart body;
    public ModelPart handR;
    public ModelPart handL;
    public ModelPart legR;
    public ModelPart legL;
    public ModelPart hat;
    public ModelPart nose;
    public ModelPart coat;

    public ArmPose armPose = ArmPose.EMPTY;

    public WanderingTrapperModel(ModelPart root) {
        this.root = root;
        this.body = root.getChild("body");
        this.head = root.getChild("head");
        this.hat = this.head.getChild("hat");
        this.nose = this.head.getChild("nose");
        this.handR = body.getChild("right_arm");
        this.handL = body.getChild("left_arm");
        this.legR = body.getChild("right_leg");
        this.legL = body.getChild("left_leg");
        this.coat = body.getChild("coat");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition builder = new MeshDefinition();
        PartDefinition rootPart = builder.getRoot();
        PartDefinition headPart = rootPart.addOrReplaceChild("head", CubeListBuilder.create().texOffs(20, 20).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F), PartPose.ZERO);
        headPart.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(22, 0).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F), PartPose.offset(0.0F, -2.0F, 0.0F));
        headPart.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(28, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.ZERO);

        PartDefinition bodyPart = rootPart.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F), PartPose.ZERO);
        bodyPart.addOrReplaceChild("coat", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, 0.5F, -3.0F, 8.0F, 18.0F, 6.0F, new CubeDeformation(0.7F)), PartPose.ZERO);
        bodyPart.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(44, 44).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(-5.0F, 2.0F, 0.0F));
        bodyPart.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(44, 44).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(5.0F, 2.0F, 0.0F));
        bodyPart.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(28, 38).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(-2.0F, 12.0F, 0.0F));
        bodyPart.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(28, 38).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(2.0F, 12.0F, 0.0F));
        return LayerDefinition.create(builder, 64, 64);
    }


//    @Override
//    public void renderToBuffer(PoseStack matrixStack, VertexConsumer iVertexBuilder, int i, int i1, float v, float v1, float v2, float v3) {
//        this.head.render(matrixStack, iVertexBuilder, i, i1, v, v1, v2, v3);
//        this.body.render(matrixStack, iVertexBuilder, i, i1, v, v1, v2, v3);
//        this.handR.render(matrixStack, iVertexBuilder, i, i1, v, v1, v2, v3);
//        this.handL.render(matrixStack, iVertexBuilder, i, i1, v, v1, v2, v3);
//        this.legL.render(matrixStack, iVertexBuilder, i, i1, v, v1, v2, v3);
//        this.legR.render(matrixStack, iVertexBuilder, i, i1, v, v1, v2, v3);
//    }

    @Override
    public ModelPart root() {
        return root;
    }


    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.yRot = (float) Math.toRadians(netHeadYaw);
        this.head.xRot = (float) Math.toRadians(headPitch);
        this.legR.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
        this.legL.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount * 0.5F;
        this.legR.yRot = 0.0F;
        this.legL.yRot = 0.0F;

        this.handR.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.2F;
        this.handR.yRot = 0.0F;
        this.handR.zRot = 0.0F;
        this.handL.xRot = Mth.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.2F;
        this.handL.yRot = 0.0F;
        this.handL.zRot = 0.0F;

        if (this.armPose == ArmPose.BOW_AND_ARROW) {
            this.handR.yRot = -0.1F + this.head.yRot;
            this.handR.xRot = (-(float) Math.PI / 2F) + this.head.xRot;
            this.handL.xRot = -0.9424779F + this.head.xRot;
            this.handL.yRot = this.head.yRot - 0.4F;
            this.handL.zRot = ((float) Math.PI / 2F);
        } else if (this.armPose == ArmPose.CROSSBOW_CHARGE) {
            this.handR.yRot = -0.3F + this.head.yRot;
            this.handL.yRot = 0.6F + this.head.yRot;
            this.handR.xRot = (-(float) Math.PI / 2F) + this.head.xRot + 0.1F;
            this.handL.xRot = -1.5F + this.head.xRot;
        } else if (this.armPose == ArmPose.ATTACKING) {
            float f = Mth.sin(this.attackTime * (float) Math.PI);
            float f1 = Mth.sin((1.0F - (1.0F - this.attackTime) * (1.0F - this.attackTime)) * (float) Math.PI);

            this.handR.xRot = -0.4F;
            this.handR.yRot = 0.0F;
            this.handR.zRot = 0.0F;
            this.handL.xRot = Mth.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.2F;
            this.handL.yRot = 0.0F;
            this.handL.zRot = 0.0F;

            if (entityIn.getMainArm() == HumanoidArm.RIGHT) {
                this.handR.xRot = -0.4F;
                this.handR.yRot = 0.0F;
                this.handR.zRot = 0.0F;
                this.handL.xRot = Mth.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.2F;
                this.handL.yRot = 0.0F;
                this.handL.zRot = 0.0F;
                this.handR.xRot += f * 2.2F - f1 * 0.4F;
                this.handL.xRot += f * 1.2F - f1 * 0.4F;
            } else {
                this.handR.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.2F;
                this.handR.yRot = 0.0F;
                this.handR.zRot = 0.0F;
                this.handL.xRot = -0.4F;
                this.handL.yRot = 0.0F;
                this.handL.zRot = 0.0F;
                this.handR.xRot += f * 1.2F - f1 * 0.4F;
                this.handL.xRot += f * 2.2F - f1 * 0.4F;
            }
        }

        this.handR.zRot += Mth.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.handL.zRot -= Mth.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.handR.xRot += Mth.sin(ageInTicks * 0.067F) * 0.05F;
        this.handL.xRot -= Mth.sin(ageInTicks * 0.067F) * 0.05F;
    }

    @Override
    public ModelPart getHead() {
        return this.head;
    }


    public ModelPart getArm(HumanoidArm handSide) {
        return handSide == HumanoidArm.LEFT ? this.handL : this.handR;
    }

    @Override
    public void translateToHand(HumanoidArm handSide, PoseStack matrixStack) {
        this.getArm(handSide).translateAndRotate(matrixStack);
    }

    @Override
    public void prepareMobModel(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        this.armPose = ArmPose.EMPTY;
        ItemStack itemStack = entityIn.getItemInHand(InteractionHand.MAIN_HAND);
        IWeaponSelector weaponSelector = WanderingTrapperEntity.weaponSelector;
        if (weaponSelector.isGun() && entityIn.isAggressive()) {
            this.armPose = ArmPose.CROSSBOW_CHARGE;
        } else if (itemStack.getItem() instanceof BowItem && entityIn.isAggressive()) {
            this.armPose = ArmPose.BOW_AND_ARROW;
        } else if (entityIn.isAggressive()) {
            this.armPose = ArmPose.ATTACKING;
        }
        super.prepareMobModel(entityIn, limbSwing, limbSwingAmount, partialTick);
    }

    public void setRotateAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.zRot = z;
        modelRenderer.yRot = y;
        modelRenderer.xRot = z;
    }

    public static enum ArmPose {
        EMPTY,
        ATTACKING,
        BOW_AND_ARROW,
        THROW_SPEAR,
        CROSSBOW_CHARGE,
        CROSSBOW_HOLD
    }
}
