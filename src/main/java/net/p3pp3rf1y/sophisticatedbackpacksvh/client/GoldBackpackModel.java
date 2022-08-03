package net.p3pp3rf1y.sophisticatedbackpacksvh.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;
import net.p3pp3rf1y.sophisticatedbackpacks.SophisticatedBackpacks;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.BackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.client.render.IBackpackModel;
import net.p3pp3rf1y.sophisticatedbackpacks.init.ModItems;
import net.p3pp3rf1y.sophisticatedcore.renderdata.RenderInfo;

import javax.annotation.Nullable;

import static net.p3pp3rf1y.sophisticatedbackpacks.client.render.BackpackModel.CHILD_SCALE;

public class GoldBackpackModel<T extends Entity> extends EntityModel<T> implements IBackpackModel {
	private static final ResourceLocation NO_TINT_TEXTURE = new ResourceLocation(SophisticatedBackpacks.MOD_ID, "textures/block/gold_backpack_no_tint.png");
	private static final ResourceLocation MAIN_TINT_TEXTURE = new ResourceLocation(SophisticatedBackpacks.MOD_ID, "textures/block/gold_backpack_main.png");
	private static final ResourceLocation ACCENT_TINT_TEXTURE = new ResourceLocation(SophisticatedBackpacks.MOD_ID, "textures/block/gold_backpack_accent.png");
	private final ModelPart leftClip;
	private final ModelPart leftPouch;
	private final ModelPart rightClip;
	private final ModelPart rightPouch;

	public GoldBackpackModel(ModelPart root) {
		leftClip = root.getChild("leftClip");
		leftPouch = root.getChild("leftPouch");
		rightClip = root.getChild("rightClip");
		rightPouch = root.getChild("rightPouch");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition leftClip = partdefinition.addOrReplaceChild("leftClip", CubeListBuilder.create(), PartPose.offset(0.0F, 20.35F, 0.0F));
		leftClip.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 9).addBox(-0.5F, -1.15F, -6.9F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rightPouch = partdefinition.addOrReplaceChild("rightPouch", CubeListBuilder.create(), PartPose.offset(0.0F, 20.35F, 0.0F));
		rightPouch.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.35F, -6.5F, 4.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition rightClip = partdefinition.addOrReplaceChild("rightClip", CubeListBuilder.create(), PartPose.offset(0.0F, 20.35F, 0.0F));
		rightClip.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 9).addBox(-0.5F, -1.15F, -6.9F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition leftPouch = partdefinition.addOrReplaceChild("leftPouch", CubeListBuilder.create(), PartPose.offset(0.0F, 20.35F, 0.0F));
		leftPouch.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.35F, -6.5F, 4.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		return LayerDefinition.create(meshdefinition, 16, 16);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		//noop
	}

	@Override
	public <L extends LivingEntity, M extends EntityModel<L>> void translateRotateAndScale(M parentModel, LivingEntity livingEntity, PoseStack poseStack, boolean wearsArmor) {
		//noop - translation done in render because of two legs it needs to be tied to
	}

	@Override
	public <L extends LivingEntity, M extends EntityModel<L>> void render(M parentModel, LivingEntity livingEntity, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int mainColor, int accentColor, Item backpackItem, RenderInfo renderInfo) {
		if (mainColor == BackpackWrapper.DEFAULT_CLOTH_COLOR) {
			mainColor = DefaultBackpackColors.getDefaultMainColor(backpackItem);
		}

		if (accentColor == BackpackWrapper.DEFAULT_BORDER_COLOR) {
			accentColor = DefaultBackpackColors.getDefaultAccentColor(backpackItem);
		}

		float accentRed = (accentColor >> 16 & 255) / 255.0F;
		float accentGreen = (accentColor >> 8 & 255) / 255.0F;
		float accentBlue = (accentColor & 255) / 255.0F;
		float mainRed = (mainColor >> 16 & 255) / 255.0F;
		float mainGreen = (mainColor >> 8 & 255) / 255.0F;
		float mainBlue = (mainColor & 255) / 255.0F;

		poseStack.pushPose();
		translate(livingEntity, poseStack, parentModel instanceof HumanoidModel<?> humanoidModel ? humanoidModel.rightLeg : null, 1);

		VertexConsumer vertexBuilder = buffer.getBuffer(RenderType.entityCutoutNoCull(NO_TINT_TEXTURE));

		rightClip.render(poseStack, vertexBuilder, packedLight, OverlayTexture.NO_OVERLAY);

		vertexBuilder = buffer.getBuffer(RenderType.entityCutoutNoCull(MAIN_TINT_TEXTURE));
		rightPouch.render(poseStack, vertexBuilder, packedLight, OverlayTexture.NO_OVERLAY, mainRed, mainGreen, mainBlue, 1);

		vertexBuilder = buffer.getBuffer(RenderType.entityCutoutNoCull(ACCENT_TINT_TEXTURE));
		rightPouch.render(poseStack, vertexBuilder, packedLight, OverlayTexture.NO_OVERLAY, accentRed, accentGreen, accentBlue, 1);
		poseStack.popPose();

		poseStack.pushPose();
		translate(livingEntity, poseStack, parentModel instanceof HumanoidModel<?> humanoidModel ? humanoidModel.leftLeg : null, -1);

		vertexBuilder = buffer.getBuffer(RenderType.entityCutoutNoCull(NO_TINT_TEXTURE));
		leftClip.render(poseStack, vertexBuilder, packedLight, OverlayTexture.NO_OVERLAY);

		vertexBuilder = buffer.getBuffer(RenderType.entityCutoutNoCull(MAIN_TINT_TEXTURE));
		leftPouch.render(poseStack, vertexBuilder, packedLight, OverlayTexture.NO_OVERLAY, mainRed, mainGreen, mainBlue, 1);

		vertexBuilder = buffer.getBuffer(RenderType.entityCutoutNoCull(ACCENT_TINT_TEXTURE));
		leftPouch.render(poseStack, vertexBuilder, packedLight, OverlayTexture.NO_OVERLAY, accentRed, accentGreen, accentBlue, 1);
		poseStack.popPose();
	}

	private void translate(LivingEntity livingEntity, PoseStack poseStack, @Nullable ModelPart legPart, int sideMultiplier) {
		if (legPart != null) {
			legPart.translateAndRotate(poseStack);
			poseStack.mulPose(Vector3f.YP.rotationDegrees(90F ));
		} else {
			if (livingEntity.isCrouching()) {
				poseStack.translate(0D, 0.2D, 0D);
			}
			poseStack.mulPose(Vector3f.YP.rotationDegrees(180));
		}

		double zOffset = !livingEntity.getItemBySlot(EquipmentSlot.LEGS).isEmpty() ? -0.15D  + BackpackArmorOffsetsManager.getOffsets(ModItems.GOLD_BACKPACK.get(), livingEntity.getItemBySlot(EquipmentSlot.LEGS).getItem()).map(offsets -> offsets.z).orElse(0D) : -0.15D;
		float yOffset = -1.20f;
		if (livingEntity.isBaby()) {
			poseStack.scale(CHILD_SCALE, CHILD_SCALE, CHILD_SCALE);
			yOffset = -0.6f;
			zOffset *= 0.5f;
		}

		poseStack.translate(0.01, yOffset, zOffset * -sideMultiplier);
		poseStack.mulPose(Vector3f.YP.rotationDegrees(90));
	}

	@Override
	public void renderBatteryCharge(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, float chargeRatio) {
		//noop
	}

	@Override
	public void renderFluid(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, Fluid fluid, float fill, boolean left) {
		//noop
	}

	@Override
	public EquipmentSlot getRenderEquipmentSlot() {
		return EquipmentSlot.LEGS;
	}
}